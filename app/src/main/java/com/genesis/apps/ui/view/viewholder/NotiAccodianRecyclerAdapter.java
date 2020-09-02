package com.genesis.apps.ui.view.viewholder;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.ui.view.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.view.BaseViewHolder;


public class NotiAccodianRecyclerAdapter extends BaseRecyclerViewAdapter2<NotiVO> {

        // Item의 클릭 상태를 저장할 array 객체
        private SparseBooleanArray selectedItems = new SparseBooleanArray();


        private ItemClickCallBack itemClickCallBack;

        public NotiAccodianRecyclerAdapter(ItemClickCallBack itemClickCallBack) {
            this.itemClickCallBack = itemClickCallBack;
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemNoti(getView(parent, R.layout.item_accodian_noti));
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
//                super.onBindViewHolder(holder, position);
                holder.onBindView(getItem(position),position, selectedItems);
//                ((ItemNoti)holder).getBinding().ivArrow.setOnClickListener(view -> itemClickCallBack.onClick(getItem(position), position));


                ((ItemNoti)holder).getBinding().lTitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                if (selectedItems.get(position)) {
                                        // 펼쳐진 Item을 클릭 시
                                        selectedItems.delete(position);
                                } else {
                                        // 클릭한 Item의 position을 저장
                                        selectedItems.put(position, true);
                                }
                                notifyItemChanged(position);
                        }
                });

        }

        public interface ItemClickCallBack {
                void onClick(NotiVO data, int position);
        }


}