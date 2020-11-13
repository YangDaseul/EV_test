package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_NOT_0003
 * @Brief 알림센터 알림내용 읽기
 */
public class NOT_0003 extends BaseData {
    /**
     * @author hjpark
     * @brief NOT_0003 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        public Request(String menuId){
            setData(APIInfo.GRA_NOT_0003.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief NOT_0003 응답 항목
     * @see #newNotiCnt 읽지 않은 새 알림목록 개수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("newNotiCnt")
        private String newNotiCnt;
    }
}
