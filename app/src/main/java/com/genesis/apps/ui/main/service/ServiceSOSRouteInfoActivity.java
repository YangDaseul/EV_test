package com.genesis.apps.ui.main.service;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.SOS_1006;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.comm.model.vo.map.FindPathReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathResVO;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomInfoBarBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.dialog.bottom.DialogSOSDriverInfo;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;
import com.hmns.playmap.shape.PlayMapPolyLine;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ServiceSOSRouteInfoActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private MapViewModel mapViewModel;
    private SOSViewModel sosViewModel;
//    private LayoutMapOverlayUiTopMsgBinding topBinding;
    private LayoutMapOverlayUiBottomInfoBarBinding bottomBinding;
    private SOSDriverVO sosDriverVO;
    private SOS_1006.Response response;
    private String tmpAcptNo;
    private int minute = 0;
    private Timer timer = null;
    private boolean initCall = true;

    private DialogSOSDriverInfo dialogSOSDriverInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        try {
            getDataFromIntent();
            setViewModel();
            setObserver();
            initView();
        }catch (Exception e){

        }
    }

    private void startTimer() {

        if (timer == null)
            timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    try {
//                        TestCode.SOS_1006 = TestCode.SOS_1006_2;
                        sosViewModel.reqSOS1006(new SOS_1006.Request(APPIAInfo.SM_EMGC03.getId(), tmpAcptNo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 10000, 10000);

    }

    private void pauseTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void initView() {
        ui.lMapOverlayTitle.lMapTitleBar.setVisibility(View.GONE);
        //내 위치로 이동 버튼 제거
        ui.btnMyPosition.setVisibility(View.GONE);
        //기본위치 갱신 시 맵 초기화
        ui.pmvMapView.initMap(Double.parseDouble(sosDriverVO.getGCustY()), Double.parseDouble(sosDriverVO.getGCustX()), DEFAULT_ZOOM);
//        mapViewModel.reqFindPathPolyLine("0","0","0","2",new PlayMapPoint(Double.parseDouble(sosDriverVO.getGXpos()), Double.parseDouble(sosDriverVO.getGYpos())), new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY())));
        reqFindPath();
    }

    private void reqFindPath() {
        mapViewModel.reqFindPathResVo(new FindPathReqVO("0", "0", "0", "2", "0", new PlayMapPoint(Double.parseDouble(sosDriverVO.getGYpos()), Double.parseDouble(sosDriverVO.getGXpos())), new ArrayList(), new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustY()), Double.parseDouble(sosDriverVO.getGCustX()))));
    }

    private void drawMaker(boolean isFit) {
        //운전자 마커 생성
        ui.pmvMapView.removeMarkerItem("start");
        PlayMapPoint point1 = new PlayMapPoint(Double.parseDouble(sosDriverVO.getGYpos()), Double.parseDouble(sosDriverVO.getGXpos()));
        PlayMapMarker driverMarker = new PlayMapMarker();
        driverMarker.setMapPoint(point1);
        driverMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_pin_driver));
        driverMarker.setCanShowCallout(false);
        driverMarker.setAutoCalloutVisible(false);
        ui.pmvMapView.addMarkerItem("start", driverMarker);


        //고객 위치 마커는 null일 경우에만 1회 생성
        if (!ui.pmvMapView.markeritems.containsKey("end")) {
            PlayMapPoint point2 = new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustY()), Double.parseDouble(sosDriverVO.getGCustX()));
            PlayMapMarker customerMarker = new PlayMapMarker();
            customerMarker.setMapPoint(point2);
            customerMarker.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_pin_mycar));
            customerMarker.setCanShowCallout(false);
            customerMarker.setAutoCalloutVisible(false);
            ui.pmvMapView.addMarkerItem("end", customerMarker);
        }

        if (isFit) {
            //마커가 찍힌 후 충분한 시간 뒤에 핏 마커가 동작함
            new Handler().postDelayed(() -> ui.pmvMapView.fitPolyLine(), 1000);
            initCall = false;
        }

    }

    public void drawPath(PlayMapPolyLine playMapPolyLine) {
        if (playMapPolyLine != null) {
            String testCor="#ba544d";
            playMapPolyLine.setLineColor(Color.RED);
            Log.v("color test", " color ori:"+Color.parseColor(testCor) +"    color:"+Color.RED);
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
        try {
            response = (SOS_1006.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SOS_DRIVER_VO);
            setData(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sosDriverVO == null || TextUtils.isEmpty(tmpAcptNo) || TextUtils.isEmpty(sosDriverVO.getGCustY()) || TextUtils.isEmpty(sosDriverVO.getGCustX())) {
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

        sosViewModel.getRES_SOS_1006().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && sosDriverVO != null && !TextUtils.isEmpty(tmpAcptNo)) {
                        setData(result.data);
                        reqFindPath();
                        showProgressDialog(false);
                    }
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });

        mapViewModel.getFindPathResVo().observe(this, result -> {
            if (result != null && result.status != null) {
                switch (result.status) {
                    case LOADING:
                        if (initCall) showProgressDialog(true);
                        break;
                    case SUCCESS:
                        if (result.data != null) {
                            PlayMapPolyLine playMapPolyLine = null;
                            try {
                                playMapPolyLine = mapViewModel.makePolyLine(result.data.getPosList());
                            } catch (Exception e) {
                                playMapPolyLine = null;
                            } finally {
                                drawPath(playMapPolyLine);
                                drawMaker(initCall);
                                updateBottomView(result.data.getSummary());
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

//    private void updateTopView(FindPathResVO.Summary summary) {
//        try {
//            if (summary != null) {
//                minute = summary.getTotalTime() / 60;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (topBinding == null) {
//                setViewStub(R.id.l_map_overlay_msg, R.layout.layout_map_overlay_ui_top_msg, new ViewStub.OnInflateListener() {
//                    @Override
//                    public void onInflate(ViewStub viewStub, View inflated) {
//                        topBinding = DataBindingUtil.bind(inflated);
//                        topBinding.tvMapTopMsgTime.setText(minute + "분 후");
//                    }
//                });
//            } else {
//                topBinding.tvMapTopMsgTime.setText(minute + "분 후");
//            }
//        }
//    }


    private void updateBottomView(FindPathResVO.Summary summary) {
        try {
            if (summary != null) {
                minute = summary.getTotalTime() / 60;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bottomBinding == null) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) findViewById(R.id.vs_map_overlay_bottom_box).getLayoutParams();
                params.setMargins(0, 0, 0, 0);
//                params.height = (int) DeviceUtil.dip2Pixel(this, 40);
                findViewById(R.id.vs_map_overlay_bottom_box).setLayoutParams(params);
                setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_info_bar, (viewStub, inflated) -> {
                    bottomBinding = DataBindingUtil.bind(inflated);
                    bottomBinding.btnDriverInfo.setOnClickListener(onSingleClickListener);
                    bottomBinding.setMinute(minute);
                });
            }else{
                bottomBinding.setMinute(minute);
            }

            if(dialogSOSDriverInfo!=null) dialogSOSDriverInfo.setMinute(minute);
        }
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.btn_driver_info:
                dialogSOSDriverInfo = new DialogSOSDriverInfo(this, R.style.BottomSheetDialogTheme);
                dialogSOSDriverInfo.setOnDismissListener(dialogInterface -> {

                });
                dialogSOSDriverInfo.setData(response);
                dialogSOSDriverInfo.setMinute(minute);
                dialogSOSDriverInfo.show();
                break;
        }
    }
}
