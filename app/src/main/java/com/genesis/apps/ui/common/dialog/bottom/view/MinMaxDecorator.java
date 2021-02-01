package com.genesis.apps.ui.common.dialog.bottom.view;

import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

/**
 * @biref 달력에서 평일에 대한 최소, 최대 값에 대한 처리
 * 1) 클릭 disable 처리
 * 2) 컬러 변경
 * 3) 평일에 토요일이 포함됨
 * ps : decorator 적용 시 가장 후순위로 적용 필요
 */
public class MinMaxDecorator implements DayViewDecorator {

    private CalendarDay minCalendar;
    private CalendarDay maxCalendar;
    private int color;
    private boolean isSunday;

    public MinMaxDecorator(Calendar calendarMinimum, Calendar calendarMaximum, int color, boolean isSunday) {
        if (calendarMinimum != null) minCalendar = CalendarDay.from(calendarMinimum);
        if (calendarMaximum != null) maxCalendar = CalendarDay.from(calendarMaximum);
        this.color = color;
        this.isSunday = isSunday;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return (isSunday == (day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) &&
                    ((maxCalendar != null && day.isAfter(maxCalendar)) ||
                     (minCalendar != null && day.isBefore(minCalendar)));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(color));
        view.setDaysDisabled(true);
    }
}
