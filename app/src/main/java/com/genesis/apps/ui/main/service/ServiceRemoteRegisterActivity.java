package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.api.gra.RMT_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.viewmodel.RMTViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityServiceRemoteRegisterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

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

    private RMTViewModel rmtViewModel;
    private SOSViewModel sosViewModel;

    private REGISTER_STEP currentStep = REGISTER_STEP.INPUT_PHONE;

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
                            "\"carRgstNo\":\"\"," +
                            "\"celphNo\":\"\"," +
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

        if (TextUtils.isEmpty(phoneNum)) {
            // 휴대폰 번호가 없는 경우.
            executeStep(REGISTER_STEP.INPUT_PHONE);
            return;
        } else {
            // 휴대폰 번호가 있는 경우.
            ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.setText(phoneNum);
            ui.lServiceRemoteStep1.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
        }

        String carNum = data.getCarRgstNo();
        if (!TextUtils.isEmpty(carNum)) {
            // 차량 번호가 있는 경우.
            ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.setText(carNum);
            ui.lServiceRemoteStep2.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
        }

        executeStep(checkStep());
    }

    private void executeStep(REGISTER_STEP step) {
        switch (step) {
            case INPUT_PHONE: {
                ui.lServiceRemoteStep1.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
                ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.requestFocusFromTouch();
                ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.setSelected(true);
                ui.lServiceRemoteStep1.tvServiceRemoteRegisterStepGuide.setVisibility(View.VISIBLE);
                break;
            }
            case INPUT_CAR_NUM: {
                break;
            }
            case SERVICE_TYPE: {
                break;
            }
            case SERVICE_TIME: {
                break;
            }
            case COMPLETE: {
                break;
            }
        }

        showStepGuide(step.guideResId);
    }

    private REGISTER_STEP checkStep() {
        String phoneNum = ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            return REGISTER_STEP.INPUT_PHONE;
        }

        String carNum = ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.getText().toString();
        if (TextUtils.isEmpty(carNum)) {
            return REGISTER_STEP.INPUT_CAR_NUM;
        }

        return REGISTER_STEP.COMPLETE;
    }

    private void showStepGuide(@StringRes int guide) {
        ui.tvServiceRemoteRegisterGuide.setText(guide);
    }
} // end of class ServiceREmoteRegisterActivity
