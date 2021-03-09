package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.OIL_0005;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.OILViewModel;
import com.genesis.apps.databinding.ActivityMygOilIntegrationBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_JOIN_CODE_R;

public class MyGOilIntegrationActivity extends SubActivity<ActivityMygOilIntegrationBinding> {

    private String oilRfnCd;
    private String rgstYn;
    private OILViewModel oilViewModel;

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
        switch (v.getId()) {
            case R.id.btn_integration:
                if (TextUtils.isEmpty(rgstYn) || !rgstYn.equalsIgnoreCase(OIL_JOIN_CODE_R))
                    startActivitySingleTop(new Intent(this, MyGOilTermActivity.class).putExtra(OilCodes.KEY_OIL_CODE, oilRfnCd), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                else
                    oilViewModel.reqOIL0005(new OIL_0005.Request(APPIAInfo.MG01.getId(), StringUtil.isValidString(oilRfnCd)));
                break;
        }
    }

    @Override
    public void setViewModel() {
        oilViewModel = new ViewModelProvider(this).get(OILViewModel.class);
    }

    @Override
    public void setObserver() {
        oilViewModel.getRES_OIL_0005().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        exitPage(getString(R.string.mg_con02_p01_3), ResultCodes.REQ_CODE_NORMAL.getCode());
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg))
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

    }

    @Override
    public void getDataFromIntent() {
        try {
            oilRfnCd = getIntent().getStringExtra(OilCodes.KEY_OIL_CODE);
            rgstYn = getIntent().getStringExtra(OilCodes.KEY_OIL_RGSTYN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(oilRfnCd)) {
            exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodes.REQ_CODE_OIL_INTEGRATION_SUCCESS.getCode()) {
            exitPage(data, ResultCodes.REQ_CODE_NORMAL.getCode());
        }
    }


}
