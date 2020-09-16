package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.RentStatusVO;
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
    class Request extends BaseRequest{

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

        public Request(String menuId, String vin, String seqNo, String crdRcvScnCd, String crdRcvZip, String crdRcvAdr, String crdRcvDtlAdr){
            this.vin = vin;
            this.seqNo = seqNo;
            this.crdRcvScnCd = crdRcvScnCd;
            this.crdRcvZip = crdRcvZip;
            this.crdRcvAdr = crdRcvAdr;
            this.crdRcvDtlAdr = crdRcvDtlAdr;
            setData(APIInfo.GRA_GNS_1013.getIfCd(),menuId);
        }
    }

    /**
     * @brief GNS_1013 응답 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }
}
