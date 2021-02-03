package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.developers.CarVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file CheckJoinCCS
 * @Brief CCS 가입 여부 확인
 */
public class CheckJoinCCS extends BaseData {
    /**
     * @author hjpark
     * @brief CheckJoinCCS 요청 항목
     * @see #userId
     * @see #vin
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("userId")
        private String userId;
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String userId, String vin) {
            this.userId = userId;
            this.vin = vin;
        }
    }

    /**
     * @author hjpark
     * @brief CheckJoinCCS 응답 항목
     * @see #master true or false
     * @see #carId
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("master")
        private boolean master;
        @Expose
        @SerializedName("carId")
        private String carId;
    }
}
