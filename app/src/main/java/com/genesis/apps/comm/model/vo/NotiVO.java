package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 공지
 * @author hjpark
 * @see #notiCd 공지구분
 * NOTI: 일반공지 (공지를 확인 하지 않아도 앱 사용가능)
 * ANNC: 필독공지 (반드시 공지를 확인 해야만 앱 사용가능)
 * EMGR: 긴급공지 (공지 확인 후 앱 종료)
 * @see #seqNo 일련번호
 * @see #notiTtl 제목
 * @see #notiCont 내용
 *
 * @see #trmsSrtDtm 공지게시일시
 * YYYYMMDDHH24MISS
 *
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class NotiVO extends BaseData {
    //TODO 공지제목, 일련번호 등 키 명을 통일 요청함 MYP-8005, CMN-0001 충돌
    @Expose
    @SerializedName("notiCd")
    private String notiCd;
    @Expose
    @SerializedName("seqNo")
    private String seqNo;
    @Expose
    @SerializedName("notiTtl")
    private String notiTtl;
    @Expose
    @SerializedName("notiCont")
    private String notiCont;


    @Expose
    @SerializedName("trmsSrtDtm")
    private String trmsSrtDtm;
}
