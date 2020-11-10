package com.genesis.apps.ui.main.service;

import android.graphics.drawable.Drawable;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.api.DDS_1001;
import com.genesis.apps.comm.model.vo.DriveServiceVO;
import com.genesis.apps.comm.model.vo.PositionVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemServiceDriveHistoryCanceledBinding;
import com.genesis.apps.databinding.ItemServiceDriveHistoryFinishedBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Date;
import java.util.Locale;


public class ServiceDriveHistoryAdapter extends BaseRecyclerViewAdapter2<DriveServiceVO> {
    private static final String TAG = ServiceDriveHistoryAdapter.class.getSimpleName();

    public static final int TYPE_NOT_HISTORY = -1;//데이터 넣기 전에 미리 검사해서 삭제 함. 그 때 비교를 위해 얘만 public 
    private static final int TYPE_FINISHED = 1;
    private static final int TYPE_CANCELED = 2;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int pageNo;


    public int getPageNo() {
        return pageNo;
    }

    public void incPageNo() {
        ++pageNo;
    }

    public ServiceDriveHistoryAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        DriveServiceVO item = getItem(position);

        return findItemType(item.getSvcStusCd());
    }

    public int findItemType(String statusCode) {
        Log.d(TAG, "findItemType: SvcStusCd : " + statusCode);

        //상태 코드는 DDS_1001과 같은 값을 공유
        switch (statusCode) {
            //현재 진행중
            case DDS_1001.STATUS_REQ:
            case DDS_1001.STATUS_RESERVED:
            case DDS_1001.STATUS_DRIVER_MATCH_WAIT:
            case DDS_1001.STATUS_DRIVER_MATCHED:
            case DDS_1001.STATUS_DRIVER_REMATCHED:
            case DDS_1001.STATUS_DRIVE_NOW:
            case DDS_1001.STATUS_NO_DRIVER:
                return TYPE_NOT_HISTORY;

            //이용완료
            case DDS_1001.STATUS_SERVICE_FINISHED:
                return TYPE_FINISHED;

            //취소
            case DDS_1001.STATUS_CANCEL_BY_USER:
            case DDS_1001.STATUS_CANCEL_CAUSE_NO_DRIVER:
                return TYPE_CANCELED;

            //이상한 값
            default:
                return TYPE_NOT_HISTORY;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: viewType : " + viewType);

        switch (viewType) {
            case TYPE_FINISHED:
                return new ServiceDriveHistoryFinishedViewHolder(getView(parent, R.layout.item_service_drive_history_finished));

            case TYPE_CANCELED:
            default:
                return new ServiceDriveHistoryCanceledViewHolder(getView(parent, R.layout.item_service_drive_history_canceled));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.d(TAG, "serviceDriveHistoryAdapter onBindViewHolder position : " + position);

        switch (getItemViewType(position)) {
            case TYPE_FINISHED:
                holder.onBindView(getItem(position), position, selectedItems);
                break;

            case TYPE_CANCELED:
                holder.onBindView(getItem(position), position);
                break;

            default:
                //do nothing
                break;
        }

    }

    //대리운전 완료됨 뷰 홀더
    public class ServiceDriveHistoryFinishedViewHolder extends BaseViewHolder<DriveServiceVO, ItemServiceDriveHistoryFinishedBinding> {
        public Drawable icon;
        public Drawable iconCloseBtn;
        public Drawable iconOpenBtn;
        public String date;
        public String carInfo;
        public String paidPrice;
        public String originalPrice;
        public String blueMemberPoint;//포인트로 차감한 금액
        public int discountVisibility;
        public String rdwnDc;//로드윈 특별할인 TODO  삭제될 것 같음. 일단 미적용
        public PositionVO from;
        public PositionVO to;
        public String driverName;
        public String driverPhone;

        public ServiceDriveHistoryFinishedViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ServiceDriveHistoryFinishedViewHolder: ");
            iconOpenBtn = itemView.getContext().getDrawable(R.drawable.btn_arrow_open);
            iconCloseBtn = itemView.getContext().getDrawable(R.drawable.btn_arrow_close);
        }

        @Override
        public void onBindView(DriveServiceVO item) {
            //do nothing
        }

        @Override
        public void onBindView(DriveServiceVO item, int pos) {
            //do nothing
        }

        @Override
        public void onBindView(DriveServiceVO item, int pos, SparseBooleanArray selectedItems) {
            Log.d(TAG, "onBindView(finished): ");

            //뷰에 들어갈 데이터 추출
            getDataFromItem(item);

            //열림/닫힘 구분해서 세부사항 뷰 visibility처리
            setViewStatus(selectedItems.get(pos));

            //세부사항 접기/펴기 리스너
            setOpenListener(pos, selectedItems);

            //데이터 바인딩
            getBinding().setHolder(this);
        }

        private void getDataFromItem(DriveServiceVO item) {
            //날짜 : 포맷에 맞게 변경
            Date date = DateUtil.getDefaultDateFormat(item.getRgstDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmm, Locale.KOREA);
            this.date = DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm);

            //차종 + 번호판
            carInfo = item.getMdlNm() + " " + item.getCarRegNo();

            //가격 : 숫자를 가격으로
            paidPrice = StringUtil.getPriceString(item.getPayPrice());
            originalPrice = StringUtil.getPriceString(item.getExpPrice());

            //할인가 : 숫자를 할인가(음수 붙은 가격)로, 단, 할인 0원이면 뷰를 숨김
            blueMemberPoint = item.getBluMbrDcPrice();
            if (blueMemberPoint.equals("0")) {
                discountVisibility = View.GONE;
            } else {
                discountVisibility = View.VISIBLE;
                blueMemberPoint = StringUtil.getDiscountString(blueMemberPoint);
            }

            //출발지, 도착지
            from = item.getPosInfo().get(0);
            to = item.getPosInfo().get(1);

            // TODO : 서버 수정되면 반영
            driverName = "운전맨";
            driverPhone = PhoneNumberUtils.formatNumber("01020104214", Locale.getDefault().getCountry());

        }

        private void setViewStatus(boolean opened) {
            icon = opened ? iconCloseBtn : iconOpenBtn;
            changeVisibility(getBinding().lServiceDriveHistoryFinishedDetail.lServiceDriveHistoryDetailRoot, opened);
        }

        private void setOpenListener(int pos, SparseBooleanArray selectedItems) {
            getBinding().lServiceDriveHistoryFinishedBtn.lServiceDriveHistoryFinishedItem.setOnClickListener(v -> {
                Log.d(TAG, "recyclerView onClick position : " + pos);

                if (selectedItems.get(pos)) {
                    selectedItems.delete(pos);  // 펼쳐진 Item 클릭하면 열림 목록에서 삭제
                } else {
                    selectedItems.put(pos, true);// 닫힌 거 클릭하면 열림 목록에 추가
                }

                getBindingAdapter().notifyItemChanged(pos);
            });
        }
    }

    //대리운전 취소됨 뷰 홀더
    public static class ServiceDriveHistoryCanceledViewHolder extends BaseViewHolder<DriveServiceVO, ItemServiceDriveHistoryCanceledBinding> {
        public ServiceDriveHistoryCanceledViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ServiceDriveHistoryCanceledViewHolder: ");
        }

        @Override
        public void onBindView(DriveServiceVO item) {
            //do nothing
        }

        @Override
        public void onBindView(DriveServiceVO item, int pos) {
            Log.d(TAG, "onBindView(canceled): ");
            //신청 일시
            Date date = DateUtil.getDefaultDateFormat(item.getRgstDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmm, Locale.KOREA);
            String dateStr = DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm);
            getBinding().setDate(dateStr);

            //차량 정보
            String carInfo = item.getMdlNm() + " " + item.getCarRegNo();
            getBinding().setCarInfo(carInfo);
        }

        @Override
        public void onBindView(DriveServiceVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }
    }
}
