package com.genesis.apps.comm.model.constants;

import java.util.Arrays;

public enum RequestCodes {
    REQ_CODE_ACTIVITY(1000,"일반 페이지 진입"),
    REQ_CODE_LOGIN(1001,"로그인 결과"),
    REQ_CODE_JOIN(1002,"회원 가입 결과"),
    REQ_CODE_AUTHUUID(1003,"번호 인증 결과"),
    REQ_CODE_JOIN_SERVICE(1004,"서비스 가입 완료"),
    REQ_CODE_RELEASE(1005,"서비스 탈퇴"),
    REQ_CODE_PERMISSIONS_CAPTURE(100,"화면 녹화 권한"),
    REQ_CODE_PERMISSIONS_MEDIAPROJECTION(101,"화면 녹화"),
    REQ_CODE_PLAY_VIDEO(102,"녹화 영상 재생"),

    REQ_CODE_PERMISSIONS(3,"퍼미션 획득"),

    REQ_CODE_FILECHOOSER(2002,"파일 획득 권한(webview)"),
    REQ_CODE_GPS(2003,"GPS 설정"),
    REQ_CODE_SERVICE_HOMETOHOME_PCKP(2004,"홈투홈 픽업 주소 설정"),
    REQ_CODE_SERVICE_HOMETOHOME_DELIVERY(2005,"홈투홈 딜리버리 주소 설정"),

    REQ_CODE_FROM_ADDRESS(3001,"출발지 주소"),
    REQ_CODE_TO_ADDRESS(3002,"도착지 주소"),

    REQ_CODE_PAYMENT_WEB_VIEW(4001,"결제 웹뷰"),
    REQ_CODE_RELAPSE_REQ(5001,"하자 재발 통보"),

    REQ_CODE_TS_AUTH(6001,"중고차 인증"),

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
