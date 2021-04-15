package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 충전 버틀러 신청 이력 정보 VO
 * @author ljeun
 * @since 2021. 4. 15.
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class BookingVO extends BaseData {
    @Expose
    @SerializedName("orderId")
    private String orderId;
    @Expose
    @SerializedName("category")
    private String category;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("statusNm")
    private String statusNm;
    @Expose
    @SerializedName("bookingDt")
    private String bookingDt;
    @Expose
    @SerializedName("finishDt")
    private String finishDt;
    @Expose
    @SerializedName("carNo")
    private String carNo;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("addressDetail")
    private String addressDetail;
    @Expose
    @SerializedName("buildingName")
    private String buildingName;
    @Expose
    @SerializedName("productName")
    private String productName;
    @Expose
    @SerializedName("optionCount")
    private int optionCount;
    @Expose
    @SerializedName("optionNameList")
    private List<String> optionNameList;
    @Expose
    @SerializedName("serviceViewLink")
    private String serviceViewLink;
}
