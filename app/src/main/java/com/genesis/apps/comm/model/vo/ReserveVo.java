package com.genesis.apps.comm.model.vo;


import android.content.Context;

import androidx.annotation.StringRes;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.QueryString;
import com.genesis.apps.comm.util.StringUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + S-트래픽 충전소 조회
 * @see #sid 충전소ID
 * @see #chgName 충전소명
 * @see #dist 거리(단위:KM)
 * @see #reservYn 예약가능여부
 * @see #carPayUseYn 카페이사용가능여부
 * @see #lat 충전소위치-위도
 * @see #lot 충전소위치-경도
 * @see #daddr 도로명 주소
 * @see #daddrDtl 도로명 주소 상세
 * @see #useStartTime 이용가능 시작 시간
 * @see #useEndTime 이용가능 종료 시간
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
class ReserveVo extends BaseData {
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
    @SerializedName("reservYn")
    private String reservYn;
    @Expose
    @SerializedName("carPayUseYn")
    private String carPayUseYn;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("lot")
    private String lot;
    @Expose
    @SerializedName("daddr")
    private String daddr;
    @Expose
    @SerializedName("daddrDtl")
    private String daddrDtl;
    @Expose
    @SerializedName("useStartTime")
    private String useStartTime;
    @Expose
    @SerializedName("useEndTime")
    private String useEndTime;
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
} // end of class ReservVo
