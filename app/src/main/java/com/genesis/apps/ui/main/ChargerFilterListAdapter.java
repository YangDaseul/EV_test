package com.genesis.apps.ui.main;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO;
import com.genesis.apps.databinding.ItemFilterCheckBinding;
import com.genesis.apps.databinding.ItemFilterOnlyOneBinding;
import com.genesis.apps.databinding.ItemFilterRadioBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.List;

import static com.genesis.apps.comm.model.vo.ChargeSearchCategoryVO.COMPONENT_TYPE;

/**
 * @author Ki-man Kim
 */
public class ChargerFilterListAdapter extends BaseRecyclerViewAdapter2<ChargeSearchCategoryVO> {
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == COMPONENT_TYPE.ONLY_ONE.ordinal()) {
            return new ViewHolderTypeOnlyOne(getView(parent, R.layout.item_filter_only_one));
        } else if (viewType == COMPONENT_TYPE.RADIO.ordinal()) {
            return new ViewHolderTypeRadio(getView(parent, R.layout.item_filter_radio));
        } else if (viewType == COMPONENT_TYPE.CHECK.ordinal()) {
            return new ViewHolderTypeCheck(getView(parent, R.layout.item_filter_check));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return getItems().get(position).getComponentType().ordinal();
    }

    public static class ViewHolderTypeOnlyOne extends BaseViewHolder<ChargeSearchCategoryVO, ItemFilterOnlyOneBinding> {
        public ViewHolderTypeOnlyOne(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item) {
            ItemFilterOnlyOneBinding binding = getBinding();
            binding.tvName.setText(item.getTitleResId());
            binding.tvName.setSelected(item.isSelected());
            binding.tvName.setOnClickListener((view) -> {
                // 선택값 스위칭.
                item.setSelected(!item.isSelected());
                // 선택값 UI에 반영.
                view.setSelected(item.isSelected());
            });
        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item, int pos) {

        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ViewHolderTypeOnlyOne

    public static class ViewHolderTypeRadio extends BaseViewHolder<ChargeSearchCategoryVO, ItemFilterRadioBinding> {
        public ViewHolderTypeRadio(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            ItemFilterRadioBinding binding = getBinding();
            binding.tvTitle.setText(item.getTitleResId());
            List<ChargeSearchCategorytype> typeList = item.getTypeList();
            for (ChargeSearchCategorytype typeItem : typeList) {
                try {
                    RadioButton radioButton = (RadioButton) layoutInflater.inflate(R.layout.item_radio, binding.rgFilterList, false);
                    radioButton.setId(View.generateViewId());
                    radioButton.setText(typeItem.getTitleResId());
                    radioButton.setChecked(item.getSelectedItem().contains(typeItem));
                    radioButton.setTag(typeItem);
                    binding.rgFilterList.addView(radioButton);
                } catch (Exception ignored) {

                }
            }
            binding.rgFilterList.setOnCheckedChangeListener((group, checkedId) -> {
                View selectedView = group.findViewById(group.getCheckedRadioButtonId());
                if (selectedView != null) {
                    Object tag = selectedView.getTag();
                    if (tag instanceof ChargeSearchCategorytype) {
                        item.clearSelectedItems();
                        item.addSelectedItem((ChargeSearchCategorytype) tag);
                    }
                }
            });
        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item, int pos) {

        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ViewHolderTypeRadio

    public static class ViewHolderTypeCheck extends BaseViewHolder<ChargeSearchCategoryVO, ItemFilterCheckBinding> {
        public ViewHolderTypeCheck(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            ItemFilterCheckBinding binding = getBinding();
            binding.tvTitle.setText(item.getTitleResId());
            List<ChargeSearchCategorytype> typeList = item.getTypeList();
            for (ChargeSearchCategorytype typeItem : typeList) {
                try {
                    CheckBox checkBox = (CheckBox) layoutInflater.inflate(R.layout.item_check, binding.lFilterList, false);
                    checkBox.setId(View.generateViewId());
                    checkBox.setText(typeItem.getTitleResId());
                    checkBox.setTag(typeItem);
                    checkBox.setChecked(item.getSelectedItem().contains(typeItem));
                    checkBox.setOnCheckedChangeListener((view, isChecked) -> {
                        Object tag = view.getTag();
                        if (tag instanceof ChargeSearchCategorytype) {
                            if (isChecked) {
                                item.addSelectedItem((ChargeSearchCategorytype) tag);
                            } else {
                                item.getSelectedItem().remove(tag);
                                item.setSelected(item.getSelectedItem().size() > 0);
                            }
                        }
                    });
                    binding.lFilterList.addView(checkBox);
                } catch (Exception ignored) {

                }
            }
        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item, int pos) {

        }

        @Override
        public void onBindView(ChargeSearchCategoryVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ViewHolderTypeCheck
} // end of class ChargerFilterListAdapter
