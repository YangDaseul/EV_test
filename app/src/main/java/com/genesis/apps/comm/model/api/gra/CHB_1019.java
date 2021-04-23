package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1019 extends BaseData {
    /**
     * @brief CHB_1019 리뷰문구 요청 요청 항목
     * @see #svcCd 서비스코드
     *  - CHRGBTR : 충전버틀러
     * @SEE #orderId 주문 ID
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("svcCd")
        private String svcCd;
        @Expose
        @SerializedName("orderId")
        private String orderId;

        public Request(String menuId, String svcCd, String orderId) {
            this.svcCd = svcCd;
            this.orderId = orderId;
            setData(APIInfo.GRA_CHB_1019.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1019 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rvwStmt")
        private String rvwStmt;
    }
}
