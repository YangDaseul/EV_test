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
 * @see #rsvtHopeDt 예약희망일자
 * YYYYMMDD
 * @see #asnNm 업체명
 * @see #raprWorkNm 작업내역
 * @see #milg 주행거리
 * @see #pkckExtapChkUri 픽업외관체크URI
 * @see #dlvryExtapChkUri 딜리버리외관체크URI
 * @see #repnTn 대표전화번호
 * @see #rsvtHopeTm rsvtHopeTm
 * HH24MI
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairHistVO extends BaseData {

    @Expose
    @SerializedName("rsvtHopeDt")
    private String rsvtHopeDt;
    @Expose
    @SerializedName("asnNm")
    private String asnNm;
    @Expose
    @SerializedName("raprWorkNm")
    private String raprWorkNm;
    @Expose
    @SerializedName("milg")
    private String milg;
    @Expose
    @SerializedName("pkckExtapChkUri")
    private String pkckExtapChkUri;
    @Expose
    @SerializedName("dlvryExtapChkUri")
    private String dlvryExtapChkUri;
    @Expose
    @SerializedName("repnTn")
    private String repnTn;
    @Expose
    @SerializedName("rsvtHopeTm")
    private String rsvtHopeTm;

}
