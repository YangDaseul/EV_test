package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.DDS_1001;
import com.genesis.apps.comm.model.gra.api.DDS_1004;
import com.genesis.apps.comm.model.gra.api.DDS_1006;
import com.genesis.apps.comm.viewmodel.DDSViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityServiceDriveReqResultBinding;
import com.genesis.apps.databinding.LayoutServiceDriveStatusReservedBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.concurrent.ExecutionException;

public class ServiceDriveReqResultActivity extends SubActivity<ActivityServiceDriveReqResultBinding> {
    private static final String TAG = ServiceDriveReqResultActivity.class.getSimpleName();

    private DDSViewModel ddsViewModel;
    private LGNViewModel lgnViewModel;

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

        setHistoryBtnListener();
        setViewModel();
        getDataFromIntent();//데이터 제대로 안 들어있으면 액티비티 종료처리까지 함
        initView();//getDataFromIntent()가 성공해야 실행가능
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
            //신청 내역
            case R.id.tv_titlebar_text_btn:
                startActivitySingleTop(new Intent(this, ServiceDriveHistoryActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //취소 3종류 중에 뭔지 구분해서 처리
            case R.id.tv_service_drive_cancel_btn:
                //todo 팝업으로 물어보고 취소
                reqCancel();
                break;

            //다시 요청 버튼
            case R.id.tv_service_drive_re_request_btn:
                //todo 팝업으로 물어보고 호출
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
        ddsViewModel = new ViewModelProvider(this).get(DDSViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
        //todo 4, 6


    }

    //인텐트 까서 데이터를 뷰에 뿌림. 실패하면 액티비티 종료(뷰에 데이터 없어서 화면 다 깨짐)
    @Override
    public void getDataFromIntent() {
        Log.d(TAG, "getDataFromIntent: ");

        try {
            DDS_1001.Response data = (DDS_1001.Response) getIntent().getSerializableExtra(DDS_1001.SERVICE_DRIVE_STATUS);
            Log.d(TAG, "getDataFromIntent/initView : " + data);
            serviceReqData = data;
        } catch (NullPointerException e) {
            Log.d(TAG, "init: 신청 현황 데이터 처리 실패");
            finish();
        }
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
                    String date = serviceReqData.getRsvDt();
                    Log.d(TAG, " date : " + date);
                    //todo : date를 yyyy.MM.dd 오후 hh:mm으로 표기
                    ((LayoutServiceDriveStatusReservedBinding) DataBindingUtil.bind(inflated)).setDate(date);
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
        setViewStub(
                R.layout.layout_service_drive_status_driver,
                new ViewStub.OnInflateListener() {
                    @Override
                    public void onInflate(ViewStub stub, View inflated) {
                        //todo 전화번호
                        // DataBindingUtil.bind(inflated).setActivity("bind target data");

                    }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setHistoryBtnListener() {
        ui.lServiceDriveReqResultTitlebar.tvTitlebarTextBtn.setOnClickListener(onSingleClickListener);
    }

    //예약취소, 신청취소, 취소
    private void reqCancel() {
        String menuId;
        String cancelType;

        switch (cancelBtnType) {
            //예약 취소
            case R.string.service_drive_req_result_btn_05:
                menuId = APPIAInfo.SM_DRV01_P03.getId();    //예약취소
                cancelType = DDS_1004.CANCEL;               //직접취소(사용자 변심)
                break;

            //신청 취소(기사 배정 대기중, 배정 완료에서)
            case R.string.service_drive_req_result_btn_02:
                menuId = APPIAInfo.SM_DRV01_P02.getId();    //신청취소
                cancelType = DDS_1004.CANCEL;               //직접취소(사용자 변심)
                break;

            //취소(기사 배정 실패에서)
            case R.string.service_drive_req_result_btn_03:
                menuId = APPIAInfo.SM_DRV01_P02.getId();    //신청취소
                cancelType = DDS_1004.CANCEL_CAUSE_NO_DRIVER;//반강제취소(기사 못 찾아서)
                break;

            default:
                Log.d(TAG, "onClickCancel: unexcepted value");
                return;
        }

        try {
            ddsViewModel.reqDDS1004(
                    new DDS_1004.Request(
                            menuId,
                            lgnViewModel.getMainVehicleFromDB().getVin(),
                            serviceReqData.getTransId(),
                            cancelType));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            //todo 차량 정보 접근 실패 예외처리
        }
    }

    //다시 요청(기사 배정 실패 후, 배정 재요청)
    private void reRequest() {
        try {
            ddsViewModel.reqDDS1006(
                    new DDS_1006.Request(
                            APPIAInfo.SM_DRV01_P01.getId(),
                            lgnViewModel.getMainVehicleFromDB().getVin(),
                            serviceReqData.getTransId()));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            //todo 차량 정보 접근 실패 예외처리
        }
    }

}
