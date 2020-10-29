package com.genesis.apps.ui.main.service;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.api.WSH_1004;
import com.genesis.apps.comm.model.vo.WashReserveVO;
import com.genesis.apps.databinding.ItemCarWashHistoryBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import static com.genesis.apps.comm.model.gra.api.WSH_1004.PAY_CASH;

public class CarWashHistoryAdapter extends BaseRecyclerViewAdapter2<WashReserveVO> {
    private static OnSingleClickListener singleClickListener;

    public CarWashHistoryAdapter(OnSingleClickListener listener) {
        singleClickListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarWashHistoryViewHolder(getView(parent, R.layout.item_car_wash_history));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    //세차 예약 내역 뷰홀더
    public static class CarWashHistoryViewHolder extends BaseViewHolder<WashReserveVO, ItemCarWashHistoryBinding> {
        //지점 전화번호, 지점 코드, 예약번호
        private String phoneNumber;
        private String branchCode;
        private String reservationNumber;


        public CarWashHistoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(WashReserveVO item) {
            //do nothing
        }

        @Override
        public void onBindView(WashReserveVO item, int pos) {
            //예약 중인지 이용완료 또는 취소인지에 따라 뷰 모양 다름
            setViewType(item.getRsvtStusCd());

            //지점명
            setBranchName(item.getBrnhNm());

            //상품명
            getBinding().tvCarWashHistoryName.setText(item.getGodsNm());
            //결제금액
            getBinding().tvCarWashHistoryPrice.setText(item.getPaymtCost());

            //결제수단
            setPayType(item.getPaymtWayCd());

            //버튼 클릭 리스너 및 해당 버튼 처리에 필요한 데이터 세팅
            setSingleClickListenerAndData(item);

            Glide.with(getContext())
                    .load(item.getGodsImgUri())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .error(R.drawable.img_car_339_2) //todo 대체 이미지 필요
                    .placeholder(R.drawable.img_car_339_2) //todo 에러시 대체 이미지 필요
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(getBinding().ivCarWashHistoryImg);
        }

        @Override
        public void onBindView(WashReserveVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }

        //예약 중인지 이용완료 또는 취소인지에 따라 뷰 모양 다름
        //공통 부분 비중이 커서 viewType을 아예 나누지는 않고 visibility로 처리함
        private void setViewType(String status) {
            switch (status) {
                case WSH_1004.RESERVE_VALID:
                    getBinding().lCarWashHistoryBottom.setVisibility(View.VISIBLE);
                    getBinding().tvCarWashHistoryBranchNameEnd.setVisibility(View.GONE);
                    break;

                case WSH_1004.RESERVE_CANCELED:
                case WSH_1004.RESERVE_COMPLETED:
                    getBinding().lCarWashHistoryBottom.setVisibility(View.GONE);
                    getBinding().tvCarWashHistoryBranchNameEnd.setVisibility(View.VISIBLE);
                    break;
            }
        }

        //지점명. 예약중/이용완료또는 취소 두 뷰에 일괄 처리
        private void setBranchName(String name) {
            getBinding().tvCarWashHistoryBranchNameReserved.setText(name);
            getBinding().tvCarWashHistoryBranchNameEnd.setText(name);
        }

        //결제 수단 코드에 따라 내용 표시
        //todo : api에 결제 수단 정보 갱신되면 반영
        private void setPayType(String type) {
            int payTypeId;

            switch (type) {
                case PAY_CASH:
                    payTypeId = R.string.sm_cw_pay_type_01;
                    break;

                default:
                    //결제수단 정보 누락된 상태
                    payTypeId = R.string.sm_cw_pay_type_xx;
                    break;
            }

            getBinding().tvCarWashHistoryPaytype.setText(payTypeId);
        }

        //리스너를 연결하고, 이를 처리하는데 필요한 데이터도 저장
        private void setSingleClickListenerAndData(WashReserveVO item) {
            //지점 전화번호, 지점 코드, 예약번호
            phoneNumber = item.getTelNo();
            branchCode = item.getBrnhCd();
            reservationNumber = item.getRsvtSeqNo();

            //todo 직원에게 확인 버튼 : [지점코드 입력 팝업] 이건 뭐 하는거지?

            //통화하기
            getBinding().tvCarWashHistoryCall.setOnClickListener(singleClickListener);
            //직원에게확인
            getBinding().tvCarWashHistoryConfirm.setOnClickListener(singleClickListener);
            //예약취소
            getBinding().tvCarWashHistoryCancel.setOnClickListener(singleClickListener);
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public String getReservationNumber() {
            return reservationNumber;
        }
    }

}
