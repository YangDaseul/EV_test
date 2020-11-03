package com.genesis.apps.ui.main.service;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.databinding.ItemMapFindResultBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

public class CarWashFindSonaxBranchAdapter extends BaseRecyclerViewAdapter2<WashBrnVO> {
    private static OnSingleClickListener singleClickListener;

    public CarWashFindSonaxBranchAdapter(OnSingleClickListener listener) {
        singleClickListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SonaxBranchDataViewHolder(getView(parent, R.layout.item_map_find_result));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    //지점 목록 뷰홀더
    public static class SonaxBranchDataViewHolder extends BaseViewHolder<WashBrnVO, ItemMapFindResultBinding> {

        public SonaxBranchDataViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(WashBrnVO item) {
            //do nothing
        }

        @Override
        public void onBindView(WashBrnVO item, int pos) {
            //지점명
            getBinding().tvMapFindResultBranchName.setText(item.getBrnhNm());
            //거리
            getBinding().tvMapFindResultBranchDistance.setText(item.getDist());
            //주소
            getBinding().tvMapFindResultBranchAddress.setText(item.getBrnhAddr());
            //전화번호
            getBinding().tvMapFindResultBranchPhone.setText(item.getTelNo());
            //클릭 리스너
            setSingleClickListenerAndData(item);
        }

        @Override
        public void onBindView(WashBrnVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }

        //리스너를 연결하고, 이를 처리하는데 필요한 데이터도 저장
        private void setSingleClickListenerAndData(WashBrnVO item) {
            getBinding().lMapFindResultItem.setOnClickListener(singleClickListener);
            getBinding().lMapFindResultItem.setTag(R.id.tag_wash_branch, item);
        }
    }
}
