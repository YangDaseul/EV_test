package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1016 extends BaseData {
    /**
     * @brief CHB_1016 요청 항목
     * @see #cardId 결제수단ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("cardId")
        private String cardId;

        public Request(String menuId, String cardId) {
            this.cardId = cardId;
            setData(APIInfo.GRA_CHB_1016.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1016 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
