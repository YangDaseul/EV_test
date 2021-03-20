package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.databinding.ActivityWebviewBinding;

public class WebviewActivity extends SubActivity<ActivityWebviewBinding> {
    private final String TAG = getClass().getSimpleName();
    public MyWebViewFrament fragment;
    public String url = ""; //초기 접속 URL
    public String fn=""; //javascript 함수
    private Object javaInterface;
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
        initWebview(url);
    }

    @Override
    public void getDataFromIntent(){
        Intent intent = getIntent();
        if(intent == null || TextUtils.isEmpty(intent.getStringExtra(KeyNames.KEY_NAME_URL))) {
            finish();
            return;
        }
        url = intent.getStringExtra(KeyNames.KEY_NAME_URL);

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
    public void reload() {
        Log.d(TAG, "reload:" + url);
        if(fragment != null) fragment.loadUrl(url);
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart:" + url);
        super.onRestart();
        clearWindowOpens();
    }

    @Override
    public void onBackPressed() {
        try {
            if (!fragment.onBackPressed() || TextUtils.isEmpty(fragment.getUrl()) || fragment.getUrl().equalsIgnoreCase("about:blank")) {
                super.onBackPressed();
            }
        }catch (Exception e){
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initWebview(String url) {
        if(!TextUtils.isEmpty(url)) {
            Bundle bundle = new Bundle();
            bundle.putString(WebViewFragment.EXTRA_MAIN_URL, url);

            fragment = new MyWebViewFrament();
            fragment.setWebViewListener(webViewListener);
            fragment.setArguments(bundle);
            if (javaInterface != null)
                fragment.setJavaInterface(javaInterface);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fm_holder, fragment);
            ft.commitAllowingStateLoss();
        }
    }

    public void loadTerms(TermVO data){
        if(data!=null&& !TextUtils.isEmpty(data.getTermCont())){
            Bundle bundle = new Bundle();
            bundle.putString(WebViewFragment.EXTRA_HTML_BODY, data.getTermCont());

            fragment = new MyWebViewFrament();
            fragment.setArguments(bundle);
            fragment.setWebViewListener(webViewListener);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fm_holder, fragment);
            ft.commitAllowingStateLoss();
        }

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
        if(fragment!=null&&fragment.openWindows!=null&&!fragment.openWindows.isEmpty()) {
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

    public void setJavaInterface(Object javaInterface) {
        this.javaInterface = javaInterface;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode== RequestCodes.REQ_CODE_FILECHOOSER.getCode()){
            if (resultCode == RESULT_OK) {
                if (fragment.filePathCallbackLollipop == null) return;
                if (data == null)
                    data = new Intent();
                if (data.getData() == null)
                    data.setData(fragment.cameraImageUri);

                fragment.filePathCallbackLollipop.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                fragment.filePathCallbackLollipop = null;
            }else{
                if (fragment.filePathCallbackLollipop != null) {   //  resultCode에 RESULT_OK가 들어오지 않으면 null 처리하지 한다.(이렇게 하지 않으면 다음부터 input 태그를 클릭해도 반응하지 않음)
                    fragment.filePathCallbackLollipop.onReceiveValue(null);
                    fragment.filePathCallbackLollipop = null;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
