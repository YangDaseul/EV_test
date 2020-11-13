package com.genesis.apps.ui.main.service;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.SOS_1006;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.comm.model.vo.map.FindPathReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathResVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomInfoBarBinding;
import com.genesis.apps.databinding.LayoutMapOverlayUiTopMsgBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.DialogSOSDriverInfo;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;
import com.hmns.playmap.shape.PlayMapPolyLine;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ServiceSOSRouteInfoActivity extends GpsBaseActivity<ActivityMap2Binding> {
//    private String tmpAcptNo;
    private MapViewModel mapViewModel;
    private SOSViewModel sosViewModel;
    private LayoutMapOverlayUiTopMsgBinding topBinding;
    private LayoutMapOverlayUiBottomInfoBarBinding bottomBinding;
    private SOSDriverVO sosDriverVO;
    private SOS_1006.Response response;
    private String tmpAcptNo;
    private int minute=0;
    private Timer timer = null;
    private boolean initCall=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void startTimer() {

        if(timer==null)
            timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    try{
                        TestCode.SOS_1006 = TestCode.SOS_1006_2;
                        sosViewModel.reqSOS1006(new SOS_1006.Request(APPIAInfo.SM_EMGC03.getId(),tmpAcptNo));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        },10000,10000);

    }

    private void pauseTimer(){
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    private void initView() {
        updateBottomView();
        ui.lMapOverlayTitle.lMapTitleBar.setVisibility(View.GONE);
        //내 위치로 이동 버튼 제거
        ui.btnMyPosition.setVisibility(View.GONE);
        //기본위치 갱신 시 맵 초기화
        ui.pmvMapView.initMap(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY()),17);
//        mapViewModel.reqFindPathPolyLine("0","0","0","2",new PlayMapPoint(Double.parseDouble(sosDriverVO.getGXpos()), Double.parseDouble(sosDriverVO.getGYpos())), new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY())));
        reqFindPath();
    }

    private void reqFindPath() {
        mapViewModel.reqFindPathResVo(new FindPathReqVO("0","0","0","2","0",new PlayMapPoint(Double.parseDouble(sosDriverVO.getGXpos()), Double.parseDouble(sosDriverVO.getGYpos())),new ArrayList(),new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY()))));
    }

    private void drawMaker(boolean isFit){
        //운전자 마커 생성
        PlayMapPoint point1 = new PlayMapPoint(Double.parseDouble(sosDriverVO.getGXpos()), Double.parseDouble(sosDriverVO.getGYpos()));
        PlayMapMarker driverMarker = new PlayMapMarker();
        driverMarker.setMapPoint(point1);
        driverMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_pin_driver));
        driverMarker.setCanShowCallout(false);
        driverMarker.setAutoCalloutVisible(false);
        ui.pmvMapView.addMarkerItem("start", driverMarker);


        //고객 위치 마커는 null일 경우에만 1회 생성
        if(!ui.pmvMapView.markeritems.containsKey("end")) {
            PlayMapPoint point2 = new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY()));
            PlayMapMarker customerMarker = new PlayMapMarker();
            customerMarker.setMapPoint(point2);
            customerMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_pin_mycar));
            customerMarker.setCanShowCallout(false);
            customerMarker.setAutoCalloutVisible(false);
            ui.pmvMapView.addMarkerItem("end", customerMarker);
        }

        if(isFit) {
            //마커가 찍힌 후 충분한 시간 뒤에 핏 마커가 동작함
            new Handler().postDelayed(() -> ui.pmvMapView.fitMarker(), 1000);
            initCall=false;
        }

    }

    public void drawPath(PlayMapPolyLine playMapPolyLine) {
        if(playMapPolyLine!=null) {
            playMapPolyLine.setLineColor(Color.RED);
            playMapPolyLine.setLineWidth(4);
            ui.pmvMapView.addPolyLine("routeResult", playMapPolyLine);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimer();
    }

    @Override
    public void getDataFromIntent() {
        try{
            response = (SOS_1006.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SOS_DRIVER_VO);
            setData((SOS_1006.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SOS_DRIVER_VO));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(sosDriverVO==null||TextUtils.isEmpty(tmpAcptNo)){
                exitPage("기사 정보를 불러오지 못했습니다\n잠시 후 다시 시도해 주세요.", ResultCodes.RES_CODE_NETWORK.getCode());
            }
        }
    }

    private void setData(SOS_1006.Response response) {
        this.response = response;
        this.tmpAcptNo = response.getTmpAcptNo();
        this.sosDriverVO = response.getSosDriverVO();
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
    }

    @Override
    public void setObserver() {

//        mapViewModel.getPlayMapPolyLine().observe(this, result -> {
//            if(result!=null&&result.status!=null) {
//                switch (result.status) {
//                    case LOADING:
//                        showProgressDialog(true);
//                        break;
//                    case SUCCESS:
//                        showProgressDialog(false);
//                        if(result.data!=null) {
//                            drawPath(result.data);
//                            drawMaker(true);
//                        }
//                        break;
//                    default:
//                        showProgressDialog(false);
//                        break;
//                }
//            }
//        });

        sosViewModel.getRES_SOS_1006().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data!=null&&sosDriverVO!=null&&!TextUtils.isEmpty(tmpAcptNo)){
                        setData(result.data);
                        reqFindPath();
                    }
                    break;
                default:
                    break;
            }
        });

        mapViewModel.getFindPathResVo().observe(this, result -> {
            if(result!=null&&result.status!=null) {
                switch (result.status) {
                    case LOADING:
                        if(initCall) showProgressDialog(true);
                        break;
                    case SUCCESS:
                        if(result.data!=null) {
                            PlayMapPolyLine playMapPolyLine = null;
                            try {
                                playMapPolyLine = mapViewModel.makePolyLine(result.data.getPosList());
                            }catch (Exception e){
                                playMapPolyLine = null;
                            }finally {
                                drawPath(playMapPolyLine);
                                drawMaker(initCall);
                                updateTopView(result.data.getSummary());
                                showProgressDialog(false);
                            }
                            break;
                        }
                    default:
                        showProgressDialog(false);
                        break;
                }
            }

        });
    }

    private void updateTopView(FindPathResVO.Summary summary) {
        try {
            if (summary != null) {
                minute = summary.getTotalTime()/60;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (topBinding == null) {
                setViewStub(R.id.l_map_overlay_msg, R.layout.layout_map_overlay_ui_top_msg, new ViewStub.OnInflateListener() {
                    @Override
                    public void onInflate(ViewStub viewStub, View inflated) {
                        topBinding = DataBindingUtil.bind(inflated);
                        topBinding.tvMapTopMsgTime.setText(minute+"분 후");
                    }
                });
            } else {
                topBinding.tvMapTopMsgTime.setText(minute+"분 후");
            }
        }
    }


    private void updateBottomView() {
        try {
            if (bottomBinding == null) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)findViewById(R.id.vs_map_overlay_bottom_box).getLayoutParams();
                params.setMargins(0,0,0,0);
                params.height= (int)DeviceUtil.dip2Pixel(this,40);
                findViewById(R.id.vs_map_overlay_bottom_box).setLayoutParams(params);
                setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_info_bar, (viewStub, inflated) -> {
                    bottomBinding = DataBindingUtil.bind(inflated);
                    bottomBinding.btnDriverInfo.setOnClickListener(onSingleClickListener);
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.btn_driver_info:

                final DialogSOSDriverInfo dialogSOSDriverInfo = new DialogSOSDriverInfo(this, R.style.BottomSheetDialogTheme);
                dialogSOSDriverInfo.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });
                dialogSOSDriverInfo.setData(response);
                dialogSOSDriverInfo.show();

                break;

//            case R.id.tv_map_address_btn://선택
//                if(selectAddressVO!=null){
//                    exitPage(new Intent().putExtra(KeyNames.KEY_NAME_ADDR, selectAddressVO), ResultCodes.REQ_CODE_SERVICE_SOS_MAP.getCode());
//                }
//                break;
//            case R.id.btn_my_position:
//                lgnViewModel.setPosition(lgnViewModel.getMyPosition().get(0), lgnViewModel.getMyPosition().get(1));
//                ui.pmvMapView.setMapCenterPoint(new PlayMapPoint(lgnViewModel.getMyPosition().get(0), lgnViewModel.getMyPosition().get(1)), 500);
//                break;
        }

    }

}