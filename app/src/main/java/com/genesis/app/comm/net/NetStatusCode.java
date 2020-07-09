package com.genesis.app.comm.net;


import java.util.Arrays;

/**
 * @biref 네트워크 상태 코드
 * 통신 시 예외에 대한 정의 코드로 8가지로 구성됨
 * {@link NetResult}에서 사용
 */
public enum NetStatusCode {
    SUCCESS(0, "성공"),
    ERR_EXCEPTION_DKC(-1, "DKC EXCEPTION"),
    ERR_EXCEPTION_HTTP(-2, "HTTP EXCEPTION"),
    ERR_EXCEPTION_UNKNOWN(-3, "ETC EXCEPTION"),
    ERR_DATA_NULL(-4, "DATA NULL"),
    ERR_ISSUE_SOURCE(-5, "SOURCE PROBLEM"),
    ERR_DATA_INCORRECT(-6, "DATA INCORRECT"),
    ERR_DATA_FAIL(-7, "DATA PROCEED FAIL");

    private int code;
    private String description;

    NetStatusCode(int code, String description) {
        this.code = code;
        this.description = description;
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

    public static NetStatusCode findCode(int code, NetStatusCode defaultCode) {
        return Arrays.asList(NetStatusCode.values()).stream().filter(data -> data.getCode()==code).findAny().orElse(defaultCode);
    }
}
