package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1004;
import com.genesis.apps.comm.model.api.gra.SOS_1005;
import com.genesis.apps.comm.model.api.gra.SOS_3001;
import com.genesis.apps.comm.model.api.gra.SOS_3004;
import com.genesis.apps.comm.model.api.gra.SOS_3005;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.SOSStateVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityServiceChargeApplyInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

/**
 * @author hjpark
 * @brief 찾아가는 충전 서비스 접수내역
 */
public class ServiceChargeApplyInfoActivity extends SubActivity<ActivityServiceChargeApplyInfoBinding> {
    private SOSViewModel sosViewModel;
    private SOSStateVO sosStateVO;
    private String tmpAcptNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_charge_apply_info);
        getDataFromIntent();
        setViewModel();
        setObserver();
        sosViewModel.reqSOS3005(new SOS_3005.Request(APPIAInfo.SM_EMGC02.getId(), tmpAcptNo));
    }

    private void initView() {
        ui.setData(sosStateVO);
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_cancel:
                MiddleDialog.dialogServiceSOSApplyCancel(this, () -> sosViewModel.reqSOS3004(new SOS_3004.Request(APPIAInfo.SM_EMGC02.getId(), tmpAcptNo)),
                        () -> {

                        });
                break;
            case R.id.btn_confirm:
                exitPage(new Intent(),0);
                //TODO 코드와 인텐트 데이터 확인 필요
                break;

        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
    }

    @Override
    public void setObserver() {

//        sosViewModel.getRES_SOS_3001().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                  showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                   showProgressDialog(false);
//                    tmpAcptNo = result.data.getTmpAcptNo();
//                    if(result.data!=null&&!TextUtils.isEmpty(tmpAcptNo)){
//                        sosViewModel.reqSOS1005(new SOS_1005.Request(APPIAInfo.SM_EMGC02.getId(), tmpAcptNo));
//                        break;
//                    }
//                default:
//                    showProgressDialog(false);
//                    if (TextUtils.isEmpty(tmpAcptNo)) {
//                        exitPage("가접수번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
//                    }
//                    break;
//            }
//        });

        sosViewModel.getRES_SOS_3005().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getSosStateVO()!=null){
                        sosStateVO = result.data.getSosStateVO();
                        initView();
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


        sosViewModel.getRES_SOS_3004().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        if(result.data.getSuccYn().equalsIgnoreCase("Y")){
                            exitPage(getString(R.string.sm_cggo_01_13), ResultCodes.REQ_CODE_NORMAL.getCode());
                        }else{
                            SnackBarUtil.show(this, getString(R.string.r_flaw06_p02_snackbar_1)+(TextUtils.isEmpty(result.data.getFailMsg()) ? "" : "\n"+result.data.getFailMsg()));
                        }
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
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg));
                    }
                    break;
            }
        });



    }

    @Override
    public void getDataFromIntent() {
        try {
            tmpAcptNo = getIntent().getStringExtra(KeyNames.KEY_NAME_SOS_TMP_NO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
