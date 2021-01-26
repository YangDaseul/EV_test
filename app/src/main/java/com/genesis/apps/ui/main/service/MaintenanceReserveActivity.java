package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.REQ_1001;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.REQ_1004;
import com.genesis.apps.comm.model.api.gra.REQ_1005;
import com.genesis.apps.comm.model.vo.CouponVO;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityMaintenanceReserveBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceReserveActivity extends SubActivity<ActivityMaintenanceReserveBinding> {
    private static final String TAG = MaintenanceReserveActivity.class.getSimpleName();

    private REQViewModel reqViewModel;
    private RepairTypeVO selectRepairTypeVO;
    private List<RepairTypeVO> list = new ArrayList<>();
    private List<CouponVO> couponList;
    private VehicleVO mainVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_reserve);
        setResizeScreen();
        getDataFromIntent();
        setViewModel();
        setObserver();
        initData();
    }

    private void initData() {
        try {
            mainVehicle = reqViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mainVehicle != null) {
                reqViewModel.reqREQ1001(new REQ_1001.Request(APPIAInfo.SM_R_RSV01.getId(), mainVehicle.getVin()));
            } else {
                exitPage("주 이용 차량 정보가 없습니다.\n차량을 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    private boolean checkValid() {

        boolean isValid = false;

        if (checkValidCarRegNo()) {
            String carRegNo = ui.etCarRegNo.getText().toString().trim();
            ui.etCarRegNo.clearFocus(); //차량번호의 포커스를 제거하고 -> 제거하면 다음버튼과 키패드가 사라짐
            ui.lMaintenanceCategory.setVisibility(View.VISIBLE);// 정비내용 뷰 활성화
            ui.tvMsg.setText(R.string.maintenance_category_msg);
            if (!StringUtil.isValidString(mainVehicle.getCarRgstNo()).equalsIgnoreCase(carRegNo)) {//기존에 입력된 차량번호와 다르면
                mainVehicle.setCarRgstNo(carRegNo); //차량번호를 다시 넣어주고
                initServiceItem(); //서비스 아이템 리스트 초기화
                initMaintenanceCategory(); // 정비내용 븊 초기화
                reqViewModel.reqREQ1004(new REQ_1004.Request(APPIAInfo.SM_R_RSV01.getId()));
            } else if (!checkValidCategory() && (list == null || list.size() < 1)) {
                reqViewModel.reqREQ1004(new REQ_1004.Request(APPIAInfo.SM_R_RSV01.getId()));
            } else if (!checkValidCategory() && list != null && list.size() > 0) {
                showDialogRepairType(list);
            } else {
                isValid = true;
            }
        }

        return isValid;
    }

    private boolean checkValidCategory() {
        return selectRepairTypeVO != null;
    }

    private boolean checkValidCarRegNo() {
        String carRegNo = ui.etCarRegNo.getText().toString().trim();
        if (TextUtils.isEmpty(carRegNo)) {
            ui.etCarRegNo.requestFocus();
            ui.lCarRegNo.setError(getString(R.string.sm_emgc01_18));
            return false;
        } else if (!StringRe2j.matches(carRegNo, getString(R.string.check_car_vrn))) {
            ui.etCarRegNo.requestFocus();
            ui.lCarRegNo.setError(getString(R.string.sm_emgc01_27));
            return false;
        } else {
            ui.lCarRegNo.setError(null);
            return true;
        }
    }

    private void initServiceItem() {
        //오토케어
        setViewVisibility(ui.lMaintenanceAutocare.lMaintenanceCategoryItemBtn, ui.lMaintenanceAutocare.btnBlock, true);
        //에어포트
        setViewVisibility(ui.lMaintenanceAirport.lMaintenanceCategoryItemBtn, ui.lMaintenanceAirport.btnBlock, true);
        //홈투홈서비스
        setViewVisibility(ui.lMaintenanceHometohome.lMaintenanceCategoryItemBtn, ui.lMaintenanceHometohome.btnBlock, true);
        //정비소 사용
        setViewVisibility(ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn, ui.lMaintenanceRepair.btnBlock, true);

        ui.lMaintenanceRepair.btnBlock.setVisibility(View.VISIBLE);
        ui.lMaintenanceHometohome.btnBlock.setVisibility(View.VISIBLE);
        ui.lMaintenanceAirport.btnBlock.setVisibility(View.VISIBLE);
        ui.lMaintenanceAutocare.btnBlock.setVisibility(View.VISIBLE);
        ui.tvService.setText(R.string.maintenance_service_title);
    }

    private void initMaintenanceCategory() {
        selectRepairTypeVO = null;
        Paris.style(ui.tvMaintenanceCategorySelectBtn).apply(R.style.CommonSpinnerItemDisable);
        ui.tvMaintenanceCategorySelectBtn.setText(R.string.maintenance_category_title);
        ui.tvMaintenanceCategoryTitle.setVisibility(View.GONE);
    }


    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            checkValid();
        }
        return false;
    };

    EditText.OnFocusChangeListener focusChangeListener = (view, hasFocus) -> {
        if (hasFocus) {
            ui.btnNext.setVisibility(View.VISIBLE);
            SoftKeyboardUtil.showKeyboard(getApplicationContext());
        } else {
            ui.btnNext.setVisibility(View.GONE);
            SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
        }
    };

    @Override
    public void onClickCommon(View v) {

        int viewId = v.getId();

        if (!isSelectCategory(viewId))
            return;

        switch (viewId) {
            case R.id.btn_next:
                checkValid();
                break;
            case R.id.tv_maintenance_category_select_btn:
                showDialogRepairType(list);
                break;
            case R.id.l_maintenance_autocare:
                startActivitySingleTop(new Intent(this, ServiceAutocare2ApplyActivity.class)
                                .putExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE, selectRepairTypeVO.getRparTypCd())
                                .putExtra(KeyNames.KEY_NAME_SERVICE_COUPON_LIST, new Gson().toJson(couponList))
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.l_maintenance_airport:
                startActivitySingleTop(new Intent(this, ServiceAirport2ApplyActivity.class)
                                .putExtra(KeyNames.KEY_NAME_VEHICLE_VO, mainVehicle)
                                .putExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE, selectRepairTypeVO)
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.l_maintenance_hometohome:
                startActivitySingleTop(new Intent(this, ServiceHomeToHome2ApplyActivity.class)
                                .putExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE, selectRepairTypeVO.getRparTypCd())
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            case R.id.l_maintenance_repair:

                startActivitySingleTop(new Intent(this, ServiceRepair2ApplyActivity.class)
                                .putExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE, selectRepairTypeVO)
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);

//                MiddleDialog.dialogServiceCantReserveInfo(this, () ->
//                    startActivitySingleTop(new Intent(this, ServiceRepair2ApplyActivity.class)
//                                    .putExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE, selectRepairTypeVO)
//                            , RequestCodes.REQ_CODE_ACTIVITY.getCode()
//                            , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE));

                break;
        }
    }

    /**
     * @param viewId
     * @return
     * @brief 서비스 아이템 클릭 시 정비 내용이 선택되어있는지 확인
     * 선택되어있지 않으면 다이얼로그(showDialogRepairType)활성화
     */
    private boolean isSelectCategory(int viewId) {
        boolean isSelect = false;

        switch (viewId) {
            case R.id.l_maintenance_autocare:
            case R.id.l_maintenance_airport:
            case R.id.l_maintenance_hometohome:
            case R.id.l_maintenance_repair:
                isSelect = checkValid();
                break;
            default:
                isSelect = true;
                break;
        }

        return isSelect;
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {
        reqViewModel.getRES_REQ_1001().observe(this, result -> {

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null) {
                        try {
                            String avlRsrVn = result.data.getAvlRsrYn();
                            if (StringUtil.isValidString(avlRsrVn).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
                                ui.etCarRegNo.setOnEditorActionListener(editorActionListener);
                                ui.etCarRegNo.setOnFocusChangeListener(focusChangeListener);

                                if (!TextUtils.isEmpty(mainVehicle.getCarRgstNo())) {
                                    ui.etCarRegNo.setText(mainVehicle.getCarRgstNo());
                                    checkValid();
                                } else {
                                    ui.etCarRegNo.requestFocus();
                                }
                            } else {
                                MiddleDialog.dialogServiceInfo(this, () -> exitPage("", ResultCodes.REQ_CODE_NORMAL.getCode()));
                            }
                            break;
                        } catch (Exception ignore) {

                        }
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        showProgressDialog(false);
                        exitPage(TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg, ResultCodes.RES_CODE_NETWORK.getCode());
                    }
                    break;
            }
        });


        reqViewModel.getRES_REQ_1004().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getRparTypList() != null) {
                        list.addAll(result.data.getRparTypList());
                        showDialogRepairType(list);
                        showProgressDialog(false);
                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });

        reqViewModel.getRES_REQ_1005().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null) {
                        setViewServiceItem(result.data);
                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });

    }

    /**
     * @param data
     * @brief REQ-1005 (정비 내용 선택) 후 결과 처리
     * 정비 내용에 따른 서비스 내용을 활성화 혹은 비활성화 진행
     */
    private void setViewServiceItem(REQ_1005.Response data) {
        //카케어쿠폰리스트
        couponList = data.getCarCareCpnList();
        //오토케어 예약가능여부
        String autoRsvtPsblYn = data.getAutoRsvtPsblYn();
        //에어포트 예약가능여부
        String arptRsvtPsblYn = data.getArptRsvtPsblYn();
        //홈투홈예약가능여부
        String hthRsvtPsblYn = data.getHthRsvtPsblYn();
        //정비소예약가능여부
        String rpshRsvtPsblYn = data.getRpshRsvtPsblYn();
        //예약신청가능여부
        String avlRsrYn = data.getAvlRsrYn();

        if (!TextUtils.isEmpty(avlRsrYn) && avlRsrYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
            //예약신청가능여부가 y인경우
            //오토케어 사용 가능 상태 + 소모성부품선택 + 픽앤딜리버리 횟수 1회 이상 + 엔진 횟수 1회 이상
            setViewVisibility(ui.lMaintenanceAutocare.lMaintenanceCategoryItemBtn, ui.lMaintenanceAutocare.btnBlock, isPossibleReservation(autoRsvtPsblYn) && selectRepairTypeVO.getRparTypCd().equalsIgnoreCase(VariableType.SERVICE_REPAIR_CODE_CS) && reqViewModel.checkCoupon(couponList, VariableType.SERVICE_CAR_CARE_COUPON_CODE_PICKUP_DELIVERY) && reqViewModel.checkCoupon(couponList, VariableType.SERVICE_CAR_CARE_COUPON_CODE_ENGINE));
            //에어포트 사용 가능 상태 + 소모성부품선택 + 픽앤딜리버리 횟수 1회 이상
            setViewVisibility(ui.lMaintenanceAirport.lMaintenanceCategoryItemBtn, ui.lMaintenanceAirport.btnBlock, isPossibleReservation(arptRsvtPsblYn) && selectRepairTypeVO.getRparTypCd().equalsIgnoreCase(VariableType.SERVICE_REPAIR_CODE_CS) && reqViewModel.checkCoupon(couponList, VariableType.SERVICE_CAR_CARE_COUPON_CODE_PICKUP_DELIVERY));
            //홈투홈서비스 사용 가능 상태 + 픽앤딜리버리 횟수 1회 이상
            setViewVisibility(ui.lMaintenanceHometohome.lMaintenanceCategoryItemBtn, ui.lMaintenanceHometohome.btnBlock, isPossibleReservation(hthRsvtPsblYn) && reqViewModel.checkCoupon(couponList, VariableType.SERVICE_CAR_CARE_COUPON_CODE_PICKUP_DELIVERY));
            //정비소 사용 가능 상태
            setViewVisibility(ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn, ui.lMaintenanceRepair.btnBlock, isPossibleReservation(rpshRsvtPsblYn));
        } else {
            //예약신청가능여부가 갑자기 N으로 변경된 경우
            MiddleDialog.dialogServiceInfo(this, () -> {
                exitPage("", ResultCodes.REQ_CODE_NORMAL.getCode());
            });
        }
    }


    /**
     * @param visibility visibility true면 아이템 활성화
     * @brief 서비스 아이템 활성화/비활성화
     */
    private void setViewVisibility(View parent, View block, boolean visibility) {

        if (visibility && parent.getVisibility() != View.VISIBLE) {
            InteractionUtil.expand(parent, null);
        } else if (!visibility && parent.getVisibility() != View.GONE) {
            InteractionUtil.collapse(parent, null);
        }

        if (visibility)
            block.setVisibility(View.GONE);
//        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private boolean isPossibleReservation(String value) {
        return !TextUtils.isEmpty(value) && value.equalsIgnoreCase(VariableType.COMMON_MEANS_YES);
    }


    @Override
    public void getDataFromIntent() {
        try {
//            avlRsrYn = getIntent().getStringExtra(KeyNames.KEY_NAME_MAINTENANCE_AVL_RSR_YN);
            selectRepairTypeVO = (RepairTypeVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_MAINTENANCE_REPAIR_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (TextUtils.isEmpty(avlRsrYn)){
//                exitPage("정비 예약 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
//            }else if(avlRsrYn.equalsIgnoreCase("N")){
//                ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn.setBackgroundResource(R.drawable.bg_77000000_round_10);
//                ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn.setOnClickListener(null);
//            }else{
//                ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn.setBackgroundResource(R.drawable.ripple_bg_ffffff_round_10);
//                ui.lMaintenanceRepair.lMaintenanceCategoryItemBtn.setOnClickListener(onSingleClickListener);
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void showDialogRepairType(final List<RepairTypeVO> list) {

        if (list == null || list.size() < 1)
            return;

        try {
            final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
            bottomListDialog.setOnDismissListener(dialogInterface -> {
                String result = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(result)) {
                    try {
                        selectRepairTypeVO = reqViewModel.getRepairTypeCd(result, list);
                        setViewCategorySelect();
                        reqViewModel.reqREQ1005(new REQ_1005.Request(APPIAInfo.SM_R_RSV01.getId(), mainVehicle.getVin(), mainVehicle.getCarRgstNo(), selectRepairTypeVO.getRparTypCd(), mainVehicle.getMdlNm()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            bottomListDialog.setDatas(reqViewModel.getRepairTypeNm(list));
            bottomListDialog.setTitle(getString(R.string.sm_snfind01_p01_1));
            bottomListDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setViewCategorySelect() {
        if (selectRepairTypeVO != null) {
            ui.tvMaintenanceCategoryTitle.setVisibility(View.VISIBLE);
            Paris.style(ui.tvMaintenanceCategorySelectBtn).apply(R.style.CommonSpinnerItemEnable);
            ui.tvMaintenanceCategorySelectBtn.setText(selectRepairTypeVO.getRparTypNm());
            ui.tvService.setText(R.string.maintenance_service_title_02);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //오토케어, 홈투홈, 원격진단, 정비 예약 완료 시 메인 서비스 프래그먼트에 CALLBACK 전달
        if (resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_AUTOCARE.getCode()
                || resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_HOMETOHOME.getCode()
                || resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_REPAIR.getCode()
                || resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_REMOTE.getCode()) {
            exitPage(data, resultCode);
        }
    }

}
