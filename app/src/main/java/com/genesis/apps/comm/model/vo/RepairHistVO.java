package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 정비소 정보
 * @author hjpark
 * @see #wrhsDtm 입고일자
 * yyyyMMdd
 * @see #asnCd 업체코드
 * @see #asnNm 업체명
 * @see #rparWorkNm 작업내역
 * @see #milg 주행거리
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairHistVO extends BaseData {
    @Expose
    @SerializedName("wrhsDtm")
    private String wrhsDtm;
    @Expose
    @SerializedName("asnCd")
    private String asnCd;
    @Expose
    @SerializedName("asnNm")
    private String asnNm;
    @Expose
    @SerializedName("rparWorkNm")
    private String rparWorkNm;
    @Expose
    @SerializedName("milg")
    private String milg;

    @Expose
    @SerializedName("wrhsNo")
    private String wrhsNo;
    @Expose
    @SerializedName("vhclInoutNo")
    private String vhclInoutNo;


    //LOCAL 에서만 사용
    //yyyyMMdd를 기준으로 가장 빠른 데이터 확인
    //2020-12-22 기준 사용안함 (쩡책변경)
    private boolean isFirst;

    private boolean isRepairImage;
}
