package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ReserveVo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @file GRA_STC_1001
 * @Brif S-트래픽 충전소 조회
 */
public class STC_1001 extends BaseData {

    /**
     * @brief STC_1001 요청 항목
     * @see #vin 차대번호
     * @see #lat 고객위치-위도
     * @see #lot 고객위치-경도
     * @see #reservYn 예약가능여부
     * @see #superSpeedYn 초고속여부
     * @see #highSpeedYn 급속여부
     * @see #slowSpeedYn 완속여부
     * @see #pgNo 페이지 번호
     * @see #pgCnt 페이지출력수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("lat")
        private double lat;
        @Expose
        @SerializedName("lot")
        private double lot;
        @Expose
        @SerializedName("reservYn")
        private String reservYn;
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
        @SerializedName("pgNo")
        private String pgNo;
        @Expose
        @SerializedName("pgCnt")
        private String pgCnt;

        public Request(String menuId, String vin, double lat, double lot, String reservYn, String superSpeedYn, String highSpeedYn, String slowSpeedYn, String pgNo, String pgCnt) {
            this.vin = vin;
            this.lat = lat;
            this.lot = lot;
            this.reservYn = reservYn;
            this.superSpeedYn = superSpeedYn;
            this.highSpeedYn = highSpeedYn;
            this.slowSpeedYn = slowSpeedYn;
            this.pgNo = pgNo;
            this.pgCnt = pgCnt;
            setData(APIInfo.GRA_STC_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief STC_1001 응답 항목
     * @see #reservList 최근예약충전소리스트
     * @see #searchList 검색충전소리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("reservList")
        private List<ReserveVo> reservList;
        @Expose
        @SerializedName("searchList")
        private List<ReserveVo> searchList;
    }
} // end of class STC_1001
