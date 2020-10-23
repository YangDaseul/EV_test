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
import com.genesis.apps.databinding.FragmentServiceMaintenanceBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentMaintenance extends SubFragment<FragmentServiceMaintenanceBinding> {
    private static final String TAG = FragmentMaintenance.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        View view = super.setContentView(inflater, R.layout.fragment_service_maintenance);
        me.setFragment(this);

        setOnSingleClickListener();

        return view;
    }

    //이중 클릭 방지 클릭 리스너 붙이기. 이 프래그먼트는 아이템 목록이 서버 값이 아니고 로컬 고정값이라 어댑터가 없으니 여기서 붙인다
    private void setOnSingleClickListener() {
        //서비스 네트워크 찾기
        me.tvServiceMaintenanceFindNetworkBtn.setOnClickListener(onSingleClickListener);
        //정비 예약
        me.lServiceMaintenanceReservationBtn.lServiceMaintenanceItem.setOnClickListener(onSingleClickListener);
        //정비 현황/예약 내역
        me.lServiceMaintenanceHistoryBtn.lServiceMaintenanceItem.setOnClickListener(onSingleClickListener);
        //긴급출동
        me.lServiceMaintenanceEmergencyBtn.lServiceMaintenanceItem.setOnClickListener(onSingleClickListener);
        //원격진단 신청
        me.lServiceMaintenanceCustomercenterBtn.lServiceMaintenanceItem.setOnClickListener(onSingleClickListener);
        //하자재발통보
        me.lServiceMaintenanceDefectBtn.lServiceMaintenanceItem.setOnClickListener(onSingleClickListener);
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
        int id = v.getId();
        Log.d(TAG, "onClickCommon: view id :" + id);

        switch (id) {
            //TODO 지금 테스트 액티비티 호출 코드임. 제대로 된 기능으로 바꾸기
            //서비스 네트워크 찾기
            case R.id.tv_service_maintenance_find_network_btn:
//                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceDriveReqActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE)
//                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), TestActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //정비 예약
            case R.id.l_service_maintenance_reservation_btn:
                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), MaintenanceReserveActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //정비 현황/예약 내역
            case R.id.l_service_maintenance_history_btn:
//                startActivity(new Intent(getActivity(), MaintenanceReserveActivity.class));
                break;

            //긴급출동
            case R.id.l_service_maintenance_emergency_btn:
//                startActivity(new Intent(getActivity(), MaintenanceReserveActivity.class));
                break;

            //원격진단 신청
            case R.id.l_service_maintenance_customercenter_btn:
//                startActivity(new Intent(getActivity(), MaintenanceReserveActivity.class));
                break;

            //하자재발통보
            case R.id.l_service_maintenance_defect_btn:
//                startActivity(new Intent(getActivity(), MaintenanceReserveActivity.class));
                break;

            default:
                //do nothing
                break;
        }
    }
}
