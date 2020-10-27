package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_WSH_1007
 * @Brief service + 소낙스 예약취소
 * @author hjpark
 */
public class WSH_1007 extends BaseData {
    /**
     * @brief WSH_1007의 요청 항목
     * @author hjpark
     * @see #rsvtSeqNo 예약일련번호      1~16자리 숫자만
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("rsvtSeqNo")
        private String rsvtSeqNo;
        public Request(String menuId, String rsvtSeqNo){
            this.rsvtSeqNo = rsvtSeqNo;
            setData(APIInfo.GRA_WSH_1007.getIfCd(), menuId);
        }
    }

    /**
     * @brief WSH_1007의 응답 항목
     * @author hjpark
     * @see #rsvtSeqNo      예약일련번호
     * @see #rsvtStusCd	    예약상태코드      1000:  예약신청    2000 : 이용완료    9000 : 예약 취소
     * @see #rsvtDtm	    예약일시          YYYYMMDDHH24MISS
     * @see #paymtWayCd	    결제수단          1000: 현금결제
     * @see #paymtCost	    결제금액
     * @see #brnhNm	        지점명            사용지점코드가 있는 경우는 사용지점명으로 설정
     * @see #telNo	        대표연락처        '-' 없음
     * @see #godsSeqNo	    상품일련번호
     * @see #godsNm	        상품명
     * @see #godsImgUri	    상품이미지URI
     * @see #evalQst	    평가질의서
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rsvtSeqNo")
        private String rsvtSeqNo;
        @Expose
        @SerializedName("rsvtStusCd")
        private String rsvtStusCd;
        @Expose
        @SerializedName("rsvtDtm")
        private String rsvtDtm;
        @Expose
        @SerializedName("paymtWayCd")
        private String paymtWayCd;
        @Expose
        @SerializedName("paymtCost")
        private String paymtCost;
        @Expose
        @SerializedName("brnhNm")
        private String brnhNm;
        @Expose
        @SerializedName("telNo")
        private String telNo;
        @Expose
        @SerializedName("godsSeqNo")
        private String godsSeqNo;
        @Expose
        @SerializedName("godsNm")
        private String godsNm;
        @Expose
        @SerializedName("godsImgUri")
        private String godsImgUri;
        @Expose
        @SerializedName("evalQst")
        private String evalQst;
    }
}
