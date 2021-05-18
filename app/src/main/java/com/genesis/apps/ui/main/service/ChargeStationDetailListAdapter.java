package com.genesis.apps.ui.main.service;

import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.databinding.ItemChargeStationDetailBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

/**
 * Class Name : ChargeStationDetailListAdapter
 *
 * @author Ki-man Kim
 * @since 2021-04-27
 */
public class ChargeStationDetailListAdapter extends BaseRecyclerViewAdapter2<ChargeStationDetailListAdapter.ItemVO> {

    private static OnSingleClickListener onSingleClickListener;

    public ChargeStationDetailListAdapter(OnSingleClickListener onSingleClickListener){
        this.onSingleClickListener = onSingleClickListener;
    }

    public enum DetailType {
        ADDRESS(R.drawable.ic_site, 0, R.string.sm_evss04_04),
        TIME(R.drawable.ic_time, R.string.sm_evss04_01, 0),
        SPNM(R.drawable.ic_vendor, R.string.sm_evss04_02, R.string.sm_evss04_05),
        PAY_TYPE(R.drawable.ic_money, R.string.sm_evss04_03, 0);

        @DrawableRes
        private int iconRes;
        @StringRes
        private int titleRes;
        @StringRes
        private int bottomBtnTitleRes;

        DetailType(int iconRes, int titleRes, int bottomBtnTitleRes) {
            this.iconRes = iconRes;
            this.titleRes = titleRes;
            this.bottomBtnTitleRes = bottomBtnTitleRes;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, R.layout.item_charge_station_detail));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    private static class ViewHolder extends BaseViewHolder<ItemVO, ItemChargeStationDetailBinding> {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ItemVO item) {
        }

        @Override
        public void onBindView(ItemVO item, int pos) {
            ItemChargeStationDetailBinding binding = getBinding();
            DetailType type = item.detailType;
            binding.ivIcon.setImageResource(type.iconRes);
            if (type.titleRes == 0) {
                binding.tvTitle.setVisibility(View.GONE);
            } else {
                binding.tvTitle.setVisibility(View.VISIBLE);
                binding.tvTitle.setText(type.titleRes);
            }
            binding.tvContent.setText(type == DetailType.ADDRESS ? (Html.fromHtml(item.content, Html.FROM_HTML_MODE_COMPACT)) : item.content);
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            binding.tvContent.setLineSpacing(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, type == DetailType.PAY_TYPE ? 20.0f : 4f, displayMetrics),
                    1.0f);

            if (type.bottomBtnTitleRes == 0) {
                binding.tvBtnBottom.setVisibility(View.GONE);
            } else {
                binding.tvBtnBottom.setVisibility(View.VISIBLE);
                binding.tvBtnBottom.setText(type.bottomBtnTitleRes);
                binding.tvBtnBottom.setTag(R.id.item, type);
                binding.tvBtnBottom.setOnClickListener(onSingleClickListener);
            }

            binding.line.setVisibility(pos == 0 ? View.INVISIBLE : View.VISIBLE);
        }

        @Override
        public void onBindView(ItemVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ViewHolder

    public static class ItemVO extends BaseData {
        private DetailType detailType;
        private String content;

        public ItemVO(DetailType detailType, String content) {
            this.detailType = detailType;
            this.content = content;
        }
    } // end of class ItemVO
} // end of class ChargeStationDetailListAdapter
