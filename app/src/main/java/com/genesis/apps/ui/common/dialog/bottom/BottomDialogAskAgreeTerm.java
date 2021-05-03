package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

public class BottomDialogAskAgreeTerm<T extends ViewDataBinding> extends BaseBottomDialog<T> {
    public boolean inputConfirmed = false;
    public TermsAdapterTest adapter;
    public List<TermVO> termList;
    public OnSingleClickListener onSingleClickListener;
    public String title;

    public BottomDialogAskAgreeTerm(@NonNull Context context, int theme, OnSingleClickListener listener) {
        super(context, theme);
        this.onSingleClickListener = listener;
    }

    public void init(List<TermVO> termList) {
        this.termList = termList;
        inputConfirmed = false;
        adapter = new TermsAdapterTest(this, onSingleClickListener);
        adapter.setRows(termList);
    }
    public void setTermEsnAgmtYn(boolean isTermEsnAgmtYn){
        adapter.setTermEsnAgmtYn(isTermEsnAgmtYn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAllowOutTouch(true);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAdapter() {
    }
    //전체동의를 사용자가 누르면 각 약관 항목 전체에 동의여부 전파
    public void setAllClickListener() {
    }

    public void setAllAgree(boolean checked) {
    }
    public boolean getAllAgree() {
        return false;
    }
    public boolean isInputConfirmed() {
        return inputConfirmed;
    }

    public void validateCheck(SparseBooleanArray selectedItems) {
        boolean isValidate = true;

        for(int i=0; i<termList.size(); i++) {
            TermVO item = termList.get(i);

            if("Y".equals(item.getTermEsnAgmtYn())) {
                boolean isEmpty = true;
                for(int j=0; j<selectedItems.size(); j++) {
                    if(selectedItems.keyAt(j) == i) {
                        isEmpty = false;

                        break;
                    }
                }

                if(isEmpty) {
                    isValidate = false;

                    break;
                }
            }
        }

        setEnabledBtn(isValidate);
    }

    public void setEnabledBtn(boolean isEnabled) {
    }
}
