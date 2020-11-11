package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.gra.api.DDS_1001;
import com.genesis.apps.comm.model.gra.api.DDS_1004;
import com.genesis.apps.comm.model.gra.api.DDS_1006;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.PhoneUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
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

        getDataFromIntent();//데이터 제대로 안 들어있으면 액티비티 종료처리까지 함
        initView();//getDataFromIntent()가 성공해야 실행가능
        setViewModel();
        setObserver();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //todo : 액티비티 유지된 상태에서 기사 배정 등 서비스 이용 상황이 진행되면 처리
        // getDataFromIntent 한 번 더 부르면 되나?
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //이용 내역
            case R.id.tv_titlebar_text_btn:
                startActivitySingleTop(new Intent(this, ServiceDriveHistoryActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //기사에게 전화
            case R.id.tv_service_drive_status_call_btn:
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
        Log.d(TAG, "setObserver: ");

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
                                    intent.putExtra(ServiceDriveReqActivity.START_MSG, R.string.sd_cancel_succ);
                                    intent.putExtra(VehicleVO.VEHICLE_VO, mainVehicle); //주 차량 정보도 가져감
                                    startActivitySingleTop(intent, RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                                    break;
                            }
                        } else {
                            //취소 실패
                            SnackBarUtil.show(this, getString(R.string.sd_cancel_fail));
                        }
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    SnackBarUtil.show(this, getString(result.message));
                    //todo : 구체적인 예외처리
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
                    SnackBarUtil.show(this, getString(result.message));
                    //todo : 구체적인 예외처리
                    break;
            }
        });
    }

    //인텐트 까서 데이터를 뷰에 뿌림. 실패하면 액티비티 종료(뷰에 데이터 없어서 화면 다 깨짐)
    @Override
    public void getDataFromIntent() {
        Log.d(TAG, "getDataFromIntent: ");

        try {
            DDS_1001.Response data = (DDS_1001.Response) getIntent().getSerializableExtra(DDS_1001.SERVICE_DRIVE_STATUS);
            Log.d(TAG, "getDataFromIntent/initView : " + data);
            serviceReqData = data;
            mainVehicle = (VehicleVO) getIntent().getSerializableExtra(VehicleVO.VEHICLE_VO);
        } catch (NullPointerException e) {
            Log.d(TAG, "init: 신청 현황 또는 주 차량 데이터 처리 실패");
            finish();
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
                1000 * 60 * 5);
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
        autoCancelHandler.removeCallbacksAndMessages(null);
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
