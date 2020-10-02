package com.genesis.apps.comm.net;

import android.text.TextUtils;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.comm.model.constants.GAInfo;
import com.genesis.apps.comm.net.model.BeanReqParm;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.inject.Inject;

public class NetCaller {
    private static String TAG_LOG = NetCaller.class.getSimpleName();
    private static final int CONNECTION_TIME_OUT = 10 * 1000;
    private static final int READ_TIME_OUT = 10 * 1000;

    private HttpRequestUtil httpRequestUtil;
    private GA ga;
    @Inject
    public NetCaller(HttpRequestUtil httpRequestUtil, GA ga){
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
                        jsonObject = httpRequestUtil.getData(beanReqParm.getUrl());
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
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                beanReqParm.getCallback().onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }



    public <RESPONSE,FORMAT> void reqDataFromAnonymous(Callable<RESPONSE> callable, NetCallback callback) {
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
     * @brief GRA API Request
     * GRA에 데이터 요청 시 사용되며
     * 데이터 결과는 JsonObject를 String으로 변환해서 callback에 전달
     *
     *
     * @param callback network result callback
     * @param apiInfo request API Infomation
     * @param reqVO request data
     * @param <REQ> request data format
     */
    public <REQ>void reqDataToGRA(NetResultCallback callback, APIInfo apiInfo, REQ reqVO) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            JsonObject jsonObject = null;
            try {
                String serverUrl = GAInfo.CCSP_URL+apiInfo.getURI();
                switch (apiInfo.getReqType()) {
                    case HttpRequest.METHOD_GET:
                        jsonObject = httpRequestUtil.getData(serverUrl);
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
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }




    public <REQ>void sendFileToGRA(NetResultCallback callback, APIInfo apiInfo, REQ reqVO, File file, String name) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            JsonObject jsonObject = null;
            try {
                String serverUrl = GAInfo.CCSP_URL+apiInfo.getURI();
                Type type = new TypeToken<HashMap<String, String>>() { }.getType();
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create(); //expose처리되어 있는 필드에 대해서만 파싱 진행
                HashMap<String, String> params = new Gson().fromJson(gson.toJson(reqVO), type);
                jsonObject = httpRequestUtil.upload(ga.getAccessToken(), serverUrl, params, name, file.getName(), file);

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
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }
}
