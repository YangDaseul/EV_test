package com.genesis.apps.comm.model.constants;

import androidx.annotation.StringRes;

import com.genesis.apps.R;

/**
 * @author Ki-man Kim
 */
public enum ChargeSearchCategorytype {
    ALL(R.string.sm_evss01_17, null),
    GENESIS(R.string.sm_evss01_18, "GEN"),
    E_PIT(R.string.sm_evss01_19, "EPT"),
    HI_CHARGER(R.string.sm_evss01_20, "HCR"),

    SUPER_SPEED(R.string.sm_evss01_22, "SUPER"),
    HIGH_SPEED(R.string.sm_evss01_23, "HIGH"),
    SLOW_SPEED(R.string.sm_evss01_24, "SLOW"),

    CREDIT_CARD(R.string.sm_evss04_06, "CRT"),
    S_TRAFFIC_CRADIT_PAY(R.string.sm_evss01_26, "STP"),
    CAR_PAY(R.string.sm_evss01_27, "GCP");

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
