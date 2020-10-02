package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
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
    /**
     * @author hjpark
     * @brief DDS_1002 요청 항목
     * @see #mbrMgmtNo 회원관리번호
     * 제네시스 CRM에서 발급되는 고객관리번호
     * @see #vin 차대번호
     * @see #reqDivCd 신청구분코드
     * @see #rsvDt 예약일시
     * @see #reqMemo 요청사항
     * @see #expPrice 예상금액
     * @see #posInfo 위치정보
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("mbrMgmtNo")
        private String mbrMgmtNo;
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
        @SerializedName("posInfo")
        private PositionVO posInfo;


        public Request(String menuId, String mbrMgmtNo, String vin, String reqDivCd, String rsvDt, String reqMemo, String expPrice, PositionVO posInfo){
            this.mbrMgmtNo = mbrMgmtNo;
            this.vin = vin;
            this.reqDivCd = reqDivCd;
            this.rsvDt = rsvDt;
            this.reqMemo = reqMemo;
            this.expPrice = expPrice;
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
