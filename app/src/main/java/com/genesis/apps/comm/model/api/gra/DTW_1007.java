package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class DTW_1007 extends BaseData {
    /**
     * @brief DTW_1007 요청 항목
     * @see #payTrxId   결제트랜잭션ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("payTrxId")
        private String payTrxId;

        public Request(String menuId, String payTrxId) {
            this.payTrxId = payTrxId;
            setData(APIInfo.GRA_DTW_1007.getIfCd(), menuId);
        }
    }

    /**
     * @brief DTW_1007 응답 항목
     *
     * @see #chgEndDtm  사용일시 YYYYMMDDHH24MISS
     * @see #chgNm  충전소명
     * @see #payAmt 고객청구금액
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("chgEndDtm")
        private String chgEndDtm;
        @Expose
        @SerializedName("chgNm")
        private String chgNm;
        @Expose
        @SerializedName("payAmt")
        private String payAmt;
    }
}
