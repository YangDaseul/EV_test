package com.genesis.apps.ui.main.service;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.databinding.ItemFilterBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

/**
 * Class Name : ChargeSearchFilterAdapter
 *
 * @author Ki-man Kim
 * @since 2021-04-09
 */
public class ChargeSearchFilterAdapter extends BaseRecyclerViewAdapter2<ChargeSearchFilterAdapter.DummyFilterData> {

    public ChargeSearchFilterAdapter() {
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(getView(parent, R.layout.item_filter));
    }

    public static class ItemViewHolder extends BaseViewHolder<DummyFilterData, ItemFilterBinding> {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(DummyFilterData item) {
            ItemFilterBinding binding = getBinding();
            binding.tvName.setText(item.getName());
        }

        @Override
        public void onBindView(DummyFilterData item, int pos) {

        }

        @Override
        public void onBindView(DummyFilterData item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ItemViewHolder

    public static class DummyFilterData extends BaseData {
        private String name;

        public DummyFilterData setName(String name) {
            this.name = name;
            return this;
        }

        public String getName() {
            return name;
        }
    }
} // end of class ChargeSearchFilterAdapter
