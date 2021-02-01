package com.genesis.apps.ui.common.dialog.bottom.view;

import android.text.TextUtils;

import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.genesis.apps.comm.util.DateUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.List;

public class RejectDecorator implements DayViewDecorator {

    List<RepairReserveDateVO> reserveDateVOList = null;

    public RejectDecorator(List<RepairReserveDateVO> reserveDateVOList) {
        this.reserveDateVOList = reserveDateVOList;
    }

    @Override
    public boolean shouldDecorate(final CalendarDay day) {
        if (reserveDateVOList != null) {
            String checkDay = DateUtil.getDate(day.getCalendar().getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
            boolean isPossible = false;
            for (RepairReserveDateVO repairReserveDateVO : reserveDateVOList) {
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
