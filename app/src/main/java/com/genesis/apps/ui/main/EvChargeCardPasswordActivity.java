package com.genesis.apps.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.DTW_1002;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.comm.viewmodel.DTWViewModel;
import com.genesis.apps.databinding.ActivityEvChargeCardPasswordBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

public class EvChargeCardPasswordActivity extends SubActivity<ActivityEvChargeCardPasswordBinding> {

    private DTWViewModel dtwViewModel;
    private VehicleVO mainVehicle;

    private final int STEP_2_1 = 3;
    private final int STEP_2_2 = 4;
    private final int STEP_2_3 = 5;
    private int step = STEP_2_1;
    private ImageView[] ivList;

    private String newPwd = "";
//    private String currPwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ev_charge_card_password);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        initData();
    }

    private void initView() {
        ivList = new ImageView[]{ui.ivInput1, ui.ivInput2, ui.ivInput3, ui.ivInput4};
        ui.etHidden.addTextChangedListener(textWatcher);
        setView(STEP_2_1);

        SoftKeyboardUtil.showKeyboard(this);
    }

    private void initData() {
        try {
            mainVehicle = dtwViewModel.getMainVehicleFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        dtwViewModel = new ViewModelProvider(this).get(DTWViewModel.class);
    }

    @Override
    public void setObserver() {
        dtwViewModel.getRES_DTW_1002().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        exitPage("비밀번호가 정상적으로 변경되었습니다.", ResultCodes.REQ_CODE_NORMAL.getCode());
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
                        if (TextUtils.isEmpty(serverMsg))
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.l_input:
                SoftKeyboardUtil.showKeyboard(this);
                break;
        }
    }

    private void setView(int step) {
        this.step = step;
        switch (step) {
//            case STEP_1_1:
//                ui.etHidden.setTag(R.id.et_reject, true);
//                ui.etHidden.setText("");
//                ui.tvMsg1.setText(R.string.mg_member03_2);
//                ui.ivInput1.setImageResource(R.drawable.ic_password);
//                ui.ivInput2.setImageResource(R.drawable.ic_password);
//                ui.ivInput3.setImageResource(R.drawable.ic_password);
//                ui.ivInput4.setImageResource(R.drawable.ic_password);
//                ui.tvMsgError.setVisibility(View.INVISIBLE);
//                break;
//            case STEP_1_2:
//                ui.etHidden.setTag(R.id.et_reject, true);
//                ui.etHidden.setText("");
//                ui.tvMsg1.setText(R.string.mg_member03_2);
//                ui.ivInput1.setImageResource(R.drawable.ic_password_error);
//                ui.ivInput2.setImageResource(R.drawable.ic_password_error);
//                ui.ivInput3.setImageResource(R.drawable.ic_password_error);
//                ui.ivInput4.setImageResource(R.drawable.ic_password_error);
//                ui.tvMsgError.setVisibility(View.VISIBLE);
//                ui.tvMsgError.setText(R.string.mg_member03_3);
//                VibratorUtil.makeMeShake(ui.tvMsgError, 20, 5);
//                break;
            case STEP_2_1:
                ui.etHidden.setTag(R.id.et_reject, true);
                ui.etHidden.setText("");
                ui.lTitle.setValue(getString(R.string.pay05_psw01_1));
                ui.tvMsg1.setText(R.string.pay05_psw01_2);
                ui.ivInput1.setImageResource(R.drawable.ic_password);
                ui.ivInput2.setImageResource(R.drawable.ic_password);
                ui.ivInput3.setImageResource(R.drawable.ic_password);
                ui.ivInput4.setImageResource(R.drawable.ic_password);
                ui.tvMsgError.setText("\n");
                break;
            case STEP_2_2:
                ui.etHidden.setTag(R.id.et_reject, true);
                ui.etHidden.setText("");
                ui.lTitle.setValue(getString(R.string.pay05_psw02_1));
                ui.tvMsg1.setText(R.string.pay05_psw02_2);
                ui.ivInput1.setImageResource(R.drawable.ic_password);
                ui.ivInput2.setImageResource(R.drawable.ic_password);
                ui.ivInput3.setImageResource(R.drawable.ic_password);
                ui.ivInput4.setImageResource(R.drawable.ic_password);
                ui.tvMsgError.setText("\n");
                break;
            case STEP_2_3:
                ui.etHidden.setTag(R.id.et_reject, true);
                ui.etHidden.setText("");
                ui.lTitle.setValue(getString(R.string.pay05_psw01_1));
                ui.tvMsg1.setText(R.string.pay05_psw01_2);
                ui.ivInput1.setImageResource(R.drawable.ic_password_error);
                ui.ivInput2.setImageResource(R.drawable.ic_password_error);
                ui.ivInput3.setImageResource(R.drawable.ic_password_error);
                ui.ivInput4.setImageResource(R.drawable.ic_password_error);
                ui.tvMsgError.setText(R.string.pay05_psw02_p01_3);
                VibratorUtil.makeMeShake(ui.tvMsgError, 20, 5);
                break;
            default:
                break;
        }
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (Boolean.parseBoolean(ui.etHidden.getTag(R.id.et_reject).toString())) {
                ui.etHidden.setTag(R.id.et_reject, false);
                return;
            }

            if (charSequence.length() == ivList.length) {
                switch (step) {
//                    case STEP_1_1:
//                    case STEP_1_2:
//                        currPwd = charSequence.toString();
//                        setView(STEP_2_1);
//                        return;
                    case STEP_2_1:
                    case STEP_2_3:
                        newPwd = charSequence.toString();
                        setView(STEP_2_2);
                        return;
                    case STEP_2_2:
                        if (newPwd.equalsIgnoreCase(charSequence.toString())) {
                            //신규비밀번호가 일치하는 경우
                            clearKeypad();
                            dtwViewModel.reqDTW1002(new DTW_1002.Request(APPIAInfo.PAY05_PSW01.getId(), mainVehicle.getVin(), newPwd));
                            break;
                        } else {
                            //일치하지 않는 경우
                            newPwd = "";
                            setView(STEP_2_3);
                            return;
                        }
                    default:
                        break;
                }
            } else {
                switch (step) {
//                    case STEP_1_2:
                    case STEP_2_3:
//                        ui.tvMsgError.setVisibility(View.INVISIBLE);
                        ui.tvMsgError.setText("\n");
                        break;
                }
            }

            for (int pos = 0; pos < ivList.length; pos++) {
                if (pos < charSequence.length()) {
                    ivList[pos].setImageResource(R.drawable.ic_password_input);
                } else {
                    ivList[pos].setImageResource(R.drawable.ic_password);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

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
        clearKeypad();

        MiddleDialog.dialogServiceRemoteTwoButton(this, R.string.pay05_psw01_p01_1, R.string.pay05_psw01_p01_2,() -> {
            finish();
            closeTransition();
        }, () -> {

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void clearKeypad() {
        SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
    }
}
