package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.OilCodes;
import com.genesis.apps.comm.model.ResultCodes;
import com.genesis.apps.databinding.ActivityMygOilIntegrationBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MyGOilIntegrationActivity extends SubActivity<ActivityMygOilIntegrationBinding> {

    private String oilRfnCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_integration);
        getOilCode();
        setView();

    }

    private void setView() {
        ui.tv1.setText(OilCodes.findCode(oilRfnCd).getOilNm()+"\n"+getString(R.string.mg_con02_2));
        ui.ivOil.setBackgroundResource(OilCodes.findCode(oilRfnCd).getBigSrc());
    }

    private void getOilCode(){
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

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_integration:
                //TODO 약관동의 페이지로 이동 및 데이터 실패에 대한 스낵바 정의를 여기서 해줘야함.

                break;
        }
    }

}
