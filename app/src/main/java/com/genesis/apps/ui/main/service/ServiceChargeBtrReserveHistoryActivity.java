package com.genesis.apps.ui.main.service;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityServiceChargeBtrHistoryBinding;
import com.genesis.apps.databinding.ItemTabRepairImageBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.service.view.ServiceChargeBtrHistoryViewpagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class ServiceChargeBtrReserveHistoryActivity extends GpsBaseActivity<ActivityServiceChargeBtrHistoryBinding> {

    private ServiceChargeBtrHistoryViewpagerAdapter serviceChargeBtrHistoryViewpagerAdapter;
    private final int PAGE_NUM = 2;//현황예약, 이력
    private final int TAB_ID_HIST = 1;// 이력
    private final int[] TAB_ID_LIST = {R.string.sm_r_rsv05_2, R.string.sm_r_rsv05_3};

    private CHBViewModel chbViewModel;

    private boolean isAfterBackground = false;

    public boolean isAfterBackground() {
        return isAfterBackground;
    }

    public void setAfterBackground(boolean isAfterBackground) {
        this.isAfterBackground = isAfterBackground;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LJEUN", "ServiceChargeBtrReserveHistoryActivity :: onCreate()");
        setContentView(R.layout.activity_service_charge_btr_history);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        isAfterBackground = true;
    }

    private void initView() {
        Log.d("LJEUN", "ServiceChargeBtrReserveHistoryActivity :: initView()");

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

        for(int i=0 ; i<TAB_ID_LIST.length; i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabRepairImageBinding itemTabRepairImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab_repair_image, null, false);
            final View view = itemTabRepairImageBinding.getRoot();
            itemTabRepairImageBinding.tvTab.setText(TAB_ID_LIST[i]);
            ui.tlTabs.getTabAt(i).setCustomView(view);
        }

    }

    public void moveChargeBtrTab(int tabId) {
        Log.d("LJEUN", "moveChargeBtrTab :: tabId(" + tabId + ")");
        ui.tlTabs.getTabAt(tabId).view.performClick();
    }

    @Override
    public void onClickCommon(View v) {
    }

    @Override
    public void setViewModel() {
        try {
            chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
        } catch (Exception e) {

        }
    }

    @Override
    public void setObserver() {
        chbViewModel.getRES_CHB_1024().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getRtCd().equals(BaseResponse.RETURN_CODE_SUCC)) {
                        SnackBarUtil.show(this, getString(R.string.service_charge_btr_popup_msg_01));
                        moveChargeBtrTab(VariableType.SERVICE_CHARGE_BTR_HIST_TAB_ID);
                        showProgressDialog(false);
                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });

        chbViewModel.getRES_CHB_1022().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000")) {
                        break;
                    }
                default:
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.service_charge_btr_err_16);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });
    }
    @Override
    public void getDataFromIntent() {

    }
}
