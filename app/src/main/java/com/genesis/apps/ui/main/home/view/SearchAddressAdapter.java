package com.genesis.apps.ui.main.home.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.genesis.apps.databinding.ItemAddressBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class SearchAddressAdapter extends BaseRecyclerViewAdapter2<AddressZipVO> {

    private int pageNo = 0;
    private static OnSingleClickListener onSingleClickListener;

    public SearchAddressAdapter(OnSingleClickListener onSingleClickListener) {
        SearchAddressAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemAddress(getView(parent, R.layout.item_address));
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

    private static class ItemAddress extends BaseViewHolder<AddressZipVO, ItemAddressBinding> {
        public ItemAddress(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(AddressZipVO item) {

        }

        @Override
        public void onBindView(AddressZipVO item, int pos) {
            try {
                getBinding().tvRoadAddr.setText(item.getRoadAddr());
                getBinding().tvZipNo.setText(item.getZipNo());
                getBinding().lWhole.setTag(R.id.addr, item);
                getBinding().lWhole.setOnClickListener(onSingleClickListener);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onBindView(AddressZipVO item, int pos, SparseBooleanArray selectedItems) {

        }



    }


}