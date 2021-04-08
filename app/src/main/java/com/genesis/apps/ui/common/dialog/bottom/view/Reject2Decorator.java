package com.genesis.apps.ui.common.dialog.bottom.view;

import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.genesis.apps.comm.model.vo.BookingDateVO;
import com.genesis.apps.comm.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.List;

/**
 * @brief 달력에서 BookingDateVO 리스트 대상으로 선택 가능유무 및 색상을 결정하는 decorator.
 *
 *
 *
 */
public class Reject2Decorator implements DayViewDecorator {

    private List<BookingDateVO> reserveDateVOList = null;
    private int color;
    private boolean isSunday;

    public Reject2Decorator(List<BookingDateVO> reserveDateVOList, int color, boolean isSunday) {
        this.reserveDateVOList = reserveDateVOList;
        this.color = color;
        this.isSunday = isSunday;
    }

    @Override
    public boolean shouldDecorate(final CalendarDay day) {
        if (reserveDateVOList != null) {
            String checkDay = DateUtil.getDate(day.getCalendar().getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
            boolean isPossible = reserveDateVOList.stream().anyMatch(data -> !TextUtils.isEmpty(data.getBookingDate()) && data.getBookingDate().equalsIgnoreCase(checkDay));

            //사용가능한 날짜가 아니고 요일이 주말 혹은 평일인 경우
            return !isPossible && (isSunday == (day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY));
        } else {
            return false;
        }
    }

    @Override
    public void decorate(final DayViewFacade view) {
        view.setDaysDisabled(true);
        view.addSpan(new ForegroundColorSpan(color));
    }
}
