package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import androidx.core.util.Pair;
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
import com.genesis.apps.ui.main.MainActivity;

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
        getDataFromIntent();//데이터 제대로 안 들어있으면 액티비티 종료처리까지 함
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //이용 내역
            case R.id.tv_titlebar_text_btn:
                startActivitySingleTop(new Intent(this, ServiceDriveHistoryActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //기사에게 전화
            case R.id.ll_service_drive_status_call_btn:
                PhoneUtil.phoneDial(this, serviceReqData.getDriverMdn());
                break;

            //취소 3종류 중에 뭔지 구분해서 처리
            case R.id.tv_service_drive_cancel_btn:
                onClickCancel();
                break;

            //다시 요청 버튼
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
        //신청 현황 확인
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
                                exitPage("기사정보가 존재하지 않습니다.", ResultCodes.RES_CODE_NETWORK.getCode());
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


        //취소요청
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
                            //취소 성공
                            //자동취소 타이머 끄기
                            cancelTimer();

                            //취소 종류에 따른 처리
                            switch (cancelBtnType) {
                                case R.string.service_drive_req_result_btn_05:
                                case R.string.service_drive_req_result_btn_02:
                                    exitPage(getString(R.string.sd_cancel_succ), ResultCodes.RES_CODE_NETWORK.getCode());
                                    break;

                                case R.string.service_drive_req_result_btn_03://대리운전 기사 못 찼았어요 상태에서
                                    exitPage("", ResultCodes.REQ_CODE_NORMAL.getCode());

                                    Intent intent = new Intent(this, ServiceDriveReqActivity.class);
                                    intent.putExtra(KeyNames.KEY_NAME_SERVICE_DRIVE_REQ_START_MSG, R.string.sd_cancel_succ);
//                                    intent.putExtra(KeyNames.KEY_NAME_VEHICLE_VO, mainVehicle); //주 차량 정보도 가져감
                                    startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                                    break;
                            }
                        } else {

                            String errMsg="";

                            switch (result.data.getRtCd()){
                                case "9029":
                                    errMsg = "기사님이 배정되어 취소를 할 수 없습니다.";
                                    setBlockCancelBtn(false);
                                    break;
                                case "9031":
                                    errMsg = "예약시간 3시간 전에만 취소할 수 있습니다.";
                                    setBlockCancelBtn(false);
                                    break;
                                default:
                                    errMsg = getString(R.string.sd_cancel_fail);
                                    break;
                            }
                            //취소 실패
                            SnackBarUtil.show(this, errMsg);
                        }
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

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

        //기사 배정 재시도 요청
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
                            //기사 배정 대기중 화면으로 바꿈(타이머도 취소함)
                            reInitDriverWait();
                        }
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

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

    //인텐트 까서 데이터를 뷰에 뿌림. 실패하면 액티비티 종료(뷰에 데이터 없어서 화면 다 깨짐)
    @Override
    public void getDataFromIntent() {
        Log.d(TAG, "getDataFromIntent: ");

        try {
            mainVehicle = viewModel.getMainVehicle();
            DDS_1001.Response data = (DDS_1001.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_DRIVE_STATUS);
            Log.d(TAG, "getDataFromIntent/initView : " + data);
            serviceReqData = data;
        } catch (NullPointerException e) {
            Log.d(TAG, "init: 신청 현황 또는 주 차량 데이터 처리 실패");
        } catch (Exception e){

        }

        if(serviceReqData!=null){
            initView();//getDataFromIntent()가 성공해야 실행가능
        }else{
            if(mainVehicle!=null&&!TextUtils.isEmpty(mainVehicle.getVin())) {
                viewModel.reqDDS1001(new DDS_1001.Request(APPIAInfo.SM_DRV06.getId(), mainVehicle.getVin()));
            }else{
                exitPage("차량 정보가 존재하지 않습니다.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        switch (serviceReqData.getSvcStusCd()) {
            //예약됨
            case DDS_1001.STATUS_RESERVED:
                initViewReserved();
                break;

            //기사 배정 대기중
            case DDS_1001.STATUS_DRIVER_MATCH_WAIT:
                initViewDriverWait();
                break;

            //기사 배정됨
            case DDS_1001.STATUS_DRIVER_MATCHED:
            case DDS_1001.STATUS_DRIVER_REMATCHED:
                initViewDriverMatched();
                break;

            //운행중
            case DDS_1001.STATUS_DRIVE_NOW:
                initViewDriveNow();
                break;

            //기사 배정 실패
            case DDS_1001.STATUS_NO_DRIVER:
                initViewNoDriver();
                break;

            default:
                //이 액티비티 진입 직전에 똑같은 검사 했으니 여기 걸릴 일은 없을 것 같긴 함
                Log.d(TAG, "getRES_DDS_1001: unknown svcStusCd ");
                finish();
                break;
        }

        //차총, 번호판, 출발지, 도착지
        //상태 메지지 타이틀, 세부설명, 버튼 텍스트, 버튼 활성화 여부
        //onSingleClickListener
        ui.setActivity(this);
    }

    //예약됨
    private void initViewReserved() {
        statusTitle = getString(R.string.service_drive_req_result_07);
        statusMsgVisibility = View.GONE;
        setCancelType(R.string.service_drive_req_result_btn_05);

        //초기값
        cancelBtnVisibility = View.VISIBLE;
        reReqBtnVisibility = View.GONE;

        setReserveViewStub();
    }

    //예약일시 ViewStub
    private void setReserveViewStub() {
        setViewStub(R.layout.layout_service_drive_status_reserved,
                (stub, inflated) -> {
                    String rsvDt = serviceReqData.getRsvDt();
                    Date date = DateUtil.getDefaultDateFormat(rsvDt, DateUtil.DATE_FORMAT_yyyyMMddHHmm, Locale.KOREA);
                    String dateStr = DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm);
                    ((LayoutServiceDriveStatusReservedBinding) DataBindingUtil.bind(inflated)).setDate(dateStr);
                });
    }

    //기사 배정 대기중
    private void initViewDriverWait() {
        statusTitle = getString(R.string.service_drive_req_result_01);
        statusMsg = getString(R.string.service_drive_req_result_02);
        setCancelType(R.string.service_drive_req_result_btn_02);

        //초기값
        statusMsgVisibility = View.VISIBLE;
        cancelBtnVisibility = View.VISIBLE;
        reReqBtnVisibility = View.GONE;

        setViewStub(
                R.layout.layout_service_drive_status_match_waiting,
                null);
    }

    //기사 배정됨
    private void initViewDriverMatched() {
        statusTitle = getString(R.string.service_drive_req_result_04);
        statusMsg = getString(R.string.service_drive_req_result_02);
        setCancelType(R.string.service_drive_req_result_btn_02);

        //초기값
        statusMsgVisibility = View.VISIBLE;
        cancelBtnVisibility = View.VISIBLE;
        reReqBtnVisibility = View.GONE;

        setDriverViewStub();
    }

    //운행중
    private void initViewDriveNow() {
        statusTitle = getString(R.string.service_drive_req_result_09);
        statusMsgVisibility = View.GONE;
        cancelBtnVisibility = View.GONE;

        //초기값
        reReqBtnVisibility = View.GONE;

        setDriverViewStub();
    }

    //운행기사 ViewStub
    private void setDriverViewStub() {
        setViewStub(R.layout.layout_service_drive_status_driver,
                (stub, inflated) -> {
                    ((LayoutServiceDriveStatusDriverBinding) DataBindingUtil.bind(inflated)).setActivity(this);
                });
    }

    private final long TIME_SEC=1_000;
    private final int TIME_MINUTE=60;
    private final int TIME_HOUR=5;
    //기사 배정 실패
    private void initViewNoDriver() {
        //layoutId : view stub 없음
        statusTitle = getString(R.string.service_drive_req_result_05);
        statusMsg = getString(R.string.service_drive_req_result_06);
        setCancelType(R.string.service_drive_req_result_btn_03);
        reReqBtnVisibility = View.VISIBLE;

        //초기값
        statusMsgVisibility = View.VISIBLE;
        cancelBtnVisibility = View.VISIBLE;

        //기사 배정 실패를 앱에서 인지하고 5분 지나면 취소 api 콜.
        //TODO 5분 뒤에 자동 취소한다고 안내는 안 해도 되나?
        autoCancelHandler = new Handler();
        autoCancelHandler.postDelayed(
                this::onClickCancel,
                TIME_SEC * TIME_MINUTE * TIME_HOUR);
    }

    //취소 버튼이 세 종류인데,
    //setActivity()로 뷰에 전달할 텍스트 및 클릭 리스너에서 이를 구별하기 위한 플래그 세팅
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

    //예약취소, 신청취소, 취소
    private void onClickCancel() {
        Log.d(TAG, "onClickCancel: ");

        switch (cancelBtnType) {
            //예약 취소
            case R.string.service_drive_req_result_btn_05:
                Log.d(TAG, "onClickCancel: 예약취소");
                MiddleDialog.dialogServiceDriveCancel(
                        this,
                        R.string.sd_cancel_msg1,
                        () -> reqCancel(
                                APPIAInfo.SM_DRV01_P03.getId(), //예약취소
                                DDS_1004.CANCEL                 //직접취소(사용자 변심)
                        )
                );
                break;

            //신청 취소(기사 배정 대기중, 배정 완료에서)
            case R.string.service_drive_req_result_btn_02:
                Log.d(TAG, "onClickCancel: 신청취소");
                MiddleDialog.dialogServiceDriveCancel(
                        this,
                        R.string.sd_cancel_msg2,
                        () -> reqCancel(
                                APPIAInfo.SM_DRV01_P02.getId(), //신청취소
                                DDS_1004.CANCEL                 //직접취소(사용자 변심)
                        )
                );
                break;

            //취소(기사 배정 실패에서, 대화상자 없이 바로 api 콜)
            case R.string.service_drive_req_result_btn_03:
                Log.d(TAG, "onClickCancel: 기사 없어서 취소");
                reqCancel(
                        APPIAInfo.SM_DRV01_P02.getId(), //신청취소
                        DDS_1004.CANCEL_CAUSE_NO_DRIVER //반강제취소(기사 못 찾아서)
                );
                break;

            default:
                Log.d(TAG, "onClickCancel: unexcepted value");
                break;
        }
    }

    //취소 요청
    private void reqCancel(String menuId, String cancelType) {
        Log.d(TAG, "reqCancel: ");
        viewModel.reqDDS1004(
                new DDS_1004.Request(
                        menuId,
                        serviceReqData.getVin(),
                        serviceReqData.getTransId(),
                        cancelType));
    }

    //자동취소 타이머를 취소
    private void cancelTimer() {
        if(autoCancelHandler != null) autoCancelHandler.removeCallbacksAndMessages(null);
    }

    //다시 요청(기사 배정 실패 후, 배정 재요청)
    private void reRequest() {
        Log.d(TAG, "reRequest: ");
        viewModel.reqDDS1006(
                new DDS_1006.Request(
                        APPIAInfo.SM_DRV01_P01.getId(),
                        serviceReqData.getVin(),
                        serviceReqData.getTransId()));
    }

    //기사 배정 재시도 요청 성공
    private void reInitDriverWait() {
        initViewDriverWait();   //ui 값을 기사 대기중으로 바꾸고
        ui.setActivity(this);   //뷰에 반영하고
        cancelTimer();          //자동취소 타이머를 취소
    }
}
