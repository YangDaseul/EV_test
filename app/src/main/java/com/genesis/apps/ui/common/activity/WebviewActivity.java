package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.fragment.app.FragmentTransaction;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.databinding.ActivityWebviewBinding;

public class WebviewActivity extends SubActivity<ActivityWebviewBinding> {
    private final String TAG = getClass().getSimpleName();
    public MyWebViewFrament fragment;
    public String url = ""; //초기 접속 URL
    public String fn=""; //javascript 함수
    private boolean isClearHistory=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activityWebviewBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_webview, null, false);
//        setContentView(activityWebviewBinding.getRoot());
        setContentView(R.layout.activity_webview);
        setResult(RESULT_CANCELED);
        getDataFromIntent();
        setViewModel();
        setObserver();
        if(!TextUtils.isEmpty(url))
            initWebview(url);
    }

    @Override
    public void getDataFromIntent(){
        Intent intent = getIntent();
        if(intent == null || TextUtils.isEmpty(intent.getStringExtra("url"))) {
            finish();
            return;
        }
        url = intent.getStringExtra("url");

    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {

    }

    @Override
    public void setObserver() {

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
            if(clearWindowOpens2()) {
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

    public boolean clearWindowOpens() {
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


    public boolean clearWindowOpens2() {

        if(!TextUtils.isEmpty(fn)){
            if(fragment.openWindows.size()>0){
                fragment.openWindows.get(0).loadUrl("javascript:"+fn);
            }else{
                fragment.loadUrl("javascript:"+fn);
            }
//            fn="";
            return true;
        }else if(!fragment.openWindows.isEmpty()) {
            try {
                for (WebView webView : fragment.openWindows) {
                    webView.clearHistory();
                    fragment.getWebViewContainer().removeView(webView);
                    fragment.onCloseWindow(webView);
//                    if(webView.canGoBack()){
//                        webView.goBack();
//                    }else {
//                        webView.clearHistory();
//                        fragment.getWebViewContainer().removeView(webView);
//                        fragment.onCloseWindow(webView);
//                    }
                }
                fragment.openWindows.clear();
                return true;
            } catch (Exception ignore) {
            }
        }
        return false;
    }


    public boolean clearWindowOpens3() {

       if(!fragment.openWindows.isEmpty()) {
            try {
                for (WebView webView : fragment.openWindows) {
                    if(webView.canGoBack()){
                        webView.goBack();
                    }else {
                        webView.clearHistory();
                        fragment.getWebViewContainer().removeView(webView);
                        fragment.onCloseWindow(webView);
                        fragment.openWindows.clear();
                    }
                }
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
