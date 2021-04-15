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

import java.util.List;

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

    public void clearCheckState() {
        List<DummyFilterData> list = getItems();
        for (DummyFilterData item : list) {
            item.isCheck = false;
        }
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends BaseViewHolder<DummyFilterData, ItemFilterBinding> {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(DummyFilterData item) {
            ItemFilterBinding binding = getBinding();
            binding.tvName.setText(item.getName());
            binding.tvName.setChecked(item.isCheck);
            binding.tvName.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.isCheck = isChecked;
            });
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
        private boolean isCheck;

        public DummyFilterData setName(String name) {
            this.name = name;
            return this;
        }

        public String getName() {
            return name;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
} // end of class ChargeSearchFilterAdapter
