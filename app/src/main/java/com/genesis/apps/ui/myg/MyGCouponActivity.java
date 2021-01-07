package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMygCouponBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * @author hjpark
 * @brief 혜택/쿠폰
 */
public class MyGCouponActivity extends SubActivity<ActivityMygCouponBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_coupon);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
    }

    @Override
    public void onClickCommon(View v) {
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void getDataFromIntent() {

    }
}
