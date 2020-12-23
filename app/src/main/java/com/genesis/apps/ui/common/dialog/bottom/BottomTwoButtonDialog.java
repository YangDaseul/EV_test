package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomTwoButtonBinding;
import com.genesis.apps.databinding.DialogTestBinding;

public class BottomTwoButtonDialog extends BaseBottomDialog<DialogBottomTwoButtonBinding> {

    private String title;

    public BottomTwoButtonDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_two_button);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);

        ui.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yes!=null){
                    yes.run();
                }
            }
        });

        ui.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(no!=null){
                    no.run();
                }
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
