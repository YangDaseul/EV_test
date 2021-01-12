package com.genesis.apps.ui.main.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.GNS_1016;
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
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityLeasingCarHistDetailBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.List;
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
    private AddressZipVO privilegeAddressZipVO = null;
    private BottomListDialog bottomListDialog;
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
        ui.setData(rentStatusVO);

        switch (StringUtil.isValidString(rentStatusVO.getAprvStusCd())){
            case VariableType.LEASING_CAR_APRV_STATUS_CODE_REJECT:
                ui.ivMark.setImageResource(R.drawable.ic_fail);
                ui.tvMsg.setText(getString(R.string.gm_carlst_02_18)+"\n"+getString(R.string.gm_carlst_02_19));
                ui.tvAddrInfo.setVisibility(View.VISIBLE);
                ui.tvAddrInfo.setText(rentStatusVO.getCrdRcvZip()+"\n"+rentStatusVO.getCrdRcvAdr()+(TextUtils.isEmpty(rentStatusVO.getCrdRcvDtlAdr()) ? "" : ("\n"+rentStatusVO.getCrdRcvDtlAdr())));

                ui.lAddr.setVisibility(View.GONE);

                ui.btnCancel.setVisibility(View.INVISIBLE);
                ui.btnModify.setVisibility(View.INVISIBLE);
                ui.btnApply.setVisibility(View.VISIBLE);

                ui.tvSubTitle2.setVisibility(View.VISIBLE);
                ui.tvSubTitle3.setVisibility(View.GONE);
                break;
            case VariableType.LEASING_CAR_APRV_STATUS_CODE_WAIT:
            default:
                ui.ivMark.setImageResource(R.drawable.ic_succeed);
                ui.tvMsg.setText(getString(R.string.gm_carlst_02_18)+"\n"+getString(R.string.gm_carlst_02_27));
                ui.tvAddrInfo.setVisibility(View.GONE);
                ui.lAddr.setVisibility(View.VISIBLE);
                ui.tvAddr.setText(rentStatusVO.getCrdRcvAdr());
                ui.tvPostNo.setText(rentStatusVO.getCrdRcvZip());
                ui.etAddrDetail.setText(rentStatusVO.getCrdRcvDtlAdr());
                ui.btnCancel.setVisibility(View.VISIBLE);
                ui.btnModify.setVisibility(View.VISIBLE);
                ui.btnApply.setVisibility(View.INVISIBLE);
                ui.tvSubTitle2.setVisibility(View.GONE);
                ui.tvSubTitle3.setVisibility(View.VISIBLE);
                Paris.style(ui.tvPostNo).apply(R.style.TextViewPostNoEnable);
                Paris.style(ui.tvAddr).apply(R.style.TextViewAddrEnable);
                break;
        }
    }

    private void selectPostNo(boolean isPrivilege) {
        clearKeypad();
        startActivitySingleTop(new Intent(this, SearchAddressActivity.class)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.gm_carlst_02_31)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.gm_carlst_02_32)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_PRIVILEGE, isPrivilege)
                ,RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.tv_privilege_service:
                gnsViewModel.reqGNS1016(new GNS_1016.Request(APPIAInfo.GM_CARLST_01_result.getId(), rentStatusVO.getVin(), rentStatusVO.getSeqNo()));
                break;
            case R.id.btn_privilege_post_no:
                selectPostNo(true);
                break;
            case R.id.btn_post_no:
                selectPostNo(false);
                break;

            case R.id.btn_modify:
//                if(newAddressZipVO!=null||!ui.etAddrDetail.getText().toString().trim().equalsIgnoreCase(rentStatusVO.getCrdRcvDtlAdr())){

                    String crdRcvZip = newAddressZipVO!=null ? newAddressZipVO.getZipNo() : rentStatusVO.getCrdRcvZip();
                    String crdRcvAdr = newAddressZipVO!=null ? newAddressZipVO.getRoadAddr() : rentStatusVO.getCrdRcvAdr();
                    String crdRcvDtlAdr = ui.etAddrDetail.getText().toString().trim();

                    String godsRcvZip = privilegeAddressZipVO!=null ? privilegeAddressZipVO.getZipNo() : rentStatusVO.getGodsRcvZip();
                    String godsRcvAdr = privilegeAddressZipVO!=null ? privilegeAddressZipVO.getRoadAddr() : rentStatusVO.getGodsRcvAdr();
                    String godsRcvDtlAdr = ui.etPrivilegeAddrDetail.getText().toString().trim();
                    String tel = ui.etPrivilegeTel.getText().toString().trim().replaceAll("-","");

                    gnsViewModel.reqGNS1013(new GNS_1013.Request(APPIAInfo.GM_CARLST_01_result.getId(), rentStatusVO.getVin(), rentStatusVO.getSeqNo(),"3",crdRcvZip, crdRcvAdr, crdRcvDtlAdr,
                            rentStatusVO.getMdlNm(), rentStatusVO.getGodsId(),rentStatusVO.getGodsNm(),godsRcvZip,godsRcvAdr,godsRcvDtlAdr,tel));
//                }else{
//                    SnackBarUtil.show(this, "수정할 주소 정보가 없습니다.\n확인 후 다시 시도해 주세요.");
//                }
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
                    if(result.data!=null&& StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("0000")&&result.data.getRentStatusVO()!=null){
                        String rentPeriod=rentStatusVO.getRentPeriod();
                        rentStatusVO = result.data.getRentStatusVO();
                        rentStatusVO.setRentPeriod(rentPeriod);
                        initView();
                        showProgressDialog(false);
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


        gnsViewModel.getRES_GNS_1016().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase("0000")&&result.data.getGodsList()!=null&&result.data.getGodsList().size()>0){
                        selectPrivilegeService();
                        showProgressDialog(false);
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
        }else if (resultCode == ResultCodes.REQ_CODE_ADDR_ZIP_PRIVILEGE.getCode()) {
           privilegeAddressZipVO = (AddressZipVO) data.getSerializableExtra(KeyNames.KEY_NAME_ZIP_ADDR);
           setPrivilegeAddressInfo();
       }
    }

    private void setAddressInfo(){
        if(newAddressZipVO==null){
            Paris.style(ui.tvPostNo).apply(R.style.TextViewPostNo);
            Paris.style(ui.tvAddr).apply(R.style.TextViewAddr);
        }else{
            Paris.style(ui.tvPostNo).apply(R.style.TextViewPostNoEnable);
            Paris.style(ui.tvAddr).apply(R.style.TextViewAddrEnable);
            ui.tvPostNo.setText(newAddressZipVO.getZipNo());
            ui.tvAddr.setText(newAddressZipVO.getRoadAddr());
        }
    }


    private void setPrivilegeAddressInfo() {
        if (privilegeAddressZipVO == null) {
            Paris.style(ui.tvPrivilegeAddr).apply(R.style.TextViewAddr);
            Paris.style(ui.tvPrivilegePostNo).apply(R.style.TextViewPostNo);
        } else {
            Paris.style(ui.tvPrivilegeAddr).apply(R.style.TextViewAddrEnable);
            Paris.style(ui.tvPrivilegePostNo).apply(R.style.TextViewPostNoEnable);
            ui.tvPrivilegePostNo.setText(privilegeAddressZipVO.getZipNo());
            ui.tvPrivilegeAddr.setText(privilegeAddressZipVO.getRoadAddr());
        }
        if (TextUtils.isEmpty(ui.etPrivilegeAddrDetail.getText().toString().trim())) {
            ui.etPrivilegeAddrDetail.requestFocus();
        }
    }

    private void selectPrivilegeService() {
        clearKeypad();
        final List<String> periodList = gnsViewModel.getGodsNmList(gnsViewModel.getRES_GNS_1016().getValue().data.getGodsList());
        showMapDialog(periodList, R.string.gm_carlst_01_01_17, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                String result = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(result)) {
                    RentStatusVO selectPrivilege = gnsViewModel.getGodsByNm(result,gnsViewModel.getRES_GNS_1016().getValue().data.getGodsList());
                    if(selectPrivilege!=null) {
                        rentStatusVO.setGodsId(selectPrivilege.getGodsId());
                        rentStatusVO.setGodsNm(selectPrivilege.getGodsNm());
                        rentStatusVO.setAdrYn(selectPrivilege.getAdrYn());

                        ui.setData(rentStatusVO);
                        Paris.style(ui.tvPrivilegeService).apply(R.style.CommonSpinnerItemEnable);
                        ui.tvPrivilegeService.setText(result);
                    }
//                    checkValidPrivilege(true);
                }
            }
        });
    }

    public boolean isPrivilege() {
        String mdlNm="";
        try {
             mdlNm = rentStatusVO.getMdlNm();
        }catch (Exception e){

        }
        return StringUtil.isValidString(mdlNm).equalsIgnoreCase("G80")||StringUtil.isValidString(mdlNm).equalsIgnoreCase("G90");
    }


    private void clearKeypad() {
        View[] edits = {ui.etAddrDetail, ui.etPrivilegeAddrDetail, ui.etPrivilegeTel};
        for (View view : edits) {
            view.clearFocus();
        }
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
    }

    private void showMapDialog(List<String> list, int title, DialogInterface.OnDismissListener dismissListener) {
        bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dismissListener);
        bottomListDialog.setDatas(list);
        bottomListDialog.setTitle(getString(title));
        bottomListDialog.show();
    }
}
