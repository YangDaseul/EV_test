package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.databinding.ActivityChargePlaceListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * Class Name : ChargePlaceListActivity
 * 충전소 목록 Activity.
 *
 * @author Ki-man Kim
 * @since 2021-03-24
 */
public class ChargePlaceListActivity extends SubActivity<ActivityChargePlaceListBinding> {

    private String title;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_place_list);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initialize();
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {

    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(ChargePlaceListActivity.this);

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
        // TODO 충전소 구분값을 전달 받아야 함.
        Intent getIntent = getIntent();
        title = getIntent.getStringExtra(KeyNames.KEY_NAME_CHARGE_TYPE);
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    /**
     * 초기화 함수.
     */
    private void initialize() {
        if (!TextUtils.isEmpty(title)) {
            ui.lTitle.setValue(title);
        }
    }

    /**
     * 충전소 목록 조회 함수.
     */
    public void reqGetChargePlaceList() {

    }
} // end of class ChargePlaceListActivity
