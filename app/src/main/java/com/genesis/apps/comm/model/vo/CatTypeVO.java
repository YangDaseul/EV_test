package com.genesis.apps.comm.model.vo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.StringUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author J
 * @brief 컨텐츠 분류 카테고리
 * @see #cd 코드
 * @see #cdNm 코드명
 */

@Entity
@EqualsAndHashCode(callSuper = false)
public @Data
class CatTypeVO extends BaseData {

//    public CatTypeVO(String cd){
//        this.cd = StringUtil.isValidString(cd);
//    }
    public CatTypeVO(String cd, String cdNm){
        this.cd = StringUtil.isValidString(cd);
        this.cdNm = StringUtil.isValidString(cdNm);
    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("cd")
    private String cd;
    @Expose
    @SerializedName("cdNm")
    private String cdNm;

}
