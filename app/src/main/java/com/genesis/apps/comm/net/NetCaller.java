package com.genesis.apps.comm.net;

import android.text.TextUtils;
import android.util.Log;

import com.genesis.apps.R;
import com.genesis.apps.comm.net.model.BeanReqParm;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class NetCaller {
    private static String TAG_LOG = NetCaller.class.getSimpleName();
    private static final int CONNECTION_TIME_OUT = 10 * 1000;
    private static final int READ_TIME_OUT = 10 * 1000;

    private void sendData(BeanReqParm beanReqParm, ExecutorService es) {
//        es = DaggerExcutorServiceComponent.builder().executorServiceModule((new ExecutorServiceModule(TAG_LOG))).build().maker();

        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            JsonObject jsonObject = null;
            try {
                switch (beanReqParm.getType()) {
                    case HttpRequest.METHOD_GET:
                        jsonObject = getData(beanReqParm.getUrl());
                        break;
                    case HttpRequest.METHOD_PUT:
                        jsonObject = sendPut(beanReqParm.getUrl(), beanReqParm.getData());
                        break;
                    case HttpRequest.METHOD_POST:
                        jsonObject = send(beanReqParm.getUrl(), beanReqParm.getData());
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

    private HttpRequest getPostRequest(String url) {
        return HttpRequest.post(url)
                .readTimeout(READ_TIME_OUT)
                .connectTimeout(CONNECTION_TIME_OUT)
                .acceptGzipEncoding()
                .uncompress(true)
                .followRedirects(true);
    }

    private HttpRequest getRequest(String url) {
        return HttpRequest.get(url)
                .readTimeout(READ_TIME_OUT)
                .connectTimeout(CONNECTION_TIME_OUT)
                .acceptGzipEncoding()
                .uncompress(true)
                .followRedirects(true);
    }

    private HttpRequest getPutRequest(String url) {
        return HttpRequest.put(url)
                .readTimeout(READ_TIME_OUT)
                .connectTimeout(CONNECTION_TIME_OUT)
                .acceptGzipEncoding()
                .uncompress(true)
                .followRedirects(true);
    }


    private JsonObject getData(String url) throws Exception, HttpRequest.HttpRequestException{
        Log.d(TAG_LOG, "getData:"+url);
        HttpRequest request = getRequest(url);
        return request!=null&&request.ok() ? toJsonObject(request.code(), request.body()) : null;
    }

    private JsonObject sendPut(String url, String data) throws Exception, HttpRequest.HttpRequestException {
        HttpRequest request = getPutRequest(url);
        Log.v(TAG_LOG, "send request : " + request.toString());
//        if (params != null) {
//            Gson gson = new Gson();
//            jsonStr = gson.toJson(params);
//            Log.v(TAG_LOG, "send Params [" + jsonStr + "]");
//        }
        Log.v(TAG_LOG, "send Params [" + data + "]");

        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        request.send(data);

        int statusCode = request.code();
        Log.v(TAG_LOG, "send statusCode [" + statusCode + "]");

        String body = request.body();
        Log.v(TAG_LOG, "send body [" + body + "]");

        return toJsonObject(statusCode, body);
    }



    private JsonObject send(String url, String data) throws Exception, HttpRequest.HttpRequestException {
        HttpRequest request = getPostRequest(url);

        Log.v(TAG_LOG, "send request : " + request.toString());

//        if (params != null) {
//            Gson gson = new Gson();
//            jsonStr = gson.toJson(params);
//            Log.v(TAG_LOG, "send Params [" + jsonStr + "]");
//        }
        Log.v(TAG_LOG, "send Params [" + data + "]");

        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        request.send(data);

        int statusCode = request.code();
        Log.v(TAG_LOG, "send statusCode [" + statusCode + "]");

        String body = request.body();
        Log.v(TAG_LOG, "send body [" + body + "]");

        return toJsonObject(statusCode, body);
    }



    private JsonObject toJsonObject(int statusCode, String body) throws Exception {
        // if 200 then ok else error(200이면 ok 아니면 에러)
        // if data type is json then ok else error(json이면 ok 아니면 에러)
        // if exist error code then error (에러 코드가 있으면 에러)
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
                throw new Exception(msg);
            }
            throw new Exception();
        }
    }



    private String getJson(JsonObject jsonObject, String key) {
        if(jsonObject == null) return "";
        if(!jsonObject.has(key)) return "";
        if(jsonObject.get(key).isJsonNull()) return "";
        return jsonObject.get(key).getAsString();
    }
    private JsonObject getJsonObject(JsonObject jsonObject, String key) {
        if(jsonObject == null) return null;
        if(!jsonObject.has(key)) return null;
        if(jsonObject.get(key).isJsonNull()) return null;
        if(!jsonObject.get(key).isJsonObject()) return null;
        return jsonObject.get(key).getAsJsonObject();
    }
    private JsonArray getJsonArray(JsonObject jsonObject, String key) {
        if(jsonObject == null) return null;
        if(!jsonObject.has(key)) return null;
        if(jsonObject.get(key).isJsonNull()) return null;
        if(!jsonObject.get(key).isJsonArray()) return null;
        return jsonObject.get(key).getAsJsonArray();
    }


}