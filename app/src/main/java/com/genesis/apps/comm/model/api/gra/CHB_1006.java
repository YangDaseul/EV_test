package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1006 extends BaseData {
    /**
     * @brief CHB_1006 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_CHB_1006.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1006 응답 항목
     * @see #dkcKeyAvailableYN DKC 발급 가능 여부
     * Y : DKC서비스가입여부(Y) & 차대번호(동일) & DKC옵션장착구분(Y) & DKC키생성여부(Y) & 서비스용 공유키이용가능여부(Y)
     * N : 그외
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("dkcKeyAvailableYN")
        private String dkcKeyAvailableYN;
    }
}
