package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.STO_1002;
import com.genesis.apps.comm.model.constants.GAInfo;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.vo.BtoVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GAWebActivity extends WebviewActivity {

    private final String TAG = getClass().getSimpleName();
    private int titleId=0;
    private APPIAInfo appiaInfo;
    private LGNViewModel lgnViewModel;
    private CMNViewModel cmnViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqHTML();
        setStatusBarColor();
    }

    //데이터마일즈 동의, 상세 페이지만 스테이터스바 색상 변경
    private void setStatusBarColor() {
        if(!TextUtils.isEmpty(url)&&
                (url.contains(GAInfo.GA_DATAMILES_AGREEMENTS_URL)||url.contains(GAInfo.GA_DATAMILES_DETAIL_URL))){
            setStatusBarColor(this, R.color.x_05141f);
        }
    }

    private void reqHTML() {
        if(appiaInfo!=null){
            switch (appiaInfo){
                case GM_BTO1://BTO
                    lgnViewModel.reqSTO1002(new STO_1002.Request(APPIAInfo.GM01.getId()));
                    break;
                case GM_BTO2://견적내기
                    VehicleVO vehicleVO = null;
                    BtoVO btoVO = null;
                    try {
                        vehicleVO = lgnViewModel.getMainVehicleFromDB();
                        if (vehicleVO != null && !TextUtils.isEmpty(vehicleVO.getMdlNm())) {
                            btoVO = cmnViewModel.getBtoVO(vehicleVO.getMdlNm());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if (btoVO == null) {
                            //todo 메시지 재 정의 필요
                            SnackBarUtil.show(this, "BTO 정보가 존재하지 않습니다.");
                        } else {
                            initWebview(btoVO.getHtmlFilUri()+btoVO.getMdlNm());
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
    }

    @Override
    public void setObserver() {
        //BTO
        lgnViewModel.getRES_STO_1002().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && !TextUtils.isEmpty(result.data.getHtmlFilUri())) {
                        showProgressDialog(false);
                        initWebview(result.data.getHtmlFilUri());
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });


    }

    /**
     * @brief
     * url이 있으면 해당 url로 웹뷰를 띄우고
     * url이 없고 appiaInfo가 있으면 화면 ia에 맞는 url 혹은 html data를 확인 후 웹뷰 오픈
     *
     */
    @Override
    public void getDataFromIntent(){
        try {
            url = getIntent().getStringExtra(KeyNames.KEY_NAME_URL);
            titleId = getIntent().getIntExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, 0);
            appiaInfo = (APPIAInfo) getIntent().getSerializableExtra(KeyNames.KEY_NAME_APP_IA_INFO);
        }catch (Exception e){
            e.printStackTrace();
            titleId = 0;
        }finally{
            if(titleId==0){
                ui.lTitle.lTitleBar.setVisibility(View.GONE);
            }else{
                ui.setValue(getString(titleId));
                ui.lTitle.lTitleBar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean parseURL(String url) {
        final Uri uri = Uri.parse(url);
        if (url.equalsIgnoreCase("https://www.genesis.com/kr/ko")
                || url.equalsIgnoreCase("https://www.genesis.com/kr/ko/genesis-membership.html")){
            finish();
            return true;
        } else if(url.startsWith("genesisapp://exeApp") || url.startsWith("genesisapps://exeApp")){
            String packgeName;
            try{
                packgeName = uri.getQueryParameter("schm");
                if(!TextUtils.isEmpty(packgeName)){
                    PackageUtil.runApp(this, packgeName);
                }
            }catch (Exception e){

            }
            return true;
        } else if(url.startsWith("genesisapp://close") || url.startsWith("genesisapps://close")){
            if(url.contains("all=y")){
                finish();
            }else{
                if(clearWindowOpens3()) {
                    return true;
                }else {
                    fragment.getWebView().clearHistory();
                    return back(fragment.getUrl());
                }
            }
            return true;
        } else if (url.startsWith("genesisapp://open") || url.startsWith("genesisapps://open")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(QueryString.encode(uri.getQueryParameter("url")));
            intent.setData(Uri.parse(uri.getQueryParameter("url")));
            startActivity(intent); //TODO 테스트 필요 0002
            return true;
        } else if (url.startsWith("genesisapp://newView") || url.startsWith("genesisapps://newView")) {
            fragment.createChildWebView(uri.getQueryParameter("url"));
            return true;
        } else if (url.startsWith("genesisapp://sendAction") || url.startsWith("genesisapps://sendAction")) {
//            fragment.loadUrl(QueryString.encodeString(uri.getQueryParameter("fn")));
//            fragment.loadUrl("javascript:"+QueryString.encodeString(uri.getQueryParameter("fn")));
            fragment.loadUrl("javascript:"+uri.getQueryParameter("fn"));
            return true;
        } else if (url.startsWith("genesisapp://backAction") || url.startsWith("genesisapps://backAction")){
            this.fn = uri.getQueryParameter("fn");
            return true;
        } else if (url.startsWith("genesisapp://menu?id=")||url.startsWith("genesisapps://menu?id=")){
            moveToNativePage(url, false);
            return true;
        } else {
            return false;
        }
    }

    //TODO 테스트 필요 0003
    @Override
    public boolean back(String currentUrl) {
        if (fragment!=null&&fragment.canGoBack()) {
            fragment.goBack();
            return false;
        } else {
            finish();
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (!TextUtils.isEmpty(fn)) {
                if (fragment.openWindows.size() > 0) {
                    fragment.openWindows.get(0).loadUrl("javascript:" + fn);
                } else {
                    fragment.loadUrl("javascript:" + fn);
                }
            } else {
                if (fragment.canGoBack()) {
                    fragment.goBack();
                } else {
                    super.onBackPressed();
                }
            }
        }catch (Exception e){
            super.onBackPressed();
        }
    }
}
