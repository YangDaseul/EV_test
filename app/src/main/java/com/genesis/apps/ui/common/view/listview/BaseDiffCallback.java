package com.genesis.apps.ui.common.view.listview;

import androidx.recyclerview.widget.DiffUtil;

import com.genesis.apps.comm.model.BaseData;

public class BaseDiffCallback<T extends BaseData> extends DiffUtil.ItemCallback<T> {
    @Override
    public boolean areItemsTheSame(T oldItem, T newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(T oldItem, T newItem) {
        return true;
    }
}