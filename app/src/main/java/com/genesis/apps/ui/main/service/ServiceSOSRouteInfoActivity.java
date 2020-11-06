package com.genesis.apps.ui.main.service;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.comm.model.vo.map.FindPathReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathResVO;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomInfoBarBinding;
import com.genesis.apps.databinding.LayoutMapOverlayUiTopMsgBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;
import com.hmns.playmap.shape.PlayMapPolyLine;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ServiceSOSRouteInfoActivity extends GpsBaseActivity<ActivityMap2Binding> {
//    private String tmpAcptNo;
    private MapViewModel mapViewModel;
    private LayoutMapOverlayUiTopMsgBinding topBinding;
    private LayoutMapOverlayUiBottomInfoBarBinding bottomSelectBinding;
    private SOSDriverVO sosDriverVO;
    private String tmpAcptNo;
    private int minute=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        ui.lMapOverlayTitle.tvMapTitleText.setVisibility(View.GONE);
        //내 위치로 이동 버튼 제거
        ui.btnMyPosition.setVisibility(View.GONE);
        //기본위치 갱신 시 맵 초기화
        ui.pmvMapView.initMap(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY()),17);
//        mapViewModel.reqFindPathPolyLine("0","0","0","2",new PlayMapPoint(Double.parseDouble(sosDriverVO.getGXpos()), Double.parseDouble(sosDriverVO.getGYpos())), new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY())));
        mapViewModel.reqFindPathResVo(new FindPathReqVO("0","0","0","2","0",new PlayMapPoint(Double.parseDouble(sosDriverVO.getGXpos()), Double.parseDouble(sosDriverVO.getGYpos())),new ArrayList(),new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY()))));
    }

    private void drawMaker(boolean isFit){
        PlayMapPoint point1 = new PlayMapPoint(Double.parseDouble(sosDriverVO.getGXpos()), Double.parseDouble(sosDriverVO.getGYpos()));
        PlayMapPoint point2 = new PlayMapPoint(Double.parseDouble(sosDriverVO.getGCustX()), Double.parseDouble(sosDriverVO.getGCustY()));
        PlayMapMarker mapMarker1 = new PlayMapMarker();
        PlayMapMarker mapMarker2 = new PlayMapMarker();
        mapMarker1.setMapPoint(point1);
        mapMarker2.setMapPoint(point2);
        mapMarker1.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_pin_driver));
        mapMarker2.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_pin_mycar));
        mapMarker1.setCanShowCallout(false);
        mapMarker1.setAutoCalloutVisible(false);
        mapMarker2.setCanShowCallout(false);
        mapMarker2.setAutoCalloutVisible(false);


        ui.pmvMapView.addMarkerItem("start", mapMarker1);
        ui.pmvMapView.addMarkerItem("end", mapMarker2);

        if(isFit) {
            //마커가 찍힌 후 충분한 시간 뒤에 핏 마커가 동작함
            new Handler().postDelayed(() -> ui.pmvMapView.fitPolyLine(), 1000);
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
    }

    @Override
    public void getDataFromIntent() {
        try{
            tmpAcptNo = getIntent().getStringExtra(KeyNames.KEY_NAME_SOS_TMP_NO);
            sosDriverVO = (SOSDriverVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SOS_DRIVER_VO);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(sosDriverVO==null||TextUtils.isEmpty(tmpAcptNo)){
                exitPage("기사 정보를 불러오지 못했습니다\n잠시 후 다시 시도해 주세요.", ResultCodes.RES_CODE_NETWORK.getCode());
            }
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public void setObserver() {

        mapViewModel.getPlayMapPolyLine().observe(this, result -> {
            if(result!=null&&result.status!=null) {
                switch (result.status) {
                    case LOADING:
                        showProgressDialog(true);
                        break;
                    case SUCCESS:
                        showProgressDialog(false);
                        if(result.data!=null) {
                            drawPath(result.data);
                            drawMaker(true);
                        }
                        break;
                    default:
                        showProgressDialog(false);
                        break;
                }
            }
        });

        mapViewModel.getFindPathResVo().observe(this, result -> {
            if(result!=null&&result.status!=null) {
                switch (result.status) {
                    case LOADING:
                        showProgressDialog(true);
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
                                drawMaker(true);
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

//        mapViewModel.getPlayMapGeoItem().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data!=null){
//                        mapViewModel.reqPlayMapPoiItemList(new AroundPOIReqVO("건물", requestPosition[0],requestPosition[1], 30, 3, 1, 0, 20));
//                    }
//                    break;
//                default:
//                    showProgressDialog(false);
//                    break;
//            }
//        });
//
//        mapViewModel.getPlayMapPoiItem().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    //빌딩이름이 획득 가능하면
//                    if(result.data!=null&&result.data.size()>0){
//                        updateAddressInfo(new Gson().fromJson(new Gson().toJson(result.data.get(0)),AddressVO.class));
//                        break;
//                    }
//                default:
//                    showProgressDialog(false);
//                    //빌딩이름이 획득 불가능하면
//                    PlayMapGeoItem item = mapViewModel.getPlayMapGeoItem().getValue().data;
//                    if(item!=null){
//                        updateAddressInfo(new Gson().fromJson(new Gson().toJson(item),AddressVO.class));
//                    }
//                    break;
//            }
//        });
//
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


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
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

//    /**
//     * drawMarkerItem 지도에 마커를 그린다.
//     */
//    public void drawMarkerItem(PlayMapGeoItem item, int iconId) {
//        PlayMapMarker markerItem = new PlayMapMarker();
////        PlayMapPoint point = mapView.getMapCenterPoint();
//        PlayMapPoint point = new PlayMapPoint(item.centerLat, item.centerLon);
//        markerItem.setMapPoint(point);
//        markerItem.setCanShowCallout(false);
//        markerItem.setAutoCalloutVisible(false);
//        markerItem.setIcon(((BitmapDrawable) getResources().getDrawable(iconId, null)).getBitmap());
//        ui.pmvMapView.addMarkerItem("center", markerItem);
//    }



    private void updateAddressInfo(final AddressVO addressVO) {
//        selectAddressVO = addressVO;
//        if (bottomSelectBinding == null) {
//            setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_address, new ViewStub.OnInflateListener() {
//                @Override
//                public void onInflate(ViewStub viewStub, View inflated) {
//                    bottomSelectBinding = DataBindingUtil.bind(inflated);
//                    bottomSelectBinding.tvMapAddressBtn.setOnClickListener(onSingleClickListener);
//                    setViewAddresInfo(addressVO.getTitle(), addressVO.getCname(), addressVO.getAddrRoad());
//                }
//            });
//        } else {
//            setViewAddresInfo(addressVO.getTitle(), addressVO.getCname(), addressVO.getAddrRoad());
//        }
    }



}
