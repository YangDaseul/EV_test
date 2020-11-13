package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.AgreeTermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_OIL_0002
 * @Brief MyG+ 정유사 약관요청(All)
 */
public class OIL_0002 extends BaseData {
    /**
     * @author hjpark
     * @brief OIL_0002 요청 항목
     * @see #oilRfnCd 정유사코드
     * HDOL : hyundai oilbank
     * GSCT  : GS 칼텍스
     * SOIL : S-OIL
     * SKNO : SK 이노베이션
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("oilRfnCd")
        private String oilRfnCd;
        @Expose
        @SerializedName("terms")
        private List<AgreeTermVO> terms;




        public Request(String menuId, String oilRfnCd, List<AgreeTermVO> terms){
            this.oilRfnCd = oilRfnCd;
            this.terms = terms;
            setData(APIInfo.GRA_OIL_0002.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief OIL_0002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
