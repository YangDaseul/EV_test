package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RemoteCheckVO;
import com.genesis.apps.comm.model.vo.RemoteHistoryVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_RMT_1004
 * @Brief 원격진단 레포트 조회
 */
public class RMT_1004 extends BaseData {
    /**
     * @brief RMT_1004 요청 항목
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
            setData(APIInfo.GRA_RMT_1004.getIfCd(), menuId);
        }
    }

    /**
     * @brief RMT_1004 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("chckCmnt")
        private String chckCmnt;
        @Expose
        @SerializedName("chckItemList")
        private List<RemoteCheckVO> chckItemList;
    }
}
