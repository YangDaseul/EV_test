package com.genesis.apps.ui.view;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<D,B> extends RecyclerView.ViewHolder{

    private final B binding;

    public BaseViewHolder(View itemView) {
        super(itemView);
        binding = (B) DataBindingUtil.bind(itemView);
    }

    public abstract int getLayout();

    public abstract void onBindView(D item);

    public B getBinding() {
        return binding;
    }
}
