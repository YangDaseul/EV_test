package com.genesis.apps.comm.model.api.roadwin;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file CheckPrice
 * @Brief 가격정보
 */
public class CheckPrice extends BaseData {
    public static final String RST_CODE_POSSIBLE = "0";
    public static final String RST_CODE_IMPOSSIBLE = "1";
    public static final String RST_SVC_TYPE_D = "D";//대리
    public static final String RST_SVC_TYPE_T = "T";//탁송

    /**
     * @author hjpark
     * @brief CheckPrice 요청 항목
     * @see #latitude_start 출발 위도
     * @see #longitude_start 출발 경도
     * @see #latitude_my 고객 위치 위도
     * @see #longitude_my 고객 위치 경도
     * @see #latitude_layover_1 경유 1 위도
     * @see #longitude_layover_1 경유 1 경도
     * @see #latitude_layover_2 경유 2 위도
     * @see #longitude_layover_2 경유 2 경도
     * @see #latitude_end 도착 위도
     * @see #longitude_end 도착 경도
     * @see #svc_type 서비스 코드 D: 대리 T : 탁송

     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("latitude_start")
        private String latitude_start;
        @Expose
        @SerializedName("longitude_start")
        private String longitude_start;
        @Expose
        @SerializedName("latitude_my")
        private String latitude_my;
        @Expose
        @SerializedName("longitude_my")
        private String longitude_my;
        @Expose
        @SerializedName("latitude_layover_1")
        private String latitude_layover_1;
        @Expose
        @SerializedName("longitude_layover_1")
        private String longitude_layover_1;
        @Expose
        @SerializedName("latitude_layover_2")
        private String latitude_layover_2;
        @Expose
        @SerializedName("longitude_layover_2")
        private String longitude_layover_2;
        @Expose
        @SerializedName("latitude_end")
        private String latitude_end;
        @Expose
        @SerializedName("longitude_end")
        private String longitude_end;
        @Expose
        @SerializedName("svc_type")
        private String svc_type;


        public Request(String latitude_start,
                       String longitude_start,
                       String latitude_my,
                       String longitude_my,
                       String latitude_layover_1,
                       String longitude_layover_1,
                       String latitude_layover_2,
                       String longitude_layover_2,
                       String latitude_end,
                       String longitude_end,
                       String svc_type) {
            this.latitude_start = latitude_start;
            this.longitude_start = longitude_start;
            this.latitude_my = latitude_my;
            this.longitude_my = longitude_my;
            this.latitude_layover_1 = latitude_layover_1;
            this.longitude_layover_1 = longitude_layover_1;
            this.latitude_layover_2 = latitude_layover_2;
            this.longitude_layover_2 = longitude_layover_2;
            this.latitude_end = latitude_end;
            this.longitude_end = longitude_end;
            this.svc_type = svc_type;
        }
    }

    /**
     * @author hjpark
     * @brief CheckPrice 응답 항목
     *
     * @Ssee #price 가격
     * @Ssee #km 거리
     * @Ssee #rst_code 0 정상, 1 실패
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseData {
        @Expose
        @SerializedName("price")
        private String price;
        @Expose
        @SerializedName("km")
        private String km;
        @Expose
        @SerializedName("rst_code")
        private String rst_code;
    }
}
