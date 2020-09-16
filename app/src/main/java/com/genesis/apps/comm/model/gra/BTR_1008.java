package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_BTR_1008
 * @Brief Genesis + 블루핸즈 검색요청
 */
public class BTR_1008 extends BaseData {

    /**
     * @brief BTR_1008 요청 항목
     * @see #nx 단말 경도 좌표
     * @see #ny 단말 위도 좌표
     * @see #addr 위치주소1
     * 시도
     * @see #addrDtl 위치주소2
     * 시/군
     * @see #firmScnCd 정비망업체속성코드
     * FIRM_SCN_CD = 1 또는 4 : 제네시스전담
     * @see #acps1Cd 지정정비공장구분코드
     * 일반블루핸즈 : ACPS1_CD = C 또는 D
     * 종합 : ACPS1_CD = C, 전문 : ACPS1_CD = D
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{

        @Expose
        @SerializedName("nx")
        private String nx;
        @Expose
        @SerializedName("ny")
        private String ny;
        @Expose
        @SerializedName("addr")
        private String addr;
        @Expose
        @SerializedName("addrDtl")
        private String addrDtl;
        @Expose
        @SerializedName("firmScnCd")
        private String firmScnCd;
        @Expose
        @SerializedName("acps1Cd")
        private String acps1Cd;

        public Request(String menuId, String nx, String ny, String addr, String addrDtl, String firmScnCd, String acps1Cd){
            this.nx = nx;
            this.ny = ny;
            this.addr = addr;
            this.addrDtl = addrDtl;
            this.firmScnCd = firmScnCd;
            this.acps1Cd = acps1Cd;
            setData(APIInfo.GRA_BTR_1008.getIfCd(), menuId);
        }
    }

    /**
     * @brief BTR_1008 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("asnList")
        private List<BtrVO> asnList;
    }
}
