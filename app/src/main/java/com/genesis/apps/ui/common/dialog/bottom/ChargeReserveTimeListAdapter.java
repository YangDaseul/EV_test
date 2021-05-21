package com.genesis.apps.ui.common.dialog.bottom;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ReserveDtVO;
import com.genesis.apps.databinding.ItemChargerReserveTimeBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

/**
 * Class Name : ChargeReserveTimeListAdapter
 *
 * @author Ki-man Kim
 * @since 2021-05-21
 */
public class ChargeReserveTimeListAdapter extends BaseRecyclerViewAdapter2<ReserveDtVO> {
    private static ReserveDtVO SelectedItem;
    private OnSingleClickListener onSingleClickListener;

    public ChargeReserveTimeListAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, R.layout.item_charger_reserve_time), onSingleClickListener);
    }

    public void setSelectedItem(ReserveDtVO selectedItem) {
        SelectedItem = selectedItem;
    }

    public ReserveDtVO getSelectedItem() {
        return SelectedItem;
    }

    public void clearSelectedItem() {
        SelectedItem = null;
    }

    private static class ViewHolder extends BaseViewHolder<ReserveDtVO, ItemChargerReserveTimeBinding> {
        private OnSingleClickListener onSingleClickListener;

        public ViewHolder(View itemView, OnSingleClickListener listener) {
            super(itemView);
            onSingleClickListener = listener;
        }

        @Override
        public void onBindView(ReserveDtVO item) {
            ItemChargerReserveTimeBinding binding = getBinding();
            binding.setOnSingleClickListener(onSingleClickListener);
            binding.tvName.setText(item.getReservDt());
            binding.tvName.setTag(item);
            if (SelectedItem != null) {
                binding.tvName.setSelected(SelectedItem == item);
            } else {
                binding.tvName.setSelected(false);
            }
        }

        @Override
        public void onBindView(ReserveDtVO item, int pos) {

        }

        @Override
        public void onBindView(ReserveDtVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }
} // end of class ChargeReserveTimeListAdapter
