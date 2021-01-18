package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.StringUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(primaryKeys = {"menuId","custGbCd"})
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class DownMenuVO extends BaseData {

    public DownMenuVO(String menuId, String custGbCd){
        this.menuId = StringUtil.isValidString(menuId);
        this.custGbCd = StringUtil.isValidString(custGbCd);
    }

    @NonNull
    @Expose
    @SerializedName("menuId")
    private String menuId;
    @Expose
    @SerializedName("menuNm")
    private String menuNm;
    @Expose
    @SerializedName("msgLnkCd")
    private String msgLnkCd;
    @Expose
    @SerializedName("wvYn")
    private String wvYn;
    @Expose
    @SerializedName("lnkUri")
    private String lnkUri;
    @Expose
    @SerializedName("nttOrd")
    private String nttOrd;

    @NonNull
    @Expose
    @SerializedName("custGbCd")
    private String custGbCd; //로컬db에서만 사용
}
