package com.genesis.apps.ui.main.service.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RepImageVO;
import com.genesis.apps.databinding.ItemServiceRepairImageBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ServiceRepairImageAdapter extends BaseRecyclerViewAdapter2<RepImageVO> {

//    private int pageNo = 0;

    public ServiceRepairImageAdapter() {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemServiceRepairImage(getView(parent, R.layout.item_service_repair_image));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

//    public int getPageNo() {
//        return pageNo;
//    }
//
//    public void setPageNo(int pageNo) {
//        this.pageNo = pageNo;
//    }

    private static class ItemServiceRepairImage extends BaseViewHolder<RepImageVO, ItemServiceRepairImageBinding> {
        public ItemServiceRepairImage(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(RepImageVO item) {

        }

        @Override
        public void onBindView(RepImageVO item, int pos) {
            Glide
                    .with(getContext())
                    .load(item.getImgUri())
                    .format(DecodeFormat.PREFER_ARGB_8888)
//                    .error(R.color.x_00000000) //todo 이미지
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(getBinding().ivImage);
        }

        @Override
        public void onBindView(RepImageVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

}