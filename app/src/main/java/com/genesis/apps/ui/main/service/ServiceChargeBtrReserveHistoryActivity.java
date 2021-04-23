package com.genesis.apps.ui.main.service;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.databinding.ActivityServiceRepairHistoryBinding;
import com.genesis.apps.databinding.ItemTabRepairImageBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.service.view.ServiceChargeBtrHistoryViewpagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class ServiceChargeBtrReserveHistoryActivity extends GpsBaseActivity<ActivityServiceRepairHistoryBinding> {

    private ServiceChargeBtrHistoryViewpagerAdapter serviceChargeBtrHistoryViewpagerAdapter;
    private final int PAGE_NUM = 2;//현황예약, 이력
    private final int PAGE_NUM_APPLY_INFO = 2;  // 예약 완료 페이지 index
    private final int[] TAB_ID_LIST = {R.string.sm_r_rsv05_2, R.string.sm_r_rsv05_3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_repair_history);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        serviceChargeBtrHistoryViewpagerAdapter = new ServiceChargeBtrHistoryViewpagerAdapter(this, PAGE_NUM);
        ui.vpContents.setAdapter(serviceChargeBtrHistoryViewpagerAdapter);
        ui.vpContents.setUserInputEnabled(false);
        ui.vpContents.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.vpContents.setCurrentItem(0);
        ui.vpContents.setOffscreenPageLimit(PAGE_NUM);
        initTabView();

    }

    private void initTabView() {
        new TabLayoutMediator(ui.tlTabs, ui.vpContents, (tab, position) -> {
        }).attach();

//        for (int i = 0; i < PAGE_NUM; i++) {
//            TextView tabTitle = new TextView(this);
//            tabTitle.setText(TAB_ID_LIST[i]);
//            tabTitle.setTextAppearance(R.style.ServiceMainTabs);
//            ui.tlTabs.getTabAt(i).setCustomView(tabTitle);
//        }

        for(int i=0 ; i<TAB_ID_LIST.length; i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabRepairImageBinding itemTabRepairImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab_repair_image, null, false);
            final View view = itemTabRepairImageBinding.getRoot();
            itemTabRepairImageBinding.tvTab.setText(TAB_ID_LIST[i]);
            ui.tlTabs.getTabAt(i).setCustomView(view);
        }


//            ui.tlTabs.getTabAt(1).view.performClick();
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
