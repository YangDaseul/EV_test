package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;

import androidx.databinding.DataBindingUtil;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomSelectBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

public class BtrChangeActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private BtrVO btrVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void getDataFromIntent() {
        try {
            btrVO = (BtrVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_BTR);
            if (btrVO==null||TextUtils.isEmpty(btrVO.getMapXcooNm())||TextUtils.isEmpty(btrVO.getMapYcooNm())) {
                exitPage("위치 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitPage("위치 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void setViewModel() {
    }

    @Override
    public void setObserver() {

    }

    private void initView() {
        setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_select, new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub viewStub, View inflated) {
                LayoutMapOverlayUiBottomSelectBinding binding = DataBindingUtil.bind(inflated);
                binding.setActivity(BtrChangeActivity.this);
                binding.setData(btrVO);
                binding.tvMapSelectBtn1.setText(R.string.bt06_14);
            }
        });
        ui.pmvMapView.initMap( Double.parseDouble(btrVO.getMapXcooNm()), Double.parseDouble(btrVO.getMapYcooNm()),17);
        drawMarkerItem(btrVO.getMapXcooNm(),btrVO.getMapYcooNm());
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.tv_map_select_btn1://선택

                break;
            case R.id.tv_map_select_btn2://목록
                startActivitySingleTop(new Intent(this, BtrBluehandsListActivity.class).putExtra(KeyNames.KEY_NAME_BTR, btrVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }

    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem(String mapXcooNm, String mapYcooNm) {
        PlayMapMarker markerItem = new PlayMapMarker();
//        PlayMapPoint point = mapView.getMapCenterPoint();
        PlayMapPoint point = new PlayMapPoint(Double.parseDouble(mapXcooNm), Double.parseDouble(mapYcooNm));
        markerItem.setMapPoint(point);
//        markerItem.setCalloutTitle("제목");
//        markerItem.setCalloutSubTitle("내용");
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_pin_carcenter,null)).getBitmap());

        String strId = String.format("marker_%d", 0);
        ui.pmvMapView.addMarkerItem(strId, markerItem);
    }

}
