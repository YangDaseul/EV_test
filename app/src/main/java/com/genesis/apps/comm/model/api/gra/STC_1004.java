package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ReserveInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @file GRA_STC_1004
 * @Brif S-트래픽 충전기 예약하기
 */
public class STC_1004 extends BaseData {
    /**
     * @brief STC_1004 요청 항목
     * @see #vin 차대번호
     * @see #sid 충전소 ID
     * @see #cid 충전기 ID
     * @see #reservDt 예약 일자
     * 당일 YYYYMMDD
     * @see #reservTm 예약 시간
     * HH24MI
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
        @Expose
        @SerializedName("reservTm")
        private String reservTm;

        public Request(String menuId, String vin, String sid, String cid, String reservDt, String reservTm) {
            this.vin = vin;
            this.sid = sid;
            this.cid = cid;
            this.reservDt = reservDt;
            this.reservTm = reservTm;
            setData(APIInfo.GRA_STC_1004.getIfCd(), menuId);
        }
    }

    /**
     * @brief STC_1004 응답 항목
     * @see #reservInfo 예약정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("reservInfo")
        private ReserveInfo reservInfo;
    }
} // end of class STC_1004
