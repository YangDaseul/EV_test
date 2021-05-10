package com.genesis.apps.ui.common.dialog.bottom.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.CreditVO;
import com.genesis.apps.databinding.ItemCreditVehicleBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class CreditVehicleAdapter extends BaseRecyclerViewAdapter2<CreditVO> {

    private static OnSingleClickListener onSingleClickListener;

    public CreditVehicleAdapter() {
    }

    public static void setOnSingleClickListener(OnSingleClickListener onSingleClickListener) {
        CreditVehicleAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemCreditVehicle(getView(parent, R.layout.item_credit_vehicle));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    private static class ItemCreditVehicle extends BaseViewHolder<CreditVO, ItemCreditVehicleBinding> {
        public ItemCreditVehicle(View itemView) {
            super(itemView);
            getBinding().setListener(onSingleClickListener);
        }

        @Override
        public void onBindView(CreditVO item) {

        }

        @Override
        public void onBindView(CreditVO item, int pos) {
            getBinding().setData(item);
            getBinding().lWhole.setTag(R.id.item, item);
        }

        @Override
        public void onBindView(CreditVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

}