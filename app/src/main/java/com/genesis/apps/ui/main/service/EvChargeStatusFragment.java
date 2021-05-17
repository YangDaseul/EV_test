package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.Html;
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

import static com.genesis.apps.comm.viewmodel.DevelopersViewModel.CCSSTAT.STAT_AGREEMENT;

/**
 * Class Name : EvChargeStatusFragment
 * 충전소 찾기 > 차량 충전 상태 표시 Fragment
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
public class EvChargeStatusFragment extends SubFragment<FragmentEvChargeStatusBinding> {

    private DevelopersViewModel developersViewModel;
    private VehicleVO mainVehicle;

    public static EvChargeStatusFragment newInstance() {
        Bundle args = new Bundle();
        EvChargeStatusFragment fragment = new EvChargeStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EvChargeStatusFragment() {

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
        setObserver();
    }

    @Override
    public void onRefresh() {
        getMainVehicle();
        getEvStatus();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.tv_btn_retry:
                try {
                    VehicleVO mainVehicleVO = developersViewModel.getMainVehicleSimplyFromDB();
                    developersViewModel.reqEvStatus(new EvStatus.Request(developersViewModel.getCarId(mainVehicleVO.getVin())));
                } catch (Exception e) {

                }
                break;
        }
    }

    /****************************************************************************************************
     * Method - Public
     ****************************************************************************************************/
    public void updateEvChargeStatus(EvStatus.Response data) {
        me.lWhole.setVisibility(View.VISIBLE);
        me.line0.setVisibility(View.VISIBLE);
        me.tvTitleDistance.setVisibility(View.VISIBLE);
        me.tvHasDistance.setVisibility(View.VISIBLE);
        me.ivIcDistance.setVisibility(View.VISIBLE);
        me.tvTitleBattery.setVisibility(View.VISIBLE);

        if (data == null) {
            // 충전 정보 조회 실패.
            me.tvHasBattery.setText("- %");
            me.tvHasDistance.setText("- km");
            me.tvBtnRetry.setVisibility(View.VISIBLE);
            me.tvErrorChargeInfo.setVisibility(View.VISIBLE);
        } else {
            //다시보기 버튼 제 거
            me.tvBtnRetry.setVisibility(View.GONE);
            //배터리 셋팅
            me.tvHasBattery.setText((int)data.getSoc()+"%");
            //거리 셋팅
            if (data.getDte() != null && data.getDte().getDistance() != null) {
                me.tvHasDistance.setText(DevelopersViewModel.getDistanceFormatByUnit((int) data.getDte().getDistance().getValue(), (int) data.getDte().getDistance().getUnit()).replace(" ", ""));
            } else {
                me.tvHasDistance.setText("- km");
            }

            if (data.isBatteryCharge()) {
                //충전 중 일경우
                //안내메시지 셋팅
                me.tvErrorChargeInfo.setVisibility(View.VISIBLE);
                me.tvErrorChargeInfo.setText(String.format(getContext().getString(R.string.sm_evss01_38), developersViewModel.getBatteryChargeTime(), "100%"));
                me.tvHasBattery.setText(Html.fromHtml(getString(R.string.sm_evss01_39), Html.FROM_HTML_MODE_COMPACT));
                me.line0.setVisibility(View.GONE);
                me.tvTitleDistance.setVisibility(View.GONE);
                me.tvHasDistance.setVisibility(View.GONE);
                me.ivIcDistance.setVisibility(View.GONE);
                me.tvTitleBattery.setVisibility(View.GONE);
            } else {
                if(data.getSoc()<=30){
                    //충전량이 30프로 이하인 경우
                    me.tvErrorChargeInfo.setVisibility(View.VISIBLE);
                    me.tvErrorChargeInfo.setText(R.string.sm_evss01_40);
                }else{
                    //그 외
                    me.tvErrorChargeInfo.setVisibility(View.GONE);
                }
            }
        }
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void getMainVehicle() {
        try {
            mainVehicle = developersViewModel.getMainVehicleSimplyFromDB();
        } catch (Exception e) {

        }
    }

    private void setObserver() {
        developersViewModel = new ViewModelProvider(this).get(DevelopersViewModel.class);
        developersViewModel.getRES_EV_STATUS().observe(getViewLifecycleOwner(), result -> {
            switch (result.status) {
                case LOADING:
                    me.tvBtnRetry.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                default:
                    updateEvChargeStatus(result.data);
                    break;
            }
        });
    }

    private void getEvStatus() {
        if (mainVehicle!=null&&developersViewModel.checkCarInfoToDevelopers(mainVehicle.getVin(), "") == STAT_AGREEMENT) {
            me.lWhole.setVisibility(View.VISIBLE);
            developersViewModel.reqEvStatus(new EvStatus.Request(developersViewModel.getCarId(mainVehicle.getVin())));
        } else {
            me.lWhole.setVisibility(View.GONE);
        }
    }


} // end of class EvChargeStatusFragment
