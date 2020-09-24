package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMygMembershipInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

public class MyGMembershipInfoActivity extends SubActivity<ActivityMygMembershipInfoBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership_info);

    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_call:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL+ui.btnCall.getTag().toString()));
                startActivity(intent);
                break;
        }
    }
}
