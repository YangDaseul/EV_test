package com.genesis.apps.ui.main.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.ui.common.activity.HtmlActivity;

public class TsAuthWebViewActivity extends HtmlActivity {
    public static final String TAG_TS_AUTH_WEBVIEW_ACTIVITY = TsAuthWebViewActivity.class.getSimpleName();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        setWebViewListener(TSWebViewListener);
        initView();
    }

    @Override
    public void getDataFromIntent() {
        try {
            url = getIntent().getStringExtra(KeyNames.KEY_NAME_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (TextUtils.isEmpty(url)) {
                exitPage("URL 정보가 없습니다.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    @Override
    public void setViewModel() {
    }

    @Override
    public void setObserver() {
    }

    private void initView() {
//        ui.setValue(getString(R.string.gm_bt01_p01_1));
//        setTopView(R.layout.layout_btr_service_info);
        loadTerms(url);
    }


    @Override
    public void onClickCommon(View v) {

    }


    private MyWebViewFrament.WebViewListener TSWebViewListener = new MyWebViewFrament.WebViewListener() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG_TS_AUTH_WEBVIEW_ACTIVITY, "shouldOverrideUrlLoading: " + url);

            try {
                Intent intent = Intent.parseUri(url , Intent.URI_INTENT_SCHEME);
                if(intent != null && intent.getPackage() != null) {
                    Intent pacakge = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (pacakge != null) {
                        startActivity(intent);
                        return true;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG_TS_AUTH_WEBVIEW_ACTIVITY, "shouldOverrideUrlLoading", e);
                return true;
            }

            return false;
        }

        @Override
        public void onPageFinished(String url) {
            Log.d(TAG_TS_AUTH_WEBVIEW_ACTIVITY, "onPageFinished: " + url);
        }

        @Override
        public boolean onBackPressed() {
            Log.d(TAG_TS_AUTH_WEBVIEW_ACTIVITY, "onBackPressed: ");
            setResult(Activity.RESULT_CANCELED);
            finish();
            return false;
        }

        @Override
        public void onCloseWindow(WebView window) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    };

}
