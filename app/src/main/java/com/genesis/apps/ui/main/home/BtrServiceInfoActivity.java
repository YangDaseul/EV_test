package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.BTR_1010;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.ui.common.activity.HtmlActivity;

public class BtrServiceInfoActivity extends HtmlActivity {

    private BTRViewModel btrViewModel;
    private String annMgmtCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btrViewModel.reqBTR1010(new BTR_1010.Request(APPIAInfo.GM_BT01_P01.getId(),annMgmtCd));
    }

    @Override
    public void getDataFromIntent() {
        try {
            annMgmtCd = getIntent().getStringExtra(KeyNames.KEY_NAME_ADMIN_CODE);
            if (TextUtils.isEmpty(annMgmtCd)) {
                exitPage("안내 관리 코드가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitPage("안내 관리 코드가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
    }

    @Override
    public void setObserver() {
        btrViewModel.getRES_BTR_1010().observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    if (result.data != null) {
                        loadTerms(result.data.getCont());
                        showProgressDialog(false);
                        return;
                    }
                case LOADING:
                    showProgressDialog(true);
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });

    }

    private void initView() {
        ui.setValue(getString(R.string.gm_bt01_p01_1));
        setTopView(R.layout.layout_btr_service_info);
    }


    @Override
    public void onClickCommon(View v) {

    }

}