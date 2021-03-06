package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.SOS_3002;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.SosStatusVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityServiceChargeApply1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.view.listener.OnRemoveClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
 */

@AndroidEntryPoint
public class ServiceChargeApplyActivity extends SubActivity<ActivityServiceChargeApply1Binding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private SOSViewModel sosViewModel;
    private VehicleVO mainVehicle;
    private final int[] layouts = {R.layout.activity_service_charge_apply_1, R.layout.activity_service_charge_apply_2, R.layout.activity_service_charge_apply_3, R.layout.activity_service_charge_apply_4};
    private final int[] textMsgId = {R.string.sm_emgc01_7, R.string.sm_emgc01_10, R.string.sm_emgc01_13, R.string.sm_emgc01_16};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;
    private SosStatusVO sosStatusVO;

    private String areaClsCd;
    private AddressVO addressVO;

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
            //??? ?????? ?????? ????????? DB?????? GET
            mainVehicle = sosViewModel.getMainVehicleFromDB();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            initPhoneNumber();
            initConstraintSets();
            initEditView();
            initData();
        }
    }

    private void initData() {
        ui.tvChargeCnt.setText(String.format(Locale.getDefault(),getString(R.string.sm_cggo_01_4), sosStatusVO!=null ? sosStatusVO.getDataCount() : "0"));
    }

    private void initEditView() {
        ui.etCelPhNo.setOnEditorActionListener(editorActionListener);
        ui.etAddrDtl.setOnEditorActionListener(editorActionListener);
        ui.etCarRegNo.setOnEditorActionListener(editorActionListener);
        ui.etCelPhNo.setOnFocusChangeListener(focusChangeListener);
        ui.etAddrDtl.setOnFocusChangeListener(focusChangeListener);
        ui.etCarRegNo.setOnFocusChangeListener(focusChangeListener);
        ui.lCelPhNo.setEndIconOnClickListener(new OnRemoveClickListener(ui.etCelPhNo) {});
        ui.lAddrDtl.setEndIconOnClickListener(new OnRemoveClickListener(ui.etAddrDtl) {});
        ui.lCarRegNo.setEndIconOnClickListener(new OnRemoveClickListener(ui.etCarRegNo) {});
    }

    /**
     * @author hjpark
     * @brief ????????? ??????
     * ???????????? ?????????????????? ???????????? ?????? ?????? ????????? ?????? ??????
     * (????????? ???????????? ?????? ????????????)
     */
    private void initPhoneNumber() {
        String phoneNumber = loginInfoDTO.getProfile()!=null ? loginInfoDTO.getProfile().getMobileNum() : "" ;

        if(TextUtils.isEmpty(phoneNumber)){
            ui.etCelPhNo.requestFocus();
        }else{
            ui.etCelPhNo.setText(PhoneNumberUtils.formatNumber(phoneNumber.replaceAll("-",""), Locale.getDefault().getCountry()));
            ui.etCelPhNo.setSelection(ui.etCelPhNo.length());
            selectAreaClsCd();
        }
    }

    private void startMapView(){
        startActivitySingleTop(new Intent(this, MapSearchMyPositionActivity.class)
                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.sm_cggo_01_5)
                .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.sm_evss01_35)
                .putExtra(KeyNames.KEY_NAME_ADDR, addressVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.tv_addr:
            case R.id.l_addr_info:
                clearKeypad();
                startMapView();
                break;
            case R.id.tv_area_cls_cd:
                selectAreaClsCd();
                break;
            case R.id.btn_question:
                startActivitySingleTop(new Intent(this, ServiceChargePayInfoActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_next://??????
                doNext();
                break;
        }
    }

    private void clearKeypad(){
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
        if(edits!=null&&edits.length>0) {
            for (View view : edits) {
                view.clearFocus();
            }
        }
    }

    private void doNext(){
        if(isValid()){
            clearKeypad();
            sosViewModel.reqSOS3002(new SOS_3002.Request(APPIAInfo.SM_EMGC01.getId(),
                    mainVehicle.getVin(),
                    ui.etCarRegNo.getText().toString().trim(),
                    mainVehicle.getMdlCd(),
                    areaClsCd,
                    ui.tvAddrInfo1.getText().toString().trim() +" "+ui.tvAddrInfo2.getText().toString().trim()+" "+ui.etAddrDtl.getText().toString().trim(),
                    addressVO.getCenterLon()+"",addressVO.getCenterLat()+"",ui.etCelPhNo.getText().toString().trim()));
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
        sosViewModel.getRES_SOS_3002().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getRtCd())&&result.data.getRtCd().equalsIgnoreCase("0000")&&!TextUtils.isEmpty(result.data.getTmpAcptNo())){
                        startActivitySingleTop(new Intent(this, ServiceChargeApplyInfoActivity.class).putExtra(KeyNames.KEY_NAME_SOS_TMP_NO, result.data.getTmpAcptNo()).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT),0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        finish();
                        break;
                    }
                default:
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if (TextUtils.isEmpty(serverMsg)){
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try{
            sosStatusVO = (SosStatusVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SOS_STATUS_VO);
        }catch (Exception e){

        } finally {
            if (sosStatusVO == null) {
                exitPage("???????????? ?????? ???????????? ????????? ???????????? ???????????????.\n?????? ??? ?????? ????????? ?????????.", ResultCodes.RES_CODE_NETWORK.getCode());
            }
        }

    }

    /**
     * @author
     * @brief constraintSet ?????????
     */
    private void initConstraintSets() {
        views = new View[]{ui.lAreaClsCd, ui.lAddr, ui.lAddrDtl, ui.lCarRegNo};
        edits = new View[]{ui.etCelPhNo, ui.tvAddr, ui.etAddrDtl, ui.etCarRegNo};
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
                startMapView();
            }else if(pos==views.length-1){
                String carRegNo = mainVehicle.getCarRgstNo();
                if(!TextUtils.isEmpty(carRegNo)){
                    ui.etCarRegNo.setText(carRegNo);
                    ui.etCarRegNo.setSelection(ui.etCarRegNo.length());
                    //checkValidCarRegNo ????????? ???????????? ??????????????? ???????????? ????????? ????????? ????????????. ????????? ??? ????????? ????????? ??????
                    ui.etCarRegNo.clearFocus();
                    SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
//                    ui.tvMsg.setVisibility(View.GONE);
                    ///////////////////////////////
                    checkValidCarRegNo();
                }
                //??????????????? ????????? ?????????..
                ui.btnNext.setText(R.string.sm_emgc01_25);
            }
        }
    }

    /**
     * @brief ?????? ?????? ?????? ??????
     */
    private void selectAreaClsCd(){
        clearKeypad();
        final List<String> divList = Arrays.asList(getResources().getStringArray(R.array.service_sos_area_cls));
        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dialogInterface -> {
            String result = bottomListDialog.getSelectItem();
            if(!TextUtils.isEmpty(result)){
                areaClsCd  = VariableType.getAreaClsCd(result);
                ui.tvTitleAreaClsCd.setVisibility(View.VISIBLE);
                Paris.style(ui.tvAreaClsCd).apply(R.style.CommonSpinnerItemEnable);
                ui.tvAreaClsCd.setText(result);
                checkValidAreaClsCd();
            }
        });
        bottomListDialog.setDatas(divList);
        bottomListDialog.setTitle(getString(R.string.sm_emgc01_22));
        bottomListDialog.show();
    }

//    private void selectfltCd(){
//        final List<String> divList = Arrays.asList(getResources().getStringArray(R.array.service_sos_fit));
//        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
//        bottomListDialog.setOnDismissListener(dialogInterface -> {
//            String result = bottomListDialog.getSelectItem();
//            if(!TextUtils.isEmpty(result)){
//                fltCd  = VariableType.getFltCd(result);
//                ui.tvTitleFltCd.setVisibility(View.VISIBLE);
//                Paris.style(ui.tvFltCd).apply(R.style.CommonSpinnerItemEnable);
//                ui.tvFltCd.setText(result);
//                checkValidfltCd();
//            }
//        });
//        bottomListDialog.setDatas(divList);
//        bottomListDialog.setTitle(getString(R.string.sm_emgc01_23));
//        bottomListDialog.show();
//    }


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


//    private boolean checkValidfltCd(){
//        if(!TextUtils.isEmpty(fltCd)){
//            ui.tvErrorFltCd.setVisibility(View.INVISIBLE);
//            doTransition(1);
//            return true;
//        }else{
//            ui.tvErrorFltCd.setVisibility(View.VISIBLE);
//            ui.tvErrorFltCd.setText(R.string.sm_emgc01_24);
//            selectfltCd();
//            return false;
//        }
//    }

    private boolean checkValidAreaClsCd(){
        if(!TextUtils.isEmpty(areaClsCd)){
            ui.tvErrorAreaClsCd.setVisibility(View.INVISIBLE);
            doTransition(1);
            return true;
        }else{
            ui.tvErrorAreaClsCd.setVisibility(View.VISIBLE);
            Paris.style(ui.tvAreaClsCd).apply(R.style.CommonSpinnerItemError);
            ui.tvErrorAreaClsCd.setText(R.string.sm_emgc01_9);
            return false;
        }
    }

    private boolean checkValidAddr(){
        String addr = ui.tvAddrInfo1.getText().toString().trim() + ui.tvAddrInfo2.getText().toString().trim();
        if(TextUtils.isEmpty(addr)){
            ui.tvErrorAddr.setVisibility(View.VISIBLE);
            ui.tvErrorAddr.setText(getString(R.string.sm_emgc01_12));
            Paris.style(ui.tvAddr).apply(R.style.CommonInputItemError);
            return false;
        }else{
            ui.lAddrInfo.setVisibility(View.VISIBLE);
            ui.tvTitleAddr.setVisibility(View.VISIBLE);
            ui.tvAddr.setVisibility(View.GONE);
            ui.tvErrorAddr.setVisibility(View.INVISIBLE);
            doTransition(2);
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
            doTransition(3);
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
            ui.etCarRegNo.requestFocus();
            ui.lCarRegNo.setError(getString(R.string.sm_emgc01_27));
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
                    //?????? ???????????? ???????????? ???????????? ??????????????????
                    case R.id.l_area_cls_cd:
                        return checkValidPhoneNumber()&&false;
                    case R.id.l_addr:
                        return checkValidPhoneNumber()&&checkValidAreaClsCd()&&false;
                    case R.id.l_addr_dtl:
                        return checkValidPhoneNumber()&&checkValidAreaClsCd()&&checkValidAddr()&&false;
                    case R.id.l_car_reg_no:
                        return checkValidPhoneNumber()&&checkValidAreaClsCd()&&checkValidAddr()&&checkValidAddrDtl()&&false;
                }
            }
        }
        return checkValidPhoneNumber()&&checkValidAreaClsCd()&&checkValidAddr()&&checkValidAddrDtl()&&checkValidCarRegNo();
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
        clearKeypad();
        MiddleDialog.dialogServiceChargeApplyExit(this, () -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ResultCodes.REQ_CODE_SERVICE_SOS_MAP.getCode()&&data!=null){
            addressVO = (AddressVO)data.getSerializableExtra(KeyNames.KEY_NAME_ADDR);
            setViewAddr(addressVO);
        }
    }

    private void setViewAddr(AddressVO addressVO){
        String[] addressInfo = getAddress(addressVO);
        ui.tvAddrInfo1.setText(addressInfo[1]);
        ui.tvAddrInfo1.setVisibility(TextUtils.isEmpty(addressInfo[1]) ? View.GONE : View.VISIBLE);
        ui.tvAddrInfo2.setText(addressInfo[0]);
        ui.tvAddrInfo2.setVisibility(TextUtils.isEmpty(addressInfo[0]) ? View.GONE : View.VISIBLE);
        checkValidAddr();
    }


}
