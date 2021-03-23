package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.developers.CarConnectVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
        private List<CarConnectVO> cars;
        //여기서 userId는.. url 생성 용도로만 사용. 실제로 해당 규격서에서 해당 데이터를 요청하지 않음
        @Expose
        @SerializedName("userId")
        private String userId;

        public Request(List<CarConnectVO> cars, String userId) {
            this.cars = cars;
            this.userId = userId;
        }
    }

    /**
     * @author hjpark
     * @brief CarConnect 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {

    }
}
