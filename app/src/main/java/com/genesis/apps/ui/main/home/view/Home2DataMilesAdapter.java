package com.genesis.apps.ui.main.home.view;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.MainHistVO;
import com.genesis.apps.databinding.ItemDrivingScoreBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

/**
 * Class Name : Home2DataMilesAdapter
 *
 * @author Ki-man Kim
 * @since 2020-11-30
 */
public class Home2DataMilesAdapter extends BaseRecyclerViewAdapter2<MainHistVO> {
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemDataMilesList(getView(parent, R.layout.item_driving_score));
    }

    private static class ItemDataMilesList extends BaseViewHolder<MainHistVO, ItemDrivingScoreBinding> {
        public ItemDataMilesList(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MainHistVO item) {

        }

        @Override
        public void onBindView(MainHistVO item, int pos) {

        }

        @Override
        public void onBindView(MainHistVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }
} // end of class Home2DataMilesAdapter
