package com.genesis.apps.comm.model.vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 사용자 정보
 * @author hjpark
 */

@Entity
public @Data
class UserVO extends BaseData {

    @PrimaryKey
    private int _id=1;

    @Expose
    @SerializedName("custNo")
    private String custNo;
    @Expose
    @SerializedName("custGbCd")
    private String custGbCd;
    @Expose
    @SerializedName("custMgmtNo")
    private String custMgmtNo;
    @Expose
    @SerializedName("custNm")
    private String custNm;
    @Expose
    @SerializedName("celphNo")
    private String celphNo;
}
