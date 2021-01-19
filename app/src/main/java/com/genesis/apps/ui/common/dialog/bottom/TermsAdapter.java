package com.genesis.apps.ui.common.dialog.bottom;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemTermBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Objects;

public class TermsAdapter extends BaseRecyclerViewAdapter2<TermVO> {
    private static final String TAG = TermsAdapter.class.getSimpleName();
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private BottomDialogAskAgreeTerms bottomDialogAskAgreeTerms;
    public OnSingleClickListener onSingleClickListener;

    //설마 그럴 일은 없다고 생각하지만 add/remove 호출하면 안됨
    public TermsAdapter(BottomDialogAskAgreeTerms dialog, OnSingleClickListener listener) {
        bottomDialogAskAgreeTerms = dialog;
        onSingleClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;//다 똑같음
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TermViewHolder(getView(parent, R.layout.item_term));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.d(TAG, "TermsAdapter onBindViewHolder position : " + position);
        holder.onBindView(getItem(position), position, selectedItems);
    }

    public void setAllCheck(boolean checked) {
        for (int i = 0; i < getItemCount(); i++) {
            selectedItems.put(i, checked);
        }
        notifyDataSetChanged();
    }

    public boolean isAll() {
        for (int i = 0; i < getItemCount(); i++) {
            if (!selectedItems.get(i)) {
                return false;
            }
        }
        return true;
    }


    public class TermViewHolder extends BaseViewHolder<TermVO, ItemTermBinding> {

        public TermViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "TermViewHolder: ");
        }

        @Override
        public void onBindView(TermVO item) {
            //do nothing
        }

        @Override
        public void onBindView(TermVO item, int pos) {
            //do nothing
        }

        @Override
        public void onBindView(TermVO item, int pos, SparseBooleanArray selectedItems) {
            Log.d(TAG, "TermViewHolder onBindView(): ");

            String termName = item.getTermNm();
            if (Objects.equals(item.getTermEsnAgmtYn(), "Y")) {
                termName += getContext().getString(R.string.terms_essential);
            }
            getBinding().cb.setText(Html.fromHtml(termName, Html.FROM_HTML_MODE_COMPACT));
            getBinding().cb.setChecked(selectedItems.get(pos));

            getBinding().cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedItems.put(pos, true);
                } else {
                    selectedItems.delete(pos);
                }

//                bottomDialogAskAgreeTerms.validateCheck(selectedItems);
                bottomDialogAskAgreeTerms.setAllAgree(isAll());
            });

            getBinding().ivArrow.setTag(R.id.tag_term_vo, item);
            getBinding().setListener(onSingleClickListener);
        }
    }
}
