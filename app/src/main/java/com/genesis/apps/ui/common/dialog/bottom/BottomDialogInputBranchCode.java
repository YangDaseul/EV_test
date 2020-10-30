package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.databinding.DialogBottomSonaxBranchBinding;

public class BottomDialogInputBranchCode extends BaseBottomDialog<DialogBottomSonaxBranchBinding> {


    public BottomDialogInputBranchCode(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_sonax_branch);
        setAllowOutTouch(true);

        //최초 상태는 입력 창 비었으니 에러 메시지 표시함
        setError(true);

        ui.etSonaxBranchNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setError(TextUtils.isEmpty(editable.toString()));
            }
        });


        ui.etSonaxBranchNo.setOnEditorActionListener((textView, actionId, keyEvent) -> {
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

        ui.btnOk.setOnClickListener(view -> {
            if (true
                //todo 유효성검사 : 길이만 봄
            ) {
                //무효값 입력하고 확인버튼 누름
                setError(true);
            } else {
                //유효값 입력하고 확인버튼 누름

                dismiss();
            }
        });

    }

    //에러 메시지 표시하기/끄기
    private void setError(boolean enable) {
        if (enable) {
            ui.lDiaBottomSonaxBranchEdit.setError(getContext().getString(R.string.cw_branch_code_error));
        } else {
            ui.lDiaBottomSonaxBranchEdit.setError(null);
        }
    }

}
