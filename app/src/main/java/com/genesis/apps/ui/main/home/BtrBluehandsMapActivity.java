package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomAddressBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class BtrBluehandsMapActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private BtrVO btrVO;
    private Double[] myPosition = new Double[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getDataFromIntent();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void getDataFromIntent() {
        try {
            btrVO = (BtrVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_BTR);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (btrVO==null||TextUtils.isEmpty(btrVO.getMapXcooNm())||TextUtils.isEmpty(btrVO.getMapYcooNm())) {
                exitPage("위치 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }else{
                setViewModel();
                setObserver();
                initView();
                checkEnableGPS();
            }
        }
    }

    private void checkEnableGPS() {
        if (!isGpsEnable()) {
            MiddleDialog.dialogGPS(this, () -> turnGPSOn(isGPSEnable -> {
                Log.v("test","value:"+isGPSEnable);
            }), () -> {
                //현대양재사옥위치
                myPosition = VariableType.DEFAULT_POSITION.clone();
            });
        } else {
            reqMyLocation();
        }
    }

    private void reqMyLocation() {
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }
//            location.setLatitude(37.5133402);
//            location.setLongitude(126.9333508);
            runOnUiThread(() -> {
                myPosition[0] = location.getLatitude();
                myPosition[1] = location.getLongitude();
            });

        }, 5000, GpsRetType.GPS_RETURN_HIGH, false);
    }


    @Override
    public void setViewModel() {
    }

    @Override
    public void setObserver() {

    }


    private void initView() {
        setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_address, (viewStub, inflated) -> {
            LayoutMapOverlayUiBottomAddressBinding binding = DataBindingUtil.bind(inflated);
            binding.tvMapAddressBtn.setVisibility(View.GONE);
            if(TextUtils.isEmpty(btrVO.getAsnNm())){
                binding.tvMapAddressTitle.setVisibility(View.GONE);
            }else {
                binding.tvMapAddressTitle.setText(btrVO.getAsnNm());
            }
            binding.tvMapAddressAddress.setText(btrVO.getPbzAdr()+ (!TextUtils.isEmpty(btrVO.getRepTn())  ? "\n"+PhoneNumberUtils.formatNumber(StringUtil.isValidString(btrVO.getRepTn()), Locale.getDefault().getCountry()) : ""));
            binding.tvMapAddressBtn.setVisibility(View.GONE);
        });
        ui.pmvMapView.initMap( Double.parseDouble(btrVO.getMapYcooNm()), Double.parseDouble(btrVO.getMapXcooNm()),DEFAULT_ZOOM);
        drawMarkerItem();
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);
        ui.lMapOverlayTitle.tvMapTitleText.setVisibility(View.GONE);
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_my_position:
                if(myPosition!=null&&myPosition[0]!=0&&myPosition[1]!=0)
                    ui.pmvMapView.setMapCenterPoint(new PlayMapPoint(myPosition[0], myPosition[1]), 500);
                break;
        }

    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem() {
        ui.pmvMapView.removeAllMarkerItem();

        PlayMapMarker markerItem = new PlayMapMarker();
        PlayMapPoint point = new PlayMapPoint(Double.parseDouble(btrVO.getMapYcooNm()), Double.parseDouble(btrVO.getMapXcooNm()));
        markerItem.setMapPoint(point);
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_pin_carcenter,null)).getBitmap());

        String strId = String.format("marker_%d", 0);
        ui.pmvMapView.addMarkerItem(strId, markerItem);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCodes.REQ_CODE_GPS.getCode()) {
            if(resultCode == RESULT_OK)
                reqMyLocation();
            else{
                //현대양재사옥위치
                myPosition = VariableType.DEFAULT_POSITION.clone();
            }
        }
    }

}
