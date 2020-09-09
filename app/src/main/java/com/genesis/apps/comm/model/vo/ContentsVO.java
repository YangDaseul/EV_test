package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 컨텐츠목록정보
 * @author hjpark
 * @see #listSeqNo 목록일련번호
 * @see #catCd 카테고리코드
 * @see #ttl 제목
 * @see #ttImgUri 제목이미지uri
 *
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ContentsVO extends BaseData {
    @Expose
    @SerializedName("listSeqNo")
    private String listSeqNo;
    @Expose
    @SerializedName("catCd")
    private String catCd;
    @Expose
    @SerializedName("ttl")
    private String ttl;
    @Expose
    @SerializedName("ttImgUri")
    private String ttImgUri;
}
