package com.genesis.apps.ui.common.dialog.bottom.view;

import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class SelectedDayDecorator implements DayViewDecorator {
    private final Calendar calendar = Calendar.getInstance();
    private int color;
    private int daySelected;

    public SelectedDayDecorator(int daySelected, int color) {
        this.daySelected = daySelected;
        this.color = color;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int dayMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayMonth == daySelected;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(color));
    }

    public void setDaySelected(int daySelected) {
        this.daySelected = daySelected;
    }
}
