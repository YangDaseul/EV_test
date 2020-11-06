package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.api.DDS_1001;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.viewmodel.DDSViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityServiceDriveReqResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.Objects;

public class ServiceDriveReqResultActivity extends SubActivity<ActivityServiceDriveReqResultBinding> {
    private static final String TAG = ServiceDriveReqResultActivity.class.getSimpleName();

    private DDSViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_drive_req_result);

        setHistoryBtnListener();
        setViewModel();
        getDataFromIntent();//데이터 제대로 안 들어있으면 액티비티 종료처리까지 함
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_text_btn:
                startActivitySingleTop(new Intent(this, ServiceDriveHistoryActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(DDSViewModel.class);
    }

    @Override
    public void setObserver() {

    }

    //인텐트 까서 데이터를 뷰에 뿌림. 실패하면 액티비티 종료(뷰에 데이터 없어서 화면 다 깨짐)
    @Override
    public void getDataFromIntent() {
        Log.d(TAG, "getDataFromIntent: ");

        try {
            DDS_1001.Response data = (DDS_1001.Response) getIntent().getSerializableExtra(DDS_1001.SERVICE_DRIVE_STATUS);
            Log.d(TAG, "getDataFromIntent: " + data);
            initView(data);
        } catch (NullPointerException e) {
            Log.d(TAG, "init: 신청 현황 데이터에 문제 있음");
            finish();
        }
    }

    private void initView(DDS_1001.Response data) {
        ui.setData(data);

        //todo : 뷰스텁 처리
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setHistoryBtnListener() {
        ui.lServiceDriveReqResultTitlebar.tvTitlebarTextBtn.setOnClickListener(onSingleClickListener);
    }

}
