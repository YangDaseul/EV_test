package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.RentStatusVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1012
 * @Brief My차고 + 렌트/리스 실운행자 상세
 */
public class GNS_1012 extends BaseData {

    /**
     * @brief GNS_1012 요청 항목
     * @see #vin 차대번호
     * @see #seqNo 일련번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("seqNo")
        private String seqNo;

        public Request(String menuId, String vin, String seqNo){
            this.vin = vin;
            this.seqNo = seqNo;
            setData(APIInfo.GRA_GNS_1012.getIfCd(),menuId);
        }
    }

    /**
     * @brief GNS_1012 응답 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        private RentStatusVO rentStatusVO;
        //TODO 별도처리필요
        // new Gson().fromJson(result, GNS_1012.Response.class)   -> MSG 및 CODE 참조
        // GNS_1012.Response.setRentStatusVO(new Gson().fromJson(result, RentStatusVO.class))
        //
//        @Expose
//        @SerializedName("seqNo")
//        private String seqNo;
//        @Expose
//        @SerializedName("vin")
//        private String vin;
//        @Expose
//        @SerializedName("csmrScnCd")
//        private String csmrScnCd;
//        @Expose
//        @SerializedName("rentPeriod")
//        private String rentPeriod;
//
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
//
//        @Expose
//        @SerializedName("crdRcvScnCd")
//        private String crdRcvScnCd;
//        @Expose
//        @SerializedName("crdRcvZip")
//        private String crdRcvZip;
//        @Expose
//        @SerializedName("crdRcvAdr")
//        private String crdRcvAdr;
//        @Expose
//        @SerializedName("crdRcvDtlAdr")
//        private String crdRcvDtlAdr;
//        @Expose
//        @SerializedName("attachFilName")
//        private String attachFilName;
//        @Expose
//        @SerializedName("cnttUrl")
//        private String cnttUrl;
//        @Expose
//        @SerializedName("subspDtm")
//        private String subspDtm;
//        @Expose
//        @SerializedName("aprvStusCd")
//        private String aprvStusCd;
//        @Expose
//        @SerializedName("aprvDtm")
//        private String aprvDtm;
//        @Expose
//        @SerializedName("rtnRsnMsg")
//        private String rtnRsnMsg;
    }
}
