package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityLeasingCarInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * @author hjpark
 * @brief 렌트/리스 실 운행자 등록 [알려드립니다]
 */
public class LeasingCarInfoActivity extends SubActivity<ActivityLeasingCarInfoBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leasing_car_info);
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
