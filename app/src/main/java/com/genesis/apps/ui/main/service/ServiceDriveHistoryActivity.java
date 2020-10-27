package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityServiceDriveHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class ServiceDriveHistoryActivity extends SubActivity<ActivityServiceDriveHistoryBinding> {
    private static final String TAG = ServiceDriveHistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_drive_history);
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
        //TODO 테스트중에는 데이터 없다
        ui.rvServiceDriveHistoryList.setVisibility(View.GONE);
    }

}
