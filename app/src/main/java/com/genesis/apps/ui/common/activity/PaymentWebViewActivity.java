package com.genesis.apps.ui.common.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.genesis.apps.comm.model.constants.ResultCodes;

public class PaymentWebViewActivity extends WebviewActivity {
    private final static String TAG = PaymentWebViewActivity.class.getSimpleName();
    final String ROADWIN_PAYMENT_SUCC = "0";
    final String ROADWIN_PAYMENT_FAIL = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setJavaInterface(new RoadWindInterface());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        String currUrl = fragment.getUrl();
        if (TextUtils.isEmpty(currUrl) || !currUrl.equalsIgnoreCase(url)) {
            if (fragment.openWindows != null&&fragment.openWindows.size()>0) {
                if (fragment.openWindows.get(0).canGoBack()) {
                    fragment.openWindows.get(0).goBack();
                } else {
                    fragment.openWindows.get(0).loadUrl("javascript:window.close();");
                }
                return;
            } else {
                if (fragment.getWebView() != null) {
                    if (fragment.getWebView().canGoBack()) {
                        fragment.getWebView().goBack();
                        return;
                    }
                }
            }
        }
        exit(ResultCodes.REQ_CODE_PAYMENT_CANCEL.getCode());
    }

    @Override
    public void getDataFromIntent(){
        super.getDataFromIntent();
//        Intent intent = getIntent();
//        if(intent == null || TextUtils.isEmpty(intent.getStringExtra(KeyNames.KEY_NAME_REVIEW_TRANS_ID))) {
//            finish();
//            return;
//        }
//        transId = intent.getStringExtra(KeyNames.KEY_NAME_URL);

    }

    private void exit(int resultCode) {
        exitPage("", resultCode);
    }

    private class RoadWindInterface {
        @JavascriptInterface
        public void ipay_event_2(final String msg) {
            Log.e(TAG, "ipay_event_2 : " + msg);
            if (!TextUtils.isEmpty(msg) && msg.equalsIgnoreCase(ROADWIN_PAYMENT_SUCC)) {
                exit(ResultCodes.REQ_CODE_PAYMENT_SUCC.getCode());
            } else {
                exit(ResultCodes.REQ_CODE_PAYMENT_FAIL.getCode());
//                String currUrl = fragment.getUrl();
//                if (!TextUtils.isEmpty(currUrl) && currUrl.equalsIgnoreCase(url)) {
//                    exit(ResultCodes.REQ_CODE_PAYMENT_FAIL.getCode());
//                } else {
//                    reload();
//                }
            }
        }
    }

}
