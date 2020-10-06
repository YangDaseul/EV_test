package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.room.QuickMenuDao;
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
 * 제네시스앱 : 메뉴관리의 메뉴ID
 * 수동등록시의 임의 ID 입력
 * @see #qckMenuDivCd 퀵메뉴구분코드
 * IM:제네시스앱메뉴, OW:외부링크메뉴
 * @see #menuNm 메뉴명
 * @see #nttOrd 표기순서
 * @see #wvYn 웹뷰여부
 * Y:웹뷰, N:아님
 * @see #lnkUri 링크URI
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class QuickMenuVO extends BaseData {

    public QuickMenuVO(){

    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("menuId")
    private String menuId;

    @Expose
    @SerializedName("qckMenuDivCd")
    private String qckMenuDivCd;
    @Expose
    @SerializedName("menuNm")
    private String menuNm;
    @Expose
    @SerializedName("nttOrd")
    private String nttOrd;
    @Expose
    @SerializedName("wvYn")
    private String wvYn;
    @Expose
    @SerializedName("lnkUri")
    private String lnkUri;

    @Expose
    @SerializedName("custGbCd")
    private String custGbCd; //로컬db에서만 사용
}
