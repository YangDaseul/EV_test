package com.genesis.apps.comm.net.ga;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.genesis.apps.R;
import com.genesis.apps.comm.net.HttpRequest;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetException;
import com.genesis.apps.comm.util.QueryString;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import static com.genesis.apps.comm.net.ga.GAInfo.CCSP_CLIENT_ID;
import static com.genesis.apps.comm.net.ga.GAInfo.CCSP_SECRET;
import static com.genesis.apps.comm.net.ga.GAInfo.GA_CALLBACK_URL;
import static com.genesis.apps.comm.net.ga.GAInfo.GA_REDIRECT_URL;
import static com.genesis.apps.comm.net.ga.GAInfo.GA_URL;

public class GA {
    private String csrf;
    private CCSP ccsp;
    private NetCaller netCaller;
    @Inject
    public GA(CCSP ccsp, NetCaller netCaller) {
        this.ccsp = ccsp;
        this.netCaller = netCaller;
    }

    private String getEnrollUrl(Context context) {
        csrf = getRandomString(10);
        Log.d("GA", "csrf string : " + csrf);

        QueryString q = new QueryString();
        q.add("clientId", CCSP_CLIENT_ID);
        q.add("state", csrf);
        q.add("scope", "signup");
        q.add("style", "page");
        q.add("redirectUrl", GA_REDIRECT_URL);

        return GA_URL + "/auth.do" + q.getQuery();
    }

    private String getLoginUrl(Context context) {
        csrf = getRandomString(10);
        QueryString q = new QueryString();
        q.add("clientId", CCSP_CLIENT_ID);
        q.add("host", GA_URL);
        q.add("scope", "url.login");
        q.add("lang", "ko");

        return GA_URL + "/api/authorize/ccsp/oauth" + q.getQuery();
    }

//    public void enrollActivity(Activity act, int requestCode) {
//        try {
//            Intent intent = new Intent(act, GWebviewCommonActivity.class);
//            intent.putExtra("url", getEnrollUrl(act));
//            act.startActivityForResult(intent, requestCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//    public void loginActivity(Activity act, int requestCode) {
//        try {
//            Intent intent = new Intent(act, GWebviewCommonActivity.class);
//            intent.putExtra("url", getLoginUrl(act));
//            act.startActivityForResult(intent, requestCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    //null : 잘못된 접근,  http 또는 에러 메시지
    public String getSignoutUrl(Activity activity, String accessToken) {
        if(activity == null) return null;

        String msg = activity.getString(R.string.instability_network);
        if(TextUtils.isEmpty(accessToken)) return msg;

        String url = GA_URL + "/api/account/ccsp/user/signout?url=" + GA_REDIRECT_URL;

        HttpRequest request = netCaller.getRequest(url);
        request.accept(HttpRequest.CONTENT_TYPE_JSON);
        request.header("clientId", CCSP_CLIENT_ID);
        request.header("clientSecret", CCSP_SECRET);
        request.header("access_token", accessToken);

        try {
            JsonObject data = netCaller.getData(request);
            if(data != null) {
                boolean success = data.get("success").getAsBoolean();
                if(success) {
                    data = data.getAsJsonObject("data");
                    msg = netCaller.getJson(data, "location");
                }
                else {
                    msg = netCaller.getJson(data, "message");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.disconnect();

        return msg;
    }

    void updateAccessToken() throws NetException {
        Log.d("GA", "updateAccessToken()");

        //long currentTime = System.currentTimeMillis();
        String url = GA_URL + "/api/account/ccsp/user/oauth2/token?grant_type=refresh_token";

        Map<String, Object> params = new HashMap<>();
        params.put("refresh_token", ccsp.getLoginInfo().getRefreshToken());
        params.put("redirect_uri", GA_CALLBACK_URL);

        HttpRequest request = netCaller.getPostRequest(url)
                .header("Authorization", "Basic " + Base64.encodeToString((CCSP_CLIENT_ID + ":" + CCSP_SECRET).getBytes(), Base64.NO_WRAP));


        String accessToken = "";
        JsonObject ret = netCaller.form(request, params);
        if(ret != null) {
            boolean success = ret.get("success").getAsBoolean();
            if(success) {
                accessToken = ret.get("access_token").getAsString();
            }
        }
        ccsp.getLoginInfo().setAccessToken(accessToken);
        ccsp.updateLoginInfo();

        request.disconnect();
    }
    private JsonObject getRefreshToken(String tokenCode) {
        String url = GA_URL + "/api/account/genesis/service/token";

        Map<String, Object> params = new HashMap<>();
        params.put("tokenCode", tokenCode);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", GA_CALLBACK_URL);

        HttpRequest request = netCaller.getPostRequest(url);
        request.contentType(HttpRequest.CONTENT_TYPE_FORM, HttpRequest.CHARSET_UTF8);
        request.accept(HttpRequest.CONTENT_TYPE_JSON);
        request.basic(CCSP_CLIENT_ID, CCSP_SECRET);

        JsonObject data = null;
        try {
            data = netCaller.form(request, params);
            if (data != null) {
                String code = netCaller.getJson(data, "code");
                if("0000".equals(code) && data.has("access_token") && data.has("refresh_token") && data.has("expires_in")) {
                    String accessToken = netCaller.getJson(data, "access_token");
                    String refreshToken = netCaller.getJson(data, "refresh_token");
                    int expiresIn = data.get("expires_in").getAsInt();
                    long currentTime = System.currentTimeMillis();
                    JsonObject data2 = data.getAsJsonObject("data");
                    LoginInfoDTO loginInfo = new LoginInfoDTO(accessToken, refreshToken, currentTime + (expiresIn * 1000), null, currentTime + 31557600000L, netCaller.getJson(data2, "tokenCode"));
                    if(!getProfile(accessToken, loginInfo)) {
                        data = null;
                        ccsp.clearLoginInfo();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.disconnect();

        return data;
    }

    private boolean getProfile(String accessToken, LoginInfoDTO loginInfoDTO) {
        if(TextUtils.isEmpty(accessToken) || loginInfoDTO == null) return false;

        String url = GA_URL + "/api/account/ccsp/user/profile";

        HttpRequest request = netCaller.getRequest(url);
        request.accept(HttpRequest.CONTENT_TYPE_JSON);
        request.header("clientId", CCSP_CLIENT_ID);
        request.header("clientSecret", CCSP_SECRET);
        request.header("access_token", accessToken);

        try {
            JsonObject data = netCaller.getData(request);
            if(data != null) {
                boolean success = data.get("success").getAsBoolean();
                if(success) {
                    data = data.getAsJsonObject("data");
//                    Log.v("parkTest","getRefreshToken:saveid server:"+DKC.getJson(data, "id"));
                    loginInfoDTO.setProfile(new UserProfileVO(
                            netCaller.getJson(data, "id"),
                            netCaller.getJson(data, "email"),
                            netCaller.getJson(data, "name"),
                            netCaller.getJson(data, "mobileNum"),
                            netCaller.getJson(data, "birthdate"),
                            data.get("status").getAsInt(),
                            netCaller.getJson(data, "lang"),
                            data.get("social").getAsBoolean(),
                            "",
                            ""
                    ));

                    ccsp.setGaLoginInfo(loginInfoDTO);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

//    // 0 : 정상 / 다음 액션 처리.., 1 : 여기서 팝업처리 / webview에서는 암껏도 안함., -1 : 비정상 / 네트워크 팝업 띄우고 화면 닫기
//    public int checkEnroll(Activity activity, String tokenCode, String scope) {
//        if(activity == null) return -1;
//
//
//        try {
//            JsonObject data = getRefreshToken(activity, tokenCode);
//            if (data != null) {
//                String code = netCaller.getJson(data, "code");
//                if("0000".equals(code) && data.has("access_token") && data.has("refresh_token") && data.has("expires_in")) {
//                    GlobalDataDao dao =   DatabaseHolder.getInstance().getDatabase().globalDataDao();
//                    switch (checkServiceRegistered(activity,scope,dao)){
//                        case GA.CHECK_SERVICE_RESULT_LOGININFO:
//                            // 이전에 로그인 한 사용자와 현재 로그인한 사용자가 다른 경우
//                            CCSP.getInstance().clearLoginInfo(activity);
//                            MyUtils.modalOneButtonDialog(activity, activity.getString(R.string.login_2), ((BaseActivity) activity)::finishAndRemoveTaskCompat);
//                            return 1;
//                        case GA.CHECK_SERVICE_RESULT_SUCCESS:
//                            boolean isDeviceLock = isDeviceLocked(activity);
//                            boolean isUserLocked = isUserLocked(activity);
//
//                            Runnable goPinAuthRunnable = () -> activity.startActivityForResult(new Intent(activity, PinAuthActivity.class), RequestCodes.REQUEST_CODE_PIN);
//                            Runnable goLogoutRunnable = () -> {
//                                CCSP.getInstance().clearLoginInfo(activity);
//                                if(activity instanceof GWebviewCommonActivity) {
//                                    ((GWebviewCommonActivity)activity).reload();
//                                    ((GWebviewCommonActivity)activity).setClearHistory(true);
//                                }
//                                else {
//                                    ((BaseActivity) activity).finishAndRemoveTaskCompat();
//                                }
//                            };
//
//                            if (!isDeviceLock && !isUserLocked) {
//                                // 일시정지하지 않음. 일반 케이스
//                                String certified = DKC.IS_FIRST_AUTH_PATH ? "Y" : dao.get(GlobalData.GLOBAL_SMS_CERTIFIED);
//                                if(!"Y".equalsIgnoreCase(certified)) {
//                                    MyUtils.oneButtonDialog(activity, activity.getString(R.string.alert_init_app_first_run), () -> goAuth(activity, "INIT"));
//                                    return 1;
//                                }
//                                else {
//                                    Log.v("parktest","111 custNumberCCSP:"+CCSP.getInstance().getLoginInfo(activity).getProfile().getCustomerNumber() + "  custNumberDB:"+dao.get(GlobalData.GLOBAL_CUSTOMER_NUMBER));
//                                    // 로그인 한 사용자 정보 갱신
//                                    dao.insert(new GlobalData(GlobalData.GLOBAL_CUSTOMER_NUMBER, CCSP.getInstance().getLoginInfo(activity).getProfile().getCustomerNumber()));
//                                    return 0;
//                                }
//                            } else  if (isDeviceLock && !isUserLocked) {
//                                // 일시정지된 사용자의 단말을 사용하려고 하는 케이스
//                                CCSP.getInstance().clearLoginInfo(activity);
//                                MyUtils.modalOneButtonDialog(activity, activity.getString(R.string.login_3), ((BaseActivity) activity)::finishAndRemoveTaskCompat);
//                                return 1;
//                            } else  if (!isDeviceLock) { // && isUserLocked) {
//                                // 일시정지된 사용자가 변경된 기기로 로그인 (lock 해제 시도)
//                                MyUtils.modalTwoButtonDialog(activity, R.string.service_stop_cancel_title, R.string.service_stop_cancel_message
//                                        , goPinAuthRunnable
//                                        , goLogoutRunnable);
//                                return 1;
//                            } else {
//                                // 일시정지된 사용자, 단말 (lock 해제 시도)
//                                MyUtils.modalTwoButtonDialog(activity, R.string.service_stop_cancel_title, R.string.service_stop_cancel_message
//                                        , goPinAuthRunnable
//                                        , goLogoutRunnable);
//                                return 1;
//                            }
//                        case GA.CHECK_SERVICE_RESULT_FAIL:
//                            if ("url.signup".equalsIgnoreCase(scope)) {
//                                serviceEnroll(activity, tokenCode, "");
//                            } else if ("url.login".equalsIgnoreCase(scope)) {
//                                // 로그인 후 서비스 가입이 되어 있지 않는 사용자인 경우 본인 인증 후 가입 하도록 수정 처리.
//                                MyUtils.twoButtonDialog(activity, activity.getString(R.string.enroll_service_enroll_confirm), () -> goAuth(activity, tokenCode), activity::finish);
//
////                                if(data.has("data") && data.get("data") != null && !data.get("data").isJsonNull()) {
////                                    data = data.getAsJsonObject("data");
////                                    String authType = DKC.getJson(data, "authType");
////                                    if("P".equals(authType)) {
////                                        //iPin 본인 인증인 경우 nice 본인 인증으로 이동 필요.
////                                        MyUtils.twoButtonDialog(activity, activity.getString(R.string.enroll_service_enroll_confirm), () -> goAuth(activity, tokenCode), activity::finish);
////                                        return 1;
////                                    }
////                                }
////                                MyUtils.twoButtonDialog(activity, activity.getString(R.string.enroll_service_enroll_confirm), () -> serviceEnroll(activity, tokenCode, ""), activity::finish);
//                            }
//                            return 1;
//                    }
//                }
//                else {
//                    String msg = DKC.getJson(data, "message");
//                    if(TextUtils.isEmpty(msg)) msg = activity.getString(R.string.instability_network);
//                    MyUtils.oneButtonDialog(activity, msg, activity::finish);
//                    return 1;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }

//    private boolean isUserLocked(Context context) {
//        JsonObject intro = null;
//        try {
//            intro = DKC.reqIntro(context).call();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (intro != null) {
//            String rspCode = intro.get("rspCode").getAsString();
//            return ("2004".equalsIgnoreCase(rspCode) || "9002".equalsIgnoreCase(rspCode));
//        } else {
//            return false;
//        }
//    }
//    private boolean isDeviceLocked(Context context) {
//        JsonObject intro = null;
//        try {
//            intro = DKC.reqIntroWithoutCustomerNumber(context).call();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (intro != null) {
//            String rspCode = intro.get("rspCode").getAsString();
//            return ("2004".equalsIgnoreCase(rspCode) || "9002".equalsIgnoreCase(rspCode));
//        } else {
//            return false;
//        }
//    }


    private String getAuthUrl() {
        csrf = getRandomString(10);
        Log.d("GA", "csrf string : " + csrf);

        QueryString q = new QueryString();
        q.add("clientId", CCSP_CLIENT_ID);
        q.add("state", csrf);
        q.add("style", "page");
        q.add("redirectUrl", GA_REDIRECT_URL);

        return GA_URL + "/auth.do" + q.getQuery();
    }
//    public synchronized void goAuth(Activity activity, String tokenCode) {
//        try {
//            Intent intent = new Intent(activity, GWebviewCommonActivity.class);
//            intent.putExtra("url", getAuthUrl());
//            intent.putExtra("tokenCode", tokenCode);
//            activity.startActivityForResult(intent, RequestCodes.REQUEST_CODE_ENROLL_SERVICE);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//    public synchronized void serviceEnroll(Activity activity, String tokenCode, String authUuid) {
//        Intent intent = new Intent(activity, GServiceEnrollActivity.class);
//        intent.putExtra("tokenCode", tokenCode);
//        intent.putExtra("authUuid", authUuid);
//        activity.startActivityForResult(intent, RequestCodes.REQUEST_CODE_ENROLL_SERVICE);
//    }

//    public static final int CHECK_SERVICE_RESULT_SUCCESS=1;
//    public static final int CHECK_SERVICE_RESULT_FAIL=2;
//    public static final int CHECK_SERVICE_RESULT_LOGININFO=3;
//    private int checkServiceRegistered(Context context, String scope, GlobalDataDao dao) {
//        // 서비스 가입 여부 확인
//        JsonObject result = null;
//        try {
//            result =  DKC.reqMasterUid(context).call();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (result != null) {
//            if (result.has("custNo") && result.get("custNo") != null && !result.get("custNo").isJsonNull()) {
//                return checkLoginStatus(context,scope,dao,result.get("custNo").getAsString());
////                if (!TextUtils.isEmpty(custNo)) {
////                    if("url.login".equalsIgnoreCase(scope)) {
////                        String custNoDB = dao.get(GlobalData.GLOBAL_CUSTOMER_NUMBER);
////                        if (!TextUtils.isEmpty(custNoDB)) {
////                            if (!custNoDB.equalsIgnoreCase(custNo)) {
////                                // 이전에 로그인 한 사용자와 현재 로그인한 사용자가 다른 경우
////                                return CHECK_SERVICE_RESULT_LOGININFO;
////                            }
////                        }
////                    }
////                    // 서비스 가입 상태
////                    CCSP.getInstance().setCustomerNumber(context, custNo);
////                    dao.insert(new GlobalData(GlobalData.GLOBAL_CUSTOMER_NUMBER, CCSP.getInstance().getLoginInfo(context).getProfile().getCustomerNumber()));
////                    return CHECK_SERVICE_RESULT_SUCCESS;
////                }
//            }
//        }
//
//        return CHECK_SERVICE_RESULT_FAIL;
//    }
//
//    public static int checkLoginStatus(Context context, String scope, GlobalDataDao dao, String custNo){
//            if (!TextUtils.isEmpty(custNo)) {
//                if("url.login".equalsIgnoreCase(scope)) {
//                    String custNoDB = dao.get(GlobalData.GLOBAL_CUSTOMER_NUMBER);
//                    if (!TextUtils.isEmpty(custNoDB)) {
//                        if (!custNoDB.equalsIgnoreCase(custNo)) {
//                            // 이전에 로그인 한 사용자와 현재 로그인한 사용자가 다른 경우
//                            return CHECK_SERVICE_RESULT_LOGININFO;
//                        }
//                    }
//                }
//                Log.v("parktest","555 custNumberCCSP:"+custNo + "  custNumberDB:"+dao.get(GlobalData.GLOBAL_CUSTOMER_NUMBER));
//                // 서비스 가입 상태
//                CCSP.getInstance().setCustomerNumber(context, custNo);
//                dao.insert(new GlobalData(GlobalData.GLOBAL_CUSTOMER_NUMBER, CCSP.getInstance().getLoginInfo(context).getProfile().getCustomerNumber()));
//                return CHECK_SERVICE_RESULT_SUCCESS;
//            }
//        return CHECK_SERVICE_RESULT_FAIL;
//    }



    public String getTokenCode() {
        return ccsp.getLoginInfo() == null ? "" : ccsp.getLoginInfo().getTokenCode();
    }
    public String getAccessToken(Context context) {
        return ccsp.getLoginInfo() == null ? "" : ccsp.getLoginInfo().getAccessToken();
    }

    public String getCustomerNumber(Context context) {
        if(ccsp.getLoginInfo() == null||ccsp.getLoginInfo().getProfile()==null)
            return "";
        else
            return ccsp.getLoginInfo().getProfile().getCustomerNumber();
    }
//    public String getHmcCustNo(Context context) {
//        if(CCSP.getInstance().getLoginInfo(context) == null) return "";
//        if(CCSP.getInstance().getLoginInfo(context).getProfile() == null) return "";
//        return CCSP.getInstance().getLoginInfo(context).getProfile().getHmcCustNo();
//    }
//    public void setHmcCustNo(Context context, String hmcCustNo) {
//        if(CCSP.getInstance().getLoginInfo(context) == null) return;
//        if(CCSP.getInstance().getLoginInfo(context).getProfile() == null) return;
//        CCSP.getInstance().getLoginInfo(context).getProfile().setHmcCustNo(hmcCustNo);
//        CCSP.getInstance().updateLoginInfo(context);
//    }

    private Random random = new Random();
    private String getRandomString(int len) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        StringBuilder buffer = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
