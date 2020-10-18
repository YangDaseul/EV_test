package com.genesis.apps.ui.main;

import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.comm.model.vo.SimilarVehicleVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemExtncPlanPontBinding;
import com.genesis.apps.databinding.ItemSimilarCarBinding;
import com.genesis.apps.databinding.ItemSimilarCarHeaderBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Locale;

import androidx.core.content.res.ResourcesCompat;


public class SimilarCarAdapter extends BaseRecyclerViewAdapter2<SimilarVehicleVO> {

    private static final int HEADER=0;
    private static final int BODY=1;
    private static Typeface typeface;

    public SimilarCarAdapter(Typeface typeface) {
        this.typeface = typeface;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==HEADER)
            return new ItemSimilarCarHeader(getView(parent, R.layout.item_similar_car_header));
        else
            return new ItemSimilarCar(getView(parent, R.layout.item_similar_car));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);

    }


    @Override
    public int getItemViewType(int position) {
        try {
            if (position==0) {
                return HEADER;
            } else {
                return BODY;
            }
        }catch (Exception e){
            return HEADER;
        }
    }



    private static class ItemSimilarCarHeader extends BaseViewHolder<SimilarVehicleVO, ItemSimilarCarHeaderBinding> {
        public ItemSimilarCarHeader(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(SimilarVehicleVO item) {

        }

        @Override
        public void onBindView(SimilarVehicleVO item, int pos) {
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
        }

        @Override
        public void onBindView(SimilarVehicleVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



    private static class ItemSimilarCar extends BaseViewHolder<SimilarVehicleVO, ItemSimilarCarBinding> {
        public ItemSimilarCar(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(SimilarVehicleVO item) {

        }

        @Override
        public void onBindView(SimilarVehicleVO item, int pos) {
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
            getBinding().tvSmlrRto.setVisibility(View.VISIBLE);
            getBinding().tvSmlrRto.setText(String.format(getContext().getString(R.string.gm02_inv01_2), item.getSmlrRto()==null ? "0" : item.getSmlrRto()));
        }

        @Override
        public void onBindView(SimilarVehicleVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

}