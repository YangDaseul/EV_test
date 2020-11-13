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
 * @file GRA_NOT_0002
 * @Brief 알림센터 알림내용 읽기
 */
public class NOT_0002 extends BaseData {
    /**
     * @author hjpark
     * @brief NOT_0002 요청 항목
     * @see #notiNo 알림일련번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("notiNo")
        private String notiNo;

        public Request(String menuId, String notiNo){
            this.notiNo = notiNo;
            setData(APIInfo.GRA_NOT_0002.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief NOT_0002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {

    }
}
