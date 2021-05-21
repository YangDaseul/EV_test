package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + S-트래픽 충전기 예약가능시간
 * @see #reservDt 예약시간
 * HH24MI(30분단위)
 * @see #reservYn 가능여부
 * Y:예약가능 N:예약불가
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ReserveDtVO extends BaseData {
    @Expose
    @SerializedName("reservDt")
    private String reservDt;
    @Expose
    @SerializedName("reservYn")
    private String reservYn;

    /**
     * 파라메터의 시간과 해당 객체의 시간 데이터를 비교하여 파라메터 시간보다 이전 시간이면 true, 아니면 false를 반환
     *
     * @param calendar 비교할 시간 객체
     * @return
     */
    public boolean isBefore(Calendar calendar) {
        try {
            String[] time = reservDt.split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            int totalMinute = (hour * 60) + minute;
            return totalMinute <= ((calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE));
        } catch (Exception ignore) {

        }
        return false;
    }
    /**
     * 파라메터의 시간과 해당 객체의 시간 데이터를 비교하여 파라메터 시간보다 이후 시간이면 true, 아니면 false를 반환
     *
     * @param calendar 비교할 시간 객체
     * @return
     */
    public boolean isAfter(Calendar calendar) {
        try {
            String[] time = reservDt.split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            int totalMinute = (hour * 60) + minute;
            return totalMinute >= ((calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE));
        } catch (Exception ignore) {

        }
        return false;
    }
} // end of class ReserveDtVO
