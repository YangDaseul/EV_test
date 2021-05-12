package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ChargeEptInfoVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @file GRA_EPT_1002
 * @Brif E-PIT 충전소 목록 조회
 */
public class EPT_1001 extends BaseData {
    /**
     * @brief EPT_1001 요청 항목
     * @see #vin 차대번호
     * @see #lat 고객위치-위도
     * 현재위치/차량위치/지명검색위치
     * @see #lot 고객위치-경도
     * 현재위치/차량위치/지명검색위치
     * @see #reservYn 예약가능여부
     * Y: 예약가능한 충전소 N:
     * @see #chgCd 충전소구분 코드
     * GN:제네시스전용충전소 EP : E-pit 충전소
     * @see #chgSpeed 충전속도
     * SUPER 초고속 (260KW이상)
     * HIGH 고속 (260KW 미만 140KW 이상)
     * SLOW 완속
     * ex) ["SUPER", "HIGH", "SLOW"]
     * @see #payType 결제방식
     * GCP : 제네시스카페이
     * STP : S-트래픽 포인트
     * ex) ["GCP", "STP" ]
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("lot")
        private String lot;
        @Expose
        @SerializedName("reservYn")
        private String reservYn;
        @Expose
        @SerializedName("chgCd")
        private String chgCd;
        @Expose
        @SerializedName("chgSpeed")
        private List<String> chgSpeed;
        @Expose
        @SerializedName("payType")
        private List<String> payType;

        public Request(String menuId, String vin, String lat, String lot, String reservYn, String chgCd, List<String> chgSpeed, List<String> payType) {
            this.vin = vin;
            this.lat = lat;
            this.lot = lot;
            this.reservYn = reservYn;
            this.chgCd = chgCd;
            this.chgSpeed = chgSpeed;
            this.payType = payType;
            setData(APIInfo.GRA_EPT_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief EPT_1001 응답 항목
     * @see #chgList 충전소목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Response extends BaseResponse {
        @Expose
        @SerializedName("chgList")
        private List<ChargeEptInfoVO> chgList;
    }
} // end of class EPT_1001
