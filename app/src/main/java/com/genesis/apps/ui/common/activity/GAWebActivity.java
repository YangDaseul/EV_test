package com.genesis.apps.ui.common.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.widget.TextView;

import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;

import androidx.annotation.Nullable;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GAWebActivity extends WebviewActivity {
    public static final String URL_PURCHASE_CONSULTING="https://mypage.genesis.com/kr/ko/mypage/guide/purchase-consulting-step1.html";
    public static final String URL_TEST_DRIVE="https://www.genesis.com/kr/ko/genesis-test-drive.html";
    public static final String URL_SIMILAR_STOCKS="https://www.genesis.com/content/genesis_owners/kr/ko/shopping/similar-stocks.html";
//    public static final String URL_MEMBERSHIP="https://www.genesis.com/kr/ko/genesis-membership/life-service.html";
    public static final String URL_MEMBERSHIP="https://www.genesis.com/kr/ko/genesis-membership/benefit-service.html";
    public static final String URL_BTO_MAIN="https://www.genesis.com/kr/ko/build-your-genesis-gate.html";

    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ui.lEdit.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goUrl();
            }
        });

        ui.edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        goUrl();
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

    }

    private void goUrl(){
        if(ui.edit.hasFocus()) {
            SoftKeyboardUtil.hideKeyboard(getApplication());
            ui.edit.clearFocus();
        }

        String url = ui.edit.getText().toString().trim();
        if(TextUtils.isEmpty(url)){
            SnackBarUtil.show(GAWebActivity.this, "주소를 입력해 주세요.");
        }else{
            if(!url.contains("http://")){
                url = "http://"+url;
            }

            if(fragment.openWindows!=null&&fragment.openWindows.size()>0){
                fragment.openWindows.get(0).loadUrl(url);
            }else{
                fragment.loadUrl(url);
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
