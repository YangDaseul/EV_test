package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RemoteHistoryVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_RMT_1003
 * @Brief 원격진단신청 내역조회
 */
public class RMT_1003 extends BaseData {
    /**
     * @brief RMT_1003 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        public Request(String menuId, String vin) {
            this.vin = vin;
            setData(APIInfo.GRA_RMT_1003.getIfCd(), menuId);
        }
    }

    /**
     * @brief RMT_1003 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("aplyList")
        private List<RemoteHistoryVO> aplyList;
    }
}
