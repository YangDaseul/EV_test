package com.genesis.apps.ui.main.service;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.databinding.ItemFilterBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Name : ChargeSearchFilterAdapter
 *
 * @author Ki-man Kim
 * @since 2021-04-09
 */
public class ChargeSearchFilterAdapter extends BaseRecyclerViewAdapter2<ChargeSearchCategoryVO> {

    private SubFragment subFragment;

    public ChargeSearchFilterAdapter(SubFragment subFragment) {
        this.subFragment = subFragment;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(this.subFragment, getView(parent, R.layout.item_filter));
    }

    public List<ChargeSearchCategoryVO> getSelectedItems() {
        return getItems().stream().filter(ChargeSearchCategoryVO::isSelected).collect(Collectors.toList());
    }

    public void clearSelectedItem() {
        List<ChargeSearchCategoryVO> list = getItems();
        for (ChargeSearchCategoryVO item : list) {
            item.clearSelectedItems();
        }
    }

    public static class ItemViewHolder extends BaseViewHolder<ChargeSearchCategoryVO, ItemFilterBinding> {
        private SubFragment subFragment;

        public ItemViewHolder(SubFragment subFragment, View itemView) {
            super(itemView);
            this.subFragment = subFragment;
        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item) {
            Context context = getContext();
            ItemFilterBinding binding = getBinding();
            binding.setSubFragment(this.subFragment);

            binding.tvName.setTag(item);
            binding.tvName.setSelected(item.isSelected());
            if (item.getSelectedItem().size() > 0) {
                binding.tvName.setText(
                        item.getSelectedItem().stream()
                                .filter(it -> it.getTitleResId() != 0)
                                .map(it -> context.getString(it.getTitleResId()))
                                .collect(Collectors.joining("/")));
            } else {
                binding.tvName.setText(item.getTitleResId());
            }

            switch (item.getComponentType()) {
                case CHECK:
                case RADIO: {
                    binding.tvName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.selector_ic_open, 0);
                    break;
                }
                case ONLY_ONE:
                default: {
                    binding.tvName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    break;
                }
            }
//            binding.tvName.setChecked(item.isCheck);
//            binding.tvName.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                item.isCheck = isChecked;
//            });
        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item, int pos) {

        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ItemViewHolder
} // end of class ChargeSearchFilterAdapter
