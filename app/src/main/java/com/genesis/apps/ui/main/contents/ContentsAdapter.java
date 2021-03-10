package com.genesis.apps.ui.main.contents;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.genesis.apps.databinding.ItemContentsBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ContentsAdapter extends BaseRecyclerViewAdapter2<ContentsVO> {

    private int pageNo=0;

    private static OnSingleClickListener onSingleClickListener;

    public ContentsAdapter(OnSingleClickListener onSingleClickListener) {
        ContentsAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemContents(getView(parent, R.layout.item_contents));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    private static class ItemContents extends BaseViewHolder<ContentsVO, ItemContentsBinding> {

        public ItemContents(View itemView) {
            super(itemView);
            getBinding().ivImage.setOnClickListener(onSingleClickListener);
        }

        @Override
        public void onBindView(ContentsVO item) {

        }

        @Override
        public void onBindView(ContentsVO item, final int pos) {
            Glide
                    .with(getContext())
                    .asBitmap()
                    .load(item.getTtImgUri())
                    .format(DecodeFormat.PREFER_ARGB_8888)
//                    .error(getDefaultImg(pos)) //todo 정의 필요
//                    .placeholder(getDefaultImg(pos)) //todo 정의 필요
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(getBinding().ivImage);

            getBinding().ivImage.setTag(R.id.item, item);
        }

        @Override
        public void onBindView(ContentsVO item, int pos, SparseBooleanArray selectedItems) {

        }


//        private int getDefaultImg(int position){
//            int pos = position % defaultBannerImg.length;
//
//            if (pos > defaultBannerImg.length-1)
//                pos = 3;
//
//            return defaultBannerImg[pos];
//        }
    }

}