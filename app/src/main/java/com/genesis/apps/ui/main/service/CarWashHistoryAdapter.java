package com.genesis.apps.ui.main.service;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.CarWashHistoryVO;
import com.genesis.apps.databinding.ItemCarWashHistoryBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

public class CarWashHistoryAdapter extends BaseRecyclerViewAdapter2<CarWashHistoryVO> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarWashHistoryViewHolder(getView(parent, R.layout.item_car_wash_history));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    //todo : 필요하면 오버라이드
/*
  @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
    */

    //세차 예약 내역 뷰홀더
    private static class CarWashHistoryViewHolder extends BaseViewHolder<CarWashHistoryVO, ItemCarWashHistoryBinding> {
        public CarWashHistoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(CarWashHistoryVO item) {
            //do nothing
        }

        @Override
        public void onBindView(CarWashHistoryVO item, int pos) {
            /*
            getBinding().tvModel.setText(item.getVhclNm());
            getBinding().tvCarRgstNo.setText(item.getMdlNm());
            Glide
                    .with(getContext())
                    .load(item.getVhclImgUri())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .error(R.drawable.img_car_339_2) //todo 대체 이미지 필요
                    .placeholder(R.drawable.img_car_339_2) //todo 에러시 대체 이미지 필요
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(getBinding().ivCar);

            getBinding().tag.setTagTextSize(DeviceUtil.dip2Pixel(getContext(),12));
            getBinding().tag.setTagTypeface(typeface);

            getBinding().tag.setGravity(Gravity.CENTER);
            getBinding().tag.addTag(item.getEtrrClrNm());
            getBinding().tag.addTag(item.getItrrClrNm());
            getBinding().tag.addTag(item.getOtpnNm());
            */
        }

        @Override
        public void onBindView(CarWashHistoryVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }
    }

}
