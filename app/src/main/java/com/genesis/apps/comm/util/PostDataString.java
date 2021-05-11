package com.genesis.apps.comm.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * url postData string builder
 */
public class PostDataString {
    private String postData = "";

    public PostDataString() {
    }

    public PostDataString(String name, String value) {
        encode(name, value);
    }

    public void add(String name, String value) {
        if (!TextUtils.isEmpty(postData)) {
            postData += "&";
        }
        encode(name, value);
    }

    private void encode(String name, String value) {
        try {
            postData += URLEncoder.encode(name, "UTF-8");
            if (!TextUtils.isEmpty(value)) {
                postData += "=";
                postData += URLEncoder.encode(value, "UTF-8");
            }
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    public String getPostData() {
        return postData;
    }

    public String toString() {
        return getPostData();
    }

}
