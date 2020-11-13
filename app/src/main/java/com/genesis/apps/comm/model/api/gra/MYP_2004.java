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
 * @file GRA_MYP_2004
 * @Brief MyG+ 멤버쉽 카드 안내 요청
 */
public class MYP_2004 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_2004 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        public Request(String menuId){
            setData(APIInfo.GRA_MYP_2004.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_2004 응답 항목
     * @see #ttl 제목
     * @see #cont 내용
     * html 형식에 문자열
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
