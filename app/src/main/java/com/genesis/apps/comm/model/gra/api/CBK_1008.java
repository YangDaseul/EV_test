package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_CBK_1008
 * @Brief insight + 차계부 수정
 */
public class CBK_1008 extends BaseData {
    /**
     * @brief CBK_1008 요청 항목
     * @see #expnSeqNo 차계부일련번호
     *
     * @see #vin 차대번호
     * @see #carRgstNo 차량번호
     * @see #mdlCd 차량모델코드
     * @see #mdlNm 차량모델명
     * @see #expnDivCd 지출분류코드
     * 1000: 주유 2000:정비 3000:세차 4000:주차 5000:통행
     * 6000: 보험 7000:세금 8000:용품 9000:기타
     * @see #expnDtm 지출일시
     * YYYYMMDDHH24MISS
     * @see #expnAmt 지출금액
     * @see #accmMilg 누적주행거리
     * 단위:km
     * @see #expnPlc 지출처
     * @see #rgstChnlCd 등록채널코드
     * 1000: 본인등록 2000:XXXX
     * 6001: GS칼텍스
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("expnSeqNo")
        private String expnSeqNo;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carRgstNo")
        private String carRgstNo;
        @Expose
        @SerializedName("mdlCd")
        private String mdlCd;
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;
        @Expose
        @SerializedName("expnDivCd")
        private String expnDivCd;

        @Expose
        @SerializedName("expnDtm")
        private String expnDtm;
        @Expose
        @SerializedName("expnAmt")
        private String expnAmt;
        @Expose
        @SerializedName("accmMilg")
        private String accmMilg;
        @Expose
        @SerializedName("expnPlc")
        private String expnPlc;
        @Expose
        @SerializedName("rgstChnlCd")
        private String rgstChnlCd;

        public Request(String menuId, String expnSeqNo, VehicleVO vehicleVO, ExpnVO expnVO){
            this.expnSeqNo = expnSeqNo;

            this.vin = vehicleVO.getVin();
            this.carRgstNo = vehicleVO.getCarRgstNo();
            this.mdlCd = vehicleVO.getMdlCd();
            this.mdlNm = vehicleVO.getMdlNm();

            this.expnDivCd = expnVO.getExpnDivCd();
            this.expnDtm = expnVO.getExpnDtm();
            this.expnAmt = expnVO.getExpnAmt();
            this.accmMilg = expnVO.getAccmMilg();
            this.expnPlc = expnVO.getExpnPlc();
            this.rgstChnlCd = expnVO.getRgstChnlCd();
            setData(APIInfo.GRA_CBK_1008.getIfCd(), menuId);
        }
    }

    /**
     * @brief CBK_1008 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
