package com.genesis.apps.ui.common.view.listener;

import android.os.SystemClock;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public abstract class OnRemoveClickListener implements View.OnClickListener {

    private TextInputEditText textInputEditText;
    public OnRemoveClickListener(TextInputEditText textInputEditText){
        this.textInputEditText = textInputEditText;
    }
    // 중복 클릭 방지 시간 설정
    private static final long MIN_CLICK_INTERVAL=500;
    private long mLastClickTime;

    @Override
    public final void onClick(View v) {
        long currentClickTime= SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;

        // 중복 클릭인 경우
        if(elapsedTime<=MIN_CLICK_INTERVAL){
            return;
        }

        if(textInputEditText!=null){
            textInputEditText.setText("");
            textInputEditText.requestFocus();
        }
    }

}