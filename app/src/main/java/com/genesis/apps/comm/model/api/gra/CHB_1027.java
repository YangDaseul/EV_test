package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1027 extends BaseData {
    /**
     * @brief CHB_1027 요청 항목
     * @see #orderId    주문ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("orderId")
        private String orderId;

        public Request(String menuId, String orderId) {
            this.orderId = orderId;
            setData(APIInfo.GRA_CHB_1027.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1027 응답 항목
     *
     * @see #orderId    주문ID
     * @see #bookingDtm 예약일시 YYYYMMDDHH24MISS
     * @see #carNo  차량번호
     * @see #address    주소
     * @see #addressDetail  상세 주소
     * @see #svcNm  서비스명
     * "충전" 또는 "충전, 세차"
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("orderId")
        private String orderId;
        @Expose
        @SerializedName("bookingDtm")
        private String bookingDtm;
        @Expose
        @SerializedName("carNo")
        private String carNo;
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("addressDetail")
        private String addressDetail;
        @Expose
        @SerializedName("svcNm")
        private String svcNm;
    }
}
