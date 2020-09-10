package com.genesis.apps.ui.view.viewholder;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<D,B> extends RecyclerView.ViewHolder{

    private final B binding;
    private Context context;
    public BaseViewHolder(View itemView) {
        super(itemView);
        binding = (B) DataBindingUtil.bind(itemView);
        setContext(itemView.getContext());
    }

//    public abstract int getLayout();

    public abstract void onBindView(D item);
    public abstract void onBindView(D item, int pos);
    public abstract void onBindView(D item, int pos, SparseBooleanArray selectedItems);

    public B getBinding() {
        return binding;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
