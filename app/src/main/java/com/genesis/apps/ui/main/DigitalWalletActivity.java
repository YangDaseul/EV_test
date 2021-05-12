package com.genesis.apps.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityDigitalWalletBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.straffic.cardemullib.CardService;

public class DigitalWalletActivity extends SubActivity<ActivityDigitalWalletBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_wallet);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
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

    private void initView() {
        ui.lTitle.setTextBtnListener(onSingleClickListener); //완료
        ui.lTitle.ivTitlebarImgBtn.setOnClickListener(onSingleClickListener); //설정
        initTitleBar();


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.vg_container, FragmentCardFront.newInstance());
        ft.commit();
    }

    private void initTitleBar() {
        ui.lTitle.setValue(""); //타이틀 없음
        ui.lTitle.setBtnText(""); //완료버튼제거
        ui.lTitle.setIconId(getDrawable(R.drawable.ic_setting_b)); //설정버튼
        ui.lTitle.lTitleBar.setBackgroundColor(getColor(R.color.x_f8f8f8));
        SubActivity.setStatusBarColor(this, R.color.x_f8f8f8);
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.tv_titlebar_text_btn:
                try {
                    showProgressDialog(true);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    showProgressDialog(false);
                }
                break;
            case R.id.btn_card_mgmt:
                // NFC로 충전기에 카드 번호 전송 TODO 실제 조회된 카드 번호로 변경 필요
                sendCardInfo("0000000000000000");
                break;

            case R.id.btn_card_open:
                verticalOpen();
                break;
            case R.id.btn_card_close:
                verticalClose();
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {
    }

    private void verticalOpen() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.anim_bottom_in,
                R.anim.anim_bottom_out,
                R.anim.anim_top_in,
                R.anim.anim_top_out);
        ft.replace(R.id.vg_container, FragmentCardBack.newInstance());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void verticalClose() {
        getSupportFragmentManager().popBackStack();
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
