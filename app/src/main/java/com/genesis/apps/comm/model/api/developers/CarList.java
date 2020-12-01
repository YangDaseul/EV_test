package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file CarList
 * @Brief 내 차량 리스트 조회
 */
public class CarList extends BaseData {
    /**
     * @author hjpark
     * @brief CarList 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        public Request() {
        }
    }

    /**
     * @author hjpark
     * @brief CarList 응답 항목
     * @see #cars 차량리스트
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseData {
        @Expose
        @SerializedName("cars")
        private List<Cars> cars;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }
    /**
     * @author hjpark
     * @brief 차량 정보
     * @see #carId 차량 고유 id
     * @see #carNickname 사용자가 커넥티드 서비스 앱에서 설정한 닉네임
     * @see #carType 차량 타입
     * (GN:가솔린, EV:전기, HEV:하이브리드, PHEV: 플러그인하이브리드, FCEV:수소전기)
     * @see #carName 차종명(차종코드)
     */
    public class Cars {
        @Expose
        @SerializedName("carId")
        private String carId;
        @Expose
        @SerializedName("carNickname")
        private String carNickname;
        @Expose
        @SerializedName("carType")
        private String carType;
        @Expose
        @SerializedName("carName")
        private String carName;
    }
}
