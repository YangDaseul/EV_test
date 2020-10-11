package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.databinding.ActivityMygOilIntegrationBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MyGOilIntegrationActivity extends SubActivity<ActivityMygOilIntegrationBinding> {

    private String oilRfnCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_integration);
        getDataFromIntent();
        setViewModel();
        setObserver();
        setView();
    }

    private void setView() {
        ui.setActivity(this);
        ui.tv1.setText(getString(OilCodes.findCode(oilRfnCd).getOilNm())+"\n"+getString(R.string.mg_con02_2));
        ui.ivOil.setImageResource(OilCodes.findCode(oilRfnCd).getBigSrc());
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_integration:
                //TODO 약관동의 페이지로 이동 및 데이터 실패에 대한 스낵바 정의를 여기서 해줘야함.
                startActivitySingleTop(new Intent(this, MyGOilTermActivity.class).putExtra(OilCodes.KEY_OIL_CODE, oilRfnCd), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }
    }

    @Override
    public void setViewModel() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
        try {
            oilRfnCd = getIntent().getStringExtra(OilCodes.KEY_OIL_CODE);
            if(TextUtils.isEmpty(oilRfnCd)){
                exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

}
