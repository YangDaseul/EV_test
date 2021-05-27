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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.GNS_1006;
import com.genesis.apps.comm.model.api.gra.GNS_1008;
import com.genesis.apps.comm.model.api.gra.GNS_1011;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.RentStatusVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.FileUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityLeasingCarRegisterInput1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.ServiceNetworkActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;


/**
 * @author hjpark
 * @brief 렌트/리스 실운행자 등록 (차대번호 입력)
 */
@AndroidEntryPoint
public class LeasingCarRegisterInputActivity extends SubActivity<ActivityLeasingCarRegisterInput1Binding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private GNSViewModel gnsViewModel;

    private BottomListDialog bottomListDialog;
    private final int[] layouts = {R.layout.activity_leasing_car_register_input_1, R.layout.activity_leasing_car_register_input_3, R.layout.activity_leasing_car_register_input_4, R.layout.activity_leasing_car_register_input_5, R.layout.activity_leasing_car_register_input_6, R.layout.activity_leasing_car_register_input_7};
    private int[] textMsgId = {R.string.gm_carlst_01_29, R.string.gm_carlst_01_34, R.string.gm_carlst_01_39, R.string.gm_carlst_01_43, R.string.gm_carlst_01_46, R.string.gm_carlst_01_01_10};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private String vin;
    private BtrVO btrVO = null;
    private AddressZipVO addressZipVO = null;
    private AddressZipVO privilegeAddressZipVO = null;
    //고객구분코드 1 법인 / 14 개인
    private String csmrScnCd;
    private String rentPeriod;
    private RentStatusVO selectPrivilege = null;
    private GNS_1011.Response gns1011Response = null;


    private int targetImgId = 0;
    private Uri cntImagPath;
    private Uri empCertImagPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(layouts[0]);
        getDataFromIntent();
    }

    private void initView() {
        ui.sc.setNestedScrollingEnabled(false);
        initConstraintSets();
        initPhoneNumber();
        ui.tvVin.setText(vin);
        setViewCsmrScnCd();
        ui.etRentPeriodEtc.setOnFocusChangeListener(focusChangeListener);
        ui.etAddrDetail.setOnFocusChangeListener(focusChangeListener);
        ui.lPrivilege.etAddrDetail.setOnFocusChangeListener(focusChangeListener);
        ui.lPrivilege.etTel.setOnFocusChangeListener(focusChangeListener);

        ui.etRentPeriodEtc.setOnEditorActionListener(editorActionListener);
        ui.etAddrDetail.setOnEditorActionListener(editorActionListener);
        ui.lPrivilege.etAddrDetail.setOnEditorActionListener(editorActionListener);
        ui.lPrivilege.etTel.setOnEditorActionListener(editorActionListener);

        ui.lPrivilege.cbAddr.setOnCheckedChangeListener((compoundButton, b) -> {

            if (compoundButton.isPressed() && b) {
                if (addressZipVO != null) {
                    try {
                        privilegeAddressZipVO = ((AddressZipVO) addressZipVO.clone());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (privilegeAddressZipVO != null) {
                            setPrivilegeAddressInfo();
                        }
                        String addrDtl = ui.etAddrDetail.getText().toString().trim();
                        if (!TextUtils.isEmpty(addrDtl)) {
                            ui.lPrivilege.etAddrDetail.setText(addrDtl);
                            clearKeypad();
                        }
                    }
                }
            }

        });
        new Handler().postDelayed(() -> {
            SnackBarUtil.show(LeasingCarRegisterInputActivity.this, getString(R.string.gm_carlst_01_snackbar_1));
            selectRentPeriod();
        }, 100);
    }

    private void setViewCsmrScnCd() {
        if(StringUtil.isValidString(csmrScnCd).equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14)){
            //개인
            ui.tvCsmrScnCd.setText(R.string.gm_carlst_01_62);
            textMsgId[2] = R.string.gm_carlst_01_65;
            ui.tvTitleEmpCertiImg.setText(R.string.gm_carlst_01_66);
            Paris.style(ui.tvEmpCertiImg).apply(R.style.TextViewEmpCertiImgDisable2);
        }else{
            //법인
            ui.tvCsmrScnCd.setText(R.string.gm_carlst_01_61);
            textMsgId[2] = R.string.gm_carlst_01_39;
            ui.tvTitleEmpCertiImg.setText(R.string.gm_carlst_01_40);
            Paris.style(ui.tvEmpCertiImg).apply(R.style.TextViewEmpCertiImgDisable);

        }
    }

    private void initPhoneNumber() {
        String phoneNumber = loginInfoDTO.getProfile() != null ? loginInfoDTO.getProfile().getMobileNum() : "";

        if (!TextUtils.isEmpty(phoneNumber)) {
            ui.lPrivilege.etTel.setText(
                    StringUtil.parsingPhoneNumber(
                            PhoneNumberUtils.formatNumber(
                                    phoneNumber.replaceAll("-", ""), Locale.getDefault().getCountry()
                            )));
        }
    }

    private void clearKeypad() {
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
        View[] edits = {ui.etRentPeriodEtc, ui.etAddrDetail, ui.lPrivilege.etAddrDetail, ui.lPrivilege.etTel};
        for (View view : edits) {
            view.clearFocus();
        }
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.tv_privilege_service: //프리빌리지 서비스
                selectPrivilegeService();
                break;
            case R.id.btn_privilege_info://프리빌리지 서비스 안내
                //외부 브라우저로 오픈
                if (gns1011Response != null && !TextUtils.isEmpty(gns1011Response.getSvcIntroUri())) {
                    moveToExternalPage(gns1011Response.getSvcIntroUri(), VariableType.COMMON_MEANS_NO);
                }
                break;
            case R.id.btn_next://다음
                doNext();
                break;
            case R.id.tv_rent_period://대여 기간 선택
                selectRentPeriod();
                break;
            case R.id.btn_cnt_img:
            case R.id.btn_emp_certi_img://계약서 이미지 첨부
                clearKeypad();
                targetImgId = v.getId();
                CropImage.startPickImageActivity(this);
                break;
            case R.id.btn_btr:
            case R.id.tv_btr:
                selectBtrInfo();
                break;
            case R.id.btn_post_no:
                String tag;
                try {
                    tag = v.getTag().toString();
                } catch (Exception e) {
                    tag = "";
                }
                selectPostNo(StringUtil.isValidString(tag).equalsIgnoreCase("privilege"));
                break;
            case R.id.btn_info:
                startActivitySingleTop(new Intent(this, LeasingCarInfoActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
        }

    }

    private void doNext() {
        if (isValid()) {
            clearKeypad();
            gnsViewModel.reqGNS1006(new GNS_1006.Request(APPIAInfo.GM_CARLST_01_01.getId(),
                    vin,
                    csmrScnCd,
                    (rentPeriod.equalsIgnoreCase(VariableType.LEASING_CAR_PERIOD_ETC) ? getPeriod() + "" : rentPeriod),
                    btrVO.getAsnCd(),
                    "3",
                    addressZipVO.getZipNo(),
                    addressZipVO.getRoadAddr(),
                    ui.etAddrDetail.getText().toString().trim(),
                    "Y",
                    empCertImagPath==null ? "N" : "Y",
                    StringUtil.isValidString(gns1011Response.getCtrctNo()),
                    selectPrivilege !=null ? StringUtil.isValidString(selectPrivilege.getGodsId()) : "",
                    selectPrivilege !=null ? StringUtil.isValidString(selectPrivilege.getGodsNm()) : "",
                    privilegeAddressZipVO !=null ? StringUtil.isValidString(privilegeAddressZipVO.getZipNo()) : "",
                    privilegeAddressZipVO !=null ? StringUtil.isValidString(privilegeAddressZipVO.getRoadAddr()) : "",
                    ui.lPrivilege.etAddrDetail.getText().toString().trim(),
                    ui.lPrivilege.etTel.getText().toString().trim().replaceAll("-", "")));
        }
    }

    private void selectBtrInfo() {
        clearKeypad();
        startActivitySingleTop(new Intent(this, ServiceNetworkActivity.class).putExtra(KeyNames.KEY_NAME_PAGE_TYPE, ServiceNetworkActivity.PAGE_TYPE_RENT), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    private void selectPostNo(boolean isPrivilege) {
        clearKeypad();
        startActivitySingleTop(new Intent(this, SearchAddressActivity.class)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.gm_carlst_02_31)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.gm_carlst_02_32)
                        .putExtra(KeyNames.KEY_NAME_MAP_SEARCH_PRIVILEGE, isPrivilege)
                , RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    private void selectRentPeriod() {
        clearKeypad();
        final List<String> periodList = Arrays.asList(getResources().getStringArray(R.array.leasing_car_rent_period));
        showMapDialog(periodList, R.string.gm_carlst_01_57, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                String result = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(result)) {
                    ui.lRentPeriodEtc.setVisibility(View.GONE);
                    if (result.equalsIgnoreCase(periodList.get(0))) {
                        rentPeriod = VariableType.LEASING_CAR_PERIOD_12; //12개월
                    } else if (result.equalsIgnoreCase(periodList.get(1))) {
                        rentPeriod = VariableType.LEASING_CAR_PERIOD_24; //24개월
                    } else if (result.equalsIgnoreCase(periodList.get(2))) {
                        rentPeriod = VariableType.LEASING_CAR_PERIOD_36; //36개월
                    } else if (result.equalsIgnoreCase(periodList.get(3))) {
                        rentPeriod = VariableType.LEASING_CAR_PERIOD_48; //48개월
                    } else if (result.equalsIgnoreCase(periodList.get(4))) {
                        rentPeriod = VariableType.LEASING_CAR_PERIOD_ETC; //기타
                        ui.lRentPeriodEtc.setVisibility(View.VISIBLE);
                        ui.etRentPeriodEtc.setText("");
                    }

                    Paris.style(ui.tvRentPeriod).apply(R.style.TextViewRentPeriodEnable);
                    ui.tvRentPeriod.setText(result);
                    ui.tvTitleRentPeriod.setVisibility(View.VISIBLE);

                    if(rentPeriod==VariableType.LEASING_CAR_PERIOD_ETC) {
                        ui.etRentPeriodEtc.requestFocus();
                    }else{
                        checkValidPeriod();
                    }
                }
            }
        });
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
        try {
            gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
            ui.setLifecycleOwner(this);
            ui.setActivity(this);
            ui.lPrivilege.setMdlNm(gns1011Response.getMdlNm());
            ui.lPrivilege.setListener(onSingleClickListener);
        } catch (Exception e) {

        }
    }

    @Override
    public void setObserver() {

        //렌트 리스 신청 대상 확인 결과
        gnsViewModel.getRES_GNS_1006().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        reqUploadImageCnt();
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

//        //렌트 리스 재직증명서 등록 결과 (결과에 성관없이 무조건 내역으,로 이도ㅓㅇ)
//        gnsViewModel.getRES_GNS_1009().observe(this, result -> {
//            switch (result.status) {
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                default:
//                    showProgressDialog(false);
//                    moveToHist();
//                    break;
//            }
//        });

        //렌트 리스 계약서 이미지 등록 결과
        gnsViewModel.getRES_GNS_1008().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        moveToHist();
//                        if (empCertImagPath==null) {
//                            //추가 증빙 서류가 없을 경우
//                            moveToHist();
//                        } else {
//                            //법인 : 재직증명서, 개인 : 추가 증빙 서류가 있을 경우
//                            reqUploadImageCert();
//                        }
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });
    }

    /**
     * @biref 계약서&재직증명서 or 추가증빙서류 이미지 업로드
     */
    private void reqUploadImageCnt() {
        File cntImag = null;
        File empCertImag = null;
        cntImag = new File(Objects.requireNonNull(FileUtil.getRealPathFromURI(this, cntImagPath)));
        if(empCertImagPath!=null) empCertImag = new File(Objects.requireNonNull(FileUtil.getRealPathFromURI(this, empCertImagPath)));
        if (cntImag.length() > 0)
            gnsViewModel.reqGNS1008(new GNS_1008.Request(APPIAInfo.GM_CARLST_01_01.getId(), vin, cntImag.getName(), cntImag, empCertImag!=null&&empCertImag.length() > 0 ? empCertImag.getName() : "", empCertImag!=null&&empCertImag.length() > 0 ? empCertImag : null));
    }

//    /**
//     * @brief 재직증명서 이미지 업로드
//     * 계약서 이미지 업로드 먼저 진쟁하지 않고 시도하면 에러 발생
//     */
//    private void reqUploadImageCert() {
//        File file = new File(Objects.requireNonNull(FileUtil.getRealPathFromURI(this, empCertImagPath)));
//        if (file.length() > 0)
//            gnsViewModel.reqGNS1009(new GNS_1009.Request(APPIAInfo.GM_CARLST_01_01.getId(), vin, file.getName(), file));
//    }

    private void moveToHist() {
        startActivitySingleTop(new Intent(this, LeasingCarHistActivity.class).putExtra(KeyNames.KEY_NAME_APPLY_LEASINGCAR, true), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        finish();
    }

    @Override
    public void getDataFromIntent() {
        try {
            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
            csmrScnCd = getIntent().getStringExtra(KeyNames.KEY_NAME_CSMR_SCN_CD);
            gns1011Response = (GNS_1011.Response) getIntent().getSerializableExtra(KeyNames.KEY_NAME_GNS_1001_RESPONSE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(vin) || TextUtils.isEmpty(csmrScnCd) || gns1011Response == null) {
                exitPage("차량 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }else{
                setViewModel();
                setObserver();
                initView();
            }
        }
    }

    private void initConstraintSets() {
        views = new View[]{ui.lRentPeriod, ui.lCntImg, ui.lEmpCertiImg, ui.lBtr, ui.lCard, ui.clPrivilege};
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

//            if (csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14) && pos > 2) {
//                views[2].setVisibility(View.GONE);
//            }

            if (pos == 1) {
                targetImgId = R.id.btn_cnt_img;
                CropImage.startPickImageActivity(this);
            } else if (pos == 2) {
                targetImgId = R.id.btn_emp_certi_img;
                CropImage.startPickImageActivity(this);
            } else if (pos == 4) {
                if (!isPrivilege()) {
                    ui.btnNext.setText(R.string.gm_carlst_04_20);
                }
            } else if (pos == 5) {
                selectPrivilegeService();
                ui.btnNext.setText(R.string.gm_carlst_04_20);
            }
        }
    }

    private boolean isPrivilege() {
        return StringUtil.isValidString(gns1011Response.getMdlNm()).equalsIgnoreCase("G80") || StringUtil.isValidString(gns1011Response.getMdlNm()).equalsIgnoreCase("G90");
    }

    private boolean checkValidPeriod() {

        boolean isValid = false;

        //VIEW 초기화
        ui.tvErrorRentPeriod.setVisibility(View.GONE); //대여 기간 미선택 에러 GONE
        ui.lRentPeriodEtc.setError(null); //기타 기간에 대한 에러 제거 및 레이아웃 GONE

        if (TextUtils.isEmpty(rentPeriod)) {
            //대여 기간이 선택되지 않은 경우
            ui.tvErrorRentPeriod.setVisibility(View.VISIBLE);
        } else if (rentPeriod.equalsIgnoreCase(VariableType.LEASING_CAR_PERIOD_ETC)) {
            //대여 기간이 기타로 선택된 경우
            if (getPeriod() < 12) {//기간이 12개월 미만일 경우
                ui.lRentPeriodEtc.setError(getString(R.string.gm_carlst_01_33));
            } else {//정상적으로 입력됬을 경우
                ui.etRentPeriodEtc.clearFocus();
                doTransition(1);
                isValid = true;
            }
        } else {
            doTransition(1);
            isValid = true;
        }

        return isValid;
    }

    private final int FILE_LENGTH_UNIT = 1024;
    private final int LIMIT_FILE_SIZE_KB = 5120;

    private boolean checkValidImg() {
        String mimeType;
        File file = null;
        try {
            mimeType = getContentResolver().getType(targetImgId == R.id.btn_cnt_img ? cntImagPath : empCertImagPath);
            file = new File(FileUtil.getRealPathFromURI(this, targetImgId == R.id.btn_cnt_img ? cntImagPath : empCertImagPath));
        } catch (Exception e) {
            mimeType = "";
        }

        if(targetImgId==R.id.btn_emp_certi_img&&StringUtil.isValidString(csmrScnCd).equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14)){
            //개인인데 추가증빙서류 선택 인경우
            doTransition(3);
            return true;
        }else if (TextUtils.isEmpty(mimeType)) {
            //파일이 첨부되지 않은 경우
            switch (targetImgId) {
                case R.id.btn_emp_certi_img:
                    Paris.style(ui.tvEmpCertiImg).apply(R.style.TextViewEmpCertiImgError2);
                    ui.tvErrorEmpCertiImg.setText(!StringUtil.isValidString(csmrScnCd).equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14) ? R.string.gm_carlst_01_42 : R.string.gm_carlst_01_68);
                    break;
                case R.id.btn_cnt_img:
                default: //아무 선택도 하지 않은 상태
                    Paris.style(ui.tvCntImg).apply(R.style.TextViewCntImgError2);
                    ui.tvErrorCntImg.setText(R.string.gm_carlst_01_55);
                    break;
            }

            return false;
        } else if (!mimeType.contains("jpeg") && !mimeType.contains("pdf")
                || (file != null && file.length() / FILE_LENGTH_UNIT > LIMIT_FILE_SIZE_KB)) {
            switch (targetImgId) {
                case R.id.btn_cnt_img:
                    Paris.style(ui.tvCntImg).apply(R.style.TextViewCntImgError);
                    ui.tvErrorCntImg.setText(R.string.gm_carlst_01_38);
                    break;
                case R.id.btn_emp_certi_img:
                    Paris.style(ui.tvEmpCertiImg).apply(R.style.TextViewEmpCertiImgError);
                    ui.tvErrorEmpCertiImg.setText(R.string.gm_carlst_01_38);
                    break;
            }
            return false;
        } else {

            switch (targetImgId) {
                case R.id.btn_cnt_img:
                    doTransition(2);
                    targetImgId = R.id.btn_emp_certi_img;
                    break;
                case R.id.btn_emp_certi_img:
                    doTransition(3);
                    break;
            }

            return true;
        }

    }

    private boolean checkValidBtr() {

        if (btrVO == null) {
            ui.tvErrorBtr.setText(R.string.gm_carlst_01_58);
            return false;
        } else {
            ui.tvErrorBtr.setText("");
            doTransition(4);
            return true;
        }

    }

    private boolean checkValidAddr() {
        if (addressZipVO == null) {
            ui.lAddrDetail.setError(getString(R.string.gm_carlst_01_59));
            return false;
        } else if (TextUtils.isEmpty(ui.etAddrDetail.getText().toString().trim())) {
            ui.lAddrDetail.setError(getString(R.string.gm_carlst_01_60));
            return false;
        } else {
            ui.lAddrDetail.setError(null);
            if (isPrivilege())
                doTransition(5);
            return true;
        }
    }

    private boolean checkValidPrivilege(boolean isFirst) {
        if (selectPrivilege == null) {
            ui.lPrivilege.tvErrorPrivilegeService.setVisibility(View.VISIBLE);
            return false;
        } else {
            ui.lPrivilege.tvErrorPrivilegeService.setVisibility(View.INVISIBLE);
            //프리빌리지 서비스가 선택된 상태
            if (StringUtil.isValidString(selectPrivilege.getAdrYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
                //주소 입력창이 활성화되는 아이템 일 경우
                if (privilegeAddressZipVO == null) {
                    if (!isFirst)
                        ui.lPrivilege.lAddrDetail.setError(getString(R.string.gm_carlst_01_59));
                    return false;
                } else if (TextUtils.isEmpty(ui.lPrivilege.etAddrDetail.getText().toString().trim())) {
                    ui.lPrivilege.lAddrDetail.setError(getString(R.string.gm_carlst_01_60));
                    return false;
                } else {
                    ui.lPrivilege.lAddrDetail.setError(null);
                    ui.lPrivilege.etAddrDetail.clearFocus();
                    return checkValidPhoneNumber();
                }
            } else {
                //주소 입력창이 활성화 필요없는 아이템일 경우
                return true;
            }
        }
    }

    private boolean checkValidPhoneNumber() {
        String celPhoneNo = ui.lPrivilege.etTel.getText().toString().replaceAll("-", "").trim();

        if (TextUtils.isEmpty(celPhoneNo)) {
            ui.lPrivilege.etTel.requestFocus();
            ui.lPrivilege.lTel.setError(getString(R.string.sm_emgc01_5));
            return false;
        } else if (!StringRe2j.matches(celPhoneNo, getString(R.string.check_phone_number))) {
            ui.lPrivilege.etTel.requestFocus();
            ui.lPrivilege.lTel.setError(getString(R.string.sm_emgc01_26));
            return false;
        } else {
            ui.lPrivilege.etTel.setText(PhoneNumberUtils.formatNumber(celPhoneNo, Locale.getDefault().getCountry()));
            ui.lPrivilege.etTel.setSelection(ui.lPrivilege.etTel.length());
            ui.lPrivilege.etTel.clearFocus();
            ui.lPrivilege.lTel.setError(null);
            return true;
        }
    }


    private final int TYPE_CSMR_SCN_CD = 0;
    private final int TYPE_RENT_PERIOD = 1;
    private final int TYPE_CNT_IMG = 2;
    private final int TYPE_EMP_CERTI_IMG = 3;
    private final int TYPE_BTR = 4;
    private final int TYPE_CARD = 5;

    private boolean isValid() {

        for (View view : views) {
            if (view.getVisibility() == View.GONE) {
                switch (view.getId()) {
                    //현재 페이지가 렌트 기간 입력하는 상태 인 경우
                    case R.id.l_cnt_img:
                        return checkValidPeriod() & false;
                    //계약서 사진 입력하는 상태 인 경우
                    case R.id.l_emp_certi_img:
                        if (csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14))
                            break; //개인 인 경우는 체크하지 않는다.
                        else
                            return checkValidPeriod() && checkValidImg() & false;
                        //재직증명서 입력하는 상태 인경우
                    case R.id.l_btr:
                        return checkValidPeriod() && checkValidImg() & false;

                    case R.id.l_card:
                        return checkValidPeriod() && checkValidImg() && checkValidBtr() & false;

                    case R.id.cl_privilege:
                        if (isPrivilege()) {
                            return checkValidPeriod() && checkValidImg() && checkValidBtr() & checkValidAddr() & false;
                        } else {
                            return checkValidPeriod() && checkValidImg() && checkValidBtr() & checkValidAddr();
                        }

                }
            }
        }
        return checkValidPeriod() && checkValidImg() && checkValidBtr() & checkValidAddr() & checkValidPrivilege(false);
    }

    private int getPeriod() {
        int period = 0;
        try {
            period = Integer.parseInt(ui.etRentPeriodEtc.getText().toString());
        } catch (Exception e) {
            period = 0;
        }

        return period;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri resultUri = CropImage.getPickImageResultUri(this, data);
            setImgAttach(resultUri);
        } else if (resultCode == ResultCodes.REQ_CODE_BTR.getCode()&&data!=null) {
            btrVO = (BtrVO) data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
            setBtrInfo();
        } else if (resultCode == ResultCodes.REQ_CODE_ADDR_ZIP.getCode()&&data!=null) {
            addressZipVO = (AddressZipVO) data.getSerializableExtra(KeyNames.KEY_NAME_ZIP_ADDR);
            setAddressInfo();
        } else if (resultCode == ResultCodes.REQ_CODE_ADDR_ZIP_PRIVILEGE.getCode()&&data!=null) {
            privilegeAddressZipVO = (AddressZipVO) data.getSerializableExtra(KeyNames.KEY_NAME_ZIP_ADDR);
            ui.lPrivilege.cbAddr.setChecked(false);
            setPrivilegeAddressInfo();
        }
    }

    private void setImgAttach(Uri resultUri) {

        switch (targetImgId) {
            case R.id.btn_cnt_img:
                cntImagPath = resultUri;
                Paris.style(ui.tvCntImg).apply(R.style.TextViewCntImgEnable);
                ui.tvCntImg.setText(new File(FileUtil.getRealPathFromURI(this, cntImagPath)).getName());
                ui.tvErrorCntImg.setText("");
                break;
            case R.id.btn_emp_certi_img:
                empCertImagPath = resultUri;
                Paris.style(ui.tvEmpCertiImg).apply(R.style.TextViewEmpCertiImgEnable);
                ui.tvEmpCertiImg.setText(new File(FileUtil.getRealPathFromURI(this, empCertImagPath)).getName());
                ui.tvErrorEmpCertiImg.setText("");
                break;
        }

        checkValidImg();

    }

    private void setBtrInfo() {

        if (btrVO == null) {
            ui.tvBtr.setVisibility(View.VISIBLE);
            ui.lBtrInfo.setVisibility(View.GONE);
        } else {
            ui.tvBtr.setVisibility(View.GONE);
            ui.lBtrInfo.setVisibility(View.VISIBLE);
            ui.tvBtrAsnm.setText(btrVO.getAsnNm());
            ui.tvBtrAddr.setText(btrVO.getPbzAdr());
            ui.tvBtrReptn.setText(PhoneNumberUtils.formatNumber(btrVO.getRepTn(), Locale.getDefault().getCountry()));
        }

        checkValidBtr();
    }


    private void setAddressInfo() {

        if (addressZipVO == null) {
            Paris.style(ui.tvPostNo).apply(R.style.TextViewPostNo);
            Paris.style(ui.tvAddr).apply(R.style.TextViewAddr);
        } else {
            Paris.style(ui.tvPostNo).apply(R.style.TextViewPostNoEnable);
            Paris.style(ui.tvAddr).apply(R.style.TextViewAddrEnable);
            ui.tvPostNo.setText(addressZipVO.getZipNo());
            ui.tvAddr.setText(addressZipVO.getRoadAddr());
        }

        if (TextUtils.isEmpty(ui.etAddrDetail.getText().toString().trim())) {
            ui.etAddrDetail.requestFocus();
        }
    }

    private void setPrivilegeAddressInfo() {
        if (privilegeAddressZipVO == null) {
            Paris.style(ui.lPrivilege.tvAddr).apply(R.style.TextViewAddr);
            Paris.style(ui.lPrivilege.tvPostNo).apply(R.style.TextViewPostNo);
        } else {
            Paris.style(ui.lPrivilege.tvAddr).apply(R.style.TextViewAddrEnable);
            Paris.style(ui.lPrivilege.tvPostNo).apply(R.style.TextViewPostNoEnable);
            ui.lPrivilege.tvPostNo.setText(privilegeAddressZipVO.getZipNo());
            ui.lPrivilege.tvAddr.setText(privilegeAddressZipVO.getRoadAddr());
        }
        if (TextUtils.isEmpty(ui.lPrivilege.etAddrDetail.getText().toString().trim())) {
            ui.lPrivilege.etAddrDetail.requestFocus();
        }
    }

    private void selectPrivilegeService() {
        clearKeypad();
        final List<String> periodList = gnsViewModel.getGodsNmList(gns1011Response.getGodsList());
        showMapDialog(periodList, R.string.gm_carlst_01_01_17, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                String result = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(result)) {
                    selectPrivilege = gnsViewModel.getGodsByNm(result, gns1011Response.getGodsList());
                    if (selectPrivilege != null) {
                        ui.lPrivilege.setData(selectPrivilege);
                        Paris.style(ui.lPrivilege.tvPrivilegeService).apply(R.style.CommonSpinnerItemEnable);
                        ui.lPrivilege.tvPrivilegeService.setText(result);
                        ui.lPrivilege.tvTitlePrivilegeService.setVisibility(View.VISIBLE);
                    }
                    checkValidPrivilege(true);
                }
            }
        });
    }


    EditText.OnFocusChangeListener focusChangeListener = (view, hasFocus) -> {
        if (!hasFocus) {
            SoftKeyboardUtil.hideKeyboard(LeasingCarRegisterInputActivity.this, getWindow().getDecorView().getWindowToken());
        } else {
            SoftKeyboardUtil.showKeyboard(getApplicationContext());
        }
    };

    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            doNext();
        }
        return false;
    };

    @Override
    public void onBackPressed() {
        dialogExit();
    }

    @Override
    public void onBackButton() {
        dialogExit();
    }

    private void dialogExit() {
        MiddleDialog.dialogLeasingCarCancel(this, () -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }

}
