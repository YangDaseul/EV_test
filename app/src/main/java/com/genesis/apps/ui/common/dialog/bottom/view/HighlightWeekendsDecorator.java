package com.genesis.apps.ui.common.dialog.bottom.view;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class HighlightWeekendsDecorator implements DayViewDecorator {

    private final int color = Color.parseColor("#ce2d2d");
    private final int disableColor = Color.parseColor("#4dce2d2d");
    private boolean isRemoveWeekends;

    public HighlightWeekendsDecorator(boolean isRemoveWeekends) {
        this.isRemoveWeekends = isRemoveWeekends;
    }

    @Override
    public boolean shouldDecorate(final CalendarDay day) {
        return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    @Override
    public void decorate(final DayViewFacade view) {
        if (isRemoveWeekends) {
            view.addSpan(new ForegroundColorSpan(disableColor));
        } else {
            view.addSpan(new ForegroundColorSpan(color));
        }
    }
}
