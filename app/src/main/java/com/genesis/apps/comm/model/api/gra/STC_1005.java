package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ReserveHisVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @file GRA_STC_1005
 * @Brif S-트래픽 충전기 예약신청내역
 */
public class STC_1005 extends BaseData {
    /**
     * @brief STC_1005 요청 항목
     * @see #vin 차대번호
     * @see #startDt 조회시작일자
     * @see #endDt 조회종료일자
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("startDt")
        private String startDt;
        @Expose
        @SerializedName("endDt")
        private String endDt;

        public Request(String menuId, String vin, String startDt, String endDt) {
            this.vin = vin;
            this.startDt = startDt;
            this.endDt = endDt;
            setData(APIInfo.GRA_STC_1005.getIfCd(), menuId);
        }
    }

    /**
     * @brief STC_1005 응답 항목
     * @see #reservList 예약목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("reservList")
        private List<ReserveHisVO> reservList;
    }
} // end of class STC_1005
