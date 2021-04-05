package com.genesis.apps.ui.main.service;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ItemCardManageListBinding;
import com.genesis.apps.ui.common.view.ItemMoveCallback;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Collections;

/**
 * Class Name : CardManageListAdapter
 *
 * @author Ki-man Kim
 * @since 2021-04-05
 */
public class CardManageListAdapter extends BaseRecyclerViewAdapter2<CardManageActivity.DummyDataCard> implements ItemMoveCallback.ItemTouchHelperAdapter {
    private final OnSingleClickListener listener;

    public CardManageListAdapter(OnSingleClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(this.listener, getView(parent, R.layout.item_card_manage_list));
    }

    @Override
    public void onItemMove(int fromPos, int targetPos) {
        if (fromPos < targetPos) {
            for (int i = fromPos; i < targetPos; i++) {
                Collections.swap(getItems(), i, i + 1);
            }
        } else {
            for (int i = fromPos; i > targetPos; i--) {
                Collections.swap(getItems(), i, i - 1);
            }
        }
        notifyItemMoved(fromPos, targetPos);
    }

    @Override
    public void onItemDismiss(int pos) {
        getItems().remove(pos);
        notifyItemRemoved(pos);
    }

    private static class ViewHolder extends BaseViewHolder<CardManageActivity.DummyDataCard, ItemCardManageListBinding> {
        private final OnSingleClickListener listener;

        public ViewHolder(OnSingleClickListener listener, View itemView) {
            super(itemView);
            this.listener = listener;
        }

        @Override
        public void onBindView(CardManageActivity.DummyDataCard item) {
            ItemCardManageListBinding binding = getBinding();
            binding.setListener(this.listener);
            binding.tvCardName.setText(item.getName());
            binding.tvCardNumber.setText(item.getNumber());
            binding.ivBtnDelete.setTag(item);
        }

        @Override
        public void onBindView(CardManageActivity.DummyDataCard item, int pos) {

        }

        @Override
        public void onBindView(CardManageActivity.DummyDataCard item, int pos, SparseBooleanArray selectedItems) {

        }
    }
} // end of class CardManageListAdapter

