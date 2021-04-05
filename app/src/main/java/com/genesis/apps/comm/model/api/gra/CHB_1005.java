package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1005 extends BaseData {
    /**
     * @brief CHB_1005 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){ setData(APIInfo.GRA_CHB_1005.getIfCd(), menuId); }
    }

    /**
     * @brief CHB_1005 응답 항목
     * @see #productThumbnailUrl 상품 썸네일 URL
     * @see #productInfoUrl 상품 안내 URL
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("productThumbnailUrl")
        private String productThumbnailUrl;
        @Expose
        @SerializedName("productInfoUrl")
        private String productInfoUrl;
    }
}
