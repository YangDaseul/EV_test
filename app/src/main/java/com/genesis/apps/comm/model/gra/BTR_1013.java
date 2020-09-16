package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_BTR_1013
 * @Brief Genesis + 버틀러 상담 답변읽기
 */
public class BTR_1013 extends BaseData {

    /**
     * @brief BTR_1013 요청 항목
     * @see #cnsltSeqNo 상담일련번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{

        @Expose
        @SerializedName("cnsltSeqNo")
        private String cnsltSeqNo;

        public Request(String menuId,String cnsltSeqNo){
            this.cnsltSeqNo = cnsltSeqNo;
            setData(APIInfo.GRA_BTR_1013.getIfCd(),menuId);
        }
    }

    /**
     * @brief BTR_1013 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }
}
