package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityServiceRelapseApply11Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * @author hjpark
 * @brief 하자재발통보1단계
 */

@AndroidEntryPoint
public class ServiceRelapseApply1Activity extends SubActivity<ActivityServiceRelapseApply11Binding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private final int[] layouts = {R.layout.activity_service_relapse_apply_1_1, R.layout.activity_service_relapse_apply_1_1, R.layout.activity_service_relapse_apply_1_2, R.layout.activity_service_relapse_apply_1_3};
    private final int[] textMsgId = {R.string.sm_flaw_02_7, R.string.sm_flaw_02_7, R.string.sm_flaw_02_8, R.string.sm_flaw_02_9};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;
    private AddressVO myPosition;
    private AddressVO addressVO;
    private LGNViewModel lgnViewModel;

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
        initConstraintSets();
        initEditView();
        initEmail();
        initPhoneNumber();
        ui.tvCsmrNm.setText(loginInfoDTO.getProfile()!=null ? loginInfoDTO.getProfile().getName() : "--");
        ui.tvCsmrTymd.setText(loginInfoDTO.getProfile()!=null ? parseDate(loginInfoDTO.getProfile().getBirthdate()) : "--");
    }

    private String parseDate(String dateOriginal) {
        if(TextUtils.isEmpty(dateOriginal)) {
            return "";
        }else {
            Date date = DateUtil.getDefaultDateFormat(dateOriginal, DateUtil.DATE_FORMAT_yyyyMMdd);
            return DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_mm_dd_dot);
        }
    }

    private void initEditView() {
        ui.etAddrDtl.setOnEditorActionListener(editorActionListener);
        ui.etEmlAdr.setOnEditorActionListener(editorActionListener);
        ui.etRegnTn.setOnEditorActionListener(editorActionListener);
        ui.etAddrDtl.setOnFocusChangeListener(focusChangeListener);
        ui.etEmlAdr.setOnFocusChangeListener(focusChangeListener);
        ui.etRegnTn.setOnFocusChangeListener(focusChangeListener);
    }

    private void initPhoneNumber() {
        String phoneNumber = loginInfoDTO.getProfile()!=null ? loginInfoDTO.getProfile().getMobileNum() : "" ;

        if(TextUtils.isEmpty(phoneNumber)){
            ui.etRegnTn.requestFocus();
        } else {
            ui.etRegnTn.setText(
                    StringUtil.parsingPhoneNumber(
                            PhoneNumberUtils.formatNumber(
                            phoneNumber.replaceAll("-",""), Locale.getDefault().getCountry()
                    )));
        }
    }

    private void initEmail() {
        String email = loginInfoDTO.getProfile()!=null ? loginInfoDTO.getProfile().getEmail() : "" ;

        if(TextUtils.isEmpty(email)){
            ui.etEmlAdr.requestFocus();
        }else{
            ui.etEmlAdr.setText(email);
        }
    }



    private void startMapView(){
        showFragment(new SearchAddressHMNFragment());
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.tv_addr:
            case R.id.l_addr_info:
                clearKeypad();
                startMapView();
                break;
            case R.id.btn_question:
                //TODO HTML 웹뷰 페이지로 이동.. 전문 호출 필요
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
        final boolean isValid = isValid();
        if(isValid){
            clearKeypad();
            VOCInfoVO vocInfoVO = new VOCInfoVO();
            vocInfoVO.setRdwNmDtlAdr(ui.etAddrDtl.getText().toString().trim());
            vocInfoVO.setRdwNmAdr(ui.tvAddrInfo2.getText().toString() + ui.tvAddrInfo1.getText().toString());
            vocInfoVO.setEmlAdr(ui.etEmlAdr.getText().toString().trim());
            String phNo = ui.etRegnTn.getText().toString().replaceAll("-","").trim();
            if(phNo.length()==10){
                //10자일때
                vocInfoVO.setRegnTn(phNo.substring(0,3));
                vocInfoVO.setFrtDgtTn(phNo.substring(3,6));
                vocInfoVO.setRealDgtTn(phNo.substring(6,10));
            }else{
                //11자일때
                vocInfoVO.setRegnTn(phNo.substring(0,3));
                vocInfoVO.setFrtDgtTn(phNo.substring(3,7));
                vocInfoVO.setRealDgtTn(phNo.substring(7,11));
            }
            vocInfoVO.setCsmrTymd(ui.tvCsmrTymd.getText().toString().replaceAll("\\.",""));
            vocInfoVO.setCsmrNm(ui.tvCsmrNm.getText().toString());
            startActivitySingleTop(new Intent(this, ServiceRelapseApply2Activity.class).putExtra(KeyNames.KEY_NAME_SERVICE_VOC_INFO_VO, vocInfoVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        }else{
            Log.d(ServiceRelapseApply1Activity.class.getSimpleName(),"valid is false");
            //do nothing
        }
    }


    @Override
    public void setViewModel() {
        ui.setActivity(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
        lgnViewModel.setMyPosition(myPosition.getCenterLat(), myPosition.getCenterLon());
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
        try {
            myPosition =(AddressVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_ADDR);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(myPosition==null||myPosition.getCenterLat()==0||myPosition.getCenterLon()==0){
                myPosition = new AddressVO();
                myPosition.setCenterLat(37.463936);
                myPosition.setCenterLon(127.042953);
            }
        }
    }

    /**
     * @author
     * @brief constraintSet 초기화
     */
    private void initConstraintSets() {
        views = new View[]{ui.lRegnTn, ui.lEmlAdr, ui.lAddr, ui.lAddrDtl};
        edits = new View[]{ui.etRegnTn, ui.etEmlAdr, ui.tvAddr, ui.etAddrDtl};
        for (int i = 0; i < layouts.length; i++) {
            constraintSets[i] = new ConstraintSet();

            if (i == 0)
                constraintSets[i].clone(ui.container);
            else if(i==1) {

            }else
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

            if(pos==2) {
                //2020-12-01 화면 전체를 덮는 입력 페이지는 자동 진입 안하도록 수정
//                startMapView();
            }
        }
    }



    private boolean checkValidPhoneNumber(){
        String celPhoneNo = ui.etRegnTn.getText().toString().replaceAll("-","").trim();

        if(TextUtils.isEmpty(celPhoneNo)){
            ui.etRegnTn.requestFocus();
            ui.lRegnTn.setError(getString(R.string.sm_emgc01_5));
            return false;
        }else if(!StringRe2j.matches(celPhoneNo, getString(R.string.check_phone_number))){
            ui.etRegnTn.requestFocus();
            ui.lRegnTn.setError(getString(R.string.sm_emgc01_26));
            return false;
        }else{
            ui.etRegnTn.setText(PhoneNumberUtils.formatNumber(celPhoneNo, Locale.getDefault().getCountry()));
            ui.etRegnTn.setSelection(ui.etRegnTn.length());
            ui.etRegnTn.clearFocus();
            ui.lRegnTn.setError(null);
            return true;
        }
    }

    private boolean checkValidEmail(){
        String email = Objects.requireNonNull(ui.etEmlAdr.getText()).toString().trim();
        if(TextUtils.isEmpty(email)){
            ui.etEmlAdr.requestFocus();
            ui.lEmlAdr.setError(getString(R.string.sm_flaw_02_13));
            return false;
        }else if(!StringRe2j.matches(email, getString(R.string.check_email))){
            ui.etEmlAdr.requestFocus();
            ui.lEmlAdr.setError(getString(R.string.sm_flaw_02_11));
            return false;
        }else{
            ui.etEmlAdr.clearFocus();
            ui.lEmlAdr.setError(null);
            doTransition(2);
            return true;
        }
    }


    private boolean checkValidAddr(){
        String addr = ui.tvAddrInfo1.getText().toString().trim() + ui.tvAddrInfo2.getText().toString().trim();
        if(TextUtils.isEmpty(addr)){
            ui.tvErrorAddr.setVisibility(View.VISIBLE);
            ui.tvErrorAddr.setText(getString(R.string.sm_emgc01_12));
            return false;
        }else{
            ui.lAddrInfo.setVisibility(View.VISIBLE);
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
            return true;
        }
    }

    private boolean isValid(){
        for(View view : views){
            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    case R.id.l_regn_tn:
                        return false;
                    case R.id.l_eml_adr:
                        return checkValidPhoneNumber()&&false;
                    case R.id.l_addr:
                        return checkValidPhoneNumber()&&checkValidEmail()&&false;
                    case R.id.l_addr_dtl:
                        return checkValidPhoneNumber()&&checkValidEmail()&&checkValidAddr()&&false;
                }
            }
        }
        return checkValidPhoneNumber()&&checkValidEmail()&&checkValidAddr()&&checkValidAddrDtl();
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
        List<SubFragment> fragments = getFragments();
        if (fragments != null && fragments.size() > 0) {
            hideFragment(fragments.get(0));
        } else {
            MiddleDialog.dialogServiceRelapseApplyExit(this, () -> {
                finish();
                closeTransition();
            }, () -> {

            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodes.REQ_CODE_APPLY_RELAPSE.getCode()) {
            exitPage(getString(R.string.relapse_succ), ResultCodes.REQ_CODE_APPLY_RELAPSE.getCode());
        }

//        if(resultCode == ResultCodes.REQ_CODE_SERVICE_SOS_MAP.getCode()){
//            addressVO = (AddressVO)data.getSerializableExtra(KeyNames.KEY_NAME_ADDR);
//            setViewAddr(addressVO);
//        }
    }

//    private void setViewAddr(AddressVO addressVO){
//        String[] addressInfo = getAddress(addressVO);
//        ui.tvAddrInfo1.setText(addressInfo[1]);
//        ui.tvAddrInfo1.setVisibility(TextUtils.isEmpty(addressInfo[1]) ? View.GONE : View.VISIBLE);
//        ui.tvAddrInfo2.setText(addressInfo[0]);
//        ui.tvAddrInfo2.setVisibility(TextUtils.isEmpty(addressInfo[0]) ? View.GONE : View.VISIBLE);
//        checkValidAddr();
//    }


    public void setAddressInfo(AddressVO addressVO){
        this.addressVO = addressVO;
        String[] addressInfo = getAddress(addressVO);
        ui.tvAddrInfo1.setText(addressInfo[1]);
        ui.tvAddrInfo1.setVisibility(TextUtils.isEmpty(addressInfo[1]) ? View.GONE : View.VISIBLE);
        ui.tvAddrInfo2.setText(addressInfo[0]);
        ui.tvAddrInfo2.setVisibility(TextUtils.isEmpty(addressInfo[0]) ? View.GONE : View.VISIBLE);
        checkValidAddr();
    }

}
