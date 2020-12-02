package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Target
 * @Brief 안전운전점수 가입여부 조회
 */
public class Target extends BaseData {
    /**
     * @author hjpark
     * @brief Target 요청 항목
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
     * @brief Target 응답 항목
     * @see #targetYn UBI 서비스 가입 여부(Y/N)
     * @see #supportedYn
     * targetYn이 N인 경우 리턴,
     * UBI 서비스 가입 가능 차종 여부(Y/N)
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("targetYn")
        private String targetYn;
        @Expose
        @SerializedName("supportedYn")
        private String supportedYn;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
