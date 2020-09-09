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
 * @see #dtlSeqNo 상세일련번호
 * @see #ctntsTypCd 컨텐츠유형코드
 * 1000: 이미지  2000: 이미지+텍스트  3000: HTML
 * @see #htmlFilUri HMTL파일URI
 * HTML인 경우
 * - HTML 링크 : 상대경로
 * - 물리 파일은 HTML/이미지 파일은 NAS 저장
 * @see #imgFilUri 이미지파일URI
 * 컨텐츠 내의 이미지 파일들의 URI
 * - 물리 파일은 HTML/이미지 파일은 NAS 저장
 *
 * @see #imgLnkCd 이미지링크코드
 * I : 대표앱 링크  O:외부링크
 * @see #imgLnkUri 이미지링크URI
 * 이미지 클릭시의 이동 URI (IMG_LNK_CD='O' 외부 링크
 * @see #textCont 텍스트내용
 * @see #nttOrd 표기순서
 * 상세뷰코드가
 *  - 1000: 통이미지 ==> 상세이미지 1개
 *  - 2000: 이미지+텍스트 ==> 1개 이상
 *  - 3000: HTML ==> 1개 / html내의 링크 상대경로, html, 이미지 동일경로(검토필요)
 *
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ContentsDetailVO extends BaseData {
    @Expose
    @SerializedName("dtlSeqNo")
    private String dtlSeqNo;
    @Expose
    @SerializedName("ctntsTypCd")
    private String ctntsTypCd;
    @Expose
    @SerializedName("htmlFilUri")
    private String htmlFilUri;
    @Expose
    @SerializedName("imgFilUri")
    private String imgFilUri;
    @Expose
    @SerializedName("imgLnkCd")
    private String imgLnkCd;
    @Expose
    @SerializedName("imgLnkUri")
    private String imgLnkUri;
    @Expose
    @SerializedName("textCont")
    private String textCont;
    @Expose
    @SerializedName("nttOrd")
    private String nttOrd;
}
