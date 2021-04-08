package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.BookingDateVO;
import com.genesis.apps.comm.model.vo.BookingTimeVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewHorizontalDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.DialogBottomCalendarBinding;
import com.genesis.apps.ui.common.dialog.bottom.view.HighlightWeekendsDecorator;
import com.genesis.apps.ui.common.dialog.bottom.view.MinMaxDecorator;
import com.genesis.apps.ui.common.dialog.bottom.view.Reject2Decorator;
import com.genesis.apps.ui.common.dialog.bottom.view.RemoveWeekendsDecorator;
import com.genesis.apps.ui.common.dialog.bottom.view.SelectedDayDecorator;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.main.service.view.ServiceChargeBtrReserveTimeHorizontalAdapter;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class DialogCalendarChargeBtr extends BaseBottomDialog<DialogBottomCalendarBinding> {

    private ServiceChargeBtrReserveTimeHorizontalAdapter serviceChargeBtrReserveTimeHorizontalAdapter;

    private HighlightWeekendsDecorator highlightWeekendsDecorator;
    private RemoveWeekendsDecorator removeWeekendsDecorator = new RemoveWeekendsDecorator();
    private SelectedDayDecorator selectedDayDecorator;
    private Reject2Decorator rejectDecorator;
    private Reject2Decorator rejectSundayDecorator;
    private MinMaxDecorator minMaxDecorator;
    private MinMaxDecorator minMaxSundayDecorator;
    public Calendar calendar = null;
    private Calendar calendarMaximum;
    private Calendar calendarMinimum;
    private String title;
    private List<BookingDateVO> bookingDateVOList;
    private boolean isRemoveWeekends = false;

    private BookingDateVO selectBookingDate = new BookingDateVO();
    private OnSingleClickListener onSingleClickListener;

    public DialogCalendarChargeBtr(@NonNull Context context, int theme, OnSingleClickListener onSingleClickListener) {
        super(context, theme);
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_calendar);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);
        ui.calendarView.setDynamicHeightEnabled(false); //달력 높이를 wrap로 설정
        initDecorator();
        initSelectedDay();

        ui.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            ui.calendarView.removeDecorator(selectedDayDecorator);
            selectedDayDecorator.setDaySelected(ui.calendarView.getSelectedDate().getDay());
            ui.calendarView.addDecorators(highlightWeekendsDecorator, selectedDayDecorator);
            if (isRemoveWeekends) ui.calendarView.addDecorator(removeWeekendsDecorator);

            if (rejectDecorator != null) {
                ui.calendarView.addDecorators(rejectDecorator, rejectSundayDecorator);
            }

            selectRsvtDt(DateUtil.getDate(ui.calendarView.getSelectedDate().getCalendar().getTime(), DateUtil.DATE_FORMAT_yyyyMMdd));

            if (minMaxDecorator!=null) {
                ui.calendarView.addDecorators(minMaxDecorator, minMaxSundayDecorator);
            }
        });

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
                        && selectBookingDate != null
                        && !TextUtils.isEmpty(selectBookingDate.getBookingDate())
                        && !TextUtils.isEmpty(selectBookingDate.getSelectBookingTime())) {
                    calendar = Calendar.getInstance(Locale.getDefault());
                    ui.calendarView.getSelectedDate().copyTo(calendar);
                    dismiss();
                } else {
                    SnackBarUtil.show(getContext(), getContext().getString(R.string.service_charge_btr_err_09));
                }
            }
        });
        ui.calendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat(DateUtil.DATE_FORMAT_yyyy_MM_KOREA, Locale.getDefault())));

        ui.lRepairGroup.setVisibility(View.VISIBLE);
        ui.tvTitleRepairGroup.setVisibility(View.GONE);
        ui.tvRepairGroup.setVisibility(View.GONE);

        initTimeAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //todo 데코레이터가 너무 많아서 select 시 느린 현상 발생.. 정리 필요
    private void initDecorator() {
        selectedDayDecorator = new SelectedDayDecorator(0, ContextCompat.getColor(getContext(), R.color.x_ffffff));
        highlightWeekendsDecorator = new HighlightWeekendsDecorator(isRemoveWeekends);
        if (isRemoveWeekends) {
            ui.calendarView.addDecorator(removeWeekendsDecorator);
        }
        ui.calendarView.addDecorator(highlightWeekendsDecorator);

        if (getBookingDateVOList() != null) {
            rejectDecorator = new Reject2Decorator(getBookingDateVOList(), ContextCompat.getColor(getContext(), R.color.x_33000000), false);
            rejectSundayDecorator = new Reject2Decorator(getBookingDateVOList(), ContextCompat.getColor(getContext(), R.color.x_4dce2d2d), true);
            ui.calendarView.addDecorators(rejectDecorator, rejectSundayDecorator);
        }

        if (calendarMaximum != null || calendarMinimum != null) {
            ui.calendarView.state().edit().setMaximumDate(calendarMaximum).setMinimumDate(calendarMinimum).commit();
            minMaxDecorator = new MinMaxDecorator(calendarMinimum, calendarMaximum,ContextCompat.getColor(getContext(), R.color.x_33000000), false);
            minMaxSundayDecorator = new MinMaxDecorator(calendarMinimum, calendarMaximum,ContextCompat.getColor(getContext(), R.color.x_4dce2d2d), true);
            ui.calendarView.addDecorators(minMaxDecorator, minMaxSundayDecorator);
        }
    }

    private void initSelectedDay() {
        if (getBookingDateVOList() != null && getBookingDateVOList().size() > 0) {

            BookingDateVO firstDate = getBookingDateVOList().stream().min(Comparator.comparingInt(data -> Integer.parseInt(data.getBookingDate()))).orElse(null);

            ui.calendarView.setSelectedDate(DateUtil.getDefaultDateFormat(firstDate.getBookingDate(), DateUtil.DATE_FORMAT_yyyyMMdd));
        }
    }

    private void initTimeAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ui.rvTime.addItemDecoration(new RecyclerViewHorizontalDecoration((int) DeviceUtil.dip2Pixel(getContext(), 4.0f)));
        ui.rvTime.setLayoutManager(layoutManager);
        ui.rvTime.setHasFixedSize(true);
        serviceChargeBtrReserveTimeHorizontalAdapter = new ServiceChargeBtrReserveTimeHorizontalAdapter(onClickListener);
        ui.rvTime.setAdapter(serviceChargeBtrReserveTimeHorizontalAdapter);

        if(ui.calendarView.getSelectedDate() != null)
            selectRsvtDt(DateUtil.getDate(ui.calendarView.getSelectedDate().getCalendar().getTime(), DateUtil.DATE_FORMAT_yyyyMMdd));
    }


    private void selectRsvtDt(String yyyyMMdd) {
        selectBookingDate.setBookingDate(yyyyMMdd);
        selectBookingDate.setSelectBookingTime("");//예약가능시간 초기화
        ui.tvRsvthopetm.setVisibility(View.INVISIBLE);

        serviceChargeBtrReserveTimeHorizontalAdapter.initSelectItem();
        serviceChargeBtrReserveTimeHorizontalAdapter.clear();

        for (BookingDateVO bookingDateVO : bookingDateVOList) {
            if (bookingDateVO.getBookingDate().equalsIgnoreCase(yyyyMMdd)) {
                serviceChargeBtrReserveTimeHorizontalAdapter.addRows(bookingDateVO.getSlotList());
            }
        }
        serviceChargeBtrReserveTimeHorizontalAdapter.notifyDataSetChanged();

        if (serviceChargeBtrReserveTimeHorizontalAdapter.getItemCount() == 0) {
            ui.tvRsvthopetm.setVisibility(View.VISIBLE);
            ui.tvRsvthopetm.setText(R.string.sm_r_rsv02_04_14);
        }
    }

    View.OnClickListener onClickListener = v -> {

        BookingTimeVO bookingTimeVO = null;
        int postion = -1;
        try {
            bookingTimeVO = ((BookingTimeVO) v.getTag(R.id.item));
            postion = Integer.parseInt(v.getTag(R.id.position).toString());
        } catch (Exception e) {

        } finally {
            if (postion > -1 && bookingTimeVO != null) {
                serviceChargeBtrReserveTimeHorizontalAdapter.setSelectItem(postion);
                selectBookingDate.setSelectBookingTime(bookingTimeVO.getBookingTime());
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

    public List<BookingDateVO> getBookingDateVOList() {
        return bookingDateVOList;
    }

    public void setBookingDateVOList(List<BookingDateVO> bookingDateVOList) {
        this.bookingDateVOList = bookingDateVOList;
    }

    public BookingDateVO getSelectBookingDate() {
        return selectBookingDate;
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
