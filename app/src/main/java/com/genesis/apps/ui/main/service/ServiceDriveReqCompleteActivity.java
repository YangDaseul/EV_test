package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.ActivityServiceDriveReqCompleteBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class ServiceDriveReqCompleteActivity extends SubActivity<ActivityServiceDriveReqCompleteBinding> {
    private static final String TAG = ServiceDriveReqCompleteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_drive_req_complete);

        getDataFromIntent();
        SnackBarUtil.show(this, getString(R.string.sd_pay_succ));
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.back:
            case R.id.tv_service_drive_req_complete_ok_btn:
                onBackPressed();
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setViewModel() {
        //do nothing
    }

    @Override
    public void setObserver() {
        //do nothing
    }

    //인텐트 까서 데이터를 뷰에 뿌림
    @Override
    public void getDataFromIntent() {
        Log.d(TAG, "getDataFromIntent: ");

        try {
            int msgId = getIntent().getIntExtra(KeyNames.KEY_NAME_SERVICE_DRIVE_REQ_COMPLETE_MSG_ID, R.string.service_drive_req_end_realtime);
            ui.tvServiceDriveReqCompleteMsg.setText(msgId);
        } catch (NullPointerException e) {
            Log.d(TAG, "인텐트 문제");
            finish();
        }
    }
}
