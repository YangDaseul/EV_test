package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ChargeEptInfoVO;
import com.genesis.apps.comm.model.vo.ChargerEptVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @file GRA_EPT_1002
 * @Brif E-PIT 충전소 상세 조회
 */
public class EPT_1002 extends BaseData {
    /**
     * @brief EPT_1002 요청 항목
     * @see #vin 차대번호
     * @see #bid 충전사업자ID
     * @see #sid 충전소ID
     * @see #eid 환경부충전소ID
     * @see #lat 고객위치-위도
     * @see #lot 고객위치-경도
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("bid")
        private String bid;
        @Expose
        @SerializedName("sid")
        private String sid;
        @Expose
        @SerializedName("eid")
        private String eid;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("lot")
        private String lot;

        public Request(String menuId, String vin, String bid, String sid, String eid, String lat, String lot) {
            this.vin = vin;
            this.bid = bid;
            this.sid = sid;
            this.eid = eid;
            this.lat = lat;
            this.lot = lot;
            setData(APIInfo.GRA_EPT_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief EPT_1002 응답 항목
     * @see #chgList 충전소정보
     * @see #chgrList 충전기 정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Response extends BaseResponse {
        @Expose
        @SerializedName("chgList")
        private ChargeEptInfoVO chgList;

        @Expose
        @SerializedName("chgrList")
        private List<ChargerEptVO> chgrList;
    }
} // end of class EPT_1002
