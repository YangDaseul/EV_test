package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.databinding.ActivityServiceHometohome4ResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * @author hjpark
 * @brief 홈투홈 4단계
 */
public class ServiceHomeToHome4ResultActivity extends SubActivity<ActivityServiceHometohome4ResultBinding> {
    private RepairReserveVO repairReserveVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_hometohome_4_result);
        getDataFromIntent();
        setViewModel();
        setObserver();
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm://다음
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        ui.setData(repairReserveVO);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
        try {
            repairReserveVO = (RepairReserveVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (repairReserveVO == null) {
                exitPage("신청 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

}
