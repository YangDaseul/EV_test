package com.genesis.apps.comm.model.constants;

import androidx.annotation.StringRes;

import com.genesis.apps.R;

/**
 * Enum Name : ChargerStatus
 *
 * @author Ki-man Kim
 * @since 2021-04-29
 */
public enum ChargerStatusSTT {
    /**
     * 알수 없음
     */
    UNKNOWN("0", R.string.sm_evss04_17),
    /**
     * 통신이상
     */
    NETWORK_ERR("1", R.string.sm_evss04_17),
    /**
     * 충전대기
     */
    STAN_BY("2", R.string.sm_evss04_15),
    /**
     * 충전중
     */
    CHARGING("3", R.string.sm_evss04_18),
    /**
     * 운영중지
     */
    NO_USE("4", R.string.sm_evss04_17),
    /**
     * 점검중
     */
    NO_INSPECT("5", R.string.sm_evss04_17),
    /**
     * 예약중
     */
    RESERVED("6", R.string.sm_evss04_15),
    /**
     * 상태 미확인
     */
    NOT_FOUND_STATUS("9", R.string.sm_evss04_17);

    String code;

    @StringRes
    int titleResId;

    ChargerStatusSTT(String code, int titleResId) {
        this.code = code;
        this.titleResId = titleResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public String getCode() {
        return code;
    }
} // end of enum ChargerStatus
