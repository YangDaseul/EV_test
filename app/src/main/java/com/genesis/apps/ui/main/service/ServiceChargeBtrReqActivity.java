package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityServiceChargeBtrReq1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomSelectKeyDeliveryDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendar;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ServiceChargeBtrReqActivity extends SubActivity<ActivityServiceChargeBtrReq1Binding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private SOSViewModel sosViewModel;
    private VehicleVO mainVehicle;
    private final int[] layouts = {R.layout.activity_service_charge_btr_req_1, R.layout.activity_service_charge_btr_req_2, R.layout.activity_service_charge_btr_req_3, R.layout.activity_service_charge_btr_req_4};
    private final int[] textMsgId = {R.string.service_charge_btr_txt_07, R.string.service_charge_btr_txt_01, R.string.service_charge_btr_txt_03, R.string.service_charge_btr_txt_05};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;

    private String keyDeliveryCd;
    private String rsvtHopeDt; //예약희망일자
    private String autoAmpmCd="A"; //오전오후구분코드
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
            //주 이용 차량 정보를 DB에서 GET
            mainVehicle = sosViewModel.getMainVehicleFromDB();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            initPhoneNumber();
            initCarRgstNo();
            selectKeyDeliveryCd();
            initConstraintSets();
            initEditView();
        }
    }

    private void initEditView() {
        ui.etAddrDtl.setOnEditorActionListener(editorActionListener);
        ui.etAddrDtl.setOnFocusChangeListener(focusChangeListener);
    }

    private void initCarRgstNo() {

        String carRegNo = mainVehicle.getCarRgstNo();

        if (!TextUtils.isEmpty(carRegNo)) {
            ui.etCarRegNo.setText(carRegNo);
            ui.etCarRegNo.setSelection(ui.etCarRegNo.length());
            //checkValidCarRegNo 에서는 포커스를 제거하거나 키보드를 내리는 부분을 넣지않음. 최초로 뷰 활성화 시에만 유효
            ui.etCarRegNo.clearFocus();
            SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
//                    ui.tvMsg.setVisibility(View.GONE);
            ///////////////////////////////
            checkValidCarRegNo();
        }
    }

    private void initPhoneNumber() {

        String phoneNumber = loginInfoDTO.getProfile()!=null ? loginInfoDTO.getProfile().getMobileNum() : "" ;

        if(TextUtils.isEmpty(phoneNumber)){
            ui.etCelPhNo.requestFocus();
        }else{
            ui.etCelPhNo.setText(PhoneNumberUtils.formatNumber(phoneNumber.replaceAll("-",""), Locale.getDefault().getCountry()));
            ui.etCelPhNo.setSelection(ui.etCelPhNo.length());
        }
    }

    private void startMapView(){
        clearKeypad();
        startActivitySingleTop(new Intent(this
                        ,MapSearchMyPositionActivity.class)
                        .putExtra(KeyNames.KEY_NAME_ADDR, addressVO)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.service_charge_btr_01)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.service_charge_btr_txt_09)
                ,RequestCodes.REQ_CODE_ACTIVITY.getCode()
                ,VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            // 차량 키 전달 방식 선택
            case R.id.tv_key_delivery_cd:
                selectKeyDeliveryCd();
                break;
            //주소검색
            case R.id.tv_addr:
            case R.id.l_addr_info:
                clearKeypad();
                startMapView();
                break;
            //예약희망일
            case R.id.tv_rsvt_hope_dt:
                selectCalendar();
                break;
            case R.id.btn_question:
//                startActivitySingleTop(new Intent(this, ServiceSOSPayInfoActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
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
//            moveToNextPage();
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

    }

    @Override
    public void getDataFromIntent() {
    }

    /**
     * @author
     * @brief constraintSet 초기화
     */
    private void initConstraintSets() {
        views = new View[]{ui.lKeyDeliveryCd, ui.lAddr, ui.lAddrDtl, ui.lRsvtHopeDt};
        edits = new View[]{ui.tvKeyDeliveryCd, ui.tvAddr, ui.etAddrDtl, ui.tvRsvtHopeDt};
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
            }else if(pos==2){
            }else if(pos==3){
                selectCalendar();
            }else if(pos==views.length-1){
                ui.btnNext.setText(R.string.sm_emgc01_25);
            }
        }
    }


    private void selectCalendar() {
        clearKeypad();
        DialogCalendar dialogCalendar = new DialogCalendar(this, R.style.BottomSheetDialogTheme);
        dialogCalendar.setOnDismissListener(dialogInterface -> {
            Calendar calendar = dialogCalendar.calendar;
            if(calendar!=null){
                rsvtHopeDt = DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
                autoAmpmCd = dialogCalendar.getAutoAmpmCd();
                checkValidRsvtHopeDt();
//                setViewDtm(calendar);
            }
        });
        dialogCalendar.setCalendarMaximum(Calendar.getInstance(Locale.getDefault()));
        dialogCalendar.setTitle(getString(R.string.service_charge_btr_03));
        dialogCalendar.setUseAutoAmpmCd(true);
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 1);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.DATE, 15);

        dialogCalendar.setCalendarMinimum(minCalendar);
        dialogCalendar.setCalendarMaximum(maxCalendar);
        dialogCalendar.setRemoveWeekends(true);
        dialogCalendar.show();

    }

    /**
     * @brief 차량 키 전달 방식 선택
     */
    private void selectKeyDeliveryCd(){
        final BottomSelectKeyDeliveryDialog selectKeyDeliveryDialog = new BottomSelectKeyDeliveryDialog(this, R.style.BottomSheetDialogTheme);
        selectKeyDeliveryDialog.setOnDismissListener(dialogInterface -> {
            String result = selectKeyDeliveryDialog.getSelectItem();
            if(!TextUtils.isEmpty(result)){
//                keyDeliveryCd  = VariableType.getAreaClsCd(result);
                keyDeliveryCd  = result;
                ui.tvTitleKeyDeliveryCd.setVisibility(View.VISIBLE);
                Paris.style(ui.tvKeyDeliveryCd).apply(R.style.CommonSpinnerItemEnable);
                ui.tvKeyDeliveryCd.setText(TextUtils.equals(result, "dk") ? R.string.service_charge_btr_txt_12 : R.string.service_charge_btr_txt_11);
                checkValidKeyDeliveryCd();
            }
        });
        selectKeyDeliveryDialog.show();
    }

    private boolean checkValidPhoneNumber() {
        String celPhoneNo = ui.etCelPhNo.getText().toString().replaceAll("-", "").trim();
        if (TextUtils.isEmpty(celPhoneNo)) {
            ui.etCelPhNo.requestFocus();
            ui.lCelPhNo.setError(getString(R.string.service_charge_btr_err_06));
            return false;
        } else if (!StringRe2j.matches(celPhoneNo, getString(R.string.check_phone_number))) {
            ui.etCelPhNo.requestFocus();
            ui.lCelPhNo.setError(getString(R.string.service_charge_btr_err_08));
            return false;
        } else {
            ui.etCelPhNo.setText(PhoneNumberUtils.formatNumber(celPhoneNo, Locale.getDefault().getCountry()));
            ui.etCelPhNo.setSelection(ui.etCelPhNo.length());
            ui.etCelPhNo.clearFocus();
            ui.lCelPhNo.setError(null);
            return true;
        }
    }

    private boolean checkValidCarRegNo(){
        String carRegNo = ui.etCarRegNo.getText().toString().trim();

        if(TextUtils.isEmpty(carRegNo)){
            ui.etCarRegNo.requestFocus();
            ui.lCarRegNo.setError(getString(R.string.service_charge_btr_err_05));
            return false;
        }else if(!StringRe2j.matches(carRegNo, getString(R.string.check_car_vrn))){
            ui.etCarRegNo.requestFocus();
            ui.lCarRegNo.setError(getString(R.string.service_charge_btr_err_07));
            return false;
        }else{
            ui.lCarRegNo.setError(null);
            return true;
        }
    }

    private boolean checkValidKeyDeliveryCd() {
        if (!TextUtils.isEmpty(keyDeliveryCd)) {
            ui.tvErrorKeyDeliveryCd.setVisibility(View.INVISIBLE);
            doTransition(1);
            return true;
        } else {
            Paris.style(ui.tvKeyDeliveryCd).apply(R.style.CommonSpinnerItemError);
            ui.tvErrorKeyDeliveryCd.setVisibility(View.VISIBLE);
            ui.tvErrorKeyDeliveryCd.setText(R.string.service_charge_btr_err_04);
            return false;
        }
    }

    private boolean checkValidAddr(){
        String addr = ui.tvAddrInfo1.getText().toString().trim() + ui.tvAddrInfo2.getText().toString().trim();
        if(TextUtils.isEmpty(addr)){
            Paris.style(ui.tvAddr).apply(R.style.CommonInputItemError);
            ui.tvErrorAddr.setVisibility(View.VISIBLE);
            ui.tvErrorAddr.setText(getString(R.string.service_charge_btr_err_01));
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
            ui.lAddrDtl.setError(getString(R.string.service_charge_btr_err_02));
            return false;
        }else{
            ui.lAddrDtl.setError(null);
            doTransition(3);
            return true;
        }
    }

    private boolean checkValidRsvtHopeDt() {
        if(TextUtils.isEmpty(rsvtHopeDt)){
            ui.tvRsvtHopeDt.setText(R.string.sm_r_rsv02_01_16);
            ui.tvRsvtHopeDt.setTextColor(getColor(R.color.x_aaabaf));
            ui.tvRsvtHopeDt.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_dadde3);
            ui.tvTitleRsvtHopeDt.setVisibility(View.GONE);
            Paris.style(ui.tvRsvtHopeDt).apply(R.style.CommonSpinnerItemCalendarError);
            ui.tvErrorRsvtHopeDt.setVisibility(View.VISIBLE);
            ui.tvErrorRsvtHopeDt.setText(R.string.service_charge_btr_err_03);
            return false;
        }else{
            String date = DateUtil.getDate(DateUtil.getDefaultDateFormat(rsvtHopeDt, DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot)
                    + " / "
                    + (autoAmpmCd.equalsIgnoreCase("A") ? getString(R.string.sm_r_rsv02_01_p02_2) : getString(R.string.sm_r_rsv02_01_p02_3));

            ui.tvRsvtHopeDt.setText(date);
            ui.tvRsvtHopeDt.setTextColor(getColor(R.color.x_000000));
            ui.tvRsvtHopeDt.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414);
            ui.tvTitleRsvtHopeDt.setVisibility(View.VISIBLE);
            ui.tvErrorRsvtHopeDt.setVisibility(View.INVISIBLE);

            return true;
        }
    }

    private boolean isValid(){
        for(View view : views){
            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    //현재 페이지가 차량번호 입력하는 페이지일경우
                    case R.id.l_key_delivery_cd:
                        return checkValidPhoneNumber()&&checkValidCarRegNo()&&false;
                    case R.id.l_addr:
                        return checkValidPhoneNumber()&&checkValidCarRegNo()&&checkValidKeyDeliveryCd()&&false;
                    case R.id.l_addr_detail:
                        return checkValidPhoneNumber()&&checkValidCarRegNo()&&checkValidKeyDeliveryCd()&&checkValidAddr()&&false;
                    case R.id.l_rsvt_hope_dt:
                        return checkValidPhoneNumber()&&checkValidCarRegNo()&&checkValidKeyDeliveryCd()&&checkValidAddr()&&checkValidAddrDtl()&&false;
                }
            }
        }
        return checkValidPhoneNumber()&&checkValidCarRegNo()&&checkValidKeyDeliveryCd()&&checkValidAddr()&&checkValidAddrDtl()&&checkValidRsvtHopeDt();
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
        MiddleDialog.dialogServiceChargeBtrReqExit(this, () -> {
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
