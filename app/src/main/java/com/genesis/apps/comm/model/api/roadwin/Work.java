package com.genesis.apps.comm.model.api.roadwin;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Work
 * @Brief 기사 위치 정보 요청
 */
public class Work extends BaseData {

    /**
     * @author hjpark
     * @brief Work 요청 항목
     * @see #t 전화번호

     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("t")
        private String t;

        public Request(String t) {
            this.t = t;
        }
    }

    /**
     * @author hjpark
     * @brief Work 응답 항목
     *
     * @Ssee #latitude 위도
     * @Ssee #longitude 경도
     * @Ssee #code
     * 0 기사 실제 접수, 1 기사 앱 off 상태
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseData {
        @Expose
        @SerializedName("latitude")
        private String latitude;
        @Expose
        @SerializedName("longitude")
        private String longitude;
        @Expose
        @SerializedName("code")
        private String code;
    }
}
