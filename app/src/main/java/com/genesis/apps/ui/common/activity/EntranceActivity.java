package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.databinding.ActivityEntranceBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EntranceActivity extends SubActivity<ActivityEntranceBinding> {
    @Inject
    public GA ga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
    }

    @Override
    public void onSingleClick(View v) {

    }

    public void moveToLogin(){
        startActivitySingleTop(new Intent(this, LoginActivity.class).putExtra("url",ga.getLoginUrl()), RequestCodes.REQ_CODE_LOGIN.getCode());
    }
    public void moveToJoin(){
        startActivitySingleTop(new Intent(this, LoginActivity.class).putExtra("url",ga.getEnrollUrl()), RequestCodes.REQ_CODE_JOIN.getCode());
    }
    public void moveToFind(){

    }
}
