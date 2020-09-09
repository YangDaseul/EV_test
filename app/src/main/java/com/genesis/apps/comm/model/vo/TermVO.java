package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 약관정보
 * @author hjpark
 * @see #termVer 결과코드
 * @see #termCd 약관구분코드
 * 앱 이용약관 : 1000
 * 개인정보처리방침 : 2000
 * 개인정보 수집/이용 : 3000
 * 광고성 정보 수신동의 : 4000
 * 제네시스 멤버십 가입 약관  : 5000
 * @see #termNm 약관명
 * @see #termCont 약관내용
 * html 형식에 문자열
 * @see #termEsnAgmtYn 약관필수동의여부
 * Y:필수동의약관 N:선택동의약관
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class TermVO extends BaseData {
    @Expose
    @SerializedName("termVer")
    private String termVer;
    @Expose
    @SerializedName("termCd")
    private String termCd;
    @Expose
    @SerializedName("termNm")
    private String termNm;
    @Expose
    @SerializedName("termCont")
    private String termCont;
    @Expose
    @SerializedName("termEsnAgmtYn")
    private String termEsnAgmtYn;
}
