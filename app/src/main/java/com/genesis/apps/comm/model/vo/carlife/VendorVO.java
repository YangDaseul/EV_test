package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 카라이프-충전버틀러 서비스 제공 업체 정보 VO
 *
 * @author ljeun
 * @since 2021. 4. 14.
 *
 * @see #vendorCode 제공업체코드
 * INSTA: 인스타워시
 * ROADW: 로드윈
 * AUTON: 오토엔
 * STRFF: 에스트래픽
 * @see #vendorName 업체명
 * @see #vendorTelNo    전화번호
 * @see #vendorAddress  위치
 * @see #vendorHomepageUrl  홈페이지
 * @see #vendorCSTelNo  고객센터 전화번호
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class VendorVO extends BaseData {
    @Expose
    @SerializedName("vendorCode")
    private String vendorCode;
    @Expose
    @SerializedName("vendorName")
    private String vendorName;
    @Expose
    @SerializedName("vendorTelNo")
    private String vendorTelNo;
    @Expose
    @SerializedName("vendorAddress")
    private String vendorAddress;
    @Expose
    @SerializedName("vendorHomepageUrl")
    private String vendorHomepageUrl;
    @Expose
    @SerializedName("vendorCSTelNo")
    private String vendorCSTelNo;
}
