package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1025 extends BaseData {
    /**
     * @brief CHB_1025 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        public Request(String menuId) {
            setData(APIInfo.GRA_CHB_1025.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1025 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
