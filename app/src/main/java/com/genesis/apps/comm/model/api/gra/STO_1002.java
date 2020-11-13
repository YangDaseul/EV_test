package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_STO_1002
 * @Brief Genesis + BTO 웹뷰
 */
public class STO_1002 extends BaseData {
    /**
     * @brief STO_1002 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_STO_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief STO_1002 응답 항목
     * @see #htmlFilUri HTML 파일 Uri
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("htmlFilUri")
        private String htmlFilUri;
    }
}
