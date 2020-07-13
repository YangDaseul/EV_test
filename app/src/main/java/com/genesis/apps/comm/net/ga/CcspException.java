package com.genesis.apps.comm.net.ga;


import com.google.gson.JsonObject;

public class CcspException extends Exception {
    private int statusCode;
    private String body;
    private JsonObject json;

    public CcspException(int statusCode, String body) {
        this(statusCode, body, null, "unknown error");
    }

    public CcspException(int statusCode, String body, JsonObject json, String message) {
        super(message);
        this.statusCode = statusCode;
        this.body = body;
        this.json = json;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public JsonObject getJson() {
        return json;
    }
}