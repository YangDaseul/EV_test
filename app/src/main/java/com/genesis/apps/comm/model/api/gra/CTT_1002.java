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
 * @file GRA_CTT_1002
 * @Brief contents + 컨텐츠평가
 */
public class CTT_1002 extends BaseData {
    /**
     * @author hjpark
     * @brief CTT_1002 요청 항목
     * @see #listSeqNo 목록일련번호
     * @see #starCnt 별수
     * @see #mdlNm 차량모델명
     * 로그인 후의 주차량
     * @see #vin 차대번호
     * 로그인 후의 주차량
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("listSeqNo")
        private String listSeqNo;
        @Expose
        @SerializedName("starCnt")
        private String starCnt;
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;
        @Expose
        @SerializedName("vin")
        private String vin;


        public Request(String menuId, String listSeqNo, String starCnt, String mdlNm, String vin){
            this.listSeqNo = listSeqNo;
            this.starCnt = starCnt;
            this.mdlNm = mdlNm;
            this.vin = vin;
            setData(APIInfo.GRA_CTT_1002.getIfCd(),menuId);
        }
    }

    /**
     * @author hjpark
     * @brief CTT_1002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
