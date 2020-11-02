package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.CBK_1005;
import com.genesis.apps.comm.model.gra.api.CBK_1006;
import com.genesis.apps.comm.model.gra.api.SOS_1002;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CBKViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityInsightExpnInput1Binding;
import com.genesis.apps.databinding.ActivityServiceSosApply1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendar;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author hjpark
 * @brief 차계부 입력
 */
public class ServiceSOSApplyActivity extends SubActivity<ActivityServiceSosApply1Binding> {

    private SOSViewModel sosViewModel;
    private VehicleVO mainVehicle;
    private final int[] layouts = {R.layout.activity_service_sos_apply_1, R.layout.activity_service_sos_apply_2, R.layout.activity_service_sos_apply_3, R.layout.activity_service_sos_apply_4, R.layout.activity_service_sos_apply_5, R.layout.activity_service_sos_apply_6};
    private final int[] textMsgId = {R.string.sm_emgc01_2, R.string.sm_emgc01_7, R.string.sm_emgc01_10, R.string.sm_emgc01_13, R.string.sm_emgc01_19, R.string.sm_emgc01_16};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;

    private String areaClsCd;
    private String fitCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(layouts[0]);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        try {
            mainVehicle = sosViewModel.getMainVehicleFromDB();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            initPhoneNumber();
            initConstraintSets();

            ui.etCelPhNo.setOnEditorActionListener(editorActionListener);
            ui.etAddrDtl.setOnEditorActionListener(editorActionListener);
            ui.etMemo.setOnEditorActionListener(editorActionListener);
            ui.etCarRegNo.setOnEditorActionListener(editorActionListener);

            ui.etCelPhNo.setOnFocusChangeListener(focusChangeListener);
            ui.etAddrDtl.setOnFocusChangeListener(focusChangeListener);
            ui.etMemo.setOnFocusChangeListener(focusChangeListener);
            ui.etCarRegNo.setOnFocusChangeListener(focusChangeListener);
        }
    }

    private void initPhoneNumber() {
        //todo 폰번호가 없을땐?

        String phoneNumber = DeviceUtil.getPhoneNumber(getApplication());

        if(TextUtils.isEmpty(phoneNumber)){
            ui.etCelPhNo.requestFocus();
        }else{
            ui.etCelPhNo.setText(PhoneNumberUtils.formatNumber(DeviceUtil.getPhoneNumber(getApplication()), Locale.getDefault().getCountry()));
            ui.etCelPhNo.setSelection(ui.etCelPhNo.length());
            selectFitCd();
        }


    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.tv_addr:
            case R.id.l_addr_info:

                break;
            case R.id.tv_area_cls_cd:
                selectAreaClsCd();
                break;
            case R.id.tv_flt_cd:
                selectFitCd();
                break;
            case R.id.btn_question:

                break;
            case R.id.btn_next://다음
                doNext();
                break;
        }
    }

    private void clearKeypad(){
        for(View view : edits){
            view.clearFocus();
        }
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
    }

    private void doNext(){
        if(isValid()){
            clearKeypad();
//            sosViewModel.reqSOS1002(new SOS_1002.Request());
//            cbkViewModel.reqCBK1006(new CBK_1006.Request(APPIAInfo.TM_EXPS01_01.getId(), vin, expnDivCd, ui.etExpnAmt.getText().toString().replaceAll(",", ""), ui.tvExpnDtm.getText().toString().replaceAll(".", ""), ui.etExpnPlc.getText().toString(), ui.etAccmMilg.getText().toString().replaceAll(",", "")));
        }
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
    }

    @Override
    public void setObserver() {

//        cbkViewModel.getRES_CBK_1005().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getAccmMilg())){
//                        ui.etAccmMilg.setText(result.data.getAccmMilg());
//                        checkVaildAccmMilg();
//                        break;
//                    }
//                default:
//                    showProgressDialog(false);
//                    break;
//            }
//        });
//
//        cbkViewModel.getRES_CBK_1006().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getRtCd())&&result.data.getRtCd().equalsIgnoreCase("0000")){
//                        exitPage(getString(R.string.tm_exps01_01_17), ResultCodes.REQ_CODE_INSIGHT_EXPN_ADD.getCode());
//                        break;
//                    }
//                default:
//                    String serverMsg="";
//                    try {
//                        serverMsg = result.data.getRtMsg();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }finally{
//                        if (TextUtils.isEmpty(serverMsg)){
//                            serverMsg = getString(R.string.instability_network);
//                        }
//                        SnackBarUtil.show(this, serverMsg);
//                        showProgressDialog(false);
//                    }
//                    break;
//            }
//        });
    }

    @Override
    public void getDataFromIntent() {
//        try {
//            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (TextUtils.isEmpty(vin)) {
//                exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
//            }
//        }
    }

    private void initConstraintSets() {
        views = new View[]{ui.lFltCd, ui.lAreaClsCd, ui.lAddr, ui.lAddrDtl, ui.lMemo, ui.lCarRegNo};
        edits = new View[]{ui.etCelPhNo, ui.tvAreaClsCd, ui.tvAddr, ui.etAddrDtl, ui.etMemo, ui.etCarRegNo};
        for (int i = 0; i < layouts.length; i++) {
            constraintSets[i] = new ConstraintSet();

            if (i == 0)
                constraintSets[i].clone(ui.container);
            else
                constraintSets[i].clone(this, layouts[i]);
        }
    }

    private void doTransition(int pos) {
        if (views[pos].getVisibility() == View.GONE) {
            Transition changeBounds = new ChangeBounds();
            changeBounds.setInterpolator(new OvershootInterpolator());
            TransitionManager.beginDelayedTransition(ui.container);
            constraintSets[pos].applyTo(ui.container);
            ui.tvMsg.setText(textMsgId[pos]);

            if (edits[pos-1] instanceof TextInputEditText) {
                edits[pos].clearFocus();
            }

            if (edits[pos] instanceof TextInputEditText) {
                edits[pos].requestFocus();
            }

            if(pos==1){
                selectAreaClsCd();
            }else if(pos==2){
                //todo 자동 지도 활성화
            }else if(pos==views.length-1){
                String carRegNo = mainVehicle.getCarRgstNo();
                if(!TextUtils.isEmpty(carRegNo)){
                    ui.etCarRegNo.setText(carRegNo);
                    checkValidCarRegNo();
                }
                //차량번호가 없으면 그대로..
                ui.btnNext.setText(R.string.sm_emgc01_25);
            }
        }
    }

    /**
     * @brief 도로 구분 코드 선택
     */
    private void selectAreaClsCd(){
        final List<String> divList = Arrays.asList(getResources().getStringArray(R.array.service_sos_area_cls));
        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dialogInterface -> {
            String result = bottomListDialog.getSelectItem();
            if(!TextUtils.isEmpty(result)){
                areaClsCd  = VariableType.getAreaClsCd(result);
                ui.tvTitleAreaClsCd.setVisibility(View.VISIBLE);
                ui.tvAreaClsCd.setTextAppearance(R.style.CommonSpinnerItemEnable);
                ui.tvAreaClsCd.setText(result);
                checkValidAreaClsCd();
            }
        });
        bottomListDialog.setDatas(divList);
        bottomListDialog.setTitle(getString(R.string.sm_emgc01_22));
        bottomListDialog.show();
    }

    private void selectFitCd(){
        final List<String> divList = Arrays.asList(getResources().getStringArray(R.array.service_sos_fit));
        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dialogInterface -> {
            String result = bottomListDialog.getSelectItem();
            if(!TextUtils.isEmpty(result)){
                fitCd  = VariableType.getFitCd(result);
                ui.tvTitleFltCd.setVisibility(View.VISIBLE);
                ui.tvFltCd.setTextAppearance(R.style.CommonSpinnerItemEnable);
                ui.tvFltCd.setText(result);
                checkValidFitCd();
            }
        });
        bottomListDialog.setDatas(divList);
        bottomListDialog.setTitle(getString(R.string.sm_emgc01_22));
        bottomListDialog.show();
    }


    private boolean checkValidPhoneNumber(){
        String celPhoneNo = ui.etCelPhNo.getText().toString().replaceAll("-","").trim();

        if(TextUtils.isEmpty(celPhoneNo)){
            ui.etCelPhNo.requestFocus();
            ui.lCelPhNo.setError(getString(R.string.sm_emgc01_5));
            return false;
        }else if(!StringRe2j.matches(celPhoneNo, getString(R.string.check_phone_number))){
            ui.etCelPhNo.requestFocus();
            ui.lCelPhNo.setError(getString(R.string.sm_emgc01_26));
            return false;
        }else{
            ui.etCelPhNo.setText(PhoneNumberUtils.formatNumber(celPhoneNo, Locale.getDefault().getCountry()));
            ui.etCelPhNo.setSelection(ui.etCelPhNo.length());
            ui.etCelPhNo.clearFocus();
            ui.lCelPhNo.setError(null);
            return true;
        }
    }


    private boolean checkValidFitCd(){
        if(!TextUtils.isEmpty(fitCd)){
            ui.tvErrorFltCd.setVisibility(View.INVISIBLE);
            doTransition(1);
            return true;
        }else{
            ui.tvErrorFltCd.setVisibility(View.VISIBLE);
            ui.tvErrorFltCd.setText(R.string.sm_emgc01_24);
            selectFitCd();
            return false;
        }
    }

    private boolean checkValidAreaClsCd(){
        if(!TextUtils.isEmpty(areaClsCd)){
            ui.tvErrorAreaClsCd.setVisibility(View.INVISIBLE);
            doTransition(2);
            return true;
        }else{
            ui.tvErrorAreaClsCd.setVisibility(View.VISIBLE);
            ui.tvErrorAreaClsCd.setText(R.string.sm_emgc01_9);
            return false;
        }
    }

    private boolean checkValidAddr(){
        String addr = ui.tvAddrInfo1.getText().toString().trim() + ui.tvAddrInfo2.getText().toString().trim();
        if(TextUtils.isEmpty(addr)){
            ui.tvErrorAddr.setVisibility(View.VISIBLE);
            ui.tvErrorAddr.setText(getString(R.string.sm_emgc01_12));
            return false;
        }else{
            ui.tvTitleAddr.setVisibility(View.GONE);
            ui.tvAddr.setVisibility(View.GONE);
            ui.tvErrorAddr.setVisibility(View.INVISIBLE);
            doTransition(3);
            return true;
        }
    }

    private boolean checkValidAddrDtl(){
        String addrDtl = ui.etAddrDtl.getText().toString().trim();

        if(TextUtils.isEmpty(addrDtl)){
            ui.etAddrDtl.requestFocus();
            ui.lAddrDtl.setError(getString(R.string.sm_emgc01_15));
            return false;
        }else{
            ui.lAddrDtl.setError(null);
            doTransition(4);
            return true;
        }
    }

    private boolean checkValidMemo(){
        String memo = ui.etMemo.getText().toString().trim();

        if(TextUtils.isEmpty(memo)){
            ui.etMemo.requestFocus();
            ui.lMemo.setError(getString(R.string.sm_emgc01_21));
            return false;
        }else{
            ui.lMemo.setError(null);
            doTransition(5);
            return true;
        }
    }

    private boolean checkValidCarRegNo(){
        String carRegNo = ui.etCarRegNo.getText().toString().trim();

        if(TextUtils.isEmpty(carRegNo)){
            ui.etCarRegNo.requestFocus();
            ui.lCarRegNo.setError(getString(R.string.sm_emgc01_18));
            return false;
        }else if(!StringRe2j.matches(carRegNo, getString(R.string.check_car_vrn))){
            ui.etCelPhNo.requestFocus();
            ui.lCelPhNo.setError(getString(R.string.sm_emgc01_27));
            return false;
        }else{
            ui.lCarRegNo.setError(null);
            return true;
        }
    }

    private boolean isValid(){
        for(View view : views){
            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    //현재 페이지가 차량번호 입력하는 페이지일경우
                    case R.id.l_flt_cd:
                        return checkValidPhoneNumber()&&false;
                    case R.id.l_area_cls_cd:
                        return checkValidPhoneNumber()&&checkValidFitCd()&&false;
                    case R.id.l_addr:
                        return checkValidPhoneNumber()&&checkValidFitCd()&&checkValidAreaClsCd()&&false;
                    case R.id.l_addr_dtl:
                        return checkValidPhoneNumber()&&checkValidFitCd()&&checkValidAreaClsCd()&&checkValidAddr()&&false;
                    case R.id.l_memo:
                        return checkValidPhoneNumber()&&checkValidFitCd()&&checkValidAreaClsCd()&&checkValidAddr()&&checkValidAddrDtl()&&false;
                    case R.id.l_car_reg_no:
                        return checkValidPhoneNumber()&&checkValidFitCd()&&checkValidAreaClsCd()&&checkValidAddr()&&checkValidAddrDtl()&&checkValidMemo()&&false;
                }
            }
        }
        return checkValidPhoneNumber()&&checkValidFitCd()&&checkValidAreaClsCd()&&checkValidAddr()&&checkValidAddrDtl()&&checkValidMemo()&&checkValidCarRegNo();
    }


    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if(actionId== EditorInfo.IME_ACTION_DONE){
            doNext();
        }
        return false;
    };

    EditText.OnFocusChangeListener focusChangeListener = (view, hasFocus) -> {
        if (hasFocus) {
            SoftKeyboardUtil.showKeyboard(getApplicationContext());
        }
    };

    @Override
    public void onBackPressed() {
        dialogExit();
    }

    @Override
    public void onBackButton(){
        dialogExit();
    }

    private void dialogExit(){
        MiddleDialog.dialogServiceSOSApplyExit(this, () -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }

}
