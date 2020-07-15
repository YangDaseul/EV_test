package com.genesis.apps;

import android.content.Intent;
import android.os.Bundle;

import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.comm.ui.BaseActivity;
import com.genesis.apps.databinding.ActivityEntranceBinding;

import androidx.databinding.DataBindingUtil;

public class EntranceActivity extends BaseActivity {

    private ActivityEntranceBinding activityEntranceBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
