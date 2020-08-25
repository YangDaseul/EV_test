package com.genesis.apps.comm.model.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 공지
 * @author hjpark
 * @see #notiType 공지구분
 * NOTI: 일반공지 (공지를 확인 하지 않아도 앱 사용가능)
 * ANNC: 필독공지 (반드시 공지를 확인 해야만 앱 사용가능)
 * EMGR: 긴급공지 (공지 확인 후 앱 종료)
 * @see #notiSeq 일련번호
 * @see #notiTitle 제목
 * @see #notiCont 내용
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class NotiVO {
    @Expose
    @SerializedName("notiType")
    private String notiType;
    @Expose
    @SerializedName("notiSeq")
    private String notiSeq;
    @Expose
    @SerializedName("notiTitle")
    private String notiTitle;
    @Expose
    @SerializedName("notiCont")
    private String notiCont;
}
