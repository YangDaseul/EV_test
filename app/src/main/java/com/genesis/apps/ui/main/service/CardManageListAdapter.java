package com.genesis.apps.ui.main.service;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.PaymtCardVO;
import com.genesis.apps.databinding.ItemCardManageListBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

/**
 * Class Name : CardManageListAdapter
 * 결제수단 관리 화면의 카드 목록 표시를 위한 {@link BaseRecyclerViewAdapter2}
 * 카드 목록 아이템별 View를 처리한다.
 *
 * @author Ki-man Kim
 * @since 2021-04-05
 */
public class CardManageListAdapter extends BaseRecyclerViewAdapter2<PaymtCardVO> {// implements ItemMoveCallback.ItemTouchHelperAdapter {
    private final OnSingleClickListener listener;

    /*
    카드 정렬 기능 삭제로 해당 코드 주석 처리.
    private ItemTouchHelper itemTouchHelper;
     */

    public CardManageListAdapter(OnSingleClickListener listener) {
        this.listener = listener;
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(this.listener, getView(parent, R.layout.item_card_manage_list));
        // 카드 정렬 기능 삭제로 해당 코드 주석 처리.
        //.setItemTouchHelper(this.itemTouchHelper);
    }

    /*
    카드 정렬 기능 삭제로 해당 함수들 주석 처리.
    @Override
    public void onItemMove(int fromPos, int targetPos) {
        if (fromPos < targetPos) {
            for (int i = fromPos; i < targetPos; i++) {
                Collections.swap(getItems(), i, i + 1);
            }
        } else {
            for (int i = fromPos; i > targetPos; i--) {
                Collections.swap(getItems(), i, i - 1);
            }
        }
        notifyItemMoved(fromPos, targetPos);
    }

    @Override
    public void onItemDismiss(int pos) {
        getItems().remove(pos);
        notifyItemRemoved(pos);
    }

    public void setItemTouchHelper(ItemTouchHelper helper) {
        this.itemTouchHelper = helper;
    }
     */

    /****************************************************************************************************
     * Inner Class
     ****************************************************************************************************/
    /**
     * 각 아이템 별 UI 처리 ViewHolder Class.
     * 카드 삭제, 아이템 드레그 이동 이벤트를 호출한다.
     */
    private static class ViewHolder extends BaseViewHolder<PaymtCardVO, ItemCardManageListBinding> {
        private final OnSingleClickListener listener;
        private ItemTouchHelper itemTouchHelper;

        public ViewHolder(OnSingleClickListener listener, View itemView) {
            super(itemView);
            this.listener = listener;
        }

        @Override
        public void onBindView(PaymtCardVO item) {
            ItemCardManageListBinding binding = getBinding();
            binding.setListener(this.listener);
            binding.tvCardName.setText(item.getCardName());
            binding.tvCardNumber.setText(item.getCardNo());
            binding.ivBtnDelete.setTag(item);
            binding.ivBtnFavorit.setTag(item);
            binding.ivBtnFavorit.setSelected("Y".equalsIgnoreCase(item.getMainCardYN()));

            if (!TextUtils.isEmpty(item.getCardImageUrl())) {
                Glide.with(getContext())
                        .asBitmap()
                        .load(item.getCardImageUrl())
                        .format(DecodeFormat.PREFER_RGB_565)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivCardThumb);
            }

            /*
                iVBtnMove View에 Touch Event를 등록하여 Item View를 Drag 처리할 수 있도록 함.
                Item View 전체 영역에서 Drag를 하는 것이 아니라 일부 View영역에서만 작동할수 있도록 처리하기 위함.
                해당 기능 삭제로 인해 주석 처리.
             */
            /*
            binding.ivBtnMove.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN && this.itemTouchHelper != null) {
                    this.itemTouchHelper.startDrag(this);
                }
                return false;
            });
             */
        }

        @Override
        public void onBindView(PaymtCardVO item, int pos) {

        }

        @Override
        public void onBindView(PaymtCardVO item, int pos, SparseBooleanArray selectedItems) {

        }

        /****************************************************************************************************
         * Method - Private
         ****************************************************************************************************/
        /*
        해당 기능 삭제로 인해 주석 처리.
        public ViewHolder setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
            this.itemTouchHelper = itemTouchHelper;
            return this;
        }
         */
    } // end of class ViewHolder
} // end of class CardManageListAdapter

