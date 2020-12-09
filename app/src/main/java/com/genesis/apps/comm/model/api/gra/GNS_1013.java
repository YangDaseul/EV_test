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
 * @file GRA_GNS_1013
 * @Brief My차고 + 렌트/리스 수정하기
 */
public class GNS_1013 extends BaseData {

    /**
     * @brief GNS_1013 요청 항목
     * @see #vin 차대번호
     * @see #seqNo 일련번호
     *
     * @see #crdRcvScnCd 카드수령지구분코드
     * 1 : 자택  2 : 회사  3 : 기타
     * @see #crdRcvZip 카드수령지우편번호
     * @see #crdRcvAdr 카드수령지주소
     * @see #crdRcvDtlAdr 상세주소
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("seqNo")
        private String seqNo;
        @Expose
        @SerializedName("crdRcvScnCd")
        private String crdRcvScnCd;
        @Expose
        @SerializedName("crdRcvZip")
        private String crdRcvZip;
        @Expose
        @SerializedName("crdRcvAdr")
        private String crdRcvAdr;
        @Expose
        @SerializedName("crdRcvDtlAdr")
        private String crdRcvDtlAdr;

        //2020-12-08추가
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;
        @Expose
        @SerializedName("godsId")
        private String godsId;
        @Expose
        @SerializedName("godsNm")
        private String godsNm;
        @Expose
        @SerializedName("godsRcvZip")
        private String godsRcvZip;
        @Expose
        @SerializedName("godsRcvAdr")
        private String godsRcvAdr;
        @Expose
        @SerializedName("godsRcvDtlAdr")
        private String godsRcvDtlAdr;
        @Expose
        @SerializedName("godsRcvTelNo")
        private String godsRcvTelNo;

        public Request(String menuId, String vin, String seqNo, String crdRcvScnCd, String crdRcvZip, String crdRcvAdr, String crdRcvDtlAdr
                , String mdlNm, String godsId, String godsNm, String godsRcvZip, String godsRcvAdr, String godsRcvDtlAdr, String godsRcvTelNo) {
            this.vin = vin;
            this.seqNo = seqNo;
            this.crdRcvScnCd = crdRcvScnCd;
            this.crdRcvZip = crdRcvZip;
            this.crdRcvAdr = crdRcvAdr;
            this.crdRcvDtlAdr = crdRcvDtlAdr;

            this.mdlNm = mdlNm;
            this.godsId = godsId;
            this.godsNm = godsNm;
            this.godsRcvZip = godsRcvZip;
            this.godsRcvAdr = godsRcvAdr;
            this.godsRcvDtlAdr = godsRcvDtlAdr;
            this.godsRcvTelNo = godsRcvTelNo;

            setData(APIInfo.GRA_GNS_1013.getIfCd(),menuId);
        }
    }

    /**
     * @brief GNS_1013 응답 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
