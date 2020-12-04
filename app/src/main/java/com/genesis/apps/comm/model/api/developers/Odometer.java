package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.developers.OdometerVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Odometer
 * @Brief 총 주행거리
 */
public class Odometer extends BaseData {
    /**
     * @author hjpark
     * @brief Odometer 요청 항목
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
     * @brief Odometer 응답 항목
     * @see #odometers 누적 운행 거리
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    abstract
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("odometers")
        private OdometerVO odometers;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
