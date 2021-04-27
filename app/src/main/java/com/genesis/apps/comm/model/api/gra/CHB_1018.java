package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1018 extends BaseData {
    /**
     * @brief CHB_1018 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        public Request(String menuId) {
            setData(APIInfo.GRA_CHB_1018.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1018 응답 항목
     * @see #ttl    제목
     * @see #cont   내용
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("ttl")
        private String ttl;
        @Expose
        @SerializedName("cont")
        private String cont;
    }
}
