package com.genesis.apps.ui.main.service;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemRelapseHistoryNewBinding;
import com.genesis.apps.databinding.ItemRelapseHistoryNotiBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Date;
import java.util.Locale;

public class ServiceRelapseHistoryAdapter extends BaseRecyclerViewAdapter2<VOCInfoVO> {
    private static final String TAG = ServiceRelapseHistoryAdapter.class.getSimpleName();

    public static final int TYPE_NO_DATA = -1;
    private static final int TYPE_FINISHED = 1;
    private static final int TYPE_WAITING = 2;
    private static final int TYPE_NOTI = 3;

    private static OnSingleClickListener onSingleClickListener;
    private String mdlNm;

    ServiceRelapseHistoryAdapter(OnSingleClickListener listener, String mdlNm) {
        onSingleClickListener = listener;
        this.mdlNm = mdlNm;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType: position : " + position);

        VOCInfoVO item = getItem(position);

        if (item == null) {
            if (position == 0) {
                //첫칸 null은 내역이 없다는 거. 내역없음 뷰를 표시하자.
                return TYPE_NO_DATA;
            } else {
                //첫 칸이 아닌데 들어있는 null은 마지막에 안내문 출력하려고 끼워넣은 더미
                return TYPE_NOTI;
            }
        }

        switch (item.getInpSt()) {
            //접수 중
            case VOCInfoVO.INP_ST_CODE_REQ:
            case VOCInfoVO.INP_ST_CODE_WAITING:
                return TYPE_WAITING;

            //신청완료
            case VOCInfoVO.INP_ST_CODE_FINISH:
                return TYPE_FINISHED;

            //이상한 값
            default:
                Log.d(TAG, "findItemType: unexpected value" + item.getInpSt());
                return TYPE_NO_DATA;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: viewType : " + viewType);

        switch (viewType) {
            case TYPE_FINISHED:
            case TYPE_WAITING:
                return new RelapseHistoryViewHolder(getView(parent, R.layout.item_relapse_history_new));

            case TYPE_NOTI:
                return new RelapseHistoryNotiViewHolder(getView(parent, R.layout.item_relapse_history_noti), mdlNm);

            case TYPE_NO_DATA:
            default:
                return new RelapseHistoryViewHolder(getView(parent, R.layout.item_relapse_history_no_data));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.d(TAG, "serviceDriveHistoryAdapter onBindViewHolder position : " + position);
        holder.onBindView(getItem(position), position);
    }

    //하자 재발 뷰 홀더
    public static class RelapseHistoryViewHolder extends BaseViewHolder<VOCInfoVO, ItemRelapseHistoryNewBinding> {
        public boolean finished;
        public String vin;
        public String dateStr;

        public RelapseHistoryViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "RelapseHistoryViewHolder: ");
        }

        @Override
        public void onBindView(VOCInfoVO item) {
            //do nothing
        }

        @Override
        public void onBindView(VOCInfoVO item, int pos) {
            Log.d(TAG, "Holder. onBindView: ");

            if (!hasData(getItemViewType())) {
                return;
            }

            //상태
            String status = item.getInpSt();

            switch (status) {
                case VOCInfoVO.INP_ST_CODE_REQ:
                case VOCInfoVO.INP_ST_CODE_WAITING:
                    finished = false;
                    setTagAndListener(item);
                    break;

                case VOCInfoVO.INP_ST_CODE_FINISH:
                    finished = true;
                    setTagAndListener(item);
                    break;
                default:
                    //do nothing
                    break;
            }

            //신청 일시
            Date date = DateUtil.getDefaultDateFormat(item.getInpDate(), DateUtil.DATE_FORMAT_yyyyMMdd);
            dateStr = DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_mm_dd_dot);

            vin = item.getVin();
            getBinding().setHolder(this);
        }

        @Override
        public void onBindView(VOCInfoVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }

        private boolean hasData(int itemViewType) {
            switch (itemViewType) {
                case TYPE_FINISHED:
                case TYPE_WAITING:
                    return true;

                case TYPE_NO_DATA:
                case TYPE_NOTI:
                default:
                    return false;
            }
        }

        private void setTagAndListener(VOCInfoVO item) {
            getBinding().getRoot().setOnClickListener(onSingleClickListener);
            getBinding().getRoot().setTag(R.id.tag_relapse_history, item);
        }
    }





    //하자 재발 뷰 홀더
    public static class RelapseHistoryNotiViewHolder extends BaseViewHolder<VOCInfoVO, ItemRelapseHistoryNotiBinding> {
        public String mdlNm;

        public RelapseHistoryNotiViewHolder(View itemView, String mdlNm) {
            super(itemView);
            this.mdlNm = mdlNm;
        }

        @Override
        public void onBindView(VOCInfoVO item) {
            //do nothing
        }

        @Override
        public void onBindView(VOCInfoVO item, int pos) {
            Log.d(TAG, "Holder. onBindView: ");
            if(!TextUtils.isEmpty(mdlNm)&& (mdlNm.equalsIgnoreCase("G90")||mdlNm.equalsIgnoreCase("EQ900"))){
                getBinding().tvRelapseNoti2.setText(String.format(Locale.getDefault(), getContext().getString(R.string.relapse_history_noti_02), getContext().getString(R.string.word_home_25)));
            }else{
                getBinding().tvRelapseNoti2.setText(String.format(Locale.getDefault(), getContext().getString(R.string.relapse_history_noti_02), getContext().getString(R.string.word_home_14)));
            }
        }

        @Override
        public void onBindView(VOCInfoVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }
    }
}
