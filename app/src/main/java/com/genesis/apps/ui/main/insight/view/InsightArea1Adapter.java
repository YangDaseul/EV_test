package com.genesis.apps.ui.main.insight.view;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.databinding.ItemInsightArea1Binding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


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
        public ItemInsightArea1(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MessageVO item) {

        }

        @Override
        public void onBindView(MessageVO item, final int pos) {

            getBinding().tvTitle.setVisibility(View.GONE);
            getBinding().tvMsg.setVisibility(View.GONE);
            getBinding().tvLinkNm.setVisibility(View.GONE);
            getBinding().ivIcon.setVisibility(View.GONE);
            getBinding().lWhole.setOnClickListener(null);

            switch (item.getMsgTypCd()) {
                case VariableType.MAIN_HOME_INSIGHT_TXL:
                    if (TextUtils.isEmpty(item.getTtl())) {
                        getBinding().tvTitle.setVisibility(View.GONE);
                    } else {
                        getBinding().tvTitle.setVisibility(View.VISIBLE);
                        getBinding().tvTitle.setText(item.getTtl());
                    }

                    if (TextUtils.isEmpty(item.getTxtMsg())) {
                        getBinding().tvMsg.setVisibility(View.GONE);
                    } else {
                        getBinding().tvMsg.setVisibility(View.VISIBLE);
                        getBinding().tvMsg.setText(item.getTxtMsg());
                    }

                    break;
                case VariableType.MAIN_HOME_INSIGHT_TXT:
                default:
                    if (TextUtils.isEmpty(item.getTtl())) {
                        getBinding().tvTitle.setVisibility(View.GONE);
                    } else {
                        getBinding().tvTitle.setVisibility(View.VISIBLE);
                        getBinding().tvTitle.setText(item.getTtl());
                    }

                    if (TextUtils.isEmpty(item.getTxtMsg())) {
                        getBinding().tvMsg.setVisibility(View.GONE);
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

        @Override
        public void onBindView(MessageVO item, int pos, SparseBooleanArray selectedItems) {

        }


    }

}