package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class DTW_1002 extends BaseData {
    /**
     * @brief DTW_1002 요청 항목
     * @see #vin    차대번호
     * @see #pwdNo  비밀번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("pwdNo")
        private String pwdNo;

        public Request(String menuId, String vin, String pwdNo) {
            this.vin = vin;
            this.pwdNo = pwdNo;
            setData(APIInfo.GRA_DTW_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief DTW_1002 응답 항목
     * @see #stcMbrYn   회원여부
     * Y: 회원가입 N:미가입
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("stcMbrYn")
        private String stcMbrYn;
    }
}
