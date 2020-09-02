package com.genesis.apps.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

public abstract class BaseListViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Row<?>> items = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position));
    }

    @SuppressWarnings("unchecked")
    public <D> D getItem(int position) {
        return (D) items.get(position);
    }

    public void addRow(Row<?> row) {
        this.items.add(row);
    }

    public void addRows(List<Row<?>> rows) {
        this.items.addAll(rows);
    }

    public void setRow(int position, Row<?> row) {
        this.items.set(position, row);
    }

    public void setRows(List<Row<?>> mRows) {
        clear();
        this.items.addAll(mRows);
    }

    public void remove(int position) {
        if (getItemCount() - 1 < position) {
            return;
        }
        this.items.remove(position);
    }

    public void remove(Row<?> row) {
        this.items.remove(row);
    }

    public void clear() {
        this.items.clear();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getItemViewType();
    }


    public View getView(ViewGroup parent,int layout){
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }


    @EqualsAndHashCode(callSuper=false)
    public @Data
    static class Row<D> {
        private D item;
        private int itemViewType;

        private Row(D item, int itemViewType) {
            this.item = item;
            this.itemViewType = itemViewType;
        }

        public static <D> Row<D> create(D item, int itemViewType) {
            return new Row<>(item, itemViewType);
        }
    }

    public void updateItems(ArrayList<Row<?>> items) {
        new Thread(() -> {
            RecyclerDiffCallback<?> callback = new RecyclerDiffCallback(getItems(), items, null);
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

            getItems().clear();
            getItems().addAll(items);
            result.dispatchUpdatesTo(BaseListViewAdapter.this);
        }).start();
    }

    public ArrayList<Row<?>> makeItems(Object data, int itemViewType){
        ArrayList<Row<?>> items = new ArrayList<>();
        items.add(Row.create(data,itemViewType));
        return items;
    }

    public ArrayList<Row<?>> makeItems(List<?> data, int itemViewType){
        ArrayList<Row<?>> items = new ArrayList<>();
        for(int i=0; i<data.size();i++){
            items.add(Row.create(data,itemViewType));
        }
        return items;
    }


    public List<Row<?>> getItems(){
        return items;
    }

}


