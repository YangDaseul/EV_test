package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.databinding.DialogBottomTermsBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.List;

public class BottomDialogAskAgreeTerms extends BaseBottomDialog<DialogBottomTermsBinding> {
    private static final String TAG = BottomDialogAskAgreeTerms.class.getSimpleName();

    private boolean inputConfirmed = false;
    private TermsAdapter adapter;
    private OnSingleClickListener onSingleClickListener;

    public BottomDialogAskAgreeTerms(@NonNull Context context, int theme, OnSingleClickListener listener) {
        super(context, theme);
        this.onSingleClickListener = listener;
    }

    public void init(List<TermVO> termList) {
        inputConfirmed = false;

        adapter = new TermsAdapter(this, onSingleClickListener);
        adapter.setRows(termList);
        //바인딩 없어서 뷰에 붙이는 건 나중에
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_terms);
        setAllowOutTouch(true);

        setAdapter();

        //전체동의 버튼
        setAllClickListener();

        //확인버튼
        ui.tvBottomTermsOkBtn.setOnClickListener(view -> {

            //필수항목 모두 동의하고 확인버튼 눌렀음
            if (getAllAgree()) {
                inputConfirmed = true;
                dismiss();
            }
        });
    }

    private void setAdapter() {
        ui.rvDiaBottomTermsList.setLayoutManager(new LinearLayoutManager(getContext()));
        ui.rvDiaBottomTermsList.setHasFixedSize(true);
        ui.rvDiaBottomTermsList.setAdapter(adapter);
    }

    //전체동의를 사용자가 누르면 각 약관 항목 전체에 동의여부 전파
    //tODO 이거 또 t/f 반대로 가는 거 아닌가 의심스럽다
    private void setAllClickListener() {
        ui.cbAgreeAll.setOnClickListener((view) -> adapter.setAllCheck(ui.cbAgreeAll.isChecked()));
    }

    public void setAllAgree(boolean checked) {
        ui.cbAgreeAll.setChecked(checked);
    }

    private boolean getAllAgree() {
        return ui.cbAgreeAll.isChecked();
    }

    public boolean isInputConfirmed() {
        return inputConfirmed;
    }
}
