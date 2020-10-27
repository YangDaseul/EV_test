package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.databinding.FragmentServiceCarWashBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentCarWash extends SubFragment<FragmentServiceCarWashBinding> {
    private static final String TAG = FragmentCarWash.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        View view = super.setContentView(inflater, R.layout.fragment_service_car_wash);
        me.setFragment(this);

        init();

        return view;
    }

    private void init() {

        //todo 신청내역 버튼 리스너, 적당한 코드 위치 정해서 이동하기
        me.lServiceCarWashHistoryBtn.lServiceCarWash.setOnClickListener(v -> {
            ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), CarWashHistoryActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        });
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
}
