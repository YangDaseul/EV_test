package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.databinding.DialogBottomNowOrReserveBinding;
import com.genesis.apps.databinding.DialogBottomSonaxBranchBinding;

public class BottomDialogReqNowOrReserve extends BaseBottomDialog<DialogBottomNowOrReserveBinding> {
    private static final String TAG = BottomDialogReqNowOrReserve.class.getSimpleName();

    private boolean inputConfirmed = false;

    public BottomDialogReqNowOrReserve(@NonNull Context context, VehicleVO vehicleVO, String price, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_now_or_reserve);
        setAllowOutTouch(true);

//        ui.tietDiaBottomNowOrReserveRequestInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //do nothing
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                setError(!validateInput(editable.toString()));
//            }
//        });

        ui.tietDiaBottomNowOrReserveRequestInput.setOnEditorActionListener((textView, actionId, keyEvent) -> {
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
//            String inputStr = ui.etSonaxBranchNo.getText().toString();

            // 유효성검사
            if (false) {
                //유효값 입력하고 확인버튼 눌렀음
                //todo impl
//                branchCode = inputStr;
                inputConfirmed = true;
                dismiss();
            } else {
                //무효값 입력하고 확인버튼 눌렀음
//                setError(true);
            }
        });

    }

//    //지점코드 유효성 검사. 길이만 검사한다.
//    private boolean validateInput(String input) {
//        return 0 < input.length() && input.length() <= BRANCH_CODE_MAX_LENGTH;
//    }

    //에러 메시지 표시하기/끄기
//    private void setError(boolean enable) {
//        if (enable) {
//            ui.lDiaBottomSonaxBranchEdit.setError(getContext().getString(R.string.cw_branch_code_error));
//            inputConfirmed = false;
//        } else {
//            ui.lDiaBottomSonaxBranchEdit.setError(null);
//        }
//    }

    public boolean isInputConfirmed() {
        return inputConfirmed;
    }
}
