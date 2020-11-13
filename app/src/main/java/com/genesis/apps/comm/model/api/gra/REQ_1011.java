package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RepairGroupVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1011
 * @Brief service + 정비예약 - 정비소 정비만 조회
 */
public class REQ_1011 extends BaseData {
    /**
     * @brief REQ_1011 요청 항목
     * @see #vin        차대번호
     * @see #asnCd      정비망코드
     * @see #rparTypCd  정비내용코드
     * @see #acps1Cd    지정정비공장구분코드
     * 일반블루핸즈 : ACPS1_CD = C 또는 D
     * 종합 : ACPS1_CD = C, 전문 : ACPS1_CD = D
     *
     * @see #firmScnCd  정비망업체속성코드
     * FIRM_SCN_CD = 1 또는 4 : 제네시스전담
     *
     * @see #rsvtDt 예약일
     * YYYYMMDD
     * @see #rsvtTm 예약시간
     * HH24MI
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
        @SerializedName("rsvtDt")
        private String rsvtDt;
        @Expose
        @SerializedName("rsvtTm")
        private String rsvtTm;

        public Request(String menuId, String vin, String asnCd, String rparTypCd, String acps1Cd, String firmScnCd, String rsvtDt, String rsvtTm) {
            this.vin = vin;
            this.asnCd = asnCd;
            this.rparTypCd = rparTypCd;
            this.acps1Cd = acps1Cd;
            this.firmScnCd = firmScnCd;
            this.rsvtDt = rsvtDt;
            this.rsvtTm = rsvtTm;
            setData(APIInfo.GRA_REQ_1011.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1011 응답 항목
     * @see #rpshGrpList 정비반리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rpshGrpList")
        private List<RepairGroupVO> rpshGrpList;
    }
}
