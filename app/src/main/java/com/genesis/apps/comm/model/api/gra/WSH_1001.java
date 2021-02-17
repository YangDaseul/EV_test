package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
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

        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId,String cmpyCd, String vin){
            this.cmpyCd = cmpyCd;
            this.vin = vin;
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
        //2020-12-08 추가
        @Expose
        @SerializedName("godsCostUri")
        private String godsCostUri;
    }
}
