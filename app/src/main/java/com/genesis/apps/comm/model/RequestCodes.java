package com.genesis.apps.comm.model;

import java.util.Arrays;

public enum RequestCodes {

    REQ_CODE_LOGIN(1001,"로그인 결과"),
    REQ_CODE_JOIN(1002,"회원 가입 결과"),
    REQ_CODE_DEFAULT(0,"기본");

    private int cdoe;
    private String description;

    RequestCodes(int cdoe, String description){
        this.cdoe = cdoe;
        this.description = description;
    }

    public static RequestCodes findCode(int code){
        return Arrays.asList(RequestCodes.values()).stream().filter(data->data.getCode()==code).findAny().orElse(REQ_CODE_DEFAULT);
    }

    public int getCode() {
        return cdoe;
    }

    public void setCdoe(int cdoe) {
        this.cdoe = cdoe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
