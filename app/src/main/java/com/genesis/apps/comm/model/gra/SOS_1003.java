package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_1003
 * @Brief Genesis + 추가 비용 안내
 */
public class SOS_1003 extends BaseData {
    /**
     * @brief SOS_1003 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        public Request(){
            setData(APIInfo.GRA_SOS_1003.getIfCd());
        }
    }

    /**
     * @brief SOS_1003 응답 항목
     * @see #ttl 제목
     * @see #cont 내용
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("ttl")
        private String ttl;
        @Expose
        @SerializedName("cont")
        private String cont;
    }
}
