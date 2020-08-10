package com.genesis.apps.ui.view;

import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.ExampleResVO;
import com.genesis.apps.databinding.ItemTestBinding;

public class ViewHolderC extends BaseViewHolder<String,ItemTestBinding> {
    public ViewHolderC(View itemView) {
        super(itemView);
    }

    @Override
    public int getLayout() {
        return R.layout.item_test;
    }

    @Override
    public void onBindView(BaseRecyclerViewAdapter.Row<String> item) {
        getBinding().nameTextView.setText(item.getItem());
    }

}

