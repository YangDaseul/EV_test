package com.genesis.apps.comm.model.vo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @brief 패밀리앱 링크 정보
 * @author hjpark
 * @see #appNm APP명
 * @see #nttOrd 표기순서
 * @see #imgUri 이미지Uri
 * @see #dnldUri 다운로드Uri
 */
@Entity
public @Data
class FamilyAppVO extends BaseData {

    public FamilyAppVO(){

    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("appNm")
    private String appNm;
    @Expose
    @SerializedName("nttOrd")
    private String nttOrd;
    @Expose
    @SerializedName("imgUri")
    private String imgUri;
    @Expose
    @SerializedName("dnldUri")
    private String dnldUri;
}
