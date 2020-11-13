package com.genesis.apps.ui.main.insight;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CBK_1008;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CBKViewModel;
import com.genesis.apps.databinding.ActivityInsightExpnModifyBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.DialogCalendar;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author hjpark
 * @brief 차계부 수정
 */
public class InsightExpnModifyActivity extends SubActivity<ActivityInsightExpnModifyBinding> {

    private CBKViewModel cbkViewModel;
    private View[] views;
    private View[] edits;
    private List<String> vehicleList = new ArrayList<>();
    private ExpnVO baseData;
    private VehicleVO selectVehicle;
    private BottomListDialog bottomListDialog;

    private String expnDivCd="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(R.layout.activity_insight_expn_modify);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        try {
            vehicleList = cbkViewModel.getInsightVehicleList();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            initConstraintSets();
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

            updateView();
        }
    }

    private void updateView() {
        //지출 일자 //todo baseData.getExpnDtm()가 yyyyMMddHhmmss가 아니면..이슈 발생 가능
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(DateUtil.getDefaultDateFormat(baseData.getExpnDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss));
        setViewDtm(calendar);
        //누적주행거리
        ui.etAccmMilg.setText(StringUtil.getDigitGroupingString(baseData.getAccmMilg().replaceAll(",","")));
        //지출 항목
        ui.tvExpnDivCd.setText(baseData.getExpnDivNm());
        //지출액
        ui.etExpnAmt.setText(StringUtil.getDigitGroupingString(baseData.getExpnAmt().replaceAll(",","")));
        //지출처
        ui.etExpnPlc.setText(baseData.getExpnPlc());
    }


    private void initVehicleBtnStatus() {
        ui.tvVehicle.setText(selectVehicle.getMdlCd() +" "+selectVehicle.getCarRgstNo());
        int size = vehicleList.size();
        if(size<2){ //1대일경우
            ui.tvVehicle.setCompoundDrawables(null, null, null, null);
            ui.tvVehicle.setOnClickListener(null);
            ui.tvVehicle.setTextAppearance(R.style.CommonSpinnerItemReject);
        }
    }

    private String getVehicleName(int position) {
        String vehicleName="--";
        try{
            vehicleName = vehicleList.get(position);
        }catch (Exception e){
            vehicleName="--";
        }finally{
            return vehicleName;
        }
    }

    private int getVehiclePosition(String selectVehicle){
        int pos=0;

        for(String vehicleName : vehicleList){
            if(vehicleName.equalsIgnoreCase(selectVehicle)){
                return pos;
            }
            pos++;
        }
        return 0;
    }

    private void setViewDtm(Calendar calendar) {
        ui.tvExpnDtm.setText(DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.tv_vehicle:
                showDialog(vehicleList, R.string.tm_exps01_24, dialogInterface -> {
                    String vehicleName = bottomListDialog.getSelectItem();
                    if(!TextUtils.isEmpty(vehicleName)){
                        selectVehicle = cbkViewModel.getVehicleList().getValue().get(getVehiclePosition(vehicleName));
                        ui.tvVehicle.setText(vehicleName);
                    }
                });
                break;
            case R.id.btn_cancel:
                dialogExit();
                break;
            case R.id.btn_modify:
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

    private void showDialog(List<String> list, int title, DialogInterface.OnDismissListener dismissListener) {
        bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dismissListener);
        bottomListDialog.setDatas(list);
        bottomListDialog.setTitle(getString(title));
        bottomListDialog.show();
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

            String vin =selectVehicle.getVin();
            String expnDivCd = this.expnDivCd;
            String expnDtm = ui.tvExpnDtm.getText().toString().replaceAll("\\.", "").trim();
            String expnAmt = ui.etExpnAmt.getText().toString().replaceAll(",", "").trim();
            String accmMilg = ui.etAccmMilg.getText().toString().replaceAll(",", "").trim();
            String expnPlc = ui.etExpnPlc.getText().toString().trim();

            //하나라도 변경된 데이터가 있는지 확인
            if (!baseData.getVin().equalsIgnoreCase(vin)
                    || !VariableType.getExpnDivCd(baseData.getExpnDivNm()).equalsIgnoreCase(expnDivCd)
                    || !baseData.getExpnDtm().substring(0,8).equalsIgnoreCase(expnDtm)
                    || !baseData.getExpnAmt().equalsIgnoreCase(expnAmt)
                    || !baseData.getAccmMilg().equalsIgnoreCase(accmMilg)
                    || !baseData.getExpnPlc().equalsIgnoreCase(expnPlc)) {
                cbkViewModel.reqCBK1008(new CBK_1008.Request(APPIAInfo.TM_EXPS01_02.getId(), baseData.getExpnSeqNo(), selectVehicle, expnDivCd,expnDtm,expnAmt,accmMilg,expnPlc,"1000"));
            }else{
                SnackBarUtil.show(this, "수정된 데이터가 존재하지 않습니다.\n확인 후 다시 시도해 주세요.");
            }
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

        //최초 진입 후 차량
        cbkViewModel.getVehicleList().observe(this, vehicleVOList -> {
            initVehicleBtnStatus();
        });

        cbkViewModel.getRES_CBK_1008().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getRtCd())&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        exitPage(getString(R.string.tm_exps01_02_6), ResultCodes.REQ_CODE_INSIGHT_EXPN_MODIFY.getCode());
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
    }

    @Override
    public void getDataFromIntent() {
        try {
            selectVehicle = (VehicleVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_VEHICLE);
            baseData = (ExpnVO)getIntent().getSerializableExtra(KeyNames.KEY_NAME_INSIGHT_EXPN);
            expnDivCd = VariableType.getExpnDivCd(baseData.getExpnDivNm());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (selectVehicle==null||baseData==null) {
                exitPage("차량정보가 존재하지 않습니다.\n잠시 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    private void initConstraintSets() {
        views = new View[]{ui.lAccmMilg, ui.lExpnDivCd, ui.lExpnAmt, ui.lExpnPlc};
        edits = new View[]{ui.etAccmMilg, ui.tvExpnDivCd, ui.etExpnAmt, ui.etExpnPlc};
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
            return true;
        }
    }

    private boolean checkVaildDivCd(){
        if(!TextUtils.isEmpty(expnDivCd)){
            ui.tvErrorExpnDivCd.setVisibility(View.INVISIBLE);
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
        MiddleDialog.dialogInsightModifyCancel(this, () -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }

}
