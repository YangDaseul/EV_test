package com.genesis.apps.ui.main.service;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Ki-man Kim on 12/17/20
 */
public class ServiceRemoteTimeGridAdapter extends RecyclerView.Adapter<ServiceRemoteTimeGridAdapter.ViewHolder>
        implements View.OnClickListener {
    private int nowTimeMin;
    private List<TimeVO> datas;

    private OnItemSelectListener listener;

    interface OnItemSelectListener {
        void onSelectItem(TimeVO item);
    }

    public ServiceRemoteTimeGridAdapter(@NonNull List<TimeVO> datas) {
        this.datas = datas;
        Calendar nowTime = Calendar.getInstance();
        nowTimeMin = getTimeMin(nowTime.get(Calendar.HOUR_OF_DAY), nowTime.get(Calendar.MINUTE));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_remote_select_time, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeVO item = datas.get(position);
        boolean isAble = getTimeMin(item.hour, item.minute) > nowTimeMin;
        holder.tvTime.setEnabled(isAble);
        holder.tvTime.setText(item.toString());
        if (isAble) {
            holder.tvTime.setTag(item);
            holder.tvTime.setOnClickListener(this);
        } else {
            holder.tvTime.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof TimeVO && listener != null) {
            listener.onSelectItem((TimeVO) tag);
        }
    }

    private int getTimeMin(int hour, int minute) {
        return hour * 60 + minute;
    }

    public void setListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    /**
     * 상담 시간을 표시하기 위해 만든 상담 시간 목록 데이터 Object.
     * 패키지를 정리할 때 알맞는 위치로 변경이 필요해 보임.
     */
    public static class TimeVO {
        private int hour;
        private int minute;

        public TimeVO(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        public String getTime() {
            return String.format("%02d%02d", hour, minute);
        }

        @NonNull
        @Override
        public String toString() {
            return String.format("%02d:%02d", hour, minute);
        }
    } // end of class TimeVO

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    } // end of class ViewHolder
} // end of class ServiceRemoteTimeGridAdapter
