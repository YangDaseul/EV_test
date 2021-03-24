package com.genesis.apps.ui.main.service.view;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.databinding.ItemChargeCategoryBinding;
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
    public enum Type {
        CATEGORY,
        PLACE
    }

    private SubActivity activity;

    public ChargePlaceListAdapter(SubActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (Type.CATEGORY.ordinal() == viewType) {
            return new ChargeCategoryViewHolder(this.activity, layoutInflater.inflate(R.layout.item_charge_category, parent, false));
        } else if (Type.PLACE.ordinal() == viewType) {
            return new ChargePlaceViewHolder(layoutInflater.inflate(R.layout.item_charge_place, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return getItems().get(position).type.ordinal();
    }

    private static class ChargeCategoryViewHolder extends BaseViewHolder<DummyData, ItemChargeCategoryBinding> {
        private SubActivity activity;

        public ChargeCategoryViewHolder(SubActivity activity, View itemView) {
            super(itemView);
            this.activity = activity;
        }

        @Override
        public void onBindView(DummyData item) {
            getBinding().setActivity(this.activity);
            getBinding().setData(item);
            getBinding().tvMore.setTag(item);
        }

        @Override
        public void onBindView(DummyData item, int pos) {

        }

        @Override
        public void onBindView(DummyData item, int pos, SparseBooleanArray selectedItems) {

        }
    }

    private static class ChargePlaceViewHolder extends BaseViewHolder<DummyData, ItemChargePlaceBinding> {
        public ChargePlaceViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(DummyData item) {
            getBinding().setData(item);
        }

        @Override
        public void onBindView(DummyData item, int pos) {

        }

        @Override
        public void onBindView(DummyData item, int pos, SparseBooleanArray selectedItems) {

        }
    }

    public static class DummyData extends BaseData {
        private Type type;

        private String name;
        private String distance;
        private String status;

        public DummyData(Type type, String name) {
            this.type = type;
            this.name = name;
        }

        public DummyData(Type type, String name, String distance, String status) {
            this.type = type;
            this.name = name;
            this.distance = distance;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public String getDistance() {
            return distance;
        }

        public String getStatus() {
            return status;
        }
    }
} // end of class ChargePlaceListAdapter
