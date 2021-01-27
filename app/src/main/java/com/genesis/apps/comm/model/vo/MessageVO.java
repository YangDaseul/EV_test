package com.genesis.apps.comm.model.vo;

import androidx.room.Ignore;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.constants.WeatherCodes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief admin/BANNER 메시지 정보
 * @author hjpark
 * @see #msgTypCd   메시지유형코드
 * TXT : 텍스트만, IMG :  이미지만
 * TXL : 텍스트 + 링크  IML : 이미지 + 링크
 * TIL : 텍스트 + 이미지 + 링크
 * SYS: 고정된 메시지 유형(서버는 APP에 항목값만 전달)
 * @see #txtMsg1        제목
 * @see #txtMsg2        텍스트메시지
 * @see #imgUri	    이미지Uri
 * @see #lnkNm	    링크명
 * 링크버튼명(상세에서 보여주는 정보)
 * @see #lnkTypCd	링크유형코드
 * I : 대표앱 링크  O:외부링크(상세에서 보여주는 정보)
 * @see #lnkUri	    링크Uri
 * @see #bnrNm 배너명
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class MessageVO extends BaseData {
    @Expose
    @SerializedName("msgTypCd")
    private String msgTypCd;
    @Expose
    @SerializedName("ttl")
    private String ttl;
    @Expose
    @SerializedName("txtMsg1")
    private String txtMsg1;
    @Expose
    @SerializedName("txtMsg2")
    private String txtMsg2;
    @Expose
    @SerializedName("imgUri")
    private String imgUri;

    @Expose
    @SerializedName("imgUri1")
    private String imgUri1;
    @Expose
    @SerializedName("imgUri2")
    private String imgUri2;
    @Expose
    @SerializedName("imgUri3")
    private String imgUri3;


    @Expose
    @SerializedName("lnkNm")
    private String lnkNm;
    @Expose
    @SerializedName("iconImgUri")
    private String iconImgUri;

    @Expose
    @SerializedName("lnkTypCd")
    private String lnkTypCd;
    @Expose
    @SerializedName("lnkUri")
    private String lnkUri;
    @Expose
    @SerializedName("bnrNm")
    private String bnrNm;

    @Ignore
    private WeatherCodes weatherCodes;//로컬에서만 사용

    @Ignore
    private String txtMsg;

    @Ignore
    private String siGuGun;
    @Ignore
    private String wthrCdNm;
    @Ignore
    private String t1h;
    @Ignore
    private int dayCd;

    @Ignore
    private boolean isBanner;//로컬 메인 홈 인사이트에서 배너메시지인지 어드민메시지인지 확인 시 사용

    @Ignore
    private int currentPos;//인사이트 영역1에서 현재 이미지 위치 확인 시 사용


}
