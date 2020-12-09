package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.os.Bundle;

import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.CommonUtil;
import com.genesis.apps.ui.intro.IntroActivity;
import com.genesis.apps.ui.main.MainActivity;

import static com.genesis.apps.comm.model.constants.KeyNames.IS_FOREGROUND_SERVICE;

public class PushDummyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isExcuteApp = CommonUtil.getServiceTaskName(PushDummyActivity.this, MainActivity.class.getName());

        if(!isForeground()) {
            //push notification
            if (!isExcuteApp && isPushData()) {
                startActivity(getPushIntent(IntroActivity.class));
            } else {
                checkPushCode();
            }
        }else{
            //foreground notification
            if(!isExcuteApp){
                startActivitySingleTop(new Intent(this, IntroActivity.class),0, VariableType.ACTIVITY_TRANSITION_ANIMATION_NONE);
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
