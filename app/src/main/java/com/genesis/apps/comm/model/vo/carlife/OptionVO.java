package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 픽업앤충전 상품 옵션 정보 VO
 * @author ljeun
 * @since 2021. 4. 5.
 *
 * @see #optionCode 옵션 상품 코드
 * OI20210330000001 세차 서비스
 * OI20210330000002 픽업(=탁송)
 * @see #optionType 옵션 타입
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

    public OptionVO(String optionCode, int optionApplyCount){
        this.optionCode = optionCode;
        this.optionApplyCount = optionApplyCount;
    }

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
