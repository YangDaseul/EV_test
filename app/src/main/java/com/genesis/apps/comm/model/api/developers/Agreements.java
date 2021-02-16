package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Agreements
 * @Brief 약관동의여부 확인
 */
public class Agreements extends BaseData {
    /**
     * @author hjpark
     * @brief Agreements 요청 항목
     * @see #userId
     * @see #carId
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("userId")
        private String userId;
        @Expose
        @SerializedName("carId")
        private String carId;
        @Expose
        @SerializedName("token")
        private String token;

        public Request(String userId, String carId, String token) {
            this.userId = userId;
            this.carId = carId;
            this.token = token;
        }
    }

    /**
     * @author hjpark
     * @brief Agreements 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("result")
        private int result;
    }
}
