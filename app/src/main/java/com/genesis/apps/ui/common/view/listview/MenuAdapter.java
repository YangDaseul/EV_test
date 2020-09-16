package com.genesis.apps.ui.common.view.listview;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.databinding.ItemMenuBinding;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class MenuAdapter extends BaseRecyclerViewAdapter2<MenuVO> {

    private static boolean isRecently=false;
    private static OnPositionClickListener onItemClickListener;
    public MenuAdapter(OnPositionClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemMenu(getView(parent, R.layout.item_menu));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    public boolean isRecently() {
        return isRecently;
    }

    public void setRecently(boolean recently) {
        isRecently = recently;
    }

    public void deleteItem(int position){
        if(isRecently()){
            remove(position);
        }
    }

    private static class ItemMenu extends BaseViewHolder<MenuVO, ItemMenuBinding> {
        public ItemMenu(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MenuVO item) {

        }

        @Override
        public void onBindView(MenuVO item, int pos) {
            getBinding().tvName.setText(item.getName());
            getBinding().btnDel.setVisibility(isRecently ? View.VISIBLE : View.GONE);
            getBinding().btnDel.setOnClickListener(view -> {
                if(onItemClickListener!=null)
                    onItemClickListener.onClick(view,pos);
            });
        }

        @Override
        public void onBindView(MenuVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

    public interface OnPositionClickListener {
        void onClick(View v, int position);
    }

}