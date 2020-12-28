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
 * @file GRA_STO_1003
 * @Brief Genesis + 계약서 조회
 */
public class STO_1003 extends BaseData {
    /**
     * @brief STO_1003 요청 항목
     * @see #ctrctNo 계약번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("ctrctNo")
        private String ctrctNo;

        public Request(String menuId, String ctrctNo){
            this.ctrctNo = ctrctNo;
            setData(APIInfo.GRA_STO_1003.getIfCd(), menuId);
        }
    }

    /**
     * @brief STO_1003 응답 항목
     * @see #saleCnttNo     판매계약번호	구매계약번호 예 : B0119JJ000009
     * @see #mnfctNm	    제조사	디폴트 :  ㈜현대자동차
     * @see #cnttYmd	    계약일	YYYYMMDD
     * @see #carnNm	        자량명	ex: 자가용 7인승 디젤 3.0 22인치 기본 디자인 4WD 오토 런칭 파퓰러 패키지
     * @see #xrclCtyNm	    외장컬러명	ex: 카본 메탈
     * @see #ieclCtyNm	    내장컬러명	ex: 브라운/베이지 투톤(베이지시트)
     * @see #saleMdlOptNm	판매옵션명	ex: 헤드업 디스플레이,하이테크 패키지,드라이빙 어시스턴트 패키지 2,파노라마썬루프^^드라이빙 어시스턴트 패키지 1
     * @see #dlgYmd	        인도일자	YYYYMMDD
     * @see #cnttStCd1	    계출(생산)상태	1000: 대기 2000: 생산중  3000: 생산완료  9000: 취소
     * @see #cnttStCd2	    계출(출고)상태	1000: 대기 4000: 출고중  5000: 출고완료  9000: 취소
     * @see #cnttStCd3	    계출(인도)상태	1000: 대기 6000: 인도중  7000: 인도완료  9000: 취소
     * @see #carCtyAmt	    차량상품금액	단위: 원
     * @see #optCpndCtyAmt	옵션조합상품금액	단위: 원
     * @see #xrclCtyAmt	    외장컬러상품금액	단위: 원
     * @see #ieclCtyAmt	    내장컬러상품금액	단위: 원
     * @see #rscc2Amt	    특수사양조합상품금액	단위: 원
     * @see #csptCpndCtyAmt	파츠조합상품금액	단위: 원
     * @see #carTtlAmt	    차량합계금액	단위: 원
     * @see #dcTtlAmt	    할인합계금액	단위: 원
     * @see #saleAmt	    판매가격	단위: 원
     * @see #mfsAmt	        보전금	단위: 원
     * @see #cnsgCtyAmt	    탁송료	단위: 원
     * @see #ttlSaleAmt	    총판매금	단위: 원
     * @see #incidExp	    부대비용	단위: 원
     * @see #fncIntAmt	    금융이자	단위: 원
     * @see #ttlBuyAmt	    총구입비용	단위: 원
     * @see #eeNm	        사원명	카마스터 성명
     * @see #eeHpTn	        사원전화번호	카마스터 전화번호(010-1234-1234)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("saleCnttNo")
        private String saleCnttNo;
        @Expose
        @SerializedName("mnfctNm")
        private String mnfctNm;
        @Expose
        @SerializedName("cnttYmd")
        private String cnttYmd;
        @Expose
        @SerializedName("carnNm")
        private String carnNm;
        @Expose
        @SerializedName("xrclCtyNm")
        private String xrclCtyNm;
        @Expose
        @SerializedName("ieclCtyNm")
        private String ieclCtyNm;
        @Expose
        @SerializedName("saleMdlOptNm")
        private String saleMdlOptNm;
        @Expose
        @SerializedName("dlgYmd")
        private String dlgYmd;
        @Expose
        @SerializedName("cnttStCd1")
        private String cnttStCd1;
        @Expose
        @SerializedName("cnttStCd2")
        private String cnttStCd2;
        @Expose
        @SerializedName("cnttStCd3")
        private String cnttStCd3;
        @Expose
        @SerializedName("carCtyAmt")
        private String carCtyAmt;
        @Expose
        @SerializedName("optCpndCtyAmt")
        private String optCpndCtyAmt;

        @Expose
        @SerializedName("xrclCtyAmt")
        private String xrclCtyAmt;
        @Expose
        @SerializedName("ieclCtyAmt")
        private String ieclCtyAmt;
        @Expose
        @SerializedName("rscc2Amt")
        private String rscc2Amt;
        @Expose
        @SerializedName("csptCpndCtyAmt")
        private String csptCpndCtyAmt;
        @Expose
        @SerializedName("carTtlAmt")
        private String carTtlAmt;

        @Expose
        @SerializedName("dcTtlAmt")
        private String dcTtlAmt;
        @Expose
        @SerializedName("saleAmt")
        private String saleAmt;
        @Expose
        @SerializedName("mfsAmt")
        private String mfsAmt;
        @Expose
        @SerializedName("cnsgCtyAmt")
        private String cnsgCtyAmt;
        @Expose
        @SerializedName("ttlSaleAmt")
        private String ttlSaleAmt;
        @Expose
        @SerializedName("incidExp")
        private String incidExp;
        @Expose
        @SerializedName("fncIntAmt")
        private String fncIntAmt;
        @Expose
        @SerializedName("ttlBuyAmt")
        private String ttlBuyAmt;
        @Expose
        @SerializedName("eeNm")
        private String eeNm;
        @Expose
        @SerializedName("eeHpTn")
        private String eeHpTn;
    }
}
