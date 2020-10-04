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
 * @see #upMenuId 상위메뉴ID
 * IM:제네시스앱메뉴, OW:외부링크메뉴
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
class FloatingMenuVO extends BaseData {

    public FloatingMenuVO(){

    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("menuId")
    private String menuId;
    @Expose
    @SerializedName("upMenuId")
    private String upMenuId;
    @Expose
    @SerializedName("menuNm")
    private String menuNm;
    @Expose
    @SerializedName("menuTypCd")
    private String menuTypCd;
    @Expose
    @SerializedName("scrnTypCd")
    private String scrnTypCd;
    @Expose
    @SerializedName("lnkUri")
    private String lnkUri;
    @Expose
    @SerializedName("type")
    private String type; //로컬db에서만 사용
}
