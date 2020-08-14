package com.genesis.apps.ui.dialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogTestBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TestDialog extends BottomDialog<DialogTestBinding> {

    public TestDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test);
    }
}
