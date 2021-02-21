package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.vo.PrivilegeVO;
import com.genesis.apps.databinding.ActivityMyGPrivilegeStateBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MyGPrivilegeStateActivity extends SubActivity<ActivityMyGPrivilegeStateBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_g_privilege_state);
        getDataFromIntent();
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
        ui.setData((PrivilegeVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_PRIVILEGE_VO));
    }
}