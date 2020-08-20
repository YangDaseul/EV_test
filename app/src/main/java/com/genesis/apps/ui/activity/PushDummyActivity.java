package com.genesis.apps.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.genesis.apps.comm.util.CommonUtil;
import com.genesis.apps.fcm.PushCode;

import static com.genesis.apps.comm.model.KeyNames.IS_FOREGROUND_SERVICE;
import static com.genesis.apps.comm.model.KeyNames.NOTIFICATION_ID;
import static com.genesis.apps.comm.model.KeyNames.PUSH_CODE;

public class PushDummyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isExcuteApp = CommonUtil.getServiceTaskName(PushDummyActivity.this, MainActivity.class.getName());

        if(!isForeground()) {
            //push notification
            if (!isExcuteApp && isPushData()) {
                startActivity(moveToPush(IntroActivity.class));
            } else {
                checkPushCode();
            }
        }else{
            //foreground notification
            if(!isExcuteApp){
                startActivitySingleTop(new Intent(this, IntroActivity.class),0);
            }
        }
        finish();
    }


    private boolean isForeground() {
        boolean isForeground=false;
        try {
            isForeground = getIntent().getBooleanExtra(IS_FOREGROUND_SERVICE, false);
        }catch (Exception e){
            isForeground=false;
        }
        return isForeground;
    }
}
