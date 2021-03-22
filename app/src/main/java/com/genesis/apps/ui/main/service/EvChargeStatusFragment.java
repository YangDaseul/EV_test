package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.genesis.apps.R;
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

    private EvChargeStatusFragment() {
    }

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
} // end of class EvChargeStatusFragment
