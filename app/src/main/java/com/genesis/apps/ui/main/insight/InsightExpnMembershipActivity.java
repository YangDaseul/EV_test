package com.genesis.apps.ui.main.insight;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.REQ_1018;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityInsightExpnMembershipBinding;
import com.genesis.apps.databinding.ActivityServiceRepairImageBinding;
import com.genesis.apps.databinding.ItemTabMembershipBinding;
import com.genesis.apps.databinding.ItemTabRepairImageBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.service.view.ServiceRepairImageAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * @author hjpark
 * @brief 멤버십 사용 방법
 */
public class InsightExpnMembershipActivity extends SubActivity<ActivityInsightExpnMembershipBinding> {

    private final int[] TAB_ID_LIST = {R.drawable.tab_oil_h_s, R.drawable.tab_oil_g_s, R.drawable.tab_oil_s_s};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_expn_membership);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        setTabView();
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


    private void setTabView(){
        for(int i=0 ; i<TAB_ID_LIST.length; i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabMembershipBinding itemTabMembershipBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab_membership, null, false);
            final View view = itemTabMembershipBinding.getRoot();
            itemTabMembershipBinding.ivTab.setImageResource(TAB_ID_LIST[i]);
            ui.tlTabs.addTab(ui.tlTabs.newTab().setCustomView(view));
        }
        //todo 2021-01-29 정책 결정되면 구현 필요
        ui.tlTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
