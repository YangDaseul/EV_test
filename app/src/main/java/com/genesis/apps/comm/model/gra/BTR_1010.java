package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_BTR_1010
 * @Brief Genesis + 버틀러 안내조회
 */
public class BTR_1010 extends BaseData {

    /**
     * @brief BTR_1010 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        public Request(String menuId){
            setData(APIInfo.GRA_BTR_1010.getIfCd(),menuId);
        }
    }

    /**
     * @brief BTR_1010 응답 항목
     * @see #ttl 제목
     * @see #cont 내용 (html 형식에 문자열)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("ttl")
        private String ttl;
        @Expose
        @SerializedName("cont")
        private String cont;
    }
}
