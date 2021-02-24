package com.genesis.apps.ui.myg.view;

import android.graphics.Paint;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.MembershipPointVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemUsePointBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class PointUseListAdapter extends BaseRecyclerViewAdapter2<MembershipPointVO> {

    private int pageNo=0;

    public PointUseListAdapter() {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemPointUse(getView(parent, R.layout.item_use_point));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);

    }

    public int getTotalUsePoint(){
        int point=0;
        for(int i=0; i<getItemCount(); i++){
            MembershipPointVO membershipPointVO = getItem(i);
            if(membershipPointVO.getTransTypNm().equalsIgnoreCase(MembershipPointVO.TYPE_TRANS_USE))
                point+=  Integer.parseInt(((MembershipPointVO)getItem(i)).getUseMlg());
        }
        return point;
    }

    public int getTotalSavePoint(){
        int point=0;
        for(int i=0; i<getItemCount(); i++){
            MembershipPointVO membershipPointVO = getItem(i);
            if(membershipPointVO.getTransTypNm().equalsIgnoreCase(MembershipPointVO.TYPE_TRANS_SAVE))
                point+=Integer.parseInt(((membershipPointVO.getUseMlg())));
        }
        return point;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }


    private static class ItemPointUse extends BaseViewHolder<MembershipPointVO, ItemUsePointBinding> {
        public ItemPointUse(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MembershipPointVO item) {

        }

        @Override
        public void onBindView(MembershipPointVO item, final int pos) {

            int textColor=0;
            int textMsg=0;
            int cancelLine=0;
            switch (StringUtil.isValidString(item.getTransTypNm())){
                case MembershipPointVO.TYPE_TRANS_SAVE:
                    textColor = R.color.x_262626;
                    textMsg = R.string.mg_member04_4;
                    break;
                case MembershipPointVO.TYPE_TRANS_USE:
                    textColor = R.color.x_996449;
                    textMsg = R.string.mg_member04_6;
                    break;
                case MembershipPointVO.TYPE_TRANS_CANCEL:
                    textColor = R.color.x_ce2d2d;
                    textMsg = R.string.mg_member04_7;
                    cancelLine = Paint.STRIKE_THRU_TEXT_FLAG;
                    break;
                default:
                    //처리안함
                    return;
            }
            getBinding().tvPoint.setText(StringUtil.getDigitGroupingString(StringUtil.isValidString(item.getUseMlg())));
            getBinding().tvPoint.setPaintFlags(cancelLine==0 ? 0 : (getBinding().tvPoint.getPaintFlags()|cancelLine));
            getBinding().tvPoint.setTextColor(getContext().getColor(textColor));
            getBinding().tvUnit.setTextColor(getContext().getColor(textColor));
            getBinding().tvUnit.setText(textMsg);
            getBinding().tvRemindPoint.setText(StringUtil.getDigitGroupingString(StringUtil.isValidString(item.getRmndPont())));
            getBinding().tvAsnnm.setText(StringUtil.isValidString(item.getFrchsNm()));
            getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(StringUtil.isValidString(item.getTransDtm()), DateUtil.DATE_FORMAT_yyyy_MM_dd), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
        }

        @Override
        public void onBindView(MembershipPointVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



}