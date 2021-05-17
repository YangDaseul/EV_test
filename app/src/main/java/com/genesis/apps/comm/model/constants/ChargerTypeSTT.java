package com.genesis.apps.comm.model.constants;

import androidx.annotation.StringRes;

import com.genesis.apps.R;

/**
 * Enum Name : ChargerTypeSTT
 *
 * @author Ki-man Kim
 * @since 2021-05-14
 */
public enum ChargerTypeSTT {
    SUPER_SPEED("001", R.string.sm_evss01_22),
    HIGH_SPEED("010", R.string.sm_evss01_23),
    SLOW_SPEED("100", R.string.sm_evss01_24);

    @StringRes
    private int titleResId;

    private String code;

    ChargerTypeSTT(String code, int titleResId) {
        this.code = code;
        this.titleResId = titleResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public String getCode() {
        return code;
    }
} // end of enum ChargerTypeSTT
