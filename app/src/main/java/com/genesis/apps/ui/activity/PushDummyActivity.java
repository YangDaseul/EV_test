package com.genesis.apps.ui.activity;

import android.os.Bundle;

import com.genesis.apps.comm.util.CommonUtil;

public class PushDummyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isExcuteApp = CommonUtil.getServiceTaskName(PushDummyActivity.this, MainActivity.class.getName());
        if (!isExcuteApp&&isPushData()) {
            startActivity(moveToPush(IntroActivity.class));
        }else {
            checkPushCode();
        }
        finish();
    }
}
