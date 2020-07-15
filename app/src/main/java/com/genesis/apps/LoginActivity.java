package com.genesis.apps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.ui.WebviewActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import static com.genesis.apps.comm.net.ga.GAInfo.GA_CALLBACK_URL;

public class LoginActivity extends WebviewActivity {
    private final String TAG = getClass().getSimpleName();
    private boolean isLogin=false;
    private String tokenCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPageType();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutDownExcutor();
    }

    private void getPageType() {
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
            String result = uri.getQueryParameter("result");
            //0000: 정상, 1126: GA가입ok & 서비스 미가입
            if ("0000".equals(result) || (isLogin && "1126".equals(result))) {
                final String scope = uri.getQueryParameter("scope");
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
        return false;
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
}
