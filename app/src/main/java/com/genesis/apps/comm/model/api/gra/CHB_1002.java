package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1002 extends BaseData {
    /**
     * @brief CHB_1002 요청 항목
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
            setData(APIInfo.GRA_CHB_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1002 응답 항목
     * @see #scrnCntn 동의화면 html 데이터
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("scrnCntn")
        private String scrnCntn;
    }
}
