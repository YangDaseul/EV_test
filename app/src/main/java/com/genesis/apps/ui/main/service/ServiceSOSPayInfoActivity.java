package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BTR_1010;
import com.genesis.apps.comm.model.api.gra.SOS_1003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.ui.common.activity.HtmlActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ServiceSOSPayInfoActivity extends HtmlActivity {

    private SOSViewModel sosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        sosViewModel.reqSOS1003(new SOS_1003.Request(APPIAInfo.SM_EMGC01_P04.getId()));
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
    }

    @Override
    public void setObserver() {

        sosViewModel.getRES_SOS_1003().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null&&!TextUtils.isEmpty(result.data.getCont())) {
                        loadTerms(result.data.getCont());
                        showProgressDialog(false);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
    }

    private void initView() {

    }


    @Override
    public void onClickCommon(View v) {

    }

}
