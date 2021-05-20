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
    UNKNOWN("UNKNOWN", "알수 없음", R.string.sm_evss04_17),
    AVAILABLE("AVAILABLE","충전가능", R.string.sm_evss04_15),
    CHARGING("CHARGING", "충전중", R.string.sm_evss04_18),
    OUTOFORDER("OUTOFORDER", "고장/점검", R.string.sm_evss04_17),
    COM_ERROR("COM_ERROR", "통신장애",R.string.sm_evss04_17),
    SHUTDOWNED("SHUTDOWNED", "사용불가", R.string.sm_evss04_17);

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
