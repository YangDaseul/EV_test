package com.genesis.apps.comm.model.vo;

import androidx.room.Ignore;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 충전 버틀러 예약 가능 일자 VO
 * @author ljeun
 * @since 2021. 4. 5.
 *
 * @see #bookingDate    일자  YYYYMMDD
 * @see #slotList   예약 슬롯 리스트
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class BookingDateVO extends BaseData {

    public BookingDateVO() {

    }

    @Expose
    @SerializedName("bookingDate")
    private String bookingDate;
    @Expose
    @SerializedName("slotList")
    private List<BookingTimeVO> slotList;

    @Ignore
    private String selectBookingTime;
}
