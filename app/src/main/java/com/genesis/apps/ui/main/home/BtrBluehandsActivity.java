package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BTR_1001;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.BTRViewModel;
import com.genesis.apps.databinding.ActivityBtrBluehandsBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.ServiceNetworkActivity;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class BtrBluehandsActivity extends GpsBaseActivity<ActivityBtrBluehandsBinding> {

    private BTRViewModel btrViewModel;
    private String vin;
    private BtrVO btrVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btr_bluehands);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
        btrViewModel.reqBTR1001(new BTR_1001.Request(APPIAInfo.GM_BT02.getId(),vin));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void getDataFromIntent() {
        try {
            vin = btrViewModel.getMainVehicleSimplyFromDB().getVin();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (TextUtils.isEmpty(vin)) {
                exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
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
        btrViewModel.getRES_BTR_1001().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")&&result.data.getBtrVO()!=null){
                        try {
                            btrVO = ((BtrVO)result.data.getBtrVO().clone());
                        }catch (Exception e){

                        }finally {
                            if(btrVO!=null) {
                                setViewBtrInfo();
                                showProgressDialog(false);
                                break;
                            }
                        }
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        exitPage((TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg, ResultCodes.RES_CODE_NETWORK.getCode());
//                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

    }

    private void initView() {
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.l_asan://내위치확인
                if(!isGpsEnable()) {
                    MiddleDialog.dialogGPS(this, () -> turnGPSOn(isGPSEnable -> {
                    }), () -> {
                        //TODO 확인 클릭
                    });
                }else{
                    startActivitySingleTop(new Intent(this, BtrBluehandsMapActivity.class).putExtra(KeyNames.KEY_NAME_BTR, btrVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
            case R.id.btn_change://변경하기

                if(!isGpsEnable()) {
                    MiddleDialog.dialogGPS(this, () -> turnGPSOn(isGPSEnable -> {
                    }), () -> {
                    });
                }else{
                    startActivitySingleTop(new Intent(this, ServiceNetworkActivity.class).putExtra(KeyNames.KEY_NAME_BTR, btrVO).putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_BTR), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
            case R.id.btn_cnsl_list://상담이력
                startActivitySingleTop(new Intent(this, BtrConslHistActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_cnsl://1:!문의하기
                startActivitySingleTop(new Intent(this, BtrConsultTypeActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_call://통화하기
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + btrVO.getCelphNo())));
                break;
        }
    }

    private void setViewBtrInfo(){
        if(btrVO!=null) {
            ui.tvAsnnm.setText(btrVO.getAsnNm());
            ui.tvAddr.setText(btrVO.getPbzAdr());
            ui.tvReptn.setText(PhoneNumberUtils.formatNumber(btrVO.getRepTn(), Locale.getDefault().getCountry()));

            ui.tvName.setText(btrVO.getBtlrNm());
            ui.tvPhone.setText(PhoneNumberUtils.formatNumber(btrVO.getCelphNo(), Locale.getDefault().getCountry()));

            if (btrVO.getBltrChgYn().equalsIgnoreCase(VariableType.BTR_CHANGE_REQUEST_YES)) {
                ui.tvInfo.setVisibility(View.VISIBLE);
                ui.lBtrMenu.setVisibility(View.GONE);
                ui.btnChange.setVisibility(View.INVISIBLE);
            } else {
                ui.tvInfo.setVisibility(View.GONE);
                ui.lBtrMenu.setVisibility(View.VISIBLE);
                ui.btnChange.setVisibility(View.VISIBLE);
            }

            ui.ivBadge.setVisibility(btrVO.getCnsltBdgYn().equalsIgnoreCase(VariableType.BTR_CNSL_BADGE_YES) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == ResultCodes.REQ_CODE_BTR.getCode()){
            BtrVO btrVO = (BtrVO)data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
            if(btrVO!=null) {//버틀러 정보는 받지만 실제  블루핸즈 4개 정보만있고 버틀러 정보는 빠저있어서 재요청해야함;
                btrViewModel.reqBTR1001(new BTR_1001.Request(APPIAInfo.GM_BT02.getId(),vin));
                SnackBarUtil.show(this, getString(R.string.gm_bt06_snackbar_1));
            }
        }
    }
}
