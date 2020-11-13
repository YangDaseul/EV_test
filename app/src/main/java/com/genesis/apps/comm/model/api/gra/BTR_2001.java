package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.CounselCodeVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_BTR_2001
 * @Brief Genesis + 상담유형 및 카테고리 작성
 */
public class BTR_2001 extends BaseData {

    /**
     * @brief BTR_2001 요청 항목
     * @see #cdReqCd 코드요청코드
     * CNSL: 상담유형코드,  LGCT : 대분류코드,
     * MDCT: 중분류코드,    SMCT: 소분류코드
     *
     * @see #conslCd 상담유형코드
     * 요청구분코드가 대분류코드 조회할 경우 필수 (고객이 선택한 상담유형코드)
     *
     * @see #lgrCatCd 대분류코드
     * 요청구분코드가 중분류코드 조회할 경우 필수 (고객이 선택한 대분류코드)
     *
     * @see #mdlCatCd 상담유형코드
     * 요청구분코드가 소분류코드 조회할 경우 필수 (고객이 선택한 중분류코드)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("cdReqCd")
        private String cdReqCd;
        @Expose
        @SerializedName("conslCd")
        private String conslCd;
        @Expose
        @SerializedName("lgrCatCd")
        private String lgrCatCd;
        @Expose
        @SerializedName("mdlCatCd")
        private String mdlCatCd;

        public Request(String menuId,String cdReqCd, String conslCd, String lgrCatCd, String mdlCatCd){
            this.cdReqCd = cdReqCd;
            this.conslCd = conslCd;
            this.lgrCatCd = lgrCatCd;
            this.mdlCatCd = mdlCatCd;
            setData(APIInfo.GRA_BTR_2001.getIfCd(),menuId);
        }
    }

    /**
     * @brief BTR_2001 응답 항목
     * @see #cdReqCd 코드요청코드
     * CNSL: 상담유형코드,  LGCT : 대분류코드,
     * MDCT: 중분류코드,    SMCT: 소분류코드
     * @see #conslCd 상담유형코드
     * @see #lgrCatCd 대분류코드
     * @see #mdlCatCd 중분류코드
     * @see #cdList 코드리스트
     * 요청구분코드 = 상담유형코드일 경우는 상담유형코드 리스트
     * 요청구분코드 = 대분류코드일 경우는 대분류코드 리스트
     * 요청구분코드 = 중분류코드일 경우는 중분류코드 리스트
     * 요청구분코드 = 소분류코드일 경우는 소분류코드 리스트
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {

        @Expose
        @SerializedName("cdReqCd")
        private String cdReqCd;
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
        @SerializedName("cdList")
        private List<CounselCodeVO> cdList;
    }
}
