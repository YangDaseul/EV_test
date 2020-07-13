package com.genesis.apps.comm.net.ga;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.comm.net.HttpRequest;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.util.crypt.AesUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import static com.genesis.apps.comm.net.ga.GAInfo.HTTP_HEADER_NAME;
import static com.genesis.apps.comm.net.ga.GAInfo.HTTP_HEADER_VALUE;
import static com.genesis.apps.comm.net.ga.GAInfo.TAG_MSG_LOGININFO;

public class CCSP {
    private static final String TAG = CCSP.class.getSimpleName();
    private NetCaller netCaller;
    private String csrf;
    private BeanLoginInfo beanLoginInfo;
    private int retryCount = 0;

    private Random random = new Random();

    private static byte[] key = new byte[]{
            (byte)0xDC, 0x0F, 0x79, (byte)0xCA, 0x39, 0x7E, (byte)0xB3, (byte)0x8C, 0x0A, 0x2E, (byte)0xB8, (byte)0x80, (byte)0xB2, 0x39, (byte)0x8B, 0x7D};

    @Inject
    public CCSP(NetCaller netCaller){
        this.netCaller = netCaller;
    }

    public synchronized boolean isLoggedIn(Context context) {
        BeanLoginInfo beanLoginInfo = loadLoginInfo(context);
        if (beanLoginInfo != null && beanLoginInfo.getProfile() != null) {
            // 로그인 정보 있음
            Log.d(TAG, "로그인 정보 있음");
            if (!TextUtils.isEmpty(beanLoginInfo.getAccessToken()) && !TextUtils.isEmpty(beanLoginInfo.getRefreshToken())) {
                if (TextUtils.isEmpty(beanLoginInfo.getProfile().getCustomerNumber())) {
                    // 고객 번호 없음. == 서비스 미 가입
                    Log.d(TAG, "고객 번호 없음. == 서비스 미 가입");
                    clearLoginInfo(context);
                    return false;
                }

                long t = beanLoginInfo.getRefreshTokenExpriesDate() - System.currentTimeMillis();
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

    public synchronized JsonObject getControlToken(String pin) throws CcspException {
        String url = getServerUrl() + "/api/v1/user/pin";

        Map<String, Object> params = new HashMap<>();
        params.put("pin", pin);
        params.put("deviceId", "");

        HttpRequest request = netCaller.getPutRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + beanLoginInfo.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);

        return netCaller.send(request, params);
    }

    public synchronized void changePin(String currentPin, String changedPin) throws CcspException {
        String url = getServerUrl() + "/api/v1/user/profile";


        Map<String, Object> pinParams = new HashMap<>();
        pinParams.put("current", currentPin);
        pinParams.put("change", changedPin);

        Map<String, Object> params = new HashMap<>();
        params.put("pin", pinParams);

        HttpRequest request = getPutRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + beanLoginInfo.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);

        send(request, params);
    }

    public synchronized void resetPin() throws CcspException {
        String url = getServerUrl() + "/api/v1/user/profile/pin";

        HttpRequest request = getDeleteRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + beanLoginInfo.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);

        try {
            getData(request);
        }
        catch (CcspException e) {
            throw e;
        }
        catch (Exception ignore) {
            //
        }
    }

    public synchronized BeanLoginInfo getLoginInfo(Context context) {
        return loadLoginInfo(context);
    }

    synchronized JsonObject postDataWithAccessToken(Context context, String url, Map<String, Object> params) throws CcspException {
        return postDataWithToken(context, url, params, beanLoginInfo.getAccessToken());
    }

    synchronized JsonObject postDataWithToken(Context context, String url, Map<String, Object> params, String token) throws CcspException {

//        token = loginInfo.getAccessToken();

        HttpRequest request = getPostRequest(url);
        request.lastActivity = context;
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + token);
        Log.d(TAG, "Authorization:Bearer " + token);
        JsonObject ret = null;

        try {
            ret = send(request, params);
            retryCount = 0;
        } catch (CcspException e) {
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
                    //if(DKC.isUsedGA)
                    GA.getInstance().updateAccessToken(context);
                    /*else updateAccessToken(context);*/
                } catch (CcspException e2) {
                    e = e2;
                }

                if(e != null) {
                    hideProgress(context);
                    throw e;
                }
                return postDataWithAccessToken(context, url, params);
            }
        }

        hideProgress(context);
        return ret;
    }

    synchronized JsonObject postData(Context context, String url, Map<String, Object> params) throws CcspException {
        HttpRequest request = getPostRequest(url);
        request.lastActivity = context;
        JsonObject ret = send(request, params);
        hideProgress(context);
        return ret;
    }

    public synchronized JsonObject getData(Context context, String url, Map<String, Object> params) throws CcspException {
        HttpRequest request = params == null ? getRequest(url) : getRequest(url, params);
        request.lastActivity = context;
        JsonObject ret = getData(request);
        hideProgress(context);
        return ret;
    }

    synchronized boolean download(Context context, String url, Map<String, Object> params, File targetFIle) throws CcspException {
        HttpRequest request = getPostRequest(url);
        request.lastActivity = context;

        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfo.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        Log.v(TAG, "download : " + request);
        Log.d(TAG, "download targetFile : " + targetFIle.getAbsolutePath());
        Gson gson = new Gson();
        String jsonStr = gson.toJson(params);
        if (params != null) {
            Log.v(TAG, "download Params [" + jsonStr + "]");
        }

        request.send(jsonStr);
        int statusCode = request.code();
        Log.v(TAG, "download statusCode [" + statusCode + "]");
        Log.v(TAG, "download contentLength [" + request.contentLength() + "]");
        //String body = request.body();
        //Log.v(TAG, "send body [" + body + "]");
        if (request.ok()) {
            request.receive(targetFIle);
            request.disconnect();
        } else {
            toJsonObject(statusCode, request.body());
        }

        hideProgress(context);
        return true;
    }

    synchronized byte[] download(Context context, String url, Map<String, Object> params) throws CcspException {
        HttpRequest request = getPostRequest(url);
        request.lastActivity = context;

        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfo.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        Log.v(TAG, "download : " + request);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(params);
        if (params != null) {
            Log.v(TAG, "download Params [" + jsonStr + "]");
        }
        request.send(jsonStr);

        int statusCode = request.code();
        Log.v(TAG, "download statusCode [" + statusCode + "]");
        Log.v(TAG, "download contentLength [" + request.contentLength() + "]");
        byte[] r = null;
        if (request.ok()) {
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                request.receive(baos);
                request.disconnect();
                r = baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            toJsonObject(statusCode, request.body());
        }

        hideProgress(context);
        return r;
    }

    synchronized JsonObject upload(Context context, String url, HashMap<String, String> params, String name, String filename, File targetFIle) throws CcspException {
        HttpRequest request = getPostRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + loginInfo.getAccessToken());
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        Log.v(TAG, "upload : " + request);
        if (targetFIle != null) {
            Log.d(TAG, "upload targetFile : " + targetFIle.getAbsolutePath());
        }
        return part(context, request, params, name, filename, null, targetFIle);
    }

    private JsonObject toJsonObject(int statusCode, String body) throws CcspException {
        // 200이면 ok 아니면 에러
//            json이면 ok 아니면 에러
//                    에러 코드가 있으면 에러
        Gson gson = new Gson();
        JsonObject gg = null;
        try {
            JsonElement g = gson.fromJson(body, JsonElement.class);
            gg = g.getAsJsonObject();
        } catch (Exception e) {
            gg = null;
        }

        if (statusCode == 200 || statusCode == 204) {
            return gg;
        } else {
            if (gg != null) {
                String msg = gg.get("error") == null ? "": gg.get("error").getAsString();
                if (TextUtils.isEmpty(msg)) {
                    msg =  gg.get("error") == null ? "unknown error": gg.get("errMsg").getAsString();
                }
                throw new CcspException(statusCode, body, gg, msg);
            }
            throw new CcspException(statusCode, body, null, null);
        }
    }

    public void setCustomerNumber(Context context, String customerNumber) {
        getLoginInfo(context).getProfile().setCustomerNumber(customerNumber);
        updateLoginInfo(context);
    }

    public String getServerUrl() {
        return GAInfo.ServerInfos[0][1];
    }

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

    private synchronized BeanLoginInfo loadLoginInfo(Context context) {
        if (beanLoginInfo != null) {
            return beanLoginInfo;
        }

        File dir = context.getFilesDir();
        File dataDir = new File(dir, "/data");
        if (!dataDir.exists()) dataDir.mkdirs();

        // added by mkpark
        // 정보파일 마이그레이션(암호화 저장)
        // 기존 암호화 되지 않은 정보가 있다면 암호화 저장 후 로딩
        File encDataFile = new File(dataDir, "cLoginInfo.json");
        if (!encDataFile.exists()) {
            // 암호화된 파일이 존재하지 않음

            // 이전에 암호화 되지 않은 파일이 있는지 확인
            File dataFile = new File(dataDir, "loginInfo.json");
            byte[] bytes;

            if (dataFile.exists()) {

                // 파일을 읽음
                BufferedInputStream buf = null;
                try {
                    int size = (int) dataFile.length();
                    bytes = new byte[size];
                    buf = new BufferedInputStream(new FileInputStream(dataFile));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();
                    buf = null;
                } catch (FileNotFoundException e) {
                    bytes = null;
                    e.printStackTrace();
                } catch (IOException e) {
                    bytes = null;
                    e.printStackTrace();
                } finally {
                    if (buf  != null) {
                        try {
                            buf.close();
                        } catch (IOException ignored) {
                        }
                    }
                }

                byte[] result = null;
                if( bytes != null && bytes.length > 0 )
                {
                    try {
                        result = AesUtils.encryptAES128_CTR(key, new byte[16], bytes);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                if( result != null && result.length > 0 )
                {
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(encDataFile);
                        fos.write(result);
                        fos.close();
                        fos = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException ignored) {
                            }
                        }
                    }

                    dataFile.delete();
                }
            }
        }
        else
        {
            // 이미 암호화된 파일이 있는 상황에서 이전 파일이 존재하면 삭제
            File dataFile = new File(dataDir, "loginInfo.json");
            if (dataFile.exists()) dataFile.delete();
        }

        if (!encDataFile.exists()) {
            beanLoginInfo = null;
            Log.d(TAG, TAG_MSG_LOGININFO + beanLoginInfo);
            return beanLoginInfo;
        }

        Gson gson = new Gson();
        //Reader reader = null;
        BufferedInputStream buf = null;
        try {
            byte[] bytes = null;
            try {
                int size = (int) encDataFile.length();
                bytes = new byte[size];
                buf = new BufferedInputStream(new FileInputStream(encDataFile));
                buf.read(bytes, 0, bytes.length);
                buf.close();
                buf = null;
            } catch (FileNotFoundException e) {
                bytes = null;
                e.printStackTrace();
            } catch (IOException e) {
                bytes = null;
                e.printStackTrace();
            } finally {
                if (buf != null) {
                    buf.close();
                    buf = null;
                }
            }

            byte[] result = null;
            if( bytes != null && bytes.length > 0 )
            {
                try {
                    result = AesUtils.decryptAES128_CTR(key, new byte[16], bytes);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            if( result != null && result.length > 0 ) {
//                reader = new FileReader(new String(result));
                //loginInfo = gson.fromJson(reader, LoginInfo.class);
                beanLoginInfo = gson.fromJson( new String(result), BeanLoginInfo.class);
                //reader.close();
                //reader = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }/* finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                    reader = null;
//                } catch (IOException e) {
//                }
//            }
        }*/

        Log.d(TAG, TAG_MSG_LOGININFO + (beanLoginInfo!=null ? beanLoginInfo.toString() : "null"));
        return beanLoginInfo;
    }

    synchronized boolean updateLoginInfo(Context context) {

        Log.d(TAG, TAG_MSG_LOGININFO + (beanLoginInfo!=null ? beanLoginInfo.toString() : "null"));
        Gson gson = new Gson();
        String json = gson.toJson(beanLoginInfo);
        File dir = context.getFilesDir();
        File dataDir = new File(dir, "/data");

        // 추후 삭제
        // 암호화된 파일형태로 저장하기 전에 기존에 파일이 있다면 삭제
        File dataFile = new File(dataDir, "loginInfo.json");
        if (dataFile.exists()) dataFile.delete();

        if (!dataDir.exists()) dataDir.mkdirs();
        File encDataFile = new File(dataDir, "cLoginInfo.json");
        if (encDataFile.exists()) encDataFile.delete();

        byte[] bytes;
        bytes = json.getBytes();
        byte[] result = null;
        if( bytes != null && bytes.length > 0 )
        {
            try {
                result = AesUtils.encryptAES128_CTR(key, new byte[16], bytes);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if( result != null && result.length > 0 )
        {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(encDataFile);
                fos.write(result);
                fos.close();
                fos = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        return true;
    }

    /**
     * 로그인 정보 삭제 - 로그아웃을 위한 처리
     */
    public synchronized void clearLoginInfo(Context context) {

        File dir = context.getFilesDir();
        File dataDir = new File(dir, "/data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        File dataFile = new File(dataDir, "cLoginInfo.json");
        if (dataFile.exists()) {
            dataFile.delete();
        }

        // 추후 삭제
        dataFile = new File(dataDir, "loginInfo.json");
        if (dataFile.exists()) {
            dataFile.delete();
        }
        beanLoginInfo = null;
    }



    //GA에서 로그인 또는 서비스 가입 이후 토큰 정보 설정시 사용.
    void setGaLoginInfo(Context context, BeanLoginInfo beanLoginInfo) {
        this.beanLoginInfo = beanLoginInfo;
        updateLoginInfo(context);
    }
}
