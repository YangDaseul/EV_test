package com.genesis.apps.ui.view;

import android.view.ViewGroup;

import com.genesis.apps.R;

public class TestItemAdapter extends BaseRecyclerViewAdapter {

    public static final int VIEW_TYPE_A = 0;
    public static final int VIEW_TYPE_B = 1;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPE_A) {
//            return new ViewHolderA(getView(parent, R.layout.item_test));
//        } else {
//            return new ViewHolderB(getView(parent, R.layout.item_test));
//        }

        return new ViewHolderC(getView(parent, R.layout.item_test));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}