package com.genesis.apps.ui.common.activity.test;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.genesis.apps.R;
import com.genesis.apps.databinding.SquareViewBinding;
import com.genesis.apps.databinding.SquareViewLineBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
import com.genesis.apps.ui.common.view.listview.test.Link;

import java.util.Collections;


public class CardViewAadapter extends BaseRecyclerViewAdapter2<Link> implements ItemMoveCallback.ItemTouchHelperAdapter {

        public static final int TYPE_CARD = 0;
        public static final int TYPE_LINE = 1;
        private int VIEW_TYPE = TYPE_CARD;

        public int getViewType() {
                return VIEW_TYPE;
        }

        public void setViewType(int VIEW_TYPE) {
                this.VIEW_TYPE = VIEW_TYPE;
        }

        // Item의 클릭 상태를 저장할 array 객체
        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        public CardViewAadapter() {
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if(VIEW_TYPE==TYPE_LINE)
                        return new SquiareViewLineHolder(getView(parent, R.layout.square_view_line));
                else
                        return new SquiareViewHolder(getView(parent, R.layout.square_view));
        }

        @Override
        public void onBindViewHolder(final BaseViewHolder holder, int position) {
                Log.v("recyclerview onBindViewHolder","position pos:"+position);
//                super.onBindViewHolder(holder, position);

                holder.onBindView(getItem(position),position, selectedItems);

//                holder.getBinding().lTitle.setOnClickListener(view -> {
//                        Log.v("recyclerview onclick","position pos:"+position);
//                        if (selectedItems.get(position)) {
//                                // 펼쳐진 Item을 클릭 시
//                                selectedItems.delete(position);
//                        } else {
//                                // 클릭한 Item의 position을 저장
//                                selectedItems.put(position, true);
//                        }
//                        notifyItemChanged(position);
//
//                });

        }

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


        // inner class
        public class SquiareViewHolder extends BaseViewHolder<Link, SquareViewBinding> {
                private ImageView image;

                public SquiareViewHolder(View itemLayoutView) {
                        super(itemLayoutView);
                        this.image = itemLayoutView.findViewById(R.id.image);
                }

                @Override
                public void onBindView(Link item) {

                }

                @Override
                public void onBindView(Link item, int pos) {

                }

                @Override
                public void onBindView(Link item, int pos, SparseBooleanArray selectedItems) {
                        this.image.setImageResource(item.getIcon());
//                        this.image.setOnClickListener(
//                                v -> myItemClickListener.onClick(link));

                }

        }


        public class SquiareViewLineHolder extends BaseViewHolder<Link, SquareViewLineBinding> {
                private ImageView image;

                public SquiareViewLineHolder(View itemLayoutView) {
                        super(itemLayoutView);
                        this.image = itemLayoutView.findViewById(R.id.image);
                }

                @Override
                public void onBindView(Link item) {

                }

                @Override
                public void onBindView(Link item, int pos) {

                }

                @Override
                public void onBindView(Link item, int pos, SparseBooleanArray selectedItems) {
                        this.image.setImageResource(item.getIcon());
//                        this.image.setOnClickListener(
//                                v -> myItemClickListener.onClick(link));

                }

        }




}