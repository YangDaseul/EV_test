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
 * @brief 모빌리티케어 쿠폰 정보
 * @author hjpark
 * @see #wthrCd     날씨코드
 * @see #msgTypCd	메시지유형코드
 * TXT: 텍스트만, IMG :  이미지만
 * TXL :텍스트 + 링크  IML : 이미지 + 링크
 * TIL : 텍스트 + 이미지 + 링크
 * SYS: 고정된 메시지 유형(서버는 APP에 항목값만 전달)
 * @see #wthrCdNm	날씨코드명
 * @see #txtMsg	    텍스트메시지
 * @see #iconImgUri        이미지Uri
 * @see #lnkUseYn	링크사용여부
 * Y: 사용 N:미사용 (상세에서 보여주는 정보)
 * @see #lnkNm	    링크명
 * 링크버튼명(상세에서 보여주는 정보)
 * @see #lnkTypCd	링크유형코드
 * I : 대표앱 링크  O:외부링크(상세에서 보여주는 정보)
 * @see #lnkUri	    링크Uri
 */

@Entity
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class WeatherVO extends BaseData {

    public WeatherVO(){

    }

    @PrimaryKey (autoGenerate = true)
    int _id;
    @Expose
    @SerializedName("wthrCd")
    private String wthrCd;

    @Expose
    @SerializedName("msgTypCd")
    private String msgTypCd;
    @Expose
    @SerializedName("wthrCdNm")
    private String wthrCdNm;
    @Expose
    @SerializedName("txtMsg")
    private String txtMsg;
    @Expose
    @SerializedName("iconImgUri")
    private String iconImgUri;
    @Expose
    @SerializedName("lnkUseYn")
    private String lnkUseYn;
    @Expose
    @SerializedName("lnkNm")
    private String lnkNm;
    @Expose
    @SerializedName("lnkTypCd")
    private String lnkTypCd;
    @Expose
    @SerializedName("lnkUri")
    private String lnkUri;
}
