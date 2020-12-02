package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.developers.CarConnectVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file CarConnect
 * @Brief 차량을 신규 서비스에 등록
 */
public class CarConnect extends BaseData {
    /**
     * @author hjpark
     * @brief CarConnect 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("cars")
        private CarConnectVO cars;

        public Request(CarConnectVO cars) {
            this.cars = cars;
        }
    }

    /**
     * @author hjpark
     * @brief CarConnect 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    abstract
    class Response extends BaseData {

    }
}
