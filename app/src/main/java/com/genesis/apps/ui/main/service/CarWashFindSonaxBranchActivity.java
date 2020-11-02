package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityCarWashFindSonaxBranchBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class CarWashFindSonaxBranchActivity extends SubActivity<ActivityCarWashFindSonaxBranchBinding> {
    private static final String TAG = CarWashFindSonaxBranchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_find_sonax_branch);

        getDataFromIntent();
//        setAdapter();

    }

    @Override
    public void setViewModel() {
        //do nothing
    }

    @Override
    public void setObserver() {
        //do nothing
    }

    @Override
    public void getDataFromIntent() {
        //do nothing
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon()");
        int id = v.getId();

        switch (id) {
//            case :
                //todo impl;
//                break;
            default:
                //do nothing
                break;
        }

    }

}
