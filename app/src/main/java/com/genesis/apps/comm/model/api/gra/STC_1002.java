package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ChargeSttInfo;
import com.genesis.apps.comm.model.vo.ChargeVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @file GRA_STC_1002
 * @Brif S-트래픽 충전소 상세조회
 */
public class STC_1002 extends BaseData {

    /**
     * @brief STC_1002 요청 항목
     * @see #vin 차대번호
     * @see #lat 고객위치-위도
     * @see #lot 고객위치-경도
     * @see #sid 충전소ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("lot")
        private String lot;
        @Expose
        @SerializedName("sid")
        private String sid;

        public Request(String menuId, String vin, String lat, String lot, String sid) {
            this.vin = vin;
            this.lat = lat;
            this.lot = lot;
            this.sid = sid;
            setData(APIInfo.GRA_STC_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief STC_1002 응답 항목
     * @see #chgSttnInfo 충전소정보
     * @see #chgrList 충전기목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("chgSttnInfo")
        private ChargeSttInfo chgSttnInfo;
        @Expose
        @SerializedName("chgrList")
        private List<ChargeVO> chgrList;
    }
} // end of class STC_1002
