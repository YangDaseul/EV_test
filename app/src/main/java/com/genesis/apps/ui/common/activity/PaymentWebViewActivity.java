package com.genesis.apps.ui.common.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.genesis.apps.comm.model.constants.ResultCodes;

import java.net.URISyntaxException;

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

    @Override
    public boolean parseURL(String url) {
        Log.d("JJJJ", "parseURL : " + url);
        if(!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript")) {
            Intent intent;
            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException use) {
                return false;
            }

            Uri uri = Uri.parse(intent.getDataString());
            intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException anfe) {
                if(url.startsWith("ispmobile://")) {
                    try {
                        getPackageManager().getPackageInfo("kvp.jjy.MispAndroid320", 0);
                    } catch(PackageManager.NameNotFoundException nnfe) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp"));
                        startActivity(intent);
                        return true;
                    }
                } else if(url.contains("kb-acp")) {
                    try {
                        Intent exceptIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        exceptIntent = new Intent(Intent.ACTION_VIEW);
                        exceptIntent.setData(Uri.parse("market://details?id=com.kbcard.kbkookmincard"));

                        startActivity(exceptIntent);
                    } catch(URISyntaxException use1) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp"));
                        startActivity(intent);
                        return true;
                    }
                } else {
                    try {
                        Intent exceptIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        String packageNm = exceptIntent.getPackage();
                        exceptIntent = new Intent(Intent.ACTION_VIEW);
                        exceptIntent.setData(Uri.parse("market://search?q=" + packageNm));

                        startActivity(exceptIntent);
                    } catch (URISyntaxException use1) {
                    }
                }
            }
        } else {
            fragment.loadUrl(url);
            return false;
        }
        return true;
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
                String currUrl = fragment.getUrl();
                if (!TextUtils.isEmpty(currUrl) && currUrl.equalsIgnoreCase(url)) {
                    exit(ResultCodes.REQ_CODE_PAYMENT_FAIL.getCode());
                } else {
                    reload();
                }
            }
        }
    }

}
