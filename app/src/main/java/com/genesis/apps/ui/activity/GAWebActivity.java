package com.genesis.apps.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.genesis.apps.comm.util.SnackBarUtil;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.net.ga.GAInfo.GA_URL;

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
                    case EditorInfo.IME_ACTION_UNSPECIFIED:
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
        String url = ui.edit.getText().toString().trim();
        if(TextUtils.isEmpty(url)){
            SnackBarUtil.show(GAWebActivity.this, "주소를 입력해주세요.");
        }else{
            if(!url.contains("http://")){
                url = "http://"+url;
            }
            fragment.loadUrl(url);
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
                || url.equalsIgnoreCase("https://www.genesis.com/kr/ko/genesis-membership.html")
                || url.startsWith("genesis_apps://close")) {
            finish();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean back(String currentUrl) {
        if (currentUrl.startsWith(GA_URL)) {
            if(!fragment.canGoBack()) {
                finish();
                return true;
            }
        }else if(currentUrl.contains(URL_PURCHASE_CONSULTING)
                ||currentUrl.contains(URL_TEST_DRIVE)
                ||currentUrl.contains(URL_SIMILAR_STOCKS) ){
            finish();
            return true;
        }
        return false;
    }
}
