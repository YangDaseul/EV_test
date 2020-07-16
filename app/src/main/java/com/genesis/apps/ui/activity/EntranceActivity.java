package com.genesis.apps.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.databinding.ActivityEntranceBinding;

import androidx.databinding.DataBindingUtil;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EntranceActivity extends BaseActivity {
    @Inject
    public GA ga;

    private ActivityEntranceBinding activityEntranceBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEntranceBinding = DataBindingUtil.setContentView(this, R.layout.activity_entrance);
        activityEntranceBinding.setActivity(this);
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
