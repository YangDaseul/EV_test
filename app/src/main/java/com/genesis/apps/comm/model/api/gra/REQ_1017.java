package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RepCostVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1017
 * @Brief service + 정비소예약 - 대표가격조회
 */
public class REQ_1017 extends BaseData {
    /**
     * @brief REQ_1017 요청 항목
     * @see #asnCd 정비망코드
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("asnCd")
        private String asnCd;

        public Request(String menuId, String asnCd){
            this.asnCd = asnCd;
            setData(APIInfo.GRA_REQ_1017.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1017 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("repCostList")
        private List<RepCostVO> repCostList;
    }
}
