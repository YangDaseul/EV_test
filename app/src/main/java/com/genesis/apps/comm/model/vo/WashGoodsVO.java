package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 소낙스 세차이용권 정보
 * @author hjpark
 * @see #godsSeqNo 상품일련번호
 * @see #cmpyCd 업체코드
 * @see #godsNm 상품명
 * @see #dsctNm 할인명
 * @see #godsImgUri 상품이미지URI
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class WashGoodsVO extends BaseData {
    @Expose
    @SerializedName("godsSeqNo")
    private String godsSeqNo;
    @Expose
    @SerializedName("cmpyCd")
    private String cmpyCd;
    @Expose
    @SerializedName("godsNm")
    private String godsNm;
    @Expose
    @SerializedName("dsctNm")
    private String dsctNm;
    @Expose
    @SerializedName("godsImgUri")
    private String godsImgUri;
}
