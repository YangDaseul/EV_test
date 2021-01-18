package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.WashReserveVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.databinding.DialogBottomSonaxBranchBinding;

import java.util.List;

public class BottomDialogInputBranchCode extends BaseBottomDialog<DialogBottomSonaxBranchBinding> {
    private static final int BRANCH_CODE_MAX_LENGTH = 8;

    private String branchCode;
    private boolean inputConfirmed = false;
    private List<WashReserveVO> rsvtList;

    public BottomDialogInputBranchCode(@NonNull Context context, String branchCodeFromServer, int theme, List<WashReserveVO> rsvtList) {
        super(context, theme);
        branchCode = branchCodeFromServer;
        this.rsvtList = rsvtList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_sonax_branch);
        setAllowOutTouch(true);

        ui.setMaxLength(BRANCH_CODE_MAX_LENGTH);

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
//                setError(!validateInput(editable.toString()));
            }
        });

        // input box 공란 수정
//        // 입력창 초기 값은 서버에서 준 값
//        ui.etSonaxBranchNo.setText(branchCode);

        SoftKeyboardUtil.showKeyboard(getContext());
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
    //2021-01-18 유효성 검사가 세차지점 전체로 변경되면서 APP에서 체크 불가로 제거 됨
    private boolean validateInput(String input) {
        return true;
//        WashReserveVO target = null;
//        try{
//            if(rsvtList!=null&&rsvtList.size()>0){
//                target = rsvtList.stream().filter(data -> (StringUtil.isValidString(data.getBrnhCd()).equalsIgnoreCase(input))).findFirst().orElse(null);
//            }
//        }catch (Exception e){
//            target = null;
//        }
//        return target != null;
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
