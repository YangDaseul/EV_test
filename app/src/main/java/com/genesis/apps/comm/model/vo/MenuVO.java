package com.genesis.apps.comm.model.vo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 메뉴 정보
 * @author hjpark
 * @see #code 메뉴코드
 * @see #name 메뉴명
 */
@Entity
public @Data
class MenuVO extends BaseData {
    @PrimaryKey(autoGenerate = true)
    private int _id;

    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("name")
    private String name;

    @Ignore
    private Class activity;
}
