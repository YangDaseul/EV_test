package com.genesis.apps.ui.main.contents;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.hybrid.core.WebViewFragment;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CTT_1002;
import com.genesis.apps.comm.model.api.gra.CTT_1004;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CTTViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityContentsDetailWebBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.activity.WebviewActivity;

import java.util.concurrent.ExecutionException;

public class ContentsDetailWebActivity extends SubActivity<ActivityContentsDetailWebBinding> {
    private final String TAG = getClass().getSimpleName();
    private ContentsDetailWebActivity mActivity = this;
    private CTTViewModel cttViewModel;
    private LGNViewModel lgnViewModel;
    private CTT_1004.Response contentsVO;
    private View[] ratingViews;
    private int mRate = 1;
    public MyWebViewFrament fragment;
    public ContentsVO item;
    public String url = ""; //초기 접속 URL
    public String fn=""; //javascript 함수
    private boolean isClearHistory=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_detail_web);

        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.ll_rate_1:
            case R.id.ll_rate_2:
            case R.id.ll_rate_3:
            case R.id.ll_rate_4:
            case R.id.ll_rate_5:
                boolean isSelect = false;
                for(int i=0; i<ratingViews.length; i++) {
                    LinearLayout linearLayout = (LinearLayout) ratingViews[i];

                    if(linearLayout.getChildCount() != 0) {
                        if(isSelect) {
                            Glide.with(mActivity).asBitmap().load(R.drawable.ic_star_l_n).into((ImageView) linearLayout.getChildAt(0));
                        } else {
                            Glide.with(mActivity).asBitmap().load(R.drawable.ic_star_l_s).into((ImageView) linearLayout.getChildAt(0));
                        }
                    }

                    if(v.getId() == ratingViews[i].getId()) {
                        isSelect = true;
                        mRate = i + 1;
                        Log.d(TAG, "Click : " + mRate);
                    }
                }

                break;
            case R.id.btn_rate:
                VehicleVO vehicleVO = null;
                try {
                    vehicleVO = lgnViewModel.getMainVehicleSimplyFromDB();
                } catch (ExecutionException | InterruptedException e)  {
                    vehicleVO = null;
                    e.printStackTrace();
                }

                Log.d(TAG, "mdlNm : " + vehicleVO.getMdlNm());
                Log.d(TAG, "vin : " + vehicleVO.getVin());
                cttViewModel.reqCTT1002(new CTT_1002.Request(APPIAInfo.CM01.getId(), contentsVO.getListSeqNo(), String.valueOf(mRate), vehicleVO.getMdlNm(), vehicleVO.getVin()));

                break;
            case R.id.btn_link:
                if(!TextUtils.isEmpty(contentsVO.getLnkUri())) {
                    if("I".equals(contentsVO.getLnkTypCd())) {
                        moveToNativePage(contentsVO.getLnkUri(), false);
                    } else {
                        moveToExternalPage(contentsVO.getLnkUri(), "");
//                        startActivitySingleTop(new Intent(mActivity, WebviewActivity.class).putExtra(KeyNames.KEY_NAME_URL, contentsVO.getLnkUri()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    }
                }

                break;
            case R.id.iv_close:
                finish();

                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);

        cttViewModel = new ViewModelProvider(mActivity).get(CTTViewModel.class);
        lgnViewModel = new ViewModelProvider(mActivity).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
        cttViewModel.getRES_CTT_1002().observe(mActivity, result -> {

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);

                    break;
                case SUCCESS:
                    showProgressDialog(false);

                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        SnackBarUtil.show(mActivity, "평점 제출이 완료 되었습니다.");
                    }

                    break;
                default:
                    showProgressDialog(false);

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

        contentsVO = (CTT_1004.Response) intent.getSerializableExtra(KeyNames.KEY_NAME_CONTENTS_VO);
        url = contentsVO.getDtlViewCd().equalsIgnoreCase("3000") ? contentsVO.getDtlList().get(0).getHtmlFilUri() : contentsVO.getDtlList().get(0).getImgFilUri();
    }

    public void initView() {
        ui.setActivity(mActivity);

        Log.d(TAG, "url : " + url);

        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.EXTRA_MAIN_URL, url);

        fragment = new MyWebViewFrament();
        fragment.setWebViewListener(webViewListener);
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fm_holder, fragment);
        ft.commitAllowingStateLoss();

        ratingViews = new View[] {ui.includeLayout.llRate1, ui.includeLayout.llRate2, ui.includeLayout.llRate3, ui.includeLayout.llRate4, ui.includeLayout.llRate5};

        try {
            if("Y".equals(contentsVO.getEvalYn()) && !VariableType.MAIN_VEHICLE_TYPE_0000.equals(lgnViewModel.getUserInfoFromDB().getCustGbCd())) {
                ui.includeLayout.llRate.setVisibility(View.VISIBLE);
                ui.includeLayout.tvRateContent.setText(contentsVO.getEvalQst());
            } else {
                ui.includeLayout.llRate.setVisibility(View.GONE);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if("Y".equals(contentsVO.getLnkUseYn())) {
            ui.includeLayout.llLink.setVisibility(View.VISIBLE);
            ui.includeLayout.btnLink.setText(contentsVO.getLnkNm());
        } else {
            ui.includeLayout.llLink.setVisibility(View.GONE);
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