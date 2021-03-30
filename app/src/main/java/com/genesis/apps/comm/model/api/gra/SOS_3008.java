package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.SOSStateVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_3008
 * @Brief 신청이력조회
 */
public class SOS_3008 extends BaseData {
    /**
     * @brief SOS_3008 요청 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_SOS_3008.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_3008 응답 항목
     * @see #useYn 신청 가능 여부
     * @see #dataCount 잔여횟수  고객명, 생년월일 기준 EV 충전 잔여 횟수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("useYn")
        private String useYn;
        @Expose
        @SerializedName("dataCount")
        private String dataCount;
    }
}
