package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief service + 정비소예약내역 - 수리 전중후이미지조회
 * @author hjpark
 * @see #imgUri 이미지Url
 * @see #thumImgUri 썸네일 이미지
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class RepImageVO extends BaseData {
    @Expose
    @SerializedName("imgUri")
    private String imgUri;
    @Expose
    @SerializedName("thumImgUri")
    private String thumImgUri;
}
