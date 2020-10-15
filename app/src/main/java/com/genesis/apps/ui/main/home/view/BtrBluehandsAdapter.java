package com.genesis.apps.ui.main.home.view;

import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.databinding.ItemBluehandsBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Locale;


public class BtrBluehandsAdapter extends BaseRecyclerViewAdapter2<BtrVO> {

    private static OnSingleClickListener onSingleClickListener;

    public BtrBluehandsAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemBluehands(getView(parent, R.layout.item_bluehands));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);

    }

    private static class ItemBluehands extends BaseViewHolder<BtrVO, ItemBluehandsBinding> {
        public ItemBluehands(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(BtrVO item) {

        }

        @Override
        public void onBindView(BtrVO item, final int pos) {
            getBinding().tvAsnm.setText(item.getAsnNm());
            getBinding().tvDistance.setText(item.getDist()+"km");
            getBinding().tvAddr.setText(item.getPbzAdr());
            getBinding().tvReptn.setText(item.getRepTn()!=null ? (PhoneNumberUtils.formatNumber(item.getRepTn(), Locale.getDefault().getCountry())):"--");
            getBinding().lWhole.setTag(R.id.btr, item);
            getBinding().lWhole.setOnClickListener(onSingleClickListener);
        }

        @Override
        public void onBindView(BtrVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



}