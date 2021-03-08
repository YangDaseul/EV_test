package com.genesis.apps.ui.myg;

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
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.OIL_0001;
import com.genesis.apps.comm.model.api.gra.OIL_0002;
import com.genesis.apps.comm.model.api.gra.OIL_0004;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AgreeMeansVO;
import com.genesis.apps.comm.model.vo.AgreeTermVO;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.model.vo.TermOilVO;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.OILViewModel;
import com.genesis.apps.databinding.ActivityMygOilTermBinding;
import com.genesis.apps.databinding.ItemTermOilBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.TermView;
import com.genesis.apps.ui.main.ServiceTermDetailActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_OIL_JOIN_GSCT0007;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_OIL_JOIN_HDOL0005;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_OIL_JOIN_SOIL0003;
import static com.genesis.apps.comm.model.vo.TermOilVO.TERM_ESN_AGMT_Y;

public class MyGOilTermActivity extends SubActivity<ActivityMygOilTermBinding> {

    private String oilRfnCd;
    private OILViewModel oilViewModel;
    private List<TermView> checkBoxs = new ArrayList<>();
    private MarketingCheckBox marketingCheckBox;
    private ConstraintLayout hoTermLayout;
    private ImageView hoTermArrow;
    private CheckBox marketingCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_term);
        getDataFromIntent();
        setViewModel();
        setObserver();
        setView();
        oilViewModel.reqOIL0001(new OIL_0001.Request(APPIAInfo.MG_CON02_01.getId(), oilRfnCd));
    }

    private void setView() {
        ui.setActivity(this);
        ui.cbAll.setOnCheckedChangeListener(listenerAll);
        ui.tv1.setText(String.format(Locale.getDefault(), getString(R.string.mg_con02_01_2), getString(OilCodes.findCode(oilRfnCd).getOilNm())));
        ui.tv2.setText(String.format(Locale.getDefault(), getString(R.string.mg_con02_01_3), getString(OilCodes.findCode(oilRfnCd).getOilNm())));
//        ui.tv3.setText(String.format(Locale.getDefault(), getString(R.string.mg_con02_01_6), getString(OilCodes.findCode(oilRfnCd).getOilPontNm())));
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                reqJoin();
                break;
            case R.id.iv_arrow:
                try{
                    TermOilVO termVO = (TermOilVO)v.getTag(R.id.oil_term);
                    Log.v("test","test:"+termVO.getTermCd());

                    if(oilRfnCd.equalsIgnoreCase(OilPointVO.OIL_CODE_HDOL)){

                        if(hoTermLayout.getVisibility()==View.VISIBLE){
                            hoTermArrow.setImageResource(R.drawable.btn_accodian_open);
                            InteractionUtil.collapse(hoTermLayout, null);
                        }else{
                            hoTermArrow.setImageResource(R.drawable.btn_accodian_close);
                            InteractionUtil.expand(hoTermLayout, null);
                        }


                    }else{
                        oilViewModel.reqOIL0004(new OIL_0004.Request(APPIAInfo.MG_CON02_01.getId(), oilRfnCd,termVO.getTermVer(),termVO.getTermCd()));
                    }

//                    startActivitySingleTop(new Intent(this, MyGOilTermDetailActivity.class).putExtra(MyGOilTermDetailActivity.OIL_CODE,oilRfnCd).putExtra(MyGOilTermDetailActivity.TERMS_CODE,termVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void reqJoin() {
        List<AgreeTermVO> terms = new ArrayList<>();
        String agreeDate = DateUtil.getDate(Calendar.getInstance(Locale.getDefault()).getTime(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss);

        //정유소 약관에 대한 데이터구조 생성
        for(int i=0; i<checkBoxs.size(); i++){
            AgreeMeansVO agreeMeansVO = null;
            if(checkBoxs.get(i).getTermOilVO().getTermCd().equalsIgnoreCase(TERM_OIL_JOIN_SOIL0003)
                    ||checkBoxs.get(i).getTermOilVO().getTermCd().equalsIgnoreCase(TERM_OIL_JOIN_HDOL0005)
                    ||checkBoxs.get(i).getTermOilVO().getTermCd().equalsIgnoreCase(TERM_OIL_JOIN_GSCT0007)){
//                agreeMeansVO = new AgreeMeansVO((ui.cbSms.isChecked() ? "Y" : "N"), (ui.cbMail.isChecked() ? "Y" : "N"),(ui.cbDm.isChecked() ? "Y" : "N"),(ui.cbPhone.isChecked() ? "Y" : "N"),null,null);
                agreeMeansVO = new AgreeMeansVO(marketingCheckBox.isCheckedSms(),marketingCheckBox.isCheckedMail(),marketingCheckBox.isCheckedDm(),marketingCheckBox.isCheckedPhone(),null,null);
            }
            terms.add(new AgreeTermVO(checkBoxs.get(i).getTermOilVO().getTermCd(), (checkBoxs.get(i).getCheckBox().isChecked() ? "Y" : "N"),checkBoxs.get(i).getTermOilVO().getTermNm(),agreeDate,agreeMeansVO));
        }

        oilViewModel.reqOIL0002(new OIL_0002.Request(APPIAInfo.MG_CON02_01.getId(), oilRfnCd, terms));
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        oilViewModel = new ViewModelProvider(this).get(OILViewModel.class);
    }

    @Override
    public void setObserver() {
        oilViewModel.getRES_OIL_0001().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getTermList()!=null&&result.data.getTermList().size()>0) {
                        showProgressDialog(false);
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
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }

        });

        oilViewModel.getRES_OIL_0002().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        exitPage(getString(R.string.mg_con02_p01_3), ResultCodes.REQ_CODE_OIL_INTEGRATION_SUCCESS.getCode());
                        showProgressDialog(false);
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
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.mg_con01_p01_snackbar_3);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });


        oilViewModel.getRES_OIL_0004().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null&&result.data.getTermVO()!=null) {
                        startActivitySingleTop(new Intent(this, ServiceTermDetailActivity.class)
                                .putExtra(VariableType.KEY_NAME_TERM_VO, result.data.getTermVO())
                                , RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        showProgressDialog(false);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            oilRfnCd = getIntent().getStringExtra(OilCodes.KEY_OIL_CODE);
            if(TextUtils.isEmpty(oilRfnCd)){
                exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    private void checkAgree() {

        for(int i=0; i<checkBoxs.size(); i++){
            if(checkBoxs.get(i).getTermOilVO().getTermEsnAgmtYn().equalsIgnoreCase(TermVO.TERM_ESN_AGMT_Y)&&!checkBoxs.get(i).getCheckBox().isChecked()){
                ui.btnBlock.setVisibility(View.VISIBLE);
                ui.btnNext.setEnabled(false);
                return;
            }
        }
        ui.btnBlock.setVisibility(View.INVISIBLE);
        ui.btnNext.setEnabled(true);
    }

    private void addTermToLayout(List<TermOilVO> termList){
        marketingCheckBox = new MarketingCheckBox(null, null, null, null);
        int cnt=0;
        for(TermOilVO termVO : termList){
            cnt++;

            final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTermOilBinding itemTermOilBinding = DataBindingUtil.inflate(inflater, R.layout.item_term_oil, null, false);
            final View view = itemTermOilBinding.getRoot();

            itemTermOilBinding.cb.setOnCheckedChangeListener(listener);
            termVO.setTermNm(termVO.getTermNm().replace(getString(R.string.mg_con02_01_14), "").replace(getString(R.string.mg_con02_01_13), ""));
            String termNm = termVO.getTermNm()+" "+ (StringUtil.isValidString(termVO.getTermEsnAgmtYn()).equalsIgnoreCase(TERM_ESN_AGMT_Y) ? getString(R.string.mg_con02_01_14) : getString(R.string.mg_con02_01_13));
            itemTermOilBinding.cb.setText(termNm);
            String target = getString(R.string.mg_con02_01_14); //(필수)
            if(termNm.contains(getString(R.string.mg_con02_01_14))){
                int start = termNm.lastIndexOf(target.charAt(0));
                int end = start + target.length();
                Spannable span = (Spannable)itemTermOilBinding.cb.getText();
                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.x_996449)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }


            itemTermOilBinding.ivArrow.setTag(R.id.oil_term, termVO);
            itemTermOilBinding.setListener(onSingleClickListener);
            checkBoxs.add(new TermView(termVO, itemTermOilBinding.cb));

            //주석 표시
            if(TextUtils.isEmpty(termVO.getTermCmnt())){
                itemTermOilBinding.lTermCmnt.setVisibility(View.GONE);
            }else{
                itemTermOilBinding.lTermCmnt.setVisibility(View.VISIBLE);
                itemTermOilBinding.tvTermCmnt.setText(termVO.getTermCmnt().replaceAll("※",""));
            }

            //마지막 아이템 라인 제거
            itemTermOilBinding.ivLine.setVisibility(cnt==termList.size() ? View.INVISIBLE : View.VISIBLE);

            //개인정보동의 체크박스 셋팅
            if(termVO.getAgreeMeansNm()==null){
                itemTermOilBinding.lAgree.setVisibility(View.GONE);
            }else{
                itemTermOilBinding.lAgree.setVisibility(View.VISIBLE);
                //오일뱅크일 경우 수신동의 시에 대한 안내 문구 제거
                if(oilRfnCd.equalsIgnoreCase(OilPointVO.OIL_CODE_HDOL)){
                    itemTermOilBinding.tvAgreeInfo.setVisibility(View.GONE);
                    itemTermOilBinding.lHoTerm.setVisibility(View.VISIBLE);
                    itemTermOilBinding.ivArrow.setImageResource(R.drawable.btn_accodian_close);
                }else{
                    itemTermOilBinding.tvAgreeInfo.setVisibility(View.VISIBLE);
                    itemTermOilBinding.lHoTerm.setVisibility(View.GONE);
                    itemTermOilBinding.ivArrow.setVisibility(View.INVISIBLE);
                }
//                itemTermOilBinding.tvAgreeInfo.setVisibility(oilRfnCd.equalsIgnoreCase(OilPointVO.OIL_CODE_HDOL) ? View.GONE : View.VISIBLE);
//                itemTermOilBinding.ivArrow.setImageResource(oilRfnCd.equalsIgnoreCase(OilPointVO.OIL_CODE_HDOL) ? R.drawable.btn_accodian_open : null);

                hoTermLayout = itemTermOilBinding.lAgree;
                hoTermArrow = itemTermOilBinding.ivArrow;
                marketingCb = itemTermOilBinding.cb;
                try {
                    //마케팅정보 셋팅
                    String sms = termVO.getAgreeMeansNm().getAgreeSmsNm();
                    String email = termVO.getAgreeMeansNm().getAgreeEmailNm();
                    String dm = termVO.getAgreeMeansNm().getAgreePostNm();
                    String phone = termVO.getAgreeMeansNm().getAgreeTelNm();
                    itemTermOilBinding.cbSms.setText(TextUtils.isEmpty(sms) ? "" : sms);
                    itemTermOilBinding.cbMail.setText(TextUtils.isEmpty(email) ? "" : email);
                    itemTermOilBinding.cbDm.setText(TextUtils.isEmpty(dm) ? "" : dm);
                    itemTermOilBinding.cbPhone.setText(TextUtils.isEmpty(phone) ? "" : phone);
                    itemTermOilBinding.cbSms.setVisibility(TextUtils.isEmpty(sms) ? View.INVISIBLE : View.VISIBLE);
                    itemTermOilBinding.cbMail.setVisibility(TextUtils.isEmpty(email) ? View.INVISIBLE : View.VISIBLE);
                    itemTermOilBinding.cbDm.setVisibility(TextUtils.isEmpty(dm) ? View.INVISIBLE : View.VISIBLE);
                    itemTermOilBinding.cbPhone.setVisibility(TextUtils.isEmpty(phone) ? View.INVISIBLE : View.VISIBLE);
                    itemTermOilBinding.cbSms.setOnCheckedChangeListener(TextUtils.isEmpty(sms) ? null : marketingListener);
                    itemTermOilBinding.cbMail.setOnCheckedChangeListener(TextUtils.isEmpty(email) ? null : marketingListener);
                    itemTermOilBinding.cbDm.setOnCheckedChangeListener(TextUtils.isEmpty(dm) ? null : marketingListener);
                    itemTermOilBinding.cbPhone.setOnCheckedChangeListener(TextUtils.isEmpty(phone) ? null : marketingListener);
                    itemTermOilBinding.cb.setOnCheckedChangeListener(marketingListenerAll);

                    marketingCheckBox = new MarketingCheckBox(TextUtils.isEmpty(sms) ? null : itemTermOilBinding.cbSms, TextUtils.isEmpty(email) ? null : itemTermOilBinding.cbMail, TextUtils.isEmpty(dm) ? null : itemTermOilBinding.cbDm, TextUtils.isEmpty(phone) ? null : itemTermOilBinding.cbPhone);
                }catch (Exception e){

                }
            }

//            //현대오일뱅크일 경우 예외처리
//            if(oilRfnCd.equalsIgnoreCase(OilPointVO.OIL_CODE_HDOL)) {
//                //arrow 드랍 형태로 수정
//                itemTermOilBinding.ivArrow.setImageResource(R.drawable.btn_arrow_down);
//            }

            ui.lTermBottom.addView(itemTermOilBinding.getRoot());





//            정책이 변경되어 TOP AREA가 사라짐
//            switch (termVO.getTermCd()){
//                case "1000":
//                case "1001":
//                    ui.lTermTop.addView(itemTermBinding.getRoot());
//                    break;
//                default:
//                    ui.lTermBottom.addView(itemTermBinding.getRoot());
//                    break;
//            }
        }
    }

    private void setCheckBoxsAll(boolean check){
        for(int i=0; i<checkBoxs.size();i++){
            checkBoxs.get(i).getCheckBox().setChecked(check);
        }
    }

    private void setCheckBoxsAll(){
        for(int i=0; i<checkBoxs.size();i++){
            if(!checkBoxs.get(i).getCheckBox().isChecked()){
                ui.cbAll.setChecked(false);
                return;
            }
        }
        ui.cbAll.setChecked(true);
    }


    CompoundButton.OnCheckedChangeListener listener = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            setCheckBoxsAll();
            checkAgree();
        }
    };

    CompoundButton.OnCheckedChangeListener listenerAll = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            setCheckBoxsAll(b);
            checkAgree();
            checkMarketing(b);
        }
    };

    private void checkMarketing(boolean b) {
        try {
            if(marketingCheckBox!=null) {
                marketingCheckBox.setCheckEmail(b);
                marketingCheckBox.setCheckDm(b);
                marketingCheckBox.setCheckSms(b);
                marketingCheckBox.setCheckPhone(b);
                marketingCb.setChecked(b);
            }
        }catch (Exception ignore){

        }
    }

    public class MarketingCheckBox {
        private CheckBox cbSms;
        private CheckBox cbMail;
        private CheckBox cbDm;
        private CheckBox cbPhone;

        public MarketingCheckBox(CheckBox cbSms,CheckBox cbMail,CheckBox cbDm,CheckBox cbPhone){
            this.cbSms = cbSms;
            this.cbMail = cbMail;
            this.cbDm = cbDm;
            this.cbPhone = cbPhone;
        }

        public String isCheckedSms(){
            return cbSms==null ? null : (cbSms.isChecked() ? "Y" : "N");
        }

        public String isCheckedMail(){
            return cbMail==null ? null : (cbMail.isChecked() ? "Y" : "N");
        }

        public String isCheckedDm(){
            return cbDm==null ? null : (cbDm.isChecked() ? "Y" : "N");
        }

        public String isCheckedPhone(){
            return cbPhone==null ? null : (cbPhone.isChecked() ? "Y" : "N");
        }

        public boolean isCheckedSmsCb(){
            return cbSms == null || (cbSms.isChecked());
        }

        public boolean isCheckedDmCb(){
            return cbDm == null || (cbDm.isChecked());
        }

        public boolean isCheckedPhoneCb(){
            return cbPhone == null || (cbPhone.isChecked());
        }

        public boolean isCheckedMailCb(){
            return cbMail == null || (cbMail.isChecked());
        }

        public void setCheckEmail(boolean isCheck){
            if(cbMail!=null)
                cbMail.setChecked(isCheck);
        }

        public void setCheckDm(boolean isCheck){
            if(cbDm!=null)
                cbDm.setChecked(isCheck);
        }

        public void setCheckSms(boolean isCheck){
            if(cbSms!=null)
                cbSms.setChecked(isCheck);
        }

        public void setCheckPhone(boolean isCheck){
            if(cbPhone!=null)
                cbPhone.setChecked(isCheck);
        }

    }


    CompoundButton.OnCheckedChangeListener marketingListener = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            marketingCb.setChecked(marketingCheckBox.isCheckedSmsCb()|marketingCheckBox.isCheckedPhoneCb()|marketingCheckBox.isCheckedDmCb()|marketingCheckBox.isCheckedMailCb());
        }
    };

    CompoundButton.OnCheckedChangeListener marketingListenerAll = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            marketingCheckBox.setCheckEmail(b);
            marketingCheckBox.setCheckDm(b);
            marketingCheckBox.setCheckSms(b);
            marketingCheckBox.setCheckPhone(b);
        }
    };

}
