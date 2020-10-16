package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.SimilarVehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_STO_1001
 * @Brief Genesis + 유사재고차량
 */
public class STO_1001 extends BaseData {
    /**
     * @brief STO_1001 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_STO_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief STO_1001 응답 항목
     * @see #estmVhclList 견적차량목록
     * @see #smlrVhclList 유사차량목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("estmVhclList")
        private SimilarVehicleVO estmVhclList;
        @Expose
        @SerializedName("smlrVhclList")
        private List<SimilarVehicleVO> smlrVhclList;
    }
}
