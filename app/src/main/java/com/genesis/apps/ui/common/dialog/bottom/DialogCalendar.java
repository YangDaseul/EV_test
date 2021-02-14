package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.DialogBottomCalendarBinding;
import com.genesis.apps.ui.common.dialog.bottom.view.HighlightWeekendsDecorator;
import com.genesis.apps.ui.common.dialog.bottom.view.MinMaxDecorator;
import com.genesis.apps.ui.common.dialog.bottom.view.RejectDecorator;
import com.genesis.apps.ui.common.dialog.bottom.view.RemoveWeekendsDecorator;
import com.genesis.apps.ui.common.dialog.bottom.view.SelectedDayDecorator;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DialogCalendar extends BaseBottomDialog<DialogBottomCalendarBinding> {

    private HighlightWeekendsDecorator highlightWeekendsDecorator;
    private RemoveWeekendsDecorator removeWeekendsDecorator = new RemoveWeekendsDecorator();
    private SelectedDayDecorator selectedDayDecorator;
    private RejectDecorator rejectDecorator;
    private MinMaxDecorator minMaxDecorator;
    private MinMaxDecorator minMaxSundayDecorator;
    public Calendar calendar = null;
    private Calendar calendarMaximum;
    private Calendar calendarMinimum;
    private String title;
    private boolean useAutoAmpmCd = false;
    private String autoAmpmCd = "A";
    private boolean isRemoveWeekends = false;
    //홈투홈에서만 사용, 예약 가능 일
    private List<RepairReserveDateVO> reserveDateVOList;

    public DialogCalendar(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_calendar);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);
        ui.calendarView.setDynamicHeightEnabled(false); //달력 높이를 wrap로 설정
        initDecorator();


        ui.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            ui.calendarView.removeDecorator(selectedDayDecorator);
            selectedDayDecorator.setDaySelected(ui.calendarView.getSelectedDate().getDay());
            ui.calendarView.addDecorators(highlightWeekendsDecorator, selectedDayDecorator);
            if (isRemoveWeekends) ui.calendarView.addDecorator(removeWeekendsDecorator);

            if (rejectDecorator != null) {
                ui.calendarView.addDecorator(rejectDecorator);
            }

            if (minMaxDecorator!=null) {
                ui.calendarView.addDecorators(minMaxDecorator, minMaxSundayDecorator);
            }
        });

        ui.calendarView.setOnMonthChangedListener((widget, date) -> {
            if (ui.calendarView.getSelectedDate() != null) {
                ui.calendarView.clearSelection();
                ui.calendarView.removeDecorator(selectedDayDecorator);
            }
        });

        ui.btnNext.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (ui.calendarView.getSelectedDate() != null) {
                    calendar = Calendar.getInstance(Locale.getDefault());
                    ui.calendarView.getSelectedDate().copyTo(calendar);
                    dismiss();

                } else {
                    SnackBarUtil.show(getContext(), "날짜를 선택해 주세요.");
                }
            }
        });

        if (useAutoAmpmCd) {
            ui.tvCategory1.setVisibility(View.VISIBLE);
            ui.tvCategory2.setVisibility(View.VISIBLE);
            ui.tvCategory1.setOnClickListener(onClickListener);
            ui.tvCategory2.setOnClickListener(onClickListener);
        }

        ui.calendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat("yyyy년 MM월", Locale.getDefault())));
    }

    private void initDecorator() {
        selectedDayDecorator = new SelectedDayDecorator(0, ContextCompat.getColor(getContext(), R.color.x_ffffff));
        highlightWeekendsDecorator = new HighlightWeekendsDecorator(isRemoveWeekends);
        if (isRemoveWeekends) {
            ui.calendarView.addDecorator(removeWeekendsDecorator);
        }
        ui.calendarView.addDecorator(highlightWeekendsDecorator);

        if (getReserveDateVOList() != null) {
            rejectDecorator = new RejectDecorator(getReserveDateVOList());
            ui.calendarView.addDecorator(rejectDecorator);
        }

        if (calendarMaximum != null || calendarMinimum != null) {
            ui.calendarView.state().edit().setMaximumDate(calendarMaximum).setMinimumDate(calendarMinimum).commit();
            minMaxDecorator = new MinMaxDecorator(calendarMinimum, calendarMaximum,ContextCompat.getColor(getContext(), R.color.x_33000000), false);
            minMaxSundayDecorator = new MinMaxDecorator(calendarMinimum, calendarMaximum,ContextCompat.getColor(getContext(), R.color.x_4dce2d2d), true);
            ui.calendarView.addDecorators(minMaxDecorator, minMaxSundayDecorator);
        }
    }

    View.OnClickListener onClickListener = v -> {
        autoAmpmCd = v.getTag().toString();
        switch (v.getId()) {
            case R.id.tv_category_1:
                Paris.style(ui.tvCategory1).apply(R.style.BtrFilterEnable2);
                Paris.style(ui.tvCategory2).apply(R.style.BtrFilterDisable2);
                break;
            case R.id.tv_category_2:
                Paris.style(ui.tvCategory2).apply(R.style.BtrFilterEnable2);
                Paris.style(ui.tvCategory1).apply(R.style.BtrFilterDisable2);
                break;
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

    public List<RepairReserveDateVO> getReserveDateVOList() {
        return reserveDateVOList;
    }

    public void setReserveDateVOList(List<RepairReserveDateVO> reserveDateVOList) {
        this.reserveDateVOList = reserveDateVOList;
    }


}
