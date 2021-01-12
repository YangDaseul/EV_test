package com.genesis.apps.ui.myg.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.FamilyAppVO;
import com.genesis.apps.databinding.ItemFamilyAppBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listener.ViewPressEffectHelper;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class FamilyAppHorizontalAdapter extends BaseRecyclerViewAdapter2<FamilyAppVO> {

    private static OnSingleClickListener onSingleClickListener;

    public FamilyAppHorizontalAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemFamilyApp(getView(parent, R.layout.item_family_app));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);

    }

    private static class ItemFamilyApp extends BaseViewHolder<FamilyAppVO, ItemFamilyAppBinding> {
        public ItemFamilyApp(View itemView) {
            super(itemView);
            getBinding().setListener(onSingleClickListener);
            ViewPressEffectHelper.attach(getBinding().ivApp);
        }

        @Override
        public void onBindView(FamilyAppVO item) {

        }

        @Override
        public void onBindView(FamilyAppVO item, final int pos) {
            getBinding().setData(item);
            getBinding().ivApp.setTag(R.id.url, item.getSchmNm());
            Glide
                    .with(getContext())
                    .load(item.getImgUri())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo_app_connected)//todo 변경 필요
                    .error(R.drawable.logo_app_connected)//todo 변경 필요
                    .into(getBinding().ivApp);
        }

        @Override
        public void onBindView(FamilyAppVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



}