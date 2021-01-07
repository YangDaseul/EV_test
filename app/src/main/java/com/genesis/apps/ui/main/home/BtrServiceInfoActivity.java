package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BTR_1010;
import com.genesis.apps.comm.util.SnackBarUtil;
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
        btrViewModel.reqBTR1010(new BTR_1010.Request(APPIAInfo.GM_BT01_P01.getId(),annMgmtCd));
    }

    @Override
    public void getDataFromIntent() {
        try {
            annMgmtCd = getIntent().getStringExtra(KeyNames.KEY_NAME_ADMIN_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (TextUtils.isEmpty(annMgmtCd)) {
                exitPage("안내 관리 코드가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
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
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null&&!TextUtils.isEmpty(result.data.getCont())) {
                        loadTerms(result.data.getCont());
                        showProgressDialog(false);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

    }

    private void initView() {
//        ui.setValue(getString(R.string.gm_bt01_p01_1));
        setTopView(R.layout.layout_btr_service_info);
    }


    @Override
    public void onClickCommon(View v) {

    }

}
