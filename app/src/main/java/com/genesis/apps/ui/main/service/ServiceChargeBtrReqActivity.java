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
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1008;
import com.genesis.apps.comm.model.api.gra.CHB_1009;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.carlife.BookingDateVO;
import com.genesis.apps.comm.model.vo.carlife.LotVO;
import com.genesis.apps.comm.model.vo.carlife.OptionVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityServiceChargeBtrReq1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.BottomSelectKeyDeliveryDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendarChargeBtr;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

@AndroidEntryPoint
public class ServiceChargeBtrReqActivity extends SubActivity<ActivityServiceChargeBtrReq1Binding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private CHBViewModel chbViewModel;

    private VehicleVO mainVehicle;
    private final int[] layouts = {R.layout.activity_service_charge_btr_req_1, R.layout.activity_service_charge_btr_req_2, R.layout.activity_service_charge_btr_req_3, R.layout.activity_service_charge_btr_req_4, R.layout.activity_service_charge_btr_req_5};
    private final int[] textMsgId = {R.string.service_charge_btr_txt_07, R.string.service_charge_btr_txt_01, R.string.service_charge_btr_txt_03, R.string.service_charge_btr_txt_20, R.string.service_charge_btr_txt_05};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;

    private boolean isDkAvl;    // 디지털 키 공유 가능 여부(비대면으로 서비스 신청 가능 여부)
    private String keyTransferType;     // 차량 키 전달 방식
    private String rsvtDate = ""; //예약희망일시
    private AddressVO addressVO;
    private String inOutCd;     // 주차장소 실내/실외 타입
    private LotVO locationVo;
    private boolean selectedOption = false;   //  세차 서비스 옵션 정보

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
            mainVehicle = chbViewModel.getMainVehicleFromDB();
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
            // 주차 장소 실내/실외 선택
            case R.id.tv_inout_cd:
                selectInOutCd();
                break;
            //예약희망일
            case R.id.tv_rsvt_hope_dt:
                requestPossibleTime();
                break;
            case R.id.btn_question:
                startActivitySingleTop(new Intent(this, ServiceChargeBtrInfoActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_next://다음
                doNext();
                break;
        }
    }

    private void clearKeypad(){
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
        ui.etCelPhNo.clearFocus();
        ui.etCarRegNo.clearFocus();
        for(View view : edits){
            view.clearFocus();
        }
    }

    private void doNext() {
        if (isValid()) {
            clearKeypad();
            reqChargeBtr();
        }
    }

    /**
     * 픽업앤충전 상품 가격 정보 조회 전문 요청
     */
    private void reqChargeBtr() {

        locationVo = new LotVO(VariableType.SERVICE_CHARGE_BTR_LOT_TYPE_STRT
                , inOutCd
                , addressVO.getCenterLat()
                , addressVO.getCenterLon()
                , getAddress(addressVO)[0]
                , ui.etAddrDtl.getText().toString().trim()
                , addressVO.getCname());

        // 픽업앤충전 신청 전문 요청
        chbViewModel.reqCHB1009(new CHB_1009.Request(APPIAInfo.SM_CGRV01.getId(),
                mainVehicle.getVin(),
                mainVehicle.getMdlCd(),
                rsvtDate,
                locationVo
        ));
    }

    /**
     * 픽업앤충전 결제정보 확인 페이지로 이동
     *
     * @param resVO
     */
    private void startServiceChargeBtrCheckActvity(CHB_1009.Response resVO) {
        Intent intent = new Intent(this, ServiceChargeBtrCheckActivity.class);
        intent.putExtra(KeyNames.KEY_NAME_CHB_HP_NO, ui.etCelPhNo.getText().toString().trim());
        intent.putExtra(KeyNames.KEY_NAME_CHB_CAR_NO, ui.etCarRegNo.getText().toString().trim());
        intent.putExtra(KeyNames.KEY_NAME_CHB_RSVT_DT, rsvtDate);
        intent.putExtra(KeyNames.KEY_NAME_CHB_KEY_TRANS_TY, keyTransferType);
        intent.putExtra(KeyNames.KEY_NAME_CHB_LOT_VO, locationVo);
        intent.putExtra(KeyNames.KEY_NAME_CHB_CONTENTS_VO, resVO);
        intent.putExtra(KeyNames.KEY_NAME_CHB_SELECTED_OPTION, selectedOption);

        startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);

        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
    }

    @Override
    public void setObserver() {
        chbViewModel.getRES_CHB_1008().observe(this, result ->{
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getDailyBookingSlotList() != null && result.data.getDailyBookingSlotList().size() > 0) {
                        selectCalendar(result.data);
                        break;
                    }
                default:
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
                        showProgressDialog(false);
                    }
                    break;
            }
        });

        chbViewModel.getRES_CHB_1009().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        startServiceChargeBtrCheckActvity(result.data);
                        break;
                    }
                default:
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
                        showProgressDialog(false);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            isDkAvl = getIntent().getBooleanExtra(KeyNames.KEY_NAME_IS_DK_AVL,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author
     * @brief constraintSet 초기화
     */
    private void initConstraintSets() {
        views = new View[]{ui.lKeyDeliveryCd, ui.lAddr, ui.lAddrDtl, ui.lInoutCd, ui.lRsvtHopeDt};
        edits = new View[]{ui.tvKeyDeliveryCd, ui.tvAddr, ui.etAddrDtl, ui.tvInoutCd, ui.tvRsvtHopeDt};
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

            if (pos == 1) {
//                startMapView();
            } else if (pos == 2) {
                ui.etAddrDtl.requestFocus();
            } else if(pos == 3){
                selectInOutCd();
            } else if (pos == views.length - 1) {
                requestPossibleTime();
//                ui.btnNext.setText(R.string.sm_emgc01_25);
            }
        }
    }

    /**
     * @brief 차량 키 전달 방식 선택
     */
    private void selectKeyDeliveryCd() {
        final BottomSelectKeyDeliveryDialog selectKeyDeliveryDialog = new BottomSelectKeyDeliveryDialog(this, R.style.BottomSheetDialogTheme);
        selectKeyDeliveryDialog.setDkAvl(isDkAvl);
        if (!TextUtils.isEmpty(ui.tvKeyDeliveryCd.getText())) {
            if (TextUtils.equals(getString(R.string.service_charge_btr_txt_12), ui.tvKeyDeliveryCd.getText()))
                selectKeyDeliveryDialog.setSelectItem(VariableType.SERVICE_CHARGE_BTR_KEY_TRANSFER_TYPE_DKC);
            else if (TextUtils.equals(getString(R.string.service_charge_btr_txt_11), ui.tvKeyDeliveryCd.getText()))
                selectKeyDeliveryDialog.setSelectItem(VariableType.SERVICE_CHARGE_BTR_KEY_TRANSFER_TYPE_FOB);
        }
        selectKeyDeliveryDialog.setOnDismissListener(dialogInterface -> {
            clearKeypad();
            String result = selectKeyDeliveryDialog.getSelectItem();
            if (!TextUtils.isEmpty(result)) {
                keyTransferType = result;
                ui.tvTitleKeyDeliveryCd.setVisibility(View.VISIBLE);
                Paris.style(ui.tvKeyDeliveryCd).apply(R.style.CommonSpinnerItemEnable);
                ui.tvKeyDeliveryCd.setText(VariableType.SERVICE_CHARGE_BTR_KEY_TRANSFER_TYPE_DKC.equals(keyTransferType) ? R.string.service_charge_btr_txt_12 : R.string.service_charge_btr_txt_11);
                checkValidKeyDeliveryCd();
            }
        });
        selectKeyDeliveryDialog.show();
    }

    /**
     * 주차 장소 실내/실외 선택
     */
    private void selectInOutCd() {
        clearKeypad();

        final List<String> parkingLotList = Arrays.asList(getResources().getStringArray(R.array.service_parking_lot_inout));
        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dialogInterface -> {
            String result = bottomListDialog.getSelectItem();
            if (!TextUtils.isEmpty(result)) {
                try{
                    inOutCd = VariableType.getInOutCd(result);
                    ui.tvTitleInoutCd.setVisibility(View.VISIBLE);
                    Paris.style(ui.tvInoutCd).apply(R.style.CommonSpinnerItemEnable);
                    ui.tvInoutCd.setText(result);
                    checkValidInOutCd();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        bottomListDialog.setDatas(parkingLotList);
        bottomListDialog.setTitle(getString(R.string.service_charge_btr_err_14));
        bottomListDialog.show();
    }


    /**
     * @brief 예약 가능 시간 요청
     */
    private void requestPossibleTime() {
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 1);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.DATE, 5);

        chbViewModel.reqCHB1008(new CHB_1008.Request(APPIAInfo.SM_CGRV01.getId(),
                DateUtil.getDate(minCalendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd),
                DateUtil.getDate(maxCalendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd)
        ));
    }

    /**
     * 예약 희망일/옵션 선택 달력
     *
     * @param data
     */
    private void selectCalendar(CHB_1008.Response data) {

        List<BookingDateVO> list = data.getDailyBookingSlotList();

        clearKeypad();
        DialogCalendarChargeBtr dialogCalendar = new DialogCalendarChargeBtr(this, R.style.BottomSheetDialogTheme, onSingleClickListener);
        dialogCalendar.setOnDismissListener(dialogInterface -> {
            Calendar calendar = dialogCalendar.calendar;
            if (calendar != null) {
                this.rsvtDate = DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
                this.rsvtDate += dialogCalendar.getSelectBookingTime();

                this.selectedOption = dialogCalendar.getOptionChecked();

                checkValidRsvtHopeDt();
            }
        });

        dialogCalendar.setCalendarMaximum(Calendar.getInstance(Locale.getDefault()));
        dialogCalendar.setTitle(getString(R.string.service_charge_btr_03));
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 1);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.DATE, 5);

        dialogCalendar.setCalendarMinimum(minCalendar);
        dialogCalendar.setCalendarMaximum(maxCalendar);
        dialogCalendar.setBookingDateVOList(list);

        // 옵션 정보
        if (data.getOptionList() != null && data.getOptionList().size() > 0) {
            for (OptionVO vo : data.getOptionList()) {
                if (StringUtil.isValidString(vo.getOptionCode()).equalsIgnoreCase(VariableType.SERVICE_CHARGE_BTR_OPT_CD_2)) {
                    dialogCalendar.setOptionVO(vo);
                    break;
                }
            }
        }

        if(!TextUtils.isEmpty(rsvtDate) && rsvtDate.length() > 8)
            dialogCalendar.setSelectBookingDay(rsvtDate.substring(0, 8));
        dialogCalendar.setOptionChecked(this.selectedOption);
        dialogCalendar.setRemoveWeekends(true);
        dialogCalendar.show();

    }

    /**
     * 휴대폰 번호 입력 여부 확인
     *
     * @return
     */
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

    /**
     * 차량번호 입력 여부 확인
     *
     * @return
     */
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

    /**
     * 차량 키 전달 방식 선택 여부 확인
     *
     * @return
     */
    private boolean checkValidKeyDeliveryCd() {
        if (!TextUtils.isEmpty(keyTransferType)) {
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

    /**
     * 픽업 주소 입력 여부 확인
     *
     * @return
     */
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

    /**
     * 상세 주소 입력 여부 확인
     *
     * @return
     */
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

    /**
     * 주차 장소 실내/실외 선택 선택 여부 확인
     *
     * @return
     */
    private boolean checkValidInOutCd() {
        if (!TextUtils.isEmpty(inOutCd)) {
            ui.tvErrorInoutCd.setVisibility(View.INVISIBLE);
            doTransition(4);
            return true;
        } else {
            Paris.style(ui.tvInoutCd).apply(R.style.CommonSpinnerItemError);
            ui.tvErrorInoutCd.setVisibility(View.VISIBLE);
            ui.tvErrorInoutCd.setText(R.string.service_charge_btr_err_14);
            return false;
        }
    }

    /**
     * 예약 희망일 선택 여부 확인
     *
     * @return
     */
    private boolean checkValidRsvtHopeDt() {
        if(TextUtils.isEmpty(rsvtDate)){
            ui.tvRsvtHopeDt.setText(R.string.service_charge_btr_txt_06);
            ui.tvRsvtHopeDt.setTextColor(getColor(R.color.x_aaabaf));
            ui.tvRsvtHopeDt.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_dadde3);
            ui.tvTitleRsvtHopeDt.setVisibility(View.GONE);
            Paris.style(ui.tvRsvtHopeDt).apply(R.style.CommonSpinnerItemCalendarError);
            ui.tvErrorRsvtHopeDt.setVisibility(View.VISIBLE);
            ui.tvErrorRsvtHopeDt.setText(R.string.service_charge_btr_err_03);
            return false;
        }else{
            ui.tvRsvtHopeDt.setText(getDateOptionFormat(rsvtDate, selectedOption));
            ui.tvRsvtHopeDt.setTextColor(getColor(R.color.x_000000));
            ui.tvRsvtHopeDt.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414);
            ui.tvTitleRsvtHopeDt.setVisibility(View.VISIBLE);
            ui.tvErrorRsvtHopeDt.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    /**
     * 예약 희망일/옵션 정보 표시
     *
     * @param date            일시
     * @param isCheckedOption 옵션 선택 여부
     * @return
     */
    private String getDateOptionFormat(String date, boolean isCheckedOption) {
        String resultDtm = DateUtil.getDate(DateUtil.getDefaultDateFormat(date, DateUtil.DATE_FORMAT_yyyyMMddHHmm, Locale.getDefault()), DateUtil.DATE_FORMAT_yyyy_MM_dd_E_HH_mm);
        String targetTxt = " / " + getString(R.string.service_charge_btr_word_34);

        if (isCheckedOption)
            resultDtm += targetTxt;

        return resultDtm;
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
                    case R.id.l_inout_cd:
                        return checkValidPhoneNumber()&&checkValidCarRegNo()&&checkValidKeyDeliveryCd()&&checkValidAddr()&&checkValidAddrDtl()&&false;
                    case R.id.l_rsvt_hope_dt:
                        return checkValidPhoneNumber()&&checkValidCarRegNo()&&checkValidKeyDeliveryCd()&&checkValidAddr()&&checkValidAddrDtl()&&checkValidInOutCd()&&false;
                }
            }
        }
        return checkValidPhoneNumber()&&checkValidCarRegNo()&&checkValidKeyDeliveryCd()&&checkValidAddr()&&checkValidAddrDtl()&&checkValidInOutCd()&&checkValidRsvtHopeDt();
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
        } else if(resultCode == ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FINISH.getCode()) {
            exitPage(new Intent(), ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FINISH.getCode());
        } else if (resultCode == ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FAIL.getCode()) {
            // 픽업앤충전 서비스 예약 실패되어 화면 종료 시
            exitPage(new Intent(), ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FAIL.getCode());
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
