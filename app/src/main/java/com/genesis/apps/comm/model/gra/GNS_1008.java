package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1008
 * @Brief My차고 + 렌트/리스 계약서이미지 등록
 */
public class GNS_1008 extends BaseData {

    /**
     * @brief GNS_1008 요청 항목
     * @see #vin 차대번호
     * @see #imgFilNm 이미지파일명
     * 계약서 이미지 파일명
     * @see #imgFil 이미지파일
     * 계약서 이미지 파일(hex string 변환)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("imgFilNm")
        private String imgFilNm;
        @Expose
        @SerializedName("imgFil")
        private String imgFil;

        public Request(String vin, String imgFilNm, String imgFil){
            this.vin = vin;
            this.imgFilNm = imgFilNm;
            this.imgFil = imgFil;
            setData(APIInfo.GRA_GNS_1008.getIfCd());
        }
    }

    /**
     * @brief GNS_1008 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }
}
