package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1004 extends BaseData {
    /**
     * @brief CHB_1004 요청 항목
     * @see #svcCd 서비스 코드
     * CHRGBTR : 픽업앤충전
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("svcCd")
        private String svcCd;

        public Request(String menuId, String svcCd){
            this.svcCd = svcCd;
            setData(APIInfo.GRA_CHB_1004.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1004 응답 항목
     * @see #prvcyInfoAgmtYn 개인정보동의여부
     * Y: 동의, N:미동의
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("prvcyInfoAgmtYn")
        private String prvcyInfoAgmtYn;
    }
}
