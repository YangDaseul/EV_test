package com.genesis.apps.ui.main.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.tsauth.GetCheckCarOwnerShip;
import com.genesis.apps.comm.model.api.tsauth.GetNewCarOwnerShip;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.UserVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.comm.viewmodel.TsAuthViewModel;
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
import dagger.hilt.android.AndroidEntryPoint;

/**
 * @author hjpark
 * @brief 내 차 등록
 */

@AndroidEntryPoint
public class RegisterUsedCarActivity extends SubActivity<ActivityRegUsedCar1Binding> {
    protected String TAG = getClass().getSimpleName();

    private GNSViewModel gnsViewModel;
    private TsAuthViewModel tsAuthViewModel;
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
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
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

    private void doNext() {
        if (isValid()) {
            UserVO userVO = null;
            try {
                userVO = gnsViewModel.getUserInfoFromDB();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (userVO != null) {
                    tsAuthViewModel.reqGetNewCarOwnerShip(
                            new GetNewCarOwnerShip.Request("getMobileCarOwnerShip",
                                    deviceDTO.getMdn(),
                                    gnsViewModel.getDbUserRepo().getUserVO().getCustNm(),
                                    userVO.getCustMgmtNo(),
                                    ui.etVrn.getText().toString(),
                                    ui.etVin.getText().toString(),
                                    "AG",
                                    "USED"));
                }
            }
        }
    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
        tsAuthViewModel = new ViewModelProvider(this).get(TsAuthViewModel.class);
    }

    @Override
    public void setObserver() {

        tsAuthViewModel.getRES_GET_NEW_CAR_OWNER_SHIP().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getResultCode().equalsIgnoreCase("0000")&&result.data.getData()!=null){

                        String html = getString(R.string.ts_auth_html,
                                result.data.getData().getHashValue(),
                                result.data.getData().getTimeStamp(),
                                result.data.getData().getSvcCodeArr(),
                                result.data.getData().getSvcType(),
                                result.data.getData().getReturnURLA(),
                                result.data.getData().getReturnURLD(),
                                result.data.getData().getCarOwner(),
                                result.data.getData().getCarRegNo(),
                                result.data.getData().getCarVinNo());

                        startActivitySingleTop(new Intent(this, TsAuthWebViewActivity.class).putExtra(KeyNames.KEY_NAME_URL, html), RequestCodes.REQ_CODE_TS_AUTH.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        showProgressDialog(false);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getMessage();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

        tsAuthViewModel.getRES_GET_CHECK_CAR_OWNER_SHIP().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null
                            &&result.data.getData()!=null
                            &&!TextUtils.isEmpty(result.data.getData().getAuthCode())
                            &&(result.data.getData().getAuthCode().equalsIgnoreCase("0000")||result.data.getData().getAuthCode().equalsIgnoreCase("1000"))){
                        showProgressDialog(false);
                        exitPage(getString(R.string.gm_carlst_03_13), ResultCodes.REQ_CODE_TS_AUTH.getCode());
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        if(result.data==null||result.data.getData()==null) serverMsg = result.data.getMessage();
                        else serverMsg = result.data.getData().getAuthMessage();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, getString(R.string.gm_carlst_03_19));
                    }
                    break;
            }
        });
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

            ui.btnCheck.setText(R.string.gm_carlst_03_9);

            if (edits[pos] instanceof TextInputEditText) {
                edits[pos].requestFocus();
            }
        }
    }


    private boolean checkVaildVrn() {

        String vrn = ui.etVrn.getText().toString().trim();

        if (TextUtils.isEmpty(vrn)) {
            ui.lVrn.setError(getString(R.string.gm_carlst_03_12));
            return false;
        } else if (!StringRe2j.matches(vrn, getString(R.string.check_car_vrn))) {
            ui.lVrn.setError(getString(R.string.gm_carlst_p01_8));
            return false;
        } else {
            ui.lVrn.setError(null);
            doTransition(1);
            return true;
        }
    }

    private boolean checkVaildVin() {

        String vin = ui.etVin.getText().toString().trim();

        if (TextUtils.isEmpty(vin) || vin.length() != 17) {
            ui.lVin.setError(getString(R.string.gm_carlst_03_10));
            return false;
        } else {
            ui.lVin.setError(null);
            return true;
        }
    }

    private boolean isValid() {
        for (View view : views) {
            if (view.getVisibility() == View.GONE) {
                switch (view.getId()) {
                    //현재 페이지가 차량번호 입력하는 페이지일경우
                    case R.id.l_vin:
                        return checkVaildVrn() && false;
                }
            }
        }
        return checkVaildVrn() && checkVaildVin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RequestCodes.REQ_CODE_TS_AUTH.getCode()) {
            if (resultCode == Activity.RESULT_OK) {
                GetNewCarOwnerShip.Response retv = null;
                try{
                    retv = tsAuthViewModel.getRES_GET_NEW_CAR_OWNER_SHIP().getValue().data;
                }catch (Exception e){

                }finally{
                    if(retv!=null&&retv.getData()!=null){
                        tsAuthViewModel.reqGetCheckCarOwnerShip(new GetCheckCarOwnerShip.Request("getCheckCarOwnerShip", retv.getData().getEaiSeq(), retv.getData().getCarVinNo(), "AG"));
                    }
                }
                return;
            }
        }

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
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            doNext();
        }
        return false;
    };
}
