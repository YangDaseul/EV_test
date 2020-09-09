package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_0005
 * @Brief 사용자의 휴대폰 번호를 변경 처리한다.
 */
public class MYP_0005 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_0005 요청 항목
     * @see #authUuid 본인인증UUID
     * GA 본인인증UUID 값
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest{
        @Expose
        @SerializedName("authUuid")
        private String authUuid;

        public Request(String authUuid){
            this.authUuid = authUuid;
            setData(APIInfo.GRA_MYP_0005.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief MYP_0005 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{

    }
}
