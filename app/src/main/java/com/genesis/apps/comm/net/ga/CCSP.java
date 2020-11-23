package com.genesis.apps.comm.net.ga;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.comm.net.HttpRequest;
import com.genesis.apps.comm.net.HttpRequestUtil;
import com.genesis.apps.comm.net.NetException;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

import static com.genesis.apps.comm.model.constants.GAInfo.CCSP_URL;
import static com.genesis.apps.comm.model.constants.GAInfo.HTTP_HEADER_NAME;
import static com.genesis.apps.comm.model.constants.GAInfo.HTTP_HEADER_VALUE;
import static com.genesis.apps.comm.model.constants.GAInfo.TAG_MSG_LOGININFO;

public class CCSP {
    private static final String TAG = CCSP.class.getSimpleName();
    private HttpRequestUtil httpRequestUtil;
    private LoginInfoDTO loginInfoDTO;
    private Context context;

    @Inject
    public CCSP(HttpRequestUtil httpRequestUtil, Context context, LoginInfoDTO loginInfoDTO){
        this.httpRequestUtil = httpRequestUtil;
        this.context = context;
        this.loginInfoDTO = loginInfoDTO;
    }

    @EntryPoint
    @InstallIn(ApplicationComponent.class)
    interface GAInterface {
        GA getGA();
    }
    //todo 앱 실행 시 토큰 확인하는 부분은 추가 필요..
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

//    public JsonObject getControlToken(String pin) throws NetException {
//        String url = CCSP_URL + "/user/pin";
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("pin", pin);
//        params.put("deviceId", "");
//
//        HttpRequest request = httpRequestUtil.getPutRequest(url);
//        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfoDTO.getAccessToken());
//        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
//
//        return httpRequestUtil.send(request, params);
//    }

//    public void changePin(String currentPin, String changedPin) throws NetException {
//        String url = CCSP_URL + "/user/profile";
//
//        Map<String, Object> pinParams = new HashMap<>();
//        pinParams.put("current", currentPin);
//        pinParams.put("change", changedPin);
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("pin", pinParams);
//
//        HttpRequest request = httpRequestUtil.getPutRequest(url);
//        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfoDTO.getAccessToken());
//        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
//
//        httpRequestUtil.send(request, params);
//    }
//
//    public void resetPin() throws NetException {
//        String url = CCSP_URL + "/user/profile/pin";
//
//        HttpRequest request = httpRequestUtil.getDeleteRequest(url);
//        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfoDTO.getAccessToken());
//        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
//
//        try {
//            httpRequestUtil.getData(request);
//        }catch (NetException e) {
//            throw e;
//        }
//    }
//    public void setCustomerNumber(String customerNumber) {
//        getLoginInfo().getProfile().setCustomerNumber(customerNumber);
//        updateLoginInfo();
//    }

    public LoginInfoDTO getLoginInfo() {
        loginInfoDTO = (loginInfoDTO.getProfile() !=null ? loginInfoDTO : loginInfoDTO.loadLoginInfo());
        Log.d(TAG, TAG_MSG_LOGININFO + (loginInfoDTO !=null ? loginInfoDTO.toString() : "null"));
        return loginInfoDTO;
    }

    public boolean updateLoginInfo() {
        return loginInfoDTO.updateLoginInfo(loginInfoDTO);
    }

    /**
     * 로그인 정보 삭제 - 로그아웃을 위한 처리
     */
    public void clearLoginInfo() {
        try {
            loginInfoDTO.clearLoginInfo();
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
