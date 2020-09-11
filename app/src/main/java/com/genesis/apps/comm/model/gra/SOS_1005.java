package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.SOSStateVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_1005
 * @Brief Genesis + 신청 상태 확인
 */
public class SOS_1005 extends BaseData {
    /**
     * @brief SOS_1005 요청 항목
     *
     * @see #tmpAcptNo 가접수번호
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("tmpAcptNo")
        private String tmpAcptNo;

        public Request(String menuId, String tmpAcptNo){
            this.tmpAcptNo = tmpAcptNo;
            setData(APIInfo.GRA_SOS_1005.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_1005 응답 항목
     * @see #sosStateVO 긴급출동 신청 상태
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        private SOSStateVO sosStateVO;
    }
}
