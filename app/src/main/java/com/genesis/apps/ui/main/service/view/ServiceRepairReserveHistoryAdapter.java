package com.genesis.apps.ui.main.service.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RepairHistVO;
import com.genesis.apps.databinding.ItemServiceRepairHistoryBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ServiceRepairReserveHistoryAdapter extends BaseRecyclerViewAdapter2<RepairHistVO> {

    private int pageNo = 0;
    private OnSingleClickListener onSingleClickListener;

    public ServiceRepairReserveHistoryAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemServiceRepairHistory(getView(parent, R.layout.item_service_repair_history), onSingleClickListener);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    private static class ItemServiceRepairHistory extends BaseViewHolder<RepairHistVO, ItemServiceRepairHistoryBinding> {
        private OnSingleClickListener onSingleClickListener;
        public ItemServiceRepairHistory(View itemView, OnSingleClickListener onSingleClickListener) {
            super(itemView);
            this.onSingleClickListener = onSingleClickListener;
        }

        @Override
        public void onBindView(RepairHistVO item) {

        }

        @Override
        public void onBindView(RepairHistVO item, int pos) {
            getBinding().setData(item);
            getBinding().setPos(pos);
            getBinding().setListener(onSingleClickListener);
            getBinding().btnRepairImage.setTag(R.id.item, item);
        }

        @Override
        public void onBindView(RepairHistVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

}