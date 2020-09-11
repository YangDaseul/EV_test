package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_1006
 * @Brief MyG+ 정유사 포인트
 */
public class MYP_1006 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_1006 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest{

        public Request(String menuId){
            setData(APIInfo.GRA_MYP_1006.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_1006 응답 항목
     * @see #oilRfnPontList 정유사포인트리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("oilRfnPontList")
        private List<OilPointVO> oilRfnPontList;
    }
}
