package com.genesis.apps.ui.main.service;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RemoteHistoryVO;
import com.genesis.apps.comm.util.DateUtil;

import java.util.ArrayList;
import java.util.stream.Stream;

import lombok.Data;

/**
 * Created by Ki-man Kim on 12/22/20
 */
public class ServiceRemoteListAdapter extends RecyclerView.Adapter<ServiceRemoteListAdapter.ViewHolder> {

    enum STATUS {
        /**
         * 상태 : 신청.
         */
        CODE_R("R", R.string.sm_remote01_status_0, R.color.x_996449),
        /**
         * 상태 : 대기.
         */
        CODE_W("W", R.string.sm_remote01_status_1, R.color.x_996449),
        /**
         * 상태 : 확정.
         */
        CODE_D("D", R.string.sm_remote01_status_2, R.color.x_996449),
        /**
         * 상태 : 완료.
         */
        CODE_E("E", R.string.sm_remote01_status_3, R.color.x_996449),
        /**
         * 상태 : 통신 상태 불량.
         */
        CODE_F("F", R.string.sm_remote01_status_4, R.color.x_ce2d2d),
        /**
         * 상태 : 취소.
         */
        CODE_C("C", R.string.sm_remote01_status_5, R.color.x_ce2d2d);

        private final String code;
        @StringRes
        private final int descResId;
        @ColorRes
        private final int colorResId;

        STATUS(String code, int descResId, int colorResId) {
            this.code = code;
            this.descResId = descResId;
            this.colorResId = colorResId;
        }
    } // end of enum STATUS

    enum FLT_CODE_CATEGORY {
        CODE_080102("080102", R.string.sm_remote01_fit_code_1000),
        CODE_080201("080201", R.string.sm_remote01_fit_code_2000),
        CODE_080202("080202", R.string.sm_remote01_fit_code_3000),
        CODE_080203("080203", R.string.sm_remote01_fit_code_3000),
        CODE_080204("080204", R.string.sm_remote01_fit_code_3000),
        CODE_080205("080205", R.string.sm_remote01_fit_code_3000),
        CODE_080206("080206", R.string.sm_remote01_fit_code_3000),
        CODE_080207("080207", R.string.sm_remote01_fit_code_3000),
        CODE_080208("080208", R.string.sm_remote01_fit_code_3000),
        CODE_080209("080209", R.string.sm_remote01_fit_code_4000),
        CODE_080210("080210", R.string.sm_remote01_fit_code_5000),
        CODE_080211("080211", R.string.sm_remote01_fit_code_6000),
        CODE_080212("080212", R.string.sm_remote01_fit_code_7000),
        CODE_080213("080213", R.string.sm_remote01_fit_code_8000);

        private String code;
        @StringRes
        private final int messageResId;

        FLT_CODE_CATEGORY(String code, int messageResId) {
            this.code = code;
            this.messageResId = messageResId;
        }
    } // end of enum FLT_CODE_CATEGORY

    private ArrayList<RemoteHistoryVO> datas;

    public ServiceRemoteListAdapter(ArrayList<RemoteHistoryVO> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_remote, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RemoteHistoryVO item = datas.get(position);
        Resources resources = holder.itemView.getResources();

        STATUS status = getStatusByCode(item.getAplyStusCd());
        FLT_CODE_CATEGORY fltCodeCategory = getFltCodeCategoryByCode(item.getFltCd());

        holder.tvDate.setText(
                DateUtil.getDate(
                        DateUtil.getDefaultDateFormat(item.getRsrvDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss),
                        DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm
                )
        );
        if (status != null) {
            holder.tvStatus.setText(status.descResId);
            holder.tvStatus.setTextColor(resources.getColor(status.colorResId));
        }
        switch (status) {
            // 예약 신청.
            case CODE_R:
                // 예약 대기.
            case CODE_W:
                // 예약 확정.
            case CODE_D: {
                holder.tvDetailBtn.setVisibility(View.GONE);
                holder.lDetailContainer.setVisibility(View.GONE);
                holder.tvCancelBtn.setVisibility(View.VISIBLE);
                break;
            }
            // 진단 완료.
            case CODE_E: {
                holder.tvDetailBtn.setVisibility(View.VISIBLE);
                holder.lDetailContainer.setVisibility(View.GONE);
                holder.tvCancelBtn.setVisibility(View.GONE);
                break;
            }
            // 예약 취소.
            case CODE_C:
                // 통신 상태 불량.
            case CODE_F: {
                holder.tvDetailBtn.setVisibility(View.GONE);
                holder.lDetailContainer.setVisibility(View.GONE);
                holder.tvCancelBtn.setVisibility(View.GONE);
                break;
            }
        }

        if (fltCodeCategory != null) {
            holder.tvDescription.setText(fltCodeCategory.messageResId);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    private STATUS getStatusByCode(String code) {
        STATUS status = null;
        try {
            status = Stream.of(STATUS.values()).filter(it -> it.code.equals(code)).findFirst().get();
        } catch (NullPointerException e) {
            // 관련 상태 코드를 못 찾음.
        }
        return status;
    }

    private FLT_CODE_CATEGORY getFltCodeCategoryByCode(String code) {
        FLT_CODE_CATEGORY category = null;
        try {
            category = Stream.of(FLT_CODE_CATEGORY.values()).filter(it -> it.code.equals(code)).findFirst().get();
        } catch (NullPointerException e) {
            // 관련 상태 코드를 못 찾음.
        }
        return category;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 원격 진단 신청 날짜 및 시간.
         */
        TextView tvDate;
        /**
         * 원격 진단 신청 상태.
         */
        TextView tvStatus;
        /**
         * 고장 내용.
         */
        TextView tvDescription;

        /**
         * 원격 진단 타이틀.
         */
        TextView tvDetailBtn;
        /**
         * 원격 진단 결과 컨테이너.
         */
        LinearLayout lDetailContainer;
        /**
         * 요청 내용.
         */
        TextView tvServiceReq;
        /**
         * 기본 점검 항목 목록.
         */
        LinearLayout lBasicServiceList;
        /**
         * 예약 취소 버튼
         */
        TextView tvCancelBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_service_remote_date);
            tvStatus = itemView.findViewById(R.id.tv_service_remote_status);
            tvDescription = itemView.findViewById(R.id.tv_service_remote_detail_desc);
            tvServiceReq = itemView.findViewById(R.id.tv_service_remote_detail_request);

            tvDetailBtn = itemView.findViewById(R.id.tv_service_remote_detail_btn);
            lDetailContainer = itemView.findViewById(R.id.l_service_remote_detail_container);
            lBasicServiceList = itemView.findViewById(R.id.l_service_remote_detail_basic_list);
            tvCancelBtn = itemView.findViewById(R.id.tv_service_remote_cancel_btn);
        }
    } // end of class ViewHolder
} // end of class ServiceRemoteListAdapter
