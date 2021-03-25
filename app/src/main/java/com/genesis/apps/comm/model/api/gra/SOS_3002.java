package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_3002
 * @Brief 긴급충전출동 신청,  EV 충전잔여횟수가 0보다 큰 경우 신청가능
 */
public class SOS_3002 extends BaseData {
    /**
     * @brief SOS_3002 요청 항목
     *
     * @see #vin 차대번호
     * @see #carRegNo 차량번호
     * @see #mdlCd 차종코드
     * @see #areaClsCd 지역구분코드
     * R - 일반 도로
     * H - 자동차 전용도로/고속도로
     * @see #addr 고객위치주소
     * @see #addrX 고객위치주소_X좌표
     * 고객위치 좌표 X (WGS84 좌표계)
     * @see #addrY 고객위치주소_Y좌표
     * 고객위치 좌표 Y (WGS84 좌표계)
     * @see #celphNo 휴대전화번호
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carRegNo")
        private String carRegNo;
        @Expose
        @SerializedName("mdlCd")
        private String mdlCd;
        @Expose
        @SerializedName("areaClsCd")
        private String areaClsCd;
        @Expose
        @SerializedName("addr")
        private String addr;
        @Expose
        @SerializedName("addrX")
        private String addrX;
        @Expose
        @SerializedName("addrY")
        private String addrY;
        @Expose
        @SerializedName("celphNo")
        private String celphNo;

        public Request(String menuId, String vin, String carRegNo, String mdlCd, String areaClsCd, String addr, String addrX, String addrY, String celphNo){
            this.vin = vin;
            this.carRegNo = carRegNo;
            this.mdlCd = mdlCd;
            this.areaClsCd = areaClsCd;
            this.addr = addr;
            this.addrX = addrX;
            this.addrY = addrY;
            this.celphNo = celphNo;
            setData(APIInfo.GRA_SOS_3002.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_3002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("tmpAcptNo")
        private String tmpAcptNo;
        @Expose
        @SerializedName("tmpAcptDtm")
        private String tmpAcptDtm;
        @Expose
        @SerializedName("carRegNo")
        private String carRegNo;
        @Expose
        @SerializedName("areaClsCd")
        private String areaClsCd;
        @Expose
        @SerializedName("addr")
        private String addr;
    }
}
