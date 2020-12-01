package com.genesis.apps.comm.model.vo.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @brief 차량 정보
 * @see #carId 차량 고유 id
 * @see #carNickname 사용자가 커넥티드 서비스 앱에서 설정한 닉네임
 * @see #carType 차량 타입
 * (GN:가솔린, EV:전기, HEV:하이브리드, PHEV: 플러그인하이브리드, FCEV:수소전기)
 * @see #carName 차종명(차종코드)
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class CarVO extends BaseData {
    @Expose
    @SerializedName("carId")
    private String carId;
    @Expose
    @SerializedName("carNickname")
    private String carNickname;
    @Expose
    @SerializedName("carType")
    private String carType;
    @Expose
    @SerializedName("carName")
    private String carName;

    @Expose
    @SerializedName("vin")
    private String vin;
    @Expose
    @SerializedName("pinchecker")
    private boolean pinchecker;
    @Expose
    @SerializedName("createDate")
    private String createDate;
    @Expose
    @SerializedName("vehicleName")
    private String vehicleName;
}
