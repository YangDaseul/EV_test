package com.genesis.apps.ui.main.service;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.DDS_1001;
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

    @Override
    public int getItemViewType(int position) {
        DriveServiceVO item = getItem(position);

        return findItemType(item.getSvcStusCd());
    }

    public int findItemType(String statusCode) {
        Log.d(TAG, "findItemType: SvcStusCd : " + statusCode);

        //상태 코드는 DDS_1001과 같은 값을 공유
        switch (statusCode) {
            //이용완료
            case DDS_1001.STATUS_SERVICE_FINISHED:
                return TYPE_FINISHED;

            //취소
            case DDS_1001.STATUS_CANCEL_BY_USER:
            case DDS_1001.STATUS_CANCEL_CAUSE_NO_DRIVER:
                return TYPE_CANCELED;

            //현재 진행중
            case DDS_1001.STATUS_REQ:
            case DDS_1001.STATUS_RESERVED:
            case DDS_1001.STATUS_DRIVER_MATCH_WAIT:
            case DDS_1001.STATUS_DRIVER_MATCHED:
            case DDS_1001.STATUS_DRIVER_REMATCHED:
            case DDS_1001.STATUS_DRIVE_NOW:
            case DDS_1001.STATUS_NO_DRIVER:
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
        private View detailView;
        public int iconCloseBtn;
        public int iconOpenBtn;
        public String rsvDt;
        public String date;
        public String carInfo;
        public String paidPrice;
        public String prmtDcPrice;
        public String originalPrice;
        public String blueMemberPoint;//포인트로 차감한 금액
        public int discountVisibility;
        public int prmtDcPriceVisibility;
        public String rdwnDc;//로드윈 특별할인 TODO  삭제될 것 같음. 일단 미적용
        public PositionVO from;
        public PositionVO to;
        public String driverName;
        public String driverPhone;

        public ServiceDriveHistoryFinishedViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ServiceDriveHistoryFinishedViewHolder: ");

            // TODO 리사이클러 뷰 아이템에 접기/펴기 기능 붙는 경우 그대로 복붙(...)해서 쓰기 가능
            // 생성자에서 detailView(상태 변화대상) 저장해놓고
            // setOpenListener(), setViewStatus(), changeViewStatus() 이 셋이 세트
            // 일단 주석통째로 복붙 해두는데.... 이 정도로 다 겹치면 유틸로 빼거나 상위 클래스에 잘 올리거나 하고싶은데
            detailView = getBinding().lServiceDriveHistoryFinishedDetail.lServiceDriveHistoryDetailRoot;
            iconOpenBtn = R.drawable.btn_arrow_open;
            iconCloseBtn = R.drawable.btn_arrow_close;
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

            //세부사항 접기/펴기 리스너
            setOpenListener(pos, selectedItems);

            //뷰에 들어갈 데이터 추출
            getDataFromItem(item);

            //열림/닫힘 구분해서 세부사항 뷰 접기/열기 아이콘 및 visibility 처리
            setViewStatus(selectedItems.get(pos));

            //데이터 바인딩
            getBinding().setHolder(this);
        }

        //접기/펴기 리스너 붙이기
        private void setOpenListener(int pos, SparseBooleanArray selectedItems) {
            getBinding().lServiceDriveHistoryFinishedBtn.lServiceDriveHistoryFinishedItem.setOnClickListener(v -> {
                Log.d(TAG, "recyclerView onClick position : " + pos);

                //클릭하면 상태 변경을 저장하고
                if (selectedItems.get(pos)) {
                    selectedItems.delete(pos);  // 펼쳐진 Item 클릭하면 열림 목록에서 삭제
                } else {
                    selectedItems.put(pos, true);// 닫힌 거 클릭하면 열림 목록에 추가
                }

                //변경된 상태를 반영
                notifyItemChanged(pos);
            });
        }

        //아이템에서 데이터를 꺼내서 뷰에 출력할 값으로 가공하여 뷰홀더에 저장
        private void getDataFromItem(DriveServiceVO item) {
            //날짜 : 포맷에 맞게 변경
            Date date = DateUtil.getDefaultDateFormat(StringUtil.isValidString(item.getRgstDt()), DateUtil.DATE_FORMAT_yyyyMMddHHmmss, Locale.KOREA);
            this.date = DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm);

            Date rsvDt = DateUtil.getDefaultDateFormat(StringUtil.isValidString(item.getRsvDt()), (StringUtil.isValidString(item.getRsvDt()).length() > 12 ? DateUtil.DATE_FORMAT_yyyyMMddHHmmss : DateUtil.DATE_FORMAT_yyyyMMddHHmm), Locale.KOREA);
            this.rsvDt = DateUtil.getDate(rsvDt, DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm);

            //차량정보 : 차종 + 번호판
            carInfo = StringUtil.isValidString(item.getMdlNm()) + " " + StringUtil.isValidString(item.getCarRegNo());

            //가격 : 숫자를 가격으로
            paidPrice = StringUtil.getDigitGroupingString(StringUtil.isValidString(item.getPayPrice()));

            originalPrice = StringUtil.getPriceString(StringUtil.isValidString(item.getExpPrice()));

            //프로모션 할인 : 숫자를 할인가(음수 붙은 가격)로, 단, 할인 0원이면 뷰를 숨김
            prmtDcPrice = item.getPrmtDcPrice();
            if (TextUtils.isEmpty(prmtDcPrice)||prmtDcPrice.equals("0")) {
                prmtDcPriceVisibility = View.GONE;
            } else {
                prmtDcPriceVisibility = View.VISIBLE;
                prmtDcPrice = StringUtil.getDiscountString(prmtDcPrice);
            }


            //할인가 : 숫자를 할인가(음수 붙은 가격)로, 단, 할인 0원이면 뷰를 숨김
            blueMemberPoint = item.getBluMbrDcPrice();
            if (TextUtils.isEmpty(blueMemberPoint)||blueMemberPoint.equals("0")) {
                discountVisibility = View.GONE;
            } else {
                discountVisibility = View.VISIBLE;
                blueMemberPoint = StringUtil.getDiscountString(blueMemberPoint);
            }

            //출발지, 도착지
            from = item.getPosInfo().get(0);
            to = item.getPosInfo().get(1);

            // TODO : 서버 수정되면 반영
            driverName = item.getDriverNm();
            driverPhone = PhoneNumberUtils.formatNumber(item.getDriverMdn(), Locale.getDefault().getCountry());
        }

        //세부사항 뷰의 개폐 상태를 처리
        // (화면 밖에 있다가 스크롤되어서 화면 안에 들어오는 경우 호출됨)
        private void setViewStatus(boolean opened) {
            setIcon(opened ? iconCloseBtn : iconOpenBtn);
            detailView.setVisibility(opened ? View.VISIBLE : View.GONE);
        }

//        //세부사항 뷰의 개폐 상태가 변경되는 애니메이션을 처리
//        // (클릭해서 상태를 토글할 때 호출됨)
//        private void changeViewStatus(boolean opened) {
//            setIcon(opened ? iconCloseBtn : iconOpenBtn);
//            changeVisibility(
//                    detailView, //개폐 애니메이션 대상 뷰
//                    (View) detailView.getParent().getParent(), //애니 재생동안 스크롤 막아야되는 뷰
//                    opened); //개폐 상태
//        }

        //드롭다운 아이콘의 개폐 상태를 변경
        private void setIcon(int icon) {
            getBinding().lServiceDriveHistoryFinishedBtn.ivServiceDriveHistoryDropdown.setImageResource(icon);
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
            Date date = DateUtil.getDefaultDateFormat(item.getRgstDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss, Locale.KOREA);
            String dateStr = DateUtil.getDate(date, DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm);
            getBinding().setDate(dateStr);

            Date rsvDt = DateUtil.getDefaultDateFormat(StringUtil.isValidString(item.getRsvDt()), (StringUtil.isValidString(item.getRsvDt()).length() > 12 ? DateUtil.DATE_FORMAT_yyyyMMddHHmmss : DateUtil.DATE_FORMAT_yyyyMMddHHmm), Locale.KOREA);
            String rsvDtStr = DateUtil.getDate(rsvDt, DateUtil.DATE_FORMAT_yyyy_MM_dd_e_hh_mm);
            getBinding().setRsvDt(rsvDtStr);

            //차량 정보
            String carInfo = StringUtil.isValidString(item.getMdlNm()) + " " + StringUtil.isValidString(item.getCarRegNo());
            getBinding().setCarInfo(carInfo);
        }

        @Override
        public void onBindView(DriveServiceVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }
    }
}
