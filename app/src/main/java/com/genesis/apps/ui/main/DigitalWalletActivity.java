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
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.DTWViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityDigitalWalletBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.main.service.CardManageActivity;

import static com.genesis.apps.comm.model.constants.VariableType.COMMON_MEANS_YES;

public class DigitalWalletActivity extends SubActivity<ActivityDigitalWalletBinding> {

    private DigitalWalletViewpagerAdapter viewpagerAdapter;
    private final int PAGE_NUM = 2;//카드 정보 화면, NFC 태그 화면

    private DTWViewModel dtwViewModel;
    private LGNViewModel lgnViewModel;

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

        if (pgIndex == 0) {
            // 간편결제 가입 여부 확인, 미가입자인 경우
            if (checkEasyPayInfo())
                showEasyPayInfoDialog();
        }
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
            case R.id.btn_easypay:
                // 간편결제카드 관리 페이지로 이동
                startActivitySingleTop(new Intent(this, CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);

        dtwViewModel = new ViewModelProvider(this).get(DTWViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
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

                        // 에스트래픽 회원 여부 확인, 회원인 경우
                        if (result.data.getStcMbrInfo() != null && StringUtil.isValidString(result.data.getStcMbrInfo().getStcMbrYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {

                            // 선불 교통카드 비밀번호 설정 여부 확인,
                            // EV 충전카드 사용 가능 && 비밀번호 설정이 안되어 있는 경우
                            if (StringUtil.isValidString(result.data.getStcMbrInfo().getStcCardUseYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES) &&
                                    !StringUtil.isValidString(result.data.getStcMbrInfo().getPwdYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
                                MiddleDialog.dialogServiceRemoteOneButton(this, R.string.pay01_p02_1, R.string.pay01_p02_2, () -> {
                                    // 비밀번호 설정 화면으로 이동
                                    startActivitySingleTop(new Intent(this, EvChargeCardPasswordActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                                });

                                initViewpagerAdapter(0);
                                return;
                            }

                            // 보유 크레딧 확인, 보유 크레딧 부족한 경우
                            // cretPnt(잔여크레딧포인트) < minCretPnt(최소사용가능 크레딧포인트)
                            // ex) 20000원(minCretPnt) 이상인 경우 크레딧 포인트 사용가능
                            //     20000원(minCretPnt) 미만은 크레딧 사용 불가
                            if (StringUtil.isValidInteger(result.data.getStcMbrInfo().getCretPnt()) < StringUtil.isValidInteger(result.data.getStcMbrInfo().getMinCretPnt())) {
                                // 간편결제 등록 카드 유무 확인,
                                // 간편결제 미 가입자나 등록된 카드가 없는 경우
                                if(result.data.getPayInfo() != null &&
                                        (!StringUtil.isValidString(result.data.getPayInfo().getSignInYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES) || StringUtil.isValidInteger(result.data.getPayInfo().getCardCount()) == 0)) {
                                    // 신용카드 등록 후 서비스 이용 가능 안내 팝업 표시
                                    MiddleDialog.dialogEvCretPntLackInfo(
                                            this,
                                            R.string.pay01_p03_1,
                                            String.format(getString(R.string.pay01_p03_2), StringUtil.getPriceString(result.data.getStcMbrInfo().getMinCretPnt())),
                                            () -> {
                                                // 간편결제카드 관리 페이지로 이동
                                                startActivitySingleTop(new Intent(this, CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                                            }, () -> {
                                            });

                                    initViewpagerAdapter(0);
                                    return;
                                }
                            }
                        }

                        // 미수금 여부 확인, 미수금이 있는 경우
                        if (result.data.getStcMbrInfo() != null && StringUtil.isValidString(result.data.getStcMbrInfo().getUnpayYn()).equalsIgnoreCase(COMMON_MEANS_YES)) {
                            MiddleDialog.dialogServiceRemoteOneButton(this, R.string.pay01_p04_1, R.string.pay01_p04_2, () -> {
                                // 미수금 결제 화면으로 이동
                                startActivitySingleTop(new Intent(this, UnpaidPayRequestActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                            });

                            initViewpagerAdapter(0);
                            return;
                        }

                        // NFC 결제 실행 가능 확인,
                        // 에스트래픽 회원 이고, EV 충전 카드 사용 가능한 경우
                        if(result.data.getStcMbrInfo() != null &&
                                StringUtil.isValidString(result.data.getStcMbrInfo().getStcMbrYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES) &&
                                StringUtil.isValidString(result.data.getStcMbrInfo().getStcCardUseYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {
                            //  EV 충전 카드 정보가 있는 경우
                            initViewpagerAdapter(1);
                        } else {
                            // 간편결제 가입 여부 확인, 미가입자인 경우
                            if (checkEasyPayInfo())
                                showEasyPayInfoDialog();

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
        if (resultCode == ResultCodes.REQ_CODE_PAYMENT_CARD_CHANGE.getCode()) {
            // 간편결제카드 관리 변동 사항 있을 때
            String userAgentString = new WebView(this).getSettings().getUserAgentString();
            dtwViewModel.reqDTW1001(new DTW_1001.Request(APPIAInfo.PAY01.getId(), mainVehicle.getVin(), userAgentString));
        } else if (resultCode == ResultCodes.REQ_CODE_UNPAID_PAYMT_CANCEL.getCode()) {
            exitPage(new Intent(), ResultCodes.REQ_CODE_UNPAID_PAYMT_CANCEL.getCode());
        }
    }

    /**
     * 간편결제 가입 안내 팝업 표시
     */
    private void showEasyPayInfoDialog() {
        MiddleDialog.dialogServiceSimplePayInfo(this, new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                setEasyPayInfo((Boolean) v.getTag(R.id.item));
                // 결제수단관리 페이지로 연결
                startActivitySingleTop(new Intent(DigitalWalletActivity.this, CardManageActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
            }
        }, new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                setEasyPayInfo((Boolean) v.getTag(R.id.item));
            }
        });
    }

    /**
     * 간편결제 가입 안내 팝업 '다시보지않기' 설정
     *
     * @param isCheck
     */
    private void setEasyPayInfo(Boolean isCheck) {
        if (isCheck) {
            lgnViewModel.updateGlobalDataToDB(KeyNames.KEY_NAME_CHARGE_TAB_PAY_SERVICE_POPUP, COMMON_MEANS_YES);
        }
    }

    private boolean checkEasyPayInfo() {
        return !VariableType.COMMON_MEANS_YES.equalsIgnoreCase(lgnViewModel.getDbGlobalDataRepository().select(KeyNames.KEY_NAME_CHARGE_TAB_PAY_SERVICE_POPUP))&&!dtwViewModel.isPayInfo();
    }

}
