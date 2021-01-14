package com.genesis.apps.ui.main.service;

import android.content.res.Resources;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RemoteCheckVO;
import com.genesis.apps.comm.model.vo.RemoteHistoryVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemServiceRemoteBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.List;
import java.util.stream.Stream;

/**
 * Class Name : ServiceRemoteListAdapter
 * 원격 진단 신청 목록 표시를 위한 RecyclerView Adapter Class.
 * <p>
 * Created by Ki-man Kim on 12/22/20
 */
public class ServiceRemoteListAdapter extends BaseRecyclerViewAdapter2<RemoteHistoryVO> {

    /**
     * 원격 진단 신청 상태 enum class.
     */
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

    /**
     * 고장 Category code.
     */
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

        /**
         * 고장 코드 Category
         */
        private String code;
        /**
         * 고장 코드별 문구 String Resource ID
         */
        @StringRes
        private final int messageResId;

        FLT_CODE_CATEGORY(String code, int messageResId) {
            this.code = code;
            this.messageResId = messageResId;
        }
    } // end of enum FLT_CODE_CATEGORY


    private OnSingleClickListener onSingleClickListener;

    /**
     * 하위 뷰의 열고 닫힘 플래그 처리를 위한 저장 변수.
     */
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public ServiceRemoteListAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getView(parent, R.layout.item_service_remote), this);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position, selectedItems);
    }

    /****************************************************************************************************
     * Public Method
     ****************************************************************************************************/
    /**
     * 아이템 선택 함수.
     * 임시 저장 변수에 플래그 값을 전환 처리.
     *
     * @param position 선택한 아이템의 포지션.
     */
    public void selectItems(int position) {
        if (selectedItems.get(position)) {
            // 펼쳐진 Item을 클릭 시
            selectedItems.delete(position);
        } else {
            // 클릭한 Item의 position을 저장
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    /****************************************************************************************************
     * Inner Class
     ****************************************************************************************************/
    /**
     * 원격 진단 신청 목록의 View Holder Class.
     */
    private static class ViewHolder extends BaseViewHolder<RemoteHistoryVO, ItemServiceRemoteBinding> {
        private LayoutInflater layoutInflater;
        private Resources resources;

        public ViewHolder(View itemView, ServiceRemoteListAdapter adapter) {
            super(itemView);
            layoutInflater = LayoutInflater.from(itemView.getContext());
            resources = itemView.getResources();

            getBinding().tvServiceRemoteDetailBtn.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    Object tag = v.getTag();
                    if (tag instanceof Integer) {
                        adapter.selectItems((int) tag);
                    }
                }
            });
            getBinding().tvServiceRemoteCancelBtn.setOnClickListener((view) -> {
                adapter.onSingleClickListener.onClick(view);
            });
        }

        /****************************************************************************************************
         * Override Method
         ****************************************************************************************************/
        @Override
        public void onBindView(RemoteHistoryVO item) {
        }

        @Override
        public void onBindView(RemoteHistoryVO item, int pos) {
        }

        @Override
        public void onBindView(RemoteHistoryVO item, int pos, SparseBooleanArray selectedItems) {
            STATUS status = getStatusByCode(item.getAplyStusCd());
            FLT_CODE_CATEGORY fltCodeCategory = getFltCodeCategoryByCode(item.getFltCd());
            ItemServiceRemoteBinding binding = getBinding();

            binding.tvServiceRemoteDate.setText(
                    DateUtil.getDate(
                            DateUtil.getDefaultDateFormat(item.getRsrvDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss),
                            DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm
                    )
            );
            if (status != null) {
                binding.tvServiceRemoteStatus.setText(status.descResId);
                binding.tvServiceRemoteStatus.setTextColor(resources.getColor(status.colorResId));
            }
            switch (status) {
                // 예약 신청.
                case CODE_R:
                    // 예약 대기.
                case CODE_W:
                    // 예약 확정.
                case CODE_D: {
                    binding.tvServiceRemoteDetailBtn.setVisibility(View.GONE);

                    binding.lServiceRemoteDetailContainer.setVisibility(View.GONE);
                    binding.tvServiceRemoteCancelBtn.setVisibility(View.VISIBLE);
                    binding.tvServiceRemoteCancelBtn.setTag(item);
                    break;
                }
                // 진단 완료.
                case CODE_E: {
                    boolean isSelected = selectedItems.get(pos);
                    binding.tvServiceRemoteDetailBtn.setVisibility(View.VISIBLE);
                    binding.tvServiceRemoteDetailBtn.setTag(pos);
                    binding.tvServiceRemoteDetailBtn.setSelected(isSelected);

                    binding.tvServiceRemoteCancelBtn.setVisibility(View.GONE);

                    binding.lServiceRemoteDetailContainer.setVisibility(isSelected ? View.VISIBLE : View.GONE);

                    binding.tvServiceRemoteDetailRequest.setText(item.getChckCmnt());
                    List<RemoteCheckVO> chckItemList = item.getChckItemList();
                    if (chckItemList != null) {
                        binding.lServiceRemoteDetailBasicList.removeAllViews();
                        int index = 0;
                        for (RemoteCheckVO vo : chckItemList) {
                            View itemView = layoutInflater.inflate(R.layout.item_service_remote_detailitem, binding.lServiceRemoteDetailBasicList, false);
                            itemView.findViewById(R.id.line0).setVisibility(index == 0 ? View.VISIBLE : View.GONE);
                            TextView tvTitle = itemView.findViewById(R.id.tv_title);
                            TextView tvDetail = itemView.findViewById(R.id.tv_detail);
                            tvTitle.setText(vo.getChckItemNm());
                            if ("Y".equals(vo.getChckItemRslt())) {
                                // 정상.
                                tvDetail.setText(R.string.sm_remote01_result_normal);
                                tvDetail.setSelected(false);
                            } else {
                                // 점검 필요.
                                tvDetail.setText(R.string.sm_remote01_result_error);
                                tvDetail.setSelected(true);
                            }

                            binding.lServiceRemoteDetailBasicList.addView(itemView);
                            index++;
                        }
                    }

                    break;
                }
                // 예약 취소.
                case CODE_C:
                    // 통신 상태 불량.
                case CODE_F: {
                    binding.tvServiceRemoteDetailBtn.setVisibility(View.GONE);
                    binding.lServiceRemoteDetailContainer.setVisibility(View.GONE);
                    binding.tvServiceRemoteCancelBtn.setVisibility(View.GONE);
                    break;
                }
            }

            if (fltCodeCategory != null) {
                binding.tvServiceRemoteDetailDesc.setText(fltCodeCategory.messageResId);
            }
        }

        /****************************************************************************************************
         * Private Method
         ****************************************************************************************************/
        /**
         * 원격 진단 상태 값을 코드로 조회하여 반환 하는 함수.
         *
         * @param code 원격 진단 상태 코드.
         * @return 원격 진단 상태 {@link STATUS}
         */
        private STATUS getStatusByCode(String code) {
            STATUS status = null;
            try {
                status = Stream.of(STATUS.values()).filter(it -> it.code.equals(code)).findFirst().orElse(null);
            } catch (NullPointerException e) {
                // 관련 상태 코드를 못 찾음.
            }
            return status;
        }

        /**
         * 고장 카테고리 코드로 메시지 및 상태를 위한 Enum Class로 반환 하는 함수.
         *
         * @param code 고장 카테고리 코드.
         * @return 매핑된 {@link FLT_CODE_CATEGORY}
         */
        private FLT_CODE_CATEGORY getFltCodeCategoryByCode(String code) {
            FLT_CODE_CATEGORY category = null;
            try {
                category = Stream.of(FLT_CODE_CATEGORY.values()).filter(it -> it.code.equals(code)).findFirst().orElse(null);
            } catch (NullPointerException e) {
                // 관련 상태 코드를 못 찾음.
            }
            return category;
        }
    } // end of class ViewHolder
} // end of class ServiceRemoteListAdapter
