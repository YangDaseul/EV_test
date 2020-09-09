package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_OIL_0003
 * @Brief MyG+ 정유사 연동해제
 */
public class OIL_0003 extends BaseData {
    /**
     * @author hjpark
     * @brief OIL_0003 요청 항목
     * @see #oilRfnCd 정유사코드
     * HDOL : hyundai oilbank
     * GSCT  : GS 칼텍스
     * SOIL : S-OIL
     * SKNO : SK 이노베이션
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("oilRfnCd")
        private String oilRfnCd;


        public Request(String oilRfnCd){
            this.oilRfnCd = oilRfnCd;
            setData(APIInfo.GRA_OIL_0003.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief OIL_0003 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{

    }
}
