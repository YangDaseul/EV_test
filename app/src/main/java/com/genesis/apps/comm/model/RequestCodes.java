package com.genesis.apps.comm.model;

import java.util.Arrays;

public enum RequestCodes {

    REQ_CODE_LOGIN(1001,"로그인 결과"),
    REQ_CODE_JOIN(1002,"회원 가입 결과"),
    REQ_CODE_PERMISSIONS_CAPTURE(100,"화면 녹화 권한"),
    REQ_CODE_PERMISSIONS_MEDIAPROJECTION(101,"화면 녹화"),

    REQ_CODE_PERMISSIONS(3,"퍼미션 획득"),
    REQ_CODE_DEFAULT(0,"기본");

    private int code;
    private String description;

    RequestCodes(int code, String description){
        this.code = code;
        this.description = description;
    }

    public static RequestCodes findCode(int code){
        return Arrays.asList(RequestCodes.values()).stream().filter(data->data.getCode()==code).findAny().orElse(REQ_CODE_DEFAULT);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
