package com.genesis.apps.ui.common.dialog.bottom.view;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class RemoveWeekendsDecorator implements DayViewDecorator {
    public RemoveWeekendsDecorator() {
        //do nothing
    }

    @Override
    public boolean shouldDecorate(final CalendarDay day) {
        return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    @Override
    public void decorate(final DayViewFacade view) {
        view.setDaysDisabled(true);
    }
}
