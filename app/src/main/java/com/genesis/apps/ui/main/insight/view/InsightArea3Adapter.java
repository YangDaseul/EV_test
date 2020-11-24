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
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.databinding.ItemInsightArea3Binding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class InsightArea3Adapter extends BaseRecyclerViewAdapter2<MessageVO> {


    private static OnSingleClickListener onSingleClickListener;

    public InsightArea3Adapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemInsightArea3(getView(parent, R.layout.item_insight_area_3));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    private static class ItemInsightArea3 extends BaseViewHolder<MessageVO, ItemInsightArea3Binding> {
        public ItemInsightArea3(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MessageVO item) {

        }

        @Override
        public void onBindView(MessageVO item, final int pos) {

            //todo 2020-11-24 park 인사이트1 및 3영역은 링크 이동 및 이미지 처리에 대한 부분을 서버에 재 확인 요청 후 적용 필요.

            getBinding().tvTitle.setVisibility(View.GONE);
            getBinding().tvMsg.setVisibility(View.INVISIBLE);
            getBinding().tvLinkNm.setVisibility(View.GONE);
            getBinding().ivIcon.setVisibility(View.GONE);
            getBinding().lWhole.setOnClickListener(null);

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
                        if (TextUtils.isEmpty(item.getTtl())) {
                            getBinding().tvTitle.setVisibility(View.GONE);
                        } else {
                            getBinding().tvTitle.setVisibility(View.VISIBLE);
                            getBinding().tvTitle.setText(item.getTtl());
                        }

                        if (TextUtils.isEmpty(item.getTxtMsg())) {
                            getBinding().tvMsg.setVisibility(View.INVISIBLE);
                        } else {
                            getBinding().tvMsg.setVisibility(View.VISIBLE);
                            getBinding().tvMsg.setText(item.getTxtMsg());
                        }

                        break;
                    case VariableType.MAIN_HOME_INSIGHT_TXL:
                    default:
                        if (TextUtils.isEmpty(item.getTtl())) {
                            getBinding().tvTitle.setVisibility(View.GONE);
                        } else {
                            getBinding().tvTitle.setVisibility(View.VISIBLE);
                            getBinding().tvTitle.setText(item.getTtl());
                        }

                        if (TextUtils.isEmpty(item.getTxtMsg())) {
                            getBinding().tvMsg.setVisibility(View.INVISIBLE);
                        } else {
                            getBinding().tvMsg.setVisibility(View.VISIBLE);
                            getBinding().tvMsg.setText(item.getTxtMsg());
                        }

                        if (TextUtils.isEmpty(item.getLnkNm())) {
                            getBinding().tvLinkNm.setVisibility(View.GONE);
                        } else {
                            getBinding().tvLinkNm.setVisibility(View.VISIBLE);
                            getBinding().tvLinkNm.setText(item.getLnkNm());
                        }

                        if (TextUtils.isEmpty(item.getImgUri())) {
                            getBinding().lWhole.setOnClickListener(null);
                        } else {
                            getBinding().lWhole.setOnClickListener(onSingleClickListener);
                            getBinding().lWhole.setTag(R.id.url, item.getImgUri());
                        }
                        break;
                }
            }


        }

        @Override
        public void onBindView(MessageVO item, int pos, SparseBooleanArray selectedItems) {

        }


    }

}