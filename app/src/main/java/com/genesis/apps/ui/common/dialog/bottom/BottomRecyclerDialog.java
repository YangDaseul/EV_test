package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomRecyclerBinding;

public class BottomRecyclerDialog extends BaseBottomDialog<DialogBottomRecyclerBinding> {

    private String title;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private OnItemSelectListener onItemSelectListener;

    public interface OnItemSelectListener<T> {
        void onSelect(T item);
    }

    private BottomRecyclerDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_recycler);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);

        if (this.layoutManager != null) {
            ui.rv.setLayoutManager(this.layoutManager);
        }

        if (adapter != null) {
            ui.rv.setAdapter(adapter);
//            ui.rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    selectItem = ((TextView)view).getText().toString();
//                    dismiss();
//                }
//            });
        }
    }

    public static class Builder {
        private Context context;
        private String title;
        private RecyclerView.Adapter adapter;
        private RecyclerView.LayoutManager layoutManager;
        private OnItemSelectListener onItemSelectListener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setAdapter(@NonNull RecyclerView.Adapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder setTitle(@StringRes int title) {
            return setTitle(context.getString(title));
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setLayoutManager(@NonNull RecyclerView.LayoutManager manager) {
            this.layoutManager = manager;
            return this;
        }

        public Builder setOnItemSelectListener(OnItemSelectListener listener) {
            this.onItemSelectListener = listener;
            return this;
        }

        public BottomRecyclerDialog build() {
            BottomRecyclerDialog dialog = new BottomRecyclerDialog(this.context, R.style.BottomSheetDialogTheme);
            if (this.title != null) {
                dialog.title = this.title;
            }

            if (this.adapter != null) {
                dialog.adapter = this.adapter;
            }

            if (this.layoutManager != null) {
                dialog.layoutManager = this.layoutManager;
            }

            if (this.onItemSelectListener != null) {
                dialog.onItemSelectListener = this.onItemSelectListener;
            }

            return dialog;
        }
    } // end of class Builder
} // end of class BottomListDialogWrnLghtCode
