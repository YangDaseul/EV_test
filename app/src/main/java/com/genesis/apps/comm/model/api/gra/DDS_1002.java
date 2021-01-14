package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.PositionVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_DDS_1002
 * @Brief Genesis + 대리운전 신청 정보 등록
 */
public class DDS_1002 extends BaseData {
    //지금 신청, 예약 상태 구분
    public static final String REQ_RIGHT_NOW = "RT";
    public static final String REQ_RESERVE = "RS";

    //출발지, 도착지 구분
    public static final String REQ_POSITION_FROM = "DEPT";
    public static final String REQ_POSITION_TO = "DEST";

    /**
     * @author hjpark
     * @brief DDS_1002 요청 항목
     * 제네시스 CRM에서 발급되는 고객관리번호
     * @see #vin 차대번호
     * @see #reqDivCd 신청구분코드
     * @see #rsvDt 예약일시
     * @see #reqMemo 요청사항
     * @see #expPrice 예상금액
     * @see #posInfo 위치정보[출발지, 도착지]
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("reqDivCd")
        private String reqDivCd;
        @Expose
        @SerializedName("rsvDt")
        private String rsvDt;
        @Expose
        @SerializedName("reqMemo")
        private String reqMemo;
        @Expose
        @SerializedName("expPrice")
        private String expPrice;
        @Expose
        @SerializedName("rwId")
        private String rwId;
        @Expose
        @SerializedName("posInfo")
        private List<PositionVO> posInfo;

        public Request(String menuId, String vin, String reqDivCd, String rsvDt, String reqMemo, String expPrice, String rwId, List<PositionVO> posInfo) {
            this.vin = vin;
            this.reqDivCd = reqDivCd;
            this.rsvDt = rsvDt;
            this.reqMemo = reqMemo;
            this.expPrice = expPrice;
            this.rwId = rwId;
            this.posInfo = posInfo;
            setData(APIInfo.GRA_DDS_1002.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief DDS_1002 응답 항목
     * @see #transId    트랜젝션ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("transId")
        private String transId;
    }
}
