package com.genesis.apps.ui.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.comm.viewmodel.DTWViewModel;
import com.genesis.apps.databinding.FragmentDigitalWalletPaymtBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.straffic.cardemullib.CardService;

public class FragmentDigitalWalletPaymt extends SubFragment<FragmentDigitalWalletPaymtBinding> {

    private DTWViewModel dtwViewModel;
    private DigitalWalletActivity activity;
    private boolean isShow = false;

    public static FragmentDigitalWalletPaymt newInstance() {
        return new FragmentDigitalWalletPaymt();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_digital_wallet_paymt);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        me.setBtnListener(((SubActivity)getActivity()).onSingleClickListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();
    }

    private void initViewModel(){
        me.setLifecycleOwner(getViewLifecycleOwner());

        dtwViewModel = new ViewModelProvider(getActivity()).get(DTWViewModel.class);

        dtwViewModel.getRES_DTW_1001().observe(getActivity(), result -> {
            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000") && result.data.getStcMbrInfo() != null) {
                        // EV 충전 정보  표시
                        me.tvCreditPoint.setVisibility(StringUtil.isValidInteger(result.data.getStcMbrInfo().getCretPnt()) > 0 ? View.VISIBLE : View.GONE);
                        me.tvCreditPoint.setText(StringUtil.getPriceString(result.data.getStcMbrInfo().getCretPnt()));
                        String creditCardNo = StringUtil.isValidString(result.data.getStcMbrInfo().getStcCardNo());
                        me.tvCreditCardNo.setText(StringRe2j.replaceAll(creditCardNo, getString(R.string.card_original), getString(R.string.card_mask)));

                        // NFC로 충전기에 카드 번호 전송
//                        if(!TextUtils.isEmpty(creditCardNo))
//                            ((DigitalWalletActivity)getActivity()).sendCardInfo(creditCardNo);

                        break;
                    }
                default:
                    break;
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void onPause() {
        super.onPause();
        // 디지털 월렛에서 벗어날 경우 PaymentScreenChecker가 화면 밖으로 나간 것을 알게 하기 위한 인터페이스 등록
//        CardService.setPaymentScreenChecker(disablePaymentScreenChecker);
    }

    @Override
    public void onRefresh() {
        checkNfcEnabled();
    }

    private void checkNfcEnabled() {
        try {
            boolean isNfcEnabled = DeviceUtil.checkNfcEnabled(getContext());
            if (isNfcEnabled) {
                animSlideDown(me.lStcCard);
                // 디지털 월렛 진입시 PaymentScreenChecker가 화면에 진입한 것을 알게 하기 위한 인터페이스 등록
//                CardService.setPaymentScreenChecker(enablePaymentScreenChecker);
            } else {
                // NFC OFF
                MiddleDialog.dialogServiceRemoteTwoButton(
                        getActivity(),
                        R.string.pay01_p05_1,
                        R.string.pay01_p05_2,
                        () -> {
                            // NFC 설정 호출
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
                                startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
                            else
                                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        }, () -> finishNfcPaymt());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            // TODO : NFC 미지원 단말 안내 필요?
            finishNfcPaymt();
        } catch (Exception e) {
            e.printStackTrace();
            finishNfcPaymt();
        }
    }

    /**
     * NFC 결제 화면 종료
     */
    private void finishNfcPaymt() {
        ((DigitalWalletActivity) getActivity()).moveViewpager(0);
    }

    AnimatorSet slideDownAniSet = new AnimatorSet();

    //TODO : 애니메이션 효과 변경 필요
    private void animSlideDown(View view) {
        if(isShow)
            return;


        int targetY = (int) DeviceUtil.dip2Pixel(getContext(), 100);

        ValueAnimator downAni = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, targetY);
        ValueAnimator alphaAni = ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1.0f);

        downAni.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                me.tvInfoTxt.setVisibility(View.GONE);
                VibratorUtil.doVibratorLong(((SubActivity)getActivity()).getApplication());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                me.tvInfoTxt.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        slideDownAniSet.playTogether(downAni, alphaAni);
        slideDownAniSet.setDuration(500);
        slideDownAniSet.start();

        isShow = true;
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


}
