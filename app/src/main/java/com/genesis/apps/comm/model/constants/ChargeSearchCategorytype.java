package com.genesis.apps.comm.model.constants;

import androidx.annotation.StringRes;

import com.genesis.apps.R;

/**
 * @author Ki-man Kim
 */
public enum ChargeSearchCategorytype {
    ALL(R.string.sm_evss01_17, null),
    GENESIS(R.string.sm_evss01_18, "GN"),
    E_PIT(R.string.sm_evss01_19, "EP"),
    HI_CHARGER(R.string.sm_evss01_20, null),

    SUPER_SPEED(R.string.sm_evss01_22, "Y"),
    HIGH_SPEED(R.string.sm_evss01_23, "Y"),
    SLOW_SPEED(R.string.sm_evss01_24, "Y"),

    S_TRAFFIC_CRADIT_PAY(R.string.sm_evss01_26, null),
    CAR_PAY(R.string.sm_evss01_27, "Y");

    @StringRes
    private int titleResId;

    private String code;

    ChargeSearchCategorytype(int titleResId, String code) {
        this.titleResId = titleResId;
        this.code = code;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public String getCode() {
        return code;
    }
} // end of enum ChargeSearchCategorytype
