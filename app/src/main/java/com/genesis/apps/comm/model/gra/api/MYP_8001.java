package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_8001
 * @Brief MyG+ 이용약관
 */
public class MYP_8001 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_8001 요청 항목
     * @see #termCd 약관구분코드
     * 앱 이용약관 : 1000
     * 개인정보처리방침 : 2000
     * 개인정보 수집/이용 : 3000
     * 광고성 정보 수신동의 : 4000
     * 제네시스 멤버십 가입 약관  : 5000
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("termCd")
        private String termCd;

        public Request(String menuId, String termCd){
            this.termCd = termCd;
            setData(APIInfo.GRA_MYP_8001.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_8001 응답 항목
     * @see #termList 약관정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Response extends BaseResponse {
        @Expose
        @SerializedName("termList")
        private List<TermVO> termList;
    }
}
