package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.model.vo.carlife.PymtFormVO;
import com.genesis.apps.comm.model.vo.carlife.UnpayInfoVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class DTW_1004 extends BaseData {
    /**
     * @brief DTW_1004 요청 항목
     *
     * @see #payTrxId   결제트랜잭션 ID
     * @see #cardType   결제수단코드
     * @see #cardId     결제수단 ID
     * @see #cardCoCode  원천사코드
     * @see #cardNo     카드번호
     * @see #userAgent  사용자 정보(userAgent)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("payTrxId")
        private String payTrxId;
        @Expose
        @SerializedName("cardType")
        private String cardType;
        @Expose
        @SerializedName("cardId")
        private String cardId;
        @Expose
        @SerializedName("cardCoCode")
        private String cardCoCode;
        @Expose
        @SerializedName("cardNo")
        private String cardNo;
        @Expose
        @SerializedName("userAgent")
        private String userAgent;

        public Request(String menuId, String payTrxId,String cardType, String cardId, String cardCoCode, String cardNo, String userAgent) {
            this.payTrxId = payTrxId;
            this.cardType = cardType;
            this.cardId = cardId;
            this.cardCoCode = cardCoCode;
            this.cardNo = cardNo;
            this.userAgent = userAgent;

            setData(APIInfo.GRA_DTW_1004.getIfCd(), menuId);
        }
    }

    /**
     * @brief DTW_1004 응답 항목
     *
     * @see #paymentFormData    결제 요청 폼 데이터
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("paymentFormData")
        private PymtFormVO paymentFormData;
    }
}
