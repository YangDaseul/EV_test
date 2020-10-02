package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.CouponVO;
import com.genesis.apps.comm.model.vo.WarrantyVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_WRT_1001
 * @Brief My차고 + 모빌리티케어 쿠폰
 */
public class WRT_1001 extends BaseData {

    /**
     * @brief WRT_1001 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_WRT_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief WRT_1001 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("contList")
        private List<WarrantyVO> contList;
    }
}
