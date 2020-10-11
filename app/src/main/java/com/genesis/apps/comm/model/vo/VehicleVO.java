package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author hjpark
 * @Brief 차량정보 (소유차량, 미로그인시, 계약차량 등)
 * 미로그인/미회원의 경우 아래 4개 정보만 의미가 있음
 * {@link #mdlCd#mdlNm#xrclCtyNo#vhclImgUri}
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
 * @see #vhclImgUri 차량이미지URI
 * 고객이 차량을 등록한 경우는 TGRA_CAR_OWN_REL의 VHCL_IMG_FIL_NM
 * 등록된 이미지가 없는 경우는 TGRA_VHCL_IMG_INFO 참조
 *
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
 */
@Entity
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
    @SerializedName("carRgstNo")//
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
    @Expose
    @SerializedName("vhclImgUri")
    private String vhclImgUri;

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

}
