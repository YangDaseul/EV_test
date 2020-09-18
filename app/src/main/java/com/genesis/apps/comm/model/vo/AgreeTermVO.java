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
 * @see #termCode 약관구분코드
 * 앱 이용약관 : 1000
 * 개인정보처리방침 : 2000
 * 개인정보 수집/이용 : 3000
 * 광고성 정보 수신동의 : 4000
 * 제네시스 멤버십 가입 약관  : 5000
 * @see #agreeYn 동의
 * Y : 동의, 그외 : N
 * @see #termName 동의약관명
 * @see #agreeDate 동의일자
 * YYYYMMDDHH24MMSS
 * @see #agreeMeans 동의수단
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class AgreeTermVO extends BaseData {
    @Expose
    @SerializedName("termCode")
    private String termCode;
    @Expose
    @SerializedName("agreeYn")
    private String agreeYn;
    @Expose
    @SerializedName("termName")
    private String termName;
    @Expose
    @SerializedName("agreeDate")
    private String agreeDate;
    @Expose
    @SerializedName("agreeMeans")
    private AgreeMeansVO agreeMeans;

}
