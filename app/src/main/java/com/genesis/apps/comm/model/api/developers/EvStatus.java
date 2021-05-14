package com.genesis.apps.comm.model.api.developers;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file EvStatus
 * @Brief 전기차 차량 상태 조회
 */
public class EvStatus extends BaseData {
    /**
     * @author hjpark
     * @brief EvStatus 요청 항목
     * @see #carId 차량 고유 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        @Expose
        @SerializedName("carId")
        private String carId;

        public Request(String carId) {
            this.carId = carId;
        }
    }

    /**
     * @author hjpark
     * @brief EvStatus 응답 항목
     * @see #batteryPlugin 플러그 연결 여부 (0: 연결 안됨, 1: 급속 충전기 연결, 2: 일반 충전기 연결)
     * @see #batteryCharge 충전 여부
     * @see #soc 배터리 잔량(단위: %)
     * @see #dte 주행 가능 거리
     * @see #targetSOC 목표 충전 설정값
     * @see #remainTime 목표 충전까지 남은 시간
     * - 충전 연결 상태: 잔여 충전 시간
     * - 충전 미연결 상태: 240V 충전 시 잔여 충전 시간
     * @see #chargePortDoorOpenStatus 충전구 열기/닫기 상태 (1: 열기, 2: 닫기)
     * @see #engine 시동 on/off 상태
     * @see #ign3 IGN3 on/off 상태
     * @see #airCtrl 공조 on/off 상태
     * @see #acc ACC on/off 상태
     * @see #timestamp 차량 전송 시간
     * @see #msgId 요청 결과 확인을 위한 메시지 ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends DevelopersBaseResponse {
        @Expose
        @SerializedName("batteryPlugin")
        private float batteryPlugin;
        @Expose
        @SerializedName("batteryCharge")
        private boolean batteryCharge;
        @Expose
        @SerializedName("soc")
        private float soc;
        @Expose
        @SerializedName("dte")
        private DTE dte;
        @Expose
        @SerializedName("targetSOC")
        private TargetSOC targetSOC;
        @Expose
        @SerializedName("remainTime")
        private RemainTime remainTime;
        @Expose
        @SerializedName("chargePortDoorOpenStatus")
        private float chargePortDoorOpenStatus;
        @Expose
        @SerializedName("engine")
        private boolean engine;
        @Expose
        @SerializedName("ign3")
        private boolean ign3;
        @Expose
        @SerializedName("airCtrl")
        private boolean airCtrl;
        @Expose
        @SerializedName("acc")
        private boolean acc;
        @Expose
        @SerializedName("timestamp")
        private String timestamp;
        @Expose
        @SerializedName("msgId")
        private String msgId;
    }

    /**
     * @author hjpark
     * @brief EvStatus 응답 항목
     * @see #type 거리구분 (0: Min, 1: Average, 2: Max)
     * @see #distance 단위 (0: feet, 1: km, 2: meter , 3: miles)
     */
    public @Data
    class DTE{
        @Expose
        @SerializedName("type")
        private float type;

        @Expose
        @SerializedName("distance")
        private DISTANCE distance;
    }

    /**
     * @author hjpark
     * @brief EvStatus 응답 항목
     * @see #value 거리 수치
     * @see #unit 단위 (0: feet, 1: km, 2: meter , 3: miles)
     */
    public @Data
    class DISTANCE{
        @Expose
        @SerializedName("value")
        private float value;
        @Expose
        @SerializedName("unit")
        private float unit;

        public String getUnitString() {
            if(unit == 0f) {
                return "feet";
            } else if(unit == 2f) {
                return "meter";
            } else if(unit == 3f) {
                return "miles";
            } else {
                return "km";
            }
        }
    }
    /**
     * @author hjpark
     * @brief EvStatus 응답 항목
     * @see #plugType 충전기 타입 (0 : DC charger, 1 : AC w/ 240V, 2 : AC w/ 120V)
     * @see #targetSOClevel 충전 목표 배터리 잔량
     */
    public @Data
    class TargetSOC{
        @Expose
        @SerializedName("plugType")
        private float plugType;
        @Expose
        @SerializedName("targetSOClevel")
        private float targetSOClevel;
    }
    /**
     * @author hjpark
     * @brief EvStatus 응답 항목
     * @see #value 시간 간격 수치
     * @see #unit 단위 (0: hour, 1: min, 2: msec, 3: sec)
     */
    public @Data
    class RemainTime{
        @Expose
        @SerializedName("value")
        private float value;
        @Expose
        @SerializedName("unit")
        private float unit;

        public String getUnitStringKo() {
            if(unit == 0f) {
                return "시간";
            } else if(unit == 1f) {
                return "분";
            } else if(unit == 2f) {
                return "밀리초";
            } else {
                return "초";
            }
        }
    }

}
