package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomSonaxBranchBinding;

public class BottomDialogInputBranchCode extends BaseBottomDialog<DialogBottomSonaxBranchBinding> {
    //todo 레이아웃에는 하드코딩 돼 있으니 혹시 수정하게 되면 같이 수정
    private static final int BRANCH_CODE_MAX_LENGTH = 8;

    private String branchCode;
    private boolean inputConfirmed = false;

    public BottomDialogInputBranchCode(@NonNull Context context, String branchCodeFromServer, int theme) {
        super(context, theme);
        branchCode = branchCodeFromServer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_sonax_branch);
        setAllowOutTouch(true);

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
                setError(!validateInput(editable.toString()));
            }
        });

        //입력창 초기 값은 서버에서 준 값
        ui.etSonaxBranchNo.setText(branchCode);
        
        ui.etSonaxBranchNo.selectAll();

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
            String inputStr = ui.etSonaxBranchNo.getText().toString();

            // 유효성검사
            if (validateInput(inputStr)) {
                //유효값 입력하고 확인버튼 눌렀음
                branchCode = inputStr;
                inputConfirmed = true;
                dismiss();
            } else {
                //무효값 입력하고 확인버튼 눌렀음
                setError(true);
            }
        });

    }

    //지점코드 유효성 검사. 길이만 검사한다.
    private boolean validateInput(String input) {
        return 0 < input.length() && input.length() <= BRANCH_CODE_MAX_LENGTH;
    }

    //에러 메시지 표시하기/끄기
    private void setError(boolean enable) {
        if (enable) {
            ui.lDiaBottomSonaxBranchEdit.setError(getContext().getString(R.string.cw_branch_code_error));
            inputConfirmed = false;
        } else {
            ui.lDiaBottomSonaxBranchEdit.setError(null);
        }
    }

    public String getBranchCode() {
        return branchCode;
    }

    public boolean isInputConfirmed() {
        return inputConfirmed;
    }
}
