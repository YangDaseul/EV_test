package com.genesis.apps.ui.main.home.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.vo.DataMilesVO;
import com.genesis.apps.comm.net.NetUIResponse;
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
            String dummyData = "{\"safetyDrvScore\":75,\"prevSafetyDrvScore\":72,\"isDiscountYn\":\"Y\",\"bsrtAccCount\":4,\"bsrtDecCount\":0,\"nightDrvCount\":2,\"rangeDrvDist\":1200,\"distribution\":10,\"modelDistribution\":21,\"insightMsg\":\"최고의 모범 안전 운전자입니다.\n안전운전 유지하시고 자동차 보험 혜택을 받아보세요!\n(최대 12% 할인)\",\"scoreDate\":20200408223039,\"msgId\":\"11e77efa-aff0-4b3c-a5a0-c4cde4674963\"}";
            item.setDrivingScoreDetail(new Gson().fromJson(dummyData, Detail.Response.class));

//            item.setDrivingScoreDetail(detail.data);
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

        public ItemDataMilesAdapter(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(DataMilesVO item) {
            ItemDatamilesBinding binding = getBinding();
            Detail.Response detail = item.getDrivingScoreDetail();
            if (detail != null) {
                ValueAnimator scoreAni = ValueAnimator.ofInt(detail.getSafetyDrvScore())
                        .setDuration(ANI_DURATION);
                scoreAni.addUpdateListener(animation -> {
                    int score = (int) animation.getAnimatedValue();
                    binding.tvDatamilesDrivingScore.setText(String.valueOf(score));
                    binding.asbDatamilesDrivingScore.setProgress(score);
                });
                scoreAni.start();
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
