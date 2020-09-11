package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 버틀러 정보
 * @author hjpark
 * @see #vin 차대번호
 * @see #custMgmtNo 고객관리번호
 * @see #asnCd 업체코드
 * @see #asnNm 업체명
 * @see #repTn 대표전화번호
 * @see #pbzAdr 사업장주소
 * @see #mapXcooNm 지도x좌표
 * @see #mapYcooNm 지도y좌표
 * @see #btlrNm 전담매니저명
 * @see #celphNo 핸드폰번호
 * @see #bltrChgYn 매니저변경신청여부
 * Y: 전담 버틀러 변경 신청 중 경우
 * N, C: 전담 버틀러 있는 경우
 *
 * @see #dist 거리(km) btr-1008에서만 취급급 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class BtrVO extends BaseData {
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("custMgmtNo")
    private String custMgmtNo;
    @Expose
    @SerializedName("asnCd")
    private String asnCd;
    @Expose
    @SerializedName("asnNm")
    private String asnNm;
    @Expose
    @SerializedName("repTn")
    private String repTn;
    @Expose
    @SerializedName("pbzAdr")
    private String pbzAdr;
    @Expose
    @SerializedName("mapXcooNm")
    private String mapXcooNm;
    @Expose
    @SerializedName("mapYcooNm")
    private String mapYcooNm;
    @Expose
    @SerializedName("btlrNm")
    private String btlrNm;
    @Expose
    @SerializedName("celphNo")
    private String celphNo;
    @Expose
    @SerializedName("bltrChgYn")
    private String bltrChgYn;


    //btr-1008에서만 데이터 있음
    @Expose
    @SerializedName("dist")
    private String dist;

}
