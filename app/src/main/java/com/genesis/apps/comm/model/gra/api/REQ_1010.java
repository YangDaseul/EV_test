package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1010
 * @Brief service + 정비예약 - 정비소 예약가능일
 */
public class REQ_1010 extends BaseData {
    /**
     * @brief REQ_1010 요청 항목
     * @see #vin        차대번호
     * @see #asnCd      정비망코드
     * @see #rparTypCd  정비내용코드
     * @see #acps1Cd    지정정비공장구분코드
     * 일반블루핸즈 : ACPS1_CD = C 또는 D
     * 종합 : ACPS1_CD = C, 전문 : ACPS1_CD = D
     * @see #firmScnCd  정비망업체속성코드
     * FIRM_SCN_CD = 1 또는 4 : 제네시스전담
     *
     * @see #srtDt 시작일자
     * YYYYMMDD
     * @see #endDt 종료일자
     * YYYYMMDD
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("asnCd")
        private String asnCd;
        @Expose
        @SerializedName("rparTypCd")
        private String rparTypCd;
        @Expose
        @SerializedName("acps1Cd")
        private String acps1Cd;
        @Expose
        @SerializedName("firmScnCd")
        private String firmScnCd;
        @Expose
        @SerializedName("srtDt")
        private String srtDt;
        @Expose
        @SerializedName("endDt")
        private String endDt;

        public Request(String menuId, String vin, String asnCd, String rparTypCd, String acps1Cd, String firmScnCd, String srtDt, String endDt) {
            this.vin = vin;
            this.asnCd = asnCd;
            this.rparTypCd = rparTypCd;
            this.acps1Cd = acps1Cd;
            this.firmScnCd = firmScnCd;
            this.srtDt = srtDt;
            this.endDt = endDt;
            setData(APIInfo.GRA_REQ_1010.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1010 응답 항목
     * @see #rsvtDtList 예약일리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rsvtDtList")
        private List<RepairReserveDateVO> rsvtDtList;
    }
}
