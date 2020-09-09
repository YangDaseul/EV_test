package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CouponVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1010
 * @Brief My차고 + 모빌리티케어 쿠폰
 */
public class GNS_1010 extends BaseData {

    /**
     * @brief GNS_1010 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{

        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String vin){
            this.vin = vin;
            setData(APIInfo.GRA_GNS_1010.getIfCd());
        }
    }

    /**
     * @brief GNS_1010 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("cpnList")
        private List<CouponVO> cpnList;
    }
}
