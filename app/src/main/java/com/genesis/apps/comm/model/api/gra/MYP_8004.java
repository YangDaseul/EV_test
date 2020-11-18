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
 * @file GRA_MYP_8004
 * @Brief MyG+ 버전정보
 */
public class MYP_8004 extends BaseData {
    /**
     * @brief MYP_8004 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("termOSGbCd")
        private String termOSGbCd;


        public Request(String menuId){
            this.termOSGbCd = "A";
            setData(APIInfo.GRA_MYP_8004.getIfCd(), menuId);
        }
    }

    /**
     * @brief MYP_8004 응답 항목
     * @see #ver 버전정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("ver")
        private String ver;
    }
}
