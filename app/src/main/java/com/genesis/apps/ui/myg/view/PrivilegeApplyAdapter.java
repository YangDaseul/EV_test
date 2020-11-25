package com.genesis.apps.ui.myg.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.PrivilegeVO;
import com.genesis.apps.databinding.ItemPrivilegeBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import static com.genesis.apps.comm.model.vo.PrivilegeVO.JOIN_CODE_UNABLE_APPLY;


public class PrivilegeApplyAdapter extends BaseRecyclerViewAdapter2<PrivilegeVO> {

    private static OnSingleClickListener onSingleClickListener;

    public PrivilegeApplyAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemPrivilege(getView(parent, R.layout.item_privilege));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);

    }

    private static class ItemPrivilege extends BaseViewHolder<PrivilegeVO, ItemPrivilegeBinding> {
        public ItemPrivilege(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(PrivilegeVO item) {

        }

        @Override
        public void onBindView(PrivilegeVO item, int pos) {
            getBinding().btnApply.setOnClickListener(null);
            getBinding().btnApply.setVisibility(View.GONE);
            getBinding().btnBenefit.setOnClickListener(null);
            getBinding().btnBenefit.setVisibility(View.GONE);
            getBinding().btnStatus.setOnClickListener(null);
            getBinding().btnStatus.setVisibility(View.GONE);

            switch (item.getJoinPsblCd()){
                case PrivilegeVO.JOIN_CODE_APPLY_POSSIBLE:
                    getBinding().lWhole.setBackgroundResource(R.drawable.bg_ffffff_stroke_e2e2e2);
                    getBinding().btnStatus.setVisibility(View.GONE);
                    getBinding().btnBenefit.setVisibility(View.GONE);
                    getBinding().btnApply.setVisibility(View.VISIBLE);
                    getBinding().btnApply.setTag(R.id.url, item.getServiceUrl());
                    getBinding().btnApply.setOnClickListener(onSingleClickListener);
                    break;
                case PrivilegeVO.JOIN_CODE_APPLYED:
                    getBinding().lWhole.setBackgroundResource(R.drawable.bg_ffffff_stroke_cd9a81);
                    getBinding().btnApply.setVisibility(View.GONE);
                    getBinding().btnStatus.setVisibility(View.VISIBLE);
                    getBinding().btnBenefit.setVisibility(View.VISIBLE);
                    getBinding().btnStatus.setTag(R.id.url, item.getServiceUrl());
                    getBinding().btnBenefit.setTag(R.id.url, item.getServiceDetailUrl());
                    getBinding().btnStatus.setOnClickListener(onSingleClickListener);
                    getBinding().btnBenefit.setOnClickListener(onSingleClickListener);
                    break;
            }

            getBinding().tvModel.setText(item.getMdlNm());
            getBinding().tvCarRgstNo.setText(item.getSaleMdlNm());
            getBinding().tvVin.setText(item.getVin());
        }

        @Override
        public void onBindView(PrivilegeVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }
}