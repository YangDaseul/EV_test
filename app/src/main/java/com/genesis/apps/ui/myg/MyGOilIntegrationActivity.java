package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityMygOilIntegrationBinding;
import com.genesis.apps.databinding.ActivityMygVersionBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

public class MyGOilIntegrationActivity extends SubActivity<ActivityMygOilIntegrationBinding> {

    private String oilRfnCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_integration);
        //TODO 처리 필요
    }

    private void getRfnCd(){
        //todo 소스 오면.. 처리 필요
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()){
            case R.id.btn_integration:

                break;
        }
    }

}
