package com.genesis.apps.ui.main.service;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ReviewVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemReviewBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class Name : ReviewListAdapter
 *
 * @author Ki-man Kim
 * @since 2021-05-06
 */
public class ReviewListAdapter extends BaseRecyclerViewAdapter2<ReviewVO> {
    private static final ArrayList<ImageView> StarTempList = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, R.layout.item_review));
    }

    private static class ViewHolder extends BaseViewHolder<ReviewVO, ItemReviewBinding> {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ReviewVO item) {
            ItemReviewBinding binding = getBinding();

            float point = 0f;
            try {
                point = Float.parseFloat(item.getStarPoint());
            } catch (Exception ignored) {

            }
            StarTempList.clear();
            StarTempList.add(binding.ivStar0);
            StarTempList.add(binding.ivStar1);
            StarTempList.add(binding.ivStar2);
            StarTempList.add(binding.ivStar3);
            StarTempList.add(binding.ivStar4);

            // 별점 표시
            int i = 0;
            while (i < StarTempList.size()) {
                StarTempList.get(i).setSelected(point >= i + 1);
                i++;
            }

            // 등록자 ID, 등록일 표시
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.getUid());
            Date regDate = DateUtil.getDefaultDateFormat(item.getRgstDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss);
            if (regDate != null) {
                stringBuilder.append("|");
                stringBuilder.append(DateUtil.getDate(regDate, DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
            }
            binding.tvIdDate.setText(stringBuilder.toString());

            binding.tvReviewContent.setText(item.getContents());
        }

        @Override
        public void onBindView(ReviewVO item, int pos) {

        }

        @Override
        public void onBindView(ReviewVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ViewHolder
} // end of class ReviewListAdapter
