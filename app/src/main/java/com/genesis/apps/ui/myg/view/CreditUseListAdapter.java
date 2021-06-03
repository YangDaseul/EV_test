package com.genesis.apps.ui.myg.view;

import android.graphics.Paint;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.CreditPointVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemUsePointBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
import com.genesis.apps.ui.myg.MyGCreditUseListActivity;


public class CreditUseListAdapter extends BaseRecyclerViewAdapter2<CreditPointVO> {

    private int pageNo=0;

    public CreditUseListAdapter() {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemPointUse(getView(parent, R.layout.item_use_point));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }


    private static class ItemPointUse extends BaseViewHolder<CreditPointVO, ItemUsePointBinding> {
        public ItemPointUse(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(CreditPointVO item) {

        }

        @Override
        public void onBindView(CreditPointVO item, final int pos) {

            int textColor=0;
            int textMsg=0;
            int cancelLine=0;
            String amount="0";
            switch (StringUtil.isValidString(item.getDivCd())){
                case MyGCreditUseListActivity.TRANS_TYPE_CODE_SAVE:
                    textColor = R.color.x_262626;
                    textMsg = R.string.mg_member04_14;
                    amount = StringUtil.isValidString(item.getAddCreditAmount());
                    break;
                case MyGCreditUseListActivity.TRANS_TYPE_CODE_USE:
                    textColor = R.color.x_996449;
                    textMsg = R.string.mg_member04_16;
                    amount = StringUtil.isValidString(item.getUseCreditAmount());
                    break;
                case MyGCreditUseListActivity.TRANS_TYPE_CODE_CANCEL:
                    textColor = R.color.x_ce2d2d;
                    textMsg = R.string.mg_member04_17;
                    amount = StringUtil.isValidString(item.getUseCreditAmount());
                    cancelLine = Paint.STRIKE_THRU_TEXT_FLAG;
                    break;
                default:
                    //처리안함
                    return;
            }
            getBinding().tvRemindUnit.setText(R.string.mg_member04_15);
            getBinding().tvPoint.setText(StringUtil.getDigitGroupingString(TextUtils.isEmpty(amount) ? "0" : amount));
            getBinding().tvPoint.setPaintFlags(cancelLine==0 ? 0 : (getBinding().tvPoint.getPaintFlags()|cancelLine));
            getBinding().tvPoint.setTextColor(getContext().getColor(textColor));
            getBinding().tvUnit.setTextColor(getContext().getColor(textColor));
            getBinding().tvUnit.setText(textMsg);
            getBinding().tvRemindPoint.setText(StringUtil.getDigitGroupingString(StringUtil.isValidString(item.getBalanceAmount())));
            getBinding().tvAsnnm.setText(StringUtil.isValidString(item.getChgName()));
            getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(StringUtil.isValidString(item.getUseCreditDate()), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
        }

        @Override
        public void onBindView(CreditPointVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



}