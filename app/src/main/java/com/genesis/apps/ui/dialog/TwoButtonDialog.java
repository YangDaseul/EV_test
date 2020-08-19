package com.genesis.apps.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogTestBinding;

public class TwoButtonDialog extends BaseBottomDialog<DialogTestBinding> {

    public TwoButtonDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test);

        ui.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yes!=null){
                    yes.run();
                }
            }
        });

        ui.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(no!=null){
                    no.run();
                }
            }
        });
    }

}
