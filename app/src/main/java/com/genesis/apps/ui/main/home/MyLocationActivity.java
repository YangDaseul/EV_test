package com.genesis.apps.ui.main.home;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.developers.ParkLocation;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.map.ReverseGeocodingReqVO;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.MapViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomAddressBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.extension.PlayMapGeoItem;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class MyLocationActivity extends GpsBaseActivity<ActivityMap2Binding> {
    private DevelopersViewModel developersViewModel;
    private MapViewModel mapViewModel;
    private List<String> vehiclePosition;
    private Double[] myPosition = new Double[2];
    private boolean isNormal=false;
    private AddressVO addressVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        ui.pmvMapView.initMap();
        setViewModel();
        setObserver();
        getDataFromIntent();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void getDataFromIntent() {
        try {
            vehiclePosition = new Gson().fromJson(getIntent().getStringExtra(KeyNames.KEY_NAME_VEHICLE_LOCATION), new TypeToken<List<String>>(){}.getType());
            isNormal = getIntent().getBooleanExtra(KeyNames.KEY_NAME_LOCATION_OTHERS, false);
            addressVO = (AddressVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_ADDR);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(!isNormal) {
                ui.btnMyPosition.setVisibility(View.VISIBLE);
                if (vehiclePosition == null || vehiclePosition.size() != 2) {
                    reqCarInfoToDevelopers(getVin());
                } else {
                    reqMyLocation();
                }
            }else{
                ui.btnMyPosition.setVisibility(View.GONE);
                if(addressVO!=null) {
                    PlayMapGeoItem item = new PlayMapGeoItem();
                    item.addr = addressVO.getAddr();
                    item.title = addressVO.getCname();
                    myPosition[0] = addressVO.getCenterLat();
                    myPosition[1] = addressVO.getCenterLon();
                    initView(item, addressVO.getCenterLat()+"", addressVO.getCenterLon()+"");
                }
            }

        }
    }

    private String getVin(){
        String vin="";

        try{
            vin = developersViewModel.getMainVehicleSimplyFromDB().getVin();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(vin)) {
            exitPage("위치 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
        return vin;
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        developersViewModel = new ViewModelProvider(this).get(DevelopersViewModel.class);
    }

    @Override
    public void setObserver() {

        developersViewModel.getRES_PARKLOCATION().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getLat() != 0 && result.data.getLon() != 0) {
                        showProgressDialog(false,  !isNormal ? PROGRESS_TYPE_LOCATION : PROGRESS_TYPE_NORMAL);
                        vehiclePosition = new ArrayList<>();
                        vehiclePosition.add(result.data.getLat()+"");
                        vehiclePosition.add(result.data.getLon()+"");
                        reqMyLocation();
                        break;
                    }
                default:
                    showProgressDialog(false,  !isNormal ? PROGRESS_TYPE_LOCATION : PROGRESS_TYPE_NORMAL);
                    exitPage("위치 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.RES_CODE_NETWORK.getCode());
                    break;
            }
        });

        mapViewModel.getPlayMapGeoItem().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true,  !isNormal ? PROGRESS_TYPE_LOCATION : PROGRESS_TYPE_NORMAL);


                    break;
                case SUCCESS:
                    showProgressDialog(false,  !isNormal ? PROGRESS_TYPE_LOCATION : PROGRESS_TYPE_NORMAL);

                    if(result.data!=null){
                        initView(result.data,vehiclePosition.get(0),vehiclePosition.get(1));
                    }

                    break;
                default:
                    showProgressDialog(false,  !isNormal ? PROGRESS_TYPE_LOCATION : PROGRESS_TYPE_NORMAL);

                    break;
            }


        });
    }

    private void initView(PlayMapGeoItem item, String lat, String lon ) {
        setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_address, (viewStub, inflated) -> {
            LayoutMapOverlayUiBottomAddressBinding binding = DataBindingUtil.bind(inflated);
            binding.tvMapAddressBtn.setVisibility(View.GONE);
            if(TextUtils.isEmpty(item.title)){
                binding.tvMapAddressTitle.setVisibility(View.GONE);
            }else {
                binding.tvMapAddressTitle.setVisibility(View.VISIBLE);
                binding.tvMapAddressTitle.setText(item.title);
            }
            binding.tvMapAddressAddress.setText(item.addr);
        });
        ui.pmvMapView.initMap( Double.parseDouble(lat), Double.parseDouble(lon),DEFAULT_ZOOM);
        drawMarkerItem(lat, lon);
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);
        ui.lMapOverlayTitle.tvMapTitleText.setVisibility(View.GONE);
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_my_position://선택
//                ui.pmvMapView.initMap(myPosition[0], myPosition[1], 17);
                ui.pmvMapView.setMapCenterPoint(new PlayMapPoint(myPosition[0], myPosition[1]), 500);
                break;
        }

    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem(String mapYcooNm, String mapXcooNm) {

        ui.pmvMapView.removeAllMarkerItem();

        PlayMapMarker markerItem = new PlayMapMarker();
//        PlayMapPoint point = mapView.getMapCenterPoint();
        PlayMapPoint point = new PlayMapPoint(Double.parseDouble(mapYcooNm), Double.parseDouble(mapXcooNm));
        markerItem.setMapPoint(point);
//        markerItem.setCalloutTitle("제목");
//        markerItem.setCalloutSubTitle("내용");
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_pin_carcenter,null)).getBitmap());

        String strId = String.format("marker_%d", 0);
        ui.pmvMapView.addMarkerItem(strId, markerItem);
    }


    private void reqMyLocation() {
        showProgressDialog(true,  !isNormal ? PROGRESS_TYPE_LOCATION : PROGRESS_TYPE_NORMAL);
        findMyLocation(location -> {
            showProgressDialog(false,  !isNormal ? PROGRESS_TYPE_LOCATION : PROGRESS_TYPE_NORMAL);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }

            runOnUiThread(() -> {
                //현재 단말기 위치 정보 저장
                myPosition[0] = location.getLatitude();
                myPosition[1] = location.getLongitude();
                //차량 위치에 대한 주소 정보 요청
                mapViewModel.reqPlayMapGeoItem(new ReverseGeocodingReqVO(Double.parseDouble(vehiclePosition.get(0)),Double.parseDouble(vehiclePosition.get(1)),0));
            });

        }, 5000, GpsRetType.GPS_RETURN_FIRST, false);
    }


    private void reqCarInfoToDevelopers(String vin) {
        String carId = developersViewModel.getCarId(vin);
        if (!TextUtils.isEmpty(carId)) {
            developersViewModel.reqParkLocation(new ParkLocation.Request(carId));
        }else{
            exitPage("위치 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }
}
