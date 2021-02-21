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
 * @file GRA_RMT_1005
 * @Brief 원격진단 취소
 */
public class RMT_1005 extends BaseData {
    /**
     * @brief RMT_1005 요청 항목
     * @see #tmpAcptCd 가접수번호
     * @see #rcptCd 접수번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("tmpAcptCd")
        private String tmpAcptCd;
        @Expose
        @SerializedName("rcptCd")
        private String rcptCd;

        public Request(String menuId,String tmpAcptCd,String rcptCd) {
            this.tmpAcptCd = tmpAcptCd;
            this.rcptCd = rcptCd;
            setData(APIInfo.GRA_RMT_1005.getIfCd(), menuId);
        }
    }

    /**
     * @brief RMT_1005 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
