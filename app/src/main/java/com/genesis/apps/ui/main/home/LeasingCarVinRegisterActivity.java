package com.genesis.apps.ui.main.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.airbnb.paris.Paris;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.GNS_1011;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.GNSViewModel;
import com.genesis.apps.databinding.ActivityLeasingCarRegisterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.constants.KeyNames.KEY_NAME_CSMR_SCN_CD;
import static com.genesis.apps.comm.model.constants.KeyNames.KEY_NAME_GNS_1001_RESPONSE;
import static com.genesis.apps.comm.model.constants.KeyNames.KEY_NAME_VIN;

/**
 * @author hjpark
 * @brief 렌트/리스 실 운행자 등록 (차대번호 입력)
 */
public class LeasingCarVinRegisterActivity extends SubActivity<ActivityLeasingCarRegisterBinding> {
    private GNSViewModel gnsViewModel;
    private BottomListDialog bottomListDialog;
    //고객구분코드 1 법인 / 14 개인
    private String csmrScnCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResizeScreen();
        setContentView(R.layout.activity_leasing_car_register);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {

        ui.etVin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!TextUtils.isEmpty(editable.toString().trim())) {
                    ui.lVin.setError(null);
                }

            }
        });

    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.btn_check:
                if (checkValidVin()) {
                    ui.btnCheck.setText(R.string.dialog_common_4);
                    ui.lCsmrScnCd.setVisibility(View.VISIBLE);
                    if (checkValidCsmrScnCd()) {
                        gnsViewModel.reqGNS1011(new GNS_1011.Request(APPIAInfo.GM_CARLST_01.getId(), ui.etVin.getText().toString().trim(), csmrScnCd));
                    } else {
                        selectCsmrScnCd();
                    }
                }
                break;
            case R.id.btn_info:
                startActivitySingleTop(new Intent(this, LeasingCarInfoActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
            case R.id.tv_csmr_scn_cd://계약서선택
                selectCsmrScnCd();
                break;
        }

    }


    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setListener(onSingleClickListener);
        gnsViewModel = new ViewModelProvider(this).get(GNSViewModel.class);
    }

    @Override
    public void setObserver() {
        gnsViewModel.getRES_GNS_1011().observe(this, result -> {

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && StringUtil.isValidString(result.data.getRgstPsblYn()).equalsIgnoreCase("Y")) {
                        startActivitySingleTop(new Intent(this, LeasingCarRegisterInputActivity.class)
                                        .putExtra(KEY_NAME_VIN, ui.etVin.getText().toString().trim())
                                        .putExtra(KEY_NAME_CSMR_SCN_CD, csmrScnCd)
                                        .putExtra(KEY_NAME_GNS_1001_RESPONSE, result.data)
                                .addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                                , 0
                                , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        showProgressDialog(false);
                        finish();
                        break;
                    } else if (StringUtil.isValidString(result.data.getRgstPsblYn()).equalsIgnoreCase("N")) {
                        if (StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("9103")) {
                            SnackBarUtil.show(this, getString(R.string.gm_carlst_01_snackbar_2));
                        } else {
                            SnackBarUtil.show(this, getString(R.string.gm_carlst_01_snackbar_3));
                        }
                        showProgressDialog(false);
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
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    /**
     * @brief 계약서 종류 선택
     */
    private void selectCsmrScnCd() {
        final List<String> cdList = Arrays.asList(getResources().getStringArray(R.array.leasing_car_csmr_scn_cd));
        showMapDialog(cdList, R.string.gm_carlst_01_26, dialogInterface -> {
            String result = bottomListDialog.getSelectItem();
            if (!TextUtils.isEmpty(result)) {
                if (result.equalsIgnoreCase(cdList.get(0))) {
                    csmrScnCd = VariableType.LEASING_CAR_CSMR_SCN_CD_14; //개인
                } else {
                    csmrScnCd = VariableType.LEASING_CAR_CSMR_SCN_CD_1; //법인
                }

                ui.tvTitleCsmrScnCd.setVisibility(View.VISIBLE);
                Paris.style(ui.tvCsmrScnCd).apply(R.style.CommonSpinnerItemEnable);
                ui.tvCsmrScnCd.setText(result);

                checkValidCsmrScnCd();
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

    private boolean checkValidCsmrScnCd() {
        if (!TextUtils.isEmpty(csmrScnCd)) {
            ui.tvErrorCsmrScnCd.setVisibility(View.GONE);
            return true;
        } else {
            ui.tvErrorCsmrScnCd.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private boolean checkValidVin() {
        if (TextUtils.isEmpty(ui.etVin.getText().toString().trim())) {
            ui.lVin.requestFocus();
            ui.lVin.setError(getString(R.string.gm_carlst_01_23));
            return false;
        } else if (ui.etVin.getText().toString().trim().length() != 17) {
            ui.lVin.requestFocus();
            ui.lVin.setError(getString(R.string.gm_carlst_01_45));
            return false;
        } else {
            ui.etVin.setText(ui.etVin.getText().toString().toUpperCase());
            ui.lVin.setError(null);
            ui.lVin.clearFocus();
            return true;
        }
    }
}
