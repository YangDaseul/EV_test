package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_CBK_1001
 * @Brief insight + 차계부 차량조회
 */
public class CBK_1001 extends BaseData {
    /**
     * @brief CBK_1001 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_CBK_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief CBK_1001 응답 항목
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
