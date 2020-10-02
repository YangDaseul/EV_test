package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.CouponVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1011
 * @Brief My차고 + 모빌리티케어 쿠폰
 */
public class GNS_1011 extends BaseData {

    /**
     * @brief GNS_1011 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_GNS_1011.getIfCd(), menuId);
        }
    }

    /**
     * @brief GNS_1011 응답 항목
     * @see #rgstPsblYn 등록가능여부
     * 차량렌트리스 정보 등록된 차량인지 확인
     * Y: 등록 N:미등록(렌트리스 차량이 아님)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rgstPsblYn")
        private String rgstPsblYn;
//        //버틀러정보
//        @Expose
//        @SerializedName("asnCd")
//        private String asnCd;
//        @Expose
//        @SerializedName("asnNm")
//        private String asnNm;
//        @Expose
//        @SerializedName("repTn")
//        private String repTn;
//        @Expose
//        @SerializedName("pbzAdr")
//        private String pbzAdr;
//        @Expose
//        @SerializedName("mapXcooNm")
//        private String mapXcooNm;
//        @Expose
//        @SerializedName("mapYcooNm")
//        private String mapYcooNm;
//        @Expose
//        @SerializedName("btlrNm")
//        private String btlrNm;
//        @Expose
//        @SerializedName("celphNo")
//        private String celphNo;
    }
}
