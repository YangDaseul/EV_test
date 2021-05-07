package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.developers.EvStatus;
import com.genesis.apps.databinding.FragmentEvChargeStatusBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;

/**
 * Class Name : EvChargeStatusFragment
 * 충전소 찾기 > 차량 충전 상태 표시 Fragment
 *
 * @author Ki-man Kim
 * @since 2021-03-22
 */
public class EvChargeStatusFragment extends SubFragment<FragmentEvChargeStatusBinding> {

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
        me.setLifecycleOwner(getViewLifecycleOwner());
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

    }

    /****************************************************************************************************
     * Method - Public
     ****************************************************************************************************/
    public void updateEvChargeStatus(EvStatus.Response data) {
        if (data == null) {
            // 충전 정보 조회 실패.
            me.tvHasBattery.setText("- %");
            me.tvHasDistance.setText("- km");
            me.progress.setProgress(0);
            me.tvBtnRetry.setVisibility(View.VISIBLE);
            me.dot.setVisibility(View.VISIBLE);
            me.tvErrorChargeInfo.setVisibility(View.VISIBLE);
            return;
        }
        me.tvBtnRetry.setVisibility(View.GONE);
        me.dot.setVisibility(View.GONE);
        me.tvErrorChargeInfo.setVisibility(View.GONE);
        me.tvHasBattery.setText(String.format("%.1f%%", data.getSoc()));
    }


} // end of class EvChargeStatusFragment
