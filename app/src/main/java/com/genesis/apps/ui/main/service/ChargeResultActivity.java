package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ReserveInfo;
import com.genesis.apps.databinding.ActivityChargeResultBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * @author hjpark
 * @brief 충전소예약 4단계
 */
public class ChargeResultActivity extends SubActivity<ActivityChargeResultBinding> {

    private ReserveInfo reserveInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_result);
        getDataFromIntent();
        setViewModel();
        setObserver();
    }

    @Override
    public void onBackPressed() {
        startChargeReserveHistoryActivity();
        super.onBackPressed();
    }

    @Override
    public void onBackButton() {
        startChargeReserveHistoryActivity();
        super.onBackPressed();
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm://다음
                startChargeReserveHistoryActivity();
                super.onBackPressed();
                break;
            case R.id.btn_call:
                if(reserveInfo!=null&&!TextUtils.isEmpty(reserveInfo.getBcall())){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + reserveInfo.getBcall())));
                }
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        ui.setData(reserveInfo);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
        //todo 데이터 구조 변경 필요
        try {
            reserveInfo = (ReserveInfo) getIntent().getSerializableExtra(KeyNames.KEY_NAME_CHARGE_RESERVE_INFO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reserveInfo == null) {
                exitPage("신청 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    private void startChargeReserveHistoryActivity() {
        startActivitySingleTop(new Intent(this, ChargeReserveHistoryActivity.class),
                RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

}
