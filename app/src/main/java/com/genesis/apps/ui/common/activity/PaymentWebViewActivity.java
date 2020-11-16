package com.genesis.apps.ui.common.activity;

import android.os.Bundle;

import com.genesis.apps.comm.model.constants.ResultCodes;

public class PaymentWebViewActivity extends WebviewActivity {
    private final static String TAG = PaymentWebViewActivity.class.getSimpleName();

    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setResult();
    }

    //todo : impl 그리고 함수 이름 이상하다 싶으면 다시 짓기
    private void setResult() {
        result = ResultCodes.REQ_CODE_PAYMENT_FAIL.getCode();//결제 실패

        result = ResultCodes.REQ_CODE_PAYMENT_CANCEL.getCode();//결제 취소

        result = ResultCodes.REQ_CODE_PAYMENT_SUCC.getCode();//결제 성공

        exitPage("", result);
    }


}
