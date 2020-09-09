package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1002
 * @Brief My차고 + 차량번호수정
 */
public class GNS_1002 extends BaseData {
    /**
     * @brief GNS_1002 요청 항목
     * @see #vin 차대번호
     * @see #vrn 차량번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("vrn")
        private String vrn;
        
        public Request(String vin, String vrn){
            this.vin = vin;
            this.vrn = vrn;
            setData(APIInfo.GRA_GNS_1002.getIfCd());
        }
    }

    /**
     * @brief GNS_1002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }
}
