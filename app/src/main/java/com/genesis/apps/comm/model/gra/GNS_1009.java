package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1009
 * @Brief My차고 + 렌트/리스 재직증명서 이미지 등록
 */
public class GNS_1009 extends BaseData {

    /**
     * @brief GNS_1009 요청 항목
     * @see #vin 차대번호
     * @see #imgFilNm 재직증명서 이미지 파일명
     *
     * imgFil 이미지파일 -> 멀티파트폼포맷으로 바이너리
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

        private File file;
        private String imageKeyName="imgFil";

        public Request(String menuId, String vin, String imgFilNm, File file){
            this.vin = vin;
            this.imgFilNm = imgFilNm;
            this.file = file;
            setData(APIInfo.GRA_GNS_1009.getIfCd(),menuId);
        }
    }

    /**
     * @brief GNS_1009 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }
}
