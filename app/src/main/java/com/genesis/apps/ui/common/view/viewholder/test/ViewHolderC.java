package com.genesis.apps.ui.common.view.viewholder.test;

import android.util.SparseBooleanArray;
import android.view.View;

import com.genesis.apps.databinding.ItemTestBinding;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

public class ViewHolderC extends BaseViewHolder<String,ItemTestBinding> {
    public ViewHolderC(View itemView) {
        super(itemView);
    }

//    @Override
//    public int getLayout() {
//        return R.layout.item_test;
//    }

    @Override
    public void onBindView(String item) {
        getBinding().nameTextView.setText(item);
    }

    @Override
    public void onBindView(String item, int pos) {

    }

    @Override
    public void onBindView(String item, int pos, SparseBooleanArray selectedItems) {

    }

}

