package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 프리빌리지 정보
 * @author hjpark
 * @see #vin 차대번호
 *
 * @see #carRgstNo   차량번호
 * @see #mdlCd	    차량모델코드
 * ex) JX
 * @see #mdlNm	    차량모델명
 * ex) GV80
 * @see #saleMdlNm	차량판매모델명
 * ex) GV80 디젤 3.0 5인승 19인치 기본디자인 2WD 오토
 * @see #xrclCtyNo	외장컬러상품번호
 * 외장색상 컬러코드
 * @see #xrclCtyNm	외장컬러명
 * 외장색상 컬러명
 * (판매정보 확인 가능한데 미정의된 데이터임)
 *
 *
 * @see #joinPsblCd 가입가능코드
 * 0:신청가능차량, 1:신청된차량, 9:신청불가차량
 * @see #stusCd 상태코드
 * 0000 : 정상, / 그외 에러
 * @see #stusMsg 상태메시지
 * success / 에러 메시지
 * @see #serviceUrl 서비스 신청/현황 URL
 * 신청가능차량인 경우 서비스신청 URL
 * 신청된 차량인 경우 현황 URL
 * @see #serviceDetailUrl 서비스 상세 혜택 URL
 * 신청된 차량인 경우 혜택 URL
 * @see #prvContent 서비스 내용
 * EQ900(Hi) / G90(Gi) / G80(GH) 일경우
 * @see #prvTerm 서비스 제공기간
 * EQ900(Hi) / G90(Gi) / G80(GH) 일경우
 * @see #prvCount 서비스 제공횟수
 * EQ900(Hi) / G90(Gi) / G80(GH) 일경우
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class PrivilegeVO extends BaseData {
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
    @SerializedName("saleMdlNm")
    private String saleMdlNm;
    @Expose
    @SerializedName("xrclCtyNo")
    private String xrclCtyNo;
    @Expose
    @SerializedName("xrclCtyNm")
    private String xrclCtyNm;


    @Expose
    @SerializedName("joinPsblCd")
    private String joinPsblCd;
    public static final String JOIN_CODE_APPLY_POSSIBLE="0";
    public static final String JOIN_CODE_APPLYED="1";
    public static final String JOIN_CODE_UNABLE_APPLY="9";

    @Expose
    @SerializedName("stusCd")
    private String stusCd;
    @Expose
    @SerializedName("stusMsg")
    private String stusMsg;
    @Expose
    @SerializedName("serviceUrl")
    private String serviceUrl;
    @Expose
    @SerializedName("serviceDetailUrl")
    private String serviceDetailUrl;

    @Expose
    @SerializedName("prvContent")
    private String prvContent;
    @Expose
    @SerializedName("prvTerm")
    private String prvTerm;
    @Expose
    @SerializedName("prvCount")
    private String prvCount;
}
