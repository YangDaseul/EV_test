package com.genesis.apps.comm.model.api.tsauth;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GetNewCarOwnerShip
 * @Brief 한국 교통안전공단 화면 호출
 * 한국 교통안전공단 화면 호출 바로 할 수 없음, 호출을 위한 키값이 존재하며 그 키값을 호출하고 이력을 생성한다.
 */
public class GetNewCarOwnerShip extends BaseData {
    /**
     * @author hjpark
     * @brief GetNewCarOwnerShip 요청 항목
     * @see #selector 호출 구분자
     * getMobileCarOwnerShip
     * @see #cellNo 전화번호
     * @see #userName 고객명
     * @see #csmrMgmtNo 고객관리번호
     * @see #carVrn 차량번호
     * @see #carVin 차대번호
     * @see #sysScnCd 호출 구분 코드
     * AH: 현대 DKC 앱
     * AK: 기아 DKC 앱
     * AG: 제네시스 DKC 앱
     * IH: 현대웹
     * IK: 기아웹
     * IG: 제네시스웹
     * @see #purposeCd 용도 구분 코드
     * DKC: DKC 인증용 (default)
     * USED: 중고차 인증용
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("selector")
        private String selector;
        @Expose
        @SerializedName("cellNo")
        private String cellNo;
        @Expose
        @SerializedName("userName")
        private String userName;
        @Expose
        @SerializedName("csmrMgmtNo")
        private String csmrMgmtNo;
        @Expose
        @SerializedName("carVrn")
        private String carVrn;
        @Expose
        @SerializedName("carVin")
        private String carVin;
        @Expose
        @SerializedName("sysScnCd")
        private String sysScnCd;
        @Expose
        @SerializedName("purposeCd")
        private String purposeCd;

        public Request(String selector, String cellNo, String userName, String csmrMgmtNo, String carVrn, String carVin, String sysScnCd, String purposeCd) {
            this.selector = selector;
            this.cellNo = cellNo;
            this.userName = userName;
            this.csmrMgmtNo = csmrMgmtNo;
            this.carVrn = carVrn;
            this.carVin = carVin;
            this.sysScnCd = sysScnCd;
            this.purposeCd = purposeCd;
        }
    }

    /**
     * @author hjpark
     * @brief GetNewCarOwnerShip 응답 항목
     *
     * @Ssee #resultCode
     * @Ssee #message
     * 0000: 성공
     * 9999: 예상치 못한 오류가 발생했습니다.
     * 9998: 디지털 키 사양이 적용된 차량이 아닙니다.
     * 9997: 이미 등록된 차량입니다.
     * 9996: 유효하지 않은 요청입니다.
     * 9995: 예상치 못한 오류가 발생했습니다.(브랜드 별 통신 오류)
     *
     * @Ssee #data 인증 파라미터 데이터
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
     * @Ssee #timeStamp 인증키 확인을 위한 시간
     * @Ssee #svcCodeArr 서비스제공코드
     * @Ssee #svcType 서비스 타입(고정/비고정)
     * @Ssee #returnURLA 인증 성공 url
     * @Ssee #returnURLD 인증 실패 url
     * @Ssee #carOwner 차량소유주명
     * @Ssee #carRegNo 차량등록번호
     * @Ssee #carVinNo 차대번호
     * @Ssee #eaiSeq 전송이력시퀀스
     */
    public @Data
    class SubData extends BaseData {
        @Expose
        @SerializedName("hashValue")
        private String hashValue;
        @Expose
        @SerializedName("timeStamp")
        private String timeStamp;
        @Expose
        @SerializedName("svcCodeArr")
        private String svcCodeArr;
        @Expose
        @SerializedName("svcType")
        private String svcType;
        @Expose
        @SerializedName("returnURLA")
        private String returnURLA;
        @Expose
        @SerializedName("returnURLD")
        private String returnURLD;
        @Expose
        @SerializedName("carOwner")
        private String carOwner;
        @Expose
        @SerializedName("carRegNo")
        private String carRegNo;
        @Expose
        @SerializedName("carVinNo")
        private String carVinNo;
        @Expose
        @SerializedName("eaiSeq")
        private String eaiSeq;
    }
}
