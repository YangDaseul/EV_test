package com.genesis.apps.comm.model.vo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @brief 알람메시지 대분류 카테고리
 * @see #cd 코드
 * @see #cdNm 코드명
 */

@Entity
@EqualsAndHashCode(callSuper = false)
public @Data
class AlarmMsgTypeVO extends BaseData {
    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("cd")
    private String cd;
    @Expose
    @SerializedName("cdNm")
    private String cdNm;

    public AlarmMsgTypeVO(){

    }
}
