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
 * 1000 : 이벤트카테고리  그외 0000 (기획 정의 요청 필요)
 *  - 이벤트인 경우 화면에 "이벤트"버튼 활성화
 * @see #ttImgUri 제목이미지uri
 * @see #dtlViewCd 상세뷰코드
 * 1000: 통이미지  2000: 이미지+텍스트  3000: HTML
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
    @SerializedName("ttImgUri")
    private String ttImgUri;
    @Expose
    @SerializedName("dtlViewCd")
    private String dtlViewCd;
}
