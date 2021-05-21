package com.genesis.apps.ui.common.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CHB_1011;
import com.genesis.apps.comm.model.api.gra.CHB_1013;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.carlife.PymtFormVO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.PostDataString;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CHBViewModel;
import com.genesis.apps.databinding.ActivityBluewalnutWebBinding;

import java.net.URISyntaxException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BluewalnutWebActivity extends SubActivity<ActivityBluewalnutWebBinding> {
    private final String TAG = getClass().getSimpleName();

    public MyWebViewFrament fragment;

    private CHBViewModel chbViewModel;

    private Handler mHandler = null;
    private String url = "";
    private boolean isClearHistory = false;
    public String fn = ""; //javascript 함수

    private PymtFormVO pymtFormVO;  // 결제 요청 폼 데이터
    private String pageType;  // 페이지 타입

    private String closeUrl;
    private String redirectUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluewalnut_web);

        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);

        chbViewModel = new ViewModelProvider(this).get(CHBViewModel.class);
    }

    @Override
    public void setObserver() {
        chbViewModel.getRES_CHB_1011().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000")) {
                        try {
                            PostDataString data = new PostDataString();
                            data.add("chnlCd", result.data.getChnlCd());
                            data.add("svrEncKey", result.data.getSvrEncKey());
                            data.add("chnlMbrIdfKey", result.data.getChnlMbrIdfKey());
                            data.add("mbrCi", result.data.getMbrCi());
                            data.add("mbrNm", result.data.getMbrNm());
                            data.add("mbPhNo", result.data.getMbPhNo());
                            data.add("mvmtCtCoCd", result.data.getMvmtCtCoCd());
                            data.add("rsdtNo", result.data.getRsdtNo());
                            data.add("closeUrl", result.data.getCloseUrl());
                            data.add("redirectUrl", result.data.getRedirectUrl());
                            data.add("userAgent", result.data.getUserAgent());
                            data.add("dvceCd", result.data.getDvceCd());
                            data.add("deceUuid", result.data.getDeceUuid());
                            data.add("ediDate", result.data.getEdiDate());
                            data.add("filler", result.data.getFiller());
                            data.add("hashVal", result.data.getHashVal());
                            Log.d("LJEUN", "postData : " + data.getPostData());
                            fragment.postUrl(result.data.getFormUrl(), data.getPostData().getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            closeUrl = result.data.getCloseUrl();
                            redirectUrl = result.data.getRedirectUrl();
                        }
                    }
                    break;
                case ERROR:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        finish();
                    }
                    break;
            }
        });

        chbViewModel.getRES_CHB_1013().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000")) {
                        try {
                            PostDataString data = new PostDataString();
                            data.add("chnlCd", result.data.getChnlCd());
                            data.add("svrEncKey", result.data.getSvrEncKey());
                            data.add("chnlMbrIdfKey", result.data.getChnlMbrIdfKey());
                            data.add("closeUrl", result.data.getCloseUrl());
                            data.add("redirectUrl", result.data.getRedirectUrl());
                            data.add("ediDate", result.data.getEdiDate());
                            data.add("filler", result.data.getFiller());
                            data.add("hashVal", result.data.getHashVal());
                            Log.d("LJEUN", "postData : " + data.getPostData());
                            fragment.postUrl(result.data.getFormUrl(), data.getPostData().getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            closeUrl = result.data.getCloseUrl();
                            redirectUrl = result.data.getRedirectUrl();
                        }
                    }
                    break;
                case ERROR:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        finish();
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        pymtFormVO = (PymtFormVO) intent.getSerializableExtra(KeyNames.KEY_NAME_CONTENTS_VO);
        pageType = intent.getStringExtra(KeyNames.KEY_NAME_PAGE_TYPE);
    }

    @Override
    public void onBackPressed() {
        try {
            if (!fragment.onBackPressed() || TextUtils.isEmpty(fragment.getUrl()) || fragment.getUrl().equalsIgnoreCase("about:blank")) {
                super.onBackPressed();
                exitPage(new Intent(), ResultCodes.REQ_CODE_BLUEWALNUT_PAYMENT_FINISH.getCode());
            }
        } catch (Exception e) {
            super.onBackPressed();
        }
    }

    private void initView() {
        Bundle bundle = new Bundle();

        if (pymtFormVO == null) {
            if (StringUtil.isValidString(pageType).equalsIgnoreCase(VariableType.EASY_PAY_WEBVIEW_TYPE_MEMBER_REG)) {
                String userAgentString = new WebView(this).getSettings().getUserAgentString();
                chbViewModel.reqCHB1011(new CHB_1011.Request(APPIAInfo.SM_CGRV02_P01.getId(), userAgentString));
            } else {
                chbViewModel.reqCHB1013(new CHB_1013.Request(APPIAInfo.SM_CGRV02_P01.getId()));
            }
        } else {
            bundle.putString(WebViewFragment.EXTRA_MAIN_URL, url);
            try {
                PostDataString data = new PostDataString();
                data.add("chnlCd", pymtFormVO.getChnlCd());
                data.add("svrEncKey", pymtFormVO.getSvrEncKey());
                data.add("chnlMbrIdfKey", pymtFormVO.getChnlMbrIdfKey());
                data.add("bpayCardId", pymtFormVO.getBpayCardId());
                data.add("srcCoCd", pymtFormVO.getSrcCoCd());
                data.add("chnlMid", pymtFormVO.getChnlMid());
                data.add("mOid", pymtFormVO.getMOid());
                data.add("prdtNm", pymtFormVO.getPrdtNm());
                data.add("stlmAmt", String.valueOf(pymtFormVO.getStlmAmt()));
                data.add("vlsp", String.valueOf(pymtFormVO.getVlsp()));
                data.add("srtx", String.valueOf(pymtFormVO.getSrtx()));
                data.add("srfe", String.valueOf(pymtFormVO.getSrfe()));
                data.add("nonMptx", String.valueOf(pymtFormVO.getNonMptx()));
                data.add("isMth", String.valueOf(pymtFormVO.getIsMth()));
                data.add("closeUrl", pymtFormVO.getCloseUrl());
                data.add("redirectUrl", pymtFormVO.getRedirectUrl());
                data.add("userAgent", pymtFormVO.getUserAgent());
                data.add("dvceCd", pymtFormVO.getDvceCd());
                data.add("deceUuid", pymtFormVO.getDeceUuid());
                data.add("ediDate", pymtFormVO.getEdiDate());
                data.add("filler", pymtFormVO.getFiller());
                data.add("hashVal", pymtFormVO.getHashVal());
                Log.d("LJEUN", "postData : " + data.getPostData());
                bundle.putByteArray(WebViewFragment.EXTRA_POST_DATA, data.getPostData().getBytes());
            } catch (Exception e) {

            }

            closeUrl = pymtFormVO.getCloseUrl();
            redirectUrl = pymtFormVO.getRedirectUrl();
        }

        fragment = new MyWebViewFrament();
        fragment.setWebViewListener(webViewListener);
        fragment.setArguments(bundle);
//            if (javaInterface != null)
//                fragment.setJavaInterface(javaInterface);

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
            if (url.startsWith("about:blank")) exitPage(new Intent(), ResultCodes.REQ_CODE_BLUEWALNUT_PAYMENT_FINISH.getCode());
            if (url.contains("/api/v1/dkc/carlife/c/payment/redirect/")) finish();
            if (isClearHistory) {
                clearHistory();
                setClearHistory(false);
            }
        }

        @Override
        public boolean onBackPressed() {
            Log.d(TAG, "onBackPressed");
            if (clearWindowOpens2()) {
                return true;
            } else {
                return back(fragment.getUrl());
            }

        }

        @Override
        public void onCloseWindow(WebView window) {
            Log.d(TAG, "onCloseWindow:" + url);
            exitPage(new Intent(), ResultCodes.REQ_CODE_BLUEWALNUT_PAYMENT_FINISH.getCode());
        }
    };

    public void setClearHistory(boolean clearHistory) {
        isClearHistory = clearHistory;
    }

    public void clearHistory() {
        if (fragment != null) fragment.clearHistory();
    }

    public boolean parseURL(String url) {
        Uri uri = Uri.parse(url);
        Log.d(TAG, "parseURL : " + url);
        if(!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript")) {
            Intent intent;
            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException use) {
                return false;
            }

            uri = Uri.parse(intent.getDataString());
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

    public boolean back(String currentUrl) {
        if (fragment.canGoBack()) {
            fragment.goBack();
        } else {
            finish();
        }
        return true;
    }

    public boolean clearWindowOpens2() {

        if (!TextUtils.isEmpty(fn)) {
            if (fragment.openWindows.size() > 0) {
                fragment.openWindows.get(0).loadUrl("javascript:" + fn);
            } else {
                fragment.loadUrl("javascript:" + fn);
            }
//            fn="";
            return true;
        } else if (!fragment.openWindows.isEmpty()) {
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
}