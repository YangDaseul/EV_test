package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1007 extends BaseData {
    /**
     * @brief CHB_1007 요청 항목
     * @see #address 픽업 주소
     * @see #addressDetail 픽업 상세 주소(선택)
     * @see #latitude 고객좌표 위도
     * @see #longitude 고객좌표 경도
     * @see #buildingName 건물명(선택)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("addressDetail")
        private String addressDetail;
        @Expose
        @SerializedName("latitude")
        private double latitude;
        @Expose
        @SerializedName("longitude")
        private double longitude;
        @Expose
        @SerializedName("buildingName")
        private String buildingName;

        public Request(String menuId, String address, String addressDetail, double latitude, double longitude, String buildingName){
            this.address = address;
            this.addressDetail = addressDetail;
            this.latitude = latitude;
            this.longitude = longitude;
            this.buildingName = buildingName;
            setData(APIInfo.GRA_CHB_1007.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1007 응답 항목
     * @see #useYn 서비스 가능 여부
     * Y : 서비스 가능, N : 서비스 불가능
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("useYn")
        private String useYn;
    }
}
