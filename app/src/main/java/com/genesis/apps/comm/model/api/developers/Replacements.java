package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.developers.OdometerVO;
import com.genesis.apps.comm.model.vo.developers.SestVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file Replacements
 * @Brief 소모품 교환 정보 조회
 */
public class Replacements extends BaseData {
    /**
     * @author hjpark
     * @brief Replacements 요청 항목
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
     * @brief Replacements 응답 항목
     * @see #sests 소모품 리스트
     * @see #odometer 최신 업데이트 누적운행거리 정보
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("sests")
        private List<SestVO> sests;
        @Expose
        @SerializedName("odometer")
        private OdometerVO odometer;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
