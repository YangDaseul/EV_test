package com.genesis.apps.comm.model.constants;

import androidx.annotation.StringRes;

import com.genesis.apps.R;

/**
 * Enum Name : ChargerStatus
 *
 * @author Ki-man Kim
 * @since 2021-04-29
 */
public enum ChargerStatus {
    UNKNOWN("0", "알수 없음", R.string.sm_evss04_17),
    COM_ERROR("1", "통신이상",R.string.sm_evss04_17),
    AVAILABLE("2","충전대기", R.string.sm_evss04_15),
    CHARGING("3", "충전중", R.string.sm_evss04_18),
    OUTOFORDER("4", "운영중지", R.string.sm_evss04_17),
    SHUTDOWNED("5", "점검중", R.string.sm_evss04_17),
    RESERVED("6", "예약중", R.string.sm_evss04_15),
    UNCHECK("9", "상태 미확인", R.string.sm_evss04_17);

    private String statusCd;
    private String description;
    @StringRes
    int titleResId;

    ChargerStatus(String statusCd, String description, int titleResId) {
        this.statusCd = statusCd;
        this.description = description;
        this.titleResId = titleResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
} // end of enum ChargerStatus
