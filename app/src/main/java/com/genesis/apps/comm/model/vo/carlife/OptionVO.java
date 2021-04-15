package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 충전 버틀러 상품 옵션 정보 VO
 * @author ljeun
 * @since 2021. 4. 5.
 *
 * @see #optionCode 옵션 상품 코드
 * @see #optionType 옵션 타입
 * OT01: 기본형(= 탁속)
 * 0T02: 추가형(= 세차 서비스)
 *
 * @see #optionName 옵션 상품명
 * @see #optionPrice   옵션 상품 금액
 *
 * @see #optionApplyCount   옵션 선택 수량
 * default = 1
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class OptionVO extends BaseData {
    @Expose
    @SerializedName("optionCode")
    private String optionCode;
    @Expose
    @SerializedName("optionType")
    private String optionType;
    @Expose
    @SerializedName("optionName")
    private String optionName;
    @Expose
    @SerializedName("optionPrice")
    private int optionPrice;
    @Expose
    @SerializedName("optionApplyCount")
    private int optionApplyCount;
}
