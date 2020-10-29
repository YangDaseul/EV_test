package com.genesis.apps.ui.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.R;
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
    private boolean isLogin=false;
    private String tokenCode;

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
        if(intent!=null&&intent.getStringExtra("url").equalsIgnoreCase(ga.getLoginUrl())){
            isLogin=true;
        }else{
            isLogin=false;
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

        if (csrf != null && csrf.equals(ga.getCsrf()) && !TextUtils.isEmpty(authUuid)) {


//                    if("INIT".equalsIgnoreCase(tokenCode)) {
//                        //앱 최초 실행시 본인 인증 하는 경우
//                        ListenableFuture<JsonObject> f = listeningExecutorService.submit(() -> {
//                            try {
//                                return DKC.reqAuthChk(GWebviewCommonActivity.this, authUuid).call();
//                            } catch (DKC.DkcException e) {
//                                e.printStackTrace();
//                            } catch (HttpRequest.HttpRequestException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            return null;
//                        });
//                        addCallback(f, new FutureCallback<JsonObject>() {
//                            @Override
//                            public void onSuccess(JsonObject result) {
//                                if (result == null) {
//                                    MyUtils.oneButtonDialog(GWebviewCommonActivity.this, getString(R.string.instability_network), GWebviewCommonActivity.this::finishAndRemoveTaskCompat);
//                                    return;
//                                }
//                                String rspCode = DKC.getJson(result, "rspCode");
//                                String rspMessage = DKC.getJson(result, "rspMessage");
//                                String resultYn = DKC.getJson(result, "resultYn");
//
//                                if ("0000".equals(rspCode)) {
//                                    if("Y".equalsIgnoreCase(resultYn)) {
//                                        GlobalDataDao dao = DatabaseHolder.getInstance().getDatabase().globalDataDao();
//                                        dao.insert(new GlobalData(GlobalData.GLOBAL_SMS_CERTIFIED, "Y"));
//                                        LoginInfo loginInfo = CCSP.getInstance().getLoginInfo(GWebviewCommonActivity.this);
//                                        Log.v("parktest","444 custNumberCCSP:"+loginInfo.getProfile().getCustomerNumber() + "  custNumberDB:"+dao.get(GlobalData.GLOBAL_CUSTOMER_NUMBER));
//                                        dao.insert(new GlobalData(GlobalData.GLOBAL_CUSTOMER_NUMBER, loginInfo.getProfile().getCustomerNumber()));
//                                        setResult(RESULT_OK);
//                                        finish();
//                                        return;
//                                    }
//                                    else {
//                                        rspMessage = getString(R.string.alert_missmatch_auth_info);
//                                    }
//                                }
//
//                                CCSP.getInstance().clearLoginInfo(GWebviewCommonActivity.this);
//                                if(TextUtils.isEmpty(rspMessage)) {
//                                    rspMessage = getString(R.string.instability_network);
//                                }
//                                MyUtils.oneButtonDialog(GWebviewCommonActivity.this, rspMessage, () -> GSettingActivity.goIntro(GWebviewCommonActivity.this));
//                            }
//                            @Override
//                            public void onFailure(@NonNull Throwable t) {
//                                CCSP.getInstance().clearLoginInfo(GWebviewCommonActivity.this);
//                                t.printStackTrace();
//                                MyUtils.oneButtonDialog(GWebviewCommonActivity.this, getString(R.string.instability_network), GWebviewCommonActivity.this::finishAndRemoveTaskCompat);
//                            }
//                        }, uiThreadExecutor);
//                    }
//                    else {
//                        // 로그인 후 서비스 가입 안된 사람인데. 기존 인증방식이 아이핀 인경우 가입 화면으로 이동.
//                        GA.getInstance().serviceEnroll(GWebviewCommonActivity.this, tokenCode, authUuid);
//                    }
//                    return true;
        }
        return true;
    }

    private boolean loginResult(Uri uri) {
        String result = uri.getQueryParameter("result");
        //0000: 정상, 1126: GA가입ok & 서비스 미가입
        if ("0000".equals(result) || (isLogin && "1126".equals(result))) {
            final String scope = uri.getQueryParameter("scope"); //로그인 혹은 회원가입 확인 가능
            final String tokenCode = uri.getQueryParameter("code");
            Log.d(TAG, "shouldOverrideUrlLoading: scope = " + scope + ", tokenCode=" + tokenCode);
            if (TextUtils.isEmpty(tokenCode)) {
                finish();
                return true;
            }else {
                this.tokenCode = tokenCode;
            }
            Log.d(TAG, "shouldOverrideUrlLoading: scope = " + scope + ", tokenCode=" + tokenCode);
            ListenableFuture<NetResult> f = executorService.getListeningExecutorService().submit(() -> ga.checkEnroll(tokenCode, scope));
            f.addListener(() -> {
                try {
                    excuteResultEnroll(f.get());
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

    private void excuteResultEnroll(NetResult netResult) {
        if (netResult != null) {
            switch (netResult.getCode()) {
                case SUCCESS:
                    setResult(Activity.RESULT_OK);
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
        if (!currentUrl.startsWith(GA_URL) && !currentUrl.startsWith(CCSP_URL)) {
            //if(currentUrl.startsWith("https://nice.checkplus.co.kr")) {
            ccsp.clearLoginInfo();
            fragment.loadUrl(url);
            setClearHistory(true);
            return true;
        }else if (currentUrl.startsWith(GA_URL)) {
            if(!fragment.canGoBack()) {
                finish();
                return true;
            }
        }else if(currentUrl.contains("/web/v1/user/signin")){
            //로그인화면
            finish();
            return true;
        }
        return false;
    }
}
