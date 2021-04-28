package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ActivityServiceChargeBtrResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ServiceChargeBtrResultActivity extends SubActivity<ActivityServiceChargeBtrResultBinding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private String carNo;
    private String rsvtDate;
    private String address;
    private boolean isCarwash;

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
        ui.lCarRegNo.setMsg(carNo);
        ui.lReserveDtm.setMsg(DateUtil.getDate(DateUtil.getDefaultDateFormat(rsvtDate, DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_MM_dd_E_HH_mm));
        ui.lPickupAddr.setMsg(address);

        String options = getString(R.string.service_charge_btr_word_05);
        if(isCarwash)
            options += ", " + getString(R.string.service_charge_btr_word_06);

        ui.lReserveInfo.setMsg(options);
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
            carNo = getIntent().getStringExtra(KeyNames.KEY_NAME_CHB_CAR_NO);
            rsvtDate = getIntent().getStringExtra(KeyNames.KEY_NAME_CHB_RSVT_DT);
            address = getIntent().getStringExtra(KeyNames.KEY_NAME_CHB_ADDRESS);
            isCarwash = !TextUtils.isEmpty(getIntent().getStringExtra(KeyNames.KEY_NAME_CHB_OPTION_TY)) ? true : false;
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
