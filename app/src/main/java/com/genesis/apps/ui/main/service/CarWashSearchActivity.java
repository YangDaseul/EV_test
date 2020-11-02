package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomSonaxBranchBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.List;

public class CarWashSearchActivity extends GpsBaseActivity<ActivityMap2Binding> {

    private WSHViewModel wshViewModel;
    private LGNViewModel lgnViewModel;
    private WashBrnVO washBrnVO;
    private LayoutMapOverlayUiBottomSonaxBranchBinding sonaxBranchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        setViewModel();
        setObserver();
        initView();
        reqMyLocation();
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
        //todo impl
        ui.setLifecycleOwner(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
        //todo maybe impl;

        lgnViewModel.getPosition().observe(this, doubles -> {
            //todo 뭐해야되나
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == ResultCodes.REQ_CODE_BTR.getCode()) {
//            washBrnVO = (WashBrnVO) data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
//            List<WashBrnVO> list = btrViewModel.getRES_BTR_1008().getValue().data.getAsnList();
//            setPosition(list, washBrnVO);
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
//                btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_CARLST_01_B01.getId(), String.valueOf(washBrnVO.getMapXcooNm()), String.valueOf(washBrnVO.getMapYcooNm()), "", "", ""));
//            }
//        }
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.tv_map_sonax_branch_reserve_btn://예약
                //todo impl
                break;

            case R.id.btn_my_position:
                //지도 초기화(현재 위치)
                initMapWithMyPosition();
                break;

            case R.id.tv_map_title_text:
                //todo impl
                //지역선택 액티비티
                break;
        }
    }

    @Override
    public void onBackPressed() {
        List<SubFragment> fragments = getFragments();
        if (fragments != null && fragments.size() > 0) {
            hideFragment(fragments.get(0));
        } else {
            super.onBackPressed();
        }
    }

    private void initView() {
        ui.lMapOverlayTitle.tvMapTitleText.setOnClickListener(onSingleClickListener);
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);
        ui.pmvMapView.onMapTouchUpListener((motionEvent, makerList) -> {

            if (makerList != null && makerList.size() > 0) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }

            }
        });
    }

    //지도 초기화(현재 위치)
    private void initMapWithMyPosition() {
        ui.pmvMapView.initMap(lgnViewModel.getMyPosition().get(0), lgnViewModel.getMyPosition().get(1), 17);
    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem(WashBrnVO washBrnVO, int iconId) {
        PlayMapMarker markerItem = new PlayMapMarker();
//        PlayMapPoint point = mapView.getMapCenterPoint();
        PlayMapPoint point = new PlayMapPoint(Double.parseDouble(washBrnVO.getBrnhX()), Double.parseDouble(washBrnVO.getBrnhY()));
        markerItem.setMapPoint(point);
//        markerItem.setCalloutTitle("제목");
//        markerItem.setCalloutSubTitle("내용");
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable) getResources().getDrawable(iconId, null)).getBitmap());


        String strId = washBrnVO.getCmpyCd();//todo 업체코드 들어가는 게 맞나 확인 지점코드로 해야되나?
        ui.pmvMapView.addMarkerItem(strId, markerItem);
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
                //내 위치를 기본값으로 사용
                lgnViewModel.setPosition(location.getLatitude(), location.getLongitude());

                //내위치는 항상 저장
                lgnViewModel.setMyPosition(location.getLatitude(), location.getLongitude());

                //지도를 내 위치로 초기화
                initMapWithMyPosition();
            });

        }, 5000);
    }


    private void setPosition(List<WashBrnVO> list, WashBrnVO washBrnVO) {
        this.washBrnVO = washBrnVO;
        if (sonaxBranchBinding == null) {
            setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_sonax_branch, new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub viewStub, View inflated) {
                    sonaxBranchBinding = DataBindingUtil.bind(inflated);
                    sonaxBranchBinding.setActivity(CarWashSearchActivity.this);
                    sonaxBranchBinding.setData(washBrnVO);
                }
            });
        } else {
            sonaxBranchBinding.setData(washBrnVO);
        }
//
//        for (int i = 0; i < list.size(); i++) {
//            if (washBrnVO.getAsnCd().equalsIgnoreCase(list.get(i).getAsnCd())) {
//                drawMarkerItem(list.get(i), R.drawable.ic_pin_carcenter);
//            } else {
//                drawMarkerItem(list.get(i), R.drawable.ic_pin);
//            }
//        }

        ui.pmvMapView.initMap(Double.parseDouble(washBrnVO.getBrnhX()), Double.parseDouble(washBrnVO.getBrnhY()), 17);

    }
}
