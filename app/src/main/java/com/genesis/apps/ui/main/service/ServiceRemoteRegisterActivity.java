package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.api.gra.RMT_1001;
import com.genesis.apps.comm.model.api.gra.RMT_1002;
import com.genesis.apps.comm.model.api.gra.SOS_1004;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.viewmodel.RMTViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityServiceRemoteRegisterBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.bottom.BottomRecyclerDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_REMOTE_RES_CODE_2401;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_REMOTE_RES_CODE_2402;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_REMOTE_RES_CODE_2403;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_REMOTE_RES_CODE_2404;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_REMOTE_RES_CODE_2405;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_SOS_STATUS_CODE_R;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_SOS_STATUS_CODE_S;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_SOS_STATUS_CODE_W;

/**
 * Class Name : ServiceRemoteRegisterActivity
 * 원격 진단 신청 화면 Activity.
 * <p>
 * Created by Ki-man, Kim on 12/10/20
 */
public class ServiceRemoteRegisterActivity extends GpsBaseActivity<ActivityServiceRemoteRegisterBinding> {


    /**
     * 원격 진단 신청 단계.
     */
    enum REGISTER_STEP {
        /**
         * 휴대폰 번호 입력.
         */
        INPUT_PHONE(R.string.sm_remote01_phone_number_guide),
        /**
         * 차량 번호 입력.
         */
        INPUT_CAR_NUM(R.string.sm_remote01_car_number_guide),
        /**
         * 차량 문제 선택.
         */
        SERVICE_TYPE(R.string.sm_remote01_service_type_guide),
        /**
         * 원격 진단 이용 시간 선택.
         */
        SERVICE_TIME(R.string.sm_remote01_service_time_guide),
        /**
         * 신청을 위한 정보 입력 완료 상태.
         */
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
        /**
         * 차량 문제 : 차량 시동이 걸리지 않아요.
         */
        CODE_1000("1000", R.string.sm_remote01_fit_code_1000),
        /**
         * 차량 문제 : 가속이 너무 느려요.
         */
        CODE_2000("2000", R.string.sm_remote01_fit_code_2000),
        /**
         * 차량 문제 : 계기판에 경고등 켜졌어요
         */
        CODE_3000("3000", R.string.sm_remote01_fit_code_3000),
        /**
         * 차량 문제 : 기어가 작동하지 않아요.
         */
        CODE_4000("4000", R.string.sm_remote01_fit_code_4000),
        /**
         * 차량 문제 : 주차 브레이크가 풀리지 않아요.
         */
        CODE_5000("5000", R.string.sm_remote01_fit_code_5000),
        /**
         * 차량 문제 : 엔진이 과열되었어요.
         */
        CODE_6000("6000", R.string.sm_remote01_fit_code_6000),
        /**
         * 차량 문제 : 엔진 떨림이 심해요.
         */
        CODE_7000("7000", R.string.sm_remote01_fit_code_7000),
        /**
         * 차량 문제 : 차량을 주행할 수 없어요.
         */
        CODE_8000("8000", R.string.sm_remote01_fit_code_8000);

        /**
         * 차량 문제 코드.
         */
        private final String code;
        /**
         * 차량 문제 표시 문자열 String Resource ID.
         */
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

    /**
     * 차량 문제 중 경고등 코드.
     */
    public enum WRN_LGHT_CODE {
        /**
         * 경코등 코드 : EPS 경고등
         */
        CODE_3100("3100", R.string.sm_remote01_wrn_lght_code_3100, R.drawable.selector_ic_eps),
        /**
         * 경코등 코드 : 브레이크 경고등
         */
        CODE_3300("3300", R.string.sm_remote01_wrn_lght_code_3300, R.drawable.selector_ic_brake),
        /**
         * 경코등 코드 : 수분분리 경고등
         */
        CODE_3400("3400", R.string.sm_remote01_wrn_lght_code_3400, R.drawable.selector_ic_water),
        /**
         * 경코등 코드 : TPMS 경고등
         */
        CODE_3500("3500", R.string.sm_remote01_wrn_lght_code_3500, R.drawable.selector_ic_tpms),
        /**
         * 경코등 코드 : 엔진체크 경고등
         */
        CODE_3600("3600", R.string.sm_remote01_wrn_lght_code_3600, R.drawable.selector_ic_engine),
        /**
         * 경코등 코드 : 충전 경고등
         */
        CODE_3700("3700", R.string.sm_remote01_wrn_lght_code_3700, R.drawable.selector_ic_battery),
        /**
         * 경코등 코드 : VDC/ESP 경고등
         */
        CODE_3200("3200", R.string.sm_remote01_wrn_lght_code_3200, R.drawable.selector_ic_brake_esp);

        /**
         * 경고등 코드
         */
        private final String code;
        /**
         * 경고등 표시 문자열 String Resource ID.
         */
        private @StringRes
        final int messageResId;
        /**
         * 경고등 표시 아이콘 Drawable Resource ID.
         */
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
    } // end of enum class WRN_LGHT_CODE

    private RMTViewModel rmtViewModel;
    private SOSViewModel sosViewModel;

    /**
     * 시간 선택을 위한 {@link com.genesis.apps.ui.main.service.ServiceRemoteTimeGridAdapter.TimeVO} 목록 정의 Field.
     * 08:30 ~ 15:30 사이의 시간을 정의.
     */
    private final List<ServiceRemoteTimeGridAdapter.TimeVO> ServiceTimes = Arrays.asList(
            new ServiceRemoteTimeGridAdapter.TimeVO(8, 30),
            new ServiceRemoteTimeGridAdapter.TimeVO(9, 0), new ServiceRemoteTimeGridAdapter.TimeVO(9, 30),
            new ServiceRemoteTimeGridAdapter.TimeVO(10, 0), new ServiceRemoteTimeGridAdapter.TimeVO(10, 30),
            new ServiceRemoteTimeGridAdapter.TimeVO(11, 0), new ServiceRemoteTimeGridAdapter.TimeVO(11, 30),
            new ServiceRemoteTimeGridAdapter.TimeVO(12, 0), new ServiceRemoteTimeGridAdapter.TimeVO(12, 30),
            new ServiceRemoteTimeGridAdapter.TimeVO(13, 0), new ServiceRemoteTimeGridAdapter.TimeVO(13, 30),
            new ServiceRemoteTimeGridAdapter.TimeVO(14, 0), new ServiceRemoteTimeGridAdapter.TimeVO(14, 30),
            new ServiceRemoteTimeGridAdapter.TimeVO(15, 0), new ServiceRemoteTimeGridAdapter.TimeVO(15, 30)
    );

    // 원격 신청을 위한 가능 여부 데이터.
    private RMT_1001.Response data;

    // 현재 모바일 기기 위치.
    private Double[] myPosition = new Double[2];

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

    private String vin;

    // FIXME : 해당 버튼 위젯은 바인딩이 되지 않아 우선 수동으로 매칭처리. - 나중에 원인 찾아 수정 필요.
    private Button btnNextStep;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_remote_register);
        btnNextStep = findViewById(R.id.btn_service_remote_register_step);
        getDataFromIntent();
        setViewModel();
        setObserver();
        reqMyLocation();

        MiddleDialog.dialogServiceRemoteInfo(this, () -> {
            try {
                vin = rmtViewModel.getMainVehicle().getVin();
                rmtViewModel.reqRMT1001(new RMT_1001.Request(APPIAInfo.R_REMOTE01.getId(), vin));
            } catch (ExecutionException ee) {
                // TODO : 차대번호 조회 오류 처리 필요.
            } catch (InterruptedException ie) {
                // TODO : 차대번호 조회 오류 처리 필요.
            }
        }, () -> exitPage("", 0));//아니오 클릭시 페이지 종료

    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        if (v == ui.lServiceRemoteStep3.lServiceRemoteRegisterStepContainer) {
            // 차량 문제 선택.
            showSelectFltCd();
        } else if (v == ui.lServiceRemoteStep4.lServiceRemoteRegisterStepContainer) {
            // 서비스 시간 선택.
            showSelectTime();
        } else if (v == btnNextStep) {
            // 서비스 신청 및 입력 단계 버튼.
            REGISTER_STEP step = checkStep();

            if (step == REGISTER_STEP.COMPLETE) {
                // 입력이 완료 되었다면 신청처리.
                rmtViewModel.reqRMT1002(new RMT_1002.Request(
                        APPIAInfo.R_REMOTE01.getId(),
                        vin,
                        ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.getText().toString(),
                        ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.getText().toString(),
                        fltCd.code,
                        fltCd == FLT_CODE.CODE_3000 ? wrnLghtCd.code : "",
                        rsrvMiss,
                        String.valueOf(myPosition[0]),
                        String.valueOf(myPosition[1])
                ));
            } else {
                // 입력이 완료되지 않았다면 입력해야 할 부분으로 이동.
                executeStep(step);
            }
        }
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        rmtViewModel = new ViewModelProvider(this).get(RMTViewModel.class);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
    }

    @Override
    public void setObserver() {
        rmtViewModel.getRES_RMT_1001().observe(this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    data = result.data;

                    if (data != null) {
                        if (BaseResponse.RETURN_CODE_SUCC.equals(data.getRtCd())) {
                            // 성공.
                            if ("N".equalsIgnoreCase(data.getRmtExitYn())) {
                                // 신청건이 없는 경우.
                                initView();
                            } else {
                                // 신청건이 있는 경우. - 안내 팝업 표시.
                                MiddleDialog.dialogServiceRemoteOneButton(
                                        this,
                                        R.string.sm_remote01_dialog_title_error,
                                        R.string.sm_remote01_msg_error_2401,
                                        () -> exitPage("", 0));
                            }
                        } else if (SERVICE_REMOTE_RES_CODE_2401.equals(data.getRtCd())) {
                            // 원격 신청이 되어 있거나 진단 진행 중인경우.
                            MiddleDialog.dialogServiceRemoteOneButton(
                                    this,
                                    R.string.sm_remote01_dialog_title_error,
                                    R.string.sm_remote01_msg_error_2401,
                                    () -> exitPage("", 0));
                        } else if (SERVICE_REMOTE_RES_CODE_2402.equals(data.getRtCd())) {
                            // 원격 진단 이용 대상 차량이 아닌 경우.
                            MiddleDialog.dialogServiceRemoteOneButton(
                                    this,
                                    R.string.sm_remote01_dialog_title_error,
                                    R.string.sm_remote01_msg_error_2402,
                                    () -> exitPage("", 0));
                        } else if (SERVICE_REMOTE_RES_CODE_2403.equals(data.getRtCd())) {
                            // 원격 진단 이용 가능 시간이 아님.
                            MiddleDialog.dialogServiceRemoteNotServiceTime(
                                    this,
                                    () -> exitPage("", 0));
                        } else if (SERVICE_REMOTE_RES_CODE_2404.equals(data.getRtCd())) {
                            // GCS 서비스 가입 회원이 아닌 경우.
                            MiddleDialog.dialogServiceRemoteOneButton(
                                    this,
                                    R.string.sm_remote01_dialog_title_error,
                                    R.string.sm_remote01_msg_error_2404,
                                    () -> exitPage("", 0));
                        } else if (SERVICE_REMOTE_RES_CODE_2405.equals(data.getRtCd())) {
                            // 긴급 출동이 접수 또는 진행중인 경우.
                            // 긴급 출동 여부 체크.
                            String sosStatusCd = data.getSosStusCd();
                            if (SERVICE_SOS_STATUS_CODE_R.equals(sosStatusCd) || SERVICE_SOS_STATUS_CODE_W.equals(sosStatusCd)) {
                                // 긴급 출동 신청, 접수 상태. - 팝업 표시.
                                final String tmpAcpNo = data.getTmpAcptNo();
                                MiddleDialog.dialogServiceRemoteTwoButton(
                                        this,
                                        R.string.sm_emg01_p04_1_1,
                                        R.string.sm_emg01_p04_1_2,
                                        () -> {
                                            // 긴급 출동 접수 취소 처리.
                                            sosViewModel.reqSOS1004(new SOS_1004.Request(APPIAInfo.R_REMOTE01.getId(), tmpAcpNo));
                                        }, () -> exitPage("", 0));
                            } else if (SERVICE_SOS_STATUS_CODE_S.equals(sosStatusCd)) {
                                // 긴급 출동 출동 상태. - 출동 상태 팝업 표시.
                                MiddleDialog.dialogServiceRemoteOneButton(
                                        this,
                                        R.string.sm_emg01_p04_1_1,
                                        R.string.sm_emgc01_p05_2,
                                        () -> exitPage("", 0));
                            }
                        } else {
                            // 실패.
                            // 사유에 대해 서버에서 온 메시지를 표시.
                            MiddleDialog.dialogServiceRemoteRegisterErr(this, data.getRtMsg(), () -> exitPage("", 0));
                        }
                    } else {
                        // 조회된 데이터가 없어 예외처리 필요.
                        // 통신 오류 안내.
                        exitPage(getString(R.string.r_flaw06_p02_snackbar_1), ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                    }
                    break;
                }
                case ERROR: {
                    showProgressDialog(false);
                    // 통신 오류 안내.
                    exitPage(getString(R.string.r_flaw06_p02_snackbar_1), ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                    break;
                }
            }
        });

        rmtViewModel.getRES_RMT_1002().observe(this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    SnackBarUtil.show(this, getString(R.string.sm_remote01_msg_register_success));
                    startActivitySingleTop(
                            new Intent(this, ServiceRemoteListActivity.class),
                            RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                            VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE
                    );
                    break;
                }
                case ERROR: {
                    showProgressDialog(false);
                    // 통신 오류 안내.
                    exitPage(getString(R.string.r_flaw06_p02_snackbar_1), ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                    break;
                }
            }
        });

        sosViewModel.getRES_SOS_1004().observe(this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    // 긴급 출동 취소 처리 성공. 안내 표시 및 원격 진단 서비스 시작.
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000")) {
                        if (result.data.getSuccYn().equalsIgnoreCase("Y")) {
                            SnackBarUtil.show(this, getString(R.string.sm_emgc01_p04_snackbar_2));
                            initView();
                        } else {
                            exitPage(getString(R.string.sm_emgc01_p04_snackbar_1), ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                        }
                    } else {
                        showProgressDialog(false);
                        String serverMsg = "";
                        try {
                            serverMsg = result.data.getRtMsg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            exitPage((TextUtils.isEmpty(serverMsg) ? getString(R.string.sm_emgc02_p01_snackbar_1) : serverMsg), ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                        }
                    }
                    break;
                }
                case ERROR: {
                    showProgressDialog(false);
                    // 통신 오류 안내.
                    exitPage(getString(R.string.r_flaw06_p02_snackbar_1), ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                    break;
                }
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onBackPressed() {
        MiddleDialog.dialogRemoteExit(
                this,
                () -> exitPage("", 0)
                , null
        );
    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    /**
     * View 초기화 함수.
     */
    private void initView() {
        if (data == null) {
            return;
        }

        String phoneNum = data.getCelphNo();
        // 휴대폰 번호 입력항목은 기본 노출
        ui.lServiceRemoteStep1.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(phoneNum)) {
            // 휴대폰 번호가 있는 경우.
            ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.setText(PhoneNumberUtils.formatNumber(phoneNum, Locale.getDefault().getCountry()));

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

        btnNextStep.setOnClickListener(view -> this.onSingleClickListener.onClick(view));

        executeStep(checkStep());
    }

    /**
     * 나의 위치 GPS 조회 함수.
     */
    private void reqMyLocation() {
        showProgressDialog(true);
        findMyLocation(location -> {
            showProgressDialog(false);
            if (location == null) {
                exitPage("위치 정보를 불러올 수 없습니다. GPS 상태를 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                return;
            }

            runOnUiThread(() -> {
                //현재 단말기 위치 정보 저장
                myPosition[0] = location.getLongitude();
                myPosition[1] = location.getLatitude();
            });
        }, 5000, GpsRetType.GPS_RETURN_HIGH, false);
    }

    /**
     * 원격 진단 신청 단계별 실행 함수.
     *
     * @param step 실행할 단계 {@link REGISTER_STEP}
     */
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
                ui.lServiceRemoteStep3.lServiceRemoteRegisterStepContainer.performClick();
                break;
            }
            case SERVICE_TIME: {
                ui.lServiceRemoteStep4.lServiceRemoteRegisterStepContainer.setVisibility(View.VISIBLE);
                ui.lServiceRemoteStep4.lServiceRemoteRegisterStepContainer.performClick();
                break;
            }
            case COMPLETE: {
                break;
            }
        }

        showStepGuide(step.guideResId);
    }

    /**
     * 원격 진단 신청을 위한 정보 체크 함수.
     * 체크에 따라 입력이 필요한 단계의 {@link REGISTER_STEP} 객체 반환.
     * 모두 입력이 되었을 경우 {@link REGISTER_STEP#COMPLETE} 반환.
     *
     * @return 체크 결과 {@link REGISTER_STEP}
     */
    private REGISTER_STEP checkStep() {
        // 휴대폰 번호가 입력되어 있는지 체크.
        boolean isEmptyPhoneNum;
        try {
            String phoneNum = ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.getText().toString().trim();
            if (TextUtils.isEmpty(phoneNum)) {
                // 휴대폰 번호가 입력되어 있지 않음.
                isEmptyPhoneNum = true;
            } else {
                // 휴대폰 번호가 입력되어 있음. - 휴대폰 번호 형식 체크.
                isEmptyPhoneNum = !StringRe2j.matches(phoneNum, getString(R.string.check_phone_number));
            }
        } catch (NullPointerException e) {
            // Phone Number 조회시 null 가능성이 있어 해당 Exception 발생시 입력이 안된 것으로 판단.
            isEmptyPhoneNum = true;
        }

        ui.lServiceRemoteStep1.tvServiceRemoteRegisterStepGuide.setVisibility(isEmptyPhoneNum ? View.VISIBLE : View.GONE);
        ui.lServiceRemoteStep1.etServiceRemoteRegisterStepInput.setSelected(isEmptyPhoneNum);

        // 차량 번호가 입력되어 있는지 체크.
        boolean isEmptyCarNum;
        try {
            String carNum = ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.getText().toString().trim();

            if (TextUtils.isEmpty(carNum)) {
                // 차량 번호가 입력되어 있지 않음.
                isEmptyCarNum = true;
            } else {
                // 차량 번호가 입력되어 있는 경우. - 차량 번호 형식 체크.
                isEmptyCarNum = !StringRe2j.matches(carNum, getString(R.string.check_car_vrn));
            }
        } catch (NullPointerException e) {
            // 차량 번호 조회시 null 가능성이 있어 해당 Exception 발생시 입력이 안된 것으로 판단.
            isEmptyCarNum = true;
        }

        ui.lServiceRemoteStep2.tvServiceRemoteRegisterStepGuide.setVisibility(isEmptyCarNum ? View.VISIBLE : View.GONE);
        ui.lServiceRemoteStep2.etServiceRemoteRegisterStepInput.setSelected(isEmptyCarNum);

        // 고장 코드가 없거나 경고등인데 상세 경고등 선택항목이 없는지 체크.
        boolean isEmptyErrorCode = (fltCd == null) || (fltCd == FLT_CODE.CODE_3000 && wrnLghtCd == null);
        ui.lServiceRemoteStep3.tvServiceRemoteRegisterStepInput.setSelected(isEmptyErrorCode);

        boolean isServiceTime = TextUtils.isEmpty(rsrvMiss);
        ui.lServiceRemoteStep4.tvServiceRemoteRegisterStepInput.setSelected(isServiceTime);

        btnNextStep.setText(R.string.sm_remote01_next);

        if (isEmptyPhoneNum) {
            return REGISTER_STEP.INPUT_PHONE;
        } else if (isEmptyPhoneNum) {
            return REGISTER_STEP.INPUT_CAR_NUM;
        } else if (isEmptyErrorCode) {
            return REGISTER_STEP.SERVICE_TYPE;
        } else if (isServiceTime) {
            return REGISTER_STEP.SERVICE_TIME;
        }

        // 모든 정보가 입력이 완료되면 문구 변경.
        btnNextStep.setText(R.string.sm_remote01_submit);
        return REGISTER_STEP.COMPLETE;
    }

    /**
     * 상단 입력이 필요한 내용을 변경해주는 함수.
     *
     * @param guide 가이드 문구 String Resource ID.
     */
    private void showStepGuide(@StringRes int guide) {
        ui.tvServiceRemoteRegisterGuide.setText(guide);
    }

    /**
     * 차량 문제 선택 하단 리스트형 다이얼로그 표시 함수.
     */
    private void showSelectFltCd() {
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
                if (fltCd == FLT_CODE.CODE_3000) {
                    // 경고등 점등인 경우 - 경고등 선택 다이얼로그 추가 표시.
                    showSelectWrnLghtCd();
                } else {
                    executeStep(checkStep());
                }
            }
        });
        bottomListDialog.setDatas(fltCodes);
        bottomListDialog.setTitle(getString(R.string.sm_emgc01_23));
        bottomListDialog.show();
    }

    /**
     * 경고등 선택 하단 리스트형 다이얼로그 표시 함수.
     */
    private void showSelectWrnLghtCd() {
        WrnLghtCodeListAdapter adapter = new WrnLghtCodeListAdapter(Stream.of(WRN_LGHT_CODE.values()).collect(Collectors.toList()));
        BottomRecyclerDialog dialog = new BottomRecyclerDialog.Builder(this)
                .setTitle(R.string.sm_romte01_p01_12)
                .setAdapter(adapter)
                .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                .build();
        adapter.setListener(selectItem -> {
            wrnLghtCd = selectItem;
            executeStep(checkStep());
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * 원격 진단 시간 선택 그리드형 다이얼로그 표시 함수.
     */
    private void showSelectTime() {
        ServiceRemoteTimeGridAdapter adapter = new ServiceRemoteTimeGridAdapter(ServiceTimes);
        BottomRecyclerDialog dialog = new BottomRecyclerDialog.Builder(this)
                .setTitle(R.string.sm_remote01_select_service_time)
                .setAdapter(adapter)
                .setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false))
                .build();
        adapter.setListener(selectItem -> {
            rsrvMiss = selectItem.getTime();
            ui.lServiceRemoteStep4.tvServiceRemoteRegisterStepInput.setText(selectItem.toString());
            executeStep(checkStep());
            dialog.dismiss();
        });
        dialog.show();
    }
} // end of class ServiceREmoteRegisterActivity
