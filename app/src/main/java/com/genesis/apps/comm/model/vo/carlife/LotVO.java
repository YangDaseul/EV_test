package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 충전 버틀러 위치 정보 VO
 * @author ljeun
 * @since 2021. 4. 8.
 *
 * @see #locationType   위치 구분 코드
 * 충전버틀러는 STRT 고정
 *
 * @see #inOutType  내외부 구분 코드(선택)
 * 충전 버틀러는 설정하지 않음
 *
 * @see #latitude   위도
 * @see #longitude  경도
 * @see #address    주소
 * @see #addressDetail  상세주소(선택)
 * @see #buildingName   빌딩명(선택)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class LotVO extends BaseData {

    @Expose
    @SerializedName("locationType")
    private String locationType;
    @Expose
    @SerializedName("inOutType")
    private String inOutType;
    @Expose
    @SerializedName("latitude")
    private double latitude;
    @Expose
    @SerializedName("longitude")
    private double longitude;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("addressDetail")
    private String addressDetail;
    @Expose
    @SerializedName("buildingName")
    private String buildingName;
}
