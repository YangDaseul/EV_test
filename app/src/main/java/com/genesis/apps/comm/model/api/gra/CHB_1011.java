package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1011 extends BaseData {
    /**
     * @brief CHB_1011 요청 항목
     * @see #userAgent  사용자 정보(브라우저 정보)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("userAgent")
        private String userAgent;

        public Request(String menuId, String userAgent) {
            this.userAgent = userAgent;
            setData(APIInfo.GRA_CHB_1011.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1011 응답 항목
     * @see #formUrl    팝업 Form URL
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("formUrl")
        private String formUrl;
    }
}
