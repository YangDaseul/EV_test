package com.genesis.apps.ui.main.service.view;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.constants.ChargePlaceStatus;
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
public class ChargePlaceListAdapter extends BaseRecyclerViewAdapter2<ChargePlaceListAdapter.DummyData> {
    private SubActivity activity;

    public ChargePlaceListAdapter(SubActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ChargePlaceViewHolder(layoutInflater.inflate(R.layout.item_charge_place, parent, false));
    }

    private static class ChargePlaceViewHolder extends BaseViewHolder<DummyData, ItemChargePlaceBinding> {
        public ChargePlaceViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(DummyData item) {
            Log.d("FID", "test :: 1111 :: onBindView :: item=" + item);
            ItemChargePlaceBinding binding = getBinding();
            binding.tvChargeName.setText(item.name + " " + item.distance);

            switch (item.status) {
                case FINISH_BOOK:
                case ABLE_BOOK: {
                    binding.tvChargeName.setText(Html.fromHtml(binding.tvChargeName.getText() + " | <font color=#996449>" + item.status.getName() + "</font>"));
                    break;
                }
                default:
                case CHECKING: {
                    break;
                }
            }

            if (!TextUtils.isEmpty(item.statusDesc)) {
                // 충전소 상태 표시.
                binding.tvChargeStatus.setText(item.statusDesc);
            }
        }

        @Override
        public void onBindView(DummyData item, int pos) {

        }

        @Override
        public void onBindView(DummyData item, int pos, SparseBooleanArray selectedItems) {
            Log.d("FID", "test :: 2222 :: onBindView :: item=" + item + " :: pos=" + pos + " :: selectedItems=" + selectedItems);

        }
    }

    public static class DummyData extends BaseData {
        private String name;
        private String distance;
        private ChargePlaceStatus status;
        private String statusDesc;

        public DummyData(String name, String distance, ChargePlaceStatus status, String statusDesc) {
            this.name = name;
            this.distance = distance;
            this.status = status;
            this.statusDesc = statusDesc;
        }

        public String getName() {
            return name;
        }

        public String getDistance() {
            return distance;
        }

        public ChargePlaceStatus getStatus() {
            return status;
        }

        public String getStatusDesc() {
            return statusDesc;
        }
    }
} // end of class ChargePlaceListAdapter
