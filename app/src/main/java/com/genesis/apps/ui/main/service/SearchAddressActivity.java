package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.BTR_1008;
import com.genesis.apps.comm.model.gra.api.BTR_1009;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomAddressBinding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomSelectBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.home.BluehandsFilterFragment;
import com.genesis.apps.ui.main.home.BtrBluehandsListActivity;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.extension.PlayMapGeoItem;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class SearchAddressActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private MapViewModel mapViewModel;
    private LGNViewModel lgnViewModel;
    private LayoutMapOverlayUiBottomAddressBinding bottomSelectBinding;
    private String addr = "";
    private String addrDtl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        reqMyLocation();
    }

    private void initView() {
        ui.lMapOverlayTitle.tvMapTitleText.setVisibility(View.GONE);
        ui.lMapOverlayTitle.tvMapTitleAddress.setVisibility(View.VISIBLE);
        ui.lMapOverlayTitle.tvMapTitleAddress.setOnClickListener(onSingleClickListener);
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);
        ui.pmvMapView.onMapTouchUpListener((motionEvent, makerList) -> {

            if (makerList != null && makerList.size() > 0) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
//                        BtrVO btrVO = btrViewModel.getBtrVO(makerList.get(0).getId());
//                        if (btrVO != null) {
//                            ui.pmvMapView.removeAllMarkerItem();
//                            setPosition(btrViewModel.getRES_BTR_1008().getValue().data.getAsnList(), btrVO);
//                        }
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public void setObserver() {

//        lgnViewModel.getPosition().observe(this, doubles -> {
//            if (btrVO == null) {
//                fillerCd = VariableType.BTR_FILTER_CODE_A;
//            } else {
//                fillerCd = "";
//            }
//
//            //기본 버틀러 정보가 없을 때는 제네시스 전담으로 기본 필터 전달
//            btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_CARLST_01_B01.getId(), String.valueOf(doubles.get(0)), String.valueOf(doubles.get(1)), "", "", fillerCd));
//        });
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
            case R.id.tv_map_select_btn1://선택
//                //기타 렌트리스.
//                setResult(ResultCodes.REQ_CODE_BTR.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_BTR, btrVO));
//                finish();
                break;
            case R.id.btn_my_position:
                ui.pmvMapView.initMap(lgnViewModel.getMyPosition().get(0), lgnViewModel.getMyPosition().get(1), 17);
                break;
            case R.id.tv_map_title_text:
                showFragment(new BluehandsFilterFragment());
//                startActivitySingleTop(new Intent(this, BtrBluehandsFilterActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }

    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem(PlayMapGeoItem item, int iconId) {
        PlayMapMarker markerItem = new PlayMapMarker();
//        PlayMapPoint point = mapView.getMapCenterPoint();
        PlayMapPoint point = new PlayMapPoint(item.centerLat, item.centerLon);
        markerItem.setMapPoint(point);
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable) getResources().getDrawable(iconId, null)).getBitmap());
        ui.pmvMapView.addMarkerItem("center", markerItem);
    }


    private void reqMyLocation() {
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }

            runOnUiThread(() -> {
                //버틀러 정보가 없으면 내 위치를 기본값으로 사용
                lgnViewModel.setPosition(location.getLatitude(), location.getLongitude());

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

    private void setPosition(PlayMapGeoItem item) {
        if (bottomSelectBinding == null) {
            setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_address, new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub viewStub, View inflated) {
                    bottomSelectBinding = DataBindingUtil.bind(inflated);
                    bottomSelectBinding.tvMapAddressBtn.setOnClickListener(onSingleClickListener);
                    bottomSelectBinding.tvMapAddressTitle.setText(item.title);
                    bottomSelectBinding.tvMapAddressAddress.setText(item.addrRoad);
                }
            });
        } else {
            bottomSelectBinding.tvMapAddressTitle.setText(item.title);
            bottomSelectBinding.tvMapAddressAddress.setText(item.addrRoad);
        }

        drawMarkerItem(item, R.drawable.ic_pin_carcenter);
        ui.pmvMapView.initMap(item.centerLat, item.centerLon, 17);
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
