package com.genesis.apps.comm.model.api.tsauth;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GetCheckCarOwnerShip
 * @Brief 한국 교통안전공단 인증결과 조회
 * 한국 교통안전공단 인증결과를 조회한다.
 */
public class GetCheckCarOwnerShip extends BaseData {
    /**
     * @author hjpark
     * @brief GetCheckCarOwnerShip 요청 항목
     * @see #eaiSeq 전송이력시퀀스
     * @see #vin 차대번호
     * @see #sysScnCd 호출 구분 코드
     * AH: 현대 DKC 앱
     * AK: 기아 DKC 앱
     * AG: 제네시스 DKC 앱
     * IH: 현대웹
     * IK: 기아웹
     * IG: 제네시스웹
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("selector")
        private String selector;
        @Expose
        @SerializedName("eaiSeq")
        private String eaiSeq;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("sysScnCd")
        private String sysScnCd;
        public Request(String selector, String eaiSeq, String vin, String sysScnCd) {
            this.selector = selector;
            this.eaiSeq = eaiSeq;
            this.vin = vin;
            this.sysScnCd = sysScnCd;
        }
    }

    /**
     * @author hjpark
     * @brief GetCheckCarOwnerShip 응답 항목
     *
     * @Ssee #resultCode
     * @Ssee #message
     * 0000: 성공
     * 9999: 예상치 못한 오류가 발생했습니다.
     * 9996: 유효하지 않은 요청입니다.
     * @Ssee #data 인증 데이터
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseData {
        @Expose
        @SerializedName("resultCode")
        private String resultCode;
        @Expose
        @SerializedName("message")
        private String message;
        @Expose
        @SerializedName("data")
        private SubData data;
    }

    /**
     * @Ssee #authCode 인증 코드
     * 0000: 차량 인증에 성공 했습니다.
     * 1000: 고객님 소유의 차량임을 확인 하였습니다.
     * 2000: 고객님 소유의 차량이 아닙니다.
     * 3000: 아직 인증 전 상태입니다.
     * @Ssee #authMessage 서비스제공코드
     */
    public @Data
    class SubData extends BaseData {
        @Expose
        @SerializedName("authCode")
        private String authCode;
        @Expose
        @SerializedName("authMessage")
        private String authMessage;
    }
}
