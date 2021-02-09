package com.genesis.apps.comm.net.ga;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.UserProfileVO;
import com.genesis.apps.comm.net.HttpRequest;
import com.genesis.apps.comm.net.HttpRequestUtil;
import com.genesis.apps.comm.net.NetException;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.util.QueryString;
import com.google.gson.JsonObject;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.genesis.apps.comm.model.constants.GAInfo.CCSP_CLIENT_ID;
import static com.genesis.apps.comm.model.constants.GAInfo.CCSP_SECRET;
import static com.genesis.apps.comm.model.constants.GAInfo.GA_CALLBACK_URL;
import static com.genesis.apps.comm.model.constants.GAInfo.GA_REDIRECT_URL;
import static com.genesis.apps.comm.model.constants.GAInfo.GA_URL;
import static com.genesis.apps.comm.model.constants.GAInfo.HTTP_HEADER_NAME;
import static com.genesis.apps.comm.model.constants.GAInfo.HTTP_HEADER_VALUE;
import static com.genesis.apps.comm.net.NetStatusCode.ERR_EXCEPTION_GA;
import static com.genesis.apps.comm.net.NetStatusCode.ERR_EXCEPTION_UNKNOWN;
import static com.genesis.apps.comm.net.NetStatusCode.SUCCESS;

public class GA {
    private static final String TAG=GA.class.getSimpleName();
    private String csrf;
    private HttpRequestUtil httpRequestUtil;
    private int retryCount=0;
    private LoginInfoDTO loginInfoDTO;
    @Inject
    public GA(HttpRequestUtil httpRequestUtil, LoginInfoDTO loginInfoDTO) {
        this.httpRequestUtil = httpRequestUtil;
        this.loginInfoDTO = loginInfoDTO;
    }

    public String getCsrf(){
        return csrf;
    }

    public String getFindUrl() {
        return getLoginUrl().replaceAll("url.login", "url.find");
    }


    public String getEnrollUrl() {
        csrf = getRandomString(10);
        Log.e("GA", "enroll csrf string : " + csrf);

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
        Log.e("GA", "login csrf string : " + csrf);
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
    public String getSignoutUrl() {
        String msg = "";
        String accessToken = getAccessToken();
        if(TextUtils.isEmpty(accessToken)) return msg;

        String url = GA_URL + "/api/account/ccsp/user/signout?url=" + GA_REDIRECT_URL;

        HttpRequest request = httpRequestUtil.getRequest(url);
        request.accept(HttpRequest.CONTENT_TYPE_JSON);
        request.header("clientId", CCSP_CLIENT_ID);
        request.header("clientSecret", CCSP_SECRET);
        request.header("access_token", accessToken);

        try {
            JsonObject data = httpRequestUtil.getData(request);
            if(data != null) {
                boolean success = data.get("success").getAsBoolean();
                if(success) {
                    data = data.getAsJsonObject("data");
                    msg = httpRequestUtil.getJson(data, "location");
                }
                else {
                    msg = httpRequestUtil.getJson(data, "message");
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
        params.put("refresh_token", loginInfoDTO.getRefreshToken());
        params.put("redirect_uri", GA_CALLBACK_URL);

        HttpRequest request = httpRequestUtil.getPostRequest(url)
                .header("Authorization", "Basic " + Base64.encodeToString((CCSP_CLIENT_ID + ":" + CCSP_SECRET).getBytes(), Base64.NO_WRAP));


        String accessToken = "";
        JsonObject ret = httpRequestUtil.form(request, params);
        if(ret != null) {
            boolean success = ret.get("success").getAsBoolean();
            if(success) {
                accessToken = ret.get("access_token").getAsString();
            }
        }
        //2021-02-08 액세스 토큰이 만료상태면 로그아웃 처리
        //인트로에서 앱 실행 시 액세스토큰이 없으면 db를 클리어 진행
        if(!TextUtils.isEmpty(accessToken)) {
            loginInfoDTO.setAccessToken(accessToken);
            loginInfoDTO.updateLoginInfo(loginInfoDTO);
        }else{
            loginInfoDTO.clearLoginInfo();
        }

        request.disconnect();
    }
    private JsonObject getRefreshToken(String tokenCode) {
        String url = GA_URL + "/api/account/genesis/service/token";

        Map<String, Object> params = new HashMap<>();
        params.put("tokenCode", tokenCode);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", GA_CALLBACK_URL);

        HttpRequest request = httpRequestUtil.getPostRequest(url);
        request.contentType(HttpRequest.CONTENT_TYPE_FORM, HttpRequest.CHARSET_UTF8);
        request.accept(HttpRequest.CONTENT_TYPE_JSON);
        request.basic(CCSP_CLIENT_ID, CCSP_SECRET);

        JsonObject data = null;
        try {
            data = httpRequestUtil.form(request, params);
            if (data != null) {
                String code = httpRequestUtil.getJson(data, "code");
                if("0000".equals(code) && data.has("access_token") && data.has("refresh_token") && data.has("expires_in")) {
                    String accessToken = httpRequestUtil.getJson(data, "access_token");
                    String refreshToken = httpRequestUtil.getJson(data, "refresh_token");
                    int expiresIn = data.get("expires_in").getAsInt();
                    long currentTime = System.currentTimeMillis();
                    JsonObject data2 = data.getAsJsonObject("data");
                    loginInfoDTO.refereshData(accessToken, refreshToken, currentTime + (expiresIn * 1000), null, currentTime + 31557600000L, httpRequestUtil.getJson(data2, "tokenCode"));
//                    LoginInfoDTO loginInfo = new LoginInfoDTO(accessToken, refreshToken, currentTime + (expiresIn * 1000), null, currentTime + 31557600000L, httpRequestUtil.getJson(data2, "tokenCode"));
                    if(!getProfile(accessToken, loginInfoDTO)) {
                        data = null;
//                        ccsp.clearLoginInfo();
                        clearLoginInfo(); //todo 2020-11-19 park ga logininfodto에서 클리어 진행
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.disconnect();

        return data;
    }

    public void clearLoginInfo(){
        loginInfoDTO.clearLoginInfo();
    }

    private boolean getProfile(String accessToken, LoginInfoDTO loginInfoDTO) {
        if(TextUtils.isEmpty(accessToken) || loginInfoDTO == null) return false;

        String url = GA_URL + "/api/account/ccsp/user/profile";

        HttpRequest request = httpRequestUtil.getRequest(url);
        request.accept(HttpRequest.CONTENT_TYPE_JSON);
        request.header("clientId", CCSP_CLIENT_ID);
        request.header("clientSecret", CCSP_SECRET);
        request.header("access_token", accessToken);

        try {
            JsonObject data = httpRequestUtil.getData(request);
            if(data != null) {
                boolean success = data.get("success").getAsBoolean();
                if(success) {
                    data = data.getAsJsonObject("data");
//                    Log.v("parkTest","getRefreshToken:saveid server:"+DKC.getJson(data, "id"));
                    loginInfoDTO.setProfile(new UserProfileVO(
                            httpRequestUtil.getJson(data, "id"),
                            httpRequestUtil.getJson(data, "email"),
                            httpRequestUtil.getJson(data, "name"),
                            httpRequestUtil.getJson(data, "mobileNum"),
                            httpRequestUtil.getJson(data, "birthdate"),
                            data.get("status").getAsInt(),
                            httpRequestUtil.getJson(data, "lang"),
                            data.get("social").getAsBoolean(),
                            "",
                            ""
                    ));

                    loginInfoDTO.updateLoginInfo(loginInfoDTO);
//                    ccsp.setGaLoginInfo(loginInfoDTO);//todo 2020-11-19 변경
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
                String code = httpRequestUtil.getJson(data, "code");
                if("0000".equals(code) && data.has("access_token") && data.has("refresh_token") && data.has("expires_in")) {
                    return new NetResult(SUCCESS,0, data);
                }else {
                    String msg = httpRequestUtil.getJson(data, "message");
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


    public String getAuthUrl() {
        csrf = getRandomString(10);
        Log.e("GA", "auth csrf string : " + csrf);

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
        return loginInfoDTO == null ? "" : loginInfoDTO.getTokenCode();
    }
    public String getAccessToken() {
        return loginInfoDTO == null ? "" : loginInfoDTO.getAccessToken();
    }

    public String getCustomerNumber() {
        if(loginInfoDTO == null||loginInfoDTO.getProfile()==null)
            return "";
        else
            return loginInfoDTO.getProfile().getCustomerNumber();
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

    private SecureRandom random = new SecureRandom();
    private String getRandomString(int len) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        StringBuilder buffer = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        Log.e("GA", "request csrf string : " + buffer.toString());

        return buffer.toString();
    }


    public JsonObject postDataWithAccessToken(String url, String data) throws NetException {
        return postDataWithToken(url, data, getAccessToken());
    }

    public JsonObject testAccessToken(String url, String data) throws NetException {
        return postDataWithToken(url, data, "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaWQiOiI1YmIyYzcyMDlhOTk0MzA0MTk0MzZlYzQiLCJ1aWQiOiI1OTRkMDQ1MC02MDI2LTQ0ZjktYTdkMi1hYjI4ODhhY2Q5NzgiLCJzaWQiOiJhMThhYTBmMy0xMWE2LTQ1YmEtYmE0Yy01MmM1NGU2OGFiNjIiLCJleHAiOjE2MDU5Mjg0MzEsImlhdCI6MTYwNTg0MjAzMSwiaXNzIjoiZ2NzIn0.aRdfcVlswreeKxOwokVZU3YGoqgwV5abFgKrE8go_tPR5WRZtaQgBJqKrbVFp0ipPfvCRPdoCrgfs3yxQKChJCGoIGwEzdgGLf2LIbAwPJn2kQcxcM-IiM0Zfqwrs6uNeiJd4nskdO8Nd9C_-Zj6TtUfpek_cH0EFW4MtMHeAAreBG7NDyrNRtzZ6OLMdhKUE8AXEyc1dfKyY_1DstaBD9WaF7g8h5xRZqycLDi485oWylYQtKqvIgm5Jao5d8bkrATcpiWdDf47RAbIG6OACxx6vnfpfJhN9W22NNGUYXe70cg10KT03W1pkJ8mmLmEa_90UtP5gPeVUq2e-Ae4FA");
    }


    public JsonObject postDataWithToken(String url, String data, String token) throws NetException {
        HttpRequest request = httpRequestUtil.getPostRequest(url);
        Log.e(TAG, "TEST NETWORK URL:"+url +"   data:"+data);
        if(!TextUtils.isEmpty(token)) request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + token);
        Log.d(TAG, "Authorization:Bearer " + token);
        JsonObject ret = null;

        try {
            ret = httpRequestUtil.send(request, data);
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
                    updateAccessToken();
//                    CCSP.GAInterface gaInterface = EntryPoints.get(this, CCSP.GAInterface.class);
//                    gaInterface.getGA().updateAccessToken();
                } catch (NetException e2) {
                    e = e2;
                }

                if(e != null) {
                    throw e;
                }
                return postDataWithAccessToken(url, data);
            }
        }

        return ret;
    }






//    public JsonObject postDataWithAccessToken(String url, Map<String, Object> params) throws NetException {
//        return postDataWithToken(url, params, loginInfoDTO.getAccessToken());
//    }
//
//    public JsonObject postDataWithToken(String url, Map<String, Object> params, String token) throws NetException {
////        token = loginInfo.getAccessToken();
//        HttpRequest request = httpRequestUtil.getPostRequest(url);
//        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + token);
//        Log.d(TAG, "Authorization:Bearer " + token);
//        JsonObject ret = null;
//
//        try {
//            ret = httpRequestUtil.send(request, params);
//            retryCount = 0;
//        } catch (NetException e) {
//            int status = e.getStatusCode();
//            String body = e.getBody();
//            JsonObject json = e.getJson();
//            if (status == 403 || status == 401) {
//                Log.d(TAG, "accessToekn expired.");
//                // access toekn, refresh token 만료시 정확히 어떤 값이 리턴되는지 확인 필요
//                if (retryCount!=0)  retryCount = 0;
//                else  e = null;
//
//                retryCount++;
//                try {
//                    CCSP.GAInterface gaInterface = EntryPoints.get(this, CCSP.GAInterface.class);
//                    gaInterface.getGA().updateAccessToken();
//                } catch (NetException e2) {
//                    e = e2;
//                }
//
//                if(e != null) {
//                    throw e;
//                }
//                return postDataWithAccessToken(url, params);
//            }
//        }
//
//        return ret;
//    }





}
