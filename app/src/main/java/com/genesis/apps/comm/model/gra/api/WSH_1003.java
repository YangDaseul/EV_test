package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_WSH_1003
 * @Brief service + 소낙스 세차예약
 * @author hjpark
 */
public class WSH_1003 extends BaseData {
    /**
     * @brief WSH_1003의 요청 항목
     * @author hjpark
     * @see #godsSeqNo 상품일련번호
     * @see #cmpyCd 업체코드            소낙스  : SONAX
     * @see #brnhCd 지점코드
     * @see #vin 차대번호
     * @see #carRgstNo 차량번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("godsSeqNo")
        private String godsSeqNo;
        @Expose
        @SerializedName("cmpyCd")
        private String cmpyCd;
        @Expose
        @SerializedName("brnhCd")
        private String brnhCd;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carRgstNo")
        private String carRgstNo;

        public Request(String menuId, String godsSeqNo, String cmpyCd, String brnhCd, String vin, String carRgstNo){
            this.godsSeqNo = godsSeqNo;
            this.cmpyCd = cmpyCd;
            this.brnhCd = brnhCd;
            this.vin = vin;
            this.carRgstNo = carRgstNo;
            setData(APIInfo.GRA_WSH_1003.getIfCd(), menuId);
        }
    }

    /**
     * @brief WSH_1003의 응답 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
