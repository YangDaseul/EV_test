package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1004;
import com.genesis.apps.comm.model.api.gra.SOS_1005;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.SOSStateVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityServiceSosApplyInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

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
        if(TextUtils.isEmpty(tmpAcptNo)){//가접수번호가 없는 경우 (서비스 메인에서 바로 접수내역 화면으로 이동 시)
            sosViewModel.reqSOS1001(new SOS_1001.Request(APPIAInfo.SM_EMGC02.getId()));
        }else{//가접수번호가 있는 경우 (신청 후)
            sosViewModel.reqSOS1005(new SOS_1005.Request(APPIAInfo.SM_EMGC02.getId(), tmpAcptNo));
        }
    }

    private void initView() {
        ui.setData(sosStateVO);
//        ui.tvAreaClsCd.setText(sosStateVO.getAreaClsCd().equalsIgnoreCase("R") ? SERVICE_SOS_AREA_CLS_CODE_R : SERVICE_SOS_AREA_CLS_CODE_H);
//        ui.tvAddr.setText(sosStateVO.getAddr());
//        ui.tvCarRegNo.setText(sosStateVO.getCarRegNo());
//        ui.tvMemo.setText(sosStateVO.getMemo());
//        ui.tvTmpAcptDtm.setText((!TextUtils.isEmpty(sosStateVO.getTmpAcptDtm()) ? (DateUtil.getDate(DateUtil.getDefaultDateFormat(DateUtil.getYyyyMMddHHmmss(sosStateVO.getTmpAcptDtm()), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_hh_mm)) : "--"));
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

        sosViewModel.getRES_SOS_1001().observe(this, result -> {
            switch (result.status){
                case LOADING:
                  showProgressDialog(true);
                    break;
                case SUCCESS:
                   showProgressDialog(false);
                    if(result.data!=null&& StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        tmpAcptNo = result.data.getTmpAcptNo();
                        if (!TextUtils.isEmpty(tmpAcptNo)) {
                            sosViewModel.reqSOS1005(new SOS_1005.Request(APPIAInfo.SM_EMGC02.getId(), tmpAcptNo));
                            break;
                        }
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        exitPage(TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg, ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                    }
                    break;
            }
        });

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
