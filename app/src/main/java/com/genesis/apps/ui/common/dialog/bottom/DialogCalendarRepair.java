package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RepairGroupVO;
import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewHorizontalDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.DialogBottomCalendarBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.main.service.view.ServiceRepairReserveTimeHorizontalAdapter;
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
import androidx.recyclerview.widget.LinearLayoutManager;

public class DialogCalendarRepair extends BaseBottomDialog<DialogBottomCalendarBinding> {

    private ServiceRepairReserveTimeHorizontalAdapter serviceRepairReserveTimeHorizontalAdapter;

    private HighlightWeekendsDecorator highlightWeekendsDecorator = new HighlightWeekendsDecorator();
    private RemoveWeekendsDecorator removeWeekendsDecorator = new RemoveWeekendsDecorator();
    private SelectedDayDecorator selectedDayDecorator = new SelectedDayDecorator(0);
    private RejectDecorator rejectDecorator = new RejectDecorator();
    public Calendar calendar = null;
    private Calendar calendarMaximum;
    private Calendar calendarMinimum;
    private String title;
    private RepairGroupVO repairGroupVO;
    private List<RepairReserveDateVO> reserveDateVOList;
    private boolean isRemoveWeekends = false;

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
        ui.calendarView.setDynamicHeightEnabled(true); //달력 높이를 wrap로 설정
        ui.calendarView.addDecorator(highlightWeekendsDecorator);
        if (isRemoveWeekends) ui.calendarView.addDecorator(removeWeekendsDecorator);
        if (getReserveDateVOList() != null) ui.calendarView.addDecorator(rejectDecorator);
        ui.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            ui.calendarView.removeDecorator(selectedDayDecorator);
            selectedDayDecorator.setDaySelected(ui.calendarView.getSelectedDate().getDay());
            ui.calendarView.addDecorators(highlightWeekendsDecorator, selectedDayDecorator);
            if (isRemoveWeekends) ui.calendarView.addDecorator(removeWeekendsDecorator);
            if (getReserveDateVOList() != null) ui.calendarView.addDecorator(rejectDecorator);
            selectRsvtDt(DateUtil.getDate(ui.calendarView.getSelectedDate().getCalendar().getTime(), DateUtil.DATE_FORMAT_yyyyMMdd));
        });

        if (calendarMaximum != null || calendarMinimum != null) {
            ui.calendarView.state().edit().setMaximumDate(calendarMaximum).setMinimumDate(calendarMinimum).commit();
        }


        ui.calendarView.setOnMonthChangedListener((widget, date) -> {
            if (ui.calendarView.getSelectedDate() != null) {
                ui.calendarView.clearSelection();
                ui.calendarView.removeDecorator(selectedDayDecorator);
                selectRsvtDt("");
            }
        });

        ui.btnNext.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (ui.calendarView.getSelectedDate() != null
                        && selectReserveDate != null
                        && !TextUtils.isEmpty(selectReserveDate.getRsvtDt())
                        && !TextUtils.isEmpty(selectReserveDate.getRsvtTm())
                        && repairGroupVO != null) {
                    calendar = Calendar.getInstance(Locale.getDefault());
                    ui.calendarView.getSelectedDate().copyTo(calendar);
                    dismiss();
                } else {
                    SnackBarUtil.show(getContext(), "예약일 및 정비반이 선택되지 않았습니다.\n확인 후 다시 시도해 주세요.");
                }
            }
        });
        ui.calendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat("yyyy년 MM월", Locale.getDefault())));

        ui.lRepairGroup.setVisibility(View.VISIBLE);
        ui.tvRepairGroup.setOnClickListener(view -> {
            if (selectReserveDate != null
                    && !TextUtils.isEmpty(selectReserveDate.getRsvtDt())
                    && !TextUtils.isEmpty(selectReserveDate.getRsvtTm())) {
                onSingleClickListener.onSingleClick(view);
            } else {
                SnackBarUtil.show(getContext(), "예약일 및 예약 가능 시간이 선택되지 않았습니다.\n예약일을 먼저 선택해 주세요.");
            }
        });

        initTimeAdapter();
    }

    private void initTimeAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ui.rvTime.addItemDecoration(new RecyclerViewHorizontalDecoration((int) DeviceUtil.dip2Pixel(getContext(), 4.0f)));
        ui.rvTime.setLayoutManager(layoutManager);
        ui.rvTime.setHasFixedSize(true);
        serviceRepairReserveTimeHorizontalAdapter = new ServiceRepairReserveTimeHorizontalAdapter(onClickListener);
        ui.rvTime.setAdapter(serviceRepairReserveTimeHorizontalAdapter);
    }


    private void selectRsvtDt(String yyyyMMdd) {
        selectReserveDate.setRsvtDt(yyyyMMdd);
        selectReserveDate.setRsvtTm("");//예약가능시간 초기화
        ui.tvRsvthopetm.setVisibility(View.INVISIBLE);
//        ui.btnTime10.setVisibility(View.GONE);
//        ui.btnTime14.setVisibility(View.GONE);
//        Paris.style(ui.btnTime14).apply(R.style.BtrFilterDisable2);
//        Paris.style(ui.btnTime10).apply(R.style.BtrFilterDisable2);
        repairGroupVO = null;
        Paris.style(ui.tvRepairGroup).apply(R.style.CommonSpinnerItemDisable);
        ui.tvRepairGroup.setText(R.string.sm_r_rsv02_04_12);

        serviceRepairReserveTimeHorizontalAdapter.initSelectItem();
        serviceRepairReserveTimeHorizontalAdapter.clear();

        for (RepairReserveDateVO repairReserveDateVO : reserveDateVOList) {
            if (repairReserveDateVO.getRsvtDt().equalsIgnoreCase(yyyyMMdd)) {
                serviceRepairReserveTimeHorizontalAdapter.addRow(repairReserveDateVO);
            }
        }
        serviceRepairReserveTimeHorizontalAdapter.notifyDataSetChanged();

        if (serviceRepairReserveTimeHorizontalAdapter.getItemCount() == 0) {
            ui.tvRsvthopetm.setVisibility(View.VISIBLE);
            ui.tvRsvthopetm.setText(R.string.sm_r_rsv02_04_14);
//            ui.tvRsvthopetm.setVisibility(View.VISIBLE);
//            ui.tvRsvthopetm.setText(R.string.sm_r_rsv02_04_18);
        }
    }

    View.OnClickListener onClickListener = v -> {

        RepairReserveDateVO repairReserveDateVO = null;
        int postion = -1;
        try {
            repairReserveDateVO = ((RepairReserveDateVO) v.getTag(R.id.item));
            postion = Integer.parseInt(v.getTag(R.id.position).toString());
        } catch (Exception e) {

        } finally {
            if (postion > -1 && repairReserveDateVO != null) {
                serviceRepairReserveTimeHorizontalAdapter.setSelectItem(postion);
                selectReserveDate.setRsvtTm(repairReserveDateVO.getRsvtTm());
            }
        }
    };

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCalendarMinimum(Calendar calendarMinimum) {
        this.calendarMinimum = calendarMinimum;
    }

    public void setCalendarMaximum(Calendar calendarMaximum) {
        this.calendarMaximum = calendarMaximum;
    }

    public boolean isRemoveWeekends() {
        return isRemoveWeekends;
    }

    public void setRemoveWeekends(boolean removeWeekends) {
        isRemoveWeekends = removeWeekends;
    }

    public List<RepairReserveDateVO> getReserveDateVOList() {
        return reserveDateVOList;
    }

    public void setReserveDateVOList(List<RepairReserveDateVO> reserveDateVOList) {
        this.reserveDateVOList = reserveDateVOList;
    }

    public RepairReserveDateVO getSelectReserveDate() {
        return selectReserveDate;
    }

    public void setSelectRepairGroup(RepairGroupVO repairGroupVO) {
        this.repairGroupVO = repairGroupVO;
        ui.tvRepairGroup.setText(repairGroupVO.getRpshGrpNm());
        Paris.style(ui.tvRepairGroup).apply(R.style.CommonSpinnerItemEnable);
    }

    public RepairGroupVO getRepairGroupVO() {
        return repairGroupVO;
    }

    public class HighlightWeekendsDecorator implements DayViewDecorator {

        private final Drawable highlightDrawable;
        private final int color = Color.parseColor("#ce2d2d");
        private final int disableColor = Color.parseColor("#33ce2d2d");

        public HighlightWeekendsDecorator() {
            highlightDrawable = new ColorDrawable(color);
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {

            return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
//            final DayOfWeek weekDay = day.getDate().getDayOfWeek();
//            return weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY;
        }

        @Override
        public void decorate(final DayViewFacade view) {
//            view.setBackgroundDrawable(highlightDrawable);
            if (isRemoveWeekends) {
                view.addSpan(new ForegroundColorSpan(disableColor));
            } else {
                view.addSpan(new ForegroundColorSpan(color));
            }
//            view.addSpan(new ForegroundColorSpan(R.style.calendarStyleWeekendValue));
        }
    }


    public class RemoveWeekendsDecorator implements DayViewDecorator {

        private final Drawable highlightDrawable;
        private final int color = Color.parseColor("#00000000");

        public RemoveWeekendsDecorator() {
            highlightDrawable = new ColorDrawable(color);
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {

            return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
//            final DayOfWeek weekDay = day.getDate().getDayOfWeek();
//            return weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY;
        }

        @Override
        public void decorate(final DayViewFacade view) {
//            view.setBackgroundDrawable(highlightDrawable);
//            view.addSpan(new ForegroundColorSpan(color));
            view.setDaysDisabled(true);
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

        public void setDaySelected(int daySelected) {
            this.daySelected = daySelected;
        }
    }

    public class RejectDecorator implements DayViewDecorator {

        public RejectDecorator() {
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            if (getReserveDateVOList() != null) {
                String checkDay = DateUtil.getDate(day.getCalendar().getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
                boolean isPossible = false;
                for (RepairReserveDateVO repairReserveDateVO : getReserveDateVOList()) {
                    if (!TextUtils.isEmpty(repairReserveDateVO.getRsvtDt()) && repairReserveDateVO.getRsvtDt().equalsIgnoreCase(checkDay)) {
                        isPossible = true;
                        break;
                    }
                }

                return !isPossible;
            } else {
                return false;
            }
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setDaysDisabled(true);
        }
    }


    public final void showProgressDialog(final boolean show) {
        Log.v("test","test show:"+show);
        try {
            if (ui.lProgress != null) {
                new Handler().postDelayed(() -> {
                    ui.lProgress.lProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                    AnimationDrawable animationDrawable = (AnimationDrawable) ui.lProgress.ivProgress.getDrawable();
                    if (show) {
                        if (!animationDrawable.isRunning()) animationDrawable.start();
                    } else {
                        animationDrawable.stop();
                    }
                },0);
            }
        } catch (Exception e) {

        }
    }

}
