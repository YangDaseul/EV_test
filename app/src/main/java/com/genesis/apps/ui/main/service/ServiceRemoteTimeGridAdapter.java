package com.genesis.apps.ui.main.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.SnackBarUtil;

import java.util.Calendar;
import java.util.List;

/**
 * Class Name : ServiceRemoteTimeGridAdapter
 * 원격 진단 신청 - 시간 선택 다이얼로그의 시간 목록을 표시하기 위한 Adapter Class.
 * <p>
 * Created by Ki-man Kim on 12/17/20
 */
public class ServiceRemoteTimeGridAdapter extends RecyclerView.Adapter<ServiceRemoteTimeGridAdapter.ViewHolder>
        implements View.OnClickListener {
    /**
     * 현재 시간을 0시 부터 몇분 지났는지 값.
     */
    private int nowTimeMin;
    /**
     * 시간대 데이터 목록
     */
    private List<TimeVO> datas;

    /**
     * 시간대 선택 이벤트 Object.
     */
    private OnItemSelectListener listener;

    interface OnItemSelectListener {
        void onSelectItem(TimeVO item);
    }

    public ServiceRemoteTimeGridAdapter(@NonNull List<TimeVO> datas) {
        this.datas = datas;
        Calendar nowTime = Calendar.getInstance();
        nowTimeMin = getTimeMin(nowTime.get(Calendar.HOUR_OF_DAY), nowTime.get(Calendar.MINUTE));
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
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
            //선택 불가능한 시간에 클릭 시 스낵바 안내 필요
            //임의로 enable 해제 및 색상 변경처리 진행
            holder.tvTime.setEnabled(true);
            holder.tvTime.setBackgroundResource(R.drawable.bg_ffffff_stroke_cccccc);
            holder.tvTime.setTextColor(holder.tvTime.getContext().getColor(R.color.x_cccccc));
            holder.tvTime.setOnClickListener(view -> SnackBarUtil.show(holder.tvTime.getContext(), "선택하신 시간에 원격진단 상담이 어렵습니다.\n상담이 가능한 다른 시간을 선택해 주세요."));
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

    /****************************************************************************************************
     * Private Method
     ****************************************************************************************************/
    /**
     * 시간과 분을 입력하여 0시 부터 몇분의 시간이 지난 상태인지 계산해주는 함수.
     *
     * @param hour   계산할 '시' 값.
     * @param minute 계산할 '분' 값.
     * @return 계산 결과.
     */
    private int getTimeMin(int hour, int minute) {
        return hour * 60 + minute;
    }

    /**
     * 아이템 선택 이벤트 설정 함수.
     *
     * @param listener
     */
    public void setListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    /****************************************************************************************************
     * Inner Class.
     ****************************************************************************************************/
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

    /**
     * 시간 목록을 표시가기 위한 Item View Hodler Class.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    } // end of class ViewHolder
} // end of class ServiceRemoteTimeGridAdapter
