package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1024 extends BaseData {
    /**
     * @brief CHB_1024 요청 항목
     * @see #orderId    주문ID
     * @see #vin        차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("orderId")
        private String orderId;
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String orderId, String vin) {
            this.orderId = orderId;
            this.vin = vin;
            setData(APIInfo.GRA_CHB_1024.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1024 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
