package com.genesis.apps.ui.main.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ChargerStatusSTT;
import com.genesis.apps.comm.model.constants.ChargerTypeSTT;
import com.genesis.apps.comm.model.vo.ChargerSttVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemChargerBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Arrays;

import androidx.annotation.NonNull;

/**
 * Class Name : ChargerListAdapter
 *
 * @author Ki-man Kim
 * @since 2021-04-29
 */
public class ChargerSTCListAdapter extends BaseRecyclerViewAdapter2<ChargerSttVO> {

    private OnSingleClickListener onSingleClickListener;

    public ChargerSTCListAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(getView(parent, R.layout.item_charger), onSingleClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    private static class ItemViewHolder extends BaseViewHolder<ChargerSttVO, ItemChargerBinding> {
        public ItemViewHolder(View itemView, OnSingleClickListener onSingleClickListener) {
            super(itemView);
            getBinding().tvBtnReserve.setOnClickListener(onSingleClickListener);
        }

        @Override
        public void onBindView(ChargerSttVO item) {
        }

        @Override
        public void onBindView(ChargerSttVO item, int pos) {
            Context context = getContext();
            ItemChargerBinding binding = getBinding();

            binding.line0.setVisibility(pos == 0 ? View.VISIBLE : View.INVISIBLE);

            // ????????? ID
            binding.tvChargerId.setText(item.getCid());

            // ????????? ?????? ?????? ??????
            try {
                int titleId = Arrays.stream(ChargerTypeSTT.values()).filter(it -> it.getCode().equalsIgnoreCase(item.getCsupport())).findFirst().get().getTitleResId();
                binding.tvChargerType.setText(context.getString(titleId) + "  | ");
            } catch (Exception e) {

            }

            // ????????? ??????
            int statusTitleId = 0;
            try {
                statusTitleId = Arrays.stream(ChargerStatusSTT.values()).filter(it -> it.getCode().equalsIgnoreCase(StringUtil.isValidString(item.getStusCd()))).findFirst().orElse(ChargerStatusSTT.UNKNOWN).getTitleResId();
            } catch (Exception ignored) {

            }

            if (statusTitleId != 0) {
                binding.tvChargerStatus.setText(context.getString(statusTitleId));
            }

            // ?????? ??????
            String pay = item.getChgPrice();
            if (TextUtils.isEmpty(pay)) {
                pay = "- ";
            }
            binding.tvChargerPay.setText(" |  " + pay + context.getString(R.string.sm_evss04_16));

            if (statusTitleId == R.string.sm_evss04_15) {
                // ?????? ???????????? ??????????????? ?????? - ??????, ?????? ?????? ?????? ??????
                binding.tvChargerStatus.setTextColor(context.getColor(R.color.x_996449));
                if ("Y".equalsIgnoreCase(item.getReservYn())) {
                    // ?????? ????????? ???????????? ?????? ?????? ??????
                    binding.tvBtnReserve.setTag(item);
                    binding.tvBtnReserve.setVisibility(View.VISIBLE);
                } else {
                    binding.tvBtnReserve.setVisibility(View.GONE);
                }
            } else {
                // ???????????? ????????? ?????? ??????
                binding.tvChargerStatus.setTextColor(context.getColor(R.color.x_000000));
                binding.tvBtnReserve.setVisibility(View.GONE);
            }
        }

        @Override
        public void onBindView(ChargerSttVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ViewHolder
} // end of class ChargerListAdapter
