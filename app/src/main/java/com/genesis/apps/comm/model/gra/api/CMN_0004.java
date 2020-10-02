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
 * @file GRA_CMN_0004
 * @Brief 대표앱 서비스 가입시 노출되는 약관 목록 조회
 * @author hjpark
 */
public class CMN_0004 extends BaseData {
    /**
     * @brief CMN_0004의 요청 항목
     * @author hjpark
     * @see #termVer 약관버전
     * ex) 01.00,  10.00, 23.10 34.23
     * @see #termCd 약관구분코드
     * 앱 이용약관 : 1000
     * 개인정보처리방침 : 2000
     * 개인정보 수집/이용 : 3000
     * 광고성 정보 수신동의 : 4000
     * 제네시스 멤버십 가입 약관  : 5000
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("termVer")
        private String termVer;
        @Expose
        @SerializedName("termCd")
        private String termCd;
        public Request(String menuId, String termVer, String termCd){
            this.termVer = termVer;
            this.termCd =termCd;
            setData(APIInfo.GRA_CMN_0004.getIfCd(),menuId);
        }
    }

    /**
     * @brief CMN_0004의 응답 항목
     * @author hjpark
     * @see #termVO 약관정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Response extends BaseResponse {
        private TermVO termVO;
    }

}
