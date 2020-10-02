package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_BTR_1011
 * @Brief Genesis + 블루핸즈 변경/신청  요청
 */
public class BTR_1011 extends BaseData {

    /**
     * @brief BTR_1011 요청 항목
     * @see #vin 차대번호
     * @see #cnsltDivCd 상담구분코드
     * 구분코드 미정의 (예시 :  문의 / 예시…)
     * @see #cnsltTypCd 상담유형코드
     * 유형코드 미정의(예시: 정비/품질/….)
     * @see #ttl 제목
     * @see #cont 내용
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("cnsltDivCd")
        private String cnsltDivCd;
        @Expose
        @SerializedName("cnsltTypCd")
        private String cnsltTypCd;

        @Expose
        @SerializedName("ttl")
        private String ttl;

        @Expose
        @SerializedName("cont")
        private String cont;

        public Request(String menuId,String vin, String cnsltDivCd, String cnsltTypCd, String ttl, String cont){
            this.vin = vin;
            this.cnsltDivCd = cnsltDivCd;
            this.cnsltTypCd = cnsltTypCd;
            this.ttl = ttl;
            this.cont = cont;
            setData(APIInfo.GRA_BTR_1011.getIfCd(),menuId);
        }
    }

    /**
     * @brief BTR_1011 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
