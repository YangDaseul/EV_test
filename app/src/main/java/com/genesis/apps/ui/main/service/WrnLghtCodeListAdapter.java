package com.genesis.apps.ui.main.service;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;

import java.util.List;

/**
 * Created by Ki-man Kim on 12/16/20
 */
public class WrnLghtCodeListAdapter extends RecyclerView.Adapter<WrnLghtCodeListAdapter.ViewHolder>
        implements View.OnClickListener {
    private final int MAX_COLUMNS = 2;
    private List<ServiceRemoteRegisterActivity.WRN_LGHT_CODE> data;

    private OnItemSelectListener listener;

    interface OnItemSelectListener {
        void onSelectItem(ServiceRemoteRegisterActivity.WRN_LGHT_CODE item);
    }

    public WrnLghtCodeListAdapter(List<ServiceRemoteRegisterActivity.WRN_LGHT_CODE> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_service_remote_wrn_lght_code, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int startIndex = getStartIndex(position);
        int endIndex = startIndex + 1;

        Log.d("FID", "test :: position=" + position);
        Log.d("FID", "test :: startIndex=" + startIndex);
        if (startIndex < data.size()) {
            ServiceRemoteRegisterActivity.WRN_LGHT_CODE item = data.get(startIndex);
            holder.txtCode0.setText(item.messageResId());
            holder.txtCode0.setCompoundDrawablesWithIntrinsicBounds(0, item.iconResId(), 0, 0);
            holder.txtCode0.setTag(item);
            holder.txtCode0.setOnClickListener(this);
        }

        if (endIndex < data.size()) {
            ServiceRemoteRegisterActivity.WRN_LGHT_CODE item = data.get(endIndex);
            holder.txtCode1.setText(item.messageResId());
            holder.txtCode1.setCompoundDrawablesWithIntrinsicBounds(0, item.iconResId(), 0, 0);
            holder.txtCode1.setVisibility(View.VISIBLE);
            holder.txtCode1.setTag(item);
            holder.txtCode1.setOnClickListener(this);
        } else {
            holder.txtCode1.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.data != null ? (int) Math.ceil((float) this.data.size() / MAX_COLUMNS) : 0;
    }

    private int getStartIndex(int position) {
        return position * MAX_COLUMNS;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof ServiceRemoteRegisterActivity.WRN_LGHT_CODE && this.listener != null) {
            this.listener.onSelectItem((ServiceRemoteRegisterActivity.WRN_LGHT_CODE) tag);
        }
    }

    public WrnLghtCodeListAdapter setListener(OnItemSelectListener listener) {
        this.listener = listener;
        return this;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCode0;
        TextView txtCode1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCode0 = itemView.findViewById(R.id.tv_wrn_lght_code_0);
            txtCode1 = itemView.findViewById(R.id.tv_wrn_lght_code_1);
        }
    } // end of class ViewHolder
} // end of class WrnLghtCodeListAdapter