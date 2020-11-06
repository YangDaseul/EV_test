package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 대리운전에서 사용하는 위치정보
 * @author hjpark
 * @see #posDivCd   위치구분코드
 * 출발지: DEPT, 도착지 : DEST
 * @see #latCoord	위도 좌표
 * @see #lonCoord	경도 좌표
 * @see #addrLotNo	주소(지번)
 * @see #addrBldNm	주소(건물명)
 * @see #addrDtl	주소(상세주소)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class PositionVO extends BaseData {
    public static final String POSITION_STARTING ="DEPT";   //출발지
    public static final String POSITION_DESTINATION ="DEST";//도착지

    @Expose
    @SerializedName("posDivCd")
    private String posDivCd;
    @Expose
    @SerializedName("latCoord")
    private String latCoord;
    @Expose
    @SerializedName("lonCoord")
    private String lonCoord;
    @Expose
    @SerializedName("addrLotNo")
    private String addrLotNo;
    @Expose
    @SerializedName("addrBldNm")
    private String addrBldNm;
    @Expose
    @SerializedName("addrDtl")
    private String addrDtl;
}
