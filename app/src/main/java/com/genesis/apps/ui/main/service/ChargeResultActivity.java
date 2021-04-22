package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.databinding.ActivityChargeResultBinding;
import com.genesis.apps.databinding.ActivityServiceRepair4ResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * @author hjpark
 * @brief 충전소예약 4단계
 */
public class ChargeResultActivity extends SubActivity<ActivityChargeResultBinding> {
    //todo VO 변경 필요
    private RepairReserveVO repairReserveVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_result);
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
            case R.id.btn_call:
                //TODO 운영업체 전화번호 연결 처리 필요
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + "전화번호넣을것")));
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
        //todo 데이터 구조 변경 필요
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
