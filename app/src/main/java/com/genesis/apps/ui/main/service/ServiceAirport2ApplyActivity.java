package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.databinding.ActivityServiceAirport2ApplyBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * @author hjpark
 * @brief 에어포트2단계
 */
public class ServiceAirport2ApplyActivity extends SubActivity<ActivityServiceAirport2ApplyBinding> {

    private RepairTypeVO repairTypeVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_airport_2_apply);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        ui.lRpar.tvRparTypNm.setText(repairTypeVO!=null&&!TextUtils.isEmpty(repairTypeVO.getRparTypNm()) ? repairTypeVO.getRparTypNm() : "--");
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_call://다음
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL+ui.btnCall.getTag().toString()));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setActivity(this);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
        VehicleVO mainVehicle=null;
        try {
            mainVehicle = (VehicleVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_VEHICLE_VO);
            repairTypeVO = (RepairTypeVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(mainVehicle!=null&&!TextUtils.isEmpty(mainVehicle.getMdlNm())&& (mainVehicle.getMdlNm().equalsIgnoreCase("GV90")||mainVehicle.getMdlNm().equalsIgnoreCase("EQ900"))){
                ui.btnCall.setTag(getString(R.string.word_home_25));
            }else{
                ui.btnCall.setTag(getString(R.string.word_home_14));
            }
        }
    }
}
