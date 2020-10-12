package com.genesis.apps.ui.common.view.listview.test;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.genesis.apps.R;
import com.genesis.apps.ui.common.activity.test.TestLayoutActivity;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
import com.genesis.apps.ui.common.view.viewholder.test.ViewHolderC;

public class TestItemAdapter extends BaseRecyclerViewAdapter {

    public static final String INTENT_LAYOUT_ID = "layoutId";
    public static final int VIEW_TYPE_A = 0;
    public static final int VIEW_TYPE_B = 1;

    private Context context;
    private int[] testLayoutList;

    public TestItemAdapter(Context context, int[] layoutList) {
        this.context = context;
        testLayoutList = layoutList;
    }

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
        int targetLayoutId = testLayoutList[position];
        String layoutName = context.getResources().getResourceName(targetLayoutId);

        holder.onBindView(layoutName);
        holder.itemView.setOnClickListener(v -> {
            showLayout(targetLayoutId);
        });

    }

    @Override
    public int getItemCount() {
        return testLayoutList.length;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    private void showLayout(int layoutId) {
        Intent intent = new Intent(context, TestLayoutActivity.class);
        intent.putExtra(INTENT_LAYOUT_ID, layoutId);
        context.startActivity(intent);
    }
}