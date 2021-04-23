package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1020 extends BaseData {
    /**
     * @brief CHB_1020 리뷰 평가 요청 항목
     * @SEE #orderId 주문 ID
     * @SEE #evalScore 서비스코드
     * @SEE #evalRvwCntn 평가문구
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("orderId")
        private String orderId;
        @Expose
        @SerializedName("evalScore")
        private String evalScore;
        @Expose
        @SerializedName("evalRvwCntn")
        private String evalRvwCntn;

        public Request(String menuId, String orderId, String evalScore, String evalRvwCntn) {
            this.orderId = orderId;
            this.evalScore = evalScore;
            this.evalRvwCntn = evalRvwCntn;
            setData(APIInfo.GRA_CHB_1020.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1020 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
