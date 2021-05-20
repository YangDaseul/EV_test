package com.genesis.apps.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.DTW_1007;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.databinding.ActivityUnpaidPayResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class UnpaidPayResultActivity extends SubActivity<ActivityUnpaidPayResultBinding> {

    private DTW_1007.Response data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpaid_pay_result);
        getDataFromIntent();
        setViewModel();
        setObserver();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                exitPage(new Intent(), ResultCodes.REQ_CODE_UNPAID_PAYMT_FINISH.getCode());
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        ui.setData(data);
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void getDataFromIntent() {
        try {
            data = (DTW_1007.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_CONTENTS_VO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (data == null) {
                exitPage("결제 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
