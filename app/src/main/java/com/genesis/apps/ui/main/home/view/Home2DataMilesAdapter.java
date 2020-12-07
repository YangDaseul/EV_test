package com.genesis.apps.ui.main.home.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.api.developers.Replacements;
import com.genesis.apps.comm.model.vo.DataMilesVO;
import com.genesis.apps.comm.model.vo.developers.SestVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.databinding.ItemDatamilesBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
import com.google.gson.Gson;

/**
 * Class Name : Home2DataMilesAdapter
 *
 * @author Ki-man Kim
 * @since 2020-11-30
 */
public class Home2DataMilesAdapter extends BaseRecyclerViewAdapter2<DataMilesVO> {
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemDataMilesAdapter(getView(parent, R.layout.item_datamiles));
    }

    public void setDetail(String carId, NetUIResponse<Detail.Response> detail) {
        DataMilesVO item = findVOByCarId(carId);
        if (item != null) {
            item.setDetailStatus(DataMilesVO.STATUS.SUCCESS);

            // TODO : 더미데이터이므로 실제로는 삭제 필요.
            String dummyData = "{\"safetyDrvScore\":75,\"prevSafetyDrvScore\":76,\"isDiscountYn\":\"Y\",\"bsrtAccCount\":4,\"bsrtDecCount\":0,\"nightDrvCount\":2,\"rangeDrvDist\":1201,\"distribution\":10,\"modelDistribution\":21,\"insightMsg\":\"최고의 모범 안전 운전자입니다.\n안전운전 유지하시고 자동차 보험 혜택을 받아보세요!\n(최대 12% 할인)\",\"scoreDate\":20200408223039,\"msgId\":\"11e77efa-aff0-4b3c-a5a0-c4cde4674963\"}";
            item.setDrivingScoreDetail(new Gson().fromJson(dummyData, Detail.Response.class));

//            item.setDrivingScoreDetail(detail.data);
        }
    }

    public void setReplacements(String carId) {
        DataMilesVO item = findVOByCarId(carId);

        if (item != null) {
            // TODO : 더미데이터이므로 실제로는 삭제 필요.
            String dummyData = "{\"sests\":[{\"sestCode\":1,\"sestName\":\"엔진오일/필터\",\"stdDistance\":10000,\"lastInfo\":{\"replacementDate\":\"20200701101011\",\"updateDate\":\"20200701101011\",\"odometer\":\"25420\"}},{\"sestCode\":2,\"sestName\":\"에어클리너\",\"stdDistance\":20000,\"lastInfo\":{\"replacementDate\":\"20200701101011\",\"updateDate\":\"20200701101011\",\"odometer\":\"25420\"}}],\"odometer\":{\"timestamp\":\"20200114152139\",\"value\":12320,\"unit\":1},\"msgId\":\"5db9fc02-1b36-448e-9307-52761fd9ad92\"}";
            item.setReplacements(new Gson().fromJson(dummyData, Replacements.Response.class));
        }
    }

    private DataMilesVO findVOByCarId(String carId) {
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

            // 안전운전 점수 그래프 터치 비활성화 처리.
            binding.asbDatamilesDrivingScore.setOnTouchListener((view, event) -> {
                return true;
            });

            if (detail != null) {
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

                // 기존 애니메이션 처리는 멈춤.
                animatorSet.removeAllListeners();
                animatorSet.end();
                animatorSet.cancel();

                // 운전 점수의 일괄 애니메이션 처리.
                animatorSet.playTogether(scoreAni, distributionAni, modelDistributionAni);
                animatorSet.start();
            }

            if (replacements != null) {
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
                    for (SestVO sestVO : replacements.getSests()) {
                        // 평균 교체 필요 거리
                        int stdDistance = sestVO.getStdDistance();
                        // 최종 교체 후 주행 거리
                        int odoMeter = Integer.parseInt(sestVO.getLastInfo().getOdometer());

                        int diff = stdDistance - odoMeter;
                        int progress = 0;

                        if(diff > 0) {
                            progress = diff / stdDistance * 100;
                        }

                        View view = LayoutInflater.from(context).inflate(R.layout.item_datamiles_expendable_item, binding.lDatamilesExpenablesList, false);
                        // 소모품 이름
                        TextView txtNme = view.findViewById(R.id.tv_datamiles_expendables_item_name);
                        txtNme.setText(sestVO.getSestName());
                        // 교체 필요 아이콘
                        view.findViewById(R.id.tv_datamiles_expendables_need_change).setVisibility(stdDistance <= odoMeter ? View.VISIBLE : View.INVISIBLE);

                        // 잔여 거리. 마이너스 남은 거리는 0으로 처리.
                        TextView txtDistance = view.findViewById(R.id.tv_datamiles_expendables_distance);
                        txtDistance.setText(String.format(context.getString(R.string.gm01_format_distance), Math.max(diff, 0)));

                        // TODO 쿠폰 연동은 추가로 진행이 필요.
                        TextView txtCoupon = view.findViewById(R.id.tv_datamiles_expendables_coupon);

                        ProgressBar progDistance = view.findViewById(R.id.progress_datamiles_expendables);


                    }
                } catch (Exception e) {

                }
            }
        }

        @Override
        public void onBindView(DataMilesVO item, int pos) {
        }

        @Override
        public void onBindView(DataMilesVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }
} // end of class Home2DataMilesAdapter
