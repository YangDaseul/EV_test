package com.genesis.apps.ui.main.store;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.FragmentServiceBinding;
import com.genesis.apps.databinding.FragmentStoreBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.service.ServiceAutocare4ResultActivity;
import com.genesis.apps.ui.main.service.ServiceHomeToHome4ResultActivity;
import com.genesis.apps.ui.main.service.ServiceRepair2ApplyActivity;
import com.genesis.apps.ui.main.service.ServiceRepair4ResultActivity;
import com.genesis.apps.ui.main.service.ServiceViewpagerAdapter;
import com.genesis.apps.ui.myg.MyGEntranceActivity;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


public class FragmentStore extends SubFragment<FragmentStoreBinding> {
    private static final String TAG = FragmentStore.class.getSimpleName();

//    private final int PAGE_NUM = 3;//정비 세차 대리
//    private final int[] TAB_ID_LIST = {R.string.sm01_header_1, R.string.sm01_header_2, R.string.sm01_header_3};
//
//    public FragmentStateAdapter serviceTabAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_store);
    }

    private void initView() {
//        serviceTabAdapter = new ServiceViewpagerAdapter(this, PAGE_NUM);
//        me.vpServiceContentsViewPager.setAdapter(serviceTabAdapter);
//        me.vpServiceContentsViewPager.setUserInputEnabled(false);
//        setTabView();
//
//        //ViewPager Setting
//        me.vpServiceContentsViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//        me.vpServiceContentsViewPager.setCurrentItem(0);
//        me.vpServiceContentsViewPager.setOffscreenPageLimit(PAGE_NUM);
    }

    //탭 헤더 세팅
    private void setTabView() {
//        new TabLayoutMediator(me.tlServiceTabs, me.vpServiceContentsViewPager, (tab, position) -> {
//        }).attach();
//
//        for (int i = 0; i < PAGE_NUM; i++) {
//            TextView tabTitle = new TextView(getActivity());
//            tabTitle.setText(TAB_ID_LIST[i]);
//            tabTitle.setTextAppearance(R.style.ServiceMainTabs);
//            me.tlServiceTabs.getTabAt(i).setCustomView(tabTitle);
//        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initView();
//        me.setLifecycleOwner(getViewLifecycleOwner());
//        me.setFragment(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onRefresh() {
        Log.e("onResume", "onReusme store");
        ((MainActivity) getActivity()).setGNB(false, 0, View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
