package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.map.AroundPOIReqVO;
import com.genesis.apps.comm.model.vo.map.ReverseGeocodingReqVO;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomAddressBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.google.gson.Gson;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.extension.PlayMapGeoItem;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapSearchMyPositionActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private MapViewModel mapViewModel;
    private LGNViewModel lgnViewModel;
    private LayoutMapOverlayUiBottomAddressBinding bottomSelectBinding;
    private AddressVO selectAddressVO;
    private Double[] requestPosition = new Double[2];

    private int titleId;
    private int msgId;
    //true면 주소 검색 창을 바로 오픈
    private boolean isDirect=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getDataFromIntent();
        setViewModel();
        setObserver();
        reqMyLocation();
    }

    private void checkIsDirect() {
        if(isDirect) {
            isDirect=false;
            openSearchAddress();
        }
    }

    private void initView(final double latitude, final double longitude) {
        ui.ivCenterMaker.setVisibility(View.VISIBLE);
        ui.ivCenterMaker.setImageResource(R.drawable.ic_pin_car);
        //기본위치 갱신 시 맵 초기화
        ui.pmvMapView.initMap(latitude, longitude,17);
        ui.lMapOverlayTitle.tvMapTitleText.setVisibility(View.GONE);
        ui.lMapOverlayTitle.tvMapTitleAddress.setVisibility(View.VISIBLE);
        ui.lMapOverlayTitle.tvMapTitleAddress.setText(getTitleAddressMsg());
        ui.lMapOverlayTitle.tvMapTitleAddress.setOnClickListener(onSingleClickListener);
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);
//        ui.lMapOverlayTitle.fabMapBack.setOnClickListener(onSingleClickListener);
        ui.pmvMapView.onMapTouchUpListener((motionEvent, makerList) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    lgnViewModel.setPosition(ui.pmvMapView.getMapCenterPoint().getLatitude(),ui.pmvMapView.getMapCenterPoint().getLongitude());

//                    mapViewModel.reqPlayMapGeoItem(new ReverseGeocodingReqVO(ui.pmvMapView.getMapCenterPoint().getLatitude(),ui.pmvMapView.getMapCenterPoint().getLongitude(),1));
                    break;
                default:
                    break;
            }

        });

        checkIsDirect();
    }

    private int getTitleAddressMsg() {
        switch (titleId){
            case R.string.service_drive_address_search_from_title://대리운전
                return R.string.service_drive_map_from_title;
            case R.string.service_drive_address_search_to_title://대리운전
                return R.string.service_drive_map_to_title;
            case 0:
            default://그 외
                return R.string.map_title_3;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void getDataFromIntent() {
        try{
            selectAddressVO = (AddressVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_ADDR);
            titleId = getIntent().getIntExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID,0);
            msgId = getIntent().getIntExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID,0);
            isDirect = getIntent().getBooleanExtra(KeyNames.KEY_NAME_MAP_SEARCH_DIRECT_OPEN,false);
        }catch (Exception e){
            e.printStackTrace();
            selectAddressVO = null;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public void setObserver() {

        lgnViewModel.getPosition().observe(this, doubles -> {
            requestPosition[0] = doubles.get(0);
            requestPosition[1] = doubles.get(1);
            //위치에 대한 도로명주소 요청
            mapViewModel.reqPlayMapGeoItem(new ReverseGeocodingReqVO(requestPosition[0],requestPosition[1],1));
        });

        mapViewModel.getPlayMapGeoItem().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null){
                        mapViewModel.reqPlayMapPoiItemList(new AroundPOIReqVO("건물", requestPosition[0],requestPosition[1], 30, 3, 1, 0, 20));
                    }
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });

        mapViewModel.getPlayMapPoiItem().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    //빌딩이름이 획득 가능하면
                    if(result.data!=null&&result.data.size()>0){
                        updateAddressInfo(new Gson().fromJson(new Gson().toJson(result.data.get(0)),AddressVO.class));
                        break;
                    }
                default:
                    showProgressDialog(false);
                    //빌딩이름이 획득 불가능하면
                    PlayMapGeoItem item = mapViewModel.getPlayMapGeoItem().getValue().data;
                    if(item!=null){
                        updateAddressInfo(new Gson().fromJson(new Gson().toJson(item),AddressVO.class));
                    }
                    break;
            }
        });
//
//        btrViewModel.getRES_BTR_1008().observe(this, result -> {
//
//            switch (result.status) {
//
//                case LOADING:
//                    showProgressDialog(true);
//
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//
//                    if (result.data != null && result.data.getAsnList() != null) {
//                        setPosition(result.data.getAsnList(), result.data.getAsnList().get(0));
//                    }
//
//                    break;
//
//                default:
//                    showProgressDialog(false);
//                    break;
//            }
//        });
//
//        btrViewModel.getRES_BTR_1009().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
//                        setResult(ResultCodes.REQ_CODE_BTR.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_BTR, btrVO));
//                        finish();
//                        break;
//                    }
//                default:
//                    showProgressDialog(false);
//                    SnackBarUtil.show(this, getString(R.string.gm_bt06_snackbar_2));
//                    break;
//            }
//        });
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.tv_map_address_btn://선택
                if(selectAddressVO!=null){
                    exitPage(new Intent().putExtra(KeyNames.KEY_NAME_ADDR, selectAddressVO), ResultCodes.REQ_CODE_SERVICE_SOS_MAP.getCode());
                }
                break;
            case R.id.btn_my_position:
                lgnViewModel.setPosition(lgnViewModel.getMyPosition().get(0), lgnViewModel.getMyPosition().get(1));
                ui.pmvMapView.setMapCenterPoint(new PlayMapPoint(lgnViewModel.getMyPosition().get(0), lgnViewModel.getMyPosition().get(1)), 500);
                break;
            case R.id.tv_map_title_address:
                openSearchAddress();
                break;
        }

    }

    private void openSearchAddress() {
        Bundle bundle = new Bundle();
        bundle.putInt(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, titleId);
        bundle.putInt(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, msgId);
        showFragment(new SearchAddressHMNFragment(),bundle);
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


    private void reqMyLocation() {
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }

            runOnUiThread(() -> {
                if(selectAddressVO==null){
                    //기 선택된 위치 정보가 없으면 map을 내 위치로 초기화
                    initView(location.getLatitude(),location.getLongitude());
                    lgnViewModel.setPosition(location.getLatitude(), location.getLongitude());
                }else{
                    initView(selectAddressVO.getCenterLat(), selectAddressVO.getCenterLon());
                    lgnViewModel.setPosition(selectAddressVO.getCenterLat(), selectAddressVO.getCenterLon());
                }
                //내위치는 항상 저장
                lgnViewModel.setMyPosition(location.getLatitude(), location.getLongitude());
            });

        }, 5000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ResultCodes.REQ_CODE_BTR.getCode()) {
//            btrVO = (BtrVO) data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
//            List<BtrVO> list = btrViewModel.getRES_BTR_1008().getValue().data.getAsnList();
//            setPosition(list, btrVO);
//        } else if (resultCode == ResultCodes.REQ_CODE_ADDR_FILTER.getCode()) {
//            try {
//                fillerCd = data.getStringExtra(KeyNames.KEY_NAME_MAP_FILTER);
//            } catch (Exception e) {
//                fillerCd = "";
//            }
//            try {
//                addr = data.getStringExtra(KeyNames.KEY_NAME_MAP_CITY);
//            } catch (Exception e) {
//                addr = "";
//            }
//            try {
//                addrDtl = data.getStringExtra(KeyNames.KEY_NAME_MAP_GU);
//            } catch (Exception e) {
//                addrDtl = "";
//            }
//
//            if (!TextUtils.isEmpty(fillerCd) || !TextUtils.isEmpty(addr) || !TextUtils.isEmpty(addrDtl)) {
//                btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_CARLST_01_B01.getId(), String.valueOf(btrVO.getMapXcooNm()), String.valueOf(btrVO.getMapYcooNm()), "", "", ""));
//            }
//        }
    }
//
//    private void setPosition(PlayMapGeoItem item) {
//        if (bottomSelectBinding == null) {
//            setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_address, new ViewStub.OnInflateListener() {
//                @Override
//                public void onInflate(ViewStub viewStub, View inflated) {
//                    bottomSelectBinding = DataBindingUtil.bind(inflated);
//                    bottomSelectBinding.tvMapAddressBtn.setOnClickListener(onSingleClickListener);
//                    bottomSelectBinding.tvMapAddressTitle.setText(item.title);
//                    bottomSelectBinding.tvMapAddressAddress.setText(item.addrRoad);
//                }
//            });
//        } else {
//            bottomSelectBinding.tvMapAddressTitle.setText(item.title);
//            bottomSelectBinding.tvMapAddressAddress.setText(item.addrRoad);
//        }
//    }


    private void updateAddressInfo(final AddressVO addressVO) {
        selectAddressVO = addressVO;
        if (bottomSelectBinding == null) {
            setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_address, new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub viewStub, View inflated) {
                    bottomSelectBinding = DataBindingUtil.bind(inflated);
                    bottomSelectBinding.tvMapAddressBtn.setOnClickListener(onSingleClickListener);
//                    setViewAddresInfo(addressVO.getTitle(), addressVO.getCname(), addressVO.getAddrRoad());
                    setViewAddresInfo(addressVO);
                }
            });
        } else {
            setViewAddresInfo(addressVO);
        }
    }


    private void setViewAddresInfo(AddressVO addressVO){
        String[] addressInfo = getAddress(addressVO);
        bottomSelectBinding.tvMapAddressTitle.setText(addressInfo[1]);
        bottomSelectBinding.tvMapAddressTitle.setVisibility(TextUtils.isEmpty(addressInfo[1]) ? View.GONE : View.VISIBLE);
        bottomSelectBinding.tvMapAddressAddress.setText(addressInfo[0]);
        bottomSelectBinding.tvMapAddressAddress.setVisibility(TextUtils.isEmpty(addressInfo[0]) ? View.GONE : View.VISIBLE);
//        bottomSelectBinding.tvMapAddressTitle.setText(buildName+ (TextUtils.isEmpty(subBuildName) ? "" : " "+subBuildName));
//        bottomSelectBinding.tvMapAddressTitle.setVisibility(TextUtils.isEmpty(buildName) ? View.GONE : View.VISIBLE);
//        bottomSelectBinding.tvMapAddressAddress.setText(address);
//        bottomSelectBinding.tvMapAddressAddress.setVisibility(TextUtils.isEmpty(address) ? View.GONE : View.VISIBLE);
    }

    public void setAddressInfo(AddressVO addressVO){
        new Handler().postDelayed(() -> {
            if(addressVO!=null) {
                updateAddressInfo(addressVO);
                ui.pmvMapView.setMapCenterPoint(new PlayMapPoint(addressVO.getCenterLat(), addressVO.getCenterLon()), 500);
            }
        },500);
    }

    @Override
    public void onBackPressed() {
        List<SubFragment> fragments = getFragments();
        if(fragments!=null&&fragments.size()>0){
            hideFragment(fragments.get(0));
        }else{
            super.onBackPressed();
        }
    }
}
