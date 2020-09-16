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
 * @see #rsvtTypCd  예약유형코드
 * AUTO:오토케어, ARPT:에어포트 HTOH:홈투홈 RPSH:정비소
 * @see #rparTypCd    정비내용코드
 * @see #vin        차대번호
 * @see #vrn        차량번호
 * @see #mdlCd        차량모델코드
 * @see #mdlNm        차량모델명
 * @see #rsvtHopeDt    예약희망일자
 * YYYYMMDD
 * @see #autoAmpmCd    오전오후구분코드
 * A : 오전 P:오후
 * @see #hpNo        입력한 휴대전화번호
 * @see #pkckZip    픽업우편번호
 * @see #pkckAddr    픽업주소
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
 *
 *
 * //홈투홈전용
 * @see #pkckDivCd 픽업구분코드
 * 1: 픽업  2: 딜리버리   3: 픽업+딜리버리
 *
 * //정비소 예약 신청 전용 1012
 * @see #rsvtHopeTm 예약희망시간
 * HH24MI
 **/
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepairReserveVO extends BaseData {

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
    @SerializedName("vrn")
    private String vrn;
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
    @SerializedName("pkckZip")
    private String pkckZip;
    @Expose
    @SerializedName("pkckAddr")
    private String pkckAddr;
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
    @SerializedName("pkckDivCd")
    private String pkckDivCd;


    //홈투홈 REQ-1012의 요청항목에서만 사용
    @Expose
    @SerializedName("rsvtHopeTm")
    private String rsvtHopeTm;


    //1012 정비소 예약신청에서만 사용
    @Expose
    @SerializedName("asnCd")
    private String asnCd;
    @Expose
    @SerializedName("asnNm")
    private String asnNm;
    @Expose
    @SerializedName("repnTn")
    private String repnTn;
    @Expose
    @SerializedName("pbzAdr")
    private String pbzAdr;
    @Expose
    @SerializedName("rpshGrpCd")
    private String rpshGrpCd;
    @Expose
    @SerializedName("rpshGrpNm")
    private String rpshGrpNm;



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




}
