package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_LGN_0007
 * @Brief 로그인+ 토픽
 * @author hjpark
 */
public class LGN_0007 extends BaseData {
    /**
     * @brief LGN_0007의 요청 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        public Request(String menuId){
            setData(APIInfo.GRA_LGN_0007.getIfCd(), menuId);
        }
    }

    /**
     * @brief LGN_0007의 응답 항목
     * @author hjpark
     * @see #topicList 토픽
     * 소유차량이 있는 경우만 값이 존재
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("topicList")
        private List<String> topicList;
    }
}
