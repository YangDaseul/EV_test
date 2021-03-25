package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.comm.model.vo.SOSStateVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_3007
 * @Brief 신청이력조회
 */
public class SOS_3007 extends BaseData {
    /**
     * @brief SOS_3007 요청 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_SOS_3007.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_3007 응답 항목
     * @see #sosList
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("sosList")
        private List<SOSStateVO> sosList;
    }
}
