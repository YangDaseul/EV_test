package com.genesis.apps.comm.model.weather;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @brief MCS-U 지점 상세 날씨
 * @see #requestID 단말 요청 request ID
 * EX)0Az0Az01Az
 * @see #time 서버 제공 utc 시간
 * @see #rtnCode
 * The list of RtnCodes (RtnCode will be
 * delivered from CP servers in normal cases,
 * but in other cases MCP server generates
 * it's RtnCode)
 * 200:OK
 * 204:No Contents
 * 4XX: Following general HTTP 4xx errors
 * 5XX: Following general HTTP 5xx errors
 * 1100: MCP Internal Error
 * 1200: MCP external Error(CP)
 * @see #weatherInfoList 날씨정보
 *
 */
@EqualsAndHashCode(callSuper=false)
public @Data class WeatherPointResVO extends BaseData {

    @Expose
    @SerializedName("requestID")
    private String requestID;
    @Expose
    @SerializedName("time")
    private UtcOffsetTime time;
    @Expose
    @SerializedName("rtnCode")
    private int rtnCode;
    @Expose
    @SerializedName("weatherInfoList")
    private List<WeatherInfo> weatherInfoList;

    /**
     * @brief 날씨정보
     * @see #locationCode 지역 코드(국내)
     * ex) "11110987"
     * @see #cityName City 명 (대소문자 구분 필요) ※ 유럽은 언어 별로 도시명이 상이
     * ex)“Manhattan”
     * @see #waypointID
     * Indicates whether this PoiInfo is a waypoint
     * or final destination
     * -2 : specific location
     * -1 : Current Position
     * 0 : Final destination
     * 1 : first waypoint (POI that the driver visits
     * at first)
     * 2 : second waypoint (POI that the driver
     * visits at second)
     * ex)“-1”
     * @see #weatherDetail 시간 날씨 상세 정보 (현재, 3시간, 6시간)
     * @see #weatherInfoDay 일별 날씨 상세 정보 (오늘 포함 3일 날씨)
     */
    public @Data class WeatherInfo extends BaseData {
        @Expose
        @SerializedName("locationCode")
        private String locationCode;
        @Expose
        @SerializedName("cityName")
        private String cityName;
        @Expose
        @SerializedName("waypointID")
        private int waypointID;
        @Expose
        @SerializedName("weatherDetail")
        private WeatherDetail weatherDetail;
        @Expose
        @SerializedName("weatherInfoDay")
        private WeatherInfoDay weatherInfoDay;
    }

    /**
     * @brief 시간 날씨 상세 정보 (현재, 3시간, 6시간)
     * @see #indexTime 예보 기준 시간
     * (0: 현재 날씨, 3: 3시간 예측 날씨, 6: 6시간 예측 날씨)
     * ex) 0,3,6
     * @see #temperature 현재기온
     * @see #icon  0:맑음, 1:흐림, 2:구름 조금 등..
     * @see #airQuality  대기질 지수
     * 1: 좋음
     * 2: 보통
     * 3: 나쁨
     * 4: 매우 나쁨
     * @see #rainfallProbability 강수확률
     * @see #weatherTime 기상예보시간(local time)
     * @see #humidity 습도
     * @see #rainfallAmount 강수량 (단위: mm)
     * @see #carWash 세차지수
     * 0 : 비/눈 예보로 세차는 미루세요.
     * 1 : 미세먼지로 세차는 미루세요.
     * 2 : 세차 효과가 길지는 않아요.
     * 3 : 세차는 얼어붙지 않도록 하세요.
     * 4 : 세차하기 아주 좋은 날이에요.
     * @see #dayNight  0: 낮, 1: 밤 (Default : 0)
     *
     */
    public @Data class WeatherDetail extends BaseData {
        @Expose
        @SerializedName("indexTime")
        private int indexTime;
        @Expose
        @SerializedName("temperature")
        private TemperatureType temperature;
        @Expose
        @SerializedName("icon")
        private int icon;
        @Expose
        @SerializedName("airQuality")
        private int airQuality;
        @Expose
        @SerializedName("rainfallProbability")
        private double rainfallProbability;
        @Expose
        @SerializedName("weatherTime")
        private String weatherTime;
        @Expose
        @SerializedName("humidity")
        private int humidity;
        @Expose
        @SerializedName("rainfallAmount")
        private double rainfallAmount;
        @Expose
        @SerializedName("carWash")
        private int carWash;
        @Expose
        @SerializedName("dayNight")
        private int dayNight;
    }

    /**
     * @brief weatherInfoDay 일별 날씨 상세 정보 (오늘 포함 3일 날씨)
     * @see #weatherTime 기상 예보 시간(local time)
     * ex)  "20190823164800"
     * @see #maxTemp 최고 기온
     * @see #minTemp 최저 기온
     * @see #icon  0:맑음, 1:흐림, 2:구름 조금 등..
     * @see #sunriseTime  일출시간
     * @see #sunsetTime  일몰시간
     * @see #dayNight  0: 낮, 1: 밤 (Default : 0)
     *
     */
    public @Data class WeatherInfoDay extends BaseData {
        @Expose
        @SerializedName("weatherTime")
        private String weatherTime;
        @Expose
        @SerializedName("maxTemp")
        private TemperatureType maxTemp;
        @Expose
        @SerializedName("minTemp")
        private TemperatureType minTemp;
        @Expose
        @SerializedName("icon")
        private int icon;
        @Expose
        @SerializedName("rainfallProbability")
        private double rainfallProbability;
        @Expose
        @SerializedName("sunriseTime")
        private String sunriseTime;
        @Expose
        @SerializedName("sunsetTime")
        private String sunsetTime;
        @Expose
        @SerializedName("dayNight")
        private int dayNight;
    }



    /**
     * @Brief UTC 타임 정의
     * @see #utc DateTime ex) "20100720103015"
     * @see #offset ex) Min Value : -12, Max Value : 12
     */
    public @Data class UtcOffsetTime extends BaseData {
        @Expose
        @SerializedName("utc")
        private String utc;
        @Expose
        @SerializedName("offset")
        private int offset;
    }


    /**
     * @Brief 온도 타입
     * @see #value 온도수치 ex) 22.5
     * @see #unit 온도타입 ex) 0:섭씨 , 1:화씨(미제공)
     *
     */
    public @Data class TemperatureType extends BaseData {
        @Expose
        @SerializedName("value")
        private double value;
        @Expose
        @SerializedName("unit")
        private int unit;
    }
}
