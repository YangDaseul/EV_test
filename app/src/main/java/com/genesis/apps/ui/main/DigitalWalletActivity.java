package com.genesis.apps.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.DTW_1001;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.DTWViewModel;
import com.genesis.apps.databinding.ActivityDigitalWalletBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.service.CardManageActivity;
import com.straffic.cardemullib.CardService;

public class DigitalWalletActivity extends SubActivity<ActivityDigitalWalletBinding> {

    private DigitalWalletViewpagerAdapter viewpagerAdapter;
    private final int PAGE_NUM = 2;//카드 정보 화면, NFC 태그 화면

    private DTWViewModel dtwViewModel;

    private VehicleVO mainVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_wallet);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 디지털 월렛 진입시 PaymentScreenChecker가 화면에 진입한 것을 알게 하기 위한 인터페이스 등록
        CardService.setPaymentScreenChecker(enablePaymentScreenChecker);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 디지털 월렛에서 벗어날 경우 PaymentScreenChecker가 화면 밖으로 나간 것을 알게 하기 위한 인터페이스 등록
        CardService.setPaymentScreenChecker(disablePaymentScreenChecker);
    }

    private void initViewpagerAdapter(int pgIndex) {
        viewpagerAdapter = new DigitalWalletViewpagerAdapter(this, PAGE_NUM);
        ui.vpContents.setAdapter(viewpagerAdapter);
        ui.vpContents.setUserInputEnabled(false);
        ui.vpContents.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.vpContents.setCurrentItem(pgIndex);
        ui.vpContents.setOffscreenPageLimit(PAGE_NUM);
    }

    public void moveViewpager(int pgIndex) {
        if(ui.vpContents != null && viewpagerAdapter != null)
            ui.vpContents.setCurrentItem(pgIndex, true);
    }

    private void initData() {
        try {
            mainVehicle = dtwViewModel.getMainVehicleFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mainVehicle != null) {
                String userAgentString = new WebView(this).getSettings().getUserAgentString();
                dtwViewModel.reqDTW1001(new DTW_1001.Request(APPIAInfo.PAY01.getId(), mainVehicle.getVin(), userAgentString));
            } else {
                exitPage("주 이용 차량 정보가 없습니다.\n차량을 확인 후 다시 시도해 주세요.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.btn_nfc:
                moveViewpager(1);
                break;
            case R.id.btn_finish_nfc:
                moveViewpager(0);
                break;
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
        dtwViewModel.getRES_DTW_1001().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000") && result.data.getBlueCardInfo() != null) {

                        // 에스트래픽 회원인 경우
                        if (dtwViewModel.isStcMbrYn()) {
                            // 비밀번호 설정이 안되어 있는 경우
                            if (!dtwViewModel.isStcPwdYn()) {
                                MiddleDialog.dialogServiceRemoteOneButton(this, R.string.pay01_p02_1, R.string.pay01_p02_2, () -> {
                                    // 비밀번호 설정 화면으로 이동
                                    startActivitySingleTop(new Intent(this, EvChargeCardPasswordActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                                });

                                initViewpagerAdapter(0);
                                return;
                            }
                            // 보유 크레딧 부족한 경우(선불교통카드 사용 불가 인 경우)
                            if (!dtwViewModel.isStcCardUseYn()) {
                                // 간편 결제 가입 및 결제 카드 등록 여부 확인
                                if (result.data.getPayInfo() != null &&
                                        (result.data.getPayInfo().getSignInYn().equalsIgnoreCase(VariableType.COMMON_MEANS_NO) || StringUtil.isValidInteger(result.data.getPayInfo().getCardCount()) == 0)) {

                                    // 신용카드 등록 후 서비스 이용 가능 안내 팝업 표시
                                    MiddleDialog.dialogServiceRemoteTwoButton(
                                            this,
                                            R.string.pay01_p03_1,
                                            R.string.pay01_p03_2,
                                            () -> {
                                                // 간편결제카드 관리 페이지로 이동
                                                startActivitySingleTop(new Intent(this, CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                                            }, () -> exitPage("", 0));

                                    initViewpagerAdapter(0);
                                    return;
                                }
                            }

                        }

                        // 미수금 있는 경우
                        if (dtwViewModel.isUnpayYn()) {
                            MiddleDialog.dialogServiceRemoteOneButton(this, R.string.pay01_p04_1, R.string.pay01_p04_2, () -> {
                                // 미수금 결제 화면으로 이동
                                startActivitySingleTop(new Intent(this, UnpaidPayRequestActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                            });

                            initViewpagerAdapter(0);
                            return;
                        }

                        if(result.data.getStcMbrInfo() != null &&
                                StringUtil.isValidString(result.data.getStcMbrInfo().getStcMbrYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES) &&
                                StringUtil.isValidString(result.data.getStcMbrInfo().getStcCardUseYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
                            //  EV 충전 카드 정보가 있는 경우
                            initViewpagerAdapter(1);
                        } else {
                            //  EV 충전 카드 정보가 없는 경우
                            initViewpagerAdapter(0);
                        }
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
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        if(viewpagerAdapter == null)
                            initViewpagerAdapter(0);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * NFC구동 화면 진입 시 NFC구동 화면에서만 true를 반환하는 인터페이스.
     */
    private CardService.PaymentScreenChecker enablePaymentScreenChecker = () -> true;
    /**
     * NFC구동 화면 밖일 경우 {@link com.straffic.cardemullib.CardService.PaymentScreenChecker}가
     * 화면을 벗어난 것을 체크할 수 있게 false를 반환하는 인터페이스
     */
    private CardService.PaymentScreenChecker disablePaymentScreenChecker = () -> false;

    /**
     * 카드 번호를 NFC로 전송하는 함수.
     *
     * @param cardNo 숫자로만 구성된 카드 번호
     */
    private void sendCardInfo(String cardNo) {
        CardService.preparePayment(cardNo, "GE");
    }
}
