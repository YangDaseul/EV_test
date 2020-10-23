package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMaintenanceReserveBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MaintenanceReserveActivity extends SubActivity<ActivityMaintenanceReserveBinding> {
    private static final String TAG = MaintenanceReserveActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_reserve);
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void getDataFromIntent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
