package com.genesis.apps.ui.main.service.view;

import android.graphics.Paint;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ReserveHisVO;
import com.genesis.apps.databinding.ItemChargeReserveHistoryBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ChargeReserveHistoryAdapter extends BaseRecyclerViewAdapter2<ReserveHisVO> {

    private int pageNo = 0;
    private OnSingleClickListener onSingleClickListener;

    public ChargeReserveHistoryAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemChargeReserveHistory(getView(parent, R.layout.item_charge_reserve_history), onSingleClickListener);
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

    private static class ItemChargeReserveHistory extends BaseViewHolder<ReserveHisVO, ItemChargeReserveHistoryBinding> {
        public ItemChargeReserveHistory(View itemView, OnSingleClickListener onSingleClickListener) {
            super(itemView);
            getBinding().setListener(onSingleClickListener);
        }

        @Override
        public void onBindView(ReserveHisVO item) {

        }

        @Override
        public void onBindView(ReserveHisVO item, int pos) {
            getBinding().setData(item);
            getBinding().setPos(pos);
            getBinding().tvChgName.setPaintFlags(getBinding().tvChgName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

//            getBinding().btnRepairImage.setTag(R.id.item, item);
        }

        @Override
        public void onBindView(ReserveHisVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

}