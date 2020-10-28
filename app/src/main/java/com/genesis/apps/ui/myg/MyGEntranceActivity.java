package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.databinding.ActivityMygEntranceBinding;
import com.genesis.apps.ui.common.activity.LoginActivity;
import com.genesis.apps.ui.common.activity.SubActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyGEntranceActivity extends SubActivity<ActivityMygEntranceBinding> {
    @Inject
    public GA ga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_entrance);
        getDataFromIntent();
        setViewModel();
        setObserver();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                moveToLogin();
                break;
            case R.id.btn_join:
                moveToJoin();
                break;
            case R.id.btn_find:

                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setActivity(this);

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

    }

    public void moveToLogin(){
        startActivitySingleTop(new Intent(this, LoginActivity.class).putExtra("url",ga.getLoginUrl()), RequestCodes.REQ_CODE_LOGIN.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }
    public void moveToJoin(){
        startActivitySingleTop(new Intent(this, LoginActivity.class).putExtra("url",ga.getEnrollUrl()), RequestCodes.REQ_CODE_JOIN.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }
    public void moveToFind(){

    }
}
