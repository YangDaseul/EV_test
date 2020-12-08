package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_RMT_1002
 * @Brief 원격진단신청
 */
public class RMT_1002 extends BaseData {
    /**
     * @brief RMT_1002 요청 항목
     * @see #vin 차대번호
     * @see #carRgstNo 차량번호
     * @see #celphNo 휴대전화번호
     * 고객이 입력한 휴대전화번호
     * @see #fltCd 차량문제코드
     * @see #wrnLghtCd 경고등코드
     * @see #rsrvMiss 예약/상담시간
     * @see #gCustX 고객 위치정보조표(경도)
     * @see #gCustY 고객 위치정보조표(위도)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carRgstNo")
        private String carRgstNo;
        @Expose
        @SerializedName("celphNo")
        private String celphNo;
        @Expose
        @SerializedName("fltCd")
        private String fltCd;
        @Expose
        @SerializedName("wrnLghtCd")
        private String wrnLghtCd;
        @Expose
        @SerializedName("rsrvMiss")
        private String rsrvMiss;
        @Expose
        @SerializedName("gCustX")
        private String gCustX;
        @Expose
        @SerializedName("gCustY")
        private String gCustY;

        public Request(String menuId, String vin, String carRgstNo, String celphNo, String fltCd, String wrnLghtCd, String rsrvMiss, String gCustX, String gCustY){
            this.vin = vin;
            this.carRgstNo = carRgstNo;
            this.celphNo = celphNo;
            this.fltCd = fltCd;
            this.wrnLghtCd = wrnLghtCd;
            this.rsrvMiss = rsrvMiss;
            this.gCustX = gCustX;
            this.gCustY = gCustY;
            setData(APIInfo.GRA_RMT_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief RMT_1002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {

    }
}
