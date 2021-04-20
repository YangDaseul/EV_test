package com.genesis.apps.comm.model.constants;

import androidx.annotation.StringRes;

import com.genesis.apps.R;

/**
 * @author Ki-man Kim
 */
public enum ChargeSearchCategorytype {
    ALL(R.string.sm_evss01_17),
    GENESIS(R.string.sm_evss01_18),
    E_PIT(R.string.sm_evss01_19),
    HI_CHARGER(R.string.sm_evss01_20),

    SUPER_SPEED(R.string.sm_evss01_22),
    HIGH_SPEED(R.string.sm_evss01_23),
    SLOW_SPEED(R.string.sm_evss01_24),

    S_TRAFFIC_CRADIT_PAY(R.string.sm_evss01_26),
    CAR_PAY(R.string.sm_evss01_27);

    @StringRes
    private int titleResId;

    ChargeSearchCategorytype(int titleResId) {
        this.titleResId = titleResId;
    }

    public int getTitleResId() {
        return titleResId;
    }
} // end of enum ChargeSearchCategorytype
