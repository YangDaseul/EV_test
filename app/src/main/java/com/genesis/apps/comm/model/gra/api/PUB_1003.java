package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.AddressGuVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_PUB_1003
 * @Brief Home + 시도조회
 */
public class PUB_1003 extends BaseData {
    /**
     * @brief PUB_1003 요청 항목
     * @see #sidoCd 시도코드
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("sidoCd")
        private String sidoCd;

        public Request(String menuId, String sidoCd){
            this.sidoCd = sidoCd;
            setData(APIInfo.GRA_PUB_1003.getIfCd(), menuId);
        }
    }
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("gugunList")
        private List<AddressGuVO> gugunList;
    }
}
