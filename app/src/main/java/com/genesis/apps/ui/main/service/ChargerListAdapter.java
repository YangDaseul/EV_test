package com.genesis.apps.ui.main.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.constants.ChargerStatus;
import com.genesis.apps.comm.model.vo.ChargerEptVO;
import com.genesis.apps.databinding.ItemChargerBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Arrays;

/**
 * Class Name : ChargerListAdapter
 *
 * @author Ki-man Kim
 * @since 2021-04-29
 */
public class ChargerListAdapter extends BaseRecyclerViewAdapter2<ChargerEptVO> {
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(getView(parent, R.layout.item_charger));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    private static class ItemViewHolder extends BaseViewHolder<ChargerEptVO, ItemChargerBinding> {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ChargerEptVO item) {
        }

        @Override
        public void onBindView(ChargerEptVO item, int pos) {
            Context context = getContext();
            ItemChargerBinding binding = getBinding();

            // 충전기 ID와 속도 타입 표시
            String type = "";
            if (ChargeSearchCategorytype.SUPER_SPEED.getCode().equalsIgnoreCase(item.getChargeDiv())) {
                // 초고속
                type = context.getString(ChargeSearchCategorytype.SUPER_SPEED.getTitleResId());
            } else if (ChargeSearchCategorytype.HIGH_SPEED.getCode().equalsIgnoreCase(item.getChargeDiv())) {
                // 고속
                type = context.getString(ChargeSearchCategorytype.HIGH_SPEED.getTitleResId());
            } else if (ChargeSearchCategorytype.SLOW_SPEED.getCode().equalsIgnoreCase(item.getChargeDiv())) {
                // 완속
                type = context.getString(ChargeSearchCategorytype.SLOW_SPEED.getTitleResId());
            }

            binding.tvChargerIdType.setText(item.getCpid() + ". " + type);

            // 충전기 상태와 충전 가격 표시
            String pay = item.getChargeUcost();
            if (TextUtils.isEmpty(pay)) {
                pay = "- ";
            }
            int statusTitleId = 0;
            try {
                statusTitleId = Arrays.stream(ChargerStatus.values()).filter(it -> it.name().equalsIgnoreCase(item.getStatusCd())).findFirst().get().getTitleResId();
            } catch (Exception ignored) {

            }
            if (statusTitleId != 0) {
                binding.tvChargerStatusPay.setText(context.getString(statusTitleId) + " | " + pay + context.getString(R.string.sm_evss04_16));
            } else {
                binding.tvChargerStatusPay.setText(pay + context.getString(R.string.sm_evss04_16));
            }
            if (statusTitleId == R.string.sm_evss04_15) {
                // 해당 충전기가 사용가능한 상태 - 상태, 가격 문구 색상 변경
                binding.tvChargerStatusPay.setTextColor(context.getColor(R.color.x_996449));
                if ("Y".equalsIgnoreCase(item.getReservYn())) {
                    // 예약 가능한 상태라면 예약 버튼 노출
                    binding.tvBtnReserve.setTag(item);
                    binding.tvBtnReserve.setVisibility(View.VISIBLE);
                } else {
                    binding.tvBtnReserve.setVisibility(View.GONE);
                }
            } else {
                // 사용가능 상태가 아닌 경우
                binding.tvChargerStatusPay.setTextColor(context.getColor(R.color.x_262626));
                binding.tvBtnReserve.setVisibility(View.GONE);
            }
        }

        @Override
        public void onBindView(ChargerEptVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ViewHolder
} // end of class ChargerListAdapter
