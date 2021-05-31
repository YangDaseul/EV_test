package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ReserveDtVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.databinding.DialogBottomChargerReserveBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class BottomChargerReserveDialog extends BaseBottomDialog<DialogBottomChargerReserveBinding> implements RadioGroup.OnCheckedChangeListener {

    private Context context;
    private List<ReserveDtVO> datas = new ArrayList<>();

    private ChargeReserveTimeListAdapter adapter;

    private EventListener eventListener;

    public interface EventListener {
        void onReserveTime(ReserveDtVO reserveDtVO);
    }

    public BottomChargerReserveDialog(@NonNull Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_charger_reserve);
        setAllowOutTouch(true);
        setEnabledBtn(false);

        // 타이틀 설정
        ui.lTitle.setValue(context.getString(R.string.sm_evsb01_p01_1));
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) ui.lTitle.getRoot().getLayoutParams();
        lp.setMargins((int) DeviceUtil.dip2Pixel(context, 20), 0, (int) DeviceUtil.dip2Pixel(context, 20), 0);
        ui.lTitle.getRoot().setLayoutParams(lp);

        // 날짜 표시
        ui.tvDate.setText(DateUtil.getCurrentDate(DateUtil.DATE_FORMAT_yyyy_mm_dd_dot_E));

        if (datas == null || datas.size() == 0) {
            // 표시할 날짜 데이터가 없다면 예약 가능 날짜 없음을 안내하는 UI 표시.
            ui.tvGuideNoTime.setVisibility(View.VISIBLE);
            ui.rgAmPm.setVisibility(View.GONE);
            ui.line0.setVisibility(View.GONE);
            ui.rvTime.setVisibility(View.GONE);
            return;
        }

        adapter = new ChargeReserveTimeListAdapter(onSingleClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        ui.rvTime.setLayoutManager(layoutManager);
        ui.rvTime.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 12) {
            // 현재 시간이 오후인 경우는 오전 버튼 비활성.
            ui.rb0.setVisibility(View.GONE);
            ui.rb1.setChecked(true);
            setAmPm(true);
        } else {
            // 현재 시간이 오전인 경우, 오전 데이터가 있는지 체크
            List<ReserveDtVO> amTimes = getTimeList(false);
            if (amTimes != null && amTimes.size() > 0) {
                // 오전 시간이 있다면 표시
                ui.rb0.setChecked(true);
                setAmPm(false);
            } else {
                // 오전 시간이 없다면 오전 버튼 비활성, 오후 버튼 표시.
                ui.rb0.setVisibility(View.GONE);
                ui.rb1.setChecked(true);
                setAmPm(true);
            }
        }
        ui.rgAmPm.setOnCheckedChangeListener(this);
        ui.tvBtnReserve.setOnClickListener((view) -> {
            if (view.isEnabled() && eventListener != null) {
                eventListener.onReserveTime(adapter.getSelectedItem());
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 오후면 true로 함수 호출, 오전이면 false로 함수 호출.
        setAmPm(checkedId == R.id.rb_1);
    }

    private OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof ReserveDtVO && adapter != null) {
                adapter.setSelectedItem((ReserveDtVO) tag);
                adapter.notifyDataSetChanged();
                setEnabledBtn(true);
            }
        }
    };

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setDatas(List<ReserveDtVO> datas) {
        this.datas = datas;
    }

    private boolean setAmPm(boolean isPm) {
        if (datas == null) {
            return false;
        }

        List<ReserveDtVO> timeList = getTimeList(isPm);
        if (timeList != null) {
            setEnabledBtn(false);
            adapter.clearSelectedItem();
            adapter.setRows(timeList);
            adapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    private List<ReserveDtVO> getTimeList(boolean isPm) {
        if (datas == null) {
            return null;
        }

        // 정오 시간 객체 생성.
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);

        if (isPm) {
            // 오후
            return datas.stream().filter(it -> it.isAfter(calendar)).collect(Collectors.toList());
        } else {
            // 오전
            return datas.stream().filter(it -> it.isBefore(calendar)).collect(Collectors.toList());
        }
    }

    private void setEnabledBtn(boolean isEnabled) {
        ui.tvBtnReserve.setEnabled(isEnabled);

        if (isEnabled) {
            ui.tvBtnReserve.setBackgroundResource(R.drawable.ripple_bg_111111);
        } else {
            ui.tvBtnReserve.setBackgroundResource(R.drawable.bg_1a141414);
        }
    }
}
