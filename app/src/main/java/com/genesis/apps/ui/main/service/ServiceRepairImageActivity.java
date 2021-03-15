package com.genesis.apps.ui.main.service;

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
import com.genesis.apps.databinding.ActivityServiceRepairImageBinding;
import com.genesis.apps.databinding.ItemTabRepairImageBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.main.service.view.ServiceRepairImageAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * @author hjpark
 * @brief 수리 사진
 */
public class ServiceRepairImageActivity extends SubActivity<ActivityServiceRepairImageBinding> {

    private REQViewModel reqViewModel;
    private final int[] TAB_ID_LIST = {R.string.sm_r_history02_2, R.string.sm_r_history02_3, R.string.sm_r_history02_4};
    private ServiceRepairImageAdapter serviceRepairImageAdapter;

    private String asnCd;
    private String imgDivCd;
    private String wrhsNo;
    private String vhclInoutNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_repair_image);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        serviceRepairImageAdapter = new ServiceRepairImageAdapter();
        ui.rv.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(this,20.0f)));
//        ((SimpleItemAnimator) ui.rvNoti.getItemAnimator()).setSupportsChangeAnimations(true);
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(serviceRepairImageAdapter);
        setTabView();
        reqViewModel.reqREQ1018(new REQ_1018.Request("", asnCd, "1", wrhsNo, vhclInoutNo));
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void setViewModel() {
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {
        reqViewModel.getRES_REQ_1018().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getImgList()!=null) {
                        showProgressDialog(false);
                        serviceRepairImageAdapter = new ServiceRepairImageAdapter();
                        serviceRepairImageAdapter.setRows(result.data.getImgList());
                        ui.rv.setAdapter(serviceRepairImageAdapter);
                        setViewEmpty();
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        setViewEmpty();
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
    }

    private void setViewEmpty() {
        ui.tvEmpty.setVisibility(serviceRepairImageAdapter.getItemCount()>0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void getDataFromIntent() {
        try {
            asnCd = getIntent().getStringExtra(KeyNames.KEY_NAME_ASNCD);
            vhclInoutNo = getIntent().getStringExtra(KeyNames.KEY_NAME_VEHICLE_IN_OUT_NO);
            wrhsNo = getIntent().getStringExtra(KeyNames.KEY_NAME_WRHS_NO);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (TextUtils.isEmpty(asnCd)||TextUtils.isEmpty(vhclInoutNo)){
                exitPage("필수 정보(정비망 코드 및 차량입출고 번호)가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }


    private void setTabView(){
        for(int i=0 ; i<TAB_ID_LIST.length; i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabRepairImageBinding itemTabRepairImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab_repair_image, null, false);
            final View view = itemTabRepairImageBinding.getRoot();
            itemTabRepairImageBinding.tvTab.setText(TAB_ID_LIST[i]);
            ui.tlTabs.addTab(ui.tlTabs.newTab().setCustomView(view));
//            ui.tlTabs.addView(view);
        }

        ui.tlTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        //todo menu id 넣을 것
                        reqViewModel.reqREQ1018(new REQ_1018.Request("", asnCd, "1", wrhsNo, vhclInoutNo));
                        break;
                    case 1:
                        reqViewModel.reqREQ1018(new REQ_1018.Request("", asnCd, "2", wrhsNo, vhclInoutNo));
                        break;
                    case 2:
                    default:
                        reqViewModel.reqREQ1018(new REQ_1018.Request("", asnCd, "3", wrhsNo, vhclInoutNo));
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
