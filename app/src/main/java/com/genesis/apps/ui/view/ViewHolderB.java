package com.genesis.apps.ui.view;

import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ItemTestBinding;

/**
 * Created by tonyjs on 16. 4. 9..
 */
public class ViewHolderB extends BaseViewHolder<Item, ItemTestBinding> {

    public ViewHolderB(View itemView) {
        super(itemView);
    }

    @Override
    public int getLayout() {
        return R.layout.item_test;
    }

    @Override
    public void onBindView(Item item) {
        getBinding().nameTextView.setText("I am B");
    }

}

