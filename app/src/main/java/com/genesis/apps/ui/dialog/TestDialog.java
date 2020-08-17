package com.genesis.apps.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogTestBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class TestDialog extends BaseBottomDialog<DialogTestBinding> {

    public TestDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test);

//        final BottomSheetBehavior behavior = BottomSheetBehavior.from(ui.llBottom);



        ui.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ui.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

//                if(hasFocus)
//                    behavior.setState(BottomSheetBehavior.STATE_DRAGGING);

            }
        });


    }



}
