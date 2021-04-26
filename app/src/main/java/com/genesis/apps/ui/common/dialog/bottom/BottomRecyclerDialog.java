package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomRecyclerBinding;

import java.util.ArrayList;

public class BottomRecyclerDialog extends BaseBottomDialog<DialogBottomRecyclerBinding> {

    private String title;
    private String bottomButtonTitle;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecyclerView.ItemDecoration> decorationList = new ArrayList<>();
    private ButtonClickListener buttonClickListener;

    public interface ButtonClickListener {
        void onBottomBtnClick(View view);
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

        if (bottomButtonTitle != null) {
            ui.tvBtnOk.setText(bottomButtonTitle);
            ui.tvBtnOk.setVisibility(View.VISIBLE);
            ui.tvBtnOk.setOnClickListener((view) -> {
                this.dismiss();
                if (this.buttonClickListener != null) {
                    this.buttonClickListener.onBottomBtnClick(view);
                }
            });
        }

        if (this.layoutManager != null) {
            ui.rv.setLayoutManager(this.layoutManager);
        }
        for (RecyclerView.ItemDecoration item : decorationList) {
            ui.rv.addItemDecoration(item);
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
        private String bottomButtonTitle;
        private RecyclerView.Adapter adapter;
        private RecyclerView.LayoutManager layoutManager;
        private ArrayList<RecyclerView.ItemDecoration> decorationList = new ArrayList<>();
        private ButtonClickListener buttonClickListener;

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

        public Builder setBottomButtonTitle(@StringRes int bottomButtonTitle) {
            return setBottomButtonTitle(context.getString(bottomButtonTitle));
        }

        public Builder setBottomButtonTitle(String bottomButtonTitle) {
            this.bottomButtonTitle = bottomButtonTitle;
            return this;
        }

        public Builder setLayoutManager(@NonNull RecyclerView.LayoutManager manager) {
            this.layoutManager = manager;
            return this;
        }

        public Builder addDecoration(RecyclerView.ItemDecoration decoration) {
            this.decorationList.add(decoration);
            return this;
        }

        public Builder setButtonClickListener(ButtonClickListener buttonClickListener) {
            this.buttonClickListener = buttonClickListener;
            return this;
        }

        public BottomRecyclerDialog build() {
            BottomRecyclerDialog dialog = new BottomRecyclerDialog(this.context, R.style.BottomSheetDialogTheme);
            if (this.title != null) {
                dialog.title = this.title;
            }

            if (this.bottomButtonTitle != null) {
                dialog.bottomButtonTitle = this.bottomButtonTitle;
            }

            if (this.adapter != null) {
                dialog.adapter = this.adapter;
            }

            if (this.layoutManager != null) {
                dialog.layoutManager = this.layoutManager;
            }

            if (this.decorationList.size() > 0) {
                dialog.decorationList.addAll(this.decorationList);
            }

            if (this.buttonClickListener != null) {
                dialog.buttonClickListener = this.buttonClickListener;
            }

            return dialog;
        }
    } // end of class Builder
} // end of class BottomListDialogWrnLghtCode
