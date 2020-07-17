package com.genesis.apps.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityIntroBinding;

public class IntroActivity extends SubActivity<ActivityIntroBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        new Handler().postDelayed(() -> {
            if(isPushData()){
                startActivity(moveToPush(MainActivity.class));
            }else{
                startActivitySingleTop(new Intent(IntroActivity.this, MainActivity.class),0);
            }
            finish();
        }, 2000);
    }


}
