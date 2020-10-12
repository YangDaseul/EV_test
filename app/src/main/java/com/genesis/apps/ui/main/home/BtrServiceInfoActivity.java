package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityBtrServiceInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class BtrServiceInfoActivity extends SubActivity<ActivityBtrServiceInfoBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btr_service_info);
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
