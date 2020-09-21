package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.RepairHistVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1015
 * @Brief service + 예약 취소
 */
public class REQ_1015 extends BaseData {
    /**
     * @brief REQ_1015 요청 항목
     * @see #rparRsvtSeqNo 정비예약일련번호
     * @see #rsvtCnclCd 예약취소코드
     * 1:예약변경, 2:타업체입고, 3:입고불가 4:대기과다 5:기타
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("rparRsvtSeqNo")
        private String rparRsvtSeqNo;
        @Expose
        @SerializedName("rsvtCnclCd")
        private String rsvtCnclCd;

        public Request(String menuId, String rparRsvtSeqNo, String rsvtCnclCd){
            this.rparRsvtSeqNo = rparRsvtSeqNo;
            this.rsvtCnclCd = rsvtCnclCd;
            setData(APIInfo.GRA_REQ_1015.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1015 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }


}
