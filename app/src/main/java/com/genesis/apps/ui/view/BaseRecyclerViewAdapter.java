package com.genesis.apps.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Row<?>> mRows = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position));
    }

    @SuppressWarnings("unchecked")
    public <T> T getItem(int position) {
        return (T) mRows.get(position).getItem();
    }

    public void addRow(Row<?> row) {
        this.mRows.add(row);
    }

    public void addRows(List<Row<?>> rows) {
        this.mRows.addAll(rows);
    }

    public void setRow(int position, Row<?> row) {
        this.mRows.set(position, row);
    }

    public void setRows(List<Row<?>> mRows) {
        clear();
        this.mRows.addAll(mRows);
    }

    public void remove(int position) {
        if (getItemCount() - 1 < position) {
            return;
        }
        this.mRows.remove(position);
    }

    public void remove(Row<?> row) {
        this.mRows.remove(row);
    }

    public void clear() {
        this.mRows.clear();
    }

    @Override
    public int getItemCount() {
        return mRows.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mRows.get(position).getItemViewType();
    }

    public static class Row<T> {
        private T item;
        private int itemViewType;

        private Row(T item, int itemViewType) {
            this.item = item;
            this.itemViewType = itemViewType;
        }

        public static <T> Row<T> create(T item, int itemViewType) {
            return new Row<>(item, itemViewType);
        }

        public T getItem() {
            return item;
        }

        public int getItemViewType() {
            return itemViewType;
        }
    }

    public View getView(ViewGroup parent,int layout){
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

}
