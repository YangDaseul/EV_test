package com.genesis.apps.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CMN_0004;
import com.genesis.apps.comm.model.vo.AgreeMeansVO;
import com.genesis.apps.comm.model.vo.AgreeTermVO;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityMembershipJoinBinding;
import com.genesis.apps.databinding.ItemTermBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.common.view.TermView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_BLM0003;
import static com.genesis.apps.comm.model.vo.TermVO.TERM_ESN_AGMT_N;

@AndroidEntryPoint
public class ServiceMembershipJoinFragment extends SubFragment<ActivityMembershipJoinBinding> {
    private CMNViewModel cmnViewModel;
    private List<TermView> checkBoxs = new ArrayList<>();
    private ItemTermBinding itemTermAd;
    private CheckBox[] checkBoxsAd;
    private List<TermVO> list;

    public View.OnClickListener onClickListener = view -> onClickCommon(view);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.activity_membership_join);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        me.lTitle.back.setOnClickListener(onSingleClickListener);
        cmnViewModel = new ViewModelProvider(getActivity()).get(CMNViewModel.class);
        cmnViewModel.getRES_MBR_0001().observe(getViewLifecycleOwner(), result -> {

            switch (result.status){
                case LOADING:
                    ((SubActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")&&!TextUtils.isEmpty(result.data.getCustGbCd())&&!result.data.getCustGbCd().equalsIgnoreCase("0000")){
                        MiddleDialog.dialogJoin(getActivity(), () -> {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        });
                        break;
                    }
                default:
                    ((SubActivity)getActivity()).showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(getActivity(), serverMsg);
                    }
                    break;
            }
        });

//        cmnViewModel.getRES_CMN_0004().observe(getViewLifecycleOwner(), result -> {
//            switch (result.status) {
//                case LOADING:
//                    ((SubActivity)getActivity()).showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    if (result.data != null&&result.data.getTermVO()!=null) {
//                        ((SubActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceTermDetailActivity.class).putExtra(VariableType.KEY_NAME_TERM_VO, result.data.getTermVO()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                        ((SubActivity)getActivity()).showProgressDialog(false);
//                        break;
//                    }
//                default:
//                    ((SubActivity)getActivity()).showProgressDialog(false);
//                    String serverMsg = "";
//                    try {
//                        serverMsg = result.data.getRtMsg();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (TextUtils.isEmpty(serverMsg)) {
//                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
//                        }
//                        SnackBarUtil.show(getActivity(), serverMsg);
//                    }
//                    break;
//            }
//
//        });


        checkBoxsAd = new CheckBox[]{me.cbSms,me.cbEmail,me.cbPost,me.cbPhone};
        me.cbSms.setOnCheckedChangeListener(listenerAd);
        me.cbEmail.setOnCheckedChangeListener(listenerAd);
        me.cbPost.setOnCheckedChangeListener(listenerAd);
        me.cbPhone.setOnCheckedChangeListener(listenerAd);


        try {
            list = cmnViewModel.getRES_CMN_0003().getValue().data.getBlueTermList();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if (list != null && list.size() > 0) {
                addTermToLayout(list);
            }
        }
    }



    @Override
    public boolean onBackPressed() {
        getActivity().onBackPressed();
        return true;
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn_next:
                //TODO ???????????? ???????????? ?????? ??? ????????? ????????? ?????? ????????? ????????? ????????? ????????????.
                setAgreeStatus();
                cmnViewModel.reqMBR0001(cmnViewModel.getREQ_MBR_0001().getValue());
                break;
            case R.id.iv_arrow:
                try{
                    TermVO termVO = (TermVO)v.getTag(R.id.term);

                    if(termVO.getTermCd().equalsIgnoreCase(TERM_SERVICE_JOIN_BLM0003)){ //??????????????????????????? ?????? ???

                        if(me.lAdInfo.getVisibility()==View.VISIBLE){
                            itemTermAd.ivArrow.setImageResource(R.drawable.btn_accodian_open);
                            InteractionUtil.collapse(me.lAdInfo, null);
                        }else{
                            itemTermAd.ivArrow.setImageResource(R.drawable.btn_accodian_close);
                            InteractionUtil.expand(me.lAdInfo, null);
                        }
                    }else{
                        cmnViewModel.reqCMN0004(new CMN_0004.Request(APPIAInfo.INT04.getId(),termVO.getTermVer(),termVO.getTermCd()));
//                        ((SubActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceTermDetailActivity.class).putExtra(MyGOilTermDetailActivity.TERMS_CODE,termVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.cb_all:
                Log.d("JJJJ", "checked : " + me.cbAll.isChecked());

                for(int i=0; i<me.lTermBottom.getChildCount(); i++) {
                    ItemTermBinding binding = DataBindingUtil.bind(me.lTermBottom.getChildAt(i));

                    binding.cb.setChecked(me.cbAll.isChecked());
                    setCheckBoxsAdAll(me.cbAll.isChecked());
                }

                checkAgree();

                break;
        }
    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    }

    private void isAllCheck() {
        boolean isAllCheck = true;
        for(int i=0; i<checkBoxs.size(); i++){
            if(!checkBoxs.get(i).getCheckBox().isChecked()){
                isAllCheck = false;
            }
        }
        me.cbAll.setChecked(isAllCheck);
    }

    private void checkAgree() {
        for(int i=0; i<checkBoxs.size(); i++){
            if(checkBoxs.get(i).getTermVO().getTermEsnAgmtYn().equalsIgnoreCase(TermVO.TERM_ESN_AGMT_Y)&&!checkBoxs.get(i).getCheckBox().isChecked()){
                me.btnBlock.setVisibility(View.VISIBLE);
                me.btnNext.setEnabled(false);
                return;
            }
        }
        me.btnBlock.setVisibility(View.INVISIBLE);
        me.btnNext.setEnabled(true);
    }



    private void setAgreeStatus(){
        List<AgreeTermVO> blueTerms = new ArrayList<>();
        String agreeDate = DateUtil.getDate(Calendar.getInstance(Locale.getDefault()).getTime(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss);

        //???????????? ??? ????????? ?????? ??????????????? ??????
        for(int i=0; i<checkBoxs.size(); i++){
            AgreeMeansVO agreeMeansVO = null;
            if(checkBoxs.get(i).getTermVO().getTermCd().equalsIgnoreCase(TERM_SERVICE_JOIN_BLM0003)){
                agreeMeansVO = new AgreeMeansVO((me.cbSms.isChecked() ? "Y" : "N"), (me.cbEmail.isChecked() ? "Y" : "N"),(me.cbPost.isChecked() ? "Y" : "N"),(me.cbPhone.isChecked() ? "Y" : "N"),(checkBoxs.get(i).getCheckBox().isChecked() ? "Y" : "N"),null);
            }
            blueTerms.add(new AgreeTermVO(checkBoxs.get(i).getTermVO().getTermCd(), (checkBoxs.get(i).getCheckBox().isChecked() ? "Y" : "N"),checkBoxs.get(i).getTermVO().getTermNm(),agreeDate,agreeMeansVO));
        }

        cmnViewModel.getREQ_MBR_0001().getValue().setBlueTerms(blueTerms);
    }



    private void addTermToLayout(List<TermVO> termList){
        for(TermVO termVO : termList){
            final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTermBinding itemTermBinding = DataBindingUtil.inflate(inflater, R.layout.item_term, null, false);
            final View view = itemTermBinding.getRoot();

            itemTermBinding.setListener(onSingleClickListener);
            itemTermBinding.cb.setOnCheckedChangeListener(listener);

            // (??????) ?????? (??????) ??? ????????? ??????????????? ?????? ??????
            if(!TextUtils.isEmpty(termVO.getTermNm())){
                termVO.setTermNm(termVO.getTermNm().replaceAll("\\(??????\\)","").replaceAll("\\(??????\\)",""));
                //?????? ?????? ????????? ?????? ?????? ?????? ??????
                termVO.setTermNm(termVO.getTermNm() + (termVO.getTermEsnAgmtYn().equalsIgnoreCase(TERM_ESN_AGMT_N) ? getString(R.string.mg_con02_01_13) : getString(R.string.mg_con02_01_14)));
            }
            itemTermBinding.cb.setText(termVO.getTermNm());

            String target = getString(R.string.int03_3); //(??????)
            if(termVO.getTermNm().contains(getString(R.string.int03_3))){
                int start = termVO.getTermNm().indexOf(target.charAt(0));
                int end = start + target.length();
                Spannable span = (Spannable)itemTermBinding.cb.getText();
                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.x_996449)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            itemTermBinding.ivArrow.setTag(R.id.term, termVO);
            checkBoxs.add(new TermView(termVO, itemTermBinding.cb));
            me.lTermBottom.addView(itemTermBinding.getRoot());


            if(termVO.getTermCd().equalsIgnoreCase(TERM_SERVICE_JOIN_BLM0003)){
                itemTermAd = itemTermBinding;
                itemTermAd.ivArrow.setImageResource(R.drawable.btn_accodian_close);
                itemTermAd.cb.setOnCheckedChangeListener(listenerAdAll);
            }
        }
    }

    private void setCheckBoxsAdAll(boolean check){
        for(int i=0; i<checkBoxsAd.length;i++){
            checkBoxsAd[i].setChecked(check);
        }
    }

    CompoundButton.OnCheckedChangeListener listener = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            checkAgree();
            isAllCheck();
        }
    };

    CompoundButton.OnCheckedChangeListener listenerAdAll = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            setCheckBoxsAdAll(compoundButton.isChecked());
            isAllCheck();
        }
    };

    CompoundButton.OnCheckedChangeListener listenerAd = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            if(itemTermAd!=null&&itemTermAd.cb!=null){
                itemTermAd.cb.setChecked(checkBoxsAd[0].isChecked()||checkBoxsAd[1].isChecked()||checkBoxsAd[2].isChecked()||checkBoxsAd[3].isChecked());
            }
            isAllCheck();
        }
    };

}
