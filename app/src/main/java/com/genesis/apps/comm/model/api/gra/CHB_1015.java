package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.PaymtCardVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1015 extends BaseData {
    /**
     * @brief CHB_1015 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        public Request(String menuId) {
            setData(APIInfo.GRA_CHB_1015.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1015 응답 항목
     * @see #signInYN       간편결제 회원 가입 여부
     * Y:가입, N:미가입
     *
     * @see #cardCount      결제 수단 갯수
     * @see #cardList       간편 결제 수단 리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("signInYN")
        private String signInYN;
        @Expose
        @SerializedName("cardCount")
        private String cardCount;
        @Expose
        @SerializedName("cardList")
        private List<PaymtCardVO> cardList;
    }
}
