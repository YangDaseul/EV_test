package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.AgreeTerm2VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_3013
 * @Brief 긴급충전출동 약관동의
 */
public class SOS_3013 extends BaseData {
    /**
     * @brief SOS_3013 요청 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("termList")
        private List<AgreeTerm2VO> termList;

        public Request(String menuId, List<AgreeTerm2VO> termList){
            this.termList = termList;
            setData(APIInfo.GRA_SOS_3013.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_3013 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
