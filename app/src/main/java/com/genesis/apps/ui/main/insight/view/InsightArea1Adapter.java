package com.genesis.apps.ui.main.insight.view;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ImageVO;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.databinding.ItemInsightArea1Binding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager2.widget.ViewPager2;


public class InsightArea1Adapter extends BaseRecyclerViewAdapter2<MessageVO> {


    private static OnSingleClickListener onSingleClickListener;

    public InsightArea1Adapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemInsightArea1(getView(parent, R.layout.item_insight_area_1));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    private static class ItemInsightArea1 extends BaseViewHolder<MessageVO, ItemInsightArea1Binding> {

        private InsightImageHorizontalAdapter insightImageHorizontalAdapter;

        public ItemInsightArea1(View itemView) {
            super(itemView);
            insightImageHorizontalAdapter = new InsightImageHorizontalAdapter();
            getBinding().vpImage.setAdapter(insightImageHorizontalAdapter);
            getBinding().vpImage.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//            getBinding().vpImage.setCurrentItem(0);
            getBinding().indicator.setViewPager(getBinding().vpImage);
            final float pageMargin = getContext().getResources().getDimensionPixelOffset(R.dimen.offset2);
            final float pageOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.offset2);
            getBinding().vpImage.setPageTransformer((page, position) -> {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (position < -1) {
                    page.setTranslationX(-myOffset);

                    Log.v("viewpager bug1","offset:"+myOffset);
                } else if (position <= 1) {
                    float scaleFactor = Math.max(1f, 1 - Math.abs(position - 0.14285715f));
                    page.setTranslationX(myOffset);
                    page.setScaleY(scaleFactor);
                    page.setScaleX(scaleFactor);
                    page.setAlpha(scaleFactor);

                    Log.v("viewpager bug2","offset:"+myOffset+ " Scale factor:"+scaleFactor);
                } else {
                    page.setAlpha(0f);
                    page.setTranslationX(myOffset);
                    Log.v("viewpager bug3","offset:"+myOffset);
                }

            });

        }

        @Override
        public void onBindView(MessageVO item) {

        }

        @Override
        public void onBindView(MessageVO item, final int pos) {
//            item.setMsgTypCd("IM");

            getBinding().tvTitle.setVisibility(View.GONE);
            getBinding().tvMsg.setVisibility(View.INVISIBLE);
            getBinding().tvLinkNm.setVisibility(View.GONE);
            getBinding().ivIcon.setVisibility(View.INVISIBLE);
            getBinding().lWhole.setOnClickListener(null);
            getBinding().vpImage.setVisibility(View.GONE);
            getBinding().indicator.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(item.getIconImgUri())) {
                getBinding().ivIcon.setVisibility(View.VISIBLE);
                Glide
                        .with(getContext())
                        .load(item.getIconImgUri())
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .error(R.drawable.ic_service_membership)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(getBinding().ivIcon);
            } else {
                getBinding().ivIcon.setVisibility(View.INVISIBLE);
            }


            if (!TextUtils.isEmpty(item.getMsgTypCd())) {
                switch (item.getMsgTypCd()) {
                    case VariableType.MAIN_HOME_INSIGHT_TXT:
                        if (TextUtils.isEmpty(item.getTxtMsg1())) {
                            getBinding().tvTitle.setVisibility(View.GONE);
                        } else {
                            getBinding().tvTitle.setVisibility(View.VISIBLE);
                            getBinding().tvTitle.setText(item.getTxtMsg1());
                        }

                        if (TextUtils.isEmpty(item.getTxtMsg2())) {
                            getBinding().tvMsg.setVisibility(View.INVISIBLE);
                        } else {
                            getBinding().tvMsg.setVisibility(View.VISIBLE);
                            getBinding().tvMsg.setText(item.getTxtMsg2());
                        }

                        break;
                    case VariableType.MAIN_HOME_INSIGHT_TXL:
                    default:
                        if (TextUtils.isEmpty(item.getTxtMsg1())) {
                            getBinding().tvTitle.setVisibility(View.GONE);
                        } else {
                            getBinding().tvTitle.setVisibility(View.VISIBLE);
                            getBinding().tvTitle.setText(item.getTxtMsg1());
                        }

                        if (TextUtils.isEmpty(item.getTxtMsg2())) {
                            getBinding().tvMsg.setVisibility(View.INVISIBLE);
                        } else {
                            getBinding().tvMsg.setVisibility(View.VISIBLE);
                            getBinding().tvMsg.setText(item.getTxtMsg2());
                        }

                        if (TextUtils.isEmpty(item.getLnkNm())) {
                            getBinding().tvLinkNm.setVisibility(View.GONE);
                        } else {
                            getBinding().tvLinkNm.setVisibility(View.VISIBLE);
                            getBinding().tvLinkNm.setText(item.getLnkNm());
                            getBinding().tvLinkNm.setTag(R.id.item, item);
                            getBinding().tvLinkNm.setOnClickListener(onSingleClickListener);
                        }

                        setImageViewPager(item);
                        break;
                }
            }


        }

        private void setImageViewPager(MessageVO item) {
            if(item==null)
                return;

//            item.setImgUri1("https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/insight/admin/ic_service_emergency@3x.png");
//            item.setImgUri2("https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/insight/admin/ic_service_emergency@3x.png");
//            item.setImgUri3("https://stg-kr-ccapi.genesis.com:8081/api/v1/graapi/nl/granas/insight/admin/ic_service_emergency@3x.png");

            List<ImageVO> list = new ArrayList<>();
            if(!TextUtils.isEmpty(item.getImgUri1())) list.add(new ImageVO(item.getImgUri1()));
            if(!TextUtils.isEmpty(item.getImgUri2())) list.add(new ImageVO(item.getImgUri2()));
            if(!TextUtils.isEmpty(item.getImgUri3())) list.add(new ImageVO(item.getImgUri3()));

            if(list.size()>0){
                getBinding().indicator.createIndicators(list.size(), 0);
                getBinding().indicator.setVisibility(View.VISIBLE);
                getBinding().vpImage.setVisibility(View.VISIBLE);
                getBinding().vpImage.setOffscreenPageLimit(list.size());
                insightImageHorizontalAdapter.setRows(list);
                insightImageHorizontalAdapter.notifyDataSetChanged();
            }else{
                getBinding().vpImage.setVisibility(View.GONE);
                getBinding().indicator.setVisibility(View.GONE);
            }
        }

        @Override
        public void onBindView(MessageVO item, int pos, SparseBooleanArray selectedItems) {

        }


    }

}