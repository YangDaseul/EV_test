package com.genesis.apps.ui.main.home;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.GNS_1003;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.databinding.ActivityLeasingCarRegisterInput1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author hjpark
 * @brief 렌트/리스 실 운행자 등록 (차대번호 입력)
 */
public class LeasingCarRegisterInputActivity extends SubActivity<ActivityLeasingCarRegisterInput1Binding> {
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
                doNext();
                break;

            case R.id.tv_csmr_scn_cd:
                final List<String> cdList = Arrays.asList(getResources().getStringArray(R.array.leasing_car_csmr_scn_cd));
                showMapDialog(cdList, R.string.gm_carlst_01_26, dialogInterface -> {
                    String result = bottomListDialog.getSelectItem();
                    if(!TextUtils.isEmpty(result)){
                        //TODO 삭제 요청

                        if(result.equalsIgnoreCase(cdList.get(0))){
                            csmrScnCd = VariableType.LEASING_CAR_CSMR_SCN_CD_14; //개인
                        }else{
                            csmrScnCd = VariableType.LEASING_CAR_CSMR_SCN_CD_1; //법인
                        }

                        ui.tvTitleCsmrScnCd.setVisibility(View.VISIBLE);
                        ui.tvCsmrScnCd.setTextAppearance(R.style.TextViewCsmrScnCdEnable);
                        ui.tvCsmrScnCd.setText(result);
                        ui.tvErrorCsmrScnCd.setVisibility(View.GONE);
                        doTransition(1);
                    }
                });


//                final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
//                bottomListDialog.setOnDismissListener(dialogInterface -> {
//                    String result = bottomListDialog.getSelectItem();
//                    if(!TextUtils.isEmpty(result)){
//                        //TODO 삭제 요청
//
//                        if(result.equalsIgnoreCase(cdList.get(0))){
//                            csmrScnCd = VariableType.LEASING_CAR_CSMR_SCN_CD_14; //개인
//                        }else{
//                            csmrScnCd = VariableType.LEASING_CAR_CSMR_SCN_CD_1; //법인
//                        }
//
//                        ui.tvCsmrScnCd.setTextAppearance(R.style.TextViewCsmrScnCdEnable);
//                        ui.tvCsmrScnCd.setText(result);
//                        ui.tvErrorCsmrScnCd.setVisibility(View.GONE);
//                        doTransition(1);
//                    }
//                });
//                bottomListDialog.setDatas(cdList);
//                bottomListDialog.setTitle(getString(R.string.gm_carlst_01_26));
//                bottomListDialog.show();
                break;

            case R.id.tv_rent_period:


                final List<String> periodList = Arrays.asList(getResources().getStringArray(R.array.leasing_car_rent_period));
                showMapDialog(periodList, R.string.gm_carlst_01_57, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        String result = bottomListDialog.getSelectItem();
                        if(!TextUtils.isEmpty(result)){
                            //TODO 삭제 요청

                            ui.lRentPeriodEtc.setError(null);
                            ui.lRentPeriodEtc.setVisibility(View.GONE);
                            ui.tvErrorRentPeriod.setVisibility(View.GONE);
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
                                ui.lRentPeriodEtc.setVisibility(View.VISIBLE);
                            }

                            ui.tvRentPeriod.setTextAppearance(R.style.TextViewRentPeriodEnable);
                            ui.tvRentPeriod.setText(result);
                            ui.tvTitleRentPeriod.setVisibility(View.VISIBLE);

                            if(result.equalsIgnoreCase(periodList.get(4))){
                                ui.etRentPeriodEtc.setText("");
                                ui.etRentPeriodEtc.requestFocus();
                                SoftKeyboardUtil.showKeyboard(getApplicationContext());
                            }else{
                                doTransition(2);
                            }
                        }
                    }
                });


                break;

            case R.id.btn_cnt_img:
//                CropImage.activity().start(this);

                CropImage.startPickImageActivity(this);
                break;
            case R.id.btn_emp_certi_img:

                break;
        }


//        switch (v.getId()){
//            case R.id.btn_check:
//                if(TextUtils.isEmpty(ui.etVin.getText().toString().trim())){
//                    ui.lVin.setError(getString(R.string.gm_carlst_01_23));
//                }else if(ui.etVin.getText().toString().trim().length()!=13){
//                    ui.lVin.setError(getString(R.string.gm_carlst_01_45));
//                }else{
//                    //todo ..next action
//                }
//                break;
//            case R.id.btn_info:
//                startActivitySingleTop(new Intent(this, LeasingCarInfoActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                break;
//        }

    }

    private void showMapDialog(List<String> list, int title, DialogInterface.OnDismissListener dismissListener) {
        bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dismissListener);
        bottomListDialog.setDatas(list);
        bottomListDialog.setTitle(getString(title));
        bottomListDialog.show();
    }

//    private void selectItem(String selectNm, int id) {
//        switch (id){
//            case R.id.tv_position_1:
//                if(!ui.tvPosition1.getText().toString().equalsIgnoreCase(selectNm)){
//                    ui.tvPosition1.setText(selectNm);
//                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionEnable);
//                    ui.tvPosition2.setText(R.string.bt06_5);
//                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
//                    pubViewModel.reqPUB1003(new PUB_1003.Request(APPIAInfo.GM_BT06_01.getId(), pubViewModel.getSidoCode(selectNm)));
//                }
//                break;
//            case R.id.tv_position_2:
//                if(!ui.tvPosition2.getText().toString().equalsIgnoreCase(selectNm)){
//                    ui.tvPosition2.setText(selectNm);
//                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionEnable);
//                }
//                break;
//        }
//    }
//
//    private void setFilter(int selectId){
//        for(int i=0; i<filterIds.length; i++){
//
//            if(selectId==filterIds[i]){
//                ((TextView)findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterEnable);
//            }else{
//                ((TextView)findViewById(filterIds[i])).setTextAppearance(R.style.BtrFilterDisable);
//            }
//        }
//    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
//        btrViewModel = new ViewModelProvider(this).get(BTRViewModel.class);
    }

    @Override
    public void setObserver() {
//        pubViewModel.getRES_PUB_1002().observe(this, result -> {
//            switch (result.status) {
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    ui.tvPosition1.setText(R.string.bt06_4);
//                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionDisable);
//                    ui.tvPosition2.setText(R.string.bt06_5);
//                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
//                    break;
//                default:
//                    showProgressDialog(false);
//                    break;
//            }
//        });
//        pubViewModel.getRES_PUB_1003().observe(this, result -> {
//            switch (result.status) {
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    break;
//                default:
//                    ui.tvPosition1.setText(R.string.bt06_4);
//                    ui.tvPosition1.setTextAppearance(R.style.BtrPositionDisable);
//                    ui.tvPosition2.setText(R.string.bt06_5);
//                    ui.tvPosition2.setTextAppearance(R.style.BtrPositionDisable);
//                    pubViewModel.getRES_PUB_1003().setValue(null);
//                    showProgressDialog(false);
//                    break;
//            }
//        });
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
        }
    }


    private void doNext(){
        String inputValue;

        for(View view : views){

            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    case R.id.l_rent_period:
                        inputValue = ui.tvCsmrScnCd.getText().toString();
                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_25))){
                            ui.tvErrorCsmrScnCd.setVisibility(View.GONE);
                            doTransition(1);
                        }else{
                            ui.tvErrorCsmrScnCd.setVisibility(View.VISIBLE);
                        }
                        return;

                    case R.id.l_cnt_img:
                        inputValue = ui.tvRentPeriod.getText().toString();
                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_30))&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_56))){
                            //빈값이 아니고, 대여 기간이 아니고 기타가 아니면
                            doTransition(2);
                            ui.lRentPeriodEtc.setError(null);
                        }else if(inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_56))){
                            //대여기간이 기타 일 때
                            if(getPeriod()<12){
                                ui.lRentPeriodEtc.setError(getString(R.string.gm_carlst_01_33));
                            }else{
                                ui.lRentPeriodEtc.setError(null);
                                doTransition(2);
                            }
                        }else{
                            ui.tvErrorRentPeriod.setVisibility(View.VISIBLE);
                        }
                        return;

                    case R.id.l_emp_certi_img:
                        inputValue = ui.tvCntImg.getText().toString();
                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_36))){
                            doTransition(3);
                            //todo tv_error_cnt_img gone 필요?
                            //todo 이미지 용량 제한 처리 필요
                        }else{
                            ui.tvErrorCntImg.setVisibility(View.VISIBLE);
                            ui.tvErrorCntImg.setText(R.string.gm_carlst_01_55);
                        }
                        return;


                    case R.id.l_btr:
                        inputValue = ui.tvEmpCertiImg.getText().toString();
                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_41))){
                            doTransition(4);
                            //todo ttvErrorEmpCertiImg gone 필요?
                            //todo 이미지 용량 제한 처리 필요
                        }else{
                            ui.tvErrorEmpCertiImg.setVisibility(View.VISIBLE);
                            ui.tvErrorEmpCertiImg.setText(R.string.gm_carlst_01_42);
                        }
                        return;

                    case R.id.l_card:
                        if(btrVO!=null){
                            doTransition(5);
                            ui.btnNext.setText(R.string.gm_carlst_04_20);
                        }else{
                            SnackBarUtil.show(this, "버틀러를 선택해 주세요.");
                        }
                        return;
                }
            }
        }


        inputValue = ui.etAddrDetail.getText().toString();

        if(addressZipVO==null){
            SnackBarUtil.show(this, "수령지 주소를 입력해 주세요.");
        }else if(TextUtils.isEmpty(inputValue)){
            SnackBarUtil.show(this, "상세 주소를 입력해 주세요.");
        }else{
            //todo 신청하기 진행
        }
    }



    private final int TYPE_CSMR_SCN_CD=0;
    private final int TYPE_RENT_PERIOD=1;
    private final int TYPE_CNT_IMG=2;
    private final int TYPE_EMP_CERTI_IMG=3;
    private final int TYPE_BTR=4;
    private final int TYPE_CARD=5;


    private void doNext(final int pos){
        String inputValue;

        switch (pos){
            case TYPE_CSMR_SCN_CD:




                break;





        }





        for(View view : views){

            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    case R.id.l_rent_period:
                        inputValue = ui.tvCsmrScnCd.getText().toString();
                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_25))){
                            ui.tvErrorCsmrScnCd.setVisibility(View.GONE);
                            doTransition(1);
                        }else{
                            ui.tvErrorCsmrScnCd.setVisibility(View.VISIBLE);
                        }
                        return;

                    case R.id.l_cnt_img:
                        inputValue = ui.tvRentPeriod.getText().toString();
                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_30))&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_56))){
                            //빈값이 아니고, 대여 기간이 아니고 기타가 아니면
                            doTransition(2);
                            ui.lRentPeriodEtc.setError(null);
                        }else if(inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_56))){
                            //대여기간이 기타 일 때
                            if(getPeriod()<12){
                                ui.lRentPeriodEtc.setError(getString(R.string.gm_carlst_01_33));
                            }else{
                                ui.lRentPeriodEtc.setError(null);
                                doTransition(2);
                            }
                        }else{
                            SnackBarUtil.show(this, "대여 기간을 선택해 주세요.");
                        }
                        return;

                    case R.id.l_emp_certi_img:
                        inputValue = ui.tvCntImg.getText().toString();
                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_36))){
                            doTransition(3);
                            //todo tv_error_cnt_img gone 필요?
                            //todo 이미지 용량 제한 처리 필요
                        }else{
                            ui.tvErrorCntImg.setVisibility(View.VISIBLE);
                            ui.tvErrorCntImg.setText(R.string.gm_carlst_01_55);
                        }
                        return;


                    case R.id.l_btr:
                        inputValue = ui.tvEmpCertiImg.getText().toString();
                        if(!TextUtils.isEmpty(inputValue)&&!inputValue.equalsIgnoreCase(getString(R.string.gm_carlst_01_41))){
                            doTransition(4);
                            //todo ttvErrorEmpCertiImg gone 필요?
                            //todo 이미지 용량 제한 처리 필요
                        }else{
                            ui.tvErrorEmpCertiImg.setVisibility(View.VISIBLE);
                            ui.tvErrorEmpCertiImg.setText(R.string.gm_carlst_01_42);
                        }
                        return;

                    case R.id.l_card:
                        if(btrVO!=null){
                            doTransition(5);
                            ui.btnNext.setText(R.string.gm_carlst_04_20);
                        }else{
                            SnackBarUtil.show(this, "버틀러를 선택해 주세요.");
                        }
                        return;
                }
            }
        }


        inputValue = ui.etAddrDetail.getText().toString();

        if(addressZipVO==null){
            SnackBarUtil.show(this, "수령지 주소를 입력해 주세요.");
        }else if(TextUtils.isEmpty(inputValue)){
            SnackBarUtil.show(this, "상세 주소를 입력해 주세요.");
        }else{
            //todo 신청하기 진행
        }
    }




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

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }



        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = CropImage.getPickImageResultUri(this,data);
                String path = resultUri.getPath();
                String name = new File(CropImage.getPickImageResultUri(this,data).getPath()).getName();
                path = path.substring(path.lastIndexOf(".")+1,path.length());
//                getContentResolver().getType(resultUri).;


//                Bitmap bitmap = MediaStore.Images.Media(getContentResolver(), resultUri);
//                imgUserProfile.setImageBitmap(bitmap);








//
//                Uri returnUri = returnIntent.getData();
//                Cursor returnCursor =
//                        getContentResolver().query(returnUri, null, null, null, null);
//                /*
//                 * Get the column indexes of the data in the Cursor,
//                 * move to the first row in the Cursor, get the data,
//                 * and display it.
//                 */
//                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
//                returnCursor.moveToFirst();
//                TextView nameView = (TextView) findViewById(R.id.filename_text);
//                TextView sizeView = (TextView) findViewById(R.id.filesize_text);
//                nameView.setText(returnCursor.getString(nameIndex));
//                sizeView.setText(Long.toString(returnCursor.getLong(sizeIndex)));





            }
        }
    }


}
