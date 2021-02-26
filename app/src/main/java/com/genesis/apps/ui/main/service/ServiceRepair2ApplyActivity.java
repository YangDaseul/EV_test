package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.REQ_1010;
import com.genesis.apps.comm.model.api.gra.REQ_1011;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.RepairGroupVO;
import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityServiceRepair2Apply1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendarRepair;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.ServiceNetworkActivity;

import java.util.Calendar;
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
 * @brief 정비소 예약 2단계
 */
@AndroidEntryPoint
public class ServiceRepair2ApplyActivity extends SubActivity<ActivityServiceRepair2Apply1Binding> {
    @Inject
    public LoginInfoDTO loginInfoDTO;

    private REQViewModel reqViewModel;
    private VehicleVO mainVehicle;
    private final int[] layouts = {R.layout.activity_service_repair_2_apply_1, R.layout.activity_service_repair_2_apply_2};
    private final int[] textMsgId = {R.string.sm_r_rsv02_04_2, R.string.sm_r_rsv02_04_9};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;

    private RepairTypeVO repairTypeVO; //정비내용코드
    private String rsvtHopeDt; //예약희망일자
    private String rsvtHopeTm = ""; //예약희망시간
    private BtrVO btrVO;
    private RepairGroupVO repairGroupVO;


    private DialogCalendarRepair dialogCalendarRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layouts[0]);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
    }

    private void initView() {
        initConstraintSets();
        setViewRparTypCd();
        MiddleDialog.dialogServiceCantReserveInfo(this, () -> {
            initChoiceView();
        });
    }

    private void initChoiceView() {
        if (btrVO == null) {
            //2020-12-01 화면 전체를 덮는 입력 페이지는 자동 진입 안하도록 수정
//            startMapView();
        } else {
            checkValidRepair();
        }
    }

    private void setViewRparTypCd() {
        if (repairTypeVO != null)
            ui.lRpar.tvRparTypNm.setText(repairTypeVO.getRparTypNm());
    }

    private void startMapView() {
        startActivitySingleTop(new Intent(this, ServiceNetworkActivity.class)
                .putExtra(KeyNames.KEY_NAME_BTR, btrVO)
                .putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_REPAIR)
                .putExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE, repairTypeVO.getRparTypCd()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.tv_repair:
                startMapView();
                break;
            //예약희망일
            case R.id.tv_rsvt_hope_dt:
                requestPossibleTime();
                break;
            case R.id.btn_next://다음
                doNext();
                break;

            case R.id.tv_repair_group:
                requestRepairGroup();
                break;
        }
    }

    private void requestRepairGroup() {
        reqViewModel.reqREQ1011(new REQ_1011.Request(APPIAInfo.SM_R_RSV02_04.getId(), mainVehicle.getVin(), btrVO.getAsnCd(), repairTypeVO.getRparTypCd(), btrVO.getAcps1Cd(), btrVO.getFirmScnCd(), dialogCalendarRepair.getSelectReserveDate().getRsvtDt(), dialogCalendarRepair.getSelectReserveDate().getRsvtTm()));
    }


    /**
     * @brief 예약 가능 시간 요청
     */
    private void requestPossibleTime() {
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 2);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.DATE, 14);

        reqViewModel.reqREQ1010(new REQ_1010.Request(APPIAInfo.SM_R_RSV02_04.getId(),
                mainVehicle.getVin(),
                btrVO.getAsnCd(),
                repairTypeVO.getRparTypCd(),
                btrVO.getAcps1Cd(),
                btrVO.getFirmScnCd(),
                DateUtil.getDate(minCalendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd),
                DateUtil.getDate(maxCalendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd)
        ));
    }


    private void selectCalendar(List<RepairReserveDateVO> list) {
        dialogCalendarRepair = new DialogCalendarRepair(this, R.style.BottomSheetDialogTheme, onSingleClickListener);
        dialogCalendarRepair.setOnDismissListener(dialogInterface -> {
            Calendar calendar = dialogCalendarRepair.calendar;
            if (calendar != null) {
                rsvtHopeDt = DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
                rsvtHopeTm = dialogCalendarRepair.getSelectReserveDate().getRsvtTm();
                repairGroupVO = dialogCalendarRepair.getRepairGroupVO();
                checkValidRsvtHopeDt();
            }
        });
        dialogCalendarRepair.setTitle(getString(R.string.sm_r_rsv02_04_10));
        Calendar minCalendar = Calendar.getInstance(Locale.getDefault());
        minCalendar.add(Calendar.DATE, 0);
        Calendar maxCalendar = Calendar.getInstance(Locale.getDefault());
        maxCalendar.add(Calendar.DATE, 14);

        dialogCalendarRepair.setCalendarMinimum(minCalendar);
        dialogCalendarRepair.setCalendarMaximum(maxCalendar);
        dialogCalendarRepair.setReserveDateVOList(list);
        dialogCalendarRepair.show();
    }


    private void doNext() {
        if (isValid()) {
            moveToNextPage();
        }
    }

    /**
     * @brief 3단계로 이동
     */
    private void moveToNextPage() {
        RepairReserveVO repairReserveVO = new RepairReserveVO(
                VariableType.SERVICE_RESERVATION_TYPE_RPSH,
                repairTypeVO.getRparTypCd(),
                mainVehicle.getVin(),
                mainVehicle.getCarRgstNo(),
                mainVehicle.getMdlCd(),
                mainVehicle.getMdlNm(),
                rsvtHopeDt,
                rsvtHopeTm,
                loginInfoDTO.getProfile() != null ? loginInfoDTO.getProfile().getMobileNum() : "",
                btrVO.getAcps1Cd(),
                btrVO.getAsnCd(), //엔진은 항상 선택
                btrVO.getAsnNm(),
                btrVO.getRepTn(),
                btrVO.getPbzAdr(),
                repairGroupVO.getRpshGrpCd(),
                repairGroupVO.getRpshGrpNm(),
                loginInfoDTO.getProfile() != null ? loginInfoDTO.getProfile().getName() : "",
                null);

        startActivitySingleTop(new Intent(this
                , ServiceRepair2PreCheckActivity.class).putExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO, repairReserveVO)
                , RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);

//        startActivitySingleTop(new Intent(this
//                        , ServiceRepair3CheckActivity.class)
//                        .putExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO, repairReserveVO)
//                , RequestCodes.REQ_CODE_ACTIVITY.getCode()
//                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {

        reqViewModel.getRES_REQ_1010().observe(this, result -> {
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


        reqViewModel.getRES_REQ_1011().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    if(dialogCalendarRepair!=null) dialogCalendarRepair.showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(dialogCalendarRepair!=null) dialogCalendarRepair.showProgressDialog(false);

                    if (result.data != null && result.data.getRpshGrpList() != null && result.data.getRpshGrpList().size() > 0) {
                        selectRepairGroup(result.data.getRpshGrpList());
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
                        if (dialogCalendarRepair != null) dialogCalendarRepair.showProgressDialog(false);
                    }
                    break;
            }
        });
//        sosViewModel.getRES_SOS_1002().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getRtCd())&&result.data.getRtCd().equalsIgnoreCase("0000")&&!TextUtils.isEmpty(result.data.getTmpAcptNo())){
//                        startActivitySingleTop(new Intent(this, ServiceSOSApplyInfoActivity.class).putExtra(KeyNames.KEY_NAME_SOS_TMP_NO, result.data.getTmpAcptNo()).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT),0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                        finish();
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
//                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
//                        }
//                        SnackBarUtil.show(this, serverMsg);
//                        showProgressDialog(false);
//                    }
//                    break;
//            }
//        });

    }

    private void selectRepairGroup(final List<RepairGroupVO> list) {
        try {
            final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
            bottomListDialog.setOnDismissListener(dialogInterface -> {
                String result = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(result)) {
                    RepairGroupVO repairGroupVO = null;
                    try {
                        repairGroupVO = reqViewModel.getRpshGrpCd(result, list);
                        dialogCalendarRepair.setSelectRepairGroup(repairGroupVO);
                    } catch (Exception e) {

                    }
                }
            });
            bottomListDialog.setDatas(reqViewModel.getRpshGrpNmList(list));
            bottomListDialog.setTitle(getString(R.string.sm_r_rsv02_04_12));
            bottomListDialog.show();
        } catch (Exception e) {

        }


    }

    @Override
    public void getDataFromIntent() {
        try {
            repairTypeVO = (RepairTypeVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE);
            btrVO = (BtrVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_BTR);
            mainVehicle = reqViewModel.getMainVehicle();
            String carRegNo = getIntent().getStringExtra(KeyNames.KEY_NAME_CAR_REG_NO);
            if(!TextUtils.isEmpty(carRegNo)){
                if(mainVehicle!=null) mainVehicle.setCarRgstNo(carRegNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (repairTypeVO == null) {
                exitPage("서비스 네트워크 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    /**
     * @author
     * @brief constraintSet 초기화
     */
    private void initConstraintSets() {
        views = new View[]{ui.lRepair, ui.lRsvtHopeDt};
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

            if (pos == 1) {
                requestPossibleTime();
            }
        }
    }


    private boolean checkValidRsvtHopeDt() {
        if (TextUtils.isEmpty(rsvtHopeDt)) {
            ui.tvRsvtHopeDt.setText(R.string.sm_r_rsv02_04_8);
            Paris.style(ui.tvRsvtHopeDt).apply(R.style.CommonSpinnerItemCalendarError);
            ui.tvErrorRsvtHopeDt.setVisibility(View.VISIBLE);
            ui.tvErrorRsvtHopeDt.setText(R.string.sm_r_rsv02_01_14);
            return false;
        } else {
            String date = DateUtil.getDate(DateUtil.getDefaultDateFormat(rsvtHopeDt, DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot)
                    + " / "
                    + DateUtil.getDate(DateUtil.getDefaultDateFormat(rsvtHopeTm, DateUtil.DATE_FORMAT_HHmm), DateUtil.DATE_FORMAT_HH_mm);

            ui.tvRsvtHopeDt.setText(date);
            Paris.style(ui.tvRsvtHopeDt).apply(R.style.CommonSpinnerItemCalendar);
            ui.tvErrorRsvtHopeDt.setVisibility(View.INVISIBLE);
            return true;
        }
    }


    private boolean checkValidRepair() {
        if (btrVO == null) {
            ui.tvErrorRepair.setVisibility(View.VISIBLE);
            ui.tvErrorRepair.setText(getString(R.string.sm_r_rsv02_01_14));
            Paris.style(ui.tvRepair).apply(R.style.CommonInputItemError);
            ui.tvRepair.setText(R.string.sm_r_rsv02_04_17);
            ui.tvTitleRepair.setVisibility(View.GONE);
            return false;
        } else {
            ui.tvErrorRepair.setVisibility(View.INVISIBLE);
            Paris.style(ui.tvRepair).apply(R.style.CommonInputItemEnable);
            ui.tvRepair.setText(btrVO.getAsnNm());
            ui.tvTitleRepair.setVisibility(View.VISIBLE);
            doTransition(1);
            return true;
        }
    }


    private boolean isValid() {

        for (View view : views) {
            if (view.getVisibility() == View.GONE) {
                switch (view.getId()) {
                    case R.id.l_repair:
                        return false;
                    case R.id.l_rsvt_hope_dt:
                        return checkValidRepair() && false;
                }
            }
        }
        return checkValidRepair() && checkValidRsvtHopeDt();
    }

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
        if (resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_REPAIR.getCode()) {
            exitPage(data, ResultCodes.REQ_CODE_SERVICE_RESERVE_REPAIR.getCode());
        } else if (resultCode == ResultCodes.REQ_CODE_BTR.getCode()&&data!=null) {
            btrVO = (BtrVO) data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
            checkValidRepair();
        }
    }


}
