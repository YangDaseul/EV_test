package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomTwoButtonTermBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

public class BottomTwoButtonTerm extends BaseBottomDialog<DialogBottomTwoButtonTermBinding> {

    private String title;
    private String content;

    private Runnable eventTerm;

    public BottomTwoButtonTerm(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_two_button_term);
        setAllowOutTouch(true);

        ui.setTitle(title);
        ui.setContent(content);

        ui.tvYes.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();
                if (yes != null) {
                    yes.run();
                }
            }
        });

        ui.tvNo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();
                if (no != null) {
                    no.run();
                }
            }
        });

        ui.btnTerm.lWhole.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (eventTerm != null) {
                    eventTerm.run();
                }
            }
        });
    }

    public void setEventTerm(Runnable eventTerm) {
        this.eventTerm = eventTerm;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
