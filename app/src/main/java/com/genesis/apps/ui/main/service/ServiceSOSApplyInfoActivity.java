package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.GNS_1012;
import com.genesis.apps.comm.model.gra.api.GNS_1013;
import com.genesis.apps.comm.model.gra.api.GNS_1014;
import com.genesis.apps.comm.model.gra.api.GNS_1015;
import com.genesis.apps.comm.model.gra.api.SOS_1004;
import com.genesis.apps.comm.model.gra.api.SOS_1005;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.genesis.apps.comm.model.vo.RentStatusVO;
import com.genesis.apps.comm.model.vo.SOSStateVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityLeasingCarHistDetailBinding;
import com.genesis.apps.databinding.ActivityServiceSosApplyInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.home.LeasingCarVinRegisterActivity;
import com.genesis.apps.ui.main.home.SearchAddressActivity;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_SOS_AREA_CLS_CODE_H;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_SOS_AREA_CLS_CODE_R;

/**
 * @author hjpark
 * @brief 긴급출동서비스 접수내역
 */
public class ServiceSOSApplyInfoActivity extends SubActivity<ActivityServiceSosApplyInfoBinding> {
    private SOSViewModel sosViewModel;
    private SOSStateVO sosStateVO;
    private String tmpAcptNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_sos_apply_info);
        getDataFromIntent();
        setViewModel();
        setObserver();
        sosViewModel.reqSOS1005(new SOS_1005.Request(APPIAInfo.SM_EMGC02.getId(), tmpAcptNo));
    }

    private void initView() {
        ui.tvAreaClsCd.setText(sosStateVO.getAreaClsCd().equalsIgnoreCase("R") ? SERVICE_SOS_AREA_CLS_CODE_R : SERVICE_SOS_AREA_CLS_CODE_H);
        ui.tvAddr.setText(sosStateVO.getAddr());
        ui.tvCarRegNo.setText(sosStateVO.getCarRegNo());
        ui.tvMemo.setText(sosStateVO.getMemo());
        ui.tvTmpAcptDtm.setText((!TextUtils.isEmpty(sosStateVO.getTmpAcptDtm()) ? (DateUtil.getDate(DateUtil.getDefaultDateFormat(getYyyyMMddHHmmss(sosStateVO.getTmpAcptDtm()), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm)) : "--"));
    }

    private String getYyyyMMddHHmmss(String tmpAcptDtm){

        while(tmpAcptDtm.length()<14){
            tmpAcptDtm+="0";
        }

        return tmpAcptDtm;
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_cancel:
                MiddleDialog.dialogServiceSOSApplyCancel(this, () -> sosViewModel.reqSOS1004(new SOS_1004.Request(APPIAInfo.SM_EMGC02.getId(), tmpAcptNo)),
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

        sosViewModel.getRES_SOS_1005().observe(this, result -> {
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


        sosViewModel.getRES_SOS_1004().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        if(result.data.getSuccYn().equalsIgnoreCase("Y")){
                            exitPage(getString(R.string.sm_emgc02_p01_snackbar_2), ResultCodes.REQ_CODE_NORMAL.getCode());
                        }else{
                            SnackBarUtil.show(this, getString(R.string.sm_emgc02_p01_snackbar_1)+(TextUtils.isEmpty(result.data.getFailMsg()) ? "" : "\n"+result.data.getFailMsg()));
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
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg) ? getString(R.string.sm_emgc02_p01_snackbar_1) : serverMsg));
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
        }finally {
            if (TextUtils.isEmpty(tmpAcptNo)){
                exitPage("가접수번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//       if(resultCode == ResultCodes.REQ_CODE_ADDR_ZIP.getCode()){
//           newAddressZipVO = (AddressZipVO)data.getSerializableExtra(KeyNames.KEY_NAME_ZIP_ADDR);
//           setAddressInfo();
//        }
    }


}
