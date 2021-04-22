package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityChargeReserveHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.service.view.ServiceRepairHistoryViewpagerAdapter;
import com.genesis.apps.ui.main.service.view.ServiceRepairReserveHistoryAdapter;

/**
 * @author hjpark
 * @brief 충전소 예약 내역
 */
public class ChargeReserveHistoryActivity extends SubActivity<ActivityChargeReserveHistoryBinding> {

    private static final int PAGE_SIZE = 20;
    private ServiceRepairReserveHistoryAdapter adapter;
    private REQViewModel reqViewModel;
    private VehicleVO mainVehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_reserve_history);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
    }

    private void initTabView() {
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {

        }
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
