package com.genesis.apps.ui.myg.view;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemCardBinding;
import com.genesis.apps.databinding.ItemExtncPlanPontBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class CardHorizontalAdapter extends BaseRecyclerViewAdapter2<CardVO> {

    public CardHorizontalAdapter() {

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemCard(getView(parent, R.layout.item_card));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);

    }

    private static class ItemCard extends BaseViewHolder<CardVO, ItemCardBinding> {
        public ItemCard(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(CardVO item) {

        }

        @Override
        public void onBindView(CardVO item, int pos) {

            getBinding().tvCardName.setText(item.getCardNm() + " " + item.getCardClsNm());
            getBinding().tvCardNo2.setText(item.getCardNo()); //카드번호 암호화?
            getBinding().tvCardNo.setText(item.getCardNo()); //카드번호 암호화?
            getBinding().tvCardDate.setText(item.getCardIsncSubspDt()); //데이트 포맷정의
            getBinding().ivBarcode.setImageBitmap(null); //바코드 생성 테스트

            //getBinding().ivFavorite.set 즐겨찾기 로컬 구현?

        }

        @Override
        public void onBindView(CardVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }


}