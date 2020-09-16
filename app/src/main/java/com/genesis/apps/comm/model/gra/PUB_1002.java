package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.AddressCityVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_PUB_1002
 * @Brief Home + 시도조회
 */
public class PUB_1002 extends BaseData {
    /**
     * @brief PUB_1002 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        public Request(String menuId){
            setData(APIInfo.GRA_PUB_1002.getIfCd(), menuId);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("sidoList")
        private List<AddressCityVO> sidoList;
    }
}
