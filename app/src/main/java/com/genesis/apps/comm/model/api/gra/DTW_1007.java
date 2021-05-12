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
     * @see #mOid   채널 주문번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("mOid")
        private String mOid;

        public Request(String menuId, String mOid) {
            this.mOid = mOid;
            setData(APIInfo.GRA_DTW_1007.getIfCd(), menuId);
        }
    }

    /**
     * @brief DTW_1007 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
