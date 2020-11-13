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
 * @file GRA_SOS_1004
 * @Brief Genesis + 신청 취소
 */
public class SOS_1004 extends BaseData {
    /**
     * @brief SOS_1004 요청 항목
     *
     * @see #tmpAcptNo 가접수번호
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("tmpAcptNo")
        private String tmpAcptNo;

        public Request(String menuId, String tmpAcptNo){
            this.tmpAcptNo = tmpAcptNo;
            setData(APIInfo.GRA_SOS_1004.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_1004 응답 항목
     * @see #succYn 성공여부
     * Y:취소 성공 N:취소실패
     * @see #failMsg 실패메시지
     * 실패시의 메시지
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("succYn")
        private String succYn;
        @Expose
        @SerializedName("failMsg")
        private String failMsg;
    }
}
