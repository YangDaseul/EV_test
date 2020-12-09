package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 설문항목
 * @author hjpark
 * @see #svyPrvsNo 설문항목번호
 * 항목번호 선택수
 *  대분류 1개 선택,  중분류 1개 선택, 소분류 다중선택
 * @see #svySetNo 설문세트번호
 * 문진 번호 (최대 3개까지)
 *
 * @see #svyMgmtNo 설문관리번호
 * @see #svyPrvsNm 설문항목명
 * @see #prvsSqn 항목순서
 *
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class SurveyItemVO extends BaseData {
    @Expose
    @SerializedName("svyPrvsNo")
    private String svyPrvsNo;
    @Expose
    @SerializedName("svySetNo")
    private String svySetNo;

    //REQ-1016에서 사용
    @Expose
    @SerializedName("svyMgmtNo")
    private String svyMgmtNo;
    @Expose
    @SerializedName("svyPrvsNm")
    private String svyPrvsNm;
    @Expose
    @SerializedName("prvsSqn")
    private String prvsSqn;
}
