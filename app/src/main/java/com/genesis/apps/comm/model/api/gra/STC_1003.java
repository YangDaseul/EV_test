package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ReserveDtVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @file GRA_STC_1003
 * @Brif S-트래픽 충전기 예약가능시간
 */
public class STC_1003 extends BaseData {

    /**
     * @brief STC_1003 요청 항목
     * @see #vin 차대번호
     * @see #sid 충전소ID
     * @see #cid 충전기ID
     * @see #reservDt 예약 일자
     * 당일예약만 가능 (YYYYMMDD) - NULL인 경우 당일로 설정함
     * 20210310 : 2021년 3월 10일
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("sid")
        private String sid;
        @Expose
        @SerializedName("cid")
        private String cid;
        @Expose
        @SerializedName("reservDt")
        private String reservDt;

        public Request(String menuId, String vin, String sid, String cid, String reservDt) {
            this.vin = vin;
            this.sid = sid;
            this.cid = cid;
            this.reservDt = reservDt;
            setData(APIInfo.GRA_STC_1003.getIfCd(), menuId);
        }
    }

    /**
     * @brief STC_1003 응답 항목
     * @see #reservDtList 예약시간목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("reservDtList")
        private List<ReserveDtVO> reservDtList;
    }
} // end of class STC_1003
