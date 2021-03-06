package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.api.gra.DDS_1001;
import com.genesis.apps.comm.model.api.gra.DDS_1004;
import com.genesis.apps.comm.model.api.gra.DDS_1006;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.PhoneUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.DDSViewModel;
import com.genesis.apps.databinding.ActivityServiceDriveReqResultBinding;
import com.genesis.apps.databinding.LayoutServiceDriveStatusDriverBinding;
import com.genesis.apps.databinding.LayoutServiceDriveStatusReservedBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ServiceDriveReqResultActivity extends SubActivity<ActivityServiceDriveReqResultBinding> {
    private static final String TAG = ServiceDriveReqResultActivity.class.getSimpleName();

    private DDSViewModel viewModel;
    private VehicleVO mainVehicle;
    private Handler autoCancelHandler;

    public DDS_1001.Response serviceReqData;

    public String statusTitle = "";
    public String statusMsg = "";
    public String cancelBtnText = "";
    public int statusMsgVisibility = View.VISIBLE;
    public int cancelBtnVisibility = View.VISIBLE;
    public int reReqBtnVisibility = View.GONE;
    private int cancelBtnType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_drive_req_result);
        setViewModel();
        setObserver();
        getDataFromIntent();//????????? ????????? ??? ??????????????? ???????????? ?????????????????? ???
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //?????? ??????
            case R.id.tv_titlebar_text_btn:
                startActivitySingleTop(new Intent(this, ServiceDriveHistoryActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //???????????? ??????
            case R.id.ll_service_drive_status_call_btn:
                PhoneUtil.phoneDial(this, serviceReqData.getDriverMdn());
                break;

            //?????? 3?????? ?????? ?????? ???????????? ??????
            case R.id.tv_service_drive_cancel_btn:
                onClickCancel();
                break;

            //?????? ?????? ??????
            case R.id.tv_service_drive_re_request_btn:
                reRequest();
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(DDSViewModel.class);
    }

    @Override
    public void setObserver() {
        //?????? ?????? ??????
        viewModel.getRES_DDS_1001().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null) {
                        showProgressDialog(false);
                        switch (StringUtil.isValidString(result.data.getSvcStusCd())) {
                            case DDS_1001.STATUS_DRIVER_MATCH_WAIT:
                            case DDS_1001.STATUS_RESERVED:
                            case DDS_1001.STATUS_DRIVER_MATCHED:
                            case DDS_1001.STATUS_DRIVER_REMATCHED:
                            case DDS_1001.STATUS_DRIVE_NOW:
                            case DDS_1001.STATUS_NO_DRIVER:
                                serviceReqData = result.data;
                                initView();
                                return;
                            default:
                                exitPage("??????????????? ???????????? ????????????.", ResultCodes.RES_CODE_NETWORK.getCode());
                                return;
                        }
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
                            serverMsg = getString(R.string.instability_network);
                        }
                        exitPage(serverMsg, ResultCodes.RES_CODE_NETWORK.getCode());
                    }
                    break;
            }
        });


        //????????????
        viewModel.getRES_DDS_1004().observe(this, result -> {
            Log.d(TAG, "setObserver: obs DDS_1004 : cancel" + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getRtCd() != null) {
                        showProgressDialog(false);

                        if (result.data.getRtCd().equals(BaseResponse.RETURN_CODE_SUCC)) {
                            setBlockCancelBtn(true);
                            //?????? ??????
                            //???????????? ????????? ??????
                            cancelTimer();

                            //?????? ????????? ?????? ??????
                            switch (cancelBtnType) {
                                case R.string.service_drive_req_result_btn_05:
                                case R.string.service_drive_req_result_btn_02:
                                    exitPage(getString(R.string.sd_cancel_succ), ResultCodes.RES_CODE_NETWORK.getCode());
                                    break;

                                case R.string.service_drive_req_result_btn_03://???????????? ?????? ??? ???????????? ????????????
                                    exitPage("", ResultCodes.REQ_CODE_NORMAL.getCode());

                                    Intent intent = new Intent(this, ServiceDriveReqActivity.class);
                                    intent.putExtra(KeyNames.KEY_NAME_SERVICE_DRIVE_REQ_START_MSG, R.string.sd_cancel_succ);
//                                    intent.putExtra(KeyNames.KEY_NAME_VEHICLE_VO, mainVehicle); //??? ?????? ????????? ?????????
                                    startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                                    break;
                            }
                        } else {

                            String errMsg="";

                            switch (result.data.getRtCd()){
                                case "9029":
                                    errMsg = "???????????? ???????????? ????????? ??? ??? ????????????.";
                                    setBlockCancelBtn(false);
                                    break;
                                case "9031":
                                    errMsg = "???????????? 3?????? ????????? ????????? ??? ????????????.";
                                    setBlockCancelBtn(false);
                                    break;
                                default:
                                    errMsg = getString(R.string.sd_cancel_fail);
                                    break;
                            }
                            //?????? ??????
                            SnackBarUtil.show(this, errMsg);
                        }
                        break;
                    }
                    //not break; ????????? ???????????? default??? ????????????

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

        //?????? ?????? ????????? ??????
        viewModel.getRES_DDS_1006().observe(this, result -> {
            Log.d(TAG, "setObserver: obs DDS_1006 : re req" + result.status);
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getRtCd() != null) {
                        showProgressDialog(false);
                        if (Objects.equals(result.data.getRtCd(), BaseResponse.RETURN_CODE_SUCC)) {
                            //?????? ?????? ????????? ???????????? ??????(???????????? ?????????)
                            reInitDriverWait();
                        }
                        break;
                    }
                    //not break; ????????? ???????????? default??? ????????????

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });
    }

    private void setBlockCancelBtn(boolean b) {
        ui.tvServiceDriveCancelBtn.setEnabled(b);
        if (b) {
            Paris.style(ui.tvServiceDriveCancelBtn).apply(R.style.BigBtn_White);
        } else {
            Paris.style(ui.tvServiceDriveCancelBtn).apply(R.style.BigBtn_Black);
        }
        ui.btnBlock.setVisibility(!b ? View.VISIBLE : View.GONE);
    }

    //????????? ?????? ???????????? ?????? ??????. ???????????? ???????????? ??????(?????? ????????? ????????? ?????? ??? ??????)
    @Override
    public void getDataFromIntent() {
        Log.d(TAG, "getDataFromIntent: ");

        try {
            mainVehicle = viewModel.getMainVehicle();
            DDS_1001.Response data = (DDS_1001.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_DRIVE_STATUS);
            Log.d(TAG, "getDataFromIntent/initView : " + data);
            serviceReqData = data;
        } catch (NullPointerException e) {
            Log.d(TAG, "init: ?????? ?????? ?????? ??? ?????? ????????? ?????? ??????");
        } catch (Exception e){

        }

        if(serviceReqData!=null){
            initView();//getDataFromIntent()??? ???????????? ????????????
        }else{
            if(mainVehicle!=null&&!TextUtils.isEmpty(mainVehicle.getVin())) {
                viewModel.reqDDS1001(new DDS_1001.Request(APPIAInfo.SM_DRV06.getId(), mainVehicle.getVin()));
            }else{
                exitPage("?????? ????????? ???????????? ????????????.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        switch (serviceReqData.getSvcStusCd()) {
            //?????????
            case DDS_1001.STATUS_RESERVED:
                initViewReserved();
                break;

            //?????? ?????? ?????????
            case DDS_1001.STATUS_DRIVER_MATCH_WAIT:
                initViewDriverWait();
                break;

            //?????? ?????????
            case DDS_1001.STATUS_DRIVER_MATCHED:
            case DDS_1001.STATUS_DRIVER_REMATCHED:
                initViewDriverMatched();
                break;

            //?????????
            case DDS_1001.STATUS_DRIVE_NOW:
                initViewDriveNow();
                break;

            //?????? ?????? ??????
            case DDS_1001.STATUS_NO_DRIVER:
                initViewNoDriver();
                break;

            default:
                //??? ???????????? ?????? ????????? ????????? ?????? ????????? ?????? ?????? ?????? ?????? ??? ?????? ???
                Log.d(TAG, "getRES_DDS_1001: unknown svcStusCd ");
                finish();
                break;
        }

        //??????, ?????????, ?????????, ?????????
        //?????? ????????? ?????????, ????????????, ?????? ?????????, ?????? ????????? ??????
        //onSingleClickListener
        ui.setActivity(this);
    }

    //?????????
    private void initViewReserved() {
        statusTitle = getString(R.string.service_drive_req_result_07);
        statusMsgVisibility = View.GONE;
        setCancelType(R.string.service_drive_req_result_btn_05);

        //?????????
        cancelBtnVisibility = View.VISIBLE;
        reReqBtnVisibility = View.GONE;

        setReserveViewStub();
    }

    //???????????? ViewStub
    private void setReserveViewStub() {
        setViewStub(R.layout.layout_service_drive_status_reserved,
                (stub, inflated) -> {
                    String rsvDt = StringUtil.isValidString(serviceReqData.getRsvDt());
                    Date date = DateUtil.getDefaultDateFormat(rsvDt, rsvDt.length()>12 ? DateUtil.DATE_FORMAT_yyyyMMddHHmmss : DateUtil.DATE_FORMAT_yyyyMMddHHmm, Locale.KOREA);
                    String dateStr = DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm);
                    ((LayoutServiceDriveStatusReservedBinding) DataBindingUtil.bind(inflated)).setDate(dateStr);
                });
    }

    //?????? ?????? ?????????
    private void initViewDriverWait() {
        statusTitle = getString(R.string.service_drive_req_result_01);
        statusMsg = getString(R.string.service_drive_req_result_02);
        setCancelType(R.string.service_drive_req_result_btn_02);

        //?????????
        statusMsgVisibility = View.VISIBLE;
        cancelBtnVisibility = View.VISIBLE;
        reReqBtnVisibility = View.GONE;

        setViewStub(
                R.layout.layout_service_drive_status_match_waiting,
                null);
    }

    //?????? ?????????
    private void initViewDriverMatched() {
        statusTitle = getString(R.string.service_drive_req_result_04);
        statusMsg = getString(R.string.service_drive_req_result_02);
        setCancelType(R.string.service_drive_req_result_btn_02);

        //?????????
        statusMsgVisibility = View.VISIBLE;
        cancelBtnVisibility = View.VISIBLE;
        reReqBtnVisibility = View.GONE;

        setDriverViewStub();
    }

    //?????????
    private void initViewDriveNow() {
        statusTitle = getString(R.string.service_drive_req_result_09);
        statusMsgVisibility = View.GONE;
        cancelBtnVisibility = View.GONE;

        //?????????
        reReqBtnVisibility = View.GONE;

        setDriverViewStub();
    }

    //???????????? ViewStub
    private void setDriverViewStub() {
        setViewStub(R.layout.layout_service_drive_status_driver,
                (stub, inflated) -> {
                    ((LayoutServiceDriveStatusDriverBinding) DataBindingUtil.bind(inflated)).setActivity(this);
                });
    }

    private final long TIME_SEC=1_000;
    private final int TIME_MINUTE=60;
    private final int TIME_HOUR=5;
    //?????? ?????? ??????
    private void initViewNoDriver() {
        //layoutId : view stub ??????
        statusTitle = getString(R.string.service_drive_req_result_05);
        statusMsg = getString(R.string.service_drive_req_result_06);
        setCancelType(R.string.service_drive_req_result_btn_03);
        reReqBtnVisibility = View.VISIBLE;

        //?????????
        statusMsgVisibility = View.VISIBLE;
        cancelBtnVisibility = View.VISIBLE;

        //?????? ?????? ????????? ????????? ???????????? 5??? ????????? ?????? api ???.
        //TODO 5??? ?????? ?????? ??????????????? ????????? ??? ?????? ???????
        autoCancelHandler = new Handler();
        autoCancelHandler.postDelayed(
                this::onClickCancel,
                TIME_SEC * TIME_MINUTE * TIME_HOUR);
    }

    //?????? ????????? ??? ????????????,
    //setActivity()??? ?????? ????????? ????????? ??? ?????? ??????????????? ?????? ???????????? ?????? ????????? ??????
    private void setCancelType(int cancelBtnTextId) {
        cancelBtnText = getString(cancelBtnTextId);
        cancelBtnType = cancelBtnTextId;
    }

    public void setViewStub(int addLayout, ViewStub.OnInflateListener listener) {
        ViewStub stub = findViewById(R.id.vs_service_drive_req_result_status_info);
        stub.setLayoutResource(addLayout);
        stub.setOnInflateListener(listener);
        stub.inflate();
    }

    //????????????, ????????????, ??????
    private void onClickCancel() {
        Log.d(TAG, "onClickCancel: ");

        switch (cancelBtnType) {
            //?????? ??????
            case R.string.service_drive_req_result_btn_05:
                Log.d(TAG, "onClickCancel: ????????????");
                MiddleDialog.dialogServiceDriveCancel(
                        this,
                        R.string.sd_cancel_msg1,
                        () -> reqCancel(
                                APPIAInfo.SM_DRV01_P03.getId(), //????????????
                                DDS_1004.CANCEL                 //????????????(????????? ??????)
                        )
                );
                break;

            //?????? ??????(?????? ?????? ?????????, ?????? ????????????)
            case R.string.service_drive_req_result_btn_02:
                Log.d(TAG, "onClickCancel: ????????????");
                MiddleDialog.dialogServiceDriveCancel(
                        this,
                        R.string.sd_cancel_msg2,
                        () -> reqCancel(
                                APPIAInfo.SM_DRV01_P02.getId(), //????????????
                                DDS_1004.CANCEL                 //????????????(????????? ??????)
                        )
                );
                break;

            //??????(?????? ?????? ????????????, ???????????? ?????? ?????? api ???)
            case R.string.service_drive_req_result_btn_03:
                Log.d(TAG, "onClickCancel: ?????? ????????? ??????");
                reqCancel(
                        APPIAInfo.SM_DRV01_P02.getId(), //????????????
                        DDS_1004.CANCEL_CAUSE_NO_DRIVER //???????????????(?????? ??? ?????????)
                );
                break;

            default:
                Log.d(TAG, "onClickCancel: unexcepted value");
                break;
        }
    }

    //?????? ??????
    private void reqCancel(String menuId, String cancelType) {
        Log.d(TAG, "reqCancel: ");
        viewModel.reqDDS1004(
                new DDS_1004.Request(
                        menuId,
                        serviceReqData.getVin(),
                        serviceReqData.getTransId(),
                        cancelType));
    }

    //???????????? ???????????? ??????
    private void cancelTimer() {
        if(autoCancelHandler != null) autoCancelHandler.removeCallbacksAndMessages(null);
    }

    //?????? ??????(?????? ?????? ?????? ???, ?????? ?????????)
    private void reRequest() {
        Log.d(TAG, "reRequest: ");
        viewModel.reqDDS1006(
                new DDS_1006.Request(
                        APPIAInfo.SM_DRV01_P01.getId(),
                        serviceReqData.getVin(),
                        serviceReqData.getTransId()));
    }

    //?????? ?????? ????????? ?????? ??????
    private void reInitDriverWait() {
        initViewDriverWait();   //ui ?????? ?????? ??????????????? ?????????
        ui.setActivity(this);   //?????? ????????????
        cancelTimer();          //???????????? ???????????? ??????
    }
}
