package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 소모폼 교환 정보
 * @author hjpark
 * @see #menuId 메뉴ID
 * @see #menuNm 메뉴명
 * @see #menuTypCd 메뉴유형코드
 * NA: native, WV:webview
 * @see #scrnTypCd 화면유형코드
 * PG: Page PU:popup
 * @see #lnkUri 링크URI
 */

@Entity
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class DownMenuVO extends BaseData {

    public DownMenuVO(){

    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("menuId")
    private String menuId;
    @Expose
    @SerializedName("menuNm")
    private String menuNm;
    @Expose
    @SerializedName("qckMenuDivCd")
    private String qckMenuDivCd;
    @Expose
    @SerializedName("wvYn")
    private String wvYn;
    @Expose
    @SerializedName("lnkUri")
    private String lnkUri;
    @Expose
    @SerializedName("nttOrd")
    private String nttOrd;

    @Expose
    @SerializedName("custGbCd")
    private String custGbCd; //로컬db에서만 사용
}
