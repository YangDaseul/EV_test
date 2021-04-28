package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.CreditPointVO;
import com.genesis.apps.comm.model.vo.CreditVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_STC_2001
 * @Brif S-트래픽 크레딧내역조회
 */
public class STC_2001 extends BaseData {
    /**
     * @brief STC_2001 요청 항목
     * @see #vin 차대번호
     * @see #carCd 고객위치-위도
     * @see #carNm 고객위치-경도
     * @see #startDt 조회시작일자
     * @see #endDt 조회종료일자
     * @see #divCd 구분코드
     *  - 00 : 전체
     *  - 01 : 적립  : 적립금액 > 0 경우
     *  - 02 : 사용  : 사용금액 > 0 경우
     *  - 09 : 취소  : 사용금액 < 0 경우
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carCd")
        private String carCd;
        @Expose
        @SerializedName("carNm")
        private String carNm;
        @Expose
        @SerializedName("startDt")
        private String startDt;
        @Expose
        @SerializedName("endDt")
        private String endDt;
        @Expose
        @SerializedName("divCd")
        private String divCd;

        public Request(String menuId, String vin, String carCd, String carNm, String startDt, String endDt, String divCd) {
            this.vin = vin;
            this.carCd = carCd;
            this.carNm = carNm;
            this.startDt = startDt;
            this.endDt = endDt;
            this.divCd = divCd;
            setData(APIInfo.GRA_STC_2001.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief STC_2001 응답 항목
     * @see #cretTotAmount 총크레딧잔액
     * @see #creditList 충전포인트리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("cretTotAmount")
        private String cretTotAmount;
        @Expose
        @SerializedName("creditList")
        private List<CreditPointVO> creditList;
        @Expose
        @SerializedName("carCretList")
        private List<CreditVO> carCretList;
    }
} // end of class STC_2001
