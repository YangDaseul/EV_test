package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.CHB_1027;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityServiceChargeBtrResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ServiceChargeBtrResultActivity extends SubActivity<ActivityServiceChargeBtrResultBinding> {

    private CHB_1027.Response data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(R.layout.activity_service_charge_btr_result);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        ui.lCarRegNo.setMsg(StringUtil.isValidString(data.getCarNo()));
        ui.lReserveDtm.setMsg(DateUtil.getDate(DateUtil.getDefaultDateFormat(StringUtil.isValidString(data.getBookingDtm()), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_MM_dd_E_HH_mm));
        ui.lPickupAddr.setMsg(StringUtil.isValidString(data.getAddress()) + StringUtil.isValidString(data.getAddressDetail()));
        ui.lReserveInfo.setMsg(StringUtil.isValidString(data.getSvcNm()));
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                exitPage(new Intent(), ResultCodes.REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FINISH.getCode());
                break;
            default:
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void getDataFromIntent() {
        try {
            data = (CHB_1027.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_CONTENTS_VO);
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackButton() {
        onBackPressed();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
