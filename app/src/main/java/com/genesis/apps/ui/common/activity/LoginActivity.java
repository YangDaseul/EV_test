package com.genesis.apps.ui.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.ga.GA;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.constants.GAInfo.CCSP_URL;
import static com.genesis.apps.comm.model.constants.GAInfo.GA_CALLBACK_URL;
import static com.genesis.apps.comm.model.constants.GAInfo.GA_REDIRECT_URL;
import static com.genesis.apps.comm.model.constants.GAInfo.GA_URL;

@AndroidEntryPoint
public class LoginActivity extends WebviewActivity {
    private final String TAG = getClass().getSimpleName();
    public static final String TYPE_AUTHUUID="authUuid";
    public static final String TYPE_LOGIN="login";
    public static final String TYPE_JOIN="join";
    public static final String TYPE_FIND="find";
    public static final String TYPE_RELEASE="release";

    private String tokenCode;
    private String authUuid;
    private String ccspType ="";
    @Inject
    public GA ga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        ui.lTitle.lTitleBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getDataFromIntent() {
        super.getDataFromIntent();
        Intent intent = getIntent();
        try {
            ccspType = intent.getStringExtra(KeyNames.KEY_ANME_CCSP_TYPE);
        }catch (Exception e){
            ccspType = TYPE_LOGIN;
        }
    }

    @Override
    public boolean parseURL(String url) {
        final Uri uri = Uri.parse(url);
        if (url.startsWith(GA_CALLBACK_URL)) { // ????????????/????????? ??????
            return loginResult(uri);
        } else if (url.startsWith(GA_REDIRECT_URL)) { // ???????????? ??????
            return certifyResult(uri);
        } else{
            return false;
        }
    }

    private boolean certifyResult(Uri uri) {
        //https://t-accounts.genesis.com/api/test/redirect.do?state=xjnweledme&authUuid=8266ce77-1705-4785-9551-98d5047dcd89
        // GaRedirect URL??? APP?????? ?????? ????????? ???????????? url <-- App????????? ?????????.
        String result = uri.getQueryParameter("result");
        if ("1249".equals(result)) {
            //????????? ??????
            finish();
            return true;
        }

        //????????? ?????? ???
        if(ccspType.equalsIgnoreCase(TYPE_RELEASE)) {
            setResult(Activity.RESULT_OK);
            finish();
            return true;
        }

        String csrf = uri.getQueryParameter("state");
        String authUuid = uri.getQueryParameter("authUuid");
        Log.e("TEST LoginActivity", "LOGIN ACTIVITY:" +authUuid + "        state:"+csrf);
        if (csrf != null && csrf.equals(ga.getCsrf()) && !TextUtils.isEmpty(authUuid)) {
            Log.v("TEST LoginActivity", "LOGIN ACTIVITY DEPTH2:" +authUuid );
            this.authUuid = authUuid;
            if(ccspType.equalsIgnoreCase(TYPE_AUTHUUID)){ //????????? ???????????? ???????????? ?????? ??? ?????? ???
                setResult(Activity.RESULT_OK, new Intent().putExtra(VariableType.KEY_NAME_LOGIN_AUTH_UUID, authUuid));
                finish();
            }
        }
        return true;
    }

    private boolean loginResult(Uri uri) {
        String result = uri.getQueryParameter("result");
        //0000: ??????, 1126: GA??????ok & ????????? ?????????
        if ("0000".equals(result) || (ccspType.equalsIgnoreCase(TYPE_LOGIN) && "1126".equals(result))) {
            final String scope = uri.getQueryParameter("scope"); //????????? ?????? ???????????? ?????? ??????
            final String tokenCode = uri.getQueryParameter("code");
            Log.d(TAG, "shouldOverrideUrlLoading: scope = " + scope + ", tokenCode=" + tokenCode);
            if (TextUtils.isEmpty(tokenCode)) {
                finish();
                return true;
            }else {
                this.tokenCode = tokenCode;
            }
            Log.v("TEST LoginActivity", "LOGIN ACTIVITY DEPTH3 url:" +uri.toString());
            Log.d(TAG, "shouldOverrideUrlLoading: scope = " + scope + ", tokenCode=" + tokenCode);
            ListenableFuture<NetResult> f = executorService.getListeningExecutorService().submit(() -> ga.checkEnroll(tokenCode, scope));
            f.addListener(() -> {
                try {
                    excuteResultEnroll(f.get(), tokenCode);
                } catch (InterruptedException e) {
                    Log.d(TAG, "InterruptedException");
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }, executorService.getUiThreadExecutor());
        } else {
            String msg = uri.getQueryParameter("message");
            alert("",msg, (dialogInterface, i) -> finish());
        }
        return true;
    }

    private void excuteResultEnroll(NetResult netResult, String tokenCode) {
        if (netResult != null) {
            switch (netResult.getCode()) {
                case SUCCESS:
                    Intent intent = new Intent();
                    intent.putExtra(VariableType.KEY_NAME_LOGIN_TOKEN_CODE, tokenCode);
                    if(!TextUtils.isEmpty(authUuid)) intent.putExtra(VariableType.KEY_NAME_LOGIN_AUTH_UUID, authUuid);

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    break;
                case ERR_EXCEPTION_GA:
                    alert("", netResult.getData().toString(), (dialogInterface, i) -> finish());
                    break;
                case ERR_EXCEPTION_UNKNOWN:
                default:
                    alert("", getString(R.string.error_msg_5), (dialogInterface, i) -> finish());
                    break;
            }
        } else {
            alert("", getString(R.string.error_msg_5), (dialogInterface, i) -> finish());
        }
    }

    @Override
    public boolean back(String currentUrl) {
        if(currentUrl.endsWith("/web/v1/user/signin")||currentUrl.endsWith("password-search")||currentUrl.contains("web/v1/user/signout?sid")){
            //???????????????????????? ??????????????? ??? ???????????? ????????? ????????? ??????
            exitPage("", 0);
            return true;
        }else if (!currentUrl.startsWith(GA_URL) && !currentUrl.startsWith(CCSP_URL)) {
            //if(currentUrl.startsWith("https://nice.checkplus.co.kr")) {
//            if(!ccspType.equalsIgnoreCase(TYPE_AUTHUUID)) ga.clearLoginInfo();
//            ga.clearLoginInfo();
            fragment.loadUrl(url);
            setClearHistory(true);
            return true;
        }else if (currentUrl.startsWith(GA_URL)) {
            if(!fragment.canGoBack()) {
                exitPage("", 0);
                return true;
            }
        }
        return false;
    }
}
