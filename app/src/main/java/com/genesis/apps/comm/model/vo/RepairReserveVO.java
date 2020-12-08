package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @brief 정비 예약 정보
 * @see #rparRsvtSeqNo  정비예약일련번호
 * @see #rsvtTypCd  예약유형코드
 * AUTO:오토케어, ARPT:에어포트 HTOH:홈투홈 RPSH:정비소
 * @see #rparTypCd    정비내용코드
 * @see #vin        차대번호
 * @see #carRgstNo        차량번호
 * @see #mdlCd        차량모델코드
 * @see #mdlNm        차량모델명
 * @see #rsvtHopeDt    예약희망일자
 * YYYYMMDD
 * @see #autoAmpmCd    오전오후구분코드
 * A : 오전 P:오후
 * @see #hpNo        입력한 휴대전화번호
 * @see #pckpZip    픽업우편번호
 * @see #pckpAddr    픽업주소
 * @see #dlvryZip    딜리버리우편번호
 * @see #dlvryAddr    딜리버리주소
 * @see #autoSvc1    엔진오일항목여부
 * Y: 선택 N:미선택
 * @see #autoSvc2    와이퍼블레이드여부
 * Y: 선택 N:미선택
 * @see #autoSvc3    에어컨필터여부
 * Y: 선택 N:미선택
 * @see #autoSvc4    네비업데이트여부
 * Y: 선택 N:미선택
 * @see #rqrm        요구사항
 *
 *
 * @See #acps1Cd1Cd 지정정비공장구분코드
 * 일반블루핸즈 : ACPS1_CD = C 또는 D
 * 종합 : ACPS1_CD = C, 전문 : ACPS1_CD = D
 *
 *
 *
 * //홈투홈전용
 * @see #pckpDivCd 픽업구분코드
 * 1: 픽업  2: 딜리버리   3: 픽업+딜리버리
 *
 * //정비소 예약 신청 전용 1012
 * @see #rsvtHopeTm 예약희망시간
 * HH24MI
 *
 * @see #rsvtStusCd 예약상태코드
 * 예약상태코드	    홈투홈	에어포트	오토케어	정비소
 * 예약신청	        1100	2100	3100	4100
 * 예약완료(예약중)	1200	2200	3200	4200
 * 픽업대기	        1300	2300	3300
 * 픽업중	        1400	2400	3400
 * 정비소도착	    1500	2500	3500
 * 딜리버리대기	    6300	7300	8300
 * 딜리버리중	    6400	7400	8400
 * 딜리버리완료	    6500	7500	8500
 * 예약취소	        6800	7800	8800	9800
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairReserveVO extends BaseData {

    public RepairReserveVO(){
        this.rsvtTypCd = "";
    }

    //REQ-1007
    public RepairReserveVO(
            String rsvtTypCd
            , String rparTypCd
            , String vin
            , String carRgstNo
            , String mdlCd
            , String mdlNm
            , String rsvtHopeDt
            , String autoAmpmCd
            , String hpNo
            , String pckpAddr
            , String autoSvc1
            , String autoSvc2
            , String autoSvc3
            , String autoSvc4
            , String rqrm
            , String userNm){

        this.rsvtTypCd = rsvtTypCd;
        this.rparTypCd = rparTypCd;
        this.vin = vin;
        this.carRgstNo = carRgstNo;
        this.mdlCd = mdlCd;
        this.mdlNm = mdlNm;
        this.rsvtHopeDt = rsvtHopeDt;
        this.autoAmpmCd = autoAmpmCd;
        this.hpNo = hpNo;
        this.pckpAddr = pckpAddr;
        this.autoSvc1 = autoSvc1;
        this.autoSvc2 = autoSvc2;
        this.autoSvc3 = autoSvc3;
        this.autoSvc4 = autoSvc4;
        this.rqrm = rqrm;
        this.userNm = userNm;
    }


    //REQ-1009
    public RepairReserveVO(
            String rsvtTypCd
            , String rparTypCd
            , String vin
            , String carRgstNo
            , String mdlCd
            , String mdlNm
            , String rsvtHopeDt
            , String hpNo
            , String pckpDivCd
            , String pckpAddr
            , String dlvryAddr
            , String rqrm
            , String userNm){

        this.rsvtTypCd = rsvtTypCd;
        this.rparTypCd = rparTypCd;
        this.vin = vin;
        this.carRgstNo = carRgstNo;
        this.mdlCd = mdlCd;
        this.mdlNm = mdlNm;
        this.rsvtHopeDt = rsvtHopeDt;
        this.hpNo = hpNo;
        this.pckpDivCd = pckpDivCd;
        this.pckpAddr = pckpAddr;
        this.dlvryAddr = dlvryAddr;
        this.rqrm = rqrm;
        this.userNm = userNm;
    }


    //REQ-1012
    public RepairReserveVO(
            String rsvtTypCd
            , String rparTypCd
            , String vin
            , String carRgstNo
            , String mdlCd
            , String mdlNm
            , String rsvtHopeDt
            , String rsvtHopeTm
            , String hpNo
            , String acps1Cd
            , String asnCd
            , String asnNm
            , String repTn
            , String pbzAdr
            , String rpshGrpCd
            , String rpshGrpNm
            , String userNm
            , SurveyVO svyInfo){

        this.rsvtTypCd = rsvtTypCd;
        this.rparTypCd = rparTypCd;
        this.vin = vin;
        this.carRgstNo = carRgstNo;
        this.mdlCd = mdlCd;
        this.mdlNm = mdlNm;
        this.rsvtHopeDt = rsvtHopeDt;
        this.rsvtHopeTm = rsvtHopeTm;
        this.hpNo = hpNo;
        this.acps1Cd = acps1Cd;
        this.asnCd = asnCd;
        this.asnNm = asnNm;
        this.repTn = repTn;
        this.pbzAdr = pbzAdr;
        this.rpshGrpCd = rpshGrpCd;
        this.rpshGrpNm = rpshGrpNm;
        this.userNm = userNm;
        this.svyInfo = svyInfo;
    }

    @Expose
    @SerializedName("rparRsvtSeqNo")
    private String rparRsvtSeqNo;
    @Expose
    @SerializedName("rsvtTypCd")
    private String rsvtTypCd;
    @Expose
    @SerializedName("rparTypCd")
    private String rparTypCd;
    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("carRgstNo")
    private String carRgstNo;
    @Expose
    @SerializedName("mdlCd")
    private String mdlCd;
    @Expose
    @SerializedName("mdlNm")
    private String mdlNm;
    @Expose
    @SerializedName("rsvtHopeDt")
    private String rsvtHopeDt;
    @Expose
    @SerializedName("autoAmpmCd")
    private String autoAmpmCd;
    @Expose
    @SerializedName("hpNo")
    private String hpNo;

    @Expose
    @SerializedName("pckpZip")
    private String pckpZip;
    @Expose
    @SerializedName("pckpAddr")
    private String pckpAddr;
    @Expose
    @SerializedName("dlvryZip")
    private String dlvryZip;
    @Expose
    @SerializedName("dlvryAddr")
    private String dlvryAddr;
    @Expose
    @SerializedName("autoSvc1")
    private String autoSvc1;
    @Expose
    @SerializedName("autoSvc2")
    private String autoSvc2;
    @Expose
    @SerializedName("autoSvc3")
    private String autoSvc3;
    @Expose
    @SerializedName("autoSvc4")
    private String autoSvc4;
    @Expose
    @SerializedName("rqrm")
    private String rqrm;


    //홈투홈 REQ-1009의 요청항목에서만 사용
    @Expose
    @SerializedName("pckpDivCd")
    private String pckpDivCd;


    //홈투홈 REQ-1012의 요청항목에서만 사용
    @Expose
    @SerializedName("rsvtHopeTm")
    private String rsvtHopeTm;


    //1012 정비소 예약신청에서만 사용
    @Expose
    @SerializedName("acps1Cd")
    private String acps1Cd;
    @Expose
    @SerializedName("asnCd")
    private String asnCd;
    @Expose
    @SerializedName("asnNm")
    private String asnNm;
    @Expose
    @SerializedName("repTn")
    private String repTn;
    @Expose
    @SerializedName("pbzAdr")
    private String pbzAdr;
    @Expose
    @SerializedName("rpshGrpCd")
    private String rpshGrpCd;
    @Expose
    @SerializedName("rpshGrpNm")
    private String rpshGrpNm;

    @Expose
    @SerializedName("svyInfo")
    private SurveyVO svyInfo;



    //REQ-1013에서 오토케어 예약현황
    @Expose
    @SerializedName("rsvtStusCd")
    private String rsvtStusCd;
    @Expose
    @SerializedName("rsvtNo")
    private String rsvtNo;
    @Expose
    @SerializedName("mbrNm")
    private String mbrNm;


    //REQ-1013 홈투홈예약현황 및 에어포트 예약현황
    @Expose
    @SerializedName("pkckExtapChkUri") //pkckExtapChkUri
    private String pkckExtapChkUri;
    @Expose
    @SerializedName("dlvryExtapChkUri") //딜리버리외관체크URI
    private String dlvryExtapChkUri;

    //로컬(오토케어 3단계 입력 확인)에서 표시용으로만 사용
    private String userNm;


}
