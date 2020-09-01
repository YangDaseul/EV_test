package com.genesis.apps.comm.util;

import android.net.Uri;
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


    public static Uri encode(String value){
        Uri retv=null;
        String startWith="";
        try {
            if(value.startsWith("http://")){
                startWith="http://";
                value = value.replace(startWith,"");
            }else if(value.startsWith("https://")){
                startWith="https://";
                value = value.replace(startWith,"");
            }
            retv = Uri.parse(startWith + URLEncoder.encode(value, "UTF-8"));
        }catch (Exception e){

        }
        return retv;
    }

    public static String encodeString(String value){
        String retv="";
        String startWith="";
        try {
            if(value.startsWith("http://")){
                startWith="http://";
                value = value.replace(startWith,"");
            }else if(value.startsWith("https://")){
                startWith="https://";
                value = value.replace(startWith,"");
            }
            retv = startWith + URLEncoder.encode(value, "UTF-8");
        }catch (Exception e){

        }
        return retv;
    }
}
