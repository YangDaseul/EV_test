package com.genesis.apps.ui.main.service;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.databinding.ItemRelapse3RepairHistoryBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class ServiceRelapse3Adapter extends BaseRecyclerViewAdapter2<ServiceRelapse3Adapter.RepairData> {
    private static final String TAG = ServiceRelapse3Adapter.class.getSimpleName();
    private static final int REPAIR_HISTORY_MAX_SIZE = 3;//레이아웃 레벨에서 입력창 3개 제공

    private List<RepairData> repairHistory;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    ServiceRelapse3Adapter() {
        repairHistory = new ArrayList<>();
        repairHistory.add(new RepairData());
        setRows(repairHistory);
    }

    @Override
    public void addRow(RepairData row) {
        if (getItemCount() < REPAIR_HISTORY_MAX_SIZE) {
            super.addRow(row);
        }
    }

    @Override
    public void addRows(List<RepairData> rows) {

        super.addRows(rows);
    }

    @Override
    public int getItemViewType(int position) {
        //다 똑같음
        return 0;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: viewType : " + viewType);
        return new ServiceRelapse3ViewHolder(getView(parent, R.layout.item_relapse_3_repair_history));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.d(TAG, "serviceDriveHistoryAdapter onBindViewHolder position : " + position);
        holder.onBindView(getItem(position), position, selectedItems);
    }

    //하자 재발 신청 3단계 입력창 뷰 홀더
    public class ServiceRelapse3ViewHolder extends BaseViewHolder<RepairData, ItemRelapse3RepairHistoryBinding> {
        private View detailView;
        public int iconCloseBtn;
        public int iconOpenBtn;

        public ServiceRelapse3ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ServiceRelapse3ViewHolder: ");

            // TODO 리사이클러 뷰 아이템에 접기/펴기 기능 붙는 경우 그대로 복붙(...)해서 쓰기 가능
            // 생성자에서 detailView(상태 변화대상) 저장해놓고
            // setOpenListener(), setViewStatus(), changeViewStatus() 이 셋이 세트
            // 일단 주석통째로 복붙 해두는데.... 이 정도로 다 겹치면 유틸로 빼거나 상위 클래스에 잘 올리거나 하고싶은데
            detailView = getBinding().lRelapse3RepairHistoryDetail;
            iconOpenBtn = R.drawable.btn_arrow_open;
            iconCloseBtn = R.drawable.btn_arrow_close;
        }

        @Override
        public void onBindView(RepairData item) {
            //do nothing
        }

        @Override
        public void onBindView(RepairData item, int pos) {
            //do nothing
        }

        @Override
        public void onBindView(RepairData item, int pos, SparseBooleanArray selectedItems) {
            Log.d(TAG, "onBindView(finished): ");

            //뷰에 들어갈 데이터 추출
            getDataFromItem(item);

            //세부사항 접기/펴기 리스너
            setOpenListener(pos, selectedItems);

            //열림/닫힘 구분해서 세부사항 뷰 접기/열기 아이콘 및 visibility 처리
            setViewStatus(selectedItems.get(pos));
        }

        //아이템에서 데이터를 꺼내서 뷰에 출력할 값으로 가공하여 뷰홀더에 저장
        private void getDataFromItem(RepairData item) {

            //todo 이거 뷰어가 아니라 입력 받는 곳인데....
            //1회 2회 3회는 여기서 써줘야됨 삭제버튼 유무도 처리하고

        }

        //접기/펴기 리스너 붙이기 :
        private void setOpenListener(int pos, SparseBooleanArray selectedItems) {
            getBinding().tvRelapse3RepairHistoryTitle.setOnClickListener(v -> {
                Log.d(TAG, "recyclerView onClick position : " + pos);

                //클릭하면 상태 변경을 저장하고
                if (selectedItems.get(pos)) {
                    selectedItems.delete(pos);  // 펼쳐진 Item 클릭하면 열림 목록에서 삭제
                } else {
                    selectedItems.put(pos, true);// 닫힌 거 클릭하면 열림 목록에 추가
                }

                //변경된 상태를 반영
                changeViewStatus(selectedItems.get(pos));
            });
        }

        //세부사항 뷰의 개폐 상태를 처리
        // (화면 밖에 있다가 스크롤되어서 화면 안에 들어오는 경우 호출됨)
        private void setViewStatus(boolean opened) {
            setIcon(opened ? iconCloseBtn : iconOpenBtn);
            detailView.setVisibility(opened ? View.VISIBLE : View.GONE);
        }

        //세부사항 뷰의 개폐 상태가 변경되는 애니메이션을 처리
        // (클릭해서 상태를 토글할 때 호출됨)
        private void changeViewStatus(boolean opened) {
            setIcon(opened ? iconCloseBtn : iconOpenBtn);
            changeVisibility(
                    detailView, //개폐 애니메이션 대상 뷰
                    (View) detailView.getParent().getParent(), //애니 재생동안 스크롤 막아야되는 뷰
                    opened); //개폐 상태
        }

        //드롭다운 아이콘의 개폐 상태를 변경
        private void setIcon(int icon) {
            //setDrawableRight()는 없고 네 방향을 한 번에 넣네 'ㅅ'..
            getBinding().tvRelapse3RepairHistoryTitle.setCompoundDrawablesRelative(
                    null,
                    null,
                    getContext().getDrawable(icon),
                    null);
        }
    }

    //입력 데이터
    public class RepairData extends BaseData {
        private String mechanic;
        private String reqDate;     //YYYYMMDD
        private String finishDate;  //YYYYMMDD
        private String defectDetail;
        private String repairDetail;

        public RepairData() {
            mechanic = "";
            reqDate = "";
            finishDate = "";
            defectDetail = "";
            repairDetail = "";
        }

        public String getMechanic() {
            return mechanic;
        }

        public void setMechanic(String mechanic) {
            this.mechanic = mechanic;
        }

        public String getReqDate() {
            return reqDate;
        }

        public void setReqDate(String reqDate) {
            this.reqDate = reqDate;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getDefectDetail() {
            return defectDetail;
        }

        public void setDefectDetail(String defectDetail) {
            this.defectDetail = defectDetail;
        }

        public String getRepairDetail() {
            return repairDetail;
        }

        public void setRepairDetail(String repairDetail) {
            this.repairDetail = repairDetail;
        }
    }
}
