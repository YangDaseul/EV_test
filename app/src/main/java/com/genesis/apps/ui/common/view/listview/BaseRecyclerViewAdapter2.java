package com.genesis.apps.ui.common.view.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerViewAdapter2<D extends BaseData> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<D> items = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position));
    }

    public List<D> getItems() {
        return items;
    }

    @SuppressWarnings("unchecked")
    public <D> D getItem(int position) {
        return (D) items.get(position);
    }

    public void addRow(D row) {
        items.add(row);
    }

    public void addRows(List<D> rows) {
        this.items.addAll(rows);
    }

    public void setRow(int position, D row) {
        this.items.set(position, row);
    }

    public void setRows(List<D> mRows) {
        clear();
        if (mRows != null) this.items.addAll(mRows);
    }

    public void remove(int pos) {
        this.items.remove(pos);
    }

    public void clear() {
        this.items.clear();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //    @Override
//    public int getItemViewType(int position) {
//        return items.get(position).getItemViewType();
//    }
    public View getView(ViewGroup parent, int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }
}


