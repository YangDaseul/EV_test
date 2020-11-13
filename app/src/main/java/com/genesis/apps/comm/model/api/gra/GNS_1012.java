package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RentStatusVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1012
 * @Brief My차고 + 렌트/리스 실운행자 상세
 */
public class GNS_1012 extends BaseData {

    /**
     * @brief GNS_1012 요청 항목
     * @see #vin 차대번호
     * @see #seqNo 일련번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("seqNo")
        private String seqNo;

        public Request(String menuId, String vin, String seqNo){
            this.vin = vin;
            this.seqNo = seqNo;
            setData(APIInfo.GRA_GNS_1012.getIfCd(),menuId);
        }
    }

    /**
     * @brief GNS_1012 응답 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        private RentStatusVO rentStatusVO;
        //TODO 별도처리필요
        // new Gson().fromJson(result, GNS_1012.Response.class)   -> MSG 및 CODE 참조
        // GNS_1012.Response.setRentStatusVO(new Gson().fromJson(result, RentStatusVO.class))
        //
    }
}
