package com.genesis.apps.comm.model.constants;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public enum PushCodes {
    PUSH_CODE_ALL("","전체"),
    PUSH_CODE_APP("1000","APP"),
    PUSH_CODE_EVENT("2000","이벤트"),
    PUSH_CODE_COUPON("3000","쿠폰함"),
    PUSH_CODE_REPAIR("4000","정비"),
    PUSH_CODE_MEMBERSHIP("5000","멤버십"),
    PUSH_CODE_INSIGHT("6000","인사이트"),
    PUSH_CODE_EVALUATE("7000","평가하기"),
    PUSH_CODE_3RD_PARTY("8000","3rd Party");

    private String cateCd;
    private String cateNm;

    PushCodes(String cateCd, String cateNm){
        this.cateCd = cateCd;
        this.cateNm = cateNm;
    }

    public static PushCodes findCode(String cateNm){
        return Arrays.asList(PushCodes.values()).stream().filter(data->data.getCateNm().equalsIgnoreCase(cateNm)).findAny().orElse(PUSH_CODE_APP);
    }

    public static PushCodes findCodeByCd(String cateCd){
        return Arrays.asList(PushCodes.values()).stream().filter(data->data.getCateCd().equalsIgnoreCase(cateCd)).findAny().orElse(PUSH_CODE_APP);
    }


    public static List<String> getPushListNm(){
        return Arrays.asList(PushCodes.values()).stream().map(PushCodes::getCateNm).collect(toList());
    }

    public String getCateNm() {
        return cateNm;
    }

    public void setCateNm(String cateNm) {
        this.cateNm = cateNm;
    }

    public String getCateCd() {
        return cateCd;
    }

    public void setCateCd(String cateCd) {
        this.cateCd = cateCd;
    }
}
