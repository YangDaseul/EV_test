package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.weather.T1H;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_LGN_0005
 * @Brief 날씨예보
 * @author hjpark
 */
public class LGN_0005 extends BaseData {
    /**
     * @brief LGN_0005의 요청 항목
     * @author hjpark
     * @see #nx 단말 경도 좌표
     * 단말 위치정보 X 좌표 (경도)
     * @see #ny 단말 위도 좌표
     * 단말 위치정보 Y 좌표 (위도)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest{
        @Expose
        @SerializedName("nx")
        private String nx;
        @Expose
        @SerializedName("ny")
        private String ny;
        public Request(String menuId, String nx, String ny){
            this.nx = nx;
            this.ny = ny;
            setData(APIInfo.GRA_LGN_0005.getIfCd(), menuId);
        }
    }

    /**
     * @brief LGN_0005의 응답 항목
     * @author hjpark
     * @see #fcstDtm 발표일자/시각
     * YYYYMMDDHH23MI
     * @see #nx 격자X
     * 경도 격자
     * @see #ny 격자Y
     * 위도 격자
     * @see #t1h 기온
     * 기온(T1H) :  ℃
     * @see #sky 하늘상태
     * 하늘상태(SKY) : 맑음(1), 구름많음(3), 흐림(4)
     * @see #reh 습도
     * 습도(REH) : 습도 %
     * @see #pty 강수형태
     * 강수형태(PTY) : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
     * @see #lgt 낙뢰
     * 낙뢰(LGT) : 확률없음(0), 낮음(1), 보통(2), 높음(3)
     * @see #dayCd 낮밤코드
     * 1: 낮 2:밤
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("fcstDtm")
        private String fcstDtm;
        @Expose
        @SerializedName("nx")
        private String nx;
        @Expose
        @SerializedName("ny")
        private String ny;
        @Expose
        @SerializedName("t1h")
        private String t1h;
        @Expose
        @SerializedName("sky")
        private String sky;
        @Expose
        @SerializedName("reh")
        private String reh;
        @Expose
        @SerializedName("pty")
        private String pty;
        @Expose
        @SerializedName("lgt")
        private String lgt;
        @Expose
        @SerializedName("dayCd")
        private String dayCd;
    }
}
