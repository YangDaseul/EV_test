package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1008
 * @Brief service + 정비예약 - 홈투홈 예약가능일
 */
public class REQ_1008 extends BaseData {
    /**
     * @brief REQ_1008 요청 항목
     * @see #srtDt 시작일자
     * YYYYMMDD
     * @see #endDt 종료일자
     * YYYYMMDD
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("srtDt")
        private String srtDt;
        @Expose
        @SerializedName("endDt")
        private String endDt;
        public Request(String menuId, String srtDt, String endDt){
            this.srtDt = srtDt;
            this.endDt = endDt;
            setData(APIInfo.GRA_REQ_1008.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1008 응답 항목
     * @see #rsvtDtList 예약일리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rsvtDtList")
        private List<RepairReserveDateVO> rsvtDtList;
    }
}
