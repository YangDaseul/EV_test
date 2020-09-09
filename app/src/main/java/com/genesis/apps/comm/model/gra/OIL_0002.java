package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_OIL_0002
 * @Brief MyG+ 정유사 약관요청(All)
 */
public class OIL_0002 extends BaseData {
    /**
     * @author hjpark
     * @brief OIL_0002 요청 항목
     * @see #oilRfnCd 정유사코드
     * HDOL : hyundai oilbank
     * GSCT  : GS 칼텍스
     * SOIL : S-OIL
     * SKNO : SK 이노베이션
     *
     * @see #terms 약관정보
     * @see #termCode 약관구분코드
     * @see #agreeYn 동의
     * Y : 동의, 그외 : N
     * @see #termName 동의약관명
     * @see #agreeDate 동의일자
     * YYYYMMDDHH24MMSS
     * @see #agreeSms 동의수단(문자)
     * Y : 동의, 그외 : N
     * @see #agreeEmail 동의수단(이메일)
     * Y : 동의, 그외 : N
     * @see #agreePost 동의수단(우편)
     * Y : 동의, 그외 : N
     * @see #agreeTel 동의수단(전화)
     * Y : 동의, 그외 : N
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("oilRfnCd")
        private String oilRfnCd;
        @Expose
        @SerializedName("terms")
        private String terms;
        @Expose
        @SerializedName("termCode")
        private String termCode;
        @Expose
        @SerializedName("agreeYn")
        private String agreeYn;
        @Expose
        @SerializedName("termName")
        private String termName;
        @Expose
        @SerializedName("agreeDate")
        private String agreeDate;
        @Expose
        @SerializedName("agreeSms")
        private String agreeSms;
        @Expose
        @SerializedName("agreeEmail")
        private String agreeEmail;
        @Expose
        @SerializedName("agreePost")
        private String agreePost;
        @Expose
        @SerializedName("agreeTel")
        private String agreeTel;


        public Request(String oilRfnCd){
            this.oilRfnCd = oilRfnCd;
            setData(APIInfo.GRA_OIL_0002.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief OIL_0002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }
}
