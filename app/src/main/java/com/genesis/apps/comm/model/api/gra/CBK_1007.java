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
 * @file GRA_CBK_1007
 * @Brief insight + 차계부 삭제
 */
public class CBK_1007 extends BaseData {
    /**
     * @brief CBK_1007 요청 항목
     * @see #expnSeqNo 차계부 일련번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("expnSeqNo")
        private String expnSeqNo;

        public Request(String menuId, String expnSeqNo){
            this.expnSeqNo = expnSeqNo;
            setData(APIInfo.GRA_CBK_1007.getIfCd(), menuId);
        }
    }

    /**
     * @brief CBK_1007 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
