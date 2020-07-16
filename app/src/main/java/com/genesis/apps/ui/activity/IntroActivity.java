package com.genesis.apps.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {

    private ActivityIntroBinding activityIntroBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityIntroBinding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        new Handler().postDelayed(() -> {
            startActivitySingleTop(new Intent(IntroActivity.this, MainActivity.class),0);
            finish();
        }, 2000);
    }


}
