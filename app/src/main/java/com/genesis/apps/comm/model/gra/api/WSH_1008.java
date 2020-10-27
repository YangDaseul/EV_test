package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_WSH_1008
 * @Brief service + 소낙스 예약취소
 * @author hjpark
 */
public class WSH_1008 extends BaseData {
    /**
     * @brief WSH_1008의 요청 항목
     * @author hjpark
     * @see #rsvtSeqNo 예약일련번호
     * @see #evlpStarCnt 평가별수
     * @see #useRvw 이용후기
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("rsvtSeqNo")
        private String rsvtSeqNo;
        @Expose
        @SerializedName("evlpStarCnt")
        private String evlpStarCnt;
        @Expose
        @SerializedName("useRvw")
        private String useRvw;


        public Request(String menuId, String rsvtSeqNo, String evlpStarCnt, String useRvw){
            this.rsvtSeqNo = rsvtSeqNo;
            this.evlpStarCnt = evlpStarCnt;
            this.useRvw = useRvw;
            setData(APIInfo.GRA_WSH_1008.getIfCd(), menuId);
        }
    }

    /**
     * @brief WSH_1008의 응답 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
