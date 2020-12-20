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
        holder.onBindView(getItem(position), position);
    }

    private class ItemInsightArea1 extends BaseViewHolder<MessageVO, ItemInsightArea1Binding> {

        private InsightImageHorizontalAdapter insightImageHorizontalAdapter;
        private int beforePostion=-1;

        public ItemInsightArea1(View itemView) {
            super(itemView);
            insightImageHorizontalAdapter = new InsightImageHorizontalAdapter();
            getBinding().vpImage.setAdapter(insightImageHorizontalAdapter);
            getBinding().vpImage.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            getBinding().vpImage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    if (positionOffsetPixels == 0&&beforePostion!=0&&beforePostion!=-1) {
                        int viewPos = Integer.parseInt(getBinding().vpImage.getTag(R.id.position).toString());
                        MessageVO messageVO = getItem(viewPos);
                        if(messageVO!=null){
                            messageVO.setCurrentPos(position);
                            setRow(viewPos, messageVO);
//                            Log.v("image","image position change:"+ ((MessageVO)getItem(viewPos)).getCurrentPos() + "  view position:"+viewPos  + " position offset pixels:"+positionOffsetPixels + "positionOffset:"+ positionOffset  );
                        }
                    }
                    beforePostion = positionOffsetPixels;
                }
            });
            getBinding().indicator.setViewPager(getBinding().vpImage);
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
            getBinding().vpImage.setTag(R.id.position, pos);
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

            setImageViewPager(item, pos);
        }

        private void setImageViewPager(MessageVO item, int pos) {
            if(item==null)
                return;
//            Log.v("image","image position:"+item.getCurrentPos() + "    view position:"+pos  );
            List<ImageVO> list = new ArrayList<>();
            if(!TextUtils.isEmpty(item.getImgUri1())) list.add(new ImageVO(item.getImgUri1()));
            if(!TextUtils.isEmpty(item.getImgUri2())) list.add(new ImageVO(item.getImgUri2()));
            if(!TextUtils.isEmpty(item.getImgUri3())) list.add(new ImageVO(item.getImgUri3()));

            if(list.size()>0){
                insightImageHorizontalAdapter.setRows(list);
//                insightImageHorizontalAdapter.notifyDataSetChanged();
                getBinding().vpImage.setVisibility(View.VISIBLE);
//                getBinding().vpImage.setOffscreenPageLimit(list.size());
                getBinding().vpImage.setCurrentItem(item.getCurrentPos(), false);
                getBinding().indicator.createIndicators(list.size(), item.getCurrentPos());
                getBinding().indicator.setVisibility(View.VISIBLE);
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