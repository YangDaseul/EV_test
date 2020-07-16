package com.genesis.apps.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.databinding.ActivityWebviewBinding;

public class WebviewActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();
    private ActivityWebviewBinding activityWebviewBinding;
    public MyWebViewFrament fragment;
    public String url = ""; //초기 접속 URL
    private boolean isClearHistory=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWebviewBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_webview, null, false);
        setContentView(activityWebviewBinding.getRoot());
        setResult(RESULT_CANCELED);
        Intent intent = getIntent();
        if(intent == null || TextUtils.isEmpty(intent.getStringExtra("url"))) {
            finish();
            return;
        }
        url = intent.getStringExtra("url");
        initWebview(url);
    }
//    public void reload() {
//        Log.d(TAG, "reload:" + url);
//        if(fragment != null) fragment.loadUrl(url);
//    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart:" + url);
        super.onRestart();
        clearWindowOpens();
    }

    @Override
    public void onBackPressed() {
        if (!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initWebview(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.EXTRA_MAIN_URL, url);

        fragment = new MyWebViewFrament();
        fragment.setWebViewListener(webViewListener);
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fm_holder, fragment);
        ft.commitAllowingStateLoss();
    }

    public boolean parseURL(String url) {
        return false;
    }

    private MyWebViewFrament.WebViewListener webViewListener = new MyWebViewFrament.WebViewListener() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return parseURL(url);
        }
        @Override
        public void onPageFinished(String url) {
            Log.d(TAG, "onPageFinished:" + url);
            if(isClearHistory){
                clearHistory();
                setClearHistory(false);
            }
        }
        @Override
        public boolean onBackPressed() {
            if(clearWindowOpens()) {
                return true;
            }else {
                return back(fragment.getUrl());
            }

        }

        @Override
        public void onCloseWindow(WebView window) {
            Log.d(TAG, "onCloseWindow:" + url);
            //
        }
    };

    public boolean back(String currentUrl) {

        return false;
    }

    private boolean clearWindowOpens() {
        Log.d(TAG, "clearWindowOpens:" + url);
        if(!fragment.openWindows.isEmpty()) {
            try {
                for (WebView webView : fragment.openWindows) {
                    webView.clearHistory();
                    fragment.getWebViewContainer().removeView(webView);
                    fragment.onCloseWindow(webView);
                }
                fragment.openWindows.clear();
                return true;
            } catch (Exception ignore) {
            }
        }
        return false;
    }

    public void setClearHistory(boolean clearHistory) {
        isClearHistory = clearHistory;
    }

    public void clearHistory(){
        if(fragment!=null) fragment.clearHistory();
    }
}
