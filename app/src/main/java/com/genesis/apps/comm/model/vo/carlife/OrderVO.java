package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.carlife.OptionVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 픽업앤충전 기본 신청 정보 VO
 * @author ljeun
 * @since 2021. 4. 8.
 *
 * @see #productCode    상품 코드
 * @see #estimatedPaymentAmount 결제 요청 금액
 * @see #paymentAmount 최종 결제 금액
 * @see #productName 상품명
 * @see #productPrice 상품 금액
 * @see #productSalePrice   상품판매금액 = 상품 금액 - 상품 할인금액
 * @see #optionCount 옵션 리스트 갯수
 * @see #optionList 옵션 리스트(선택)
 * @see #membershipCount    포인트 멤버십 적용 갯수
 * @see #membershipList 포인트 멤버십 적용 리스트
 * @see #totalServiceCost   총 서비스 금액 = 상품금액 + 옵션금액
 * @see #totalDcAmount  총 할인 금액 = 기본할인 + 쿠폰할인
 * @see #totalUsePoint  총 포인트 차감 금액
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class OrderVO extends BaseData {

    public OrderVO(String productCode, int estimatedPaymentAmount, int optionCount, List<OptionVO> optionList) {
        this.productCode = productCode;
        this.estimatedPaymentAmount = estimatedPaymentAmount;
        this.optionCount = optionCount;
        this.optionList = optionList;
    }

    @Expose
    @SerializedName("productCode")
    private String productCode;
    @Expose
    @SerializedName("estimatedPaymentAmount")
    private int estimatedPaymentAmount;
    @Expose
    @SerializedName("paymentAmount")
    private int paymentAmount;
    @Expose
    @SerializedName("productName")
    private String productName;
    @Expose
    @SerializedName("productPrice")
    private int productPrice;
    @Expose
    @SerializedName("productSalePrice")
    private int productSalePrice;

    @Expose
    @SerializedName("optionCount")
    private int optionCount;
    @Expose
    @SerializedName("optionList")
    private List<OptionVO> optionList;

    @Expose
    @SerializedName("membershipCount")
    private int membershipCount;
    @Expose
    @SerializedName("membershipList")
    private List<MembershipVO> membershipList;

    @Expose
    @SerializedName("totalServiceCost")
    private int totalServiceCost;
    @Expose
    @SerializedName("totalDcAmount")
    private int totalDcAmount;
    @Expose
    @SerializedName("totalUsePoint")
    private int totalUsePoint;
}
