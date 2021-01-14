package com.genesis.apps.comm.model.vo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.StringUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @brief BTO 링크 정보
 * @author hjpark
 * @see #mdlNm 모델명
 * @see #htmlFilUri HTML파일Uri
 */
@Entity
public @Data
class BtoVO extends BaseData {

    public BtoVO(String mdlNm){
        this.mdlNm = StringUtil.isValidString(mdlNm);
    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("mdlNm")
    private String mdlNm;
    @Expose
    @SerializedName("htmlFilUri")
    private String htmlFilUri;
}
