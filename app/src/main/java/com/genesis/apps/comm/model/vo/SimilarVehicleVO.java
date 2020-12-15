package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @Brief 유사재고차량정보
 * @see #vhclCd     차량코드        ex) JJJS4CAT1B08UYHNNB
 * @see #smlrRto	유사도          ex) 97
 * @see #vhclNm	    차종명          ex) G80, GV80
 * @see #mdlNm	    모델명          ex) 2.5 가솔린 2WD 4도어 18인치
 * @see #etrrClrNm	외장색상명      ex) 우유니 화이트
 * @see #itrrClrNm	내장색상명      ex) 블랙모노톤(블랙시트)
 * @see #otpnNm	    옵션명          ex) 파노라마 선루프, 파퓰러 패키지
 * @see #vhclImgUri	차량이미지경로   ex) /content/dam/genesis_owners/kr/image/shopping/jj/d/jj_uyh_d.png.thumb.1280.720.png
 * @see #celphNo    카마스터휴대전화번호
 * 판매(계출)정보에 사원휴대전호번호가 있는 경우는 NULL 값
 * 포맷 :  010-1234-1234
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class SimilarVehicleVO extends BaseData {
    @Expose
    @SerializedName("vhclCd")
    private String vhclCd;
    @Expose
    @SerializedName("vhclNm")//
    private String vhclNm;
    @Expose
    @SerializedName("mdlNm")
    private String mdlNm;
    @Expose
    @SerializedName("etrrClrNm")
    private String etrrClrNm;
    @Expose
    @SerializedName("itrrClrNm")
    private String itrrClrNm;
    @Expose
    @SerializedName("otpnNm")
    private String otpnNm;
    @Expose
    @SerializedName("vhclImgUri")
    private String vhclImgUri;
    @Expose
    @SerializedName("smlrRto")
    private String smlrRto;
    @Expose
    @SerializedName("celphNo")
    private String celphNo;
}
