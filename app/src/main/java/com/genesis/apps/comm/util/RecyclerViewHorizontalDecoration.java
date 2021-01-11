package com.genesis.apps.comm.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHorizontalDecoration extends RecyclerView.ItemDecoration {

    private final int divEnd;

    public RecyclerViewHorizontalDecoration(int divEnd)
    {
        this.divEnd = divEnd;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = divEnd;
    }
}