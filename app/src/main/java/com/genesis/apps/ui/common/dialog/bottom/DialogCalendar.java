package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.DialogBottomCalendarBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DialogCalendar extends BaseBottomDialog<DialogBottomCalendarBinding> {

    private DayViewDecorator weekendsDecorator;
    private HighlightWeekendsDecorator highlightWeekendsDecorator = new HighlightWeekendsDecorator();
    private RemoveWeekendsDecorator removeWeekendsDecorator = new RemoveWeekendsDecorator();
    private SelectedDayDecorator selectedDayDecorator = new SelectedDayDecorator(0);
    public Calendar calendar = null;
    private Calendar calendarMaximum;
    private Calendar calendarMinimum;
    private String title;
    private boolean useAutoAmpmCd=false;
    private String autoAmpmCd="A";
    private boolean isRemoveWeekends=false;

    public DialogCalendar(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_calendar);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);

        weekendsDecorator = (isRemoveWeekends ? removeWeekendsDecorator : highlightWeekendsDecorator);

        ui.calendarView.addDecorator(weekendsDecorator);
        ui.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            ui.calendarView.removeDecorator(selectedDayDecorator);
            selectedDayDecorator.setDaySelected(ui.calendarView.getSelectedDate().getDay());
            ui.calendarView.addDecorators( weekendsDecorator, selectedDayDecorator);



//            ui.calendarView.removeDecorators();
//            ui.calendarView.addDecorators( highlightWeekendsDecorator, new SelectedDayDecorator(ui.calendarView.getSelectedDate().getDay()));
        });

        if(calendarMaximum!=null||calendarMinimum!=null) {
            ui.calendarView.state().edit().setMaximumDate(calendarMaximum).setMinimumDate(calendarMinimum).commit();
//            ui.calendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        }


        ui.calendarView.setOnMonthChangedListener((widget, date) -> {
//            ui.calendarView.removeDecorators();
//            ui.calendarView.addDecorator(highlightWeekendsDecorator);


            ui.calendarView.removeDecorator(selectedDayDecorator);
        });

        ui.btnNext.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(ui.calendarView.getSelectedDate()!=null){
                    calendar = Calendar.getInstance(Locale.getDefault());
                    ui.calendarView.getSelectedDate().copyTo(calendar);
                    dismiss();
                }else{
                    SnackBarUtil.show(getContext(), "날짜를 선택해 주세요.");
                }
            }
        });

        if(useAutoAmpmCd){
            ui.tvCategory1.setVisibility(View.VISIBLE);
            ui.tvCategory2.setVisibility(View.VISIBLE);
            ui.tvCategory1.setOnClickListener(onClickListener);
            ui.tvCategory2.setOnClickListener(onClickListener);
        }

        ui.calendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat("yyyy년 MM월", Locale.getDefault())));

    }

    View.OnClickListener onClickListener = v -> {
        autoAmpmCd = v.getTag().toString();
        switch (v.getId()){
            case R.id.tv_category_1:
                ui.tvCategory1.setTextAppearance(R.style.BtrFilterEnable);
                ui.tvCategory1.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414_2dp);
                ui.tvCategory2.setTextAppearance(R.style.BtrFilterDisable);
                ui.tvCategory2.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_4d141414);
                break;
            case R.id.tv_category_2:
                ui.tvCategory1.setTextAppearance(R.style.BtrFilterDisable);
                ui.tvCategory1.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_4d141414);
                ui.tvCategory2.setTextAppearance(R.style.BtrFilterEnable);
                ui.tvCategory2.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414_2dp);
                break;
        }
    };

    public void setTitle(String title){
        this.title = title;
    }

    public void setCalendarMinimum(Calendar calendarMinimum){
        this.calendarMinimum = calendarMinimum;
    }
    public void setCalendarMaximum(Calendar calendarMaximum){
        this.calendarMaximum = calendarMaximum;
    }

    public boolean isUseAutoAmpmCd() {
        return useAutoAmpmCd;
    }

    public void setUseAutoAmpmCd(boolean useAutoAmpmCd) {
        this.useAutoAmpmCd = useAutoAmpmCd;
    }

    public String getAutoAmpmCd() {
        return autoAmpmCd;
    }

    public void setAutoAmpmCd(String autoAmpmCd) {
        this.autoAmpmCd = autoAmpmCd;
    }

    public boolean isRemoveWeekends() {
        return isRemoveWeekends;
    }

    public void setRemoveWeekends(boolean removeWeekends) {
        isRemoveWeekends = removeWeekends;
    }


    public class HighlightWeekendsDecorator implements DayViewDecorator {

        private final Drawable highlightDrawable;
        private final int color = Color.parseColor("#ba544d");

        public HighlightWeekendsDecorator() {
            highlightDrawable = new ColorDrawable(color);
        }

        @Override public boolean shouldDecorate(final CalendarDay day) {

            return day.getCalendar().get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY;
//            final DayOfWeek weekDay = day.getDate().getDayOfWeek();
//            return weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY;
        }

        @Override public void decorate(final DayViewFacade view) {
//            view.setBackgroundDrawable(highlightDrawable);
            view.addSpan(new ForegroundColorSpan(color));
//            view.addSpan(new ForegroundColorSpan(R.style.calendarStyleWeekendValue));
        }
    }


    public class RemoveWeekendsDecorator implements DayViewDecorator {

        private final Drawable highlightDrawable;
        private final int color = Color.parseColor("#00000000");

        public RemoveWeekendsDecorator() {
            highlightDrawable = new ColorDrawable(color);
        }

        @Override public boolean shouldDecorate(final CalendarDay day) {

            return day.getCalendar().get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY||day.getCalendar().get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY;
//            final DayOfWeek weekDay = day.getDate().getDayOfWeek();
//            return weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY;
        }

        @Override public void decorate(final DayViewFacade view) {
//            view.setBackgroundDrawable(highlightDrawable);
//            view.addSpan(new ForegroundColorSpan(color));
            view.setDaysDisabled(true);
            view.addSpan(new ForegroundColorSpan(color));
//            view.addSpan(new ForegroundColorSpan(R.style.calendarStyleWeekendValue));
        }
    }



    public class SelectedDayDecorator implements DayViewDecorator {
        private final Calendar calendar = Calendar.getInstance();
        private final int color = ContextCompat.getColor(getContext(), R.color.x_ffffff);
        private int daySelected;

        public SelectedDayDecorator(int daySelected) {
            this.daySelected = daySelected;
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

        public void setDaySelected(int daySelected){
            this.daySelected = daySelected;
        }
    }

}
