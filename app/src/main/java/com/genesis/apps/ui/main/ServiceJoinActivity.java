package com.genesis.apps.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.CMN_0003;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityServiceJoinBinding;
import com.genesis.apps.databinding.ItemTermBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.TermView;
import com.genesis.apps.ui.main.home.BluehandsFilterFragment;
import com.genesis.apps.ui.myg.MyGOilTermDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.genesis.apps.comm.model.vo.TermVO.TERM_ESN_AGMT_N;

public class ServiceJoinActivity extends SubActivity<ActivityServiceJoinBinding> {

    private CMNViewModel cmnViewModel;
    private List<TermView> checkBoxs = new ArrayList<>();
    private ItemTermBinding itemTermAd;
    private CheckBox[] checkBoxsAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_join);
        getDataFromIntent();
        setViewModel();
        setObserver();
        setView();
        cmnViewModel.reqCMN0003(new CMN_0003.Request(APPIAInfo.INT04.getId()));
    }

    private void setView() {
        ui.setActivity(this);
        checkBoxsAd = new CheckBox[]{ui.cbSms,ui.cbEmail,ui.cbPhone};
        ui.cbSms.setOnCheckedChangeListener(listenerAd);
        ui.cbEmail.setOnCheckedChangeListener(listenerAd);
        ui.cbPhone.setOnCheckedChangeListener(listenerAd);
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                showFragment(new ServiceMembershipJoinFragment());
                break;
            case R.id.iv_arrow:
                try{
                    TermVO termVO = (TermVO)v.getTag(R.id.term);
                    Log.v("test","test:"+termVO.getTermCd());

                    if(termVO.getTermCd().equalsIgnoreCase(VariableType.TERM_SERVICE_JOIN_GRA0005)){ //광고성정보수신유무 선택 시
                        if(ui.lAdInfo.getVisibility()==View.VISIBLE){
                            itemTermAd.ivArrow.setImageResource(R.drawable.btn_accodian_open);
                            InteractionUtil.collapse(ui.lAdInfo);
                        }else{
                            itemTermAd.ivArrow.setImageResource(R.drawable.btn_accodian_close);
                            InteractionUtil.expand(ui.lAdInfo);
                        }
                    }else{
                        startActivitySingleTop(new Intent(this, ServiceTermDetailActivity.class).putExtra(MyGOilTermDetailActivity.TERMS_CODE,termVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
    }

    @Override
    public void setObserver() {
        cmnViewModel.getRES_CMN_0003().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getTermList()!=null&&result.data.getTermList().size()>0
                            &&result.data.getBlueTermList()!=null&&result.data.getBlueTermList().size()>0
                            &&result.data.getMypgTermList()!=null&&result.data.getMypgTermList().size()>0){
                        addTermToLayout(result.data.getTermList());
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        exitPage(serverMsg , ResultCodes.RES_CODE_NETWORK.getCode());
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    private void checkAgree() {

        for(int i=0; i<checkBoxs.size(); i++){
            if(checkBoxs.get(i).getTermVO().getTermEsnAgmtYn().equalsIgnoreCase(TermVO.TERM_ESN_AGMT_Y)&&!checkBoxs.get(i).getCheckBox().isChecked()){
                ui.btnBlock.setVisibility(View.VISIBLE);
                ui.btnNext.setEnabled(false);
                return;
            }
        }
        ui.btnBlock.setVisibility(View.INVISIBLE);
        ui.btnNext.setEnabled(true);
    }

    private void addTermToLayout(List<TermVO> termList){
        for(TermVO termVO : termList){
            final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTermBinding itemTermBinding = DataBindingUtil.inflate(inflater, R.layout.item_term, null, false);
            final View view = itemTermBinding.getRoot();

            itemTermBinding.setActivity(this);
            itemTermBinding.cb.setOnCheckedChangeListener(listener);

            // (필수) 혹은 (선택) 이 제목에 붙어있으면 일단 제거
            if(!TextUtils.isEmpty(termVO.getTermNm())){
                termVO.setTermNm(termVO.getTermNm().replaceAll("\\(선택\\)","").replaceAll("\\(필수\\)",""));
                //필수 혹은 선택을 값에 따라 다시 표기
                termVO.setTermNm(termVO.getTermNm() + (termVO.getTermEsnAgmtYn().equalsIgnoreCase(TERM_ESN_AGMT_N) ? getString(R.string.mg_con02_01_13) : getString(R.string.mg_con02_01_14)));
            }
            itemTermBinding.cb.setText(termVO.getTermNm());

            String target = getString(R.string.int03_3); //(필수)
            if(termVO.getTermNm().contains(getString(R.string.int03_3))){
                int start = termVO.getTermNm().indexOf(target.charAt(0));
                int end = start + target.length();
                Spannable span = (Spannable)itemTermBinding.cb.getText();
                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.x_ba544d)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            itemTermBinding.ivArrow.setTag(R.id.term, termVO);
            checkBoxs.add(new TermView(termVO, itemTermBinding.cb));
            ui.lTermBottom.addView(itemTermBinding.getRoot());

            if(termVO.getTermCd().equalsIgnoreCase(VariableType.TERM_SERVICE_JOIN_GRA0005)){
                itemTermAd = itemTermBinding;
                itemTermAd.ivArrow.setImageResource(R.drawable.btn_accodian_open);
                itemTermAd.cb.setOnCheckedChangeListener(listenerAdAll);
            }
        }
    }

    private void setCheckBoxsAdAll(boolean check){
        for(int i=0; i<checkBoxsAd.length;i++){
            checkBoxsAd[i].setChecked(check);
        }
    }

//    private void setCheckBoxsAll(){
//        for(int i=0; i<checkBoxs.size();i++){
//            if(!checkBoxs.get(i).checkBox.isChecked()){
//                ui.cbAll.setChecked(false);
//                return;
//            }
//        }
//        ui.cbAll.setChecked(true);
//    }


    CompoundButton.OnCheckedChangeListener listener = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            checkAgree();
        }
    };

    CompoundButton.OnCheckedChangeListener listenerAdAll = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            setCheckBoxsAdAll(compoundButton.isChecked());
        }
    };

    CompoundButton.OnCheckedChangeListener listenerAd = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            if(itemTermAd!=null&&itemTermAd.cb!=null){
                itemTermAd.cb.setChecked(checkBoxsAd[0].isChecked()&&checkBoxsAd[1].isChecked()&&checkBoxsAd[2].isChecked());
            }
        }
    };

}
