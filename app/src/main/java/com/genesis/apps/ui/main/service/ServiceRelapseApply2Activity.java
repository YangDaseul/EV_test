package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.genesis.apps.comm.model.api.gra.PUB_1002;
import com.genesis.apps.comm.model.api.gra.PUB_1003;
import com.genesis.apps.comm.model.api.gra.VOC_1001;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.PUBViewModel;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.comm.viewmodel.VOCViewModel;
import com.genesis.apps.databinding.ActivityServiceRelapseApply21Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendar;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * @author hjpark
 * @brief 하자재발통보2단계
 */

@AndroidEntryPoint
public class ServiceRelapseApply2Activity extends SubActivity<ActivityServiceRelapseApply21Binding> {
    private PUBViewModel pubViewModel;
    private VOCViewModel vocViewModel;
    private REQViewModel reqViewModel;
    private final int[] layouts = {R.layout.activity_service_relapse_apply_2_1, R.layout.activity_service_relapse_apply_2_2, R.layout.activity_service_relapse_apply_2_3, R.layout.activity_service_relapse_apply_2_4, R.layout.activity_service_relapse_apply_2_5, R.layout.activity_service_relapse_apply_2_6};
    private final int[] textMsgId = {R.string.r_flaw05_14, R.string.r_flaw05_16, R.string.r_flaw05_17, R.string.r_flaw05_18, R.string.r_flaw05_19, R.string.r_flaw05_20};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;
    private boolean isValidVin; //차대번호를 확인했는지 유무
    private String validVin;
    private VOCInfoVO vocInfoVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(layouts[0]);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        initConstraintSets();
        initEditView();
    }

    private void initEditView() {
        ui.etVin.setOnEditorActionListener(editorActionListener);
        ui.etVrn.setOnEditorActionListener(editorActionListener);
        ui.etTrvgDist.setOnEditorActionListener(editorActionListener);
        ui.etVin.setOnFocusChangeListener(focusChangeListener);
        ui.etVrn.setOnFocusChangeListener(focusChangeListener);
        ui.etTrvgDist.setOnFocusChangeListener(focusChangeListener);
        ui.etVin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isValidVin = !TextUtils.isEmpty(validVin) && charSequence.toString().equalsIgnoreCase(validVin);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setViewVin();
    }

    private void setViewVin() {
        String vin = "";

        try {
            vin = reqViewModel.getMainVehicle().getVin();
        } catch (Exception e) {

        } finally {
            ui.etVin.setText(vin);
            ui.etVin.setSelection(ui.etVin.length());
        }

    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_vin:
                checkVin();
                break;
            case R.id.tv_wpa://시도
                selectSido();
                break;
            case R.id.tv_admz://시군구
                selectSiGunGu();
                break;
            case R.id.tv_md_yyyy://등록연월일
                selectCalendar();
                break;
            case R.id.btn_example:
                startActivitySingleTop(new Intent(this, ServiceRelapseApplyExampleActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
            case R.id.btn_next://다음
                doNext();
                break;
        }
    }

    private void checkVin(){
        String vin = ui.etVin.getText().toString().trim();
        if (TextUtils.isEmpty(vin)) {
            ui.etVin.requestFocus();
            ui.tvErrorVin.setVisibility(View.VISIBLE);
            ui.tvErrorVin.setText(getString(R.string.r_flaw05_28));
        } else if (vin.length() != 17) {
            ui.etVin.requestFocus();
            ui.tvErrorVin.setVisibility(View.VISIBLE);
            ui.tvErrorVin.setText(getString(R.string.r_flaw05_29));
        } else if (!isValidVin) {
            vocViewModel.reqVOC1001(new VOC_1001.Request(APPIAInfo.SM_FLAW05.getId(), vin));
        }
    }

    private void clearKeypad() {
        for (View view : edits) {
            view.clearFocus();
        }
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
    }

    private void doNext() {
        if (isValid()) {
            clearKeypad();
            vocInfoVO.setVin(ui.etVin.getText().toString().trim());
            vocInfoVO.setCarNo(ui.etVrn.getText().toString().trim());
            vocInfoVO.setTrvgDist(ui.etTrvgDist.getText().toString().trim().replaceAll(",", ""));
            startActivitySingleTop(new Intent(this, ServiceRelapse3Activity.class).putExtra(KeyNames.KEY_NAME_SERVICE_VOC_INFO_VO, vocInfoVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        }
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        vocViewModel = new ViewModelProvider(this).get(VOCViewModel.class);
        pubViewModel = new ViewModelProvider(this).get(PUBViewModel.class);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
        pubViewModel.reqPUB1002(new PUB_1002.Request(APPIAInfo.SM_FLAW05.getId()));
    }

    @Override
    public void setObserver() {

        vocViewModel.getRES_VOC_1001().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);

                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getVhclList() != null && result.data.getVhclList().size() > 0) {
                        showProgressDialog(false);
                        vocInfoVO.setCarNm(result.data.getVhclList().get(0).getMdlNm());
                        vocInfoVO.setRecvDt(result.data.getVhclList().get(0).getRecvYmd());
                        ui.tvCarNm.setText(vocInfoVO.getCarNm());
                        ui.tvRecvDt.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(StringUtil.isValidString(vocInfoVO.getRecvYmd()), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
                        isValidVin = true;
                        validVin = result.data.getVhclList().get(0).getVin();
                        checkValidVin();
                        break;
                    }
                default:
                    showProgressDialog(false);
                    SnackBarUtil.show(this, getString(R.string.r_flaw05_15));
                    break;
            }
        });


        pubViewModel.getRES_PUB_1002().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    vocInfoVO.setWpa("");
                    vocInfoVO.setAdmz("");
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });

        pubViewModel.getRES_PUB_1003().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    vocInfoVO.setAdmz("");
                    checkValidWpa();
                    break;
                default:
                    showProgressDialog(false);
                    break;
            }
        });


    }


    private void showMapDialog(int id, List<String> list, int title) {
        clearKeypad();
        if (list != null && list.size() > 0) {
            final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
            bottomListDialog.setOnDismissListener(dialogInterface -> {
                String selectItem = bottomListDialog.getSelectItem();
                if (!TextUtils.isEmpty(selectItem)) {
                    selectItem(selectItem, id);
                }
            });
            bottomListDialog.setDatas(list);
            bottomListDialog.setTitle(getString(title));
            bottomListDialog.show();
        } else {
            SnackBarUtil.show(this, id == R.id.tv_admz ? "시/도가 선택되지 않았습니다.\n시/도 정보를 선택 후 다시 시도해 주세요." : "시/도 정보가 없습니다.\n네트워크 상태를 확인 후 다시 시도해 주십시오.");
        }
    }

    private void selectItem(String selectNm, int id) {
        switch (id) {
            case R.id.tv_wpa:
                if (!vocInfoVO.getWpa().equalsIgnoreCase(selectNm)) {
                    vocInfoVO.setWpa(selectNm);
                    vocInfoVO.setAdmz("");
                    ui.tvAdmz.setText(R.string.r_flaw05_13);
                    Paris.style(ui.tvAdmz).apply(R.style.CommonSpinnerItemDisable);
                    pubViewModel.reqPUB1003(new PUB_1003.Request(APPIAInfo.SM_FLAW05.getId(), pubViewModel.getSidoCode(selectNm)));
                }
                break;
            case R.id.tv_admz:
                if (!vocInfoVO.getAdmz().equalsIgnoreCase(selectNm)) {
                    vocInfoVO.setAdmz(selectNm);
                    checkValidAdmz();
                }
                break;
        }
    }

    private void selectSido() {
        List<String> listSidoNm = pubViewModel.getAddrNm();
        showMapDialog(R.id.tv_wpa, listSidoNm, R.string.r_flaw05_32);
    }

    private void selectSiGunGu() {
        List<String> listGuNm = pubViewModel.getAddrGuNm();
        showMapDialog(R.id.tv_admz, listGuNm, R.string.r_flaw05_33);
    }

    @Override
    public void getDataFromIntent() {
        try {
            vocInfoVO = (VOCInfoVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_SERVICE_VOC_INFO_VO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author
     * @brief constraintSet 초기화
     */
    private void initConstraintSets() {
        views = new View[]{ui.lVin, ui.lVrn, ui.lMdYyyy, ui.lTrvgDist, ui.lWpa, ui.lAdmz};
        edits = new View[]{ui.etVin, ui.etVrn, ui.tvMdYyyy, ui.etTrvgDist, ui.tvWpa, ui.tvAdmz};

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

            if (edits[pos - 1] instanceof TextInputEditText) {
                edits[pos].clearFocus();
            }

            if (edits[pos] instanceof TextInputEditText) {
                edits[pos].requestFocus();
            }

            if (pos == 2) {
                selectCalendar();
            } else if (pos == 4) {
                selectSido();
            } else if (pos == 5) {
                selectSiGunGu();
            }
        }
    }

    private boolean checkValidVin() {
        String vin = ui.etVin.getText().toString().trim();
        if (TextUtils.isEmpty(vin)) {
            ui.etVin.requestFocus();
            ui.tvErrorVin.setVisibility(View.VISIBLE);
            ui.tvErrorVin.setText(getString(R.string.r_flaw05_28));
            return false;
        } else if (vin.length() != 17) {
            ui.etVin.requestFocus();
            ui.tvErrorVin.setVisibility(View.VISIBLE);
            ui.tvErrorVin.setText(getString(R.string.r_flaw05_29));
            return false;
        } else if (!isValidVin) {
            ui.etVin.requestFocus();
//            ui.tvErrorVin.setVisibility(View.VISIBLE);
//            ui.tvErrorVin.setText(getString(R.string.r_flaw05_30));
            checkVin();
            return false;
        } else {
            ui.tvErrorVin.setVisibility(View.INVISIBLE);
            doTransition(1);
            return true;
        }
    }

    private boolean checkValidCarRegNo() {
        String carRegNo = ui.etVrn.getText().toString().trim();

        if (TextUtils.isEmpty(carRegNo)) {
            ui.etVrn.requestFocus();
            ui.lVrn.setError(getString(R.string.r_flaw05_22));
            return false;
        } else if (!StringRe2j.matches(carRegNo, getString(R.string.check_car_vrn))) {
            ui.etVrn.requestFocus();
            ui.lVrn.setError(getString(R.string.r_flaw05_23));
            return false;
        } else {
            ui.lVrn.setError(null);
            doTransition(2);
            return true;
        }
    }


    private boolean checkValidMdYyyy() {
        String mdyyyy = vocInfoVO.getMdYyyy();

        if (TextUtils.isEmpty(mdyyyy)) {
            ui.tvMdYyyy.setText(R.string.r_flaw05_10);
            Paris.style(ui.tvMdYyyy).apply(R.style.CommonSpinnerItemCalendarDisable);
            ui.tvTitleMdYyyy.setVisibility(View.GONE);
            ui.tvErrorMdYyyy.setVisibility(View.VISIBLE);
            ui.tvErrorMdYyyy.setText(R.string.r_flaw05_24);
            return false;
        } else {
            String date = DateUtil.getDate(DateUtil.getDefaultDateFormat(mdyyyy, DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot);
            ui.tvMdYyyy.setText(date);
            Paris.style(ui.tvMdYyyy).apply(R.style.CommonSpinnerItemCalendar);
            ui.tvTitleMdYyyy.setVisibility(View.VISIBLE);
            ui.tvErrorMdYyyy.setVisibility(View.INVISIBLE);
            doTransition(3);
            return true;
        }
    }


    private void selectCalendar() {
        clearKeypad();
        DialogCalendar dialogCalendar = new DialogCalendar(this, R.style.BottomSheetDialogTheme);
        dialogCalendar.setOnDismissListener(dialogInterface -> {
            Calendar calendar = dialogCalendar.calendar;
            if (calendar != null) {
                vocInfoVO.setMdYyyy(DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd));
                checkValidMdYyyy();
            }
        });
        dialogCalendar.setCalendarMaximum(Calendar.getInstance(Locale.getDefault()));
        dialogCalendar.setTitle(getString(R.string.r_flaw05_31));
        dialogCalendar.show();
    }


    private boolean checkValidTrvgDist() {
        String trvgDist = ui.etTrvgDist.getText().toString().replaceAll(",", "").trim();

        if (TextUtils.isEmpty(trvgDist)) {
            ui.etTrvgDist.requestFocus();
            ui.lTrvgDist.setError(getString(R.string.r_flaw05_25));
            return false;
        } else {
            ui.lTrvgDist.setError(null);
            ui.etTrvgDist.setText(StringUtil.getDigitGroupingString(trvgDist));
            doTransition(4);
            return true;
        }
    }


    private boolean checkValidWpa() {
        String wpa = vocInfoVO.getWpa();

        if (TextUtils.isEmpty(wpa)) {
            ui.tvWpa.setText(R.string.r_flaw05_12);
            Paris.style(ui.tvWpa).apply(R.style.CommonSpinnerItemDisable);
            ui.tvTitleWpa.setVisibility(View.GONE);
            ui.tvErrorWpa.setVisibility(View.VISIBLE);
            ui.tvErrorWpa.setText(R.string.r_flaw05_26);
            return false;
        } else {
            ui.tvWpa.setText(wpa);
            Paris.style(ui.tvWpa).apply(R.style.CommonSpinnerItemEnable);
            ui.tvTitleWpa.setVisibility(View.VISIBLE);
            ui.tvErrorWpa.setVisibility(View.INVISIBLE);
            doTransition(5);
            return true;
        }
    }


    private boolean checkValidAdmz() {
        String admz = vocInfoVO.getAdmz();

        if (TextUtils.isEmpty(admz)) {
            ui.tvAdmz.setText(R.string.r_flaw05_12);
            Paris.style(ui.tvAdmz).apply(R.style.CommonSpinnerItemDisable);
            ui.tvTitleAdmz.setVisibility(View.GONE);
            ui.tvErrorAdmz.setVisibility(View.VISIBLE);
            ui.tvErrorAdmz.setText(R.string.r_flaw05_26);
            return false;
        } else {
            ui.tvAdmz.setText(admz);
            Paris.style(ui.tvAdmz).apply(R.style.CommonSpinnerItemEnable);
            ui.tvTitleAdmz.setVisibility(View.VISIBLE);
            ui.tvErrorAdmz.setVisibility(View.INVISIBLE);
            return true;
        }
    }


    private boolean isValid() {
        for (View view : views) {
            if (view.getVisibility() == View.GONE) {
                switch (view.getId()) {
                    case R.id.l_vin:
                        return false;
                    case R.id.l_vrn:
                        return checkValidVin() && false;
                    case R.id.l_md_yyyy:
                        return checkValidVin() && checkValidCarRegNo() && false;
                    case R.id.l_trvg_dist:
                        return checkValidVin() && checkValidCarRegNo() && checkValidMdYyyy() && false;
                    case R.id.l_wpa:
                        return checkValidVin() && checkValidCarRegNo() && checkValidMdYyyy() && checkValidTrvgDist() && false;
                    case R.id.l_admz:
                        return checkValidVin() && checkValidCarRegNo() && checkValidMdYyyy() && checkValidTrvgDist() && checkValidWpa() && false;
                    default:
                        break;
                }
            }
        }
        return checkValidVin() && checkValidCarRegNo() && checkValidMdYyyy() && checkValidTrvgDist() && checkValidWpa() && checkValidAdmz();
    }


    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            doNext();
        }
        return false;
    };

    EditText.OnFocusChangeListener focusChangeListener = (view, hasFocus) -> {
        if (hasFocus) {
            SoftKeyboardUtil.showKeyboard(getApplicationContext());
        }
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
        List<SubFragment> fragments = getFragments();
        if (fragments != null && fragments.size() > 0) {
            hideFragment(fragments.get(0));
        } else {
            finish();
            closeTransition();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodes.REQ_CODE_APPLY_RELAPSE.getCode()) {
            exitPage(getString(R.string.relapse_succ), ResultCodes.REQ_CODE_APPLY_RELAPSE.getCode());
        }
    }
}
