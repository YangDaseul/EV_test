package com.genesis.apps.ui.main.insight.view;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.genesis.apps.databinding.ItemInsightArea2Binding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class InsightArea2Adapter extends BaseRecyclerViewAdapter2<SOSDriverVO> {


    private static OnSingleClickListener onSingleClickListener;

    public InsightArea2Adapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemInsightArea2(getView(parent, R.layout.item_insight_area_2));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    private static class ItemInsightArea2 extends BaseViewHolder<SOSDriverVO, ItemInsightArea2Binding> {
        public ItemInsightArea2(View itemView) {
            super(itemView);
            getBinding().btnDriverPosition.setOnClickListener(onSingleClickListener);
        }

        @Override
        public void onBindView(SOSDriverVO item) {

        }

        @Override
        public void onBindView(SOSDriverVO item, final int pos) {
            //FIX 2020-11-24 긴급출동은 메시지 하드코딩으로 결정
            getBinding().btnDriverPosition.setTag(R.id.item_sos, item);
            if(!TextUtils.isEmpty(item.getMinute())){
                getBinding().tvMsg.setVisibility(View.VISIBLE);
                getBinding().tvMsg.setText(String.format(getContext().getString(R.string.tm01_9), item.getMinute()));
            }else{
                getBinding().tvMsg.setVisibility(View.GONE);
            }
        }

        @Override
        public void onBindView(SOSDriverVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }

}