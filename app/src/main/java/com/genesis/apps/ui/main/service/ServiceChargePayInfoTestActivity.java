package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1003;
import com.genesis.apps.comm.model.api.gra.CHB_1005;
import com.genesis.apps.comm.model.api.gra.SOS_3003;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;

public class ServiceChargePayInfoTestActivity extends ServiceSOSPayInfoActivity {

    public CHBViewModel chbViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        reqDataToServer();

    }

    @Override
    public void reqDataToServer() {
        chbViewModel.reqCHB1005(new CHB_1005.Request(APPIAInfo.SM_CGRV01_P04.getId()));

    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);

    }

    @Override
    public void setObserver() {

        chbViewModel.getRES_CHB_1005().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data != null){

                        if(!TextUtils.isEmpty(result.data.getCont())){
                            if(StringUtil.isValidString(result.data.getCont()).startsWith("http"))
                                loadTermsUrl(result.data.getCont());
                            else
                                loadTerms(result.data.getCont());
                        }
                        showProgressDialog(false);
                        break;
                    }
                default:
                    String serverMsg = "";
                    try{
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if(TextUtils.isEmpty(serverMsg)){
                            serverMsg = "죄송합니다. 일시적인 오류가 발생하였습니다. 네트워크 연결 확인 후 다시 시도해 주세요.";
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });


    }

    private void initView(){

    }

    @Override
    public void onClickCommon(View v) {

    }

}
