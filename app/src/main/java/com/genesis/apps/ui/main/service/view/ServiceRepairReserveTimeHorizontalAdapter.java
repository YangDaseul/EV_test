package com.genesis.apps.ui.main.service.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RepairReserveDateVO;
import com.genesis.apps.databinding.ItemRepairReserveTimeBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ServiceRepairReserveTimeHorizontalAdapter extends BaseRecyclerViewAdapter2<RepairReserveDateVO> {

    private static View.OnClickListener onClickListener;

    public ServiceRepairReserveTimeHorizontalAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View itemView = inflater.inflate(R.layout.itemview, parent, false);



        View itemView = getView(parent, R.layout.item_repair_reserve_time);
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.5);
        itemView.setLayoutParams(layoutParams);

        return new ItemRepairReserveTime(itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);

    }

    public void setSelectItem(int pos){
        for(int i=0; i<getItemCount(); i++){
            RepairReserveDateVO item = ((RepairReserveDateVO)getItem(i));
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
            RepairReserveDateVO item = ((RepairReserveDateVO)getItem(i));
            item.setSelect(false);
            setRow(i, item);
        }
        notifyDataSetChanged();
    }

    private static class ItemRepairReserveTime extends BaseViewHolder<RepairReserveDateVO, ItemRepairReserveTimeBinding> {
        public ItemRepairReserveTime(View itemView) {
            super(itemView);
            getBinding().btnTime.setOnClickListener(onClickListener);
        }

        @Override
        public void onBindView(RepairReserveDateVO item) {

        }

        @Override
        public void onBindView(RepairReserveDateVO item, final int pos) {
            getBinding().setData(item);
            getBinding().btnTime.setTag(R.id.item, item);
            getBinding().btnTime.setTag(R.id.position, pos);
            Paris.style(getBinding().btnTime).apply(item.isSelect() ? R.style.BtrFilterEnable2 : R.style.BtrFilterDisable2);
        }

        @Override
        public void onBindView(RepairReserveDateVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



}