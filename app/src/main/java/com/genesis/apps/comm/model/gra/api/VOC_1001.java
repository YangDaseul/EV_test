package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_VOC_1001
 * @Brief Genesis + 하자재발 차대번호 조회
 */
public class VOC_1001 extends BaseData {
    /**
     * @brief VOC_1001 요청 항목
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
            setData(APIInfo.GRA_VOC_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief VOC_1001 응답 항목
     * @see #vhclList 차량리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("vhclList")
        private List<VehicleVO> vhclList;
    }
}
