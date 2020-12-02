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
 * @file CarId
 * @Brief 내 차량 리스트 조회
 */
public class CarId extends BaseData {
    /**
     * @author hjpark
     * @brief CarId 요청 항목
     * @see #userId
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("userId")
        private String userId;

        public Request(String userId) {
            this.userId = userId;
        }
    }

    /**
     * @author hjpark
     * @brief CarId 응답 항목
     * @see #cars 차량리스트
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseData {
        @Expose
        @SerializedName("cars")
        private List<CarVO> cars;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
}
