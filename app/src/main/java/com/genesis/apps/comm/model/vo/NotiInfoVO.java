package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @Brief 알림 정보
 * @see #notiNo 알림일련번호
 * @see #cateCd 카테고리코드(대분류코드)
 * @see #cateNm 카테고리명 (대분류명)
 * @see #notDt 알림일시 (YYYYMMSSHH24MI) / 발송일시
 * @see #title 제목
 * @see #contents 내용
 * @see #readYn 읽기확인 여부
 * 확인상태 : Y, 미확인상태 : N
 * @see #msgLnkCd 메시지링크코드
 * I : 대표앱 링크  O:외부링크
 * @see #msgLnkUri 메시지링크URI
 * 메시지 타이틀의 우측 링크 URI
 * @see #dtlLnkCd 자세히링크코드
 * I : 대표앱 링크  O:외부링크
 * @see #dtlLnkUri 자세히링크URI
 * 메시지 Body의 하단 링크 URI
 * @see #imgFilUri1 이미지파일Uri1
 * 이미지 파일 uri
 * @see #imgFilUri2 이미지파일Uri2
 * 이미지 파일 uri
 * @see #imgFilUri3 이미지파일Uri3
 * 이미지 파일 uri
 *
 */

@Entity
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class NotiInfoVO extends BaseData {

    public NotiInfoVO(){

    }

    @PrimaryKey(autoGenerate = true)
    private int _id;

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
    @SerializedName("readYn")
    private String readYn;

    @Expose
    @SerializedName("msgLnkCd")
    private String msgLnkCd;
    @Expose
    @SerializedName("msgLnkUri")
    private String msgLnkUri;
    @Expose
    @SerializedName("dtlLnkCd")
    private String dtlLnkCd;
    @Expose
    @SerializedName("dtlLnkUri")
    private String dtlLnkUri;
    @Expose
    @SerializedName("imgFilUri1")
    private String imgFilUri1;
    @Expose
    @SerializedName("imgFilUri2")
    private String imgFilUri2;
    @Expose
    @SerializedName("imgFilUri3")
    private String imgFilUri3;
    @Expose
    @SerializedName("dtlLnkNm")
    private String dtlLnkNm;
}
