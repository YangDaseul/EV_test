package com.genesis.apps.comm.model.weather;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief MCS-U 지점 상세 날씨 정보 제공 요청
 * @see #weatherZoneList 날씨 조회 지역 리스트 (반경 기준)
 * EX)0Az0Az01Az
 * @see #requestID 단말 요청 unique ID 값
 * [생성 규칙] 랜덤 string 값 (10자리)
 */
@EqualsAndHashCode(callSuper=false)
public @Data class WeatherPointReqVO extends BaseData {

    @Expose
    @SerializedName("weatherZoneList")
    private List<WeatherZone> weatherZoneList;
    @Expose
    @SerializedName("requestID")
    private String requestID;
    /**
     * @see #locationGPS 위치 좌표
     * @see #locationCode 지역 코드(국내)
     * ex) "11110987"
     * @see #waypointID altitude(전지향 공용화를 위한 추가)
     * Indicates whether this PoiInfo is a waypoint or
     * final destination
     * -2 : specific location
     * -1 : Current Position
     * 0 : Final destination
     * 1 : first waypoint (POI that the driver visits at
     * first)
     * 2 : second waypoint (POI that the driver visits
     * at second)
     */
    public @Data class WeatherZone extends BaseData {
        @Expose
        @SerializedName("locationGPS")
        private Coord locationGPS;
        @Expose
        @SerializedName("locationCode")
        private String locationCode;
        @Expose
        @SerializedName("waypointID")
        private int waypointID;
    }
    /**
     * @brief 위치 좌표
     * @see #lat Latitude Range is between -90~90 위도
     * @see #lon Longitude Range between -180~180 경도
     * @see #alt altitude(전지향 공용화를 위한 추가)
     * @see #type
     * 0 : WGS84,
     * 1 : Bessel,
     * 2 : Coord Shift (for China)
     */
    public @Data class Coord extends BaseData {
        @Expose
        @SerializedName("lat")
        private double lat;
        @Expose
        @SerializedName("lon")
        private double lon;
        @Expose
        @SerializedName("alt")
        private double alt;
        @Expose
        @SerializedName("type")
        private int type;
    }

}
