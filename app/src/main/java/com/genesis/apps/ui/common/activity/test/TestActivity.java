package com.genesis.apps.ui.common.activity.test;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityServiceDriveReq1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class TestActivity extends SubActivity<ActivityServiceDriveReq1Binding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_drive_req_1);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void setViewModel() {
    }

    @Override
    public void setObserver() {
    }

    private void initView() {
    }


    @Override
    public void onClickCommon(View v) {

    }

}
