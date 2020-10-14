package com.genesis.apps.ui.main.home;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.databinding.ActivityMapBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.Locale;

public class BtrBluehandsMapActivity extends SubActivity<ActivityMapBinding> {
    private BtrVO btrVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
        ui.tvAsnm.setText(btrVO.getAsnNm());
        ui.tvAddr.setText(btrVO.getPbzAdr());
        ui.tvReptn.setText(PhoneNumberUtils.formatNumber(btrVO.getCelphNo(), Locale.getDefault().getCountry()));
        ui.map.initMap( Double.parseDouble(btrVO.getMapXcooNm()), Double.parseDouble(btrVO.getMapYcooNm()),17);
        drawMarkerItem();
    }


    @Override
    public void onClickCommon(View v) {

    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem() {
        PlayMapMarker markerItem = new PlayMapMarker();
//        PlayMapPoint point = mapView.getMapCenterPoint();
        PlayMapPoint point = new PlayMapPoint(Double.parseDouble(btrVO.getMapXcooNm()), Double.parseDouble(btrVO.getMapYcooNm()));
        markerItem.setMapPoint(point);
//        markerItem.setCalloutTitle("제목");
//        markerItem.setCalloutSubTitle("내용");
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_pin_carcenter,null)).getBitmap());

        String strId = String.format("marker_%d", 0);
        ui.map.addMarkerItem(strId, markerItem);
    }

}
