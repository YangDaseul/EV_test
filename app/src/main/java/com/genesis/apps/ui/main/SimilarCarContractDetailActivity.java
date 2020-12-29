package com.genesis.apps.ui.main;

import android.os.Bundle;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.STO_1003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.databinding.ActivitySimilarCarContractDetailBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * @author hjpark
 * @brief 구매 계약 내역 상세
 */
public class SimilarCarContractDetailActivity extends SubActivity<ActivitySimilarCarContractDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_car_contract_detail);
        setViewModel();
        setObserver();
        getDataFromIntent();
    }


    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void getDataFromIntent() {
        STO_1003.Response response = null;
        try {
            response = (STO_1003.Response)getIntent().getSerializableExtra(KeyNames.KEY_NAME_STO_1003);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response==null) {
                exitPage("게약 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }else{
                ui.setData(response);
            }
        }
    }
}
