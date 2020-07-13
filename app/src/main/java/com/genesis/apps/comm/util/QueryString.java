package com.genesis.apps.comm.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * url query string builder
 */
public class QueryString {
    private String query = "";

    public QueryString() {
    }

    public QueryString(String name, String value) {
        encode(name, value);
    }

    public void add(String name, String value) {
        if (TextUtils.isEmpty(query)) {
            query = "?" + query;
        } else {
            query += "&";
        }
        encode(name, value);
    }

    private void encode(String name, String value) {
        try {
            query += URLEncoder.encode(name, "UTF-8");
            query += "=";
            query += URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    public String getQuery() {
        return query;
    }

    public String toString() {
        return getQuery();
    }
}
