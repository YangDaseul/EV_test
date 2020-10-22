package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
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
     * @see #fillerCd 필터코드
     * A: 제네시스전담, C:종합, D:전문, P:일반블루핸즈
     * 필터가 여러 개일 경우 코드값을 더해서 요청
     * 예) 전담+종합+전문 인 경우.  "ACD"
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

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
        @SerializedName("fillerCd")
        private String fillerCd;

        public Request(String menuId, String nx, String ny, String addr, String addrDtl, String fillerCd){
            this.nx = nx;
            this.ny = ny;
            this.addr = addr;
            this.addrDtl = addrDtl;
            this.fillerCd = fillerCd;
            setData(APIInfo.GRA_BTR_1008.getIfCd(), menuId);
        }
    }

    /**
     * @brief BTR_1008 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("asnList")
        private List<BtrVO> asnList;
    }
}
