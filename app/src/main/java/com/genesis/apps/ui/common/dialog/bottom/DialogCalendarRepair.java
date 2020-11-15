package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RepairGroupVO;
import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.DialogBottomCalendarBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class DialogCalendarRepair extends BaseBottomDialog<DialogBottomCalendarBinding> {

    private DayViewDecorator weekendsDecorator;
    private HighlightWeekendsDecorator highlightWeekendsDecorator = new HighlightWeekendsDecorator();
    private RemoveWeekendsDecorator removeWeekendsDecorator = new RemoveWeekendsDecorator();
    private SelectedDayDecorator selectedDayDecorator = new SelectedDayDecorator(0);
    public Calendar calendar = null;
    private Calendar calendarMaximum;
    private Calendar calendarMinimum;
    private String title;
    private RepairGroupVO repairGroupVO;

    private List<RepairReserveDateVO> reserveDateVOList;

    private RepairReserveDateVO selectReserveDate = new RepairReserveDateVO();

    private OnSingleClickListener onSingleClickListener;

    public DialogCalendarRepair(@NonNull Context context, int theme, OnSingleClickListener onSingleClickListener) {
        super(context, theme);
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_calendar);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);

        weekendsDecorator =  highlightWeekendsDecorator;

        ui.calendarView.addDecorator(weekendsDecorator);
        ui.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            ui.calendarView.removeDecorator(selectedDayDecorator);
            selectedDayDecorator.setDaySelected(ui.calendarView.getSelectedDate().getDay());
            ui.calendarView.addDecorators( weekendsDecorator, selectedDayDecorator);
            selectRsvtDt(DateUtil.getDate(ui.calendarView.getSelectedDate().getCalendar().getTime(),DateUtil.DATE_FORMAT_yyyyMMdd));
        });

        if(calendarMaximum!=null||calendarMinimum!=null) {
            ui.calendarView.state().edit().setMaximumDate(calendarMaximum).setMinimumDate(calendarMinimum).commit();
        }


        ui.calendarView.setOnMonthChangedListener((widget, date) -> {
            ui.calendarView.removeDecorator(selectedDayDecorator);
        });

        ui.btnNext.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(ui.calendarView.getSelectedDate()!=null
                        &&selectReserveDate!=null
                        &&!TextUtils.isEmpty(selectReserveDate.getRsvtDt())
                        &&!TextUtils.isEmpty(selectReserveDate.getRsvtTm())
                        &&repairGroupVO!=null){
                    calendar = Calendar.getInstance(Locale.getDefault());
                    ui.calendarView.getSelectedDate().copyTo(calendar);
                    dismiss();
                }else{
                    SnackBarUtil.show(getContext(), "예약일 및 정비반이 선택되지 않았습니다.\n확인 후 다시 시도해 주세요.");
                }
            }
        });
        ui.calendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat("yyyy년 MM월", Locale.getDefault())));





        ui.lRepairGroup.setVisibility(View.VISIBLE);
        ui.btnTime10.setOnClickListener(onClickListener);
        ui.btnTime14.setOnClickListener(onClickListener);
        ui.tvRepairGroup.setOnClickListener(view -> {
                if(selectReserveDate!=null
                        &&!TextUtils.isEmpty(selectReserveDate.getRsvtDt())
                        &&!TextUtils.isEmpty(selectReserveDate.getRsvtTm())){
                    onSingleClickListener.onSingleClick(view);
                }else{
                    SnackBarUtil.show(getContext(), "예약일 및 예약 가능 시간이 선택되지 않았습니다.\n예약일을 먼저 선택해 주세요.");
                }
        });
    }

    private void selectRsvtDt(String yyyyMMdd){
        selectReserveDate.setRsvtDt(yyyyMMdd);
        selectReserveDate.setRsvtTm("");//예약가능시간 초기화
        ui.tvRsvthopetm.setVisibility(View.INVISIBLE);
        ui.btnTime10.setVisibility(View.GONE);
        ui.btnTime10.setTextAppearance(R.style.BtrFilterDisable);
        ui.btnTime10.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_4d141414);
        ui.btnTime14.setVisibility(View.GONE);
        ui.btnTime14.setTextAppearance(R.style.BtrFilterDisable);
        ui.btnTime14.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_4d141414);

        repairGroupVO = null;
        ui.tvRepairGroup.setTextAppearance(R.style.CommonSpinnerItemDisable);
        ui.tvRepairGroup.setText(R.string.sm_r_rsv02_04_12);


        int cnt=0;
        for(RepairReserveDateVO repairReserveDateVO : reserveDateVOList){
            if(repairReserveDateVO.getRsvtDt().equalsIgnoreCase(yyyyMMdd)){
                cnt++;
                //todo 10:00 인 시간과 14:00인 시간의  날짜 상태값 변경 필요
                if(repairReserveDateVO.getRsvtTm().equalsIgnoreCase(ui.btnTime10.getTag().toString())){
                    ui.btnTime10.setVisibility(View.VISIBLE);
                }else if(repairReserveDateVO.getRsvtTm().equalsIgnoreCase(ui.btnTime14.getTag().toString())){
                    ui.btnTime14.setVisibility(View.VISIBLE);
                }
                if(cnt>1)
                    break;
            }
        }

        if(cnt==0){
            ui.tvRsvthopetm.setVisibility(View.VISIBLE);
        }
    }

    View.OnClickListener onClickListener = v -> {
        selectReserveDate.setRsvtTm(v.getTag().toString());
        switch (v.getId()){
            case R.id.btn_time_10:
                ui.btnTime10.setTextAppearance(R.style.BtrFilterEnable);
                ui.btnTime10.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414_2dp);
                ui.btnTime14.setTextAppearance(R.style.BtrFilterDisable);
                ui.btnTime14.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_4d141414);
                break;
            case R.id.btn_time_14:
                ui.btnTime10.setTextAppearance(R.style.BtrFilterDisable);
                ui.btnTime10.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_4d141414);
                ui.btnTime14.setTextAppearance(R.style.BtrFilterEnable);
                ui.btnTime14.setBackgroundResource(R.drawable.ripple_bg_ffffff_stroke_141414_2dp);
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

    public List<RepairReserveDateVO> getReserveDateVOList() {
        return reserveDateVOList;
    }

    public void setReserveDateVOList(List<RepairReserveDateVO> reserveDateVOList) {
        this.reserveDateVOList = reserveDateVOList;
    }

    public RepairReserveDateVO getSelectReserveDate(){
        return selectReserveDate;
    }

    public void setSelectRepairGroup(RepairGroupVO repairGroupVO) {
        this.repairGroupVO = repairGroupVO;
        ui.tvRepairGroup.setText(repairGroupVO.getRpshGrpNm());
        ui.tvRepairGroup.setTextAppearance(R.style.CommonSpinnerItemEnable);
    }

    public RepairGroupVO getRepairGroupVO() {
        return repairGroupVO;
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
