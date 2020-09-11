package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_VOC_1005
 * @Brief MyG+ 이용약관
 */
public class VOC_1005 extends BaseData {
    /**
     * @author hjpark
     * @brief VOC_1005 요청 항목
     * @see #termCd 약관구분코드
     * 약관코드 정의 필요
     *  - 개인 정보 수집, 이용 약관 동의
     *  - 자동차관리법
     * @see #termVer 약관버전
     * ex) 01.00,  10.00, 23.10 34.23
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("termCd")
        private String termCd;
        @Expose
        @SerializedName("termVer")
        private String termVer;

        public Request(String menuId, String termCd, String termVer){
            this.termCd = termCd;
            this.termVer = termVer;
            setData(APIInfo.GRA_VOC_1005.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief VOC_1005 응답 항목
     * @see #termVO 약관정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Response extends BaseResponse{
        private TermVO termVO;
    }
}
