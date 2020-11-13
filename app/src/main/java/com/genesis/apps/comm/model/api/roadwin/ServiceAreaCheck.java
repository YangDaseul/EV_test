package com.genesis.apps.comm.model.api.roadwin;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SERVICE_AREA_CHECK
 * @Brief Genesis + 대리운전 취소 요청
 */
public class ServiceAreaCheck extends BaseData {

    /**
     * @author hjpark
     * @brief ServiceAreaCheck 요청 항목
     * @see #latitude 위도
     * @see #longitude 경도

     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("latitude")
        private String latitude;
        @Expose
        @SerializedName("longitude")
        private String longitude;

        public Request(String longitude, String latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }

    /**
     * @author hjpark
     * @brief ServiceAreaCheck 응답 항목
     *
     * @Ssee #rspCode 1 가능, 그 외 불가능
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseData {
        @Expose
        @SerializedName("rspCode")
        private String rspCode;
    }
}
