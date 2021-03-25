package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 긴급출동 신청 상태 정보
 * @author hjpark
 * @see #celphNo 휴대전화번호
 * 긴급출동 신청시 입력한 번호
 * @see #fltCd 고장구분코드
 * '010101' : 시동불가
 *  '010111' : EV충전
 *  '020110' : 경고등 점등
 *  '020104' : 오일 누유
 *  '020108' : 윈도우 작동 불량
 *  '040101' : 견인
 * @see #acptNo 접수번호
 * 상담원 접수 시 생성
 *
 * @see #acptDtm 접수일시
 * YYYYMMDDHH24MISS (상담원 접수 시 생성)
 * @see #acptCnclYn 접수취소여부
 * Y: 취소 N:취소아님
 *
 * @see #pgrsStusCd 진행상태코드
 * 진행상태 - (R:신청, -> W:접수,-> S:출동,-> E:완료, C:취소)
 * @see #carRegNo 차량등록번호
 * @see #areaClsCd 지역구분코드
 * R - 일반 도로
 * H - 자동차 전용도로/고속도로
 * @see #addr 고객위치주소
 * @see #mdlCd 차종코드
 * @see #memo 메모
 * @see #tmpAcptNo 가접수번호
 * @see #tmpAcptDtm 가접수등록일시
 * YYYYMMDDHH24MISS (최초 신청일시)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class SOSStateVO extends BaseData {
    @Expose
    @SerializedName("celphNo")
    private String celphNo;
    @Expose
    @SerializedName("fltCd")
    private String fltCd;
    @Expose
    @SerializedName("acptNo")
    private String acptNo;
    @Expose
    @SerializedName("acptDtm")
    private String acptDtm;
    @Expose
    @SerializedName("acptCnclYn")
    private String acptCnclYn;
    @Expose
    @SerializedName("pgrsStusCd")
    private String pgrsStusCd;
    @Expose
    @SerializedName("carRegNo")
    private String carRegNo;
    @Expose
    @SerializedName("areaClsCd")
    private String areaClsCd;
    @Expose
    @SerializedName("addr")
    private String addr;
    @Expose
    @SerializedName("mdlCd")
    private String mdlCd;
    @Expose
    @SerializedName("memo")
    private String memo;
    @Expose
    @SerializedName("tmpAcptNo")
    private String tmpAcptNo;
    @Expose
    @SerializedName("tmpAcptDtm")
    private String tmpAcptDtm;


}
