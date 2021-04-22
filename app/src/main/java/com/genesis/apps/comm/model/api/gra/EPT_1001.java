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
     * @see #lot 고객위치-경도
     * @see #chgCd 충전소구분 코드
     * GN:제네시스전용충전소 EP : E-pit 충전소
     * @see #superSpeedYn 초고속여부
     * @see #highSpeedYn 급속여부
     * @see #slowSpeedYn 완속여부
     * @see #carPayYn 카페이지원여부
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
        @SerializedName("chgCd")
        private String chgCd;
        @Expose
        @SerializedName("superSpeedYn")
        private String superSpeedYn;
        @Expose
        @SerializedName("highSpeedYn")
        private String highSpeedYn;
        @Expose
        @SerializedName("slowSpeedYn")
        private String slowSpeedYn;
        @Expose
        @SerializedName("carPayYn")
        private String carPayYn;

        public Request(String menuId, String vin, String lat, String lot, String chgCd, String superSpeedYn, String highSpeedYn, String slowSpeedYn, String carPayYn) {
            this.vin = vin;
            this.lat = lat;
            this.lot = lot;
            this.chgCd = chgCd;
            this.superSpeedYn = superSpeedYn;
            this.highSpeedYn = highSpeedYn;
            this.slowSpeedYn = slowSpeedYn;
            this.carPayYn = carPayYn;
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
