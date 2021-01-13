package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.databinding.DialogBottomModifyCarVrnBinding;

import androidx.annotation.NonNull;

public class DialogCarRgstNo extends BaseBottomDialog<DialogBottomModifyCarVrnBinding> {

    private String carRgstNo="";

    public DialogCarRgstNo(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_modify_car_vrn);
        setAllowOutTouch(true);
//        ui.lEdit.setError(getContext().getString(R.string.gm_carlst_p01_1));
        ui.etCarRgstNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(TextUtils.isEmpty(editable.toString())){
                    ui.lEdit.setError(getContext().getString(R.string.gm_carlst_p01_1));
                }else{
                    ui.lEdit.setError(null);
                }

            }
        });

        ui.btnOk.setOnClickListener(view -> {
            if(!StringRe2j.matches(ui.etCarRgstNo.getText().toString(), getContext().getString(R.string.check_car_vrn))){
                ui.lEdit.setError(getContext().getString(R.string.gm_carlst_p01_8));
            }else{
                setCarRgstNo(ui.etCarRgstNo.getText().toString());
                dismiss();
            }
        });

        ui.etCarRgstNo.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    ui.btnOk.performClick();
                    break;
                default:
                    // 기본 엔터키 동작
                    return false;
            }
            return true;
        });

        SoftKeyboardUtil.showKeyboard(getContext());

    }

    public String getCarRgstNo() {
        return carRgstNo;
    }

    public void setCarRgstNo(String carRgstNo) {
        this.carRgstNo = carRgstNo;
    }


}
