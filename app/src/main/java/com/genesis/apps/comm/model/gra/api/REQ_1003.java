package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.model.vo.RepairVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1003
 * @Brief service + 서비스메인 - 정비내용조회
 */
public class REQ_1003 extends BaseData {
    /**
     * @brief REQ_1003 요청 항목
     * @see #vin 차대번호
     * @see #mdlCd 모델코드
     * @see #asnCd 정비망코드
     * @see #acps1Cd 지정정비공장구분코드
     * 일반블루핸즈 : ACPS1_CD = C 또는 D
     * 종합 : ACPS1_CD = C, 전문 : ACPS1_CD = D
     * 서비스센터 : 2
     * @see #firmScnCd 정비망업체속성코드
     * Y: 제네시스전담인경우.  N: 그외
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("mdlCd")
        private String mdlCd;
        @Expose
        @SerializedName("asnCd")
        private String asnCd;
        @Expose
        @SerializedName("acps1Cd")
        private String acps1Cd;
        @Expose
        @SerializedName("firmScnCd")
        private String firmScnCd;

        public Request(String menuId, String vin, String mdlCd, String asnCd, String acps1Cd, String firmScnCd){
            this.vin = vin;
            this.mdlCd = mdlCd;
            this.asnCd = asnCd;
            this.acps1Cd = acps1Cd;
            this.firmScnCd = firmScnCd;
            setData(APIInfo.GRA_REQ_1003.getIfCd(), menuId);
        }
    }
    /**
     * @brief REQ_1003 응답 항목
     * @see #prctYn 예약가능여부
     * Y:예약가능 N:예약불가
     * @see #rparTypList 정비내용리스트
     * 예약이 가능할 경우 필수.
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("prctYn")
        private String prctYn;
        @Expose
        @SerializedName("rparTypList")
        private List<RepairTypeVO> rparTypList;
    }
}
