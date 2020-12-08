package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 설문정보
 * @author hjpark
 * @see #svyMgmtNo 설문고한리번호
 * @see #svyInpSbc 입력내용
 * @see #svyPrvsList 설문항목리스트
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class SurveyVO extends BaseData {
    @Expose
    @SerializedName("svyMgmtNo")
    private String svyMgmtNo;
    @Expose
    @SerializedName("svyInpSbc")
    private String svyInpSbc;
    @Expose
    @SerializedName("svyPrvsList")
    private List<SurveyItemVO> svyPrvsList;
}
