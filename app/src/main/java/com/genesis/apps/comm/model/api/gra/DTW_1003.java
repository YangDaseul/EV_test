package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.model.vo.carlife.UnpayInfoVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class DTW_1003 extends BaseData {
    /**
     * @brief DTW_1003 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId) {
            setData(APIInfo.GRA_DTW_1003.getIfCd(), menuId);
        }
    }

    /**
     * @brief DTW_1003 응답 항목
     * @see #unpayInfo  미수금 정보
     * @see #cardInfo   결제 수단 리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("unpayInfo")
        private UnpayInfoVO unpayInfo;
        @Expose
        @SerializedName("cardInfo")
        private List<PaymtCardVO> cardInfo;
    }
}
