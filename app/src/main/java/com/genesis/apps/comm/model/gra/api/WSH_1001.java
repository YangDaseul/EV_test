package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.WashGoodsVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_WSH_1001
 * @Brief service + 소낙스 세차이용권 조회
 * @author hjpark
 */
public class WSH_1001 extends BaseData {
    /**
     * @brief WSH_1001의 요청 항목
     * @author hjpark
     * @see #cmpyCd 업체코드
     * 소낙스  : SONAX
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {

        @Expose
        @SerializedName("cmpyCd")
        private String cmpyCd;

        public Request(String menuId,String cmpyCd){
            this.cmpyCd = cmpyCd;
            setData(APIInfo.GRA_WSH_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief WSH_1001의 응답 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("godsList")
        private List<WashGoodsVO> godsList;
    }
}
