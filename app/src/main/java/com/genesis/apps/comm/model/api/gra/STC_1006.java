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
 * @author Ki-man Kim
 * @file GRA_STC_1006
 * @Brif S-트래픽 충전기 예약취소
 */
public class STC_1006 extends BaseData {
    /**
     * @brief STC_1006 요청 항목
     * @see #reservNo 예약번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("reservNo")
        private String reservNo;

        public Request(String menuId, String reservNo) {
            this.reservNo = reservNo;
            setData(APIInfo.GRA_STC_1006.getIfCd(), menuId);
        }
    }

    /**
     * @brief STC_1006 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {

    }
} // end of class STC_1006
