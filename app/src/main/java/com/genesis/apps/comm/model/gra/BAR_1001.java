package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CardVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_BAR_1001
 * @Brief 바코드 (카드리스트)
 */
public class BAR_1001 extends BaseData {
    /**
     * @author hjpark
     * @brief BAR_1001 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        public Request(){
            setData(APIInfo.GRA_BAR_1001.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief NOT_0002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("cardList")
        private List<CardVO> cardList;
    }
}
