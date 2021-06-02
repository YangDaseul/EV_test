package com.genesis.apps.ui.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.straffic.ev.util.Constant;

public class FragmentDigitalWalletPaymt extends SubFragment<FragmentDigitalWalletPaymtBinding> {

    private DTWViewModel dtwViewModel;

    private String creditCardNo;

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
        me.setFragment(this);

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
                        this.creditCardNo = StringUtil.isValidString(result.data.getStcMbrInfo().getStcCardNo());
                        me.tvCreditCardNo.setText(StringRe2j.replaceAll(creditCardNo, getString(R.string.card_original), getString(R.string.card_mask)));

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
        switch (v.getId()) {
            case R.id.btn_finish_nfc:
                finishNfcPaymt();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(vibrator != null )
            vibrator.cancel();

        unRegisterBroadcastReceiver();
    }

    @Override
    public void onRefresh() {
        checkNfcEnabled();
    }

    private void checkNfcEnabled() {
        try {
            boolean isNfcEnabled = DeviceUtil.checkNfcEnabled(getContext());
            if (isNfcEnabled) {
                setNfcPayment(this.creditCardNo);
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
        animSlidUpDown(false);
        ((DigitalWalletActivity) getActivity()).moveViewpager(0);
    }

    Vibrator vibrator = null;
    /**
     * EV 충전 카드 애니메이션 처리
     *
     * @param down
     */
    private void animSlidUpDown(boolean down) {

        me.lStcCard.setTag(down);

        int toMargin = ((ConstraintLayout.LayoutParams) me.lStcCard.getLayoutParams()).topMargin;
        int targetMargin = down ? (int) DeviceUtil.dip2Pixel(getContext(), 125) : (int) DeviceUtil.dip2Pixel(getContext(), 30);

        if (toMargin == targetMargin)
            return;


        ValueAnimator valAnim = ValueAnimator.ofInt(toMargin, targetMargin);
        valAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) me.lStcCard.getLayoutParams();
                params.topMargin = val;
                me.lStcCard.setLayoutParams(params);
            }
        });

        valAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (down)
                    vibrator = VibratorUtil.doVibratorRepeat(((SubActivity) getActivity()).getApplication());
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valAnim.setDuration(down ? 400 : 0);
        valAnim.start();
    }


    private void setNfcPayment(String mbrspCardNo) {
        //브로드캐스트 리시버 등록
        registerBroadcastReceiver();

        //static 메서드이므로 인스턴스 생성 없이 직접 호출
        CardService.setPaymentScreenChecker(() -> {
            //결제 화면에서만 true를 리턴하도록 구현
            //현재 액티비티가 NFC 결제 화면인 경우 항상 true를 반환하면 됨.
            return true;
        });

        if (!CardService.checkBasicPayApp(getActivity())) {
            //모바일 결제 기본앱으로 설정이 되어있지 않고 현재 앱에서 결제하기 기능도 꺼져있는 경우
            //현재 앱을 NFC 기본앱으로 설정하도록 확인하는 절차
            CardService.setBasicPayApp(getActivity(), new CardService.OnPaymentCancelListener() {
                @Override
                public void onPaymentSetting() {
                    //모바일 결제 설정 팝업에서 설정화면으로 이동한 경우.
                    //여기에서 진동 및 애니메이션 일시정지 기능을 구현한다.
                }

                @Override
                public void onPaymentCancel() {
                    //모바일 결제 설정 팝업에서 결제 취소 버튼을 누른 경우.
                    //여기에서 진동 및 애니메이션 중지, NFC화면 종료기능을 구현한다.
                    finishNfcPaymt();
                }
            });

        } else {
            //모바일 결제 기본앱으로 설정이 되었거나 현재 앱에서 결제하기 기능이 켜져있으면 인증 실행
            //인증 성공 여부에 따라 앱을 컨트롤
            CardService.setOnPreparePaymentCompleteListener(new CardService.OnPreparePaymentCompleteListener() {
                @Override
                public void onPreparePaymentComplete() {
                    // 인증 완료되어 결제를 진행할 수 있는 상태가 됨. 여기에서 진동 및 애니메이션 재시작
                    if (me.lStcCard.getTag() == null || !(boolean) me.lStcCard.getTag())
                        animSlidUpDown(true);
                }

                @Override
                public void onPreparePaymentFail() {
                    // 인증이 정상적으로 완료되지 않음. 진동 및 애니메이션 종료, 안내 후 NFC 화면 종료 등의 처리 필요.

                    MiddleDialog.dialogCommonOneButton(getActivity(), R.string.pay01_p06_1, getString(R.string.pay01_p06_2), () -> {
                        finishNfcPaymt();
                    });
                }
            });

            //카드번호 : 2055101200003878
            //회원유형 : GE
            sendCardInfo(mbrspCardNo);
        }
    }

    /**
     * Receiver 등록
     */
    private void registerBroadcastReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constant.ACTION_VIBRATOR_CANCEL);
            getActivity().registerReceiver(this.mEventReceiver, intentFilter);
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Receiver 해제
     */
    private void unRegisterBroadcastReceiver() {
        try {
            getActivity().unregisterReceiver(this.mEventReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    private BroadcastReceiver mEventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.ACTION_VIBRATOR_CANCEL)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //결제가 끝나면 기본서비스 해제
                        CardService.unsetBasicPayApp(getActivity());

                        // 브로드캐스트 리시버 해제
                        unRegisterBroadcastReceiver();
                        //결제 화면 종료
                        finishNfcPaymt();
                    }
                }, 1500);
            }
        }
    };

    /**
     * 카드 번호를 NFC로 전송하는 함수.
     *
     * @param cardNo 숫자로만 구성된 카드 번호
     */
    private void sendCardInfo(String cardNo) {
        Log.d("LJEUN", "mbrspCardNo : " + cardNo);

        try {
            CardService.preparePayment(getActivity(), cardNo, "GE");
        }  catch (Exception e) {
            e.printStackTrace();
            MiddleDialog.dialogCommonOneButton(getActivity(), R.string.pay01_p06_1, getString(R.string.pay01_p06_2), () -> {
                finishNfcPaymt();
            });
        }
    }
}
