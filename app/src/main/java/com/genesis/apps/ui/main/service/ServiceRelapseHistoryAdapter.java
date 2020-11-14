package com.genesis.apps.ui.main.service;

import android.app.Activity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.DDS_1001;
import com.genesis.apps.comm.model.api.gra.DDS_1002;
import com.genesis.apps.comm.model.vo.DriveServiceVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemRelapseHistoryBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Date;
import java.util.Locale;

public class ServiceRelapseHistoryAdapter extends BaseRecyclerViewAdapter2<DriveServiceVO> {
    private static final String TAG = ServiceRelapseHistoryAdapter.class.getSimpleName();

    public static final int TYPE_NO_DATA = -1;
    private static final int TYPE_FINISHED = 1;
    private static final int TYPE_WAITING = 2;
    private static final int TYPE_NOTI = 3;

    private int pageNo;

    public int getPageNo() {
        return pageNo;
    }

    public void incPageNo() {
        ++pageNo;
    }

    ServiceRelapseHistoryAdapter() {
        //todo : 공지사항 메시지를 나타낼 더미(?) 데이터 아이템 하나 만들기
//        addRow();
    }

    @Override
    public int getItemViewType(int position) {
        DriveServiceVO item = getItem(position);

        return findItemType(item.getSvcStusCd());
    }

    public int findItemType(String statusCode) {
        Log.d(TAG, "findItemType: SvcStusCd : " + statusCode);


        //TODO 평가 변수명, 타입, case전체 갈아엎기


        switch (statusCode) {
            //내역 없음 뷰 
//            case DDS_1001.STATUS_REQ:
//                return TYPE_NO_DATA;

            //신청완료
            case DDS_1001.STATUS_SERVICE_FINISHED:
                return TYPE_FINISHED;

            //접수 중
//            case DDS_1001.STATUS_CANCEL_CAUSE_NO_DRIVER:
//                return TYPE_WAITING;

            //이상한 값
            default:
                return TYPE_WAITING;
//                Log.d(TAG, "findItemType: unexpected value" + statusCode);
//                return TYPE_NO_DATA;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: viewType : " + viewType);

        switch (viewType) {
            case TYPE_FINISHED:
            case TYPE_WAITING:
                return new RelapseHistoryViewHolder(getView(parent, R.layout.item_relapse_history));

            case TYPE_NOTI:
                //todo 별도 뷰 홀더 또는 안에서 알아서 처리
                return new RelapseHistoryViewHolder(getView(parent, R.layout.item_relapse_history_noti));

            case TYPE_NO_DATA:
            default:
                //todo 별도 뷰 홀더 또는 안에서 알아서 처리
                return new RelapseHistoryViewHolder(getView(parent, R.layout.item_relapse_history_no_data));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.d(TAG, "serviceDriveHistoryAdapter onBindViewHolder position : " + position);

        switch (getItemViewType(position)) {
            case TYPE_FINISHED:
            case TYPE_WAITING:
                holder.onBindView(getItem(position), position);
                break;

            case TYPE_NOTI:
            case TYPE_NO_DATA:
                //TODO impl
            default:
                //do nothing
                break;
        }
    }

    //하자 재발 뷰 홀더 TODO VO 객체 타입 맞추기 중간에 많이 쓰니까 일일이 찾아서 고치기
    public static class RelapseHistoryViewHolder extends BaseViewHolder<DriveServiceVO, ItemRelapseHistoryBinding> {
        //        private View root;
        public boolean finished;
        public String vin;
        public String dateStr;

        public RelapseHistoryViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "RelapseHistoryViewHolder: ");
            //root = itemView;
        }

        @Override
        public void onBindView(DriveServiceVO item) {
            //do nothing
        }

        @Override
        public void onBindView(DriveServiceVO item, int pos) {
            Log.d(TAG, "Holder. onBindView: ");

            finished = item.getSvcStusCd() == DDS_1001.STATUS_SERVICE_FINISHED;//todo 값 제대로 넣기

            //신청 일시 todo 데이터 제대로 넣기
            Date date = DateUtil.getDefaultDateFormat(item.getRgstDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmm);
            dateStr = DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_mm_dd_dot);

            vin = "vin 찾아서 넣기";
            getBinding().setHolder(this);

            //todo 확인 사항 : 배경은 클릭 이펙트 붙은 놈 꽂았고
            // clickable, focusable은 holder.finished에 종속시켜놨는데 이것만으로 클릭 이펙트 관련 처리 끝인지 검사
            // 안 되면 코드에서 배경 스위칭이나 clickable, focusable 필요?         addressBtn.setBackground(getContext().getDrawable(R.drawable.ripple_bg_ffffff_stroke_e2e2e2));
            // 되면 root 삭제
        }

        @Override
        public void onBindView(DriveServiceVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }
    }
}
