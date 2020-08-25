package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @Brief 알림 정보
 * @see #notiNo 알림일련번호
 * @see #cateCd 카테고리코드
 * @see #cateNm 카테고리명
 * @see #notDt 알림일시
 * @see #title 제목
 * @see #contents 내용
 * @see #linkUrl 링크 URL
 * @see #imgUrl 이미지 URL
 * @see #readYn 읽기확인 여부
 * 확인상태 : Y, 미확인상태 : N
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class NotiInfoVO extends BaseData {
    @Expose
    @SerializedName("notiNo")
    private String notiNo;
    @Expose
    @SerializedName("cateCd")
    private String cateCd;
    @Expose
    @SerializedName("cateNm")
    private String cateNm;
    @Expose
    @SerializedName("notDt")
    private String notDt;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("contents")
    private String contents;
    @Expose
    @SerializedName("linkUrl")
    private String linkUrl;
    @Expose
    @SerializedName("imgUrl")
    private String imgUrl;
    @Expose
    @SerializedName("readYn")
    private String readYn;
}
