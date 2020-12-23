package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomContentBinding;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BottomContentDialog extends BaseBottomDialog<DialogBottomContentBinding> {

    private String title;
    private String content = "";

    public BottomContentDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_content);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);
        ui.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(ui.etContent.isFocusable()) {
                    try {
                        byte[] bytetext = ui.etContent.getText().toString().getBytes("KSC5601");
                        ui.tvByte.setText(String.valueOf(bytetext.length));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String after_text = s.toString();
                try {
                    byte[] getbyte = after_text.getBytes("KSC5601");
                    if (getbyte.length > 500) {
                        s.delete(s.length()-2, s.length()-1);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        ui.tvNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = ui.etContent.getText().toString();

                dismiss();
            }
        });
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
