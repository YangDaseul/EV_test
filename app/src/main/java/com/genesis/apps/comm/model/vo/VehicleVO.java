package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.StringUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author hjpark
 * @Brief 차량정보 (소유차량, 미로그인시, 계약차량 등)
 * 미로그인/미회원의 경우 아래 4개 정보만 의미가 있음
 * {@link #mdlCd#mdlNm#xrclCtyNo#mainImgUri}
 *
 *
 * 계약차량인 경우 아래 정보만 의미가 있음
 * {@link #saleMdlCd#xrclCtyNo#ieclCtyNo#ctrctNo}
 *
 *
 * @see #vin 차대번호
 * 디폴트 차량인 경우 0000
 * @see #carRgstNo 차량번호
 * @see #mdlCd 차량모델코드
 * ex) JX
 * @see #mdlNm 차량 모델 명
 * ex) GV80
 * @see #xrclCtyNo 외장컬러상품번호
 * 외장색상 컬러코드
 * @see #mainVhclYn 기본차량여부
 * 주차량여부 (Y: 주차량, N: 주차량이 아님)
 * 차량 1대인 경우는 기본차량으로 설정하지 않아도 주차량으로 간주
 * @see #mainImgUri 메인주간차량이미지URI
 * @see #mainNgtImgUri 메인야간차량이미지URI
 * @see #mygImgUri MYG이미지URI
 *
 * @see #saleMdlCd 판매모델코드
 * @see #ieclCtyNo 내장컬러상품번호
 * @see #ctrctNo 계약번호
 *
 * @see #recvYmd 인도일자
 * 1:단독소유, 2:주계약자, 3:공동계약, 4:실운행자, 5:렌트리스
 *
 *
 * @see #delYn 삭제여부
 * Y:삭제된 차량 N: 삭제안된 차량
 * @see #csmrCarRelCd 고객차량관계코드
 * 1:단독소유, 2:주계약자, 3:공동계약, 4:실운행자, 5:렌트리스
 * @see #evCd 전기차 코드 구분
 * GN: 내연기관차, EV: 전기차
 * @see #spCd 스포츠패키지구분코드
 * 판매차량상품번호(SALE_CAR_CTY_NO)의 7번째 자리
 *   - GV70의 경우  GV70의 경우 M, K, N, L, Q, R, S, T
 *   - 스포츠패키지 차량 : SP
 *   - 스포츠패지기 미차량 : NO
 */
@Entity(indices = {@Index(value = {"vin","ctrctNo"}, unique = true)})
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class VehicleVO extends BaseData {
    public VehicleVO(){

    }

    @PrimaryKey(autoGenerate = true)
    private int _id;//app local db용도로 사용
    @Expose
    @SerializedName("custGbCd")
    private String custGbCd; //app local db용도로 사용


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
    @SerializedName("xrclCtyNo")
    private String xrclCtyNo;
    @Expose
    @SerializedName("mainVhclYn")
    private String mainVhclYn;
//    @Expose
//    @SerializedName("vhclImgUri")
//    private String vhclImgUri;
    @Expose
    @SerializedName("mainImgUri")
    private String mainImgUri;
    @Expose
    @SerializedName("mainNgtImgUri")
    private String mainNgtImgUri;
    @Expose
    @SerializedName("mygImgUri")
    private String mygImgUri;

    //아래는 계약차량용..
    @Expose
    @SerializedName("saleMdlCd")
    private String saleMdlCd;
    @Expose
    @SerializedName("ieclCtyNo")
    private String ieclCtyNo;
    @Expose
    @SerializedName("ctrctNo")
    private String ctrctNo;


    //아래는 GNS-1001에서만 취급하는 데이터
    @Expose
    @SerializedName("delYn")
    private String delYn;
    @Expose
    @SerializedName("csmrCarRelCd")
    private String csmrCarRelCd;
    @Expose
    @SerializedName("saleMdlNm")
    private String saleMdlNm;
    @Expose
    @SerializedName("saleMdlOptNm")
    private String saleMdlOptNm;
    @Expose
    @SerializedName("xrclCtyNm")
    private String xrclCtyNm;
    @Expose
    @SerializedName("usedCarYn")
    private String usedCarYn;
    @Expose
    @SerializedName("delExpYn")
    private String delExpYn;
    @Expose
    @SerializedName("delExpDay")
    private String delExpDay;

    //VOC-1001에서만 취급
    @Expose
    @SerializedName("recvYmd")
    private String recvYmd;

    //내장컬러명
    @Expose
    @SerializedName("ieclCtyNm")
    private String ieclCtyNm;

    //ev구분
    @Expose
    @SerializedName("evCd")
    private String evCd;

    //스포츠패키지구분
    @Expose
    @SerializedName("spCd")
    private String spCd;

    public boolean isEV(){
        return !StringUtil.isValidString(evCd).equalsIgnoreCase(VariableType.VEHICLE_CODE_EV);
    }

    public boolean isSP(){
        return StringUtil.isValidString(spCd).equalsIgnoreCase(VariableType.VEHICLE_CODE_SP);
    }
}
