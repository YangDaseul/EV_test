package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 인사이트 이미지 VO
 * @author hjpark
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ImageVO extends BaseData {
    @Expose
    @SerializedName("image")
    private String image;
}
