package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.databinding.Frame3pBinding;
import com.genesis.apps.ui.fragment.SubFragment;
import com.genesis.apps.ui.view.BaseRecyclerViewAdapter;
import com.genesis.apps.ui.view.Item;
import com.genesis.apps.ui.view.TestItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class FragThird extends SubFragment<Frame3pBinding> {
    TestItemAdapter testItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_3p);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        me.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        testItemAdapter = new TestItemAdapter();
        me.recyclerView.setAdapter(testItemAdapter);


    }

    @Override
    public void onRefresh() {
        setItemsToAdvancedItemAdapter(Item.getSampleItems());
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public void setItemsToAdvancedItemAdapter(List<Item> items) {
        List<BaseRecyclerViewAdapter.Row<?>> rows = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (TextUtils.equals(item.getType(), "A")) {
                rows.add(BaseRecyclerViewAdapter.Row.create(item, TestItemAdapter.VIEW_TYPE_A));
            } else {
                rows.add(BaseRecyclerViewAdapter.Row.create(item, TestItemAdapter.VIEW_TYPE_B));
            }
        }

        testItemAdapter.setRows(rows);
        testItemAdapter.notifyDataSetChanged();
    }

}

