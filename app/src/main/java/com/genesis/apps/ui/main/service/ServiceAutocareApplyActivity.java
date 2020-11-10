package com.genesis.apps.ui.main.service;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.CouponVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.REQViewModel;
import com.genesis.apps.databinding.ActivityServiceAutocareApply1Binding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.DialogAutocareService;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author hjpark
 * @brief 차계부 입력
 */
public class ServiceAutocareApplyActivity extends SubActivity<ActivityServiceAutocareApply1Binding> {

    private REQViewModel reqViewModel;
    private VehicleVO mainVehicle;
    private final int[] layouts = {R.layout.activity_service_autocare_apply_1, R.layout.activity_service_autocare_apply_2, R.layout.activity_service_autocare_apply_3, R.layout.activity_service_autocare_apply_4};
    private final int[] textMsgId = {R.string.sm_r_rsv02_01_2, R.string.sm_r_rsv02_01_6, R.string.sm_r_rsv02_01_12, R.string.sm_r_rsv02_01_15};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];
    private View[] views;
    private View[] edits;

    private final String rsvtTypCd=VariableType.SERVICE_RESERVATION_TYPE_AUTOCARE; //에약유형코드
    private String rparTypCd; //정비내용코드
    private List<CouponVO> couponVOList;
    private List<CouponVO> selectCouponList;


    private AddressVO addressVO;

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
        try {
            //주 이용 차량 정보를 DB에서 GET
            mainVehicle = reqViewModel.getMainVehicle();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            initConstraintSets();
            initEditView();
        }
    }

    private void initEditView() {
        ui.etAddrDtl.setOnEditorActionListener(editorActionListener);
        ui.etAddrDtl.setOnFocusChangeListener(focusChangeListener);
    }


    private void startMapView(){
        startActivitySingleTop(new Intent(this, MapSearchMyPositionActivity.class).putExtra(KeyNames.KEY_NAME_ADDR, addressVO).putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, R.string.sm_r_rsv02_01_10).putExtra(KeyNames.KEY_NAME_MAP_SEARCH_MSG_ID, R.string.sm_r_rsv02_01_11), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.tv_addr:
                clearKeypad();
                startMapView();
                break;

            case R.id.tv_autocare_service:
            case R.id.btn_change_autocare_service:
                selectAutocareService();
                break;
            case R.id.tv_rsvt_hope_dt:
                //예야 희망일
                break;



//            case R.id.tv_area_cls_cd:
//                selectAreaClsCd();
//                break;
//            case R.id.tv_flt_cd:
//                selectfltCd();
//                break;
            case R.id.btn_next://다음
                doNext();
                break;
        }
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
//            sosViewModel.reqSOS1002(new SOS_1002.Request(APPIAInfo.SM_EMGC01.getId(),
//                    mainVehicle.getVin(),
//                    ui.etCarRegNo.getText().toString().trim(),
//                    mainVehicle.getMdlCd(),
//                    fltCd,
//                    areaClsCd,
//                    ui.tvAddrInfo1.getText().toString().trim() +" "+ui.tvAddrInfo2.getText().toString().trim()+" "+ui.etAddrDtl.getText().toString().trim(),
//                    addressVO.getCenterLat()+"",addressVO.getCenterLon()+"",ui.etCelPhNo.getText().toString().trim(),ui.etMemo.getText().toString().trim()));
        }
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        reqViewModel = new ViewModelProvider(this).get(REQViewModel.class);
    }

    @Override
    public void setObserver() {
//        sosViewModel.getRES_SOS_1002().observe(this, result -> {
//            switch (result.status){
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getRtCd())&&result.data.getRtCd().equalsIgnoreCase("0000")&&!TextUtils.isEmpty(result.data.getTmpAcptNo())){
//                        startActivitySingleTop(new Intent(this, ServiceSOSApplyInfoActivity.class).putExtra(KeyNames.KEY_NAME_SOS_TMP_NO, result.data.getTmpAcptNo()).addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT),0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                        finish();
//                        break;
//                    }
//                default:
//                    String serverMsg="";
//                    try {
//                        serverMsg = result.data.getRtMsg();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }finally{
//                        if (TextUtils.isEmpty(serverMsg)){
//                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
//                        }
//                        SnackBarUtil.show(this, serverMsg);
//                        showProgressDialog(false);
//                    }
//                    break;
//            }
//        });

    }

    @Override
    public void getDataFromIntent() {
        try {
            rparTypCd = getIntent().getStringExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE);
            couponVOList = new Gson().fromJson(getIntent().getStringExtra(KeyNames.KEY_NAME_SERVICE_COUPON_LIST), new TypeToken<List<CouponVO>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(rparTypCd)||couponVOList==null||couponVOList.size()<1) {
                exitPage("오토케어 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    /**
     * @author
     * @brief constraintSet 초기화
     */
    private void initConstraintSets() {
        views = new View[]{ui.lAutocareService, ui.lAddr, ui.lAddrDtl, ui.lRsvtHopeDt};
        edits = new View[]{ui.tvAutocareService, ui.tvAddr, ui.etAddrDtl, ui.tvRsvtHopeDt};
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
                //todo startMap
//                selectAreaClsCd();
            }else if(pos==3){
                //todo select date
//                startMapView();
            }else if(pos==views.length-1){
                //todo 아래 해야하나 ?
                ui.btnNext.setText(R.string.sm_emgc01_25);
            }
        }
    }

    /**
     * @brief 오토케어 예약 서비스 항목 선택 다이얼로그 활성화
     */
    private void selectAutocareService() {
        final DialogAutocareService dialogAutocareService = new DialogAutocareService(this, R.style.BottomSheetDialogTheme);
        dialogAutocareService.setList(reqViewModel.getAutocareCouponList(couponVOList));
        dialogAutocareService.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(dialogAutocareService.isNext()){
                    selectCouponList = dialogAutocareService.getCheckService();
                    checkValidAutocareService();
                }
            }
        });
        dialogAutocareService.show();
    }

    /**
     * @brief 예약 서비스 tag 갱신
     */
    private void setViewAutocareServiceTag() {
            Typeface typeface = ResourcesCompat.getFont(this, R.font.regular_genesissanstextglobal);
            ui.tagAutocareService.removeAllTags();
            ui.tagAutocareService.setTagTextSize(DeviceUtil.dip2Pixel(this, 15));
            ui.tagAutocareService.setTagTypeface(typeface);
//        getBinding().tag.setGravity(Gravity.CENTER);
            for(CouponVO couponVO : selectCouponList){
                ui.tagAutocareService.addTag(couponVO.getItemNm());
            }
    }


    private boolean checkValidAutocareService(){

        if(selectCouponList==null||selectCouponList.size()<1){
            ui.tvTitleAutocareService.setVisibility(View.GONE);
            ui.tagAutocareService.setVisibility(View.GONE);
            ui.btnChangeAutocareService.setVisibility(View.GONE);
            ui.tvAutocareService.setVisibility(View.VISIBLE);
            ui.tvErrorAutocareService.setVisibility(View.VISIBLE);
            ui.tvErrorAutocareService.setText(R.string.sm_r_rsv02_01_17);
            return false;
        }else{
            ui.tvTitleAutocareService.setVisibility(View.VISIBLE);
            ui.tagAutocareService.setVisibility(View.VISIBLE);
            ui.btnChangeAutocareService.setVisibility(View.VISIBLE);
            ui.tvAutocareService.setVisibility(View.GONE);
            ui.tvErrorAutocareService.setVisibility(View.GONE);
            setViewAutocareServiceTag();
            doTransition(1);
            return true;
        }
    }





//    private boolean checkValidPhoneNumber(){
//        String celPhoneNo = ui.etCelPhNo.getText().toString().replaceAll("-","").trim();
//
//        if(TextUtils.isEmpty(celPhoneNo)){
//            ui.etCelPhNo.requestFocus();
//            ui.lCelPhNo.setError(getString(R.string.sm_emgc01_5));
//            return false;
//        }else if(!StringRe2j.matches(celPhoneNo, getString(R.string.check_phone_number))){
//            ui.etCelPhNo.requestFocus();
//            ui.lCelPhNo.setError(getString(R.string.sm_emgc01_26));
//            return false;
//        }else{
//            ui.etCelPhNo.setText(PhoneNumberUtils.formatNumber(celPhoneNo, Locale.getDefault().getCountry()));
//            ui.etCelPhNo.setSelection(ui.etCelPhNo.length());
//            ui.etCelPhNo.clearFocus();
//            ui.lCelPhNo.setError(null);
//            return true;
//        }
//    }
//
//
//    private boolean checkValidfltCd(){
//        if(!TextUtils.isEmpty(fltCd)){
//            ui.tvErrorFltCd.setVisibility(View.INVISIBLE);
//            doTransition(1);
//            return true;
//        }else{
//            ui.tvErrorFltCd.setVisibility(View.VISIBLE);
//            ui.tvErrorFltCd.setText(R.string.sm_emgc01_24);
//            selectfltCd();
//            return false;
//        }
//    }
//
//    private boolean checkValidAreaClsCd(){
//        if(!TextUtils.isEmpty(areaClsCd)){
//            ui.tvErrorAreaClsCd.setVisibility(View.INVISIBLE);
//            doTransition(2);
//            return true;
//        }else{
//            ui.tvErrorAreaClsCd.setVisibility(View.VISIBLE);
//            ui.tvErrorAreaClsCd.setText(R.string.sm_emgc01_9);
//            return false;
//        }
//    }
//
//    private boolean checkValidAddr(){
//        String addr = ui.tvAddrInfo1.getText().toString().trim() + ui.tvAddrInfo2.getText().toString().trim();
//        if(TextUtils.isEmpty(addr)){
//            ui.tvErrorAddr.setVisibility(View.VISIBLE);
//            ui.tvErrorAddr.setText(getString(R.string.sm_emgc01_12));
//            return false;
//        }else{
//            ui.lAddrInfo.setVisibility(View.VISIBLE);
//            ui.tvTitleAddr.setVisibility(View.GONE);
//            ui.tvAddr.setVisibility(View.GONE);
//            ui.tvErrorAddr.setVisibility(View.INVISIBLE);
//            doTransition(3);
//            return true;
//        }
//    }
//
//    private boolean checkValidAddrDtl(){
//        String addrDtl = ui.etAddrDtl.getText().toString().trim();
//
//        if(TextUtils.isEmpty(addrDtl)){
//            ui.etAddrDtl.requestFocus();
//            ui.lAddrDtl.setError(getString(R.string.sm_emgc01_15));
//            return false;
//        }else{
//            ui.lAddrDtl.setError(null);
//            doTransition(4);
//            return true;
//        }
//    }
//
//    private boolean checkValidMemo(){
//        String memo = ui.etMemo.getText().toString().trim();
//
//        if(TextUtils.isEmpty(memo)){
//            ui.etMemo.requestFocus();
//            ui.lMemo.setError(getString(R.string.sm_emgc01_21));
//            return false;
//        }else{
//            ui.lMemo.setError(null);
//            doTransition(5);
//            return true;
//        }
//    }
//
//    private boolean checkValidCarRegNo(){
//        String carRegNo = ui.etCarRegNo.getText().toString().trim();
//
//        if(TextUtils.isEmpty(carRegNo)){
//            ui.etCarRegNo.requestFocus();
//            ui.lCarRegNo.setError(getString(R.string.sm_emgc01_18));
//            return false;
//        }else if(!StringRe2j.matches(carRegNo, getString(R.string.check_car_vrn))){
//            ui.etCelPhNo.requestFocus();
//            ui.lCelPhNo.setError(getString(R.string.sm_emgc01_27));
//            return false;
//        }else{
//            ui.lCarRegNo.setError(null);
//            return true;
//        }
//    }

    private boolean isValid(){

        for(View view : views){
            if(view.getVisibility()==View.GONE) {
                switch (view.getId()) {
                    case R.id.l_autocare_service:
                        return false;
                    case R.id.l_addr:
                        return checkValidAutocareService()&&false;
                    case R.id.l_addr_dtl:
                        return checkValidAutocareService()&&false;
                    case R.id.l_rsvt_hope_dt:
                        return checkValidAutocareService()&&false;
                }
            }
        }
        return checkValidAutocareService();
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
        MiddleDialog.dialogServiceSOSApplyExit(this, () -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ResultCodes.REQ_CODE_SERVICE_SOS_MAP.getCode()){
            addressVO = (AddressVO)data.getSerializableExtra(KeyNames.KEY_NAME_ADDR);
//            setViewAddr(addressVO.getTitle(), addressVO.getCname(), (TextUtils.isEmpty(addressVO.getAddrRoad()) ? addressVO.getAddr() : addressVO.getAddrRoad()));
        }
    }

//    private void setViewAddr(String buildName, String subBuildName, String address){
//        ui.tvAddrInfo1.setText(buildName+ (TextUtils.isEmpty(subBuildName) ? "" : " "+subBuildName));
//        ui.tvAddrInfo1.setVisibility(TextUtils.isEmpty(buildName) ? View.GONE : View.VISIBLE);
//        ui.tvAddrInfo2.setText(address);
//        ui.tvAddrInfo2.setVisibility(TextUtils.isEmpty(address) ? View.GONE : View.VISIBLE);
//        checkValidAddr();
//    }

}
