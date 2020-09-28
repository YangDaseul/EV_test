package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief admin/BANNER 메시지 정보
 * @author hjpark
 * @see #msgTypCd   메시지유형코드
 * TXT : 텍스트만, IMG :  이미지만
 * TXL : 텍스트 + 링크  IML : 이미지 + 링크
 * TIL : 텍스트 + 이미지 + 링크
 * SYS: 고정된 메시지 유형(서버는 APP에 항목값만 전달)
 * @see #ttl	    제목
 * @see #txtMsg	    텍스트메시지
 * @see #imgUri	    이미지Uri
 * @see #lnkNm	    링크명
 * 링크버튼명(상세에서 보여주는 정보)
 * @see #lnkTypCd	링크유형코드
 * I : 대표앱 링크  O:외부링크(상세에서 보여주는 정보)
 * @see #lnkUri	    링크Uri
 * @see #bnrNm 배너명
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class MessageVO extends BaseData {
    @Expose
    @SerializedName("msgTypCd")
    private String msgTypCd;
    @Expose
    @SerializedName("ttl")
    private String ttl;
    @Expose
    @SerializedName("txtMsg")
    private String txtMsg;
    @Expose
    @SerializedName("imgUri")
    private String imgUri;
    @Expose
    @SerializedName("lnkNm")
    private String lnkNm;
    @Expose
    @SerializedName("lnkTypCd")
    private String lnkTypCd;
    @Expose
    @SerializedName("lnkUri")
    private String lnkUri;
    @Expose
    @SerializedName("bnrNm")
    private String bnrNm;
}
