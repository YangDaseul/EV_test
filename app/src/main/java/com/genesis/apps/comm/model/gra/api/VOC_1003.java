package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_VOC_1003
 * @Brief Genesis + 하자재발 내역
 */
public class VOC_1003 extends BaseData {
    /**
     * @brief VOC_1003 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_VOC_1003.getIfCd(), menuId);
        }
    }

    /**
     * @brief VOC_1003 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("dfctList")
        private List<VOCInfoVO> dfctList;
    }
}
