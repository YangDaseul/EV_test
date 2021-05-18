package com.genesis.apps.ui.main.service.view;

import android.text.Html;
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
            binding.lWhole.setTag(item);
            binding.tvBtnRouteDetail.setTag(R.id.item, item);
            binding.tvChargeStatus.setText(Html.fromHtml(item.getChargeStatus(getContext()), Html.FROM_HTML_MODE_COMPACT));
            if (item.isReserve()) {
                // 예약 가능한 상태.
                binding.tvBookStatus.setVisibility(View.VISIBLE);
                binding.tvBookStatus.setText(R.string.sm_evss01_30);
            } else {
                // 예약 불가능한 상태.
                binding.tvBookStatus.setVisibility(View.GONE);
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
