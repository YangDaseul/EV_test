package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 멤버십 포인트 정보
 * @author hjpark
 * @see #seqNo 일련번호
 * @see #transDtm 거래일시
 * YYYYMMDDHH24MISS
 * @see #frchsNm 가맹점
 * @see #transTypNm 거래유형명
 * 적립, 사용, 할인, 취소, 소멸, 값 없음
 * (실제 거래는 적립/사용/취소만 사용) - 힐인, 소멸 표기하지 않음
 * @see #useMlg 사용마일리지
 * @see #rmndPont 잔여포인트
 *
 *
 * //아래는 myp2006에서만 사용
 * @see #extncPlanDt 소멸예정일자
 * YYYYMMDD
 * @see #extncPlanPont 소멸예정포인트
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class MembershipPointVO extends BaseData {
    @Expose
    @SerializedName("seqNo")
    private String seqNo;
    @Expose
    @SerializedName("transDtm")
    private String transDtm;
    @Expose
    @SerializedName("frchsNm")
    private String frchsNm;
    @Expose
    @SerializedName("transTypNm")
    private String transTypNm;
    public static final String TYPE_TRANS_SAVE ="적립";
    public static final String TYPE_TRANS_USE ="사용";
    public static final String TYPE_TRANS_CANCEL ="취소";


    @Expose
    @SerializedName("useMlg")
    private String useMlg;
    @Expose
    @SerializedName("rmndPont")
    private String rmndPont;


    @Expose
    @SerializedName("extncPlanDt")
    private String extncPlanDt;
    @Expose
    @SerializedName("extncPlanPont")
    private String extncPlanPont;
}
