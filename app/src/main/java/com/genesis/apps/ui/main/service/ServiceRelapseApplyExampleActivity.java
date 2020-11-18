package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityServiceRelapseApplyExampleBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class ServiceRelapseApplyExampleActivity extends SubActivity<ActivityServiceRelapseApplyExampleBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_relapse_apply_example);
        getDataFromIntent();
        setViewModel();
        setObserver();
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
}
