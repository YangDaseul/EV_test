package com.genesis.apps.ui.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.ga.CCSP;
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

    private String tokenCode;
    private String authUuid;
    private String ccspType ="";
    @Inject
    public GA ga;
    @Inject
    public CCSP ccsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
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
        if (url.startsWith(GA_CALLBACK_URL)) { // 회원가입/로그인 관련
            return loginResult(uri);
        } else if (url.startsWith(GA_REDIRECT_URL)) { // 본인인증 관련
            return certifyResult(uri);
        } else{
            return false;
        }
    }

    private boolean certifyResult(Uri uri) {

        //https://t-accounts.genesis.com/api/test/redirect.do?state=xjnweledme&authUuid=8266ce77-1705-4785-9551-98d5047dcd89
        // GaRedirect URL은 APP에서 리턴 받을때 사용하는 url <-- App에서만 사용함.
        String result = uri.getQueryParameter("result");
        if ("1249".equals(result)) {
            //사용자 취소
            finish();
            return true;
        }
        String csrf = uri.getQueryParameter("state");
        String authUuid = uri.getQueryParameter("authUuid");
        Log.e("TEST LoginActivity", "LOGIN ACTIVITY:" +authUuid + "        state:"+csrf);
        if (csrf != null && csrf.equals(ga.getCsrf()) && !TextUtils.isEmpty(authUuid)) {
            Log.v("TEST LoginActivity", "LOGIN ACTIVITY DEPTH2:" +authUuid );
            this.authUuid = authUuid;
            if(ccspType.equalsIgnoreCase(TYPE_AUTHUUID)){ //폰번호 변경으로 본인인증 결과 값 요청 시
                setResult(Activity.RESULT_OK, new Intent().putExtra(VariableType.KEY_NAME_LOGIN_AUTH_UUID, authUuid));
                finish();
            }
        }
        return true;
    }

    private boolean loginResult(Uri uri) {
        String result = uri.getQueryParameter("result");
        //0000: 정상, 1126: GA가입ok & 서비스 미가입
        if ("0000".equals(result) || (ccspType.equalsIgnoreCase(TYPE_LOGIN) && "1126".equals(result))) {
            final String scope = uri.getQueryParameter("scope"); //로그인 혹은 회원가입 확인 가능
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
                    e.printStackTrace();
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
        if(currentUrl.endsWith("/web/v1/user/signin")||currentUrl.endsWith("password-search")){
            //로그인화면이거나 아이디찾기 및 비밀번호 초기화 화면일 경우
            finish();
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
                finish();
                return true;
            }
        }
        return false;
    }
}
