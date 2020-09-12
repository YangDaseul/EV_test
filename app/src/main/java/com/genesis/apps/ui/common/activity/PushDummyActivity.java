package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.os.Bundle;

import com.genesis.apps.comm.util.CommonUtil;

import static com.genesis.apps.comm.model.KeyNames.IS_FOREGROUND_SERVICE;

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
