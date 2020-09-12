package com.genesis.apps.ui.common.view.listview.test;

import androidx.recyclerview.widget.DiffUtil;

public class LinkDiffCallback extends DiffUtil.ItemCallback<Link> {
    @Override
    public boolean areItemsTheSame(Link oldItem, Link newItem) {
        return oldItem.getUrl().equals(newItem.getUrl());
    }

    @Override
    public boolean areContentsTheSame(Link oldItem, Link newItem) {
        return oldItem.getUrl().equals(newItem.getUrl());
    }
}
