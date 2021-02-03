package com.genesis.apps.comm.net;

import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.comm.model.constants.GAInfo;
import com.genesis.apps.comm.net.model.BeanReqParm;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

public class NetCaller {
    private static String TAG_LOG = NetCaller.class.getSimpleName();
    private static final int CONNECTION_TIME_OUT = 10 * 1000;
    private static final int READ_TIME_OUT = 10 * 1000;
    private static final String LOG_JSON_NULL="jsonObject is null";

    private HttpRequestUtil httpRequestUtil;
    private GA ga;

    @Inject
    public NetCaller(HttpRequestUtil httpRequestUtil, GA ga) {
        this.httpRequestUtil = httpRequestUtil;
        this.ga = ga;
    }


    public void sendData(BeanReqParm beanReqParm) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            JsonObject jsonObject = null;
            try {
                switch (beanReqParm.getType()) {
                    case HttpRequest.METHOD_GET:
                        jsonObject = httpRequestUtil.getData(beanReqParm.getUrl(), ga.getAccessToken());
                        break;
                    case HttpRequest.METHOD_PUT:
                        jsonObject = httpRequestUtil.sendPut(beanReqParm.getUrl(), beanReqParm.getData());
                        break;
                    case HttpRequest.METHOD_POST:
                        jsonObject = httpRequestUtil.send(beanReqParm.getUrl(), beanReqParm.getData());
                        break;
                    default:
                        break;
                }

                if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
                    return new NetResult(NetStatusCode.SUCCESS, 0, jsonObject);
                } else {
                    return new NetResult(NetStatusCode.ERR_DATA_NULL, R.string.error_msg_1, null);
                }
            } catch (HttpRequest.HttpRequestException e) {
                e.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_HTTP, R.string.error_msg_2, null);
            } catch (Exception e1) {
                e1.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_UNKNOWN, R.string.error_msg_3, null);
            }

        }), new FutureCallback<NetResult>() {
            @Override
            public void onSuccess(@NullableDecl NetResult result) {
                if(result!=null) {
                    switch (result.getCode()) {
                        case SUCCESS:
                            beanReqParm.getCallback().onSuccess(((JsonObject) result.getData()).toString());
                            break;
                        case ERR_EXCEPTION_DKC:
                        case ERR_EXCEPTION_HTTP:
                        case ERR_EXCEPTION_UNKNOWN:
                        case ERR_DATA_NULL:
                        case ERR_ISSUE_SOURCE:
                        case ERR_DATA_INCORRECT:
                        default:
                            beanReqParm.getCallback().onFail(result);
                            break;
                    }
                }else{
                    beanReqParm.getCallback().onFail(null);
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                beanReqParm.getCallback().onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }


    public <RESPONSE, FORMAT> void reqDataFromAnonymous(Callable<RESPONSE> callable, NetCallback callback) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            RESPONSE reponseData = null;
            try {
                reponseData = callable.call();

                if (reponseData != null && !TextUtils.isEmpty(reponseData.toString())) {
                    return new NetResult(NetStatusCode.SUCCESS, 0, reponseData);
                } else {
                    return new NetResult(NetStatusCode.ERR_DATA_NULL, R.string.error_msg_1, null);
                }
            } catch (HttpRequest.HttpRequestException e) {
                e.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_HTTP, R.string.error_msg_2, null);
            } catch (Exception e1) {
                e1.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_UNKNOWN, R.string.error_msg_3, null);
            }

        }), new FutureCallback<NetResult>() {
            @Override
            public void onSuccess(@NullableDecl NetResult result) {

                if(result!=null) {
                    switch (result.getCode()) {
                        case SUCCESS:
                            callback.onSuccess(result.getData());
                            break;
                        case ERR_EXCEPTION_DKC:
                        case ERR_EXCEPTION_HTTP:
                        case ERR_EXCEPTION_UNKNOWN:
                        case ERR_DATA_NULL:
                        case ERR_ISSUE_SOURCE:
                        case ERR_DATA_INCORRECT:
                        default:
                            callback.onFail(result);
                            break;
                    }
                }else{
                    callback.onFail(null);
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }


    /**
     * @param callback network result callback
     * @param apiInfo  request API Infomation
     * @param reqVO    request data
     * @param <REQ>    request data format
     * @brief GRA API Request
     * GRA에 데이터 요청 시 사용되며
     * 데이터 결과는 JsonObject를 String으로 변환해서 callback에 전달
     */
    public <REQ> void reqDataToGRA(NetResultCallback callback, APIInfo apiInfo, REQ reqVO) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            JsonObject jsonObject = null;
            try {
                String serverUrl = GAInfo.CCSP_URL + apiInfo.getURI();
                switch (apiInfo.getReqType()) {
                    case HttpRequest.METHOD_GET:
                        jsonObject = httpRequestUtil.getData(serverUrl, ga.getAccessToken());
                        break;
                    case HttpRequest.METHOD_PUT:
                        jsonObject = httpRequestUtil.sendPut(serverUrl, new Gson().toJson(reqVO));
                        break;
                    case HttpRequest.METHOD_POST:
                        jsonObject = ga.postDataWithAccessToken(serverUrl, new Gson().toJson(reqVO));
//                       jsonObject = httpRequestUtil.send(serverUrl, new Gson().toJson(reqVO));
                        break;
                    default:
                        break;
                }

                if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
                    setLog(apiInfo.getIfCd(), new Gson().toJson(reqVO), jsonObject.toString());
                    return new NetResult(NetStatusCode.SUCCESS, 0, jsonObject);
                } else {
                    return new NetResult(NetStatusCode.ERR_DATA_NULL, R.string.error_msg_1, null);
                }
            } catch (HttpRequest.HttpRequestException e) {
                e.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_HTTP, R.string.error_msg_2, null);
            } catch (Exception e1) {
                e1.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_UNKNOWN, R.string.error_msg_3, null);
            }

        }), new FutureCallback<NetResult>() {
            @Override
            public void onSuccess(@NullableDecl NetResult result) {
                if(result!=null) {
                    switch (result.getCode()) {
                        case SUCCESS:
                            callback.onSuccess(((JsonObject) result.getData()).toString());
                            break;
                        case ERR_EXCEPTION_DKC:
                        case ERR_EXCEPTION_HTTP:
                        case ERR_EXCEPTION_UNKNOWN:
                        case ERR_DATA_NULL:
                        case ERR_ISSUE_SOURCE:
                        case ERR_DATA_INCORRECT:
                        default:
                            callback.onFail(result);
                            break;
                    }
                }else{
                    callback.onFail(null);
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }


    /**
     * @param apiInfo request API Infomation
     * @param reqVO   request data
     * @param <REQ>   request data format
     * @brief ETC API Request
     * ETC 서버에 데이터 요청 시 사용되며
     * 데이터 결과는 JsonObject를 String으로 변환해서 callback에 전달
     */
    public <REQ> JsonObject reqDataFromAnonymous(String serverDomain, APIInfo apiInfo, REQ reqVO) {
        JsonObject jsonObject = null;
        String serverUrl = serverDomain + apiInfo.getURI();

        if(apiInfo==APIInfo.DEVELOPERS_CHECK_JOIN_CCS){
            serverUrl = String.format(Locale.getDefault(), serverUrl, getUriInfo(apiInfo.getIfCd(), new Gson().toJson(reqVO)), getUriInfo("vin", new Gson().toJson(reqVO)));
        } else if (apiInfo.getIfCd().equalsIgnoreCase("carId") || apiInfo.getIfCd().equalsIgnoreCase("userId")) {
            serverUrl = String.format(Locale.getDefault(), serverUrl, getUriInfo(apiInfo.getIfCd(), new Gson().toJson(reqVO)));
        }

        try {
            switch (apiInfo.getReqType()) {
                case HttpRequest.METHOD_GET:
                    if (reqVO != null
                            && apiInfo != APIInfo.DEVELOPERS_CAR_ID //TODO 확인필요
                            && apiInfo != APIInfo.DEVELOPERS_CAR_CHECK
                            && apiInfo != APIInfo.DEVELOPERS_CHECK_JOIN_CCS) {
                        Map<String, Object> map = new Gson().fromJson(
                                new Gson().toJson(reqVO), new TypeToken<HashMap<String, Object>>() {
                                }.getType());
                        jsonObject = httpRequestUtil.getData(serverUrl, map, ga.getAccessToken());
                    } else {
                        jsonObject = httpRequestUtil.getData(serverUrl, ga.getAccessToken());
                    }
                    break;
                case HttpRequest.METHOD_PUT:
                    jsonObject = httpRequestUtil.sendPut(serverUrl, new Gson().toJson(reqVO));
                    break;
                case HttpRequest.METHOD_POST:
                    jsonObject = ga.postDataWithAccessToken(serverUrl, new Gson().toJson(reqVO));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            jsonObject = null;
        }
        setLog(apiInfo.getIfCd(), new Gson().toJson(reqVO), jsonObject!=null ? jsonObject.toString() : LOG_JSON_NULL);
        return jsonObject;
    }


    /**
     * @param callback network result callback
     * @param apiInfo  request API Infomation
     * @param reqVO    request data
     * @param <REQ>    request data format
     * @brief ETC API Request
     * ETC 서버에 데이터 요청 시 사용되며
     * 데이터 결과는 JsonObject를 String으로 변환해서 callback에 전달
     */
    public <REQ> void reqDataFromAnonymous(NetResultCallback callback, String serverDomain, APIInfo apiInfo, REQ reqVO) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            JsonObject jsonObject = null;
            try {
                String serverUrl = serverDomain + apiInfo.getURI();

                if (apiInfo.getIfCd().equalsIgnoreCase("carId") || apiInfo.getIfCd().equalsIgnoreCase("userId")) {
                    serverUrl = String.format(Locale.getDefault(), serverUrl, getUriInfo(apiInfo.getIfCd(), new Gson().toJson(reqVO)));
                }

                switch (apiInfo.getReqType()) {
                    case HttpRequest.METHOD_GET:
                        if (reqVO != null) {
                            Map<String, Object> map = new Gson().fromJson(
                                    new Gson().toJson(reqVO), new TypeToken<HashMap<String, Object>>() {
                                    }.getType());
                            jsonObject = httpRequestUtil.getData(serverUrl, map, ga.getAccessToken());
                        } else {
                            jsonObject = httpRequestUtil.getData(serverUrl, ga.getAccessToken());
                        }
                        break;
                    case HttpRequest.METHOD_PUT:
                        jsonObject = httpRequestUtil.sendPut(serverUrl, new Gson().toJson(reqVO));
                        break;
                    case HttpRequest.METHOD_POST:
                        jsonObject = ga.postDataWithAccessToken(serverUrl, new Gson().toJson(reqVO));
//                       jsonObject = httpRequestUtil.send(serverUrl, new Gson().toJson(reqVO));
                        break;
                    default:
                        break;
                }

                if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
                    setLog(apiInfo.getIfCd(), new Gson().toJson(reqVO), jsonObject.toString());
                    return new NetResult(NetStatusCode.SUCCESS, 0, jsonObject);
                } else {
                    return new NetResult(NetStatusCode.ERR_DATA_NULL, R.string.error_msg_1, null);
                }
            } catch (HttpRequest.HttpRequestException e) {
                e.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_HTTP, R.string.error_msg_2, null);
            } catch (Exception e1) {
                e1.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_UNKNOWN, R.string.error_msg_3, null);
            }

        }), new FutureCallback<NetResult>() {
            @Override
            public void onSuccess(@NullableDecl NetResult result) {
                if(result!=null) {
                    switch (result.getCode()) {
                        case SUCCESS:
                            callback.onSuccess(((JsonObject) result.getData()).toString());
                            break;
                        case ERR_EXCEPTION_DKC:
                        case ERR_EXCEPTION_HTTP:
                        case ERR_EXCEPTION_UNKNOWN:
                        case ERR_DATA_NULL:
                        case ERR_ISSUE_SOURCE:
                        case ERR_DATA_INCORRECT:
                        default:
                            callback.onFail(result);
                            break;
                    }
                }else{
                    callback.onFail(null);
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }

    private String getUriInfo(String key, String reqData) {
        String uriInfo = "";

        try {
            JsonElement element = new Gson().fromJson(reqData, JsonElement.class);
            JsonObject json = element.getAsJsonObject();
            uriInfo = json.get(key).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(uriInfo)) {
                uriInfo = "";
            }
        }

        return uriInfo;
    }


    public <REQ> void sendFileToGRA(NetResultCallback callback, APIInfo apiInfo, REQ reqVO, File file, String name) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            JsonObject jsonObject = null;
            try {
                String serverUrl = GAInfo.CCSP_URL + apiInfo.getURI();
                Type type = new TypeToken<HashMap<String, String>>() {
                }.getType();
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); //expose처리되어 있는 필드에 대해서만 파싱 진행
                HashMap<String, String> params = new Gson().fromJson(gson.toJson(reqVO), type);
                jsonObject = httpRequestUtil.upload(ga.getAccessToken(), serverUrl, params, name, file.getName(), file);

                if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
                    setLog(apiInfo.getIfCd(), new Gson().toJson(reqVO), jsonObject.toString());
                    return new NetResult(NetStatusCode.SUCCESS, 0, jsonObject);
                } else {
                    return new NetResult(NetStatusCode.ERR_DATA_NULL, R.string.error_msg_1, null);
                }
            } catch (HttpRequest.HttpRequestException e) {
                e.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_HTTP, R.string.error_msg_2, null);
            } catch (Exception e1) {
                e1.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_UNKNOWN, R.string.error_msg_3, null);
            }

        }), new FutureCallback<NetResult>() {
            @Override
            public void onSuccess(@NullableDecl NetResult result) {
                if(result!=null) {
                    switch (result.getCode()) {
                        case SUCCESS:
                            callback.onSuccess(((JsonObject) result.getData()).toString());
                            break;
                        case ERR_EXCEPTION_DKC:
                        case ERR_EXCEPTION_HTTP:
                        case ERR_EXCEPTION_UNKNOWN:
                        case ERR_DATA_NULL:
                        case ERR_ISSUE_SOURCE:
                        case ERR_DATA_INCORRECT:
                        default:
                            callback.onFail(result);
                            break;
                    }
                }else{
                    callback.onFail(null);
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }
    final String IFCD ="ifcd :";
    private void setLog(String ifcd, String send, String receive){
        try {
            Log.e(TAG_LOG, IFCD + ifcd + "  send :   " + send);
            Log.e(TAG_LOG, IFCD + ifcd + "  receive :   " + receive);
        }catch (Exception ignore){
            //do nothing
        }
    }
}
