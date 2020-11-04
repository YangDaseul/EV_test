package com.genesis.apps.ui.main.insight;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.CBK_1005;
import com.genesis.apps.comm.model.gra.api.CBK_1006;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CBKViewModel;
import com.genesis.apps.databinding.ActivityInsightExpnInput1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendar;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.home.LeasingCarRegisterInputActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author hjpark
 * @brief 차계부 입력
 */
public class InsightExpnInputActivity extends SubActivity<ActivityInsightExpnInput1Binding> {

    private CBKViewModel cbkViewModel;

    private final int[] layouts = {R.layout.activity_insight_expn_input_1, R.layout.activity_insight_expn_input_2, R.layout.activity_insight_expn_input_3, R.layout.activity_insight_expn_input_4};
    private final int[] textMsgId = {R.string.tm_exps01_01_6, R.string.tm_exps01_01_2, R.string.tm_exps01_01_9, R.string.tm_exps01_01_12};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;

    private String vin;
    private String expnDivCd="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(layouts[0]);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        cbkViewModel.reqCBK1005(new CBK_1005.Request(APPIAInfo.TM_EXPS01_01.getId(),vin));
    }

    private void initView() {
        initConstraintSets();
        setViewDtm(Calendar.getInstance(Locale.getDefault()));
        ui.etAccmMilg.setOnEditorActionListener(editorActionListener);
        ui.etExpnAmt.setOnEditorActionListener(editorActionListener);
        ui.etExpnPlc.setOnEditorActionListener(editorActionListener);
        ui.etAccmMilg.setOnFocusChangeListener(focusChangeListener);
        ui.etExpnAmt.setOnFocusChangeListener(focusChangeListener);
        ui.etExpnPlc.setOnFocusChangeListener(focusChangeListener);



        ui.etExpnAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String value = charSequence.toString().replace(",","");

                if(value.length()>10){
                    ui.etExpnAmt.setText(StringUtil.getDigitGroupingString(value.substring(0,10)));
                    ui.etExpnAmt.setSelection(ui.etExpnAmt.length());
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ui.etAccmMilg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String value = charSequence.toString().replace(",","");

                if(value.length()>10){
                    ui.etAccmMilg.setText(StringUtil.getDigitGroupingString(value.substring(0,10)));
                    ui.etAccmMilg.setSelection(ui.etAccmMilg.length());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void setViewDtm(Calendar calendar) {
        ui.tvExpnDtm.setText(DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_next://다음
                doNext();
                break;
            case R.id.tv_expn_div_cd:
                selectDivCd();
                break;
            case R.id.tv_expn_dtm:
                DialogCalendar dialogCalendar = new DialogCalendar(this, R.style.BottomSheetDialogTheme);
                dialogCalendar.setOnDismissListener(dialogInterface -> {
                    Calendar calendar = dialogCalendar.calendar;
                    if(calendar!=null){
                        setViewDtm(calendar);
                    }
                });
                dialogCalendar.setCalendarMaximum(Calendar.getInstance(Locale.getDefault()));
                dialogCalendar.setTitle(getString(R.string.tm_exps01_01_15));
                dialogCalendar.show();
                break;
        }
    }

    private void selectDivCd() {
        final List<String> divList = Arrays.asList(getResources().getStringArray(R.array.insight_item));
        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dialogInterface -> {
            String result = bottomListDialog.getSelectItem();
            if(!TextUtils.isEmpty(result)){
                expnDivCd  = VariableType.getExpnDivCd(result);
                ui.tvTitleExpnDivCd.setVisibility(View.VISIBLE);
                ui.tvExpnDivCd.setTextAppearance(R.style.CommonSpinnerItemEnable);
                ui.tvExpnDivCd.setText(result);

                checkVaildDivCd();
            }
        });
        bottomListDialog.setDatas(divList);
        bottomListDialog.setTitle(getString(R.string.tm_exps01_01_18));
        bottomListDialog.show();
    }

    private void clearKeypad(){
        for(View view : edits){
            view.clearFocus();
        }
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
    }

    private void doNext(){
        if(isValid()){
            clearKeypad();
            cbkViewModel.reqCBK1006(new CBK_1006.Request(APPIAInfo.TM_EXPS01_01.getId(), vin, expnDivCd, ui.etExpnAmt.getText().toString().replaceAll(",", ""), ui.tvExpnDtm.getText().toString().replaceAll(".", ""), ui.etExpnPlc.getText().toString(), ui.etAccmMilg.getText().toString().replaceAll(",", "")));
        }
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        cbkViewModel = new ViewModelProvider(this).get(CBKViewModel.class);
    }

    @Override
    public void setObserver() {

        cbkViewModel.getRES_CBK_1005().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getAccmMilg())){
                        ui.etAccmMilg.setText(result.data.getAccmMilg());
                        checkVaildAccmMilg();
                        break;
                    }
                default:
                    showProgressDialog(false);
                    break;
            }
        });

        cbkViewModel.getRES_CBK_1006().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getRtCd())&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        exitPage(getString(R.string.tm_exps01_01_17), ResultCodes.REQ_CODE_INSIGHT_EXPN_ADD.getCode());
                        break;
                    }
                default:
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if (TextUtils.isEmpty(serverMsg)){
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });

        //렌트 리스 신청하기 결과
//        gnsViewModel.getRES_GNS_1006().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//
//                case SUCCESS:
//                    if(result.data.getRtCd().equalsIgnoreCase("0000")){
//                        if(csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14)){
//                            File file = new File(FileUtil.getRealPathFromURI(this, cntImagPath));
//                            gnsViewModel.reqGNS1008(new GNS_1008.Request(APPIAInfo.GM_CARLST_01_01.getId(), vin, file.getName(), file ));
//                        }else{
//                            File file = new File(FileUtil.getRealPathFromURI(this, empCertImagPath));
//                            gnsViewModel.reqGNS1009(new GNS_1009.Request(APPIAInfo.GM_CARLST_01_01.getId(), vin, file.getName(), file ));
//                        }
//                    }
//                    break;
//                default:
//                    showProgressDialog(false);
//                    break;
//            }
//        });


    }

    @Override
    public void getDataFromIntent() {
        try {
            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(vin)) {
                exitPage("차대번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    private void initConstraintSets() {
        views = new View[]{ui.lAccmMilg, ui.lExpnDivCd, ui.lExpnAmt, ui.lExpnPlc};
        edits = new View[]{ui.etAccmMilg, ui.tvExpnDivCd, ui.etExpnAmt, ui.etExpnPlc};
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

            if (edits[pos-1] instanceof TextInputEditText) {
                edits[pos].clearFocus();
            }

            if (edits[pos] instanceof TextInputEditText) {
                edits[pos].requestFocus();
            }

            if(pos==1){
                selectDivCd();
            }else if(pos==views.length-1){
                ui.btnNext.setText(R.string.tm_exps01_01_16);
            }
        }
    }


    private boolean checkVaildAccmMilg(){

        String accmMilg = ui.etAccmMilg.getText().toString().trim();

        if(TextUtils.isEmpty(accmMilg)){
            ui.etAccmMilg.requestFocus();
            ui.lAccmMilg.setError(getString(R.string.tm_exps01_01_7));
            return false;
        }else{
            ui.etAccmMilg.setText(StringUtil.getDigitGroupingString(accmMilg.replaceAll(",","")));
            ui.lAccmMilg.setError(null);
            doTransition(1);
            return true;
        }
    }

    private boolean checkVaildDivCd(){
        if(!TextUtils.isEmpty(expnDivCd)){
            ui.tvErrorExpnDivCd.setVisibility(View.INVISIBLE);
            doTransition(2);
            return true;
        }else{
            ui.tvErrorExpnDivCd.setVisibility(View.VISIBLE);
            ui.tvErrorExpnDivCd.setText(R.string.tm_exps01_01_4);
            return false;
        }
    }

    private boolean checkVaildAmt(){

        String amt = ui.etExpnAmt.getText().toString().trim();

        if(TextUtils.isEmpty(amt)){
            ui.etExpnAmt.requestFocus();
            ui.lExpnAmt.setError(getString(R.string.tm_exps01_01_11));
            return false;
        }else{
            ui.etExpnAmt.setText(StringUtil.getDigitGroupingString(amt.replaceAll(",","")));
            ui.lExpnAmt.setError(null);
            doTransition(3);
            return true;
        }
    }


    private boolean checkVaildPlc(){

        String plc = ui.etExpnPlc.getText().toString().trim();

        if(TextUtils.isEmpty(plc)){
            ui.etExpnPlc.requestFocus();
            ui.lExpnPlc.setError(getString(R.string.tm_exps01_01_14));
            return false;
        }else{
            ui.lExpnPlc.setError(null);
            doTransition(3);
            return true;
        }
    }

    private boolean isValid(){
//        ui.lAccmMilg, ui.lExpnDivCd, ui.lExpnAmt, ui.lExpnPlc
        for(View view : views){
            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    //현재 페이지가 차량번호 입력하는 페이지일경우
                    case R.id.l_expn_div_cd:
                        return checkVaildAccmMilg()&&false;
                    case R.id.l_expn_amt:
                        return checkVaildAccmMilg()&&checkVaildDivCd()&&false;
                    case R.id.l_expn_plc:
                        return checkVaildAccmMilg()&&checkVaildDivCd()&&checkVaildAmt()&&false;
                }
            }
        }
        return checkVaildAccmMilg()&&checkVaildDivCd()&&checkVaildAmt()&&checkVaildPlc();
    }


    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if(actionId== EditorInfo.IME_ACTION_DONE){
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
    public void onBackButton(){
        dialogExit();
    }

    private void dialogExit(){
        MiddleDialog.dialogInsightInputCancel(this, () -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }

}