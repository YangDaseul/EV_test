package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

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
        ui.ivOil1.setImageResource(OilCodes.findCode(oilRfnCd).getBigSrc());
        ui.ivOil2.setImageResource(OilCodes.findCode(oilRfnCd).getBigSrc2());
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_integration:
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
        }catch (Exception e){
            e.printStackTrace();
        }

        if(TextUtils.isEmpty(oilRfnCd)){
            exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ResultCodes.REQ_CODE_OIL_INTEGRATION_SUCCESS.getCode()){
            exitPage(data, ResultCodes.REQ_CODE_NORMAL.getCode());
        }
    }


}
