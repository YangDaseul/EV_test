package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 하자정보
 * @author hjpark
 * @see #csmrNm 고객명
 *
 * @see #csmrTymd 생년월일
 * YYYYMMDD
 * @see #emlAdr 이메일
 * @see #rdwNmZip 도로명우편번호
 * @see #rdwNmAdr 도로명주소
 *
 * @see #rdwNmDtlAdr 도로명상세주소
 * @see #regnTn 연락처1
 * '010'
 * @see #frtDgtTn 연락처2
 * '1234'
 * @see #realDgtTn 연락처3
 * '1234'
 * @see #recvDt 인도날짜
 * YYYYMMDD
 * @see #carNm 차명
 * @see #crnVehlCd 차종코드
 * @see #mdYyyy 등록연월일
 * @see #whotYmd 출고일자
 * @see #trvgDist 주행거리
 * @see #carNo 차량등록번호
 * @see #vin 차대번호
 * @see #wpa 운행지역 시/도
 * @see #admz 운행지역 시/군/구
 * @see #flawCd 하자구분
 * A:중대한하자
 * B:일반하자
 * @see #wkr1Nm 수리업자명(1회차)
 * @see #wk1StrtDt 정비시작일자(1회차)
 * @see #wk1Dt 정비종료일자(1회차)
 * @see #wk1TrvgDist 주행거리(1회차)
 * @see #wk1Caus 하자내용증상(1회차)
 * @see #wk1Dtl 수리내용(1회차)
 * @see #wkr2Nm 수리업자명(2회차)
 * @see #wk2StrtDt 정비시작일자(2회차)
 * @see #wk2Dt 정비종료일자(2회차)
 * @see #wk2TrvgDist 주행거리(2회차)
 * @see #wk2Caus 하자내용증상(2회차)
 * @see #wk2Dtl 수리내용(2회차)
 * @see #wkr3Nm 수리업자명(3회차)
 * @see #wk3StrtDt 정비시작일자(3회차)
 * @see #wk3Dt 정비종료일자(3회차)
 * @see #wk3TrvgDist 주행거리(3회차)
 * @see #wk3Caus 하자내용증상(3회차)
 * @see #wk3Dtl 수리내용(3회차)
 *
 * @see #wkCntFth 수리시도회수4회이상여부 (4회 이상일 경우만 입력)
 * @see #wkPeriod 누적수리기간
 * @see #prnInfoAgreeFlg 개인정보 취급 동의
 *
 * //voc-1003에서만 사용
 * @see #inpSt 접수상태코드
 * 0 : 신청 1: 접수중 2:접수완료
 * @see #inpDate 접수일자
 * @see #inpDesc 접수내용
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class VOCInfoVO extends BaseData {

    public VOCInfoVO(){

    }


    public static final String INP_ST_CODE_REQ = "0";    //신청
    public static final  String INP_ST_CODE_WAITING = "1";//접수중
    public static final  String INP_ST_CODE_FINISH = "2"; //접수완료

    public static final String DEFECT_LEVEL_HIGH = "A";//중대한 하자
    public static final String DEFECT_LEVEL_LOW = "B"; //일반 하자

    @Expose
    @SerializedName("csmrNm")
    private String csmrNm;
    @Expose
    @SerializedName("csmrTymd")
    private String csmrTymd;
    @Expose
    @SerializedName("emlAdr")
    private String emlAdr;
    @Expose
    @SerializedName("rdwNmZip")
    private String rdwNmZip;
    @Expose
    @SerializedName("rdwNmAdr")
    private String rdwNmAdr;

    @Expose
    @SerializedName("rdwNmDtlAdr")
    private String rdwNmDtlAdr;
    @Expose
    @SerializedName("regnTn")
    private String regnTn;
    @Expose
    @SerializedName("frtDgtTn")
    private String frtDgtTn;
    @Expose
    @SerializedName("realDgtTn")
    private String realDgtTn;
    @Expose
    @SerializedName("recvDt")
    private String recvDt;
    @Expose
    @SerializedName("carNm")
    private String carNm;
    @Expose
    @SerializedName("crnVehlCd")
    private String crnVehlCd;
    @Expose
    @SerializedName("mdYyyy")
    private String mdYyyy;
    @Expose
    @SerializedName("whotYmd")
    private String whotYmd;
    @Expose
    @SerializedName("trvgDist")
    private String trvgDist;
    @Expose
    @SerializedName("carNo")
    private String carNo;
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("wpa")
    private String wpa;
    @Expose
    @SerializedName("admz")
    private String admz;
    @Expose
    @SerializedName("flawCd")
    private String flawCd;
    @Expose
    @SerializedName("wkr1Nm")
    private String wkr1Nm;
    @Expose
    @SerializedName("wk1StrtDt")
    private String wk1StrtDt;
    @Expose
    @SerializedName("wk1Dt")
    private String wk1Dt;
    @Expose
    @SerializedName("wk1TrvgDist")
    private String wk1TrvgDist;
    @Expose
    @SerializedName("wk1Caus")
    private String wk1Caus;
    @Expose
    @SerializedName("wk1Dtl")
    private String wk1Dtl;

    @Expose
    @SerializedName("wkr2Nm")
    private String wkr2Nm;
    @Expose
    @SerializedName("wk2StrtDt")
    private String wk2StrtDt;
    @Expose
    @SerializedName("wk2Dt")
    private String wk2Dt;
    @Expose
    @SerializedName("wk2TrvgDist")
    private String wk2TrvgDist;
    @Expose
    @SerializedName("wk2Caus")
    private String wk2Caus;
    @Expose
    @SerializedName("wk2Dtl")
    private String wk2Dtl;

    @Expose
    @SerializedName("wkr3Nm")
    private String wkr3Nm;
    @Expose
    @SerializedName("wk3StrtDt")
    private String wk3StrtDt;
    @Expose
    @SerializedName("wk3Dt")
    private String wk3Dt;
    @Expose
    @SerializedName("wk3TrvgDist")
    private String wk3TrvgDist;
    @Expose
    @SerializedName("wk3Caus")
    private String wk3Caus;
    @Expose
    @SerializedName("wk3Dtl")
    private String wk3Dtl;

    
    @Expose
    @SerializedName("wkCntFth")
    private String wkCntFth;
    @Expose
    @SerializedName("wkPeriod")
    private String wkPeriod;
    @Expose
    @SerializedName("prnInfoAgreeFlg")
    private String prnInfoAgreeFlg;


    //1003에서만 사용
    @Expose
    @SerializedName("inpSt")
    private String inpSt;
    @Expose
    @SerializedName("inpDate")
    private String inpDate;
    @Expose
    @SerializedName("inpDesc")
    private String inpDesc;


}
