package com.genesis.apps.ui.main.service.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.RepairHistVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemInsightExpnBinding;
import com.genesis.apps.databinding.ItemLeasingHistMoreBinding;
import com.genesis.apps.databinding.ItemServiceRepairHistoryBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ServiceRepairReserveHistoryAdapter extends BaseRecyclerViewAdapter2<RepairHistVO> {

    private int pageNo = 0;

    public ServiceRepairReserveHistoryAdapter() {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemServiceRepairHistory(getView(parent, R.layout.item_service_repair_history));
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
        public ItemServiceRepairHistory(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(RepairHistVO item) {

        }

        @Override
        public void onBindView(RepairHistVO item, int pos) {
            getBinding().setData(item);
            getBinding().setPos(pos);
        }

        @Override
        public void onBindView(RepairHistVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

}