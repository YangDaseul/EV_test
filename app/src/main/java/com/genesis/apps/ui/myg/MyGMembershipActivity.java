package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMygMembershipBinding;
import com.genesis.apps.databinding.ActivityMygMembershipInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MyGMembershipActivity extends SubActivity<ActivityMygMembershipBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership);

    }

    @Override
    public void onSingleClick(View v) {
    }
}
