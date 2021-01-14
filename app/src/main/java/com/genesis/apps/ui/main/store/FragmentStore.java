package com.genesis.apps.ui.main.store;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CMS_1001;
import com.genesis.apps.comm.model.api.gra.MYP_1003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.StoreInfo;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CMSViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.FragmentStoreBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class FragmentStore extends SubFragment<FragmentStoreBinding> {
    private static final String TAG = FragmentStore.class.getSimpleName();

    public MyWebViewFrament fragment;

    private LGNViewModel lgnViewModel;
    private CMSViewModel cmsViewModel;
    private MYPViewModel mypViewModel;

    public String url = ""; //초기 접속 URL
    public String fn=""; //javascript 함수
    private boolean isClearHistory=false;
    private Handler mHandler = null;

    public String isDlp = "NO";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_store);
    }

    private void initViewModel() {
        me.setLifecycleOwner(getViewLifecycleOwner());

        lgnViewModel = new ViewModelProvider(getActivity()).get(LGNViewModel.class);
        cmsViewModel = new ViewModelProvider(getActivity()).get(CMSViewModel.class);
        mypViewModel = new ViewModelProvider(getActivity()).get(MYPViewModel.class);

        mypViewModel.getRES_MYP_1003().observe(getViewLifecycleOwner(), result -> {
            //TODO 예외처리 및 뷰별 로딩처리 필요
            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                default:
                    ((MainActivity) getActivity()).ui.lGnb.setPoint((result.data==null||TextUtils.isEmpty(result.data.getBludMbrPoint()))
                            ? "--" : String.format(Locale.getDefault(), getString(R.string.word_home_24), StringUtil.getDigitGroupingString(result.data.getBludMbrPoint())));

                    break;
            }
        });

        cmsViewModel.getRES_CMS_1001().observe(getViewLifecycleOwner(), result -> {

            switch (result.status) {
                case SUCCESS:
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        Log.d("JJJJ", "getCustInfo : " + result.data.getCustInfo());

//                        Map<String, Object> params = new HashMap<>();
//                        params.put("data", result.data.getCustInfo());

//                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//                        String jsonStr = gson.toJson(params);
//
//                        Log.d("JJJJ", "json str : " + jsonStr);

                        try {
                            String postData = "data=" + URLEncoder.encode(result.data.getCustInfo(), "UTF-8");
                            fragment.postUrl(url, postData.getBytes());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

//                        try {
//                            String html = "<!DOCTYPE html>" +
//                                    "<html>" +
//                                    "<body onload='document.frm1.submit()'>" +
//                                    "<form action='" + url + "' method='post' name='frm1'>" +
//                                    "  <input type='hidden' name='data' value='" + result.data.getCustInfo() + "'><br>" +
//                                    "</form>" +
//                                    "</body>" +
//                                    "</html>";
//                            fragment.loadData(html);

//                            String postData = "data=" + URLEncoder.encode(result.data.getCustInfo(), "UTF-8");
//                            String postData = "data=" + result.data.getCustInfo();
//                            String postData = "{\"data\":\"" + result.data.getCustInfo() + "\"}";

//                            Log.d("JJJJ", "postData : " + postData);

//                            fragment.postUrl(url, jsonStr.getBytes());
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
                    }

                    break;
            }
        });
    }

    private void initView() {
        if(fragment != null) return;

        url = StoreInfo.STORE_LIST_URL;

        try {
            Bundle bundle = new Bundle();
            bundle.putString(WebViewFragment.EXTRA_MAIN_URL, url);

            if(!TextUtils.isEmpty(lgnViewModel.getUserInfoFromDB().getCustGbCd())&&VariableType.MAIN_VEHICLE_TYPE_0000.equals(lgnViewModel.getUserInfoFromDB().getCustGbCd())) {
                mypViewModel.reqMYP1003(new MYP_1003.Request(APPIAInfo.MG01.getId()));
                cmsViewModel.reqCMS1001(new CMS_1001.Request(APPIAInfo.SM02.getId()));
            }

            fragment = new MyWebViewFrament();
            fragment.setWebViewListener(webViewListener);
            fragment.setJavaInterface(new ScriptInterface(), "isdlpshown");
            fragment.setArguments(bundle);

            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.fm_holder, fragment);
            ft.commitAllowingStateLoss();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadUrl(String url) {
        this.url = url;
//        Log.d("JJJJ", "url : " + url);
//        fragment.getWebView().loadDataWithBaseURL(url, "data=nxqe7SJ61ySkkamxwDea4crIQ0TLULh%2B75YTJrH4RrF03LhZCbH%2FjzU20O1vvTB%2BLJYNqhDEInD3UUYQ4se%2BItep%2BYMLkeSVTQW%2BYzl0sYGzPNu7Zs5sJjcLYkdHDEnwDK38KZRF%2FhZd4AyWdz%2B1uP7Pg1eSqJPT36vsMYmlX8Ueh2eXTi7heXymlpZ6n47GuFKO%2BJSGVaY2XxPe53vJj2XlEu3uA%2BIUkxfaaWeNmtB70nVEbwCJ4s9QH9WZfu3dorZcfE4UiBSTfHznsgUIA5GkxctWi1SKjUAWSLXK%2FHUsKDafWbbvISQdKCOOHAYCJJzGhOS3JmSBdFhW95%2Bh7Em%2BqgFH1b%2BIjEpdzGgzAagCj3PZLrS2UPtn5m68i2H%2FXynrGzxvNacfYagdT1puOmwDz3QuiBLb0vUXXO18luGtd6bE40mnpJqk1QBPNabiDtfJztz7qxmbR82uEOBtkh3S5lZbAizwQzyr%2FhvQaFsvqdIot3xrLCdQgL6v%2BkEIh%2FgRh%2F0yK8ZWagJY8U3Z1weaIiGe0c6RL%2BDPs9WtB%2BK7LysbSUIMKZ0qcG8r2eyqaFwJXGJHl7ImQx1VvWTT04cwCgsQGlOuYFcrUaJSLE1W8HsAX7vxeLiBaikFf79kQ4qLtABcdv4R9dDR9IbUWUKw%2Bmq8H7BuSxYU%2B%2BZI4pZ8zlYlhDGv%2Fee%2FwFj5jw3X3bPGasDSJFX73yY7kGNgyIOogHepIDun%2BrR%2BLBf4pSCOPdWoUEPT5aFZtDj8n%2F1IOkMD1VhnwcRdQsTb5SGYFWyFYjtmkJiw5DLFq8VOc0leIMK%2BHx1rb%2FLkqEFLi426mzP7rQXRoWue32hB7gCba1UPGUIQpYi3qyEOOUCwDbyVItCOaL%2F71zsEK%2BZHz8SqZP4XqCHUyKc60jhEeRELJ8nOEOEP3ei%2BJnBeKV6tdr4%2BARuJoTB1Xt8ilf%2Bz%2F1l%2FgOIOYPjbNHsPFrsE5ZhUWvgdkoFxayH3qGK47F3jR0rAoYlBU0IDOWs%2BtbjZgRh1jerK2ubbyKOGWG29cR8NXJgrxtJtVD1p4dsn9OscE8Zk3xXI7iZVy49D42W1UFTIBWAu6ua%2B63lk%2BOEaoSfavQ%3D%3D", "text/html", "UTF-8","");
//        fragment.getWebView().loadUrl(url);
        cmsViewModel.reqCMS1001(new CMS_1001.Request(APPIAInfo.SM02.getId()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onRefresh() {
        Log.e("onResume", "onReusme store");
        ((MainActivity) getActivity()).setGNB(getString(R.string.main_word_5), View.VISIBLE, true, true);

        initView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean parseURL(String url) {
        Log.d("JJJJ", "url : " + url);
        Uri uri = Uri.parse(url);
        if (url.equalsIgnoreCase("https://www.genesis.com/kr/ko")
                || url.equalsIgnoreCase("https://www.genesis.com/kr/ko/genesis-membership.html")){
            //TODO 테스트 필요 0001
            getActivity().finish();
            return true;
        } else if(url.startsWith("genesisapps://close")){
            //TODO 테스트 필요 0004
            if(url.contains("all=y")){
                getActivity().finish();
            }else{
                if(clearWindowOpens3()) {
                    return true;
                }else {
                    return back(fragment.getUrl());
                }
            }
            return true;
        } else if (url.startsWith("genesisapps://open")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(QueryString.encode(uri.getQueryParameter("url")));
            intent.setData(Uri.parse(uri.getQueryParameter("url")));
            startActivity(intent); //TODO 테스트 필요 0002
            return true;
        } else if (url.startsWith("genesisapps://newView")) {
            fragment.createChildWebView(uri.getQueryParameter("url"));
            return true;
        } else if (url.startsWith("genesisapps://openView")) {
            ((MainActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), StoreWebActivity.class).putExtra(KeyNames.KEY_NAME_URL, uri.getQueryParameter("url")), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
            return true;
        } else if (url.startsWith("genesisapps://sendAction")) {
//            fragment.loadUrl(QueryString.encodeString(uri.getQueryParameter("fn")));
//            fragment.loadUrl("javascript:"+QueryString.encodeString(uri.getQueryParameter("fn")));
            fragment.loadUrl("javascript:"+uri.getQueryParameter("fn"));
            return true;
        } else if (url.startsWith("genesisapps://backAction")){
            this.fn = uri.getQueryParameter("fn");
            return true;
        } else if (url.startsWith("genesisapp://menu?id=")||url.startsWith("genesisapps://menu?id=")){
            ((MainActivity) getActivity()).moveToNativePage(url, false);
            return true;
        } else if(!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript")) {
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
                        getActivity().getPackageManager().getPackageInfo("kvp.jjy.MispAndroid320", 0);
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
            return false;
        }

        return true;
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
            return false;
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

    // Javascript Brige class
    class ScriptInterface {
        @JavascriptInterface
        public void setMessage(final String value) {
            Log.d("JJJJ", "setMessage : " + value);
            mHandler = new Handler();
            mHandler.post(new Runnable() {
                public void run() {
                    isDlp = value;
                }
            });
        }
    }

}
