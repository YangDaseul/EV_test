package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 픽업앤충전 기본 신청 정보 VO
 *
 * @author ljeun
 * @see #productCode    상품 코드
 * @see #estimatedPaymentAmount 결제 요청 금액
 * @see #optionCount 옵션 리스트 갯수
 * @see #optionList 옵션 리스트(선택)
 * @since 2021. 4. 8.
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ReqOrderVO extends BaseData {

    @Expose
    @SerializedName("productCode")
    private String productCode;
    @Expose
    @SerializedName("estimatedPaymentAmount")
    private int estimatedPaymentAmount;

    @Expose
    @SerializedName("optionCount")
    private int optionCount;
    @Expose
    @SerializedName("optionList")
    private List<OptionVO> optionList;
}
