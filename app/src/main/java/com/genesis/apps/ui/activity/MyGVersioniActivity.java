package com.genesis.apps.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.MYP_8004;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.ActivityMygVersionBinding;

public class MyGVersioniActivity extends SubActivity<ActivityMygVersionBinding> {

    MYPViewModel mypViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_version);
        ui.lTitle.title.setText(R.string.title_version);

        ui.tvUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageUtil.goMarket(MyGVersioniActivity.this, getPackageName());
            }
        });

        ui.setLifecycleOwner(this);
        mypViewModel= new ViewModelProvider(this).get(MYPViewModel.class);
        mypViewModel.getRES_MYP_8004().observe(this, responseNetUIResponse -> {
            switch (responseNetUIResponse.status){
                case SUCCESS:
                    showProgressDialog(false);
                    setView(PackageUtil.getApplicationVersionName(this, getPackageName()), responseNetUIResponse.data.getVer());
                    break;
//                    if(responseNetUIResponse.data!=null&&!TextUtils.isEmpty(responseNetUIResponse.data.getVer())){
//                        showProgressDialog(false);
//                        setView(PackageUtil.getApplicationVersionName(this, getPackageName()), responseNetUIResponse.data.getVer());
//                        return;
//                    }
                case LOADING:
                    showProgressDialog(true);
                    break;
                default: //ERROR 포함
                    SnackBarUtil.show(this,"네트워크 이슈");
                    showProgressDialog(false);
                    //TODO 에러 팝업 및 처리 필요
                    //TODO 스낵바는 이전페이지의 onActivityResult에서 처리필요함.
                    setView(PackageUtil.getApplicationVersionName(this, getPackageName()), null);
                    break;
            }
        });

    }

    private void setView(String currentVersion, String newViersion){
        if (PackageUtil.versionCompare(currentVersion,newViersion)<0) {
            //업데이트필요한경우
            ui.tvMsg.setText(R.string.msg_version_2);
            ui.lNewVersion.setBackgroundResource(R.drawable.bg_ffffff_stroke_cd9a81);
            ui.tvNewTitle.setTextColor(getColor(R.color.x_cd9a81));
            ui.tvNewVersion.setTextColor(getColor(R.color.x_cd9a81));
            ui.tvUpdate2.setVisibility(View.VISIBLE);
        }else{
            //최신버전일경우
            ui.tvMsg.setText(R.string.msg_version_1);
            ui.lNewVersion.setBackgroundColor(getColor(R.color.x_f4f4f4));
            ui.tvNewTitle.setTextColor(getColor(R.color.x_525252));
            ui.tvNewVersion.setTextColor(getColor(R.color.x_141414));
            ui.tvUpdate2.setVisibility(View.GONE);
        }
        ui.tvCurrentVersion.setText(currentVersion);
        ui.tvNewVersion.setText(newViersion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mypViewModel.reqMYP8004(new MYP_8004.Request());
    }
}
