package com.genesis.apps.ui.view;

import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ItemTestBinding;

/**
 * Created by tonyjs on 16. 4. 9..
 */
public class ViewHolderA extends BaseViewHolder<Item,ItemTestBinding> {
    public ViewHolderA(View itemView) {
        super(itemView);
    }

    @Override
    public int getLayout() {
        return R.layout.item_test;
    }

    @Override
    public void onBindView(Item item) {
        getBinding().nameTextView.setText(item.getName());
    }
}

