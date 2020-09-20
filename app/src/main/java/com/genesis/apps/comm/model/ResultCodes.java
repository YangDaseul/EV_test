package com.genesis.apps.comm.model;

import java.util.Arrays;

public enum ResultCodes {
    RES_CODE_NETWORK(-1,"네트워크 통신 에러"),
    REQ_CODE_EMPTY_INTENT(-2,"intent 데이터 없음"),
    REQ_CODE_NORMAL(0,"정상");

    private int code;
    private String description;

    ResultCodes(int code, String description){
        this.code = code;
        this.description = description;
    }

    public static ResultCodes findCode(int code){
        return Arrays.asList(ResultCodes.values()).stream().filter(data->data.getCode()==code).findAny().orElse(REQ_CODE_NORMAL);
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
