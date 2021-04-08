package com.genesis.apps.ui.main.service.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.BookingTimeVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.databinding.ItemServiceChargeReserveTimeBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ServiceChargeBtrReserveTimeHorizontalAdapter extends BaseRecyclerViewAdapter2<BookingTimeVO> {

    private static View.OnClickListener onClickListener;

    public ServiceChargeBtrReserveTimeHorizontalAdapter(View.OnClickListener onClickListener) {
        ServiceChargeBtrReserveTimeHorizontalAdapter.onClickListener = onClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = getView(parent, R.layout.item_service_charge_reserve_time);
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.5);
//        layoutParams.width = (int) DeviceUtil.dip2Pixel(parent.getContext(), 150f);
        itemView.setLayoutParams(layoutParams);

        return new ItemChargeBtrReserveTime(itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);

    }

    public void setSelectItem(int pos){
        for(int i=0; i<getItemCount(); i++){
            BookingTimeVO item = ((BookingTimeVO)getItem(i));
            if(i==pos){
                item.setSelect(true);
            }else{
                item.setSelect(false);
            }
            setRow(i, item);
        }
        notifyDataSetChanged();
    }

    public void initSelectItem(){
        for(int i=0; i<getItemCount(); i++){
            BookingTimeVO item = ((BookingTimeVO)getItem(i));
            item.setSelect(false);
            setRow(i, item);
        }
        notifyDataSetChanged();
    }

    private static class ItemChargeBtrReserveTime extends BaseViewHolder<BookingTimeVO, ItemServiceChargeReserveTimeBinding> {
        public ItemChargeBtrReserveTime(View itemView) {
            super(itemView);
            getBinding().btnTime.setOnClickListener(onClickListener);
        }

        @Override
        public void onBindView(BookingTimeVO item) {

        }

        @Override
        public void onBindView(BookingTimeVO item, final int pos) {
            getBinding().setData(item);
            getBinding().btnTime.setTag(R.id.item, item);
            getBinding().btnTime.setTag(R.id.position, pos);
            Paris.style(getBinding().btnTime).apply(item.isSelect() ? R.style.BtrFilterEnable2 : R.style.BtrFilterDisable2);
        }

        @Override
        public void onBindView(BookingTimeVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



}