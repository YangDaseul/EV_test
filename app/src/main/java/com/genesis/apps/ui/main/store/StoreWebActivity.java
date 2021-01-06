package com.genesis.apps.ui.main.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CMS_1001;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.viewmodel.CMSViewModel;
import com.genesis.apps.databinding.ActivityContentsDetailWebBinding;
import com.genesis.apps.databinding.ActivityStoreWebBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StoreWebActivity extends SubActivity<ActivityStoreWebBinding> {
    private final String TAG = getClass().getSimpleName();

    public MyWebViewFrament fragment;

    private CMSViewModel cmsViewModel;

    private String url = "";
    private boolean isClearHistory=false;
    public String fn=""; //javascript 함수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_web);

        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);

        cmsViewModel = new ViewModelProvider(this).get(CMSViewModel.class);
    }

    @Override
    public void setObserver() {
        cmsViewModel.getRES_CMS_1001().observe(this, result -> {

            switch (result.status) {
                case SUCCESS:
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        Log.d("JJJJ", "getCustInfo : " + result.data.getCustInfo());
                        try {
//                            String html = "<!DOCTYPE html>" +
//                                    "<html>" +
//                                    "<body onload='document.frm1.submit()'>" +
//                                    "<form action='" + url + "' method='post' name='frm1'>" +
//                                    "  <input type='hidden' name='data' value='" + result.data.getCustInfo() + "'><br>" +
//                                    "</form>" +
//                                    "</body>" +
//                                    "</html>";
//                            fragment.loadData(html);

                            String postData = "data=" + URLEncoder.encode(result.data.getCustInfo(), "UTF-8");
//                            String postData = "data=" + result.data.getCustInfo();
//                            String postData = "{\"data\":\"" + result.data.getCustInfo() + "\"}";

                            Log.d("JJJJ", "postData : " + postData);

//                            fragment.postUrl(url, postData.getBytes());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        Intent intent = getIntent();
        if(intent == null) {
            finish();
            return;
        }

        url = intent.getStringExtra(KeyNames.KEY_NAME_URL);

        Log.d("JJJJ", "store url : " + url);
        cmsViewModel.reqCMS1001(new CMS_1001.Request(APPIAInfo.SM02.getId()));
    }

    @Override
    public void onBackPressed() {
        Log.d("JJJJ", "clearWindowOpens2 : " + clearWindowOpens2());
        try {
            if (!fragment.onBackPressed() || TextUtils.isEmpty(fragment.getUrl()) || fragment.getUrl().equalsIgnoreCase("about:blank")) {
                super.onBackPressed();
            }
        }catch (Exception e){
            super.onBackPressed();
        }
    }

    private void initView() {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.EXTRA_MAIN_URL, url);

        fragment = new MyWebViewFrament();
        fragment.setWebViewListener(webViewListener);
        fragment.setJavaInterface(new ScriptInterface(), "isdlpshown");
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fm_holder, fragment);
        ft.commitAllowingStateLoss();
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

    public void setClearHistory(boolean clearHistory) {
        isClearHistory = clearHistory;
    }

    public void clearHistory(){
        if(fragment!=null) fragment.clearHistory();
    }

    public boolean parseURL(String url) {
        return false;
    }

    public boolean back(String currentUrl) {
        if (fragment.canGoBack()) {
            fragment.goBack();
        } else {
            finish();
        }
        return true;
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

    // Javascript Brige class
    class ScriptInterface {
        @JavascriptInterface
        public void setMessage(final String value) {
            Log.d("JJJJ", "setMessage : " + value);
//            hd = new Handler();
//            hd.post(new Runnable() {
//                public void run() {
//                    isDlp = value;
//                }
//            });
        }
    }
}