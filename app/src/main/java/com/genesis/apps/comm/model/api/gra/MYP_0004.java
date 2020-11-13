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
 * @file GRA_MYP_0004
 * @Brief 사용자의 마케팅 수신동의 정보를 변경한다.
 */
public class MYP_0004 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_0004 요청 항목
     * @see #mrktYn 동의여부
     * Y:  동의  N:미동의
     * @see #mrktCd 동의채널코드
     * 동의 한 경우 :
     *  - 1 -> 동의채널  0-> 미동의채널
     *  - SMS(1) + 이메일(1) + 우편(1) + 전화(1)
     *    ex) 1010 : SMS와 우편은 동의했지만 그 외는 미동의
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("mrktYn")
        private String mrktYn;
        @Expose
        @SerializedName("mrktCd")
        private String mrktCd;

        public Request(String menuId, String mrktYn, String mrktCd){
            this.mrktYn = mrktYn;
            this.mrktCd = mrktCd;
            setData(APIInfo.GRA_MYP_0004.getIfCd(),menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_0004 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
