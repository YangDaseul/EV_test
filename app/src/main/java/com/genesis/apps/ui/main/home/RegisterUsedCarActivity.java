package com.genesis.apps.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityRegUsedCar1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.android.material.textfield.TextInputEditText;

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
public class RegisterUsedCarActivity extends SubActivity<ActivityRegUsedCar1Binding> {

    private GNSViewModel gnsViewModel;

    private final int[] layouts = {R.layout.activity_reg_used_car_1, R.layout.activity_reg_used_car_2};
    private final int[] textMsgId = {R.string.gm_carlst_03_11, R.string.gm_carlst_03_2};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;

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
        ui.tvName.setText(gnsViewModel.getDbUserRepo().getUserVO().getCustNm());
        ui.etVin.setOnEditorActionListener(editorActionListener);
        ui.etVrn.setOnEditorActionListener(editorActionListener);
//        ui.etRentPeriodEtc.setOnFocusChangeListener((view, hasFocus) -> {
//
//            if (!hasFocus) {
//                SoftKeyboardUtil.hideKeyboard(RegisterUsedCarActivity.this);
//            }else{
//                SoftKeyboardUtil.showKeyboard(getApplicationContext());
//            }
//
//        });



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
            case R.id.btn_check://다음
                doNext();
                break;
            case R.id.btn_guide:

                MiddleDialog.dialogUsedCarInfo(this, () -> {

                }, () -> {

                });

                break;
        }

    }

    private void doNext(){
        if(isValid()){
            //todo 신청하기 진행
//                    gnsViewModel.reqGNS1006(new GNS_1006.Request(APPIAInfo.GM_CARLST_01_01.getId(),
//                            vin,
//                            csmrScnCd,
//                            (rentPeriod.equalsIgnoreCase(VariableType.LEASING_CAR_PERIOD_ETC) ? getPeriod()+"" : rentPeriod),
//                            "3",
//                            addressZipVO.getZipNo(),
//                            addressZipVO.getRoadAddr(),
//                            ui.etAddrDetail.getText().toString().trim(),
//                            "Y",
//                            (csmrScnCd.equalsIgnoreCase(VariableType.LEASING_CAR_CSMR_SCN_CD_14) ? "N" : "Y")));
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
    }

    private void initConstraintSets() {
        views = new View[]{ui.lVrn, ui.lVin};
        edits = new View[]{ui.etVrn, ui.etVin};
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

            if (edits[pos] instanceof TextInputEditText) {
                edits[pos].requestFocus();
            }
        }
    }


    private boolean checkVaildVrn(){

        String vrn = ui.etVrn.getText().toString().trim();

        if(TextUtils.isEmpty(vrn)){
            ui.lVrn.setError(getString(R.string.gm_carlst_03_12));
            return false;
        }else if(!StringRe2j.matches(vrn, getString(R.string.check_car_vrn))){
            ui.lVrn.setError(getString(R.string.gm_carlst_p01_8));
            return false;
        }else{
            ui.lVrn.setError(null);
            doTransition(1);
            return true;
        }
    }

    private boolean checkVaildVin(){

        String vin = ui.etVin.getText().toString().trim();

        if(TextUtils.isEmpty(vin)||vin.length()!=17){
            ui.lVin.setError(getString(R.string.gm_carlst_03_10));
            return false;
        }else{
            ui.lVin.setError(null);
            return true;
        }
    }

    private boolean isValid(){
        for(View view : views){
            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    //현재 페이지가 차량번호 입력하는 페이지일경우
                    case R.id.l_vin:
                        return checkVaildVrn();
                }
            }
        }
        return checkVaildVin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE&&resultCode == RESULT_OK) {
//            Uri resultUri = CropImage.getPickImageResultUri(this, data);
//            setImgAttach(resultUri);
//        }else if(resultCode == ResultCodes.REQ_CODE_BTR.getCode()){
//            btrVO = (BtrVO)data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
//            setBtrInfo();
//        }else if(resultCode == ResultCodes.REQ_CODE_ADDR_ZIP.getCode()){
//            addressZipVO = (AddressZipVO)data.getSerializableExtra(KeyNames.KEY_NAME_ZIP_ADDR);
//            setAddressInfo();
//        }
    }

    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if(actionId== EditorInfo.IME_ACTION_DONE){
            doNext();
        }
        return false;
    };
}
