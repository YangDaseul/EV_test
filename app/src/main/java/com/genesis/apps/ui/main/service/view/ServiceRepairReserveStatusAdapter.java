package com.genesis.apps.ui.main.service.view;

import android.animation.ValueAnimator;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.databinding.ItemServiceRepairHeaderBinding;
import com.genesis.apps.databinding.ItemServiceRepairReserveAutocareBinding;
import com.genesis.apps.databinding.ItemServiceRepairReserveEmptyBinding;
import com.genesis.apps.databinding.ItemServiceRepairReserveHometohomeBinding;
import com.genesis.apps.databinding.ItemServiceRepairReserveRepairBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ServiceRepairReserveStatusAdapter extends BaseRecyclerViewAdapter2<RepairReserveVO> {

    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private static OnSingleClickListener onSingleClickListener;

    private static final int SERVICE_STATUS_ITEM_HEADER = 0;
    private static final int SERVICE_STATUS_ITEM_AUTOCARE = 1;
    private static final int SERVICE_STATUS_ITEM_AIRPORT = 2;
    private static final int SERVICE_STATUS_ITEM_HOMETOHOME = 3;
    private static final int SERVICE_STATUS_ITEM_REPAIR = 4;
    private static final int SERVICE_STATUS_ITEM_EMPTY = 5;

    public ServiceRepairReserveStatusAdapter(OnSingleClickListener onSingleClickListener) {
        ServiceRepairReserveStatusAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (position == 0) {
                return SERVICE_STATUS_ITEM_HEADER;
            } else if (((RepairReserveVO) getItem(1)).getRsvtTypCd().equalsIgnoreCase("")) {
                return SERVICE_STATUS_ITEM_EMPTY;
            } else {
                switch (((RepairReserveVO) getItem(position)).getRsvtTypCd()) {
                    case VariableType.SERVICE_RESERVATION_TYPE_AUTOCARE:

                        return SERVICE_STATUS_ITEM_AUTOCARE;
                    case VariableType.SERVICE_RESERVATION_TYPE_HOMETOHOME:


                        return SERVICE_STATUS_ITEM_HOMETOHOME;
                    case VariableType.SERVICE_RESERVATION_TYPE_RPSH:
                    default:

                        return SERVICE_STATUS_ITEM_REPAIR;
                }
            }
        } catch (Exception e) {
            return SERVICE_STATUS_ITEM_HEADER;
        }
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SERVICE_STATUS_ITEM_HEADER:
                return new ItemServiceRepairHeader(getView(parent, R.layout.item_service_repair_header));
            case SERVICE_STATUS_ITEM_AUTOCARE:
                return new ItemServiceRepairReserveAutocare(getView(parent, R.layout.item_service_repair_reserve_autocare), this);
            case SERVICE_STATUS_ITEM_HOMETOHOME:
                return new ItemServiceRepairReserveHomeToHome(getView(parent, R.layout.item_service_repair_reserve_hometohome), this);
            case SERVICE_STATUS_ITEM_REPAIR:
                return new ItemServiceRepairReserveRepair(getView(parent, R.layout.item_service_repair_reserve_repair), this);
            case SERVICE_STATUS_ITEM_EMPTY:
            default:
                return new ItemServiceRepairReserveEmpty(getView(parent, R.layout.item_service_repair_reserve_empty));
        }
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position, selectedItems);
    }

    public void selectItems(int position){
        if (selectedItems.get(position)) {
            // 펼쳐진 Item을 클릭 시
            selectedItems.delete(position);
        } else {
            // 클릭한 Item의 position을 저장
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }


    public boolean isReserveCancel(String rsvtStusCd) {
        boolean isCancel = false;
        try {
            if (rsvtStusCd.equalsIgnoreCase("6800") //홈투홈 예약 취소
                    || rsvtStusCd.equalsIgnoreCase("7800")  //에어포트 예약 취소
                    || rsvtStusCd.equalsIgnoreCase("8800")  //오토케어 예약 취소
                    || rsvtStusCd.equalsIgnoreCase("9800")) { //정비소 예약 취소
                isCancel = true;
            }
        } catch (Exception e) {
            isCancel = false;
        }

        return isCancel;
    }


    private static class ItemServiceRepairReserveAutocare extends BaseViewHolder<RepairReserveVO, ItemServiceRepairReserveAutocareBinding> {

        private ServiceRepairReserveStatusAdapter serviceRepairReserveStatusAdapter;

        public ItemServiceRepairReserveAutocare(View itemView, ServiceRepairReserveStatusAdapter serviceRepairReserveStatusAdapter) {
            super(itemView);
            this.serviceRepairReserveStatusAdapter = serviceRepairReserveStatusAdapter;

            getBinding().btnCancel.setOnClickListener(onSingleClickListener);
            getBinding().tvRsvtStatus.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    getBinding().btnArrow.performClick();
                }
            });
            getBinding().btnArrow.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    int position = Integer.parseInt(v.getTag(R.id.position).toString());
                    serviceRepairReserveStatusAdapter.selectItems(position);
                }
            });
        }

        @Override
        public void onBindView(RepairReserveVO item) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos, SparseBooleanArray selectedItems) {
            getBinding().setAdapter(serviceRepairReserveStatusAdapter);
            getBinding().setData(item);
            getBinding().lDetail.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);
            getBinding().btnCancel.setTag(R.id.position, pos);
            getBinding().btnArrow.setTag(R.id.position, pos);
            getBinding().btnArrow.setImageResource(selectedItems.get(pos) ? R.drawable.btn_arrow_close : R.drawable.btn_arrow_open);
//            serviceRepairReserveStatusAdapter.changeVisibility(getBinding().lDetail, selectedItems.get(pos), pos);
        }
    }


    private static class ItemServiceRepairReserveHomeToHome extends BaseViewHolder<RepairReserveVO, ItemServiceRepairReserveHometohomeBinding> {

        private ServiceRepairReserveStatusAdapter serviceRepairReserveStatusAdapter;

        public ItemServiceRepairReserveHomeToHome(View itemView, ServiceRepairReserveStatusAdapter serviceRepairReserveStatusAdapter) {
            super(itemView);
            this.serviceRepairReserveStatusAdapter = serviceRepairReserveStatusAdapter;

            getBinding().btnCancel.setOnClickListener(onSingleClickListener);
            getBinding().tvRsvtStatus.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    getBinding().btnArrow.performClick();
                }
            });
            getBinding().btnArrow.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    int position = Integer.parseInt(v.getTag(R.id.position).toString());
                    serviceRepairReserveStatusAdapter.selectItems(position);
                }
            });
        }

        @Override
        public void onBindView(RepairReserveVO item) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos, SparseBooleanArray selectedItems) {
            getBinding().setAdapter(serviceRepairReserveStatusAdapter);
            getBinding().setListener(onSingleClickListener);
            getBinding().setData(item);
            getBinding().lDetail.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);
            getBinding().btnCancel.setTag(R.id.position, pos);
            getBinding().btnArrow.setTag(R.id.position, pos);

            getBinding().btnDlvryExtapChk.setTag(R.id.item, item);
            getBinding().btnPckpExtapChk.setTag(R.id.item, item);

            getBinding().btnArrow.setImageResource(selectedItems.get(pos) ? R.drawable.btn_arrow_close : R.drawable.btn_arrow_open);
//            serviceRepairReserveStatusAdapter.changeVisibility(getBinding().lDetail, selectedItems.get(pos), pos);
        }
    }


    private static class ItemServiceRepairReserveRepair extends BaseViewHolder<RepairReserveVO, ItemServiceRepairReserveRepairBinding> {

        private ServiceRepairReserveStatusAdapter serviceRepairReserveStatusAdapter;

        public ItemServiceRepairReserveRepair(View itemView, ServiceRepairReserveStatusAdapter serviceRepairReserveStatusAdapter) {
            super(itemView);
            this.serviceRepairReserveStatusAdapter = serviceRepairReserveStatusAdapter;

            getBinding().btnCancel.setOnClickListener(onSingleClickListener);
            getBinding().tvRsvtStatus.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    getBinding().btnArrow.performClick();
                }
            });
            getBinding().btnArrow.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    int position = Integer.parseInt(v.getTag(R.id.position).toString());
                    serviceRepairReserveStatusAdapter.selectItems(position);
                }
            });
        }

        @Override
        public void onBindView(RepairReserveVO item) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos, SparseBooleanArray selectedItems) {
            getBinding().setAdapter(serviceRepairReserveStatusAdapter);
            getBinding().setData(item);
            getBinding().lDetail.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);
            getBinding().btnCancel.setTag(R.id.position, pos);
            getBinding().btnArrow.setTag(R.id.position, pos);
            getBinding().btnArrow.setImageResource(selectedItems.get(pos) ? R.drawable.btn_arrow_close : R.drawable.btn_arrow_open);
//            serviceRepairReserveStatusAdapter.changeVisibility(getBinding().lDetail, selectedItems.get(pos), pos);
        }
    }


    private static class ItemServiceRepairReserveEmpty extends BaseViewHolder<RepairReserveVO, ItemServiceRepairReserveEmptyBinding> {
        public ItemServiceRepairReserveEmpty(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(RepairReserveVO item) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }


    private static class ItemServiceRepairHeader extends BaseViewHolder<RepairReserveVO, ItemServiceRepairHeaderBinding> {
        public ItemServiceRepairHeader(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(RepairReserveVO item) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos) {

        }

        @Override
        public void onBindView(RepairReserveVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }

    public void changeVisibility(ConstraintLayout layout, final boolean isExpanded, final int position) {

        if (isExpanded) {
            layout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layout.requestLayout();
        }

        if (layout.getVisibility() == View.VISIBLE && isExpanded)
            return;

        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, layout.getHeight()) : ValueAnimator.ofInt(layout.getHeight(), 0);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // imageView의 높이 변경
                layout.getLayoutParams().height = (int) animation.getAnimatedValue();
                layout.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                layout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        // Animation start
        va.start();
    }

}