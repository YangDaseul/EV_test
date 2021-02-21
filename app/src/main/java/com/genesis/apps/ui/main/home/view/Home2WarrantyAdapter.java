package com.genesis.apps.ui.main.home.view;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.databinding.ItemWarrantyNewBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class Home2WarrantyAdapter extends BaseRecyclerViewAdapter2<VehicleVO> {

    private static OnSingleClickListener onSingleClickListener;

    public Home2WarrantyAdapter(OnSingleClickListener onSingleClickListener) {
        Home2WarrantyAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemWarrantyNew(getView(parent, R.layout.item_warranty_new));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    private static class ItemWarrantyNew extends BaseViewHolder<VehicleVO, ItemWarrantyNewBinding> {
        public ItemWarrantyNew(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(VehicleVO item) {

        }

        @Override
        public void onBindView(VehicleVO item, final int pos) {

            if (pos == 0)
                getBinding().tvTitleWarranty.setVisibility(View.VISIBLE);
            else
                getBinding().tvTitleWarranty.setVisibility(View.GONE);

            getBinding().setListener(onSingleClickListener);
        }

        @Override
        public void onBindView(VehicleVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }

}