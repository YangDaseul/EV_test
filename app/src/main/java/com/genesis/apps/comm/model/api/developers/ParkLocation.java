package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file ParkLocation
 * @Brief 최종 주차 위치 조회
 */
public class ParkLocation extends BaseData {
    /**
     * @author hjpark
     * @brief ParkLocation 요청 항목
     * @see #carId 차량 고유 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("carId")
        private String carId;

        public Request(String carId) {
            this.carId = carId;
        }
    }

    /**
     * @author hjpark
     * @brief ParkLocation 응답 항목
     * @see #timestamp 차량 전송 시간(YYYYMMDDHHmmSS)
     * @see #lat 위도
     * @see #lon 경도
     * @see #alt 고도
     * @see #type projection type
     * (0: WGS84, 1: Bessel, 2: Coord Shift (for China))
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("timestamp")
        private String timestamp;
        @Expose
        @SerializedName("lat")
        private double lat;
        @Expose
        @SerializedName("lon")
        private double lon;
        @Expose
        @SerializedName("alt")
        private int alt;
        @Expose
        @SerializedName("type")
        private int type;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
