package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 카라이프 결제 요청 폼 데이터 VO
 *
 * @author ljeun
 * @since 2021. 4. 29.
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class PymtFormVO extends BaseData {
    @Expose
    @SerializedName("chnlCd")
    private String chnlCd;
    @Expose
    @SerializedName("svrEncKey")
    private String svrEncKey;
    @Expose
    @SerializedName("chnlMbrIdfKey")
    private String chnlMbrIdfKey;
    @Expose
    @SerializedName("bpayCardId")
    private String bpayCardId;
    @Expose
    @SerializedName("srcCoCd")
    private String srcCoCd;
    @Expose
    @SerializedName("chnlMid")
    private String chnlMid;
    @Expose
    @SerializedName("mOid")
    private String mOid;
    @Expose
    @SerializedName("prdtNm")
    private String prdtNm;
    @Expose
    @SerializedName("stlmAmt")
    private int stlmAmt;
    @Expose
    @SerializedName("vlsp")
    private int vlsp;
    @Expose
    @SerializedName("srtx")
    private int srtx;
    @Expose
    @SerializedName("srfe")
    private int srfe;
    @Expose
    @SerializedName("nonMptx")
    private int nonMptx;
    @Expose
    @SerializedName("isMth")
    private int isMth;
    @Expose
    @SerializedName("closeUrl")
    private String closeUrl;
    @Expose
    @SerializedName("redirectUrl")
    private String redirectUrl;
    @Expose
    @SerializedName("userAgent")
    private String userAgent;
    @Expose
    @SerializedName("dvceCd")
    private String dvceCd;
    @Expose
    @SerializedName("deceUuid")
    private String deceUuid;
    @Expose
    @SerializedName("ediDate")
    private String ediDate;
    @Expose
    @SerializedName("filler")
    private String filler;
    @Expose
    @SerializedName("hashVal")
    private String hashVal;
    @Expose
    @SerializedName("formUrl")
    private String formUrl;
}
