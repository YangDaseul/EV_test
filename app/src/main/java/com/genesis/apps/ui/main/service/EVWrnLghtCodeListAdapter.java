package com.genesis.apps.ui.main.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;

import java.util.List;

/**
 * Class Name : WrnLghtCodeListAdapter
 * 경고등 표시를 위한 View Adapter Class.
 * <p>
 * Created by Ki-man Kim on 12/16/20
 */
public class EVWrnLghtCodeListAdapter extends RecyclerView.Adapter<EVWrnLghtCodeListAdapter.ViewHolder>
        implements View.OnClickListener {
    /**
     * 가로로 표시할 최대 갯수.
     * 나중에 확장성도 고려를 해보기 위해 우선 변수로 제어하기 위해 만듬.
     */
    private final int MAX_COLUMNS = 2;
    /**
     * 경고등 데이터 목록 Object.
     */
    private List<ServiceRemoteRegisterActivity.EV_WRN_LGHT_CODE> data;

    /**
     * 경고등 아이템 선택 이벤트 Object.
     */
    private OnItemSelectListener listener;

    interface OnItemSelectListener {
        void onSelectItem(ServiceRemoteRegisterActivity.EV_WRN_LGHT_CODE item);
    }

    public EVWrnLghtCodeListAdapter(List<ServiceRemoteRegisterActivity.EV_WRN_LGHT_CODE> data) {
        this.data = data;
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
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

        if (startIndex < data.size()) {
            ServiceRemoteRegisterActivity.EV_WRN_LGHT_CODE item = data.get(startIndex);
            holder.txtCode0.setText(item.messageResId());
            holder.txtCode0.setCompoundDrawablesWithIntrinsicBounds(0, item.iconResId(), 0, 0);
            holder.txtCode0.setTag(item);
            holder.txtCode0.setOnClickListener(this);
        }

        if (endIndex < data.size()) {
            ServiceRemoteRegisterActivity.EV_WRN_LGHT_CODE item = data.get(endIndex);
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

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof ServiceRemoteRegisterActivity.EV_WRN_LGHT_CODE && this.listener != null) {
            this.listener.onSelectItem((ServiceRemoteRegisterActivity.EV_WRN_LGHT_CODE) tag);
        }
    }

    /****************************************************************************************************
     * Private Method
     ****************************************************************************************************/
    /**
     * 시작 인덱스 설정 계산 함수.
     * 데이터 배열에서 {@link MAX_COLUMNS} 만큼 나누어 표시를 위해 계산하는 함수.
     *
     * @param position
     * @return
     */
    private int getStartIndex(int position) {
        return position * MAX_COLUMNS;
    }

    /****************************************************************************************************
     * Public Method
     ****************************************************************************************************/
    /**
     * 경고등 아이템 선택 이벤트 설정 함수.
     *
     * @param listener
     * @return {@link EVWrnLghtCodeListAdapter} Object.
     */
    public EVWrnLghtCodeListAdapter setListener(OnItemSelectListener listener) {
        this.listener = listener;
        return this;
    }

    /****************************************************************************************************
     * Inner Class
     ****************************************************************************************************/
    /**
     * 경고등 Item View Holder Class.
     */
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