package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_OIL_0004
 * @Brief MyG+ 정유사 약관요청(All)
 */
public class OIL_0004 extends BaseData {
    /**
     * @author hjpark
     * @brief OIL_0004 요청 항목
     * @see #oilRfnCd 정유사코드
     * HDOL : hyundai oilbank
     * GSCT  : GS 칼텍스
     * SOIL : S-OIL
     * SKNO : SK 이노베이션
     * @see #termVer 약관버전
     * ex) 01.00,  10.00, 23.10 34.23
     * @see #termCd 약관코드
     * 정유사별로 약관명 및 내용이 다르고
     * 스토리보드와 칼텍스의 약관명이 일치하지 않아 확인 후 정의될 예정
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("oilRfnCd")
        private String oilRfnCd;
        @Expose
        @SerializedName("termVer")
        private String termVer;
        @Expose
        @SerializedName("termCd")
        private String termCd;

        public Request(String menuId, String oilRfnCd, String termVer, String termCd){
            this.termVer = termVer;
            this.termCd = termCd;
            this.oilRfnCd = oilRfnCd;
            setData(APIInfo.GRA_OIL_0004.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief OIL_0004 응답 항목
     * @see #termVO 약관정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Response extends BaseResponse {
        private TermVO termVO;
    }
}
