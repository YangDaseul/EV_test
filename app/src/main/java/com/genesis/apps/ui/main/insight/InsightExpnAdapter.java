package com.genesis.apps.ui.main.insight;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemInsightExpnBinding;
import com.genesis.apps.databinding.ItemLeasingHistMoreBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class InsightExpnAdapter extends BaseRecyclerViewAdapter2<ExpnVO> {



    private static final int BOTTOM=0;
    private static final int BODY=1;
    private static OnSingleClickListener onSingleClickListener;


    private int pageNo = 0;
    private int pageLimit=10;
    private boolean isMore=false;
    private String deleteExpnSeqNo;

    public InsightExpnAdapter(OnSingleClickListener onSingleClickListener) {
        InsightExpnAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==BODY)
            return new ItemInsightExpn(getView(parent, R.layout.item_insight_expn));
        else
            return new ItemLeasingHistMore(getView(parent, R.layout.item_leasing_hist_more));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);

    }


    @Override
    public int getItemViewType(int position) {
        try {
            if (isMore&&position==(getItemCount()-1)) {
                return BOTTOM;
            } else {
                return BODY;
            }
        }catch (Exception e){
            return BODY;
        }
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public String getDeleteExpnSeqNo() {
        return deleteExpnSeqNo;
    }

    public void setDeleteExpnSeqNo(String deleteExpnSeqNo) {
        this.deleteExpnSeqNo = deleteExpnSeqNo;
    }

    public int getRemovePosition(){
        int position = -1;
        for(int i=0; i<getItems().size(); i++){
            if(getItems().get(i).getExpnSeqNo().equalsIgnoreCase(deleteExpnSeqNo)){
                position=i;
                break;
            }
        }
        return position;
    }


//    public int getRefreshDatePos(String expnDtm) {
//        int position = -1;
//
//        if (getItemCount() > 0) {
//            try {
//                position = IntStream.range(0, getItemCount())
//                        .filter(pos -> getItems().get(pos).getExpnDtm().equalsIgnoreCase(expnDtm))
//                        .findFirst()
//                        .getAsInt();
//            } catch (Exception e) {
//                position = -1;
//            }
//
//            if (position > -1) {
//                getItems().get(position).setFirst(true);
//            }
//        }
//
//        return position;
//    }


    private static class ItemInsightExpn extends BaseViewHolder<ExpnVO, ItemInsightExpnBinding> {
        public ItemInsightExpn(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ExpnVO item) {

        }

        @Override
        public void onBindView(ExpnVO item, int pos) {

            if(item.isFirst()){
                getBinding().tvExpnDtm.setVisibility(View.VISIBLE);
                getBinding().tvExpnDtm.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getExpnDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
            }else{
                getBinding().tvExpnDtm.setVisibility(View.GONE);
            }

            getBinding().tvExpnPlc.setText(item.getExpnPlc());

            if(TextUtils.isEmpty(item.getAccmMilg())){
                getBinding().tvTitleAccmMilg.setVisibility(View.GONE);
                getBinding().tvAccmMilg.setVisibility(View.GONE);
            }else{
                getBinding().tvTitleAccmMilg.setVisibility(View.VISIBLE);
                getBinding().tvAccmMilg.setVisibility(View.VISIBLE);
                getBinding().tvAccmMilg.setText(StringUtil.getDigitGroupingString(item.getAccmMilg())+"km");
            }

            getBinding().tvExpnAmt.setText(StringUtil.getDigitGroupingString(item.getExpnAmt())+"원");

            getBinding().btnDelete.lWhole.setOnClickListener(onSingleClickListener);
            getBinding().btnDelete.lWhole.setTag(R.id.insight_expn_vo, item);
            getBinding().btnModify.setOnClickListener(onSingleClickListener);
            getBinding().btnModify.setTag(R.id.insight_expn_vo, item);

            if(StringUtil.isValidString(item.getRgstChnlCd()).equalsIgnoreCase("1000")){
                getBinding().btnModify.setVisibility(View.VISIBLE);
            }else{
                getBinding().btnModify.setVisibility(View.INVISIBLE);
            }

            int iconId=R.drawable.ic_service_potentiometer; //기타 이미지 변경 필요
            int expnDivNmId = R.string.tm_exps01_21;

            switch (item.getExpnDivNm()){
                case VariableType.INSIGHT_EXPN_DIV_CODE_1000:
                    expnDivNmId = R.string.tm_exps01_13;
                    iconId = R.drawable.ic_service_refueling;
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_1100:
                    expnDivNmId = R.string.tm_exps01_13_1;
                    iconId = R.drawable.ic_service_refueling;
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_2000:
                    expnDivNmId = R.string.tm_exps01_14;
                    iconId = R.drawable.ic_service_repair;
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_3000:
                    expnDivNmId = R.string.tm_exps01_15;
                    iconId = R.drawable.ic_service_wash;
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_4000:
                    expnDivNmId = R.string.tm_exps01_16;
                    iconId = R.drawable.ic_service_hometohome; //TODO 주차 아이콘으로 변경 필요
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_5000:
                    expnDivNmId = R.string.tm_exps01_17;
                    iconId = R.drawable.ic_service_hometohome; //TODO 통행 아이콘으로 변경 필요
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_6000:
                    expnDivNmId = R.string.tm_exps01_18;
                    iconId = R.drawable.ic_service_hometohome; //TODO 보험 아이콘으로 변경 필요
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_7000:
                    expnDivNmId = R.string.tm_exps01_19;
                    iconId = R.drawable.ic_service_hometohome; //TODO 세금 아이콘으로 변경 필요
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_8000:
                    expnDivNmId = R.string.tm_exps01_20;
                    iconId = R.drawable.ic_service_hometohome; //TODO 용품 아이콘으로 변경 필요
                    break;
                case VariableType.INSIGHT_EXPN_DIV_CODE_9000:
                default:
                    expnDivNmId = R.string.tm_exps01_21;
                    iconId = R.drawable.ic_service_potentiometer;
                    break;
            }

            getBinding().tvExpnDivNm.setText(expnDivNmId);
//            getBinding().ivIcon.setImageResource(iconId);

        }

        @Override
        public void onBindView(ExpnVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



    private static class ItemLeasingHistMore extends BaseViewHolder<ExpnVO, ItemLeasingHistMoreBinding> {
        public ItemLeasingHistMore(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ExpnVO item) {

        }

        @Override
        public void onBindView(ExpnVO item, int pos) {
            getBinding().btnMore.setOnClickListener(onSingleClickListener);
        }

        @Override
        public void onBindView(ExpnVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

}