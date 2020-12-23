package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 원격진단신청정보
 * @author hjpark
 * @see #dvcId 디바이스ID
 * @see #tmpAcptCd 가접수번호
 * @see #rcptCd 접수번호
 *
 * @see #fltCd 고장코드
 * 고장코드/카테고리코드 의미
 * (ex : 080102  차량 시동이 걸리지 않아요)
 * @see #fltStmt 고장문구
 * @see #rsrvDtm 예약일시
 * YYYYMMDDHH24MISS
 * @see #aplyStusCd 신청상태코드
 * R':신청 / W:대기 / D:확정 / E:완료 / F:통신상태불량  'C':취소
 * @see #chckCmnt 점검결과 Comment
 * @see #chckItemList 점검항목리스트
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RemoteHistoryVO extends BaseData {
    @Expose
    @SerializedName("dvcId")
    private String dvcId;
    @Expose
    @SerializedName("tmpAcptCd")
    private String tmpAcptCd;
    @Expose
    @SerializedName("rcptCd")
    private String rcptCd;
    @Expose
    @SerializedName("fltCd")
    private String fltCd;
    @Expose
    @SerializedName("fltStmt")
    private String fltStmt;
    @Expose
    @SerializedName("rsrvDtm")
    private String rsrvDtm;
    @Expose
    @SerializedName("aplyStusCd")
    private String aplyStusCd;

    @Expose
    @SerializedName("chckCmnt")
    private String chckCmnt;
    @Expose
    @SerializedName("chckItemList")
    private List<RemoteCheckVO> chckItemList;
}
