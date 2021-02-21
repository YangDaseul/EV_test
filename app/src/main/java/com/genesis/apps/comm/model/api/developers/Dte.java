package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Dte
 * @Brief 주행가능거리 조회
 */
public class Dte extends BaseData {
    /**
     * @author hjpark
     * @brief Dte 요청 항목
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
     * @brief Dte 응답 항목
     * @see #timestamp 차량 전송 시간(YYYYMMDDHHmmSS)
     * @see #value 거리 수치
     * @see #unit 단위(0: feet, 1: km, 2: meter, 3: miles)
     * @see #phevTotalValue PHEV 차량의 Battery + Engine 주행가능거리 거리 수치
     * @see #phevTotalUnit  PHEV 차량의 Battery + Engine 주행가능거리
     * 단위(0: feet, 1: km, 2: meter, 3: miles)
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("timestamp")
        private String timestamp;
        @Expose
        @SerializedName("value")
        private float value;
        @Expose
        @SerializedName("unit")
        private float unit;
        @Expose
        @SerializedName("phevTotalValue")
        private float phevTotalValue;
        @Expose
        @SerializedName("phevTotalUnit")
        private float phevTotalUnit;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
