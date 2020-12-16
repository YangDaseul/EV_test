package com.genesis.apps.ui.main.home.view;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.databinding.ItemBluehandsBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Locale;


public class BtrBluehandsAdapter extends BaseRecyclerViewAdapter2<BtrVO> {

    private static OnSingleClickListener onSingleClickListener;

    public BtrBluehandsAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemBluehands(getView(parent, R.layout.item_bluehands));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);

    }

    private static class ItemBluehands extends BaseViewHolder<BtrVO, ItemBluehandsBinding> {
        public ItemBluehands(View itemView) {
            super(itemView);
            getBinding().lWhole.setOnClickListener(onSingleClickListener);
        }

        @Override
        public void onBindView(BtrVO item) {

        }

        @Override
        public void onBindView(BtrVO item, final int pos) {
            getBinding().lWhole.setTag(R.id.btr, item);
            getBinding().tvAsnm.setText(item.getAsnNm());
            getBinding().tvDistance.setText(item.getDist()+"km");
            getBinding().tvAddr.setText(item.getPbzAdr());
            getBinding().tvReptn.setText(item.getRepTn()!=null ? (PhoneNumberUtils.formatNumber(item.getRepTn(), Locale.getDefault().getCountry())):"--");
            getBinding().tvAcps1CdC.setVisibility(!TextUtils.isEmpty(item.getAcps1Cd())&&item.getAcps1Cd().contains("C") ? View.VISIBLE : View.GONE);
            getBinding().tvAcps1CdD.setVisibility(!TextUtils.isEmpty(item.getAcps1Cd())&&item.getAcps1Cd().contains("D") ? View.VISIBLE : View.GONE);
            setAuthView(item);
        }

        private void setAuthView(BtrVO btrVO) {

            if (btrVO == null || TextUtils.isEmpty(btrVO.getAcps1Cd()))
                return;

            TextView[] textViews = {getBinding().tvAuth1, getBinding().tvAuth2, getBinding().tvAuth3, getBinding().tvAuth4};
            String[] authNM;

            if (btrVO.getAcps1Cd().equalsIgnoreCase("2")) {
                //서비스 네트워크인 경우
                authNM = new String[]{
                        !TextUtils.isEmpty(btrVO.getGenLngYn()) && btrVO.getGenLngYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_27) : ""
                        , !TextUtils.isEmpty(btrVO.getFmRronYn()) && btrVO.getFmRronYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_28) : ""
                        , !TextUtils.isEmpty(btrVO.getHealZnYn()) && btrVO.getHealZnYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_29) : ""
                        , !TextUtils.isEmpty(btrVO.getCsmrPcYn()) && btrVO.getCsmrPcYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_30) : ""
                };
            } else {
                //특화 서비스인 경우
                authNM = new String[]{
                        !TextUtils.isEmpty(btrVO.getPntgXclYn()) && btrVO.getPntgXclYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_17) : ""
                        , !TextUtils.isEmpty(btrVO.getPrimTechYn()) && btrVO.getPrimTechYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_18) : ""
                        , !TextUtils.isEmpty(btrVO.getPrimCsYn()) && btrVO.getPrimCsYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_23) : ""
                };
            }
            //인증 뷰 초기화
            for (TextView textView : textViews) {
                textView.setVisibility(View.GONE);
                textView.setText("");
            }
            //인증 뷰 SET
            for (String auth : authNM) {
                if (!TextUtils.isEmpty(auth)) {
                    for (TextView textView : textViews) {
                        if (TextUtils.isEmpty(textView.getText().toString())) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(auth);
                            if (textView == getBinding().tvAuth1) {
                                getBinding().tvAuth2.setVisibility(View.INVISIBLE);
                            } else if (textView == getBinding().tvAuth3) {
                                getBinding().tvAuth4.setVisibility(View.INVISIBLE);
                            }
                            break;
                        }
                    }
                }
            }
        }


//        private void setAuthView(String pntgXclYn, String primTechYn, String primCsYn) {
//            TextView[] textViews = {getBinding().tvAuth1, getBinding().tvAuth2, getBinding().tvAuth3};
//            String[] authNM = {
//                    !TextUtils.isEmpty(pntgXclYn) && pntgXclYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_17) : ""
//                    , !TextUtils.isEmpty(primTechYn) && primTechYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_18) : ""
//                    , !TextUtils.isEmpty(primCsYn) && primCsYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES) ? getContext().getString(R.string.bt06_23) : ""
//            };
//            //인증 뷰 초기화
//            for(TextView textView : textViews){
//                textView.setVisibility(View.GONE);
//                textView.setText("");
//            }
//            //인증 뷰 SET
//            for(String auth : authNM){
//                if(!TextUtils.isEmpty(auth)) {
//                    for (TextView textView : textViews) {
//                        if(TextUtils.isEmpty(textView.getText().toString())){
//                            textView.setVisibility(View.VISIBLE);
//                            textView.setText(auth);
//                            if(textView==getBinding().tvAuth1){
//                                getBinding().tvAuth2.setVisibility(View.INVISIBLE);
//                            }else if(textView==getBinding().tvAuth3){
//                                getBinding().tvAuth4.setVisibility(View.INVISIBLE);
//                            }
//                            break;
//                        }
//                    }
//                }
//            }
//        }

        @Override
        public void onBindView(BtrVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }



}