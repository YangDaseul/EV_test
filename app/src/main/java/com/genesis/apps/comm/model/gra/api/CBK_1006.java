package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_CBK_1006
 * @Brief insight + 차계부 등록
 */
public class CBK_1006 extends BaseData {
    /**
     * @brief CBK_1006 요청 항목
     * @see #vin 차대번호
     * @see #expnDivCd 지출분류코드
     * 1000: 주유 2000:정비 3000:세차 4000:주차 5000:통행
     * 6000: 보험 7000:세금 8000:용품 9000:기타
     * @see #expnAmt 지출금액
     * @see #expnDtm 지출일시
     * YYYYMMDDHH24MISS
     * @see #expnPlc 지출처
     * @see #accmMilg 누적주행거리 (단위:km)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("expnDivCd")
        private String expnDivCd;
        @Expose
        @SerializedName("expnAmt")
        private String expnAmt;
        @Expose
        @SerializedName("expnDtm")
        private String expnDtm;
        @Expose
        @SerializedName("expnPlc")
        private String expnPlc;
        @Expose
        @SerializedName("accmMilg")
        private String accmMilg;

        public Request(String menuId, String vin, String expnDivCd, String expnAmt, String expnDtm, String expnPlc, String accmMilg){
            this.vin = vin;
            this.expnDivCd = expnDivCd;
            this.expnAmt = expnAmt;
            this.expnDtm = expnDtm;
            this.expnPlc = expnPlc;
            this.accmMilg = accmMilg;
            setData(APIInfo.GRA_CBK_1006.getIfCd(), menuId);
        }
    }

    /**
     * @brief CBK_1006 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
