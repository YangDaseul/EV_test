package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1003 extends BaseData {
    /**
     * @brief CHB_1003 요청 항목
     * @see #svcCd 서비스코드
     * CHRGBTR : 픽업앤충전
     *
     * @see #prvcyInfoAgmtYn 개인정보동의여부
     * Y: 동의, N:미동의
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("svcCd")
        private String svcCd;
        @Expose
        @SerializedName("prvcyInfoAgmtYn")
        private String prvcyInfoAgmtYn;

        public Request(String menuId, String svcCd, String prvcyInfoAgmtYn){
            this.svcCd = svcCd;
            this.prvcyInfoAgmtYn = prvcyInfoAgmtYn;
            setData(APIInfo.GRA_CHB_1003.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1003 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
