package com.genesis.apps.ui.main.service.view;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ChargeEptInfoVO;
import com.genesis.apps.databinding.ItemChargePlaceBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

/**
 * Class Name : ChargePlaceListAdapter
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
public class ChargePlaceListAdapter extends BaseRecyclerViewAdapter2<ChargeEptInfoVO> {
    private SubActivity activity;

    public ChargePlaceListAdapter(SubActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ChargePlaceViewHolder(this.activity, layoutInflater.inflate(R.layout.item_charge_place, parent, false));
    }

    private static class ChargePlaceViewHolder extends BaseViewHolder<ChargeEptInfoVO, ItemChargePlaceBinding> {
        private SubActivity activity;

        public ChargePlaceViewHolder(SubActivity activity, View itemView) {
            super(itemView);
            this.activity = activity;
        }

        @Override
        public void onBindView(ChargeEptInfoVO item) {
            ItemChargePlaceBinding binding = getBinding();
            binding.setActivity(this.activity);
            binding.tvChargeName.setText(item.getCsnm());
            binding.tvDist.setText(item.getDist() + "km");
            binding.ivArrow.setTag(item);

            if ("Y".equalsIgnoreCase(item.getUseYn())) {
                // 운영중인 경우 - 예약 상태 표시
                Context context = getContext();
                StringBuilder strBuilder = new StringBuilder();
                int superSpeedCnt = 0;
                int highSpeedCnt = 0;
                int slowSpeedCnt = 0;
                try {
                    superSpeedCnt = Integer.parseInt(item.getSuperSpeedCnt());
                    highSpeedCnt = Integer.parseInt(item.getHighSpeedCnt());
                    slowSpeedCnt = Integer.parseInt(item.getSlowSpeedCnt());
                } catch (Exception e) {

                }
                if (superSpeedCnt > 0) {
                    strBuilder.append(String.format(context.getString(R.string.sm_evss02_01), superSpeedCnt));
                }
                if (highSpeedCnt > 0) {
                    if (strBuilder.length() > 0) {
                        strBuilder.append(", ");
                    }
                    strBuilder.append(String.format(context.getString(R.string.sm_evss02_02), highSpeedCnt));
                }
                if (slowSpeedCnt > 0) {
                    if (strBuilder.length() > 0) {
                        strBuilder.append(", ");
                    }
                    strBuilder.append(String.format(context.getString(R.string.sm_evss02_03), slowSpeedCnt));
                }
                if ("Y".equalsIgnoreCase(item.getReservYn())) {
                    // 예약 가능한 상태.
                    binding.tvBookStatus.setVisibility(View.VISIBLE);
                    binding.tvBookStatus.setText(R.string.sm_evss01_30);
                    binding.tvChargeStatus.setText(strBuilder.toString() + " " + context.getString(R.string.sm_evss03_04));
                } else {
                    // 예약 불가능한 상태.
                    binding.tvBookStatus.setVisibility(View.GONE);
                    binding.tvChargeStatus.setText(R.string.sm_evss01_33);
                }
            } else {
                // 기타 상태 - 점검중으로 표시.
                binding.tvBookStatus.setVisibility(View.GONE);
                binding.tvChargeStatus.setText(R.string.sm_evss01_32);
            }
        }

        @Override
        public void onBindView(ChargeEptInfoVO item, int pos) {

        }

        @Override
        public void onBindView(ChargeEptInfoVO item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ChargePlaceViewHolder
} // end of class ChargePlaceListAdapter
