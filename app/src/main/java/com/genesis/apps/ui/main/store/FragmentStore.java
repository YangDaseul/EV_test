package com.genesis.apps.ui.main.store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.FragmentServiceBinding;
import com.genesis.apps.databinding.FragmentStoreBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.main.service.ServiceAutocare4ResultActivity;
import com.genesis.apps.ui.main.service.ServiceHomeToHome4ResultActivity;
import com.genesis.apps.ui.main.service.ServiceRepair2ApplyActivity;
import com.genesis.apps.ui.main.service.ServiceRepair4ResultActivity;
import com.genesis.apps.ui.main.service.ServiceViewpagerAdapter;
import com.genesis.apps.ui.myg.MyGEntranceActivity;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


public class FragmentStore extends SubFragment<FragmentStoreBinding> {
    private static final String TAG = FragmentStore.class.getSimpleName();

    public MyWebViewFrament fragment;

    public String url = ""; //초기 접속 URL
    public String fn=""; //javascript 함수
    private boolean isClearHistory=false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_store);
    }

    private void initView() {
        // 임시 URL
        url = "https://mall.genesis.com:44035/ko/main.do";

        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.EXTRA_MAIN_URL, url);

        fragment = new MyWebViewFrament();
        fragment.setWebViewListener(webViewListener);
        fragment.setArguments(bundle);

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.fm_holder, fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
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
        ((MainActivity) getActivity()).setGNB(false, 0, View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    public void setClearHistory(boolean clearHistory) {
        isClearHistory = clearHistory;
    }

    public void clearHistory(){
        if(fragment!=null) fragment.clearHistory();
    }

}
