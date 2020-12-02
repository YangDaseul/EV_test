package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.developers.OdometerVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Dte
 * @Brief 주행가능거리 조회
 */
public class Odometer extends BaseData {
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
     * @see #odometers 누적 운행 거리
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    abstract
    class Response extends BaseData {
        @Expose
        @SerializedName("odometers")
        private OdometerVO odometers;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
