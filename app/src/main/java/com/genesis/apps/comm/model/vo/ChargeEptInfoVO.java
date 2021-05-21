package com.genesis.apps.comm.model.vo;

import android.content.Context;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.QueryString;
import com.genesis.apps.comm.util.StringUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + E-PIT 충전소 목록 조회
 * @see #spid 환경부-기관ID
 * 환경부에서 발급한 ID
 * @see #csid 환경부-충전소ID
 * 환경부에서 발급한 ID
 * @see #espid EPIT-기관ID
 * @see #ecsid EPIT-충전소ID
 * @see #csnm 충전소명
 * @see #dist 거리
 * 단위 KM
 * @see #lat 충전소-위도
 * @see #lot 충전소-경도
 * @see #daddr 주소
 * 충전소 주소(도로명)
 * @see #addrDtl 상세주소
 * 상세 위치
 * @see #spnm 운영사업자명
 * E-PIT-기관명
 * @see #useYn 충전소운영여부
 * Y: 운영 N:미운영
 * @see #useTime 이용가능시간
 * @see #spcall 충전기문의연락처
 * @see #reservYn 예약가능여부
 * 환경부-기관ID = 'ST' (s-트래픽) 인경우에 'Y'
 * 그 외는 'N'
 * @see #genYn 제네시스전용충전소여부
 * Y: 제네시스전용충전소 N:제네시스전용충전소아님
 * @see #payType 결제방식
 * GCP : 제네시스카페이
 * STP : S-트래픽 포인트
 * CRT : 신용카드  ==> 디폴트
 * ex) ["GCP", "STP", "CRT" ]
 * 에스트래픽 :  신용카드, 충전크레딧은 디폴드
 * 에스트래픽 외 : 신용카드 디폴트
 * 제네시스카페이는 옵션
 * @see #chgrUpdDtm 충전기상태갱신시간
 * YYYYMMDDHH24MISS
 * 충전기리스트 중에서 가장 최근값으로 지정
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
class ChargeEptInfoVO extends BaseData {
    @Expose
    @SerializedName("spid")
    private String spid;
    @Expose
    @SerializedName("csid")
    private String csid;
    @Expose
    @SerializedName("espid")
    private String espid;
    @Expose
    @SerializedName("ecsid")
    private String ecsid;
    @Expose
    @SerializedName("csnm")
    private String csnm;
    @Expose
    @SerializedName("dist")
    private String dist;
    @Expose
    @SerializedName("lot")
    private String lot;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("daddr")
    private String daddr;
    @Expose
    @SerializedName("addrDtl")
    private String addrDtl;
    @Expose
    @SerializedName("spnm")
    private String spnm;
    @Expose
    @SerializedName("useYn")
    private String useYn;
    @Expose
    @SerializedName("useTime")
    private String useTime;
    @Expose
    @SerializedName("spcall")
    private String spcall;
    @Expose
    @SerializedName("reservYn")
    private String reservYn;
    @Expose
    @SerializedName("genYn")
    private String genYn;
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

    public boolean isReserve(){
        //사용가능
        int totalUseAbleCnt = StringUtil.isValidInteger(getUsablSuperSpeedCnt()) + StringUtil.isValidInteger(getUsablHighSpeedCnt()) + StringUtil.isValidInteger(getUsablSlowSpeedCnt());
        //사용중
        int totalUseCnt = StringUtil.isValidInteger(getUseSuperSpeedCnt()) + StringUtil.isValidInteger(getUseHighSpeedCnt()) + StringUtil.isValidInteger(getUseSlowSpeedCnt());

        return "Y".equalsIgnoreCase(getReservYn())&& (totalUseAbleCnt>0||totalUseAbleCnt==0&&totalUseCnt>0);
    }
} // end of class ChargeEptInfoVO
