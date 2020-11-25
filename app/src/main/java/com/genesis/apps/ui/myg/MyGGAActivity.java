package com.genesis.apps.ui.myg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_0001;
import com.genesis.apps.comm.model.api.gra.MYP_0004;
import com.genesis.apps.comm.model.api.gra.MYP_0005;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.comm.util.InteractionUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.ActivityMygGaBinding;
import com.genesis.apps.ui.common.activity.LoginActivity;
import com.genesis.apps.ui.common.activity.SubActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyGGAActivity extends SubActivity<ActivityMygGaBinding> {
    @Inject
    public GA ga;

    private MYPViewModel mypViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_ga);
        getDataFromIntent();
        setViewModel();
        setObserver();
        ui.setActivity(this);
        ui.cbEmail.setOnCheckedChangeListener(listener);
        ui.cbPhone.setOnCheckedChangeListener(listener);
        ui.cbSms.setOnCheckedChangeListener(listener);
        ui.cbPost.setOnCheckedChangeListener(listener);
        ui.cbAd.setOnCheckedChangeListener(listenerAll);
        ui.vBlock.setOnTouchListener((view, motionEvent) -> true);
        mypViewModel.reqMYP0001(new MYP_0001.Request(APPIAInfo.MG_GA01.getId()));
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_0001().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null){
                        ui.setData(result.data);
                        ui.cbAd.setChecked(result.data.getMrktYn().equalsIgnoreCase("Y") ? true : false);
                        ui.cbSms.setChecked(result.data.getMrktCd().substring(0,1).equalsIgnoreCase("1") ? true : false);
                        ui.cbEmail.setChecked(result.data.getMrktCd().substring(1,2).equalsIgnoreCase("1") ? true : false);
                        ui.cbPost.setChecked(result.data.getMrktCd().substring(2,3).equalsIgnoreCase("1") ? true : false);
                        ui.cbPhone.setChecked(result.data.getMrktCd().substring(3,4).equalsIgnoreCase("1") ? true : false);
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
                        exitPage(serverMsg , ResultCodes.RES_CODE_NETWORK.getCode());
                    }
                    break;
            }
        });


        mypViewModel.getRES_MYP_0004().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null){
                        SnackBarUtil.show(this, getString(R.string.snackbar_etc_1));
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
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);

                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

        mypViewModel.getRES_MYP_0005().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null){
                        SnackBarUtil.show(this, getString(R.string.snackbar_etc_2));
                        mypViewModel.reqMYP0001(new MYP_0001.Request(APPIAInfo.MG_GA01.getId()));
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
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
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
            case R.id.btn_save_agree:
                String mrktYn = ui.cbAd.isChecked() ? "Y" : "N";
                String mrktCd = (ui.cbSms.isChecked() ? "1" : "0")
                            + (ui.cbEmail.isChecked() ? "1" : "0")
                             + (ui.cbPost.isChecked() ? "1" : "0")
                            + (ui.cbPhone.isChecked() ? "1" : "0");
                mypViewModel.reqMYP0004(new MYP_0004.Request(APPIAInfo.MG_GA01.getId(), mrktYn, mrktCd));
                break;
            case R.id.btn_change_phone:
                startActivitySingleTop(new Intent(this, LoginActivity.class).putExtra(KeyNames.KEY_NAME_URL, ga.getAuthUrl()).putExtra(KeyNames.KEY_NAME_AUTHUUID, KeyNames.KEY_NAME_AUTHUUID), RequestCodes.REQ_CODE_AUTHUUID.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCodes.REQ_CODE_AUTHUUID.getCode()) {
            String authUuid = "";
            try {
                authUuid = data.getStringExtra(VariableType.KEY_NAME_LOGIN_AUTH_UUID);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (!TextUtils.isEmpty(authUuid))
                    mypViewModel.reqMYP0005(new MYP_0005.Request(APPIAInfo.MG_GA01.getId(), authUuid));
                else
                    SnackBarUtil.show(this, "본인인증이 정상적으로 진행되지 않았습니다.\n잠시 후 다시 시도해 주세요.");
            }
        }
    }

}
