package com.genesis.apps.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMygMembershipInfoBinding;
import com.genesis.apps.ui.view.listener.OnSingleClickListener;

public class MyGMembershipInfoActivity extends SubActivity<ActivityMygMembershipInfoBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership_info);
        ui.lTitle.title.setVisibility(View.INVISIBLE);

        ui.btnCall.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL+ui.btnCall.getTag().toString()));
                startActivity(intent);
            }
        });
        //TODO 처리 필요
    }



}
