package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.BtrCnslVO;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_BTR_2002
 * @Brief Genesis + 상담문의 작성
 */
public class BTR_2002 extends BaseData {

    /**
     * @brief BTR_2002 요청 항목
     * @see #vin 차대번호
     * @see #conslCd 상담유형코드
     * @see #lgrCatCd 대분류코드
     * @see #mdlCatCd 중분류코드
     * @see #smlCatCd 소분류코드
     * @see #conslTtl 상담제목
     * @see #conslCont 상담내용
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("conslCd")
        private String conslCd;

        @Expose
        @SerializedName("lgrCatCd")
        private String lgrCatCd;
        @Expose
        @SerializedName("mdlCatCd")
        private String mdlCatCd;
        @Expose
        @SerializedName("smlCatCd")
        private String smlCatCd;
        @Expose
        @SerializedName("conslTtl")
        private String conslTtl;
        @Expose
        @SerializedName("conslCont")
        private String conslCont;

        public Request(String menuId,String vin, String conslCd, String lgrCatCd, String mdlCatCd, String smlCatCd, String conslTtl, String conslCont){
            this.vin = vin;
            this.conslCd = conslCd;
            this.lgrCatCd = lgrCatCd;
            this.mdlCatCd = mdlCatCd;
            this.smlCatCd = smlCatCd;
            this.conslTtl = conslTtl;
            this.conslCont = conslCont;
            setData(APIInfo.GRA_BTR_2002.getIfCd(),menuId);
        }
    }

    /**
     * @brief BTR_2002 응답 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
