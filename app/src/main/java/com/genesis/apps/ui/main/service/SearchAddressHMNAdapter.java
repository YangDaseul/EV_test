package com.genesis.apps.ui.main.service;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.databinding.ItemAddressHmnBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class SearchAddressHMNAdapter extends BaseRecyclerViewAdapter2<AddressVO> {

    private static OnSingleClickListener onSingleClickListener;

    public static int TYPE_NORMAL=0;
    public static int TYPE_RECENTLY=1;
    private static int TYPE=TYPE_RECENTLY;

    public SearchAddressHMNAdapter(OnSingleClickListener onSingleClickListener) {
        SearchAddressHMNAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemAddressHMN(getView(parent, R.layout.item_address_hmn));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    public void setType(int type){
        TYPE = type;
    }

    public int getPosition(AddressVO addressVO){
        int pos = -1;
        for (int i = 0; i < getItemCount(); i++) {
            if(!TextUtils.isEmpty((getItems().get(i).getAddrRoad()))&&getItems().get(i).getAddrRoad().equalsIgnoreCase(addressVO.getAddrRoad())) {
                pos = i;
                break;
            }
        }

        if(pos==-1){
            for (int i = 0; i < getItemCount(); i++) {
                if (!TextUtils.isEmpty((getItems().get(i).getAddr()))
                        &&!TextUtils.isEmpty((getItems().get(i).getTitle()))
                        &&getItems().get(i).getAddr().equalsIgnoreCase(addressVO.getAddr())
                        &&getItems().get(i).getTitle().equalsIgnoreCase(addressVO.getTitle())) {
                    pos = i;
                    break;
                }
            }
        }

        return pos;
    }


    private static class ItemAddressHMN extends BaseViewHolder<AddressVO, ItemAddressHmnBinding> {
        public ItemAddressHMN(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(AddressVO item) {

        }

        @Override
        public void onBindView(AddressVO item, int pos) {
            try {
                getBinding().tvAddr.setText(TextUtils.isEmpty(item.getAddrRoad()) ? item.getAddr() : item.getAddrRoad());
                getBinding().tvTitle.setText(item.getTitle() + (TextUtils.isEmpty(item.getCname()) ? "" : " "+item.getCname()));
                getBinding().lWhole.setTag(R.id.addr, item);
                getBinding().lWhole.setOnClickListener(onSingleClickListener);
                getBinding().btnDel.setTag(R.id.addr, item);
                getBinding().btnDel.setOnClickListener(onSingleClickListener);
                getBinding().btnDel.setVisibility(TYPE==TYPE_RECENTLY ? View.VISIBLE : View.GONE);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onBindView(AddressVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }


}