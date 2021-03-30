package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_3006
 * @Brief 긴급충전출동 현황(위치)
 */
public class SOS_3006 extends BaseData {
    /**
     * @brief SOS_3006 요청 항목
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
            setData(APIInfo.GRA_SOS_3006.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_3006 응답 항목
     * @see #sosDriverVO 기사/위치정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("tmpAcptNo")
        private String tmpAcptNo;
        @Expose
        @SerializedName("tmpAcptDtm")
        private String tmpAcptDtm;
        @Expose
        @SerializedName("areaClsCd")
        private String areaClsCd;
        @Expose
        @SerializedName("addr")
        private String addr;
        @Expose
        @SerializedName("carRegNo")
        private String carRegNo;

        private SOSDriverVO sosDriverVO;
    }
}
