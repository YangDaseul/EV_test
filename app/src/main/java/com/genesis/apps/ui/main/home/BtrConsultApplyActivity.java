package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.BTR_2002;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.databinding.ActivityBtrConsultApplyBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import androidx.lifecycle.ViewModelProvider;

public class BtrConsultApplyActivity extends SubActivity<ActivityBtrConsultApplyBinding> {

    private BTRViewModel btrViewModel;
    private List<String> selectCdValId;
    private final String REQUEST_CALL="[전화응답요청]";
    private final String REQUEST_APP="[APP응답요청]";
    private String vin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btr_consult_apply);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void getDataFromIntent() {
        try {
            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
            selectCdValId = new Gson().fromJson(getIntent().getStringExtra(KeyNames.KEY_NAME_BTR_CNSL_LIST), new TypeToken<List<String>>(){}.getType());
            if (selectCdValId.size()!=4) {
                exitPage("문의 유형 정보가 존재하지 않습니다.\n문의를 다시 신청해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
            if (TextUtils.isEmpty(vin)) {
                exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitPage("문의 유형 정보가 존재하지 않습니다.\n문의를 다시 신청해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
    }

    @Override
    public void setObserver() {

        btrViewModel.getRES_BTR_2002().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
                        exitPage(getString(R.string.gm_bt04_snackbar_1), ResultCodes.REQ_CODE_NORMAL.getCode());
                    }else{
                        SnackBarUtil.show(this, getString(R.string.gm_bt04_snackbar_2));
                    }
                    break;
                case ERROR:
                    showProgressDialog(false);
                    SnackBarUtil.show(this, getString(R.string.gm_bt04_snackbar_2));
                    break;
            }

        });

    }


    private void initView() {

        ui.etSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    ui.lSubject.setError(null);
                }
            }
        });

        ui.etContents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    ui.lContents.setError(null);
                }
            }
        });

    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.btn_apply:
                if (isValid()) {
                    String conslCont = (ui.cbCall.isChecked() ? REQUEST_CALL : REQUEST_APP) + ui.etContents.getText().toString();
                    btrViewModel.reqBTR2002(new BTR_2002.Request(APPIAInfo.GM_BT04.getId(), vin, selectCdValId.get(0), selectCdValId.get(1), selectCdValId.get(2), selectCdValId.get(3), ui.etSubject.getText().toString(), conslCont));
                }
                break;
        }
    }

    private boolean isValid(){
        boolean isValid=true;
        if(TextUtils.isEmpty(ui.etSubject.getText().toString().trim())){
            ui.lSubject.setError(getString(R.string.gm_bt04_15));
            isValid=false;
        }
        if(TextUtils.isEmpty(ui.etContents.getText().toString().trim())){
            ui.lContents.setError(getString(R.string.gm_bt04_16));
            isValid=false;
        }
        return isValid;
    }

}
