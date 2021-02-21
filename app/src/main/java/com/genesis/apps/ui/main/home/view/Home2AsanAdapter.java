package com.genesis.apps.ui.main.home.view;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.MainHistVO;
import com.genesis.apps.databinding.ItemAsanListNewBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class Home2AsanAdapter extends BaseRecyclerViewAdapter2<MainHistVO> {


    private static OnSingleClickListener onSingleClickListener;

    public Home2AsanAdapter(OnSingleClickListener onSingleClickListener) {
        Home2AsanAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemAsanListNew(getView(parent, R.layout.item_asan_list_new));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    private static class ItemAsanListNew extends BaseViewHolder<MainHistVO, ItemAsanListNewBinding> {
        public ItemAsanListNew(View itemView) {
            super(itemView);
            getBinding().setListener(onSingleClickListener);
        }

        @Override
        public void onBindView(MainHistVO item) {

        }

        @Override
        public void onBindView(MainHistVO item, final int pos) {
            if(pos==0) {
                getBinding().tvTitleAsan.setVisibility(View.VISIBLE);
                getBinding().btnAsanDetail.lWhole.setVisibility(View.VISIBLE);
            }else {
                getBinding().tvTitleAsan.setVisibility(View.GONE);
                getBinding().btnAsanDetail.lWhole.setVisibility(View.GONE);
            }
            if(item.getAsnHist().equalsIgnoreCase("")&&item.getArrivDt().equalsIgnoreCase("")&&item.getAsnNm().equalsIgnoreCase("")){
                getBinding().tvAsanEmpty.setVisibility(View.VISIBLE);
                getBinding().lAsan.setVisibility(View.GONE);
                getBinding().btnAsanDetail.lWhole.setVisibility(View.GONE);
            }else{
                getBinding().tvAsanEmpty.setVisibility(View.GONE);
                getBinding().lAsan.setVisibility(View.VISIBLE);
                getBinding().setData(item);
            }

        }

        @Override
        public void onBindView(MainHistVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }

}