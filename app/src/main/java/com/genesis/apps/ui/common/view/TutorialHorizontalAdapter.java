package com.genesis.apps.ui.common.view;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ImageVO;
import com.genesis.apps.databinding.ItemTutorialBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

public class TutorialHorizontalAdapter extends BaseRecyclerViewAdapter2<ImageVO> {

    private static OnSingleClickListener onSingleClickListener;

    public TutorialHorizontalAdapter(OnSingleClickListener onSingleClickListener) {
        TutorialHorizontalAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemTutorial(getView(parent, R.layout.item_tutorial));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
        holder.onBindView(getItem(position), position);
    }


    private static class ItemTutorial extends BaseViewHolder<ImageVO, ItemTutorialBinding> {
        public ItemTutorial(View itemView) {
            super(itemView);
            getBinding().ivTutorial1.setOnClickListener(onSingleClickListener);
            getBinding().ivTutorial2.setOnClickListener(onSingleClickListener);
        }

        @Override
        public void onBindView(ImageVO item) {

        }

        @Override
        public void onBindView(ImageVO item, final int pos) {
            setImage(item.getImage2(), getBinding().ivTutorial1);
            setImage(item.getImage3(), getBinding().ivTutorial2);
            setListener(item.getLink1(), getBinding().ivTutorial1);
            setListener(item.getLink2(), getBinding().ivTutorial2);
        }

        @Override
        public void onBindView(ImageVO item, int pos, SparseBooleanArray selectedItems) {

        }

        private void setImage(int img, ImageView view){
            if(img==0){
                view.setVisibility(View.GONE);
            }else {
                view.setVisibility(View.VISIBLE);
                Glide
                        .with(getContext())
                        .load(img)
                        .format(DecodeFormat.PREFER_RGB_565)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            }
        }

        private void setListener(String url, ImageView view){
            view.setTag(R.id.url, TextUtils.isEmpty(url) ? null : url);
        }

    }


}
