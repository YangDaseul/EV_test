package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1001 extends BaseData {
    /**
     * @brief CHB_1001 요청 항목
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
            setData(APIInfo.GRA_CHB_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1001 응답 항목
     * @see #status 서비스 상태 코드
     *
     * @see #statusNm 서비스 상태명
     * 예약완료, 픽업중, 서비스중, 딜리버리중, 이용완료, 예약취소
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("statusNm")
        private String statusNm;
    }
}
