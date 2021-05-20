package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ChbStatusVO;
import com.genesis.apps.comm.model.vo.EvTermVO;
import com.genesis.apps.comm.model.vo.SosStatusVO;
import com.genesis.apps.comm.model.vo.carlife.PayInfoVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_3001
 * @Brief 긴급충전출동, 충전버틀러 신청 진행 중인 건 존재시 진행상태 정보
 */
public class SOS_3001 extends BaseData {
    /**
     * @brief SOS_3001 요청 항목
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
            setData(APIInfo.GRA_SOS_3001.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_3001 응답 항목
     * @see #sosStus 긴급충전출동 상태정보
     * @see #chbStus 픽업앤충전 상태정보
     * @see #reservYn 충전소예약여부
     * Y: 예약중 N:미예약
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("evSvcTerm")
        private EvTermVO evSvcTerm;
        @Expose
        @SerializedName("sosStus")
        private SosStatusVO sosStus;
        @Expose
        @SerializedName("chbStus")
        private ChbStatusVO chbStus;
        @Expose
        @SerializedName("reservYn")
        private String reservYn;
        @Expose
        @SerializedName("payInfo")
        private PayInfoVO payInfo;
    }
}
