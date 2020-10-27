package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityServiceDriveReqResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class ServiceDriveReqResultActivity extends SubActivity<ActivityServiceDriveReqResultBinding> {
    private static final String TAG = ServiceDriveReqResultActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_drive_req_result);
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
