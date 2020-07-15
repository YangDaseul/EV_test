package com.genesis.apps.comm.net.ga;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.genesis.apps.R;
import com.genesis.apps.comm.net.HttpRequest;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetException;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.util.QueryString;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import static com.genesis.apps.comm.net.NetStatusCode.ERR_EXCEPTION_GA;
import static com.genesis.apps.comm.net.NetStatusCode.ERR_EXCEPTION_UNKNOWN;
import static com.genesis.apps.comm.net.NetStatusCode.SUCCESS;
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

    public String getCsrf(){
        return csrf;
    }

    public String getEnrollUrl() {
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

    public String getLoginUrl() {
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

    public NetResult checkEnroll(String tokenCode, String scope) {

        try {
            JsonObject data = getRefreshToken(tokenCode);
            if (data != null) {
                String code = netCaller.getJson(data, "code");
                if("0000".equals(code) && data.has("access_token") && data.has("refresh_token") && data.has("expires_in")) {
                    return new NetResult(SUCCESS,0, data);
                }else {
                    String msg = netCaller.getJson(data, "message");
                    if(TextUtils.isEmpty(msg))
                        return new NetResult(ERR_EXCEPTION_UNKNOWN,R.string.error_msg_5, null);
                    else
                        return new NetResult(ERR_EXCEPTION_GA,R.string.error_msg_5, msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new NetResult(ERR_EXCEPTION_UNKNOWN,R.string.error_msg_5, null);
    }

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
    public String getAccessToken() {
        return ccsp.getLoginInfo() == null ? "" : ccsp.getLoginInfo().getAccessToken();
    }

    public String getCustomerNumber() {
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
