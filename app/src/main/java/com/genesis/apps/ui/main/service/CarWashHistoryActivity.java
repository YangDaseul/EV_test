package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityCarWashHistoryBinding;
import com.genesis.apps.databinding.ActivityServiceDriveHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class CarWashHistoryActivity extends SubActivity<ActivityCarWashHistoryBinding> {
    private static final String TAG = CarWashHistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_history);
        init();
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

    private void init() {
        //TODO : 필요한 거
    }

}
