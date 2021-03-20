package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomTwoButtonIndivTermBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import androidx.annotation.NonNull;

public class BottomTwoButtonIndivTerm extends BaseBottomDialog<DialogBottomTwoButtonIndivTermBinding> {

    private String title;
    private Runnable eventTerm;
    public BottomTwoButtonIndivTerm(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_two_button_indiv_term);
        setAllowOutTouch(true);

        ui.tvYes.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();
                if(yes!=null){
                    yes.run();
                }
            }
        });

        ui.tvNo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();
                if(no!=null){
                    no.run();
                }
            }
        });

        ui.btnTerm.lWhole.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(eventTerm!=null){
                    eventTerm.run();
                }
            }
        });
    }

    public void setEventTerm(Runnable eventTerm){
        this.eventTerm = eventTerm;
    }

}
