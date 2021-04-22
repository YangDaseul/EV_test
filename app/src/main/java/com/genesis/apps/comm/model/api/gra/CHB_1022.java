package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1022 extends BaseData {
    /**
     * @brief CHB_1022 요청 항목
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

        public Request(String menuId, String orderId, String vin){
            this.orderId = orderId;
            this.vin = vin;
            setData(APIInfo.GRA_CHB_1022.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1022 응답 항목
     * @see #workerName 기사 이름
     * @see #workerHpNo 기사 전화번호
     * @see #latitude   위도
     * @see #longitude  경도
     * @see #address    주소
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("workerName")
        private String workerName;
        @Expose
        @SerializedName("workerHpNo")
        private String workerHpNo;
        @Expose
        @SerializedName("latitude")
        private double latitude;
        @Expose
        @SerializedName("longitude")
        private double longitude;
        @Expose
        @SerializedName("address")
        private String address;
    }
}
