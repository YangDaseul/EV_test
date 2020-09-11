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
 * @file GRA_MYP_2003
 * @Brief MyG+ 포인트 사용내역처 안내
 */
public class MYP_2003 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_2003 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{

        public Request(String menuId){
            setData(APIInfo.GRA_MYP_2003.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_2003 응답 항목
     * @see #ttl 제목
     * @see #cont 내용
     * html 형식에 문자열
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
