package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;

import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.STO_1002;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.vo.BtoVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GAWebActivity extends WebviewActivity {
    //todo 제거 예정
    public static final String URL_PURCHASE_CONSULTING="https://mypage.genesis.com/kr/ko/mypage/guide/purchase-consulting-step1.html";
    public static final String URL_TEST_DRIVE="https://www.genesis.com/kr/ko/genesis-test-drive.html";
    public static final String URL_SIMILAR_STOCKS="https://www.genesis.com/content/genesis_owners/kr/ko/shopping/similar-stocks.html";
    public static final String URL_MEMBERSHIP="https://www.genesis.com/kr/ko/genesis-membership/benefit-service.html";
    public static final String URL_BTO_MAIN="https://www.genesis.com/kr/ko/build-your-genesis-gate.html";

    private final String TAG = getClass().getSimpleName();
    private int titleId=0;
    private APPIAInfo appiaInfo;
    private LGNViewModel lgnViewModel;
    private CMNViewModel cmnViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqHTML();
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
            //TODO 테스트 필요 0001
            finish();
            return true;
        } else if(url.startsWith("genesisapps://close")){
            //TODO 테스트 필요 0004
            if(url.contains("all=y")){
                finish();
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
        } else if (url.startsWith("genesisapps://sendAction")) {
//            fragment.loadUrl(QueryString.encodeString(uri.getQueryParameter("fn")));
//            fragment.loadUrl("javascript:"+QueryString.encodeString(uri.getQueryParameter("fn")));
            fragment.loadUrl("javascript:"+uri.getQueryParameter("fn"));
            return true;
        } else if (url.startsWith("genesisapps://backAction")){
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
//        if (fragment.canGoBack()) {
//            fragment.goBack();
//        } else {
//            finish();
//        }
        finish();
        closeTransition();
        return true;
//        if (currentUrl.startsWith(GA_URL)) {
//            if(!fragment.canGoBack()) {
//                finish();
//                return true;
//            }
//        }else if(currentUrl.contains(URL_PURCHASE_CONSULTING)
//                ||currentUrl.contains(URL_TEST_DRIVE)
//                ||currentUrl.contains(URL_SIMILAR_STOCKS) ){
//            finish();
//            return true;
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==RequestCodes.REQ_CODE_FILECHOOSER.getCode()){
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
