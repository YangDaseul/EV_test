package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.api.gra.RMT_1001;
import com.genesis.apps.comm.viewmodel.RMTViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityServiceRemoteRegisterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.BottomRecyclerDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Ki-man, Kim on 12/10/20
 */
public class ServiceRemoteRegisterActivity extends SubActivity<ActivityServiceRemoteRegisterBinding> {


    enum REGISTER_STEP {
        INPUT_PHONE(R.string.sm_remote01_phone_number_guide),
        INPUT_CAR_NUM(R.string.sm_remote01_car_number_guide),
        SERVICE_TYPE(R.string.sm_remote01_service_type_guide),
        SERVICE_TIME(R.string.sm_remote01_service_time_guide),
        COMPLETE(R.string.sm_remote01_input_complete);

        private @StringRes
        final int guideResId;

        REGISTER_STEP(@StringRes int guideResId) {
            this.guideResId = guideResId;
        }
    } // end of enum class REGISTER_STEP

    /**
     * 고장 코드 Enum Class.
     */
    enum FLT_CODE {
        CODE_1000("1000", R.string.sm_remote01_fit_code_1000),
        CODE_2000("2000", R.string.sm_remote01_fit_code_2000),
        CODE_3000("3000", R.string.sm_remote01_fit_code_3000),
        CODE_4000("4000", R.string.sm_remote01_fit_code_4000),
        CODE_5000("5000", R.string.sm_remote01_fit_code_5000),
        CODE_6000("6000", R.string.sm_remote01_fit_code_6000),
        CODE_7000("7000", R.string.sm_remote01_fit_code_7000),
        CODE_8000("8000", R.string.sm_remote01_fit_code_8000);

        private final String code;
        private @StringRes
        final int messageResId;

        FLT_CODE(String code, @StringRes int messageResId) {
            this.code = code;
            this.messageResId = messageResId;
        }

        public int messageResId() {
            return this.messageResId;
        }
    } // end of enum class FLT_CODE

    public enum WRN_LGHT_CODE {
        CODE_3100("3100", R.string.sm_remote01_wrn_lght_code_3100, R.drawable.selector_ic_eps),
        CODE_3300("3300", R.string.sm_remote01_wrn_lght_code_3300, R.drawable.selector_ic_brake),
        CODE_3400("3400", R.string.sm_remote01_wrn_lght_code_3400, R.drawable.selector_ic_water),
        CODE_3500("3500", R.string.sm_remote01_wrn_lght_code_3500, R.drawable.selector_ic_tpms),
        CODE_3600("3600", R.string.sm_remote01_wrn_lght_code_3600, R.drawable.selector_ic_engine),
        CODE_3700("3700", R.string.sm_remote01_wrn_lght_code_3700, R.drawable.selector_ic_battery),
        CODE_3200("3200", R.string.sm_remote01_wrn_lght_code_3200, R.drawable.selector_ic_brake_esp);

        private final String code;
        private @StringRes
        final int messageResId;
        private @DrawableRes
        final int iconResId;

        WRN_LGHT_CODE(String code, @StringRes int messageResId, @DrawableRes int iconResId) {
            this.code = code;
            this.messageResId = messageResId;
            this.iconResId = iconResId;
        }

        public int messageResId() {
            return this.messageResId;
        }

        public int iconResId() {
            return this.iconResId;
        }
    }

    private RMTViewModel rmtViewModel;
    private SOSViewModel sosViewModel;

    private REGISTER_STEP currentStep = REGISTER_STEP.INPUT_PHONE;

    /**
     * 차량 문제 코드.
     */
    private FLT_CODE fltCd = null;

    /**
     * 경고등 코드.
     */
    private WRN_LGHT_CODE wrnLghtCd = null;

    /**
     * 예약 시간.
     */
    private String rsrvMiss;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_remote_register);
        getDataFromIntent();
        setViewModel();
        setObserver();

        try {
            rmtViewModel.reqRMT1001(new RMT_1001.Request(APPIAInfo.R_REMOTE01.getId(), rmtViewModel.getMainVehicle().getVin()));
        } catch (ExecutionException ee) {
            // TODO : 차대번호 조회 오류 처리 필요.
        } catch (InterruptedException ie) {
            // TODO : 차대번호 조회 오류 처리 필요.
        }
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        Log.d("FID", "test :: onClickCommon :: view=" + v);
        if (v == ui.lServiceRemoteStep3.lServiceRemoteRegisterStepContainer) {
            // 차량 문제 선택.
            showSelectFltCd();
        } else if (v == ui.lServiceRemoteStep4.lServiceRemoteRegisterStepContainer) {
            // 서비스 시간 선택.
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        rmtViewModel = new ViewModelProvider(this).get(RMTViewModel.class);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
    }

    @Override
    public void setObserver() {
        rmtViewModel.getRES_RMT_1001().observe(this, result -> {
            Log.d("FID", "test :: getRES_RMT_1001 :: status=" + result.status);
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case ERROR:
                case SUCCESS: {
                    showProgressDialog(false);
                    RMT_1001.Response response = result.data;

                    String dummyData = "{" +
                            "\"rtCd\":\"0000\"," +
                            "\"rtMsg\":\"Success\"," +
                            "\"rmtExitYn\":\"N\"," +
                            "\"carRgstNo\":\"123가4565\"," +
                            "\"celphNo\":\"010-1234-5678\"," +
                            "\"sosStusCd\":\"\"," +
                            "\"tmpAcptNo\":\"\"" +
                            "}";
                    response = new Gson().fromJson(dummyData, RMT_1001.Response.class);

                    Log.d("FID", "test :: getRES_RMT_1001 :: rtCd=" + response.getRtCd());
                    Log.d("FID", "test :: getRES_RMT_1001 :: rmtExitYn=" + response.getRmtExitYn());
                    Log.d("FID", "test :: getRES_RMT_1001 :: message=" + response.getRtMsg());

                    if (response != null) {
                        if (BaseResponse.RETURN_CODE_SUCC.equals(response.getRtCd())) {
                            // 성공.
                            if ("N".equalsIgnoreCase(response.getRmtExitYn())) {
                                // 신청건이 없는 경우. - 신청 프로세스 진행.
                                initView(response);
                            } else {
                                // 신청건이 있는 경우. - 안내 팝업 표시.
                            }
                        } else {
                            // 실패.
                            // 사유에 대해 서버에서 온 메시지를 표시.
                            MiddleDialog.dialogServiceRemoteRegisterErr(this, response.getRtMsg(), () -> finish());
                        }
                    } else {
                        // TODO : 조회된 데이터가 없어 예외처리 필요.
                    }
                    break;
                }
//                case ERROR: {
//                    showProgressDialog(false);
//                    break;
//                }
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initView(RMT_1001.Response data) {
        String phoneNum = data.getCelphNo();
        // 휴대폰 번호 입력항목은 기본 노출
        ui.lServiceRemoteStep1.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(phoneNum)) {
            // 휴대폰 번호가 있는 경우.
            ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.setText(phoneNum);

            // 차량 입력 영역을 활성화
            ui.lServiceRemoteStep2.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
        }

        String carNum = data.getCarRgstNo();
        if (!TextUtils.isEmpty(carNum)) {
            // 차량 번호가 있는 경우.
            ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.setText(carNum);

            // 고장 선택 항목을 활성화.
            ui.lServiceRemoteStep3.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
        }

        ui.lServiceRemoteStep3.lServiceRemoteRegisterStepContainer.setOnClickListener(view -> this.onSingleClickListener.onClick(view));
        ui.lServiceRemoteStep4.lServiceRemoteRegisterStepContainer.setOnClickListener(view -> this.onSingleClickListener.onClick(view));

        executeStep(checkStep());
    }

    private void executeStep(REGISTER_STEP step) {
        switch (step) {
            case INPUT_PHONE: {
                ui.lServiceRemoteStep1.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
                ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.requestFocusFromTouch();
                break;
            }
            case INPUT_CAR_NUM: {
                ui.lServiceRemoteStep2.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
                ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.requestFocusFromTouch();
                break;
            }
            case SERVICE_TYPE: {
                ui.lServiceRemoteStep3.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
                break;
            }
            case SERVICE_TIME: {
                ui.lServiceRemoteStep4.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
                break;
            }
            case COMPLETE: {
                break;
            }
        }

        showStepGuide(step.guideResId);
    }

    private REGISTER_STEP checkStep() {
        boolean isEmptyPhoneNum = TextUtils.isEmpty(ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.getText().toString());
        ui.lServiceRemoteStep1.tvServiceRemoteRegisterStepGuide.setVisibility(isEmptyPhoneNum ? View.VISIBLE : View.INVISIBLE);
        ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.setSelected(isEmptyPhoneNum);

        boolean isEmptyCarNum = TextUtils.isEmpty(ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.getText().toString());
        ui.lServiceRemoteStep2.tvServiceRemoteRegisterStepGuide.setVisibility(isEmptyCarNum ? View.VISIBLE : View.INVISIBLE);
        ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.setSelected(isEmptyCarNum);

        boolean isEmptyErrorCode = fltCd == null;
        ui.lServiceRemoteStep3.tvServiceRemoteRegisterStepInput.setEnabled(isEmptyErrorCode);

        boolean isServiceTime = TextUtils.isEmpty(rsrvMiss);
        ui.lServiceRemoteStep3.tvServiceRemoteRegisterStepInput.setEnabled(isServiceTime);

        if (isEmptyPhoneNum) {
            return REGISTER_STEP.INPUT_PHONE;
        } else if (isEmptyPhoneNum) {
            return REGISTER_STEP.INPUT_CAR_NUM;
        } else if (isEmptyErrorCode) {
            return REGISTER_STEP.SERVICE_TYPE;
        } else if (isServiceTime) {
            return REGISTER_STEP.SERVICE_TIME;
        }
        return REGISTER_STEP.COMPLETE;
    }

    private void showStepGuide(@StringRes int guide) {
        ui.tvServiceRemoteRegisterGuide.setText(guide);
    }

    private void showSelectFltCd() {
        Log.d("FID", "test :: showSelectFltCd");
        ArrayList<String> fltCodes = new ArrayList<>();
        Stream.of(FLT_CODE.values())
                .map(FLT_CODE::messageResId)
                .forEach(id -> fltCodes.add(getString(id)));
        final BottomListDialog bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dialogInterface -> {
            String result = bottomListDialog.getSelectItem();

            if (!TextUtils.isEmpty(result)) {
                fltCd = Stream.of(FLT_CODE.values()).filter(item -> result.equals(getString(item.messageResId))).findFirst().get();
                ui.lServiceRemoteStep3.tvServiceRemoteRegisterStepInput.setText(result);
            }

            if (fltCd == FLT_CODE.CODE_3000) {
                // 경고등 점등인 경우 - 경고등 선택 다이얼로그 추가 표시.
                showSelectWrnLghtCd();
            } else {
                executeStep(checkStep());
            }
        });
        bottomListDialog.setDatas(fltCodes);
        bottomListDialog.setTitle(getString(R.string.sm_emgc01_23));
        bottomListDialog.show();
    }

    private void showSelectWrnLghtCd() {
        Log.d("FID", "test :: showSelectWrnLghtCd");
        WrnLghtCodeListAdapter adapter = new WrnLghtCodeListAdapter(Stream.of(WRN_LGHT_CODE.values()).collect(Collectors.toList()));
        final BottomRecyclerDialog dialog = new BottomRecyclerDialog.Builder(this)
                .setTitle(R.string.sm_romte01_p01_12)
                .setAdapter(adapter)
                .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                .build();
        adapter.setListener(selectItem -> {
            Log.d("FID", "test :: item select :: " + selectItem);
            wrnLghtCd = selectItem;
            dialog.dismiss();
        });
        dialog.show();
    }
} // end of class ServiceREmoteRegisterActivity
