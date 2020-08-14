package com.genesis.apps.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomDialog<T extends ViewDataBinding> extends BottomSheetDialog {
    T ui;

    public BottomDialog(@NonNull Context context) {
        super(context);
    }

    public BottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void setContentView(int layoutResId) {
        ui = inflate(layoutResId);
        super.setContentView(ui.getRoot());
    }

    private <T extends ViewDataBinding> T inflate(int layoutResId) {
        return DataBindingUtil.inflate(getLayoutInflater(), layoutResId, null, false);
    }

}
