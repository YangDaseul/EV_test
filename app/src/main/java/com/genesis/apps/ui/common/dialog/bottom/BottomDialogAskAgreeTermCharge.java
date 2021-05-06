package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.databinding.DialogBottomTermsChargeBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

public class BottomDialogAskAgreeTermCharge extends BottomDialogAskAgreeTerm<DialogBottomTermsChargeBinding> {

    public BottomDialogAskAgreeTermCharge(@NonNull Context context, int theme, OnSingleClickListener listener) {
        super(context, theme, listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_terms_charge);
        setAdapter();
        //전체동의 버튼
        setAllClickListener();
        setEnabledBtn(false);
        ui.tvBottomTermsOkBtn.setOnClickListener(view -> {
            //필수항목 모두 동의하고 확인버튼 눌렀음
            if (getAllAgree()) {
                inputConfirmed = true;
                dismiss();
            }
        });
        if(TextUtils.isEmpty(title)){
            title = getContext().getString(R.string.terms_title);
        }
        ui.lDiaBottomSonaxBranchTitle.setValue(title);
    }
    @Override
    public void setAdapter() {
        ui.rvDiaBottomTermsList.setLayoutManager(new LinearLayoutManager(getContext()));
        ui.rvDiaBottomTermsList.setHasFixedSize(true);
        ui.rvDiaBottomTermsList.setAdapter(adapter);
    }
    //전체동의를 사용자가 누르면 각 약관 항목 전체에 동의여부 전파
    @Override
    public void setAllClickListener() {
        ui.cbAgreeAll.setOnClickListener((view) -> adapter.setAllCheck(ui.cbAgreeAll.isChecked()));
    }
    @Override
    public void setAllAgree(boolean checked) {
        ui.cbAgreeAll.setChecked(checked);
    }
    @Override
    public boolean getAllAgree() {
        return ui.cbAgreeAll.isChecked();
    }
    @Override
    public void setEnabledBtn(boolean isEnabled) {
        ui.tvBottomTermsOkBtn.setEnabled(isEnabled);

        if(isEnabled) {
            ui.tvBottomTermsOkBtn.setBackgroundResource(R.drawable.ripple_bg_111111);
        } else {
            ui.tvBottomTermsOkBtn.setBackgroundResource(R.drawable.bg_1a141414);
        }
    }
}
