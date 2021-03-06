package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_8004;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.ActivityMygVersionBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

public class MyGVersioniActivity extends SubActivity<ActivityMygVersionBinding> {

    MYPViewModel mypViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_version);
        getDataFromIntent();
        setViewModel();
        setObserver();
        mypViewModel.reqMYP8004(new MYP_8004.Request(APPIAInfo.MG_VERSION01.getId()));
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel= new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_8004().observe(this, result -> {
            switch (result.status){
                case SUCCESS:
                    if(result.data!=null) {
                        showProgressDialog(false);
                        setView(PackageUtil.getApplicationVersionName(this, getPackageName()), result.data.getVer());
                    }
                    break;
//                    if(responseNetUIResponse.data!=null&&!TextUtils.isEmpty(responseNetUIResponse.data.getVer())){
//                        showProgressDialog(false);
//                        setView(PackageUtil.getApplicationVersionName(this, getPackageName()), responseNetUIResponse.data.getVer());
//                        return;
//                    }
                case LOADING:
                    showProgressDialog(true);
                    break;
                default: //ERROR ??????
                    SnackBarUtil.show(this,"???????????? ??????");
                    showProgressDialog(false);
                    setView(PackageUtil.getApplicationVersionName(this, getPackageName()), null);
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    private void setView(String currentVersion, String newVersion){
        if (PackageUtil.versionCompare(currentVersion,newVersion)<0) {
            //???????????????????????????
            ui.tvMsg.setText(R.string.msg_version_2);
            ui.tvUpdate2.setVisibility(View.VISIBLE);
            ui.tvUpdate2.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    PackageUtil.goMarket(MyGVersioniActivity.this, getPackageName());
                }
            });
        }else{
            //?????????????????????
            ui.tvMsg.setText(R.string.msg_version_1);
            ui.tvUpdate2.setVisibility(View.GONE);
        }
        ui.tvCurrentVersion.setText(PackageUtil.changeVersionToAppFormat(currentVersion));
        ui.tvNewVersion.setText(!TextUtils.isEmpty(newVersion) ? PackageUtil.changeVersionToAppFormat(newVersion) : PackageUtil.changeVersionToAppFormat(currentVersion));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
