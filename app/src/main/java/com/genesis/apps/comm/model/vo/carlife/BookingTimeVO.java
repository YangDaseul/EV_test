package com.genesis.apps.comm.model.vo.carlife;

import androidx.room.Ignore;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 픽업앤충전 예약 가능 시간 VO
 * @author ljeun
 * @since 2021. 4. 5.
 *
 * @see #bookingTime    예약 시간   HH24MISS
 * @see #remainCount    잔여 예약 수
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class BookingTimeVO extends BaseData {
    @Expose
    @SerializedName("bookingTime")
    private String bookingTime;
    @Expose
    @SerializedName("remainCount")
    private int remainCount;

    @Ignore
    private boolean isSelect;
}
