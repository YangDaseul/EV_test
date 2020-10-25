package com.genesis.apps.ui.main.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.GNS_1006;
import com.genesis.apps.comm.model.gra.api.GNS_1008;
import com.genesis.apps.comm.model.gra.api.GNS_1009;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.util.FileUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityLeasingCarRegisterInput1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

/**
 * @author hjpark
 * @brief 렌트/리스 실 운행자 등록 (차대번호 입력)
 */
public class LeasingCarRegisterInputActivity extends SubActivity<ActivityLeasingCarRegisterInput1Binding> {

    private GNSViewModel gnsViewModel;

    private BottomListDialog bottomListDialog;
    private final int[] layouts = {R.layout.activity_leasing_car_register_input_1, R.layout.activity_leasing_car_register_input_2, R.layout.activity_leasing_car_register_input_3, R.layout.activity_leasing_car_register_input_4, R.layout.activity_leasing_car_register_input_5, R.layout.activity_leasing_car_register_input_6};
    private final int[] textMsgId = {R.string.gm_carlst_01_24, R.string.gm_carlst_01_29, R.string.gm_carlst_01_34, R.string.gm_carlst_01_39, R.string.gm_carlst_01_43, R.string.gm_carlst_01_46};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private String vin;
    private BtrVO btrVO=null;
    private AddressZipVO addressZipVO = null;
    //고객구분코드 1 법인 / 14 개인
    private String csmrScnCd;
    private String rentPeriod;

    private int targetImgId=0;
    private Uri cntImagPath;
    private Uri empCertImagPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(layouts[0]);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
//        pubViewModel.reqPUB1002(new PUB_1002.Request(APPIAInfo.GM_BT06_01.getId()));
    }

    private void initView() {
        initConstraintSets();
        ui.tvVin.setText(vin);

        ui.etRentPeriodEtc.setOnFocusChangeListener((view, hasFocus) -> {

            if (!hasFocus) {
                SoftKeyboardUtil.hideKeyboard(LeasingCarRegisterInputActivity.this);
            }else{
                SoftKeyboardUtil.showKeyboard(getApplicationContext());
            }

        });

        new Handler().postDelayed(() -> SnackBarUtil.show(LeasingCarRegisterInputActivity.this, getString(R.string.gm_carlst_01_snackbar_1)),2000);


//        ui.etVin.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                if(!TextUtils.isEmpty(editable.toString().trim())){
//                    ui.lVin.setError(null);
//                }
//
//            }
//        });

    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_next://다음
                if(isValid()){
                    //todo 신청하기 진행
                    gnsViewModel.reqGNS1006(new GNS_1006.Request(APPIAInfo.GM_CARLST_01_01.getId(),
                            vin,
                            csmrScnCd,
                            (rentPeriod.equalsIgnoreCase(VariableType.LEASING_CAR_PERIOD_ETC) ? getPeriod()+"" : rentPeriod),
                            "3",
                            addressZipVO.getZipNo(),
                            addressZipVO.getRoadAddr(),
                            ui.etAddrDetail.getText().toString().trim(),
                            "Y",
                            (csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14) ? "N" : "Y")));
                }
                break;

            case R.id.tv_csmr_scn_cd:
                final List<String> cdList = Arrays.asList(getResources().getStringArray(R.array.leasing_car_csmr_scn_cd));
                showMapDialog(cdList, R.string.gm_carlst_01_26, dialogInterface -> {
                    String result = bottomListDialog.getSelectItem();
                    if(!TextUtils.isEmpty(result)){
                        //TODO 삭제 요청

                        if(result.equalsIgnoreCase(cdList.get(0))){
                            csmrScnCd = VariableType.LEASING_CAR_CSMR_SCN_CD_14; //개인
                            if(views[4].getVisibility()==View.VISIBLE){
                                views[3].setVisibility(View.GONE);
                                targetImgId = R.id.btn_cnt_img;
                            }
                        }else{
                            csmrScnCd = VariableType.LEASING_CAR_CSMR_SCN_CD_1; //법인
                            if(views[4].getVisibility()==View.VISIBLE){
                                views[3].setVisibility(View.VISIBLE);
                                targetImgId = R.id.btn_emp_certi_img;
                            }
                        }

                        ui.tvTitleCsmrScnCd.setVisibility(View.VISIBLE);
                        ui.tvCsmrScnCd.setTextAppearance(R.style.TextViewCsmrScnCdEnable);
                        ui.tvCsmrScnCd.setText(result);

                        checkValidCsmrScnCd();

                    }
                });
                break;

            case R.id.tv_rent_period:


                final List<String> periodList = Arrays.asList(getResources().getStringArray(R.array.leasing_car_rent_period));
                showMapDialog(periodList, R.string.gm_carlst_01_57, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        String result = bottomListDialog.getSelectItem();
                        if(!TextUtils.isEmpty(result)){

                            if(result.equalsIgnoreCase(periodList.get(0))){
                                rentPeriod = VariableType.LEASING_CAR_PERIOD_12; //12개월
                            }else if(result.equalsIgnoreCase(periodList.get(1))){
                                rentPeriod = VariableType.LEASING_CAR_PERIOD_24; //24개월
                            }else if(result.equalsIgnoreCase(periodList.get(2))){
                                rentPeriod = VariableType.LEASING_CAR_PERIOD_36; //36개월
                            }else if(result.equalsIgnoreCase(periodList.get(3))){
                                rentPeriod = VariableType.LEASING_CAR_PERIOD_48; //48개월
                            }else if(result.equalsIgnoreCase(periodList.get(4))){
                                rentPeriod = VariableType.LEASING_CAR_PERIOD_ETC; //기타
                                ui.etRentPeriodEtc.setText("");
                            }

                            ui.tvRentPeriod.setTextAppearance(R.style.TextViewRentPeriodEnable);
                            ui.tvRentPeriod.setText(result);
                            ui.tvTitleRentPeriod.setVisibility(View.VISIBLE);


                            checkValidPeriod();
                        }
                    }
                });


                break;
            case R.id.btn_cnt_img:
            case R.id.btn_emp_certi_img:
                targetImgId = v.getId();
                CropImage.startPickImageActivity(this);
                break;
            case R.id.btn_btr:
            case R.id.tv_btr:
                startActivitySingleTop(new Intent(this, LeasingCarBtrChangeActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_post_no:
                startActivitySingleTop(new Intent(this, SearchAddressActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }

    }


    private void showMapDialog(List<String> list, int title, DialogInterface.OnDismissListener dismissListener) {
        bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dismissListener);
        bottomListDialog.setDatas(list);
        bottomListDialog.setTitle(getString(title));
        bottomListDialog.show();
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
    }

    @Override
    public void setObserver() {

        //렌트 리스 신청하기 결과
        gnsViewModel.getRES_GNS_1006().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
                        if(csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14)){
                            File file = new File(FileUtil.getRealPathFromURI(this, cntImagPath));
                            gnsViewModel.reqGNS1008(new GNS_1008.Request(APPIAInfo.GM_CARLST_01_01.getId(), vin, file.getName(), file ));
                        }else{
                            File file = new File(FileUtil.getRealPathFromURI(this, empCertImagPath));
                            gnsViewModel.reqGNS1009(new GNS_1009.Request(APPIAInfo.GM_CARLST_01_01.getId(), vin, file.getName(), file ));
                        }
                    }
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });

        //렌트 리스 재직증명서 등록 결과
        gnsViewModel.getRES_GNS_1009().observe(this, result -> {

            switch (result.status){
                case LOADING:

                    break;

                case SUCCESS:
                default:
                    File file = new File(FileUtil.getRealPathFromURI(this, cntImagPath));
                    gnsViewModel.reqGNS1008(new GNS_1008.Request(APPIAInfo.GM_CARLST_01_01.getId(), vin, file.getName(), file));

                    break;
            }

        });

        //렌트 리스 계약서 이미지 등록 결과
        gnsViewModel.getRES_GNS_1008().observe(this, result -> {

            switch (result.status){
                case LOADING:

                    break;
                case SUCCESS:
                default:
                    showProgressDialog(false);
                    startActivitySingleTop(new Intent(this, LeasingCarHistActivity.class).putExtra(KeyNames.KEY_NAME_APPLY_LEASINGCAR, true), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    finish();
                    break;
            }

        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
            if (TextUtils.isEmpty(vin)) {
                exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    private void initConstraintSets() {
        views = new View[]{ui.lCsmrScnCd, ui.lRentPeriod, ui.lCntImg, ui.lEmpCertiImg, ui.lBtr, ui.lCard};
        for (int i = 0; i < layouts.length; i++) {
            constraintSets[i] = new ConstraintSet();

            if (i == 0)
                constraintSets[i].clone(ui.container);
            else
                constraintSets[i].clone(this, layouts[i]);
        }
    }

    private void doTransition(int pos) {
        if (views[pos].getVisibility() == View.GONE) {
            Transition changeBounds = new ChangeBounds();
            changeBounds.setInterpolator(new OvershootInterpolator());
            TransitionManager.beginDelayedTransition(ui.container);
            constraintSets[pos].applyTo(ui.container);
            ui.tvMsg.setText(textMsgId[pos]);

            if(csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14)&&pos>3){
                views[3].setVisibility(View.GONE);
            }

        }
    }

    private boolean checkValidCsmrScnCd(){
        if(!TextUtils.isEmpty(csmrScnCd)){
            ui.tvErrorCsmrScnCd.setVisibility(View.GONE);
            doTransition(1);
            return true;
        }else{
            ui.tvErrorCsmrScnCd.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private boolean checkValidPeriod(){

        boolean isValid=false;

        //VIEW 초기화
        ui.tvErrorRentPeriod.setVisibility(View.GONE); //대여 기간 미선택 에러 GONE
        ui.lRentPeriodEtc.setError(null); //기타 기간에 대한 에러 제거 및 레이아웃 GONE
        ui.lRentPeriodEtc.setVisibility(View.GONE);


        if(TextUtils.isEmpty(rentPeriod)) {
            //대여 기간이 선택되지 않은 경우
            ui.tvErrorRentPeriod.setVisibility(View.VISIBLE);
        }else if(rentPeriod.equalsIgnoreCase(VariableType.LEASING_CAR_PERIOD_ETC)){
            ui.lRentPeriodEtc.setVisibility(View.VISIBLE);

            //대여 기간이 기타로 선택된 경우
            if(getPeriod()<12){//기간이 12개월 미만일 경우
                ui.lRentPeriodEtc.setError(getString(R.string.gm_carlst_01_33));
            }else {//정상적으로 입력됬을 경우
                ui.etRentPeriodEtc.clearFocus();
                doTransition(2);
                isValid=true;
            }
        }else{
            doTransition(2);
            isValid=true;
        }

        return isValid;
    }

    private boolean checkValidImg(){
        String mimeType;
        try {
            mimeType = getContentResolver().getType(targetImgId == R.id.btn_cnt_img ? cntImagPath : empCertImagPath);
        }catch (Exception e){
            mimeType="";
        }

        if (TextUtils.isEmpty(mimeType)) {
            //파일이 첨부되지 않은 경우
            switch (targetImgId) {
                case R.id.btn_emp_certi_img:
                    ui.tvEmpCertiImg.setTextAppearance(R.style.TextViewEmpCertiImgError2);
                    ui.tvErrorEmpCertiImg.setText(R.string.gm_carlst_01_42);
                    break;
                case R.id.btn_cnt_img:
                default: //아무 선택도 하지 않은 상태 0
                    ui.tvCntImg.setTextAppearance(R.style.TextViewCntImgError2);
                    ui.tvErrorCntImg.setText(R.string.gm_carlst_01_55);
                    break;
            }

            return false;
        } else if (!mimeType.contains("jpeg") && !mimeType.contains("pdf")) {

            switch (targetImgId) {
                case R.id.btn_cnt_img:
                    ui.tvCntImg.setTextAppearance(R.style.TextViewCntImgError);
                    ui.tvErrorCntImg.setText(R.string.gm_carlst_01_38);
                    break;
                case R.id.btn_emp_certi_img:
                    ui.tvEmpCertiImg.setTextAppearance(R.style.TextViewEmpCertiImgError);
                    ui.tvErrorEmpCertiImg.setText(R.string.gm_carlst_01_38);
                    break;
            }
            return false;
        } else {

            switch (targetImgId) {
                case R.id.btn_cnt_img:

                    if(csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_1)){
                        doTransition(3);
                        targetImgId = R.id.btn_emp_certi_img;
                    }else{
                        doTransition(4);
                    }

                    break;
                case R.id.btn_emp_certi_img:
                    doTransition(4);
                    break;
            }

            return true;
        }

    }

    private boolean checkValidBtr(){

        if(btrVO==null){
            ui.tvErrorBtr.setText(R.string.gm_carlst_01_58);
            return false;
        }else{
            ui.tvErrorBtr.setText("");
            ui.btnNext.setText(R.string.gm_carlst_04_20);
            doTransition(5);
            return true;
        }

    }

    private boolean checkValidAddr(){
        if(addressZipVO==null){
            ui.lAddrDetail.setError(getString(R.string.gm_carlst_01_59));
            return false;
        }else if(TextUtils.isEmpty(ui.etAddrDetail.getText().toString().trim())){
            ui.lAddrDetail.setError(getString(R.string.gm_carlst_01_60));
            return false;
        }else{
            ui.lAddrDetail.setError(null);
            return true;
        }
    }


    private final int TYPE_CSMR_SCN_CD=0;
    private final int TYPE_RENT_PERIOD=1;
    private final int TYPE_CNT_IMG=2;
    private final int TYPE_EMP_CERTI_IMG=3;
    private final int TYPE_BTR=4;
    private final int TYPE_CARD=5;
    private boolean isValid(){

        for(View view : views){
            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    //현재 페이지가 계약서 종류 입력하는 상태 인 경우
                    case R.id.l_rent_period:
                        return checkValidCsmrScnCd();
                    //현재 페이지가 렌트 기간 입력하는 상태 인 경우
                    case R.id.l_cnt_img:
                        return checkValidCsmrScnCd()&&checkValidPeriod();
                    //계약서 사진 입력하는 상태 인 경우
                    case R.id.l_emp_certi_img:
                        if(csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14))
                            break; //개인 인 경우는 체크하지 않는다.
                        else
                            return checkValidCsmrScnCd()&&checkValidPeriod()&&checkValidImg();
                    //재직증명서 입력하는 상태 인경우
                    case R.id.l_btr:
                        return checkValidCsmrScnCd()&&checkValidPeriod()&&checkValidImg();

                    case R.id.l_card:
                        return checkValidCsmrScnCd()&&checkValidPeriod()&&checkValidImg()&&checkValidBtr();
                }
            }
        }
        return checkValidAddr();
    }




//    private void doNext(){
//        String inputValue;
//
//        for(View view : views){
//
//            if(view.getVisibility()==View.GONE) {
//                switch (view.getId()) {
//                    //현재 페이지가 계약서 종류 입력하는 상태 인 경우
//                    case R.id.l_rent_period:
//                        inputValue = ui.tvCsmrScnCd.getText().toString();
//                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_25))){
//                            ui.tvErrorCsmrScnCd.setVisibility(View.GONE);
//                            doTransition(1);
//                        }else{
//                            ui.tvErrorCsmrScnCd.setVisibility(View.VISIBLE);
//                        }
//                        return;
//                    //현재 페이지가 렌트 기간 입력하는 상태 인 경우
//                    case R.id.l_cnt_img:
//                        inputValue = ui.tvRentPeriod.getText().toString();
//                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_30))&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_56))){
//                            //빈값이 아니고, 대여 기간이 아니고 기타가 아니면
//                            doTransition(2);
//                            ui.lRentPeriodEtc.setError(null);
//                        }else if(inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_56))){
//                            //대여기간이 기타 일 때
//                            if(getPeriod()<12){
//                                ui.lRentPeriodEtc.setError(getString(R.string.gm_carlst_01_33));
//                            }else{
//                                ui.lRentPeriodEtc.setError(null);
//                                doTransition(2);
//                            }
//                        }else{
//                            ui.tvErrorRentPeriod.setVisibility(View.VISIBLE);
//                        }
//                        return;
//
//                    case R.id.l_emp_certi_img:
//                        inputValue = ui.tvCntImg.getText().toString();
//                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_36))){
//                            doTransition(3);
//                            //todo tv_error_cnt_img gone 필요?
//                            //todo 이미지 용량 제한 처리 필요
//                        }else{
//                            ui.tvErrorCntImg.setVisibility(View.VISIBLE);
//                            ui.tvErrorCntImg.setText(R.string.gm_carlst_01_55);
//                        }
//                        return;
//
//
//                    case R.id.l_btr:
//                        inputValue = ui.tvEmpCertiImg.getText().toString();
//                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_41))){
//                            doTransition(4);
//                            //todo ttvErrorEmpCertiImg gone 필요?
//                            //todo 이미지 용량 제한 처리 필요
//                        }else{
//                            ui.tvErrorEmpCertiImg.setVisibility(View.VISIBLE);
//                            ui.tvErrorEmpCertiImg.setText(R.string.gm_carlst_01_42);
//                        }
//                        return;
//
//                    case R.id.l_card:
//                        if(btrVO!=null){
//                            doTransition(5);
//                            ui.btnNext.setText(R.string.gm_carlst_04_20);
//                        }else{
//                            SnackBarUtil.show(this, "버틀러를 선택해 주세요.");
//                        }
//                        return;
//                }
//            }
//        }
//
//
//        inputValue = ui.etAddrDetail.getText().toString();
//
//        if(addressZipVO==null){
//            SnackBarUtil.show(this, "수령지 주소를 입력해 주세요.");
//        }else if(TextUtils.isEmpty(inputValue)){
//            SnackBarUtil.show(this, "상세 주소를 입력해 주세요.");
//        }else{
//            //todo 신청하기 진행
//        }
//    }






    private int getPeriod(){
        int period = 0;
        try{
            period = Integer.parseInt(ui.etRentPeriodEtc.getText().toString());
        }catch (Exception e){
            period = 0;
        }

        return period;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE&&resultCode == RESULT_OK) {
            Uri resultUri = CropImage.getPickImageResultUri(this, data);
            setImgAttach(resultUri);
        }else if(resultCode == ResultCodes.REQ_CODE_BTR.getCode()){
            btrVO = (BtrVO)data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
            setBtrInfo();
        }else if(resultCode == ResultCodes.REQ_CODE_ADDR_ZIP.getCode()){
            addressZipVO = (AddressZipVO)data.getSerializableExtra(KeyNames.KEY_NAME_ZIP_ADDR);
            setAddressInfo();
        }
    }

    private void setImgAttach(Uri resultUri) {

        switch (targetImgId){
            case R.id.btn_cnt_img:
                cntImagPath = resultUri;
                ui.tvCntImg.setTextAppearance(R.style.TextViewCntImgEnable);

                ui.tvCntImg.setText(new File(FileUtil.getRealPathFromURI(this, cntImagPath)).getName());
                ui.tvErrorCntImg.setText("");
                break;
            case R.id.btn_emp_certi_img:
                empCertImagPath = resultUri;
                ui.tvEmpCertiImg.setTextAppearance(R.style.TextViewEmpCertiImgEnable);
                ui.tvEmpCertiImg.setText(new File(FileUtil.getRealPathFromURI(this, empCertImagPath)).getName());
                ui.tvErrorEmpCertiImg.setText("");
                break;
        }

        checkValidImg();

    }

    private void setBtrInfo(){

        if(btrVO==null){
            ui.tvBtr.setVisibility(View.VISIBLE);
            ui.lBtrInfo.setVisibility(View.GONE);
        }else{
            ui.tvBtr.setVisibility(View.GONE);
            ui.lBtrInfo.setVisibility(View.VISIBLE);
            ui.tvBtrAsnm.setText(btrVO.getAsnNm());
            ui.tvBtrAddr.setText(btrVO.getPbzAdr());
            ui.tvBtrReptn.setText(PhoneNumberUtils.formatNumber(btrVO.getRepTn(), Locale.getDefault().getCountry()));
        }

        checkValidBtr();
    }


    private void setAddressInfo(){

        if(addressZipVO==null){
            ui.tvPostNo.setTextAppearance(R.style.TextViewPostNo);
            ui.tvAddr.setTextAppearance(R.style.TextViewAddr);
        }else{
            ui.tvPostNo.setTextAppearance(R.style.TextViewPostNoEnable);
            ui.tvAddr.setTextAppearance(R.style.TextViewAddrEnable);
            ui.tvPostNo.setText(addressZipVO.getZipNo());
            ui.tvAddr.setText(addressZipVO.getRoadAddr());
        }

        checkValidAddr();
    }


}
