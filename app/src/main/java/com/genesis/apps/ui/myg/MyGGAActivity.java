package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMygVersionBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MyGGAActivity extends SubActivity<ActivityMygVersionBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_ga);
        //TODO 처리 필요
    }

    @Override
    public void onSingleClick(View v) {

    }

}
