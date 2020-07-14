package com.genesis.apps.comm.net.ga;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.comm.net.HttpRequest;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetException;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.EntryPoints;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

import static com.genesis.apps.comm.net.ga.GAInfo.CCSP_URL;
import static com.genesis.apps.comm.net.ga.GAInfo.HTTP_HEADER_NAME;
import static com.genesis.apps.comm.net.ga.GAInfo.HTTP_HEADER_VALUE;
import static com.genesis.apps.comm.net.ga.GAInfo.TAG_MSG_LOGININFO;

public class CCSP {
    private static final String TAG = CCSP.class.getSimpleName();
    private NetCaller netCaller;
    private LoginInfoDTO loginInfoDTO;
    private Context context;
    private int retryCount = 0;

    @Inject
    public CCSP(NetCaller netCaller, Context context){
        this.netCaller = netCaller;
        this.context = context;
    }

    @EntryPoint
    @InstallIn(ApplicationComponent.class)
    interface GAInterface {
        GA getGA();
    }

    public boolean isLoggedIn() {
        LoginInfoDTO loginInfoDTO = getLoginInfo();
        if (loginInfoDTO != null && loginInfoDTO.getProfile() != null) {
            // 로그인 정보 있음
            Log.d(TAG, "로그인 정보 있음");
            if (!TextUtils.isEmpty(loginInfoDTO.getAccessToken()) && !TextUtils.isEmpty(loginInfoDTO.getRefreshToken())) {
                if (TextUtils.isEmpty(loginInfoDTO.getProfile().getCustomerNumber())) {
                    // 고객 번호 없음. == 서비스 미 가입
                    Log.d(TAG, "고객 번호 없음. == 서비스 미 가입");
                    clearLoginInfo();
                    return false;
                }

                long t = loginInfoDTO.getRefreshTokenExpriesDate() - System.currentTimeMillis();
                if (t > 0) {
                    // 로그인 기간 남음
                    Log.d(TAG, "로그인 기간 남음");
                    // xx 분 미만으로 남으면 로그아웃 처리???
                    return true;
                } else {
                    // 토큰 만료
                    Log.d(TAG, "[CCSP] 토큰 만료");
                    return false;
                }
            } else {
                // 토큰 또는 사용자 정보 없음.
                Log.d(TAG, "토큰 또는 사용자 정보 없음 1");
                return false;
            }
        } else {
            // 토큰 또는 사용자 정보 없음.
            Log.d(TAG, "토큰 또는 사용자 정보 없음 2");
            return false;
        }
    }

    public JsonObject getControlToken(String pin) throws NetException {
        String url = CCSP_URL + "/api/v1/user/pin";

        Map<String, Object> params = new HashMap<>();
        params.put("pin", pin);
        params.put("deviceId", "");

        HttpRequest request = netCaller.getPutRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfoDTO.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);

        return netCaller.send(request, params);
    }

    public void changePin(String currentPin, String changedPin) throws NetException {
        String url = CCSP_URL + "/api/v1/user/profile";

        Map<String, Object> pinParams = new HashMap<>();
        pinParams.put("current", currentPin);
        pinParams.put("change", changedPin);

        Map<String, Object> params = new HashMap<>();
        params.put("pin", pinParams);

        HttpRequest request = netCaller.getPutRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfoDTO.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);

        netCaller.send(request, params);
    }

    public void resetPin() throws NetException {
        String url = CCSP_URL + "/api/v1/user/profile/pin";

        HttpRequest request = netCaller.getDeleteRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfoDTO.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);

        try {
            netCaller.getData(request);
        }catch (NetException e) {
            throw e;
        }
    }

    public JsonObject postDataWithAccessToken(String url, Map<String, Object> params) throws NetException {
        return postDataWithToken(url, params, loginInfoDTO.getAccessToken());
    }

    public JsonObject postDataWithToken(String url, Map<String, Object> params, String token) throws NetException {
//        token = loginInfo.getAccessToken();
        HttpRequest request = netCaller.getPostRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + token);
        Log.d(TAG, "Authorization:Bearer " + token);
        JsonObject ret = null;

        try {
            ret = netCaller.send(request, params);
            retryCount = 0;
        } catch (NetException e) {
            int status = e.getStatusCode();
            String body = e.getBody();
            JsonObject json = e.getJson();
            if (status == 403 || status == 401) {
                Log.d(TAG, "accessToekn expired.");
                // access toekn, refresh token 만료시 정확히 어떤 값이 리턴되는지 확인 필요
                if (retryCount!=0)  retryCount = 0;
                else  e = null;

                retryCount++;
                try {
                    GAInterface gaInterface = EntryPoints.get(this, GAInterface.class);
                    gaInterface.getGA().updateAccessToken();
                } catch (NetException e2) {
                    e = e2;
                }

                if(e != null) {
                    throw e;
                }
                return postDataWithAccessToken(url, params);
            }
        }

        return ret;
    }

    public void setCustomerNumber(String customerNumber) {
        getLoginInfo().getProfile().setCustomerNumber(customerNumber);
        updateLoginInfo();
    }

    public LoginInfoDTO getLoginInfo() {
        loginInfoDTO = (loginInfoDTO !=null ? loginInfoDTO : loginInfoDTO.loadLoginInfo(context));
        Log.d(TAG, TAG_MSG_LOGININFO + (loginInfoDTO !=null ? loginInfoDTO.toString() : "null"));
        return loginInfoDTO;
    }

    public boolean updateLoginInfo() {
        return loginInfoDTO.updateLoginInfo(context, loginInfoDTO);
    }

    /**
     * 로그인 정보 삭제 - 로그아웃을 위한 처리
     */
    public void clearLoginInfo() {
        try {
            loginInfoDTO.clearLoginInfo(context);
            loginInfoDTO = null;
        }catch (Exception ignore){

        }
    }

    //GA에서 로그인 또는 서비스 가입 이후 토큰 정보 설정시 사용.
    public void setGaLoginInfo(LoginInfoDTO loginInfoDTO) {
        try {
            this.loginInfoDTO = loginInfoDTO;
            updateLoginInfo();
        }catch (Exception ignore){

        }
    }
}
