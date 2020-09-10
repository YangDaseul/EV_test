package com.genesis.apps.ui.view.listview;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.MembershipPointVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemExtncPlanPontBinding;
import com.genesis.apps.ui.view.viewholder.BaseViewHolder;


public class ExtncPlanPontAdapter extends BaseRecyclerViewAdapter2<MembershipPointVO> {

    public ExtncPlanPontAdapter() {

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemExtncPlanPont(getView(parent, R.layout.item_extnc_plan_pont));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);

    }

    private static class ItemExtncPlanPont extends BaseViewHolder<MembershipPointVO, ItemExtncPlanPontBinding> {
        public ItemExtncPlanPont(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MembershipPointVO item) {

        }

        @Override
        public void onBindView(MembershipPointVO item, int pos) {
            getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getExtncPlanDt(),DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
            getBinding().tvExtncPont.setText(StringUtil.getDigitGrouping(Integer.parseInt(item.getExtncPlanPont())));
        }

        @Override
        public void onBindView(MembershipPointVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

    public int getTotalExtncPlanPont() {
        int total = 0;

        for (int i = 0; i < getItemCount(); i++) {
            try {
                MembershipPointVO data = getItem(i);
                total += Integer.parseInt(data.getExtncPlanPont());
            } catch (Exception e) {

            }
        }

        return total;
    }
}