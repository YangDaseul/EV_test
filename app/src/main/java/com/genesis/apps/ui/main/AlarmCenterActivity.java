package com.genesis.apps.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.PushCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.NOT_0001;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityAlarmCenterBinding;
import com.genesis.apps.databinding.ItemTabAlarmBinding;
import com.genesis.apps.databinding.ItemTabBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author hjpark
 * @brief 알림센터
 */
public class AlarmCenterActivity extends SubActivity<ActivityAlarmCenterBinding> {
    private CMNViewModel cmnViewModel;
    private AlarmCenterRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_center);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        cmnViewModel.reqNOT0001(new NOT_0001.Request(APPIAInfo.ALRM01.getId(), ""));
    }

    private void initView() {
        ui.lTitle.btnEtc.setOnClickListener(onSingleClickListener);
        adapter = new AlarmCenterRecyclerAdapter(onSingleClickListener);
        ui.rvNoti.setLayoutManager(new LinearLayoutManager(this));
        ui.rvNoti.setHasFixedSize(true);
        ui.rvNoti.setAdapter(adapter);
        initTabView();
    }

    private void initTabView() {
//        ui.tabs.addTab(ui.tabs.newTab().setText(R.string.alrm01_2));
        for (String codeNm : PushCodes.getPushListNm()) {
//            ui.tabs.addTab(ui.tabs.newTab().setText(codeNm));
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabAlarmBinding itemTabAlarmBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab_alarm, null, false);
            final View view = itemTabAlarmBinding.getRoot();
            itemTabAlarmBinding.tvTab.setText(codeNm);
            ui.tabs.addTab(ui.tabs.newTab().setCustomView(view));
        }

        ui.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getList(PushCodes.findCode(((ItemTabAlarmBinding)DataBindingUtil.bind(tab.getCustomView())).tvTab.getText().toString()).getCateCd(), "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getList(String cateCd, String search) {
        showProgressDialog(true);
        try {
            List<NotiInfoVO> list = cmnViewModel.getNotiInfoFromDB(cateCd, search);
            adapter.setRows(list);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            showProgressDialog(false);
        }
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            //todo 어댑터 이벤트 정의 필요
            case R.id.btn_etc:
                startActivitySingleTop(new Intent(this, AlarmCenterSearchActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
    }

    @Override
    public void setObserver() {

        cmnViewModel.getRES_NOT_0001().observe(this, result -> {

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getNotiInfoList() != null) {
                        try {
                            if (cmnViewModel.updateNotiList(result.data.getNotiInfoList())) {
                                adapter.setRows(result.data.getNotiInfoList());
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            showProgressDialog(false);
                        }
                    } else {
                        showProgressDialog(false);
                    }
                    break;

                default:
                    showProgressDialog(false);
                    break;
            }


        });

    }

    @Override
    public void getDataFromIntent() {

    }
}
