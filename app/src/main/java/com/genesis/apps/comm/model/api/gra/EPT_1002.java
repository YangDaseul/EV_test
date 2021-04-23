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
     * @see #spid 환경부-기관ID
     * @see #csid 환경부-충전소ID
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
        @SerializedName("spid")
        private String spid;
        @Expose
        @SerializedName("csid")
        private String csid;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("lot")
        private String lot;

        public Request(String menuId, String vin, String spid, String csid, String lat, String lot) {
            this.vin = vin;
            this.spid = spid;
            this.csid = csid;
            this.lat = lat;
            this.lot = lot;
            setData(APIInfo.GRA_EPT_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief EPT_1002 응답 항목
     * @see #chgInfo 충전소정보
     * @see #chgrList 충전기 정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Response extends BaseResponse {
        @Expose
        @SerializedName("chgInfo")
        private ChargeEptInfoVO chgInfo;

        @Expose
        @SerializedName("chgrList")
        private List<ChargerEptVO> chgrList;
    }
} // end of class EPT_1002
