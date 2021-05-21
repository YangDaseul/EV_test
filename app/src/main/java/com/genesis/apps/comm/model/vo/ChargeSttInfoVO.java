package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.QueryString;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + S-트래픽 충전소 상세조회
 * @see #sid 충전소ID
 * @see #chgName 충전소명
 * @see #dist 거리
 * @see #daddr 도로명주소
 * @see #daddrDtl 도로명주소상세
 * @see #lat 충전소위치-위도
 * @see #lot 충전소위치-경도
 * @see #useStartTime 이용가능시작시간
 * HH:MM
 * @see #useEndTime 이용가능종료시간
 * HH:MM
 * @see #bname 운영기관명칭
 * @see #bcall 운영기관연락처
 * @see #reservYn 예약가능여부
 * Y:가능 N:불가
 * @see #payType 결제 방식
 * GCP : 제네시스카페이
 * STP : S-트래픽 포인트 ==> 디폴트
 * CRT : 신용카드  ==> 디폴트
 * ex) ["GCP", "STP", "CRT" ]
 * 결제방식 : 에스트래픽은  신용카드와 충전크레딧은 기본적 가능
 * 카페이는 가능여부 확인 필요
 * @see #chgrUpdDtm 충전기상태갱신시간
 * YYYYMMDDHH24MISS
 * 충전기리스트 중에서 가장 최근값으로 지정(수정시간)
 * @see #useSuperSpeedCnt 사용중 초고속충전기수
 * @see #useHighSpeedCnt 사용중 급속충전기수
 * @see #useSlowSpeedCnt 사용중 완속충전기수
 * @see #usablSuperSpeedCnt 사용가능 초고속충전기수
 * @see #usablHighSpeedCnt 사용가능 급속충전기수
 * @see #usablSlowSpeedCnt 사용가능 완속충전기수
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ChargeSttInfoVO extends BaseData {
    @Expose
    @SerializedName("sid")
    private String sid;
    @Expose
    @SerializedName("chgName")
    private String chgName;
    @Expose
    @SerializedName("dist")
    private String dist;
    @Expose
    @SerializedName("daddr")
    private String daddr;
    @Expose
    @SerializedName("daddrDtl")
    private String daddrDtl;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("lot")
    private String lot;
    @Expose
    @SerializedName("useStartTime")
    private String useStartTime;
    @Expose
    @SerializedName("useEndTime")
    private String useEndTime;
    @Expose
    @SerializedName("bname")
    private String bname;
    @Expose
    @SerializedName("bcall")
    private String bcall;
    @Expose
    @SerializedName("reservYn")
    private String reservYn;
    @Expose
    @SerializedName("payType")
    private List<String> payType;
    @Expose
    @SerializedName("chgrUpdDtm")
    private String chgrUpdDtm;
    @Expose
    @SerializedName("useSuperSpeedCnt")
    private String useSuperSpeedCnt;
    @Expose
    @SerializedName("useHighSpeedCnt")
    private String useHighSpeedCnt;
    @Expose
    @SerializedName("useSlowSpeedCnt")
    private String useSlowSpeedCnt;
    @Expose
    @SerializedName("usablSuperSpeedCnt")
    private String usablSuperSpeedCnt;
    @Expose
    @SerializedName("usablHighSpeedCnt")
    private String usablHighSpeedCnt;
    @Expose
    @SerializedName("usablSlowSpeedCnt")
    private String usablSlowSpeedCnt;
} // end of class ChargeSttInfoVO
