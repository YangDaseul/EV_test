package com.genesis.apps.ui.main.home.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.api.developers.Dtc;
import com.genesis.apps.comm.model.api.developers.Replacements;
import com.genesis.apps.comm.model.vo.DataMilesVO;
import com.genesis.apps.comm.model.vo.developers.DtcVO;
import com.genesis.apps.comm.model.vo.developers.SestVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.databinding.ItemDatamilesBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import java.util.Date;

/**
 * Class Name : Home2DataMilesAdapter
 *
 * @author Ki-man Kim
 * @since 2020-11-30
 */
public class Home2DataMilesAdapter extends BaseRecyclerViewAdapter2<DataMilesVO> {
    private static OnSingleClickListener onSingleClickListener;

    public Home2DataMilesAdapter(OnSingleClickListener listener) {
        onSingleClickListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemDataMilesAdapter(getView(parent, R.layout.item_datamiles));
    }

    public void setDetail(String carId, Detail.Response detail) {
        DataMilesVO item = findVOByCarId(carId);
        if (item != null) {
            item.setDetailStatus(DataMilesVO.STATUS.SUCCESS);
            item.setDrivingScoreDetail(detail);
        }
    }

    public void setReplacements(String carId, Replacements.Response replacements) {
        DataMilesVO item = findVOByCarId(carId);

        if (item != null) {
            item.setReplacementsStatus(DataMilesVO.STATUS.SUCCESS);
            item.setReplacements(replacements);
        }
    }

    public void setDtc(String carId, Dtc.Response dtc) {
        DataMilesVO item = findVOByCarId(carId);

        if (item != null) {
            item.setDtc(dtc);
        }
    }

    public DataMilesVO findVOByCarId(String carId) {
        DataMilesVO result = null;
        try {
            result = getItems().stream().filter(vo -> vo.getCarId().equals(carId)).findFirst().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private class ItemDataMilesAdapter extends BaseViewHolder<DataMilesVO, ItemDatamilesBinding> {
        private final long ANI_DURATION = 1000;
        private AnimatorSet animatorSet = new AnimatorSet();

        public ItemDataMilesAdapter(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(DataMilesVO item) {
            Context context = getContext();
            ItemDatamilesBinding binding = getBinding();
            Detail.Response detail = item.getDrivingScoreDetail();
            Replacements.Response replacements = item.getReplacements();
            Dtc.Response dtc = item.getDtc();

            // 기존 애니메이션 처리는 멈춤.
            animatorSet.removeAllListeners();
            animatorSet.end();
            animatorSet.cancel();

            // 더보기 버튼 이벤트 등록.
            binding.tvDatamilesMore.setOnClickListener(view -> onSingleClickListener.onClick(view));

            /**
             * 안전 운전 점수
             */
            switch (item.getUbiStatus()) {
                case JOIN: {
                    // UBI 가입 상태.
                    binding.lDatamilesDrivingScoreContainer.setVisibility(View.VISIBLE);
                    switch (item.getDetailStatus()) {
                        case SUCCESS: {
                            binding.lDrivingScoreContainer.setVisibility(View.VISIBLE);
                            binding.lDatamilesGuideContainer.setVisibility(View.GONE);
                            bindDrivingScore(binding, detail);
                            break;
                        }
                        case FAIL: {
                            binding.lDrivingScoreContainer.setVisibility(View.GONE);
                            binding.lDatamilesGuideContainer.setVisibility(View.VISIBLE);
                            binding.tvDatamilesDrivingScoreError.setVisibility(View.VISIBLE);
                            binding.tvDatamilesDrivingScoreError.setOnClickListener(view -> onSingleClickListener.onClick(view));
                            binding.ivDatamilesServiceGuide.setVisibility(View.GONE);
                            break;
                        }
                    }
                    break;
                }
                case NOT_JOIN: {
                    // UBI 미가입, 가입 가능 상태. - 안내 가이드 표시.
                    binding.lDrivingScoreContainer.setVisibility(View.GONE);
                    binding.lDatamilesGuideContainer.setVisibility(View.VISIBLE);
                    binding.tvDatamilesDrivingScoreError.setVisibility(View.GONE);
                    binding.ivDatamilesServiceGuide.setVisibility(View.VISIBLE);
                    break;
                }
                case NOT_SUPPORTED:
                default: {
                    // UBI 미가입, 가입 불가 상태.
                    binding.lDatamilesDrivingScoreContainer.setVisibility(View.GONE);
                    binding.lDatamilesGuideContainer.setVisibility(View.GONE);
                    break;
                }
            }

            /**
             * 소모품 현황
             */
            switch (item.getReplacementsStatus()) {
                case SUCCESS: {
                    binding.tvDatamilesExpendablesTotalDistanceTitle.setVisibility(View.VISIBLE);
                    binding.tvDatamilesExpendablesTotalDistance.setVisibility(View.VISIBLE);
                    binding.tvDatamilesExpenablesError.setVisibility(View.GONE);
                    bindReplacements(context, binding, replacements);
                    break;
                }
                case FAIL: {
                    binding.tvDatamilesExpendablesTotalDistanceTitle.setVisibility(View.GONE);
                    binding.tvDatamilesExpendablesTotalDistance.setVisibility(View.GONE);
                    binding.tvDatamilesExpenablesError.setVisibility(View.VISIBLE);
                    binding.tvDatamilesExpenablesError.setOnClickListener(view -> onSingleClickListener.onClick(view));
                    break;
                }
            }

            /**
             * 차량 진단.
             */
            bindDtc(binding, dtc);

            // 애니메이션 시작!!
            animatorSet.start();
        }

        @Override
        public void onBindView(DataMilesVO item, int pos) {
        }

        @Override
        public void onBindView(DataMilesVO item, int pos, SparseBooleanArray selectedItems) {

        }

        /**
         * 안전 운전 점수 데이터 매핑 처리 함수.
         *
         * @param binding binding 된 View.
         * @param detail  안전 운전 점수 데이터.
         */
        private void bindDrivingScore(ItemDatamilesBinding binding, Detail.Response detail) {
            if (detail == null) {
                return;
            }

            // 안전운전 점수 그래프 터치 비활성화 처리.
            binding.asbDatamilesDrivingScore.setOnTouchListener((view, event) -> {
                return true;
            });

            try {
                binding.tvDatamilesDrivingScoreUpdateDate.setText(
                        DateUtil.getDate(
                                DateUtil.getDefaultDateFormat(detail.getScoreDate(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss),
                                DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm
                        ) + " " + getContext().getString(R.string.gm01_update)
                );
            } catch (Exception e) {
                // Date 파싱 에러.
                // TODO 필요에 따라 에러 예외 처리 필요.
            }
            // 주행 기준
            binding.tvDatamilesDrivingScoreGuide.setText(String.format(getContext().getString(R.string.gm01_format_driving_score_guide),
                    detail.getRangeDrvDist()));

            // Insight 메시지
            binding.tvDatamilesDrivingScoreDescription.setText(detail.getInsightMsg());

            // 운전 점수
            int prevScore = detail.getPrevSafetyDrvScore();
            int currentScore = detail.getSafetyDrvScore();
            ValueAnimator scoreAni = ValueAnimator.ofInt(currentScore)
                    .setDuration(ANI_DURATION);
            scoreAni.addUpdateListener(animation -> {
                int score = (int) animation.getAnimatedValue();
                binding.tvDatamilesDrivingScore.setText(String.valueOf(score));
                binding.asbDatamilesDrivingScore.setProgress(score);
            });

            // 전일 대비 안전 운전 점수 차이.
            binding.tvDatamilesBeforeScore.setText(String.format("%d점", Math.abs(prevScore - currentScore)));

            // 전일 대비 안전 운전 점수 상승, 하락 아이콘
            binding.ivDatamilesDrivingScoreIcon.setVisibility(prevScore == currentScore ? View.INVISIBLE : View.VISIBLE);
            binding.ivDatamilesDrivingScoreIcon.setEnabled(prevScore < currentScore);

            String rankTemplate = getContext().getString(R.string.gm01_format_rank);
            // 전체 차량 대비 상위 %
            ValueAnimator distributionAni = ValueAnimator.ofInt(detail.getDistribution())
                    .setDuration(ANI_DURATION);
            distributionAni.addUpdateListener(animation -> {
                binding.tvDatamilesDrivingScoreRankAll.setText(
                        String.format(rankTemplate, (int) animation.getAnimatedValue())
                );
            });

            // 동일 차종 대비 상위 %
            ValueAnimator modelDistributionAni = ValueAnimator.ofInt(detail.getModelDistribution())
                    .setDuration(ANI_DURATION);
            modelDistributionAni.addUpdateListener(animation -> {
                binding.tvDatamilesDrivingScoreRankCategory.setText(
                        String.format(rankTemplate, (int) animation.getAnimatedValue())
                );
            });

            // 운전 점수의 일괄 애니메이션 처리.
            animatorSet.playTogether(scoreAni, distributionAni, modelDistributionAni);
        }

        private void bindReplacements(Context context, ItemDatamilesBinding binding, Replacements.Response replacements) {
            if (replacements == null) {
                return;
            }

            try {
                binding.tvDatamilesExpendablesUpdateDate.setText(
                        DateUtil.getDate(
                                DateUtil.getDefaultDateFormat(replacements.getOdometer().getTimestamp(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss),
                                DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm
                        ) + " " + getContext().getString(R.string.gm01_update)
                );
            } catch (Exception e) {
                // Date 파싱 에러.
                // TODO 필요에 따라 에러 예외 처리 필요.
            }

            binding.tvDatamilesExpendablesTotalDistance.setText(DevelopersViewModel.getDistanceFormatByUnit(
                    replacements.getOdometer().getValue(),
                    replacements.getOdometer().getUnit()
            ));

            try {
                binding.lDatamilesExpenablesList.removeAllViews();
                final String distanceFormat = context.getString(R.string.gm01_format_distance);
                for (SestVO sestVO : replacements.getSests()) {
                    // 평균 교체 필요 거리
                    int stdDistance = sestVO.getStdDistance();
                    // 최종 교체 후 주행 거리
                    int odoMeter = Integer.parseInt(sestVO.getLastInfo().getOdometer());

                    int diff = stdDistance - odoMeter;
                    int progress = 0;

                    View view = LayoutInflater.from(context).inflate(R.layout.item_datamiles_expendable_item, binding.lDatamilesExpenablesList, false);
                    // 소모품 이름
                    TextView txtNme = view.findViewById(R.id.tv_datamiles_expendables_item_name);
                    txtNme.setText(sestVO.getSestName());
                    // 교체 필요 아이콘
                    view.findViewById(R.id.tv_datamiles_expendables_need_change).setVisibility(stdDistance <= odoMeter ? View.VISIBLE : View.INVISIBLE);

                    // 잔여 거리. 마이너스 남은 거리는 0으로 처리.
                    TextView txtDistance = view.findViewById(R.id.tv_datamiles_expendables_distance);
                    ProgressBar progDistance = view.findViewById(R.id.progress_datamiles_expendables);

                    // 잔여 거리 대비 퍼센트 계산을 진행. 최대 값 기준은 ProgressBar의 최대치를 기준.
                    if (diff > 0) {
                        // 잔여 거리가 있을 경우 애니메이션 처리 진행.
                        ValueAnimator diffAni = ValueAnimator.ofInt(diff)
                                .setDuration(ANI_DURATION);
                        diffAni.addUpdateListener(animation -> {
                            txtDistance.setText(String.format(distanceFormat, (int) animation.getAnimatedValue()));
                        });
                        animatorSet.playTogether(diffAni);

                        // 잔여 비율 계산.
                        progress = (diff * progDistance.getMax()) / stdDistance;
                    } else {
                        // 잔여 거리가 없는 경우
                        txtDistance.setText(String.format(context.getString(R.string.gm01_format_distance), 0));
                        progress = progDistance.getMax();
                    }

                    // 잔여 거리 ProgressBar 애니메이션 처리.
                    ValueAnimator progressAni = ValueAnimator.ofInt(progress)
                            .setDuration(ANI_DURATION);
                    progressAni.addUpdateListener(animation -> {
                        progDistance.setProgress((int) animation.getAnimatedValue());
                    });
                    animatorSet.playTogether(progressAni);

                    // TODO 쿠폰 연동은 추가로 진행이 필요.
                    TextView txtCoupon = view.findViewById(R.id.tv_datamiles_expendables_coupon);

                    binding.lDatamilesExpenablesList.addView(view);
                }
            } catch (Exception e) {

            }
        }

        private void bindDtc(ItemDatamilesBinding binding, Dtc.Response dtc) {
            if (dtc != null && dtc.getDtcList() != null && !dtc.getDtcList().isEmpty()) {
                // 차량 진단 데이터가 있고, 고장 코드가 하나라도 있는 경우.
                binding.lDatamilesDiagnosticContainer.setVisibility(View.VISIBLE);
                binding.tvDatamilesBreakdownCodeCount.setText(String.format("%d건", dtc.getDtcList().size()));

                // DTC List 중 가장 최신 시간을 가져오기 위한 코드.
                Date latestTime = new Date(0);
                for (DtcVO dtcVo : dtc.getDtcList()) {
                    Date time = DateUtil.getDefaultDateFormat(dtcVo.getTimestamp(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss);
                    if (time.getTime() > latestTime.getTime()) {
                        latestTime = time;
                    }
                }
                try {
                    binding.tvDatamilesVehicleDiagnosticUpdateDate.setText(
                            DateUtil.getDate(latestTime, DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm
                            ) + " " + getContext().getString(R.string.gm01_update)
                    );
                } catch (Exception e) {
                    // Date 파싱 에러.
                    // TODO 필요에 따라 에러 예외 처리 필요.
                }
            } else {
                // 차량 진단 데이터가 없거나, 고장 코드가 없는 경우.
                binding.lDatamilesDiagnosticContainer.setVisibility(View.GONE);
            }
        }
    }
} // end of class Home2DataMilesAdapter
