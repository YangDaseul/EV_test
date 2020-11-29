package com.genesis.apps.ui.main.home;

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
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.GNS_1012;
import com.genesis.apps.comm.model.api.gra.GNS_1013;
import com.genesis.apps.comm.model.api.gra.GNS_1014;
import com.genesis.apps.comm.model.api.gra.GNS_1015;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.genesis.apps.comm.model.vo.RentStatusVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityLeasingCarHistDetailBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author hjpark
 */
public class LeasingCarHistDetailActivity extends SubActivity<ActivityLeasingCarHistDetailBinding> {
    private GNSViewModel gnsViewModel;
    private RentStatusVO rentStatusVO;
    private AddressZipVO newAddressZipVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(R.layout.activity_leasing_car_hist_detail);
        getDataFromIntent();
        setViewModel();
        setObserver();
        gnsViewModel.reqGNS1012(new GNS_1012.Request(APPIAInfo.GM_CARLST_01_result.getId(), rentStatusVO.getVin(), rentStatusVO.getSeqNo()));
    }

    private void initView() {

        ui.tvCsmrScnCd.setText(rentStatusVO.equals(VariableType.LEASING_CAR_CSMR_SCN_CD_14) ? R.string.gm_carlst_01_62 : R.string.gm_carlst_01_61);
        ui.tvVin.setText(rentStatusVO.getVin());
        ui.tvRentPeriod.setText(rentStatusVO.getRentPeriod()+getString(R.string.gm_carlst_01_63));
        ui.tvAttachFileName.setText(rentStatusVO.getAttachFilName());
        ui.tvBluehandsInfo.setText(rentStatusVO.getAsnNm()+"\n"+rentStatusVO.getPbzAdr()+(rentStatusVO.getRepTn()!=null ? ("\n"+(PhoneNumberUtils.formatNumber(rentStatusVO.getRepTn(), Locale.getDefault().getCountry()))):""));


        switch (rentStatusVO.getAprvStusCd()){
            case VariableType.LEASING_CAR_APRV_STATUS_CODE_REJECT:
                ui.ivMark.setImageResource(R.drawable.ic_fail);
                ui.tvMsg.setText(getString(R.string.gm_carlst_02_18)+"\n"+getString(R.string.gm_carlst_02_19));
                ui.tvAddrInfo.setVisibility(View.VISIBLE);
                ui.tvAddrInfo.setText(rentStatusVO.getCrdRcvZip()+"\n"+rentStatusVO.getCrdRcvAdr()+(TextUtils.isEmpty(rentStatusVO.getCrdRcvDtlAdr()) ? "" : ("\n"+rentStatusVO.getCrdRcvDtlAdr())));
                ui.lAddr.setVisibility(View.GONE);

                ui.btnCancel.setVisibility(View.INVISIBLE);
                ui.btnModify.setVisibility(View.INVISIBLE);
                ui.btnApply.setVisibility(View.VISIBLE);
                break;
            case VariableType.LEASING_CAR_APRV_STATUS_CODE_WAIT:
            default:
                ui.ivMark.setImageResource(R.drawable.ic_succeed);//todo 수정필요
                ui.tvMsg.setText(getString(R.string.gm_carlst_02_18)+"\n"+getString(R.string.gm_carlst_02_27));
                ui.tvAddrInfo.setVisibility(View.GONE);
                ui.lAddr.setVisibility(View.VISIBLE);
                ui.tvAddr.setText(rentStatusVO.getCrdRcvAdr());
                ui.tvPostNo.setText(rentStatusVO.getCrdRcvZip());
                ui.etAddrDetail.setText(rentStatusVO.getCrdRcvDtlAdr());
                ui.tvPostNo.setTextAppearance(R.style.TextViewPostNoEnable);
                ui.tvAddr.setTextAppearance(R.style.TextViewAddrEnable);
                ui.btnCancel.setVisibility(View.VISIBLE);
                ui.btnModify.setVisibility(View.VISIBLE);
                ui.btnApply.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_post_no:
                startActivitySingleTop(new Intent(this, SearchAddressActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            case R.id.btn_modify:
                if(newAddressZipVO!=null||!ui.etAddrDetail.getText().toString().trim().equalsIgnoreCase(rentStatusVO.getCrdRcvDtlAdr())){

                    String crdRcvZip = newAddressZipVO!=null ? newAddressZipVO.getZipNo() : rentStatusVO.getCrdRcvZip();
                    String crdRcvAdr = newAddressZipVO!=null ? newAddressZipVO.getRoadAddr() : rentStatusVO.getCrdRcvAdr();
                    String crdRcvDtlAdr = ui.etAddrDetail.getText().toString().trim();
                    //TODO 수정 전문 요청 진행
                    gnsViewModel.reqGNS1013(new GNS_1013.Request(APPIAInfo.GM_CARLST_01_result.getId(), rentStatusVO.getVin(), rentStatusVO.getSeqNo(),"3",crdRcvZip, crdRcvAdr, crdRcvDtlAdr));
                }else{
                    SnackBarUtil.show(this, "수정할 주소 정보가 없습니다.\n확인 후 다시 시도해 주세요.");
                }
                break;

            case R.id.btn_cancel:
                MiddleDialog.dialogLeasingCarApplyCancel(this, () -> gnsViewModel.reqGNS1014(new GNS_1014.Request(APPIAInfo.GM_CARLST_01_result.getId(), rentStatusVO.getVin(), rentStatusVO.getSeqNo())),
                        () -> {

                        });

                break;
                
            case R.id.btn_apply:
                gnsViewModel.reqGNS1015(new GNS_1015.Request(APPIAInfo.GM_CARLST_01_result.getId(), rentStatusVO.getVin(), rentStatusVO.getSeqNo()));
                break;

        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
    }

    @Override
    public void setObserver() {
        gnsViewModel.getRES_GNS_1012().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRentStatusVO()!=null){
                        String rentPeriod=rentStatusVO.getRentPeriod();
                        rentStatusVO = result.data.getRentStatusVO();
                        rentStatusVO.setRentPeriod(rentPeriod);
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

        gnsViewModel.getRES_GNS_1013().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        String crdRcvZip = newAddressZipVO!=null ? newAddressZipVO.getZipNo() : rentStatusVO.getCrdRcvZip();
                        String crdRcvAdr = newAddressZipVO!=null ? newAddressZipVO.getRoadAddr() : rentStatusVO.getCrdRcvAdr();
                        rentStatusVO.setCrdRcvAdr(crdRcvAdr);
                        rentStatusVO.setCrdRcvDtlAdr(ui.etAddrDetail.getText().toString().trim());
                        rentStatusVO.setCrdRcvZip(crdRcvZip);
                        newAddressZipVO=null;
                        SnackBarUtil.show(this, getString(R.string.gm_carlist_01_p05_snackbar_2));
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

        //신청취소결과
        gnsViewModel.getRES_GNS_1014().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        exitPage(getString(R.string.gm_carlist_01_p05_snackbar_1), ResultCodes.REQ_CODE_LEASING_CAR_CANCEL.getCode());
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


        //재신청
        gnsViewModel.getRES_GNS_1015().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        startActivitySingleTop(new Intent(this, LeasingCarVinRegisterActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        exitPage("", ResultCodes.REQ_CODE_LEASING_CAR_RE_APPLY.getCode());
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

    @Override
    public void getDataFromIntent() {

        try {
            rentStatusVO = (RentStatusVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_DATA_LEASINGCAR);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (rentStatusVO==null){
                exitPage("리스 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(resultCode == ResultCodes.REQ_CODE_ADDR_ZIP.getCode()){
           newAddressZipVO = (AddressZipVO)data.getSerializableExtra(KeyNames.KEY_NAME_ZIP_ADDR);
           setAddressInfo();
        }
    }

    private void setAddressInfo(){
        if(newAddressZipVO==null){
            ui.tvPostNo.setTextAppearance(R.style.TextViewPostNo);
            ui.tvAddr.setTextAppearance(R.style.TextViewAddr);
        }else{
            ui.tvPostNo.setTextAppearance(R.style.TextViewPostNoEnable);
            ui.tvAddr.setTextAppearance(R.style.TextViewAddrEnable);
            ui.tvPostNo.setText(newAddressZipVO.getZipNo());
            ui.tvAddr.setText(newAddressZipVO.getRoadAddr());
        }
    }

}
