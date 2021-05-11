package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.developers.EvStatus;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.databinding.FragmentEvChargeStatusBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

/**
 * Class Name : EvChargeStatusFragment
 * 충전소 찾기 > 차량 충전 상태 표시 Fragment
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
public class EvChargeStatusFragment extends SubFragment<FragmentEvChargeStatusBinding> {

    private DevelopersViewModel developersViewModel;

    public static EvChargeStatusFragment newInstance() {
        Bundle args = new Bundle();
        EvChargeStatusFragment fragment = new EvChargeStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private EvStatus.Response evData;

    private EvChargeStatusFragment() {
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_ev_charge_status);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        me.setFragment(this);
        me.setLifecycleOwner(getViewLifecycleOwner());
        developersViewModel = new ViewModelProvider(getActivity()).get(DevelopersViewModel.class);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.tv_btn_retry:
                try {
                    VehicleVO mainVehicleVO = developersViewModel.getMainVehicleSimplyFromDB();
                    developersViewModel.reqEvStatus(new EvStatus.Request(developersViewModel.getCarId(mainVehicleVO.getVin())));
                }catch (Exception e){

                }
                break;
        }

    }

    /****************************************************************************************************
     * Method - Public
     ****************************************************************************************************/
    public void updateEvChargeStatus(EvStatus.Response data) {
        me.lWhole.setVisibility(View.VISIBLE);
        if (data == null) {
            // 충전 정보 조회 실패.
            me.tvHasBattery.setText("- %");
            me.tvHasDistance.setText("- km");
            me.progress.setProgress(0);
            me.tvBtnRetry.setVisibility(View.VISIBLE);
            me.dot.setVisibility(View.VISIBLE);
            me.tvErrorChargeInfo.setVisibility(View.VISIBLE);
        } else {
            //프로그레스바 셋팅
            me.progress.setBackgroundColor(getContext().getColor(getProgressColor(data)));
            me.progress.setProgress((int) data.getSoc());
            //배터리 셋팅
            me.tvHasBattery.setText(String.format("%.1f%%", data.getSoc()));
            //거리 셋팅
            if (data.getDte() != null && data.getDte().getDistance() != null) {
                me.tvHasDistance.setText(DevelopersViewModel.getDistanceFormatByUnit((int) data.getDte().getDistance().getValue(), (int) data.getDte().getDistance().getUnit()).replace(" ", ""));
            } else {
                me.tvHasDistance.setText("- km");
            }

            //하단 메시지 표시
            me.tvBtnRetry.setVisibility(View.GONE);
            if (data.isBatteryCharge()) {
                me.dot.setVisibility(View.VISIBLE);
                me.tvErrorChargeInfo.setVisibility(View.VISIBLE);
                me.tvErrorChargeInfo.setText(String.format(getContext().getString(R.string.sm_evss01_38), developersViewModel.getBatteryChargeTime()));
            } else {
                me.dot.setVisibility(View.GONE);
                me.tvErrorChargeInfo.setVisibility(View.GONE);
            }
        }
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private int getProgressColor(EvStatus.Response data){
        int progressColor = R.color.x_996449;

        if (data.isBatteryCharge()) {
            //충전중
            progressColor = R.color.x_2b9d49;
        } else if(data.getSoc() <= 30) {
            //30퍼 이하일 경우
            progressColor = R.color.x_ce2d2d;
        }

        return progressColor;
    }


} // end of class EvChargeStatusFragment
