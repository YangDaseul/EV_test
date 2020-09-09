package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("listSeqNo")
        private String listSeqNo;
        @Expose
        @SerializedName("starCnt")
        private String starCnt;

        public Request(String listSeqNo, String starCnt){
            this.listSeqNo = listSeqNo;
            this.starCnt = starCnt;
            setData(APIInfo.GRA_CTT_1002.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief CTT_1002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }
}
