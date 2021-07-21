package com.genesis.apps.ui.main.service;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.databinding.ItemCardManageTestListBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

public class CardManageTestListAdapter extends BaseRecyclerViewAdapter2<PaymtCardVO> {
    private final OnSingleClickListener listener;

    public CardManageTestListAdapter(OnSingleClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(this.listener, getView(parent, R.layout.item_card_manage_test_list));
    }

    private static class ViewHolder extends BaseViewHolder<PaymtCardVO, ItemCardManageTestListBinding>{
        private final OnSingleClickListener listener;


        public ViewHolder(OnSingleClickListener listener, View itemView) {
            super(itemView);
            this.listener = listener;
        }

        @Override
        public void onBindView(PaymtCardVO item) {
            ItemCardManageTestListBinding binding = getBinding();
            binding.setListener(this.listener);
            binding.tvCardName.setText(item.getCardName());
            binding.tvCardNumber.setText(item.getCardNo());
            binding.btnDelete.setTag(item);
            binding.tvMainCard.setTag(item);
            binding.tvMainCard.setSelected("Y".equalsIgnoreCase(item.getMainCardYN()));

            // 카드 이미지 불러오기
            if(!TextUtils.isEmpty(item.getCardImageUrl())){
                Glide.with(getContext())
                        .asBitmap()
                        .load(item.getCardImageUrl())
                        .format(DecodeFormat.PREFER_RGB_565)
                        .error(R.drawable.no_img)
                        .placeholder(R.drawable.no_img)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivCardThumb);
            }
        }

        @Override
        public void onBindView(PaymtCardVO item, int pos) {

        }

        @Override
        public void onBindView(PaymtCardVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }
}
