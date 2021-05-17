package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.databinding.ActivityServiceChargeBtrFailBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ServiceChargeBtrFailActivity extends SubActivity<ActivityServiceChargeBtrFailBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(R.layout.activity_service_charge_btr_fail);
        getDataFromIntent();
        setViewModel();
        setObserver();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void getDataFromIntent() {
    }

    @Override
    public void onBackButton() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        exitPage(new Intent(), ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FAIL.getCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
