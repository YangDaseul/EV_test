package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 대리운전에서 사용하는 위치정보
 * @author hjpark
 * @see #transId    트랜젝션ID        서버에서 생성되 관리되는 코드
 * @see #mbrMgmtNo	회원관리번호      제네시스 CRM에서 발급되는 고객관리번호
 * @see #reqDivCd	신청구분코드      실시간 : RT, 예약 : RS
 * @see #svcStusCd	서비스상태코드
 * 1) 초기 신청 - 신청중(1000)
 * 2) 결제 완료 - 기사 배정 준비 중(1100), 예약 완료(1110)
 * 3) 배정 완료 - 기사 배정 완료(1200), 기사 재배정 완료(1210)
 * 4) 출발지 도착 - 운행중(1300)
 * 5) 도착지 도착 - 완료(1310)
 * 6) 미 배정 - 기사 미배정 (1410)
 * 7) 취소 - 사용자 취소(1400), 기사 미배정 취소(1420)
 * @see #stusNm	    상태명
 * @see #vin	    차대번호
 * @see #carRegNo	차량번호
 * @see #mdlNm	    모델명
 * @see #expPrice	예상금액            최초 예상금액
 * @see #bluMbrDcPrice	블루멤버스포인트금액
 * @see #rdwnSpcDcPrice	로드윈 특별 할인 금액
 * 결제 금액이 1100원 미만일 경우 신용카드 결제가 안되므로 로드윈에서
 * 특별할인금액을 추가하여 결제 금액을 0원으로 처리 함.
 * @see #payDiv	    결제 구분       카드 : CARD, 현금 : CASH
 * @see #payPrice	결제금액
 * @see #payDt	    결제일시        결제일시(YYYYMMDDHH24MISS 형식)
 * @see #updtDt	    상태변경일시    상태변경일시(YYYYMMDDHH24MISS 형식)
 * @see #rsvDt	    예약일시
 * 신청 구분 코드가 예약(RS) 일 경우 필수
 * 예약일시 형식 : YYYYMMDDHH24MI 형식
 * @see #rgstDt	    서비스신청일시   서비스신청일시(YYYYMMDDHH24MISS 형식)
 * @see #posInfo	위치정보
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class DriveServiceVO extends BaseData {
    @Expose
    @SerializedName("transId")
    private String transId;
    @Expose
    @SerializedName("mbrMgmtNo")
    private String mbrMgmtNo;
    @Expose
    @SerializedName("reqDivCd")
    private String reqDivCd;
    @Expose
    @SerializedName("svcStusCd")
    private String svcStusCd;
    @Expose
    @SerializedName("stusNm")
    private String stusNm;
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("carRegNo")
    private String carRegNo;
    @Expose
    @SerializedName("mdlNm")
    private String mdlNm;
    @Expose
    @SerializedName("expPrice")
    private String expPrice;
    @Expose
    @SerializedName("bluMbrDcPrice")
    private String bluMbrDcPrice;
    @Expose
    @SerializedName("rdwnSpcDcPrice")
    private String rdwnSpcDcPrice;
    @Expose
    @SerializedName("payDiv")
    private String payDiv;
    @Expose
    @SerializedName("payPrice")
    private String payPrice;
    @Expose
    @SerializedName("payDt")
    private String payDt;
    @Expose
    @SerializedName("updtDt")
    private String updtDt;
    @Expose
    @SerializedName("rsvDt")
    private String rsvDt;
    @Expose
    @SerializedName("rgstDt")
    private String rgstDt;
    @Expose
    @SerializedName("driverNm")
    private String driverNm;
    @Expose
    @SerializedName("driverMdn")
    private String driverMdn;
    @Expose
    @SerializedName("posInfo")
    private List<PositionVO> posInfo;
}
