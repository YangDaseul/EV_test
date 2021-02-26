package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
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
import com.genesis.apps.comm.model.api.gra.REQ_1008;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityServiceHometohome2Apply1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendar;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * @author hjpark
 * @brief 차계부 입력
 */
@AndroidEntryPoint
public class ServiceHomeToHome2ApplyActivity extends SubActivity<ActivityServiceHometohome2Apply1Binding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private REQViewModel reqViewModel;
    private VehicleVO mainVehicle;
    private final int[] layouts = {R.layout.activity_service_hometohome_2_apply_1, R.layout.activity_service_hometohome_2_apply_2, R.layout.activity_service_hometohome_2_apply_3, R.layout.activity_service_hometohome_2_apply_4, R.layout.activity_service_hometohome_2_apply_5, R.layout.activity_service_hometohome_2_apply_6};
    private final int[] textMsgId = {R.string.sm_r_rsv02_03_4, R.string.sm_r_rsv02_03_8, R.string.sm_r_rsv02_03_11, R.string.sm_r_rsv02_03_15, R.string.sm_r_rsv02_03_19, R.string.sm_r_rsv02_03_22};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;

    private String rparTypCd; //정비내용코드
    private String rsvtHopeDt; //예약희망일자
    private String pckpDivCd; //픽업구분코드

    private AddressVO pckpAddressVO;
    private AddressVO dlvryAddressVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(layouts[0]);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
    }

    private void initView() {
        initConstraintSets();
        initEditView();
        initViewCheckBox();
        selectHomeToHomeService();
    }

    private void initEditView() {
        ui.etDlvryAddrDtl.setOnEditorActionListener(editorActionListener);
        ui.etDlvryAddrDtl.setOnFocusChangeListener(focusChangeListener);
        ui.etPckpAddrDtl.setOnEditorActionListener(editorActionListener);
        ui.etPckpAddrDtl.setOnFocusChangeListener(focusChangeListener);
    }

    private void startMapView(boolean isPckp) {
        clearKeypad();
        startActivitySingleTop(new Intent(this
                        , MapSearchMyPositionActivity.class)
                        .putExtra(KeyNames.KEY_NAME_ADDR, isPckp ? pckpAddressVO : dlvryAddressVO)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, isPckp ? R.string.sm_r_rsv02_03_a01_1 : R.string.sm_r_rsv02_03_a03_1)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, isPckp ? R.string.sm_r_rsv02_03_a01_2 : R.string.sm_r_rsv02_03_a03_2)
                , isPckp ? RequestCodes.REQ_CODE_SERVICE_HOMETOHOME_PCKP.getCode() : RequestCodes.REQ_CODE_SERVICE_HOMETOHOME_DELIVERY.getCode()
                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }



    private void initViewCheckBox(){
        ui.cbPckp.setOnCheckedChangeListener((compoundButton, b) -> {

            if (compoundButton.isPressed() && b) {
                if (pckpAddressVO != null) {
                    try {
                        dlvryAddressVO = ((AddressVO) pckpAddressVO.clone());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (dlvryAddressVO != null) {
                            checkValidDlvryAddr();
                            String addrDtl = ui.etPckpAddrDtl.getText().toString();
                            if(!TextUtils.isEmpty(addrDtl)) {
                                ui.etDlvryAddrDtl.setText(addrDtl);
                                ui.etDlvryAddrDtl.setSelection(addrDtl.length());
                            }
                        }
                    }
                }
            }

        });
    }



////    //스피너를 선택하거나 doTranstion이 발생될 떄 ..
//    private void setViewPckpDivCd(){
//        switch (pckpDivCd){
//            case VariableType.SERVICE_HOMETOHOME_PCKP_DIV_CD_PICKUP:
//                ui.lDlvryAddr.setVisibility(View.GONE);
//                ui.lDlvryAddrDtl.setVisibility(View.GONE);
//                ui.cbPckp.setVisibility(View.VISIBLE);
//                break;
//            case VariableType.SERVICE_HOMETOHOME_PCKP_DIV_CD_DELIVERY:
//                ui.lPckpAddr.setVisibility(View.GONE);
//                ui.lPckpAddrDtl.setVisibility(View.GONE);
//                ui.cbPckp.setVisibility(View.GONE);
//                break;
//            default:
//                ui.cbPckp.setVisibility(View.VISIBLE);
//                break;
//        }
//    }



    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //에약서비스선택
            case R.id.tv_hometohome_service:
                selectHomeToHomeService();
                break;
            //주소검색 픽업
            case R.id.tv_pckp_addr:
                startMapView(true);
                break;
            //주소검색 딜리버리
            case R.id.tv_dlvry_addr:
                startMapView(false);
                break;
            //예약희망일
            case R.id.tv_rsvt_hope_dt:
                reqCalendarInfo();
//                selectCalendar();
                break;
            //다음
            case R.id.btn_next:
                doNext();
                break;
        }
    }

    private void reqCalendarInfo(){
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 2);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.MONTH, 2);
        reqViewModel.reqREQ1008(new REQ_1008.Request(APPIAInfo.SM_R_RSV02_03.getId()
                ,DateUtil.getDate(minCalendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd)
                ,DateUtil.getDate(maxCalendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd)));
    }


    private void selectCalendar(List<RepairReserveDateVO> list) {
        if(list==null||list.size()==0)
            return;

        clearKeypad();
        DialogCalendar dialogCalendar = new DialogCalendar(this, R.style.BottomSheetDialogTheme);
        dialogCalendar.setOnDismissListener(dialogInterface -> {
            Calendar calendar = dialogCalendar.calendar;
            if (calendar != null) {
                rsvtHopeDt = DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
                checkValidRsvtHopeDt();
            }
        });
        dialogCalendar.setCalendarMaximum(Calendar.getInstance(Locale.getDefault()));
        dialogCalendar.setTitle(getString(R.string.sm_r_rsv02_01_p02_1));
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 2);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.MONTH, 2);
        dialogCalendar.setCalendarMinimum(minCalendar);
        dialogCalendar.setCalendarMaximum(maxCalendar);
        dialogCalendar.setReserveDateVOList(list);
        dialogCalendar.setRemoveWeekends(true);
        dialogCalendar.show();
    }


    private void clearKeypad() {
        for (View view : edits) {
            view.clearFocus();
        }
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
    }

    private void doNext() {
        if (isValid()) {
            clearKeypad();
            moveToNextPage();
        }
    }

    /**
     * @brief 3단계로 이동
     */
    private void moveToNextPage() {

        RepairReserveVO repairReserveVO = new RepairReserveVO(
                VariableType.SERVICE_RESERVATION_TYPE_HOMETOHOME,
                rparTypCd,
                mainVehicle.getVin(),
                mainVehicle.getCarRgstNo(),
                mainVehicle.getMdlCd(),
                mainVehicle.getMdlNm(),
                rsvtHopeDt,
                loginInfoDTO.getProfile() != null ? loginInfoDTO.getProfile().getMobileNum() : "",
                pckpDivCd,
                getAddress(pckpAddressVO)[0] + (TextUtils.isEmpty(ui.etPckpAddrDtl.getText().toString().trim()) ? "" : ("\n"+ui.etPckpAddrDtl.getText().toString().trim())),
                getAddress(dlvryAddressVO)[0] + (TextUtils.isEmpty(ui.etDlvryAddrDtl.getText().toString().trim()) ? "" : ("\n"+ui.etDlvryAddrDtl.getText().toString().trim())),
                "",
                loginInfoDTO.getProfile() != null ? loginInfoDTO.getProfile().getName() : "");


        startActivitySingleTop(new Intent(this
                        , ServiceHomeToHome3CheckActivity.class)
                        .putExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO, repairReserveVO)
                , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {
        reqViewModel.getRES_REQ_1008().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRsvtDtList() != null && result.data.getRsvtDtList().size() > 0) {
                        selectCalendar(result.data.getRsvtDtList());
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
            rparTypCd = getIntent().getStringExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE);
            mainVehicle = reqViewModel.getMainVehicle();
            String carRegNo = getIntent().getStringExtra(KeyNames.KEY_NAME_CAR_REG_NO);
            if(!TextUtils.isEmpty(carRegNo)){
                if(mainVehicle!=null) mainVehicle.setCarRgstNo(carRegNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(rparTypCd)) {
                exitPage("홈투홈 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }
    /**
     * @author
     * @brief constraintSet 초기화
     */
    private void initConstraintSets() {
        views = new View[]{ui.lHometohomeService, ui.lPckpAddr, ui.lPckpAddrDtl, ui.lDlvryAddr, ui.lDlvryAddrDtl, ui.lRsvtHopeDt};
        edits = new View[]{ui.tvHometohomeService, ui.tvPckpAddr, ui.etPckpAddrDtl, ui.tvDlvryAddr, ui.etDlvryAddrDtl, ui.tvRsvtHopeDt};
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

            if (edits[pos - 1] instanceof TextInputEditText) {
                edits[pos].clearFocus();
            }

            if (edits[pos] instanceof TextInputEditText) {
                edits[pos].requestFocus();
            }

            if (pos == 1) {
                //2020-12-01 화면 전체를 덮는 입력 페이지는 자동 진입 안하도록 수정
//                startMapView(true);
            } else if (pos == 3) {
                //2020-12-01 화면 전체를 덮는 입력 페이지는 자동 진입 안하도록 수정
//                startMapView(false);
            } else if (pos == views.length - 1) {
//                selectCalendar();
                reqCalendarInfo();
            }
        }else{
            switch (pckpDivCd){
                case VariableType.SERVICE_HOMETOHOME_PCKP_DIV_CD_PICKUP_DELIVERY:
                    if(views[5].getVisibility() == View.VISIBLE){//이미 희망일 지정하는 부분이 visible 된 상태 일때 픽업+딜리버리로 변경 시.
                        ui.lDlvryAddr.setVisibility(View.VISIBLE);
                        ui.lDlvryAddrDtl.setVisibility(View.VISIBLE);
                    }
                    break;
                case VariableType.SERVICE_HOMETOHOME_PCKP_DIV_CD_PICKUP:
                    if(views[5].getVisibility() == View.VISIBLE
                            ||views[4].getVisibility() == View.VISIBLE
                            ||views[3].getVisibility() == View.VISIBLE){//이미 희망일 지정하는 부분이 visible 된 상태 일때 픽업+딜리버리로 변경 시.
                        clearDlvry();
                    }
                    break;
            }
        }
    }


    private void clearDlvry(){
        dlvryAddressVO = null;
        ui.lDlvryAddr.setVisibility(View.GONE);
        ui.tvDlvryAddr.setText("");
        ui.tvErrorDlvryAddr.setVisibility(View.GONE);
        ui.lDlvryAddrDtl.setVisibility(View.GONE);
        ui.etDlvryAddrDtl.setText("");
        ui.lDlvryAddrDtl.setError(null);
        ui.cbPckp.setChecked(false);
    }


    /**
     * @brief 홈투홈 예약 서비스 항목 선택 다이얼로그 활성화
     */
    private void selectHomeToHomeService() {
        final List<String> pckpDivCdList = Arrays.asList(getResources().getStringArray(R.array.service_hometohome));
        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dialogInterface -> {
            String result = bottomListDialog.getSelectItem();
            if (!TextUtils.isEmpty(result)) {
                pckpDivCd = VariableType.getPckpDivCd(result);
                ui.tvHometohomeService.setText(result);
                checkValidHometohomeService();
            }
        });
        bottomListDialog.setDatas(pckpDivCdList);
        bottomListDialog.setTitle(getString(R.string.sm_r_rsv02_03_7));
        bottomListDialog.show();
    }

    private boolean checkValidHometohomeService() {

        if (TextUtils.isEmpty(pckpDivCd)) {
            ui.tvTitleHometohomeService.setVisibility(View.GONE);
            ui.tvErrorHometohomeService.setVisibility(View.VISIBLE);
            ui.tvErrorHometohomeService.setText(R.string.sm_r_rsv02_03_6);
            Paris.style(ui.tvHometohomeService).apply(R.style.CommonSpinnerItemDisable);
            return false;
        } else {
            ui.tvTitleHometohomeService.setVisibility(View.VISIBLE);
            ui.tvErrorHometohomeService.setVisibility(View.INVISIBLE);
            Paris.style(ui.tvHometohomeService).apply(R.style.CommonSpinnerItemEnable);
            doTransition(1);//ok
            return true;
        }
    }

    private boolean checkValidRsvtHopeDt() {
        if (TextUtils.isEmpty(rsvtHopeDt)) {
            ui.tvRsvtHopeDt.setText(R.string.sm_r_rsv02_01_16);
            Paris.style(ui.tvRsvtHopeDt).apply(R.style.CommonSpinnerItemCalendarDisable);
            ui.tvTitleRsvtHopeDt.setVisibility(View.GONE);
            ui.tvErrorRsvtHopeDt.setVisibility(View.VISIBLE);
            ui.tvErrorRsvtHopeDt.setText(R.string.sm_r_rsv02_01_14);
            return false;
        } else {
            String date = DateUtil.getDate(DateUtil.getDefaultDateFormat(rsvtHopeDt, DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot);
            ui.tvRsvtHopeDt.setText(date);
            Paris.style(ui.tvRsvtHopeDt).apply(R.style.CommonSpinnerItemCalendar);
            ui.tvTitleRsvtHopeDt.setVisibility(View.VISIBLE);
            ui.tvErrorRsvtHopeDt.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean checkValidPckpAddr() {

        if (pckpAddressVO == null) {
            ui.tvErrorPckpAddr.setVisibility(View.VISIBLE);
            ui.tvErrorPckpAddr.setText(getString(R.string.sm_r_rsv02_01_14));
            Paris.style(ui.tvPckpAddr).apply(R.style.CommonInputItemDisable);
            ui.tvPckpAddr.setText(R.string.sm_r_rsv02_03_9);
            ui.tvTitlePckpAddr.setVisibility(View.INVISIBLE);
            return false;
        } else {
            ui.tvErrorPckpAddr.setVisibility(View.INVISIBLE);
            Paris.style(ui.tvPckpAddr).apply(R.style.CommonInputItemEnable);
            ui.tvPckpAddr.setText(getAddress(pckpAddressVO)[0]);
            ui.tvTitlePckpAddr.setVisibility(View.VISIBLE);
            doTransition(2);
            return true;
        }
    }

    //상세 주소 자동 입력 취소 요청
    private void setViewPckpAddrDetail() {
//        String addrDetail = (TextUtils.isEmpty(pckpAddressVO.getTitle()) ? "" : pckpAddressVO.getTitle()) + (TextUtils.isEmpty(pckpAddressVO.getCname()) ? "" : " " + pckpAddressVO.getCname());
//        if (!TextUtils.isEmpty(addrDetail.trim())) {
//            ui.etPckpAddrDtl.setText(addrDetail.trim());
//            ui.etPckpAddrDtl.setSelection(ui.etPckpAddrDtl.length());
//        }
    }


    private boolean checkValidPckpAddrDtl() {
        String addrDtl = ui.etPckpAddrDtl.getText().toString().trim();

        if (TextUtils.isEmpty(addrDtl)) {
            ui.etPckpAddrDtl.requestFocus();
            ui.lPckpAddrDtl.setError(getString(R.string.sm_r_rsv02_01_14));
            return false;
        } else {
            ui.lPckpAddrDtl.setError(null);
            switch (pckpDivCd){
                case VariableType.SERVICE_HOMETOHOME_PCKP_DIV_CD_PICKUP://픽업상태인경우 바로 일정입
                    doTransition(5);//ok
                    ui.lDlvryAddrDtl.setVisibility(View.GONE);
                    ui.lDlvryAddr.setVisibility(View.GONE);
                    break;
                case VariableType.SERVICE_HOMETOHOME_PCKP_DIV_CD_PICKUP_DELIVERY:
                default:
                    doTransition(3);//ok
                    break;
            }
            return true;
        }
    }


    private boolean checkValidDlvryAddr() {

        if (dlvryAddressVO == null) {
            ui.tvErrorDlvryAddr.setVisibility(View.VISIBLE);
            ui.tvErrorDlvryAddr.setText(getString(R.string.sm_r_rsv02_01_14));
            Paris.style(ui.tvDlvryAddr).apply(R.style.CommonInputItemDisable);
            ui.tvDlvryAddr.setText(R.string.sm_r_rsv02_03_16);
            ui.tvTitleDlvryAddr.setVisibility(View.INVISIBLE);
            return isOnlyPickUp()||false;
        } else {
            ui.tvErrorDlvryAddr.setVisibility(View.GONE);
            Paris.style(ui.tvDlvryAddr).apply(R.style.CommonInputItemEnable);
            ui.tvDlvryAddr.setText(getAddress(dlvryAddressVO)[0]);
            ui.tvTitleDlvryAddr.setVisibility(View.VISIBLE);
            doTransition(4);
            return true;
        }
    }

    //딜리버리 주소 자동 입력 제거 요청
    private void setViewDlvryAddrDetail() {
//        String addrDetail = (TextUtils.isEmpty(dlvryAddressVO.getTitle()) ? "" : dlvryAddressVO.getTitle()) + (TextUtils.isEmpty(dlvryAddressVO.getCname()) ? "" : " " + dlvryAddressVO.getCname());
//        if (!TextUtils.isEmpty(addrDetail.trim())) {
//            ui.etDlvryAddrDtl.setText(addrDetail.trim());
//            ui.etDlvryAddrDtl.setSelection(ui.etDlvryAddrDtl.length());
//        }
    }

    private boolean checkValidDlvryAddrDtl() {
        String addrDtl = ui.etDlvryAddrDtl.getText().toString().trim();

        if (TextUtils.isEmpty(addrDtl)) {
            ui.etDlvryAddrDtl.requestFocus();
            ui.lDlvryAddrDtl.setError(getString(R.string.sm_r_rsv02_01_14));
            return isOnlyPickUp()||false;
        } else {
            ui.lDlvryAddrDtl.setError(null);
            doTransition(5);//TODO 딜리버리일 때 예약희망일을 날릴지는 확인 중
            return true;
        }
    }


    private boolean isValid() {
        for (View view : views) {
            if (view.getVisibility() == View.GONE) {
                switch (view.getId()) {
                    case R.id.l_hometohome_service:
                        return false;
                    case R.id.l_pckp_addr:
                        return checkValidHometohomeService() && false;
                    case R.id.l_pckp_addr_dtl:
                        return checkValidHometohomeService() && checkValidPckpAddr() && false;
                    case R.id.l_dlvry_addr:
                        if(isOnlyPickUp())
                            break;
                        return checkValidHometohomeService() && checkValidPckpAddr() && checkValidPckpAddrDtl() && false;
                    case R.id.l_dlvry_addr_dtl:
                        if(isOnlyPickUp())
                            break;
                        return checkValidHometohomeService() && checkValidPckpAddr() && checkValidPckpAddrDtl() && checkValidDlvryAddr() && false;
                    case R.id.l_rsvt_hope_dt:
                        return checkValidHometohomeService() && checkValidPckpAddr() && checkValidPckpAddrDtl() && checkValidDlvryAddr() && checkValidDlvryAddrDtl() && false;
                }
            }
        }
        return checkValidHometohomeService() && checkValidPckpAddr() && checkValidPckpAddrDtl() && checkValidDlvryAddr() && checkValidDlvryAddrDtl() && checkValidRsvtHopeDt();
    }


    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            doNext();
        }
        return false;
    };

    EditText.OnFocusChangeListener focusChangeListener = (view, hasFocus) -> {
        if (hasFocus) {
            if(view.getId()==R.id.et_dlvry_addr_dtl&&isOnlyPickUp()){

            }else{
                SoftKeyboardUtil.showKeyboard(getApplicationContext());
            }
        }
    };

    @Override
    public void onBackPressed() {
        dialogExit();
    }

    @Override
    public void onBackButton() {
        dialogExit();
    }

    private void dialogExit() {
        MiddleDialog.dialogServiceBack(this, () -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        AddressVO addressVO = null;
        if (requestCode == RequestCodes.REQ_CODE_SERVICE_HOMETOHOME_PCKP.getCode()&&data!=null) {
            try {
                addressVO = (AddressVO) data.getSerializableExtra(KeyNames.KEY_NAME_ADDR);
            } catch (Exception e) {

            } finally {
                if (addressVO != null) {
                    //픽업 주소 입력 완료
                    pckpAddressVO = addressVO;
                    setViewPckpAddrDetail();
                    checkValidPckpAddr();
                }
            }
        } else if (requestCode == RequestCodes.REQ_CODE_SERVICE_HOMETOHOME_DELIVERY.getCode()&&data!=null) {
            try {
                addressVO = (AddressVO) data.getSerializableExtra(KeyNames.KEY_NAME_ADDR);
            } catch (Exception e) {

            } finally {
                if (addressVO != null) {
                    dlvryAddressVO = addressVO;
                    setViewDlvryAddrDetail();
                    checkValidDlvryAddr();
                }
            }
        } else if (resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_HOMETOHOME.getCode()) {
            exitPage(data, ResultCodes.REQ_CODE_SERVICE_RESERVE_HOMETOHOME.getCode());
        }
    }

    private boolean isOnlyPickUp(){
        return pckpDivCd.equalsIgnoreCase(VariableType.SERVICE_HOMETOHOME_PCKP_DIV_CD_PICKUP);
    }

}
