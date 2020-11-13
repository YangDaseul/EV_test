package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_0001;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.ActivityMygGaBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.gson.Gson;

import androidx.lifecycle.ViewModelProvider;

public class MyGGAActivity extends SubActivity<ActivityMygGaBinding> {

    private MYPViewModel mypViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_ga);
        getDataFromIntent();
        setViewModel();
        setObserver();
        mypViewModel.reqMYP0001(new MYP_0001.Request(APPIAInfo.MG_GA01.getId()));
        //TODO 처리 필요
        ui.setActivity(this);
        ui.cbEmail.setOnCheckedChangeListener(listener);
        ui.cbPhone.setOnCheckedChangeListener(listener);
        ui.cbSms.setOnCheckedChangeListener(listener);
        ui.cbPost.setOnCheckedChangeListener(listener);
        ui.cbAd.setOnCheckedChangeListener(listenerAll);
        ui.vBlock.setOnTouchListener((view, motionEvent) -> true);
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_0001().observe(this, result -> {
            String test ="{\n" +
                    "  \"rtCd\": \"0000\",\n" +
                    "  \"rtMsg\": \"Success\",\n" +
                    "  \"mbrStustCd\": \"1000\",\n" +
                    "  \"ccspEmail\": \"kim.genesis@email.com\",\n" +
                    "  \"mbrNm\": \"김수현\",\n" +
                    "  \"brtdy\": \"19800102\",\n" +
                    "  \"sexDiv\": \"M\",\n" +
                    "  \"celphNo\": \"01099990001\",\n" +
                    "  \"mrktYn\": \"Y\",\n" +
                    "  \"mrktDate\": \"20200824155819\",\n" +
                    "  \"mrktCd\": \"1111\"\n" +
                    "}";
            MYP_0001.Response sample = new Gson().fromJson(test, MYP_0001.Response.class);
            if(sample!=null) {
                ui.setData(sample);
                ui.cbAd.setChecked(sample.getMrktYn().equalsIgnoreCase("Y") ? true : false);
                ui.cbSms.setChecked(sample.getMrktCd().substring(0,1).equalsIgnoreCase("1") ? true : false);
                ui.cbEmail.setChecked(sample.getMrktCd().substring(1,2).equalsIgnoreCase("1") ? true : false);
                ui.cbPost.setChecked(sample.getMrktCd().substring(2,3).equalsIgnoreCase("1") ? true : false);
                ui.cbPhone.setChecked(sample.getMrktCd().substring(3,4).equalsIgnoreCase("1") ? true : false);
            }


            switch (result.status){
                case SUCCESS:
                    break;
                case LOADING:

                    break;
                case ERROR:

                    break;
            }


        });
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.iv_arrow:
                if(ui.tvAdInfo.getVisibility()==View.VISIBLE){
                    ui.ivArrow.setImageResource(R.drawable.btn_accodian_open);
                    InteractionUtil.collapse(ui.tvAdInfo, null);
                }else{
                    ui.ivArrow.setImageResource(R.drawable.btn_accodian_close);
                    InteractionUtil.expand(ui.tvAdInfo, null);
                }
                break;

//            case R.id.cb_email:
//            case R.id.cb_sms:
//            case R.id.cb_phone:
//                setAllCheckBox();
//                break;
//            case R.id.cb_all:
//
//                Log.v("checkbox","checkbox:"+ui.cbAll.isChecked());
//                break;
        }
    }


    CompoundButton.OnCheckedChangeListener listener = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            ui.cbAd.setChecked(ui.cbEmail.isChecked()|ui.cbPhone.isChecked()|ui.cbSms.isChecked()|ui.cbPost.isChecked());
        }
    };

    CompoundButton.OnCheckedChangeListener listenerAll = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            ui.cbEmail.setChecked(b);
            ui.cbPhone.setChecked(b);
            ui.cbSms.setChecked(b);
            ui.cbPost.setChecked(b);
        }
        ui.vBlock.setVisibility(b ? View.GONE : View.VISIBLE);
    };

}
