package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.OIL_0004;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.ui.common.activity.TermActivity;

public class MyGOilTermDetailActivity extends TermActivity {
    public static final String TERMS_CODE="termVO";
    public static final String OIL_CODE="oilRfnCd";
    private String oilRfnCd;
    private TermVO termVO;
    private MYPViewModel mypViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui.setValue(termVO.getTermNm());
        getDataFromIntent();
        setViewModel();
        setObserver();
        mypViewModel.reqOIL0004(new OIL_0004.Request(APPIAInfo.MG_CON02_02.getId(), oilRfnCd,termVO.getTermVer(),termVO.getTermCd()));

//            loadTerms(new TermVO("",TERMS_6000,getString(R.string.title_terms_6),getStringFromAssetsFile(),""));
    }

    @Override
    public void getDataFromIntent() {
        try {
            oilRfnCd = getIntent().getStringExtra(OIL_CODE);
            termVO = (TermVO)getIntent().getSerializableExtra(TERMS_CODE);
            if (termVO == null
                    || TextUtils.isEmpty(termVO.getTermCd())
                    || TextUtils.isEmpty(termVO.getTermVer())
                    || TextUtils.isEmpty(oilRfnCd)) {
                exitPage("약관정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitPage("약관정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel= new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_OIL_0004().observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    if (result.data != null) {
                        loadTerms(result.data.getTermVO());
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
}
