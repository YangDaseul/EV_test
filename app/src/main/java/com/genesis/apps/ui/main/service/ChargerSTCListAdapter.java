package com.genesis.apps.ui.main.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;
import com.genesis.apps.comm.model.constants.ChargerStatus;
import com.genesis.apps.comm.model.constants.ChargerStatusSTT;
import com.genesis.apps.comm.model.constants.ChargerTypeSTT;
import com.genesis.apps.comm.model.vo.ChargerEptVO;
import com.genesis.apps.comm.model.vo.ChargerSttVO;
import com.genesis.apps.databinding.ItemChargerBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Arrays;

/**
 * Class Name : ChargerListAdapter
 *
 * @author Ki-man Kim
 * @since 2021-04-29
 */
public class ChargerSTCListAdapter extends BaseRecyclerViewAdapter2<ChargerSttVO> {

    private String reservYn;
    private String chgPrice;

    private OnSingleClickListener onSingleClickListener;

    public ChargerSTCListAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(getView(parent, R.layout.item_charger), onSingleClickListener)
                .setReservYn(reservYn)
                .setChgPrice(chgPrice);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    public void setReservYn(String reservYn) {
        this.reservYn = reservYn;
    }

    public void setChgPrice(String chgPrice) {
        this.chgPrice = chgPrice;
    }

    private static class ItemViewHolder extends BaseViewHolder<ChargerSttVO, ItemChargerBinding> {
        private String reservYn;
        private String chgPrice;

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

            // 충전기 ID
            binding.tvChargerId.setText(item.getCid());

            // 충전기 속도 타입 표시
            try {
                int titleId = Arrays.stream(ChargerTypeSTT.values()).filter(it -> it.getCode().equalsIgnoreCase(item.getCsupport())).findFirst().get().getTitleResId();
                binding.tvChargerType.setText(context.getString(titleId) + " |");
            } catch (Exception e) {

            }

            // 충전기 상태
            int statusTitleId = 0;
            try {
                statusTitleId = Arrays.stream(ChargerStatusSTT.values()).filter(it -> it.getCode().equalsIgnoreCase(item.getStusCd())).findFirst().get().getTitleResId();
            } catch (Exception ignored) {

            }

            if (statusTitleId != 0) {
                binding.tvChargerStatus.setText(context.getString(statusTitleId));
            }

            // 충전 가격
            String pay = chgPrice;
            if (TextUtils.isEmpty(pay)) {
                pay = "- ";
            }
            binding.tvChargerPay.setText("| " + pay + context.getString(R.string.sm_evss04_16));

            if (statusTitleId == R.string.sm_evss04_15) {
                // 해당 충전기가 사용가능한 상태 - 상태, 가격 문구 색상 변경
                binding.tvChargerStatus.setTextColor(context.getColor(R.color.x_996449));
                if ("Y".equalsIgnoreCase(reservYn)) {
                    // 예약 가능한 상태라면 예약 버튼 노출
                    binding.tvBtnReserve.setTag(item);
                    binding.tvBtnReserve.setVisibility(View.VISIBLE);
                } else {
                    binding.tvBtnReserve.setVisibility(View.GONE);
                }
            } else {
                // 사용가능 상태가 아닌 경우
                binding.tvChargerStatus.setTextColor(context.getColor(R.color.x_262626));
                binding.tvBtnReserve.setVisibility(View.GONE);
            }
        }

        @Override
        public void onBindView(ChargerSttVO item, int pos, SparseBooleanArray selectedItems) {

        }

        public ItemViewHolder setReservYn(String reservYn) {
            this.reservYn = reservYn;
            return this;
        }

        public ItemViewHolder setChgPrice(String chgPrice) {
            this.chgPrice = chgPrice;
            return this;
        }
    } // end of class ViewHolder
} // end of class ChargerListAdapter
