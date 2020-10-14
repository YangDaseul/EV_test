package com.genesis.apps.ui.main.home;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.databinding.ActivityBtrConsultApplyBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class BtrConsultApplyActivity extends SubActivity<ActivityBtrConsultApplyBinding> {

    private BTRViewModel btrViewModel;
    private List<String> selectCdValId;



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
            selectCdValId = new Gson().fromJson(getIntent().getStringExtra(KeyNames.KEY_NAME_BTR_CNSL_LIST), new TypeToken<List<String>>(){}.getType());
            if (selectCdValId.size()!=4) {
                exitPage("문의 유형 정보가 존재하지 않습니다.\n문의를 다시 신청해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
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

    }

    private void initView() {
    }


    @Override
    public void onClickCommon(View v) {

    }

}
