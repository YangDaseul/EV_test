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
    /**
     * 상태 불분명
     */
    UNKNOWN(R.string.sm_evss04_17),
    /**
     * 충전가능
     */
    AVAILABLE(R.string.sm_evss04_15),
    /**
     * 충전중
     */
    CHARGING(R.string.sm_evss04_18),
    /**
     * 고장/점검
     */
    OUTOFORDER(R.string.sm_evss04_17),
    /**
     * 통신장애
     */
    COM_ERROR(R.string.sm_evss04_17),
    /**
     * 통신미연결
     */
//    DISCONNECTION(R.string.sm_evss04_17),
    /**
     * 충전종료
     */
//    CHARGED(R.string.sm_evss04_15),
    /**
     * 계획 정지
     */
//    PLANNED(R.string.sm_evss04_17),
    /**
     * 예약
     */
//    RESERVED(R.string.sm_evss04_15);
    /**
     * 통신장애
     */
    SHUTDOWNED(R.string.sm_evss04_17);

    @StringRes
    int titleResId;

    ChargerStatus(int titleResId) {
        this.titleResId = titleResId;
    }

    public int getTitleResId() {
        return titleResId;
    }
} // end of enum ChargerStatus
