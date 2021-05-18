package com.genesis.apps.ui.main.service.view;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.ReserveVo;
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
public class ChargeSTCPlaceListAdapter extends BaseRecyclerViewAdapter2<ReserveVo> {
    private SubActivity activity;

    public ChargeSTCPlaceListAdapter(SubActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ChargePlaceViewHolder(this.activity, layoutInflater.inflate(R.layout.item_charge_place, parent, false));
    }

    private static class ChargePlaceViewHolder extends BaseViewHolder<ReserveVo, ItemChargePlaceBinding> {
        private SubActivity activity;

        public ChargePlaceViewHolder(SubActivity activity, View itemView) {
            super(itemView);
            this.activity = activity;
        }

        @Override
        public void onBindView(ReserveVo item) {
            ItemChargePlaceBinding binding = getBinding();
            binding.setActivity(this.activity);
            binding.tvChargeName.setText(item.getChgName());
            binding.tvDist.setText(item.getDist() + "km");
            binding.vgChargePlaceContainer.setTag(item);


            // 운영중인 경우 - 예약 상태 표시
            Context context = getContext();
            StringBuilder strBuilder = new StringBuilder();
            int superSpeedCnt = 0;
            int highSpeedCnt = 0;
            int slowSpeedCnt = 0;

            int useSuperSpeedCnt = 0;
            int useHighSpeedCnt = 0;
            int useSlowSpeedCnt = 0;


            try {
                // 사용 가능 충전기 수
                superSpeedCnt = Integer.parseInt(item.getUsablSuperSpeedCnt());
                highSpeedCnt = Integer.parseInt(item.getUsablHighSpeedCnt());
                slowSpeedCnt = Integer.parseInt(item.getUsablSlowSpeedCnt());
                // 사용중 충전기 수
                useSuperSpeedCnt = Integer.parseInt(item.getUseSuperSpeedCnt());
                useHighSpeedCnt = Integer.parseInt(item.getUseHighSpeedCnt());
                useSlowSpeedCnt = Integer.parseInt(item.getUseSlowSpeedCnt());
            } catch (Exception ignore) {

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
            if (superSpeedCnt + highSpeedCnt + slowSpeedCnt + useSuperSpeedCnt + useHighSpeedCnt + useSlowSpeedCnt > 0) {
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
        public void onBindView(ReserveVo item, int pos) {

        }

        @Override
        public void onBindView(ReserveVo item, int pos, SparseBooleanArray selectedItems) {

        }
    } // end of class ChargePlaceViewHolder
} // end of class ChargePlaceListAdapter
