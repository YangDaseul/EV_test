package com.genesis.apps.comm.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.genesis.apps.comm.net.ga.GAInfo.HTTP_HEADER_NAME;
import static com.genesis.apps.comm.net.ga.GAInfo.HTTP_HEADER_VALUE;
import static com.genesis.apps.comm.net.ga.GAInfo.TAG_MSG_BODY;

public class HttpRequestUtil {
    private static String TAG_LOG = HttpRequestUtil.class.getSimpleName();
    private static final int CONNECTION_TIME_OUT = 10 * 1000;
    private static final int READ_TIME_OUT = 10 * 1000;


    public HttpRequest getDeleteRequest(String url) {
        return HttpRequest.delete(url)
                .acceptGzipEncoding()
                .readTimeout(READ_TIME_OUT)
                .connectTimeout(CONNECTION_TIME_OUT)
                .uncompress(true)
                .followRedirects(true)
                .trustAllCerts()
                .trustAllHosts();
    }


    public HttpRequest getPutRequest(String url) {
        return HttpRequest.put(url)
                .acceptGzipEncoding()
                .readTimeout(READ_TIME_OUT)
                .connectTimeout(CONNECTION_TIME_OUT)
                .uncompress(true)
                .followRedirects(true)
                .trustAllCerts()
                .trustAllHosts();
    }

    public HttpRequest getPostRequest(String url) {
        return HttpRequest.post(url)
                .readTimeout(READ_TIME_OUT)
                .connectTimeout(CONNECTION_TIME_OUT)
                .acceptGzipEncoding()
                .uncompress(true)
                .followRedirects(true)
                .trustAllCerts()
                .trustAllHosts();
    }

    public HttpRequest getRequest(String url) {
        return HttpRequest.get(url)
                .acceptGzipEncoding()
                .readTimeout(READ_TIME_OUT)
                .connectTimeout(CONNECTION_TIME_OUT)
                .uncompress(true)
                .followRedirects(true)
                .trustAllCerts()
                .trustAllHosts();
    }

    public HttpRequest getRequest(String url, Map<String, Object> params) {
        return HttpRequest.get(url, params, true)
                .acceptGzipEncoding()
                .readTimeout(READ_TIME_OUT)
                .connectTimeout(CONNECTION_TIME_OUT)
                .uncompress(true)
                .followRedirects(true)
                .trustAllCerts()
                .trustAllHosts();
    }


    public JsonObject getData(String url) throws NetException{
        Log.d(TAG_LOG, "getData:"+url);
        HttpRequest request = getRequest(url);
        return request!=null&&request.ok() ? toJsonObject(request.code(), request.body()) : null;
    }

    public JsonObject sendPut(String url, String data) throws NetException {
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



    public JsonObject send(String url, String data) throws NetException {
        HttpRequest request = getPostRequest(url);

        Log.v(TAG_LOG, "send request : " + request.toString());

        Log.v(TAG_LOG, "send Params [" + data + "]");

        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        request.send(data);

        int statusCode = request.code();
        Log.v(TAG_LOG, "send statusCode [" + statusCode + "]");

        String body = request.body();
        Log.v(TAG_LOG, "send body [" + body + "]");

        return toJsonObject(statusCode, body);
    }

    public JsonObject send(HttpRequest request, String data) throws NetException {
        Log.v(TAG_LOG, "send request : " + request.toString());

        Log.v(TAG_LOG, "send Params [" + data + "]");

        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        request.send(data);

        int statusCode = request.code();
        Log.v(TAG_LOG, "send statusCode [" + statusCode + "]");

        String body = request.body();
        Log.v(TAG_LOG, "send body [" + body + "]");

        return toJsonObject(statusCode, body);
    }



    public JsonObject toJsonObject(int statusCode, String body) throws NetException {
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
                throw new NetException(statusCode, body, gg, msg);
            }
            throw new NetException(statusCode, body, null, null);
        }
    }


    public JsonObject getData(HttpRequest request) throws NetException {
        Log.v(TAG_LOG, "getData request : " + request.toString());
        int statusCode = request.code();
        Log.v(TAG_LOG, "getData statusCode [" + statusCode + "]");
        String body = request.body();
        Log.v(TAG_LOG, "getData body [" + body + "]");
        request.disconnect();
        return toJsonObject(statusCode, body);
    }

    public JsonObject send(HttpRequest request, Map<String, Object> params) throws NetException {
        Log.v(TAG_LOG, "send request : " + request.toString());
        String jsonStr = null;
        if (params != null) {
            Gson gson = new Gson();
            jsonStr = gson.toJson(params);
            Log.v(TAG_LOG, "send Params [" + jsonStr + "]");
        }

        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);

        request.send(jsonStr);
        int statusCode = request.code();
        Log.v(TAG_LOG, "send statusCode [" + statusCode + "]");
        String body = request.body();
        Log.v(TAG_LOG, String.format(TAG_MSG_BODY,body));
        request.disconnect();
        return toJsonObject(statusCode, body);
    }

    public JsonObject form(HttpRequest request, Map<String, Object> params) throws NetException {
        Log.v(TAG_LOG, "form request : " + request.toString());

        if (params != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<?, ?> entry : params.entrySet()) {
                if (sb.length() == 0) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
                } else {
                    sb.append("&").append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : entry.getValue());
                }
            }
            Log.v(TAG_LOG, "form Params [" + sb.toString() + "]");
        }

        request.form(params);
        int statusCode = request.code();
        Log.v(TAG_LOG, "form statusCode [" + statusCode + "]");
        String body = request.body();
        Log.v(TAG_LOG, String.format(TAG_MSG_BODY,body));
        request.disconnect();
        return toJsonObject(statusCode, body);
    }

    public JsonObject part(HttpRequest request, HashMap<String, String> params, String name, String filename, String contentType, File part) throws NetException {
        Log.v(TAG_LOG, "form request : " + request.toString());

        if (params != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<?, ?> entry : params.entrySet()) {
                if (sb.length() == 0) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
                } else {
                    sb.append("&").append(entry.getKey()).append("=").append(entry.getValue() == null ? "" : entry.getValue());
                }
            }
            Log.v(TAG_LOG, "part Params [" + sb.toString() + "]");
        }

        request.part(params, name, filename, contentType, part);
        int statusCode = request.code();
        Log.v(TAG_LOG, "form statusCode [" + statusCode + "]");
        String body = request.body();
        Log.v(TAG_LOG, String.format(TAG_MSG_BODY,body));
        request.disconnect();
        return toJsonObject(statusCode, body);
    }

    public JsonObject postData(String url, Map<String, Object> params) throws NetException {
        HttpRequest request = getPostRequest(url);
        JsonObject ret = send(request, params);
        return ret;
    }

    public JsonObject getData(String url, Map<String, Object> params) throws NetException {
        HttpRequest request = params == null ? getRequest(url) : getRequest(url, params);
        JsonObject ret = getData(request);
        return ret;
    }

    public boolean download(String accessToken, String url, Map<String, Object> params, File targetFIle) throws NetException {
        HttpRequest request = getPostRequest(url);

        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + accessToken);
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        Log.v(TAG_LOG, "download : " + request);
        Log.d(TAG_LOG, "download targetFile : " + targetFIle.getAbsolutePath());
        Gson gson = new Gson();
        String jsonStr = gson.toJson(params);
        if (params != null) {
            Log.v(TAG_LOG, "download Params [" + jsonStr + "]");
        }

        request.send(jsonStr);
        int statusCode = request.code();
        Log.v(TAG_LOG, "download statusCode [" + statusCode + "]");
        Log.v(TAG_LOG, "download contentLength [" + request.contentLength() + "]");
        //String body = request.body();
        //Log.v(TAG, "send body [" + body + "]");
        if (request.ok()) {
            request.receive(targetFIle);
            request.disconnect();
        } else {
            toJsonObject(statusCode, request.body());
        }

        return true;
    }

    public byte[] download(String accessToken, String url, Map<String, Object> params) throws NetException {
        HttpRequest request = getPostRequest(url);

        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + accessToken);
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        Log.v(TAG_LOG, "download : " + request);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(params);
        if (params != null) {
            Log.v(TAG_LOG, "download Params [" + jsonStr + "]");
        }
        request.send(jsonStr);

        int statusCode = request.code();
        Log.v(TAG_LOG, "download statusCode [" + statusCode + "]");
        Log.v(TAG_LOG, "download contentLength [" + request.contentLength() + "]");
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

        return r;
    }

    public JsonObject upload(String accessToken, String url, HashMap<String, String> params, String name, String filename, File targetFIle) throws NetException {
        HttpRequest request = getPostRequest(url);
        request.header(HTTP_HEADER_NAME, HTTP_HEADER_VALUE + accessToken);
        request.contentType(HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);
        Log.v(TAG_LOG, "upload : " + request);
        if (targetFIle != null) {
            Log.d(TAG_LOG, "upload targetFile : " + targetFIle.getAbsolutePath());
        }
        return part(request, params, name, filename, null, targetFIle);
    }

    public String getJson(JsonObject jsonObject, String key) {
        if(jsonObject == null) return "";
        if(!jsonObject.has(key)) return "";
        if(jsonObject.get(key).isJsonNull()) return "";
        return jsonObject.get(key).getAsString();
    }
    public JsonObject getJsonObject(JsonObject jsonObject, String key) {
        if(jsonObject == null) return null;
        if(!jsonObject.has(key)) return null;
        if(jsonObject.get(key).isJsonNull()) return null;
        if(!jsonObject.get(key).isJsonObject()) return null;
        return jsonObject.get(key).getAsJsonObject();
    }
    public JsonArray getJsonArray(JsonObject jsonObject, String key) {
        if(jsonObject == null) return null;
        if(!jsonObject.has(key)) return null;
        if(jsonObject.get(key).isJsonNull()) return null;
        if(!jsonObject.get(key).isJsonArray()) return null;
        return jsonObject.get(key).getAsJsonArray();
    }


}
