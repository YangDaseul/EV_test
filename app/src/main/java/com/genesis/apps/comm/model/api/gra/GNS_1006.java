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
 * @file GRA_GNS_1006
 * @Brief My차고 + 렌트/리스 실운행자 신청
 */
public class GNS_1006 extends BaseData {

    //TODO 아래 전역변수는 1007 SUBCRIBECARINFO와 함께 쓰이므로 옴겨야함
    public static final String CUSTOMER_CORPORATION="1";
    public static final String CUSTOMER_INDIVIDUAL="14";

    public static final String RENT_PERIOD_12="12";
    public static final String RENT_PERIOD_24="24";
    public static final String RENT_PERIOD_36="36";
    public static final String RENT_PERIOD_48="48";
    public static final String RENT_PERIOD_ETC="99";

    public static final String CARD_RECEIVE_AREA_HOME="1";
    public static final String CARD_RECEIVE_AREA_COMPANY="2";
    public static final String CARD_RECEIVE_AREA_ETC="3";

    /**
     * @brief GNS_1006 요청 항목
     * @see #vin 차대번호
     *
     * @see #csmrScnCd 고객구분코드
     * 1 : 개인(법인임직원)
     * 14 : 개인(리스/렌트 이용개인)
     *
     * @see #rentPeriod 대여기간
     * 12, 24, 36, 48   99:기타
     *
     * @see #asnCd 정비망코드
     * 전담블루핸즈를 신청한 정비업체코드
     *
     * @see #crdRcvScnCd 카드수령지구분코드
     * 1 : 자택  2 : 회사  3 : 기타
     *
     * @see #crdRcvZip 카드수령지 우편 번호
     * @see #crdRcvAdr 카드수령지주소
     * @see #crdRcvDtlAdr 상세주소
     * @see #cnttImgYn 계약서이미지유무
     * 이미지 필수
     * @see #empCertiImgYn 재직증명서이미지유무
     * 개인(법인임직원) 인 경우 필수
     *
     *
     * @see #ctrctNo 계약번호
     * 렌트리스 등록 가능한 차량인 경우 필수
     * @see #godsId 상품ID
     * 렌트리스 등록 가능한 차량(G80, G90)인 경우 필수
     * @see #godsNm 상품명
     * 렌트리스 등록 가능한 차량(G80, G90)인 경우 필수
     * @see #godsRcvZip 상품수령지우편번호
     * 선택한 특화상품에 주소입력 필수인 경우는 필수 입력
     * @see #godsRcvAdr 상품수령지주소
     * 선택한 특화상품에 주소입력 필수인 경우는 필수 입력
     * @see #godsRcvDtlAdr 상품수령지상세주소
     * 선택한 특화상품에 주소입력 필수인 경우는 필수 입력
     * @see #godsRcvTelNo 상품수령지전화번호
     * 선택한 특화상품에 주소입력 필수인 경우는 필수 입력
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("csmrScnCd")
        private String csmrScnCd;
        @Expose
        @SerializedName("rentPeriod")
        private String rentPeriod;
        @Expose
        @SerializedName("asnCd")
        private String asnCd;
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
        @Expose
        @SerializedName("cnttImgYn")
        private String cnttImgYn;
        @Expose
        @SerializedName("empCertiImgYn")
        private String empCertiImgYn;


        //2020-12-08 추가
        @Expose
        @SerializedName("ctrctNo")
        private String ctrctNo;
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

        public Request(String menuId, String vin, String csmrScnCd, String rentPeriod, String asnCd, String crdRcvScnCd, String crdRcvZip, String crdRcvAdr, String crdRcvDtlAdr, String cnttImgYn, String empCertiImgYn){
            this.vin = vin;
            this.csmrScnCd = csmrScnCd;
            this.rentPeriod = rentPeriod;
            this.asnCd = asnCd;
            this.crdRcvScnCd = crdRcvScnCd;
            this.crdRcvZip = crdRcvZip;
            this.crdRcvAdr = crdRcvAdr;
            this.crdRcvDtlAdr = crdRcvDtlAdr;
            this.cnttImgYn = cnttImgYn;
            this.empCertiImgYn = empCertiImgYn;
            setData(APIInfo.GRA_GNS_1006.getIfCd(), menuId);
        }

        public Request(String menuId, String vin, String csmrScnCd, String rentPeriod, String asnCd, String crdRcvScnCd, String crdRcvZip, String crdRcvAdr, String crdRcvDtlAdr, String cnttImgYn, String empCertiImgYn
        ,String ctrctNo,String godsId,String godsNm,String godsRcvZip,String godsRcvAdr,String godsRcvDtlAdr,String godsRcvTelNo){
            this.vin = vin;
            this.csmrScnCd = csmrScnCd;
            this.rentPeriod = rentPeriod;
            this.asnCd = asnCd;
            this.crdRcvScnCd = crdRcvScnCd;
            this.crdRcvZip = crdRcvZip;
            this.crdRcvAdr = crdRcvAdr;
            this.crdRcvDtlAdr = crdRcvDtlAdr;
            this.cnttImgYn = cnttImgYn;
            this.empCertiImgYn = empCertiImgYn;
            this.ctrctNo = ctrctNo;
            this.godsId = godsId;
            this.godsNm = godsNm;
            this.godsRcvZip = godsRcvZip;
            this.godsRcvAdr = godsRcvAdr;
            this.godsRcvDtlAdr = godsRcvDtlAdr;
            this.godsRcvTelNo = godsRcvTelNo;
            setData(APIInfo.GRA_GNS_1006.getIfCd(), menuId);
        }

    }

    /**
     * @brief GNS_1006 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
