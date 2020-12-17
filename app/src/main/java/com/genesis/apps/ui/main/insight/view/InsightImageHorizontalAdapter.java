package com.genesis.apps.ui.main.insight.view;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ImageVO;
import com.genesis.apps.databinding.ItemInsightImageBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class InsightImageHorizontalAdapter extends BaseRecyclerViewAdapter2<ImageVO> {

    public InsightImageHorizontalAdapter() {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemInsightImage(getView(parent, R.layout.item_insight_image));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);

    }

    private static class ItemInsightImage extends BaseViewHolder<ImageVO, ItemInsightImageBinding> {
        public ItemInsightImage(View itemView) {
            super(itemView);
//            getBinding().setListener(onSingleClickListener);
//            ViewPressEffectHelper.attach(getBinding().ivApp);
        }

        @Override
        public void onBindView(ImageVO item) {

        }

        @Override
        public void onBindView(ImageVO item, final int pos) {

            if (!TextUtils.isEmpty(item.getImage())) {
                Glide
                        .with(getContext())
                        .load(item.getImage())
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.drawable.logo_app_connected)//todo 변경 필요
//                    .error(R.drawable.logo_app_connected)//todo 변경 필요
                        .into(getBinding().ivImage);
            } else {

            }
        }

        @Override
        public void onBindView(ImageVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }


}