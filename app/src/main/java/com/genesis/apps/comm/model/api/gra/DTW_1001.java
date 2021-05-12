package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.carlife.BlueCardInfoVO;
import com.genesis.apps.comm.model.vo.carlife.PayInfoVO;
import com.genesis.apps.comm.model.vo.carlife.StcMbrInfoVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class DTW_1001 extends BaseData {
    /**
     * @brief DTW_1001 요청 항목
     * @see #vin    차대번호
     * @see #userAgent  사용자 정보(userAgent)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("userAgent")
        private String userAgent;

        public Request(String menuId, String vin, String userAgent) {
            this.vin = vin;
            this.userAgent = userAgent;
            setData(APIInfo.GRA_DTW_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief DTW_1001 응답 항목
     * @see #payInfo
     * @see #stcMbrInfo
     * @see #blueCardInfo
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("payInfo")
        private PayInfoVO payInfo;
        @Expose
        @SerializedName("stcMbrInfo")
        private StcMbrInfoVO stcMbrInfo;
        @Expose
        @SerializedName("blueCardInfo")
        private BlueCardInfoVO blueCardInfo;
    }
}
