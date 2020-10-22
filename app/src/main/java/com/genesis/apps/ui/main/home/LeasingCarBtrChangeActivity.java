package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityMap2Binding;
import com.genesis.apps.databinding.LayoutMapOverlayUiBottomSelectBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.shape.PlayMapMarker;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class LeasingCarBtrChangeActivity extends GpsBaseActivity<ActivityMap2Binding> {

    private BTRViewModel btrViewModel;
    private LGNViewModel lgnViewModel;
    private BtrVO btrVO = null;
    private LayoutMapOverlayUiBottomSelectBinding bottomSelectBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        if(btrVO==null) {
            reqMyLocation();
        }else{
            btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_CARLST_01_B01.getId(), String.valueOf(btrVO.getMapXcooNm()), String.valueOf(btrVO.getMapYcooNm()), "", "",  ""));
        }
    }

    private void initView() {
        ui.btnMyPosition.setOnClickListener(onSingleClickListener);
        ui.pmvMapView.onMapTouchUpListener((motionEvent, makerList) -> {

            if(makerList!=null&&makerList.size()>0) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        BtrVO btrVO = btrViewModel.getBtrVO(makerList.get(0).getId());
                        if(btrVO!=null){
                            ui.pmvMapView.removeAllMarkerItem();
                            setPosition(btrViewModel.getRES_BTR_1008().getValue().data.getAsnList(),btrVO);
                        }

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
        try {
            btrVO = (BtrVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_BTR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {

        btrViewModel.getRES_BTR_1008().observe(this, result -> {

            switch (result.status){

                case LOADING:
                    showProgressDialog(true);

                    break;
                case SUCCESS:
                    showProgressDialog(false);

                    if(result.data!=null&&result.data.getAsnList()!=null){
                        setPosition(result.data.getAsnList(),result.data.getAsnList().get(0));
                    }

                    break;

                default:
                    showProgressDialog(false);
                    break;
            }


        });

    }



    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.tv_map_select_btn1://선택
                setResult(ResultCodes.REQ_CODE_BTR.getCode(), new Intent().putExtra(KeyNames.KEY_NAME_BTR, btrVO));
                finish();
                break;
            case R.id.tv_map_select_btn2://목록
                if(btrViewModel.getRES_BTR_1008().getValue()!=null&&btrViewModel.getRES_BTR_1008().getValue().data.getAsnList()!=null&&btrViewModel.getRES_BTR_1008().getValue().data.getAsnList().size()>0){
                    startActivitySingleTop(new Intent(this, BtrBluehandsListActivity.class).putExtra(KeyNames.KEY_NAME_BTR, btrViewModel.getRES_BTR_1008().getValue().data), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
            case R.id.btn_my_position:
                Double[] position = lgnViewModel.getPosition();
                ui.pmvMapView.initMap(position[0], position[1], 17);
                break;
        }

    }

    /**
     * drawMarkerItem 지도에 마커를 그린다.
     */
    public void drawMarkerItem(BtrVO btrVO,int iconId) {
        PlayMapMarker markerItem = new PlayMapMarker();
//        PlayMapPoint point = mapView.getMapCenterPoint();
        PlayMapPoint point = new PlayMapPoint(Double.parseDouble(btrVO.getMapXcooNm()), Double.parseDouble(btrVO.getMapYcooNm()));
        markerItem.setMapPoint(point);
//        markerItem.setCalloutTitle("제목");
//        markerItem.setCalloutSubTitle("내용");
        markerItem.setCanShowCallout(false);
        markerItem.setAutoCalloutVisible(false);
        markerItem.setIcon(((BitmapDrawable)getResources().getDrawable(iconId,null)).getBitmap());


        String strId = btrVO.getAsnCd();
        ui.pmvMapView.addMarkerItem(strId, markerItem);
    }


    private void reqMyLocation(){
        showProgressDialog(true);
        findMyLocation(location -> {
           showProgressDialog(false);
            if (location == null) {
                return;
            }
            lgnViewModel.setPosition(location.getLatitude(),location.getLongitude());

            runOnUiThread(() -> new Handler().postDelayed(() -> btrViewModel.reqBTR1008(new BTR_1008.Request(APPIAInfo.GM_CARLST_01_B01.getId(), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "", "",  VariableType.BTR_FILTER_CODE_A)),0));

        },5000);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ResultCodes.REQ_CODE_BTR.getCode()){
            btrVO = (BtrVO)data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
            List<BtrVO> list = btrViewModel.getRES_BTR_1008().getValue().data.getAsnList();
            setPosition(list,btrVO);
        }

    }

    private void setPosition(List<BtrVO> list, BtrVO btrVO) {
        this.btrVO = btrVO;
        if(bottomSelectBinding==null) {
            setViewStub(R.id.vs_map_overlay_bottom_box, R.layout.layout_map_overlay_ui_bottom_select, new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub viewStub, View inflated) {
                    bottomSelectBinding = DataBindingUtil.bind(inflated);
                    bottomSelectBinding.setActivity(LeasingCarBtrChangeActivity.this);
                    bottomSelectBinding.tvMapSelectBtn1.setText(R.string.bt06_15);
                    bottomSelectBinding.setData(btrVO);
                }
            });
        }else{
            bottomSelectBinding.setData(btrVO);
        }

        for(int i=0; i<list.size();i++){
            if(btrVO.getAsnCd().equalsIgnoreCase(list.get(i).getAsnCd())){
                drawMarkerItem(list.get(i), R.drawable.ic_pin_carcenter);
            }else{
                drawMarkerItem(list.get(i), R.drawable.ic_pin);
            }
        }

        ui.pmvMapView.initMap(Double.parseDouble(btrVO.getMapXcooNm()), Double.parseDouble(btrVO.getMapYcooNm()), 17);

    }
}
