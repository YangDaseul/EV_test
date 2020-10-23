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
import com.genesis.apps.databinding.FragmentServiceDriveBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentServiceDrive extends SubFragment<FragmentServiceDriveBinding> {
    private static final String TAG = FragmentServiceDrive.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        View view = super.setContentView(inflater, R.layout.fragment_service_drive);
        me.setFragment(this);

        setOnSingleClickListener();

        return view;
    }

    //이중 클릭 방지 클릭 리스너 붙이기. 이 프래그먼트는 누를 게 신청 버튼 하나 뿐이라 어댑터가 없으니 여기서 붙인다
    private void setOnSingleClickListener() {
        me.tvServiceDriveReqBtn.setOnClickListener(onSingleClickListener);
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
            //대리운전 신청(이미 신청한 상태이면 그 내용을 보여줌)
            case R.id.tv_service_drive_req_btn:
                ((BaseActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceDriveReqActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            default:
                //do nothing
                break;
        }
    }
}
