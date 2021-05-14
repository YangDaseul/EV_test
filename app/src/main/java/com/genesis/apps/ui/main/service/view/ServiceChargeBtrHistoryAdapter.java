package com.genesis.apps.ui.main.service.view;

import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.CHB_1026;
import com.genesis.apps.comm.model.constants.ChargeBtrStatus;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.carlife.BookingVO;
import com.genesis.apps.comm.model.vo.carlife.MembershipVO;
import com.genesis.apps.comm.model.vo.carlife.OptionVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemServiceChargeBtrHistoryBinding;
import com.genesis.apps.databinding.ItemServiceChargeBtrHistoryBottomBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.List;


public class ServiceChargeBtrHistoryAdapter extends BaseRecyclerViewAdapter2<BookingVO> {

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private OnSingleClickListener onSingleClickListener;

    private static final int ITEM_BOTTOM = 0;
    private static final int ITEM_BODY = 1;

    private int pageNo = 0;

    public ServiceChargeBtrHistoryAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_BODY)
            return new ItemServiceChargeBtrHistory(getView(parent, R.layout.item_service_charge_btr_history), onSingleClickListener);
        else
            return new ItemServiceChargeBtrHistoryBottom(getView(parent, R.layout.item_service_charge_btr_history_bottom));

    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position, selectedItems);
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (position == (getItemCount() - 1)) {
                return ITEM_BOTTOM;
            } else {
                return ITEM_BODY;
            }
        } catch (Exception e) {
            return ITEM_BODY;
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void eventAccordion(int position){
        Log.v("recyclerview onclick", "position pos:" + position);
        if (selectedItems.get(position)) {
            // 펼쳐진 Item을 클릭 시
            selectedItems.delete(position);
        } else {
            // 클릭한 Item의 position을 저장
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public static class ItemServiceChargeBtrHistory extends BaseViewHolder<BookingVO, ItemServiceChargeBtrHistoryBinding> {
        private OnSingleClickListener onSingleClickListener;

        public ItemServiceChargeBtrHistory(View itemView, OnSingleClickListener onSingleClickListener) {
            super(itemView);
            this.onSingleClickListener = onSingleClickListener;
        }

        @Override
        public void onBindView(BookingVO item) {

        }

        @Override
        public void onBindView(BookingVO item, int pos) {

        }

        @Override
        public void onBindView(BookingVO item, int pos, SparseBooleanArray selectedItems) {
            getBinding().setData(item);
            getBinding().setPos(pos);
            getBinding().setListener(onSingleClickListener);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getBinding().lOrderNo.getLayoutParams();
            if(pos == 0)
                params.topMargin = 0;
            else
                params.topMargin = (int) DeviceUtil.dip2Pixel(getContext(), 30f);
            getBinding().lOrderNo.setLayoutParams(params);

            // 예약 번호 표시
            getBinding().lOrderNo.setText(String.format("예약번호 : %s", StringUtil.isValidString(item.getOrderId())));

            // 예약 상품 표시 (충전 or 충전, 세차)
            if(item.getOptionNameList() != null) {
                String serviceNm = getContext().getString(R.string.service_charge_btr_word_05);
                for(String optNm : item.getOptionNameList()) {
                    serviceNm += ", " + optNm;
                }
                getBinding().lOptionInfo.setMsg(serviceNm);
            }
            getBinding().btnChargeBtrImage.btnNm.setTag(item.getServiceViewLink());

            getBinding().btnDetail.setTag(R.id.item, item.getOrderId());
            getBinding().btnDetail.setTag(R.id.position, pos);
            getBinding().btnDetail.setTag(R.id.dtl_opened, selectedItems.get(pos) ? true : false);
            getBinding().ivArrowImage.setTag(R.id.item, item.getOrderId());
            getBinding().ivArrowImage.setTag(R.id.position, pos);
            getBinding().ivArrowImage.setTag(R.id.dtl_opened, selectedItems.get(pos) ? true : false);

            getBinding().ivArrowImage.setImageResource(selectedItems.get(pos) ? R.drawable.btn_arrow_close : R.drawable.btn_arrow_open);
            getBinding().lDetail.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);

            if(selectedItems.get(pos)){
                if(item.getWorkerVO() != null) {
                    // 담당 기사 표시
                    getBinding().lDriverNm.setMsg(String.format(getContext().getString(R.string.service_charge_btr_word_09), item.getWorkerVO().getWorkerName()));
                    // 고객센터 번호 연결
                    getBinding().btnChargeBtrTel.btnNm.setTag(item.getWorkerVO().getWorkerHpNo());
                }

                if(item.getOrderVO() != null) {
                    // 충전 금액 표시
                    getBinding().lAdvancePaymt.setMsg(StringUtil.getPriceString(item.getOrderVO().getProductPrice()));

                    // 충전 크레딧 포인트 정보 표시
                    String useCreditPoint = null;
                    if(item.getOrderVO().getMembershipList() != null && item.getOrderVO().getMembershipList().size() > 0) {
                        for(MembershipVO vo : item.getOrderVO().getMembershipList()) {
                            if(!vo.getMembershipCode().equalsIgnoreCase(VariableType.SERVICE_CHARGE_BTR_MEMBERSHIP_CODE_STRFF)) {
                                useCreditPoint = StringUtil.getDiscountString(vo.getMembershipUsePoint());
                                break;
                            }
                        }
                    }

                    if(TextUtils.isEmpty(useCreditPoint)) {
                        getBinding().lCreditPoint.lWhole.setVisibility(View.GONE);
                        getBinding().txtCreditPoint.setVisibility(View.GONE);
                    } else {
                        getBinding().lCreditPoint.lWhole.setVisibility(View.VISIBLE);
                        getBinding().txtCreditPoint.setVisibility(View.VISIBLE);
                        getBinding().lCreditPoint.setMsg(useCreditPoint);
                    }

                    OptionVO deliverVO = null;
                    OptionVO carwashVO = null;
                    for (OptionVO optVo : item.getOrderVO().getOptionList()) {
                        if (optVo.getOptionCode().equalsIgnoreCase(VariableType.SERVICE_CHARGE_BTR_OPT_CD_1)) {
                            deliverVO = optVo;
                            continue;
                        } else {
                            carwashVO = optVo;
                            continue;
                        }
                    }

                    // 탁송 금액 표시
                    if (deliverVO != null) {
                        getBinding().lDeliveryPaymt.setMsg(StringUtil.getPriceString(deliverVO.getOptionPrice()));
                    }

                    // 세차 금액 표시
                    if(carwashVO == null) {
                        // 선택 옵션인 세차는 숨김 처리(초기화)
                        getBinding().lCarWashPaymt.lWhole.setVisibility(View.GONE);
                    } else {
                        getBinding().lCarWashPaymt.lWhole.setVisibility(View.VISIBLE);
                        getBinding().lCarWashPaymt.setMsg(StringUtil.getPriceString(carwashVO.getOptionPrice()));
                    }

                    // 결제 금액 표시
                    getBinding().lPaymtAmt.setMsg(item.getOrderVO().getPaymentAmount() > 0 ? StringUtil.getPriceString(item.getOrderVO().getPaymentAmount()) : getContext().getString(R.string.service_charge_btr_word_42));
                    if(item.getStatus().equalsIgnoreCase(ChargeBtrStatus.STATUS_6000.getStusCd()))
                        getBinding().lPaymtAmt.tvMsg.setPaintFlags(getBinding().lPaymtAmt.tvMsg.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    else
                        getBinding().lPaymtAmt.tvMsg.setPaintFlags(0);

                }
            }
        }

    }

    private static class ItemServiceChargeBtrHistoryBottom extends BaseViewHolder<BookingVO, ItemServiceChargeBtrHistoryBottomBinding> {
        public ItemServiceChargeBtrHistoryBottom(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(BookingVO item) {

        }

        @Override
        public void onBindView(BookingVO item, int pos) {

        }

        @Override
        public void onBindView(BookingVO item, int pos, SparseBooleanArray selectedItems) {
        }
    }
}