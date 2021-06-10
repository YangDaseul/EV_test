package com.genesis.apps.ui.main;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.BarcodeUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.DTWViewModel;
import com.genesis.apps.databinding.FragmentDigitalWalletInfoBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class FragmentDigitalWalletInfo extends SubFragment<FragmentDigitalWalletInfoBinding> {

    private DTWViewModel dtwViewModel;

    public static FragmentDigitalWalletInfo newInstance() {
        return new FragmentDigitalWalletInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_digital_wallet_info);
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
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000") &&
                            result.data.getBlueCardInfo() != null && !TextUtils.isEmpty(result.data.getBlueCardInfo().getBlueCardNo())) {

                        me.tvCardBg.setVisibility(View.GONE);
                        // 제네시스 멤버십 카드 정보 표시
                        String cardNo = result.data.getBlueCardInfo().getBlueCardNo();
                        me.tvCardNo.setText(StringRe2j.replaceAll(StringUtil.isValidString(cardNo), getString(R.string.card_original), getString(R.string.card_mask)));

                        // 바코드 표시
                        me.ivBarcode.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                try {
                                    me.ivBarcode.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                    Bitmap bitmap = new BarcodeUtil().encodeAsBitmap(cardNo.replace("-", ""), BarcodeFormat.CODE_128, (int) DeviceUtil.dip2Pixel(getContext(), (float) me.ivBarcode.getWidth()), (int) DeviceUtil.dip2Pixel(getContext(), (float) me.ivBarcode.getHeight()));
                                    me.ivBarcode.setImageBitmap(bitmap);
                                } catch (WriterException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                        // EV 충전 정보  표시
                        // 에스트래픽 회원 여부 확인, 회원인 경우
                        if (result.data.getStcMbrInfo() != null && StringUtil.isValidString(result.data.getStcMbrInfo().getStcMbrYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)) {

                            me.lStcCardBottom.setVisibility(View.VISIBLE);
                            me.tvCreditPoint.setText(String.format(getString(R.string.digital_wallet02_6), StringUtil.getPriceString(result.data.getStcMbrInfo().getCretPnt())));
                            String creditCardNo = result.data.getStcMbrInfo().getStcCardNo();
                            me.tvCreditCardNo.setText(String.format(getString(R.string.digital_wallet02_7), StringRe2j.replaceAll(StringUtil.isValidString(creditCardNo), getString(R.string.card_original), getString(R.string.card_mask))));

                            // 크레딧 사용 제한 안내 (선불교통카드 사용 불가) 표시
                            // EV 충전 크레딧 사용 불가(=부족) && ( 간편결제 미회원 || 등록된 결제카드 0개 )
                            if (StringUtil.isValidInteger(result.data.getStcMbrInfo().getCretPnt()) < StringUtil.isValidInteger(result.data.getStcMbrInfo().getMinCretPnt()) &&
                                    (result.data.getPayInfo() == null ||
                                            !StringUtil.isValidString(result.data.getPayInfo().getSignInYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES) || StringUtil.isValidInteger(result.data.getPayInfo().getCardCount()) == 0)) {

                                // 버튼 분기 처리
                                if(result.data.getPayInfo() == null || !StringUtil.isValidString(result.data.getPayInfo().getSignInYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES))
                                    me.btnEasypay.setText(R.string.pay01_3);
                                else
                                    me.btnEasypay.setText(R.string.pay01_4);

                                // 간편결제 가입 및 카드 등록 유도 레이아웃 표시
                                me.lEasypayInfo.setVisibility(View.VISIBLE);
                                me.btnNfc.setVisibility(View.GONE);

                            } else {
                                // 간편결제 가입 및 카드 등록 유도 레이아웃 표시
                                me.lEasypayInfo.setVisibility(View.GONE);
                                me.btnNfc.setVisibility(View.VISIBLE);
                            }

                        } else {

                            // EV 충전 카드 미노출 처리
                            me.lStcCardBottom.setVisibility(View.GONE);
                            // 간편결제 가입 및 카드 등록 유도 레이아웃 표시
                            me.lEasypayInfo.setVisibility(View.GONE);
                            //  NFC 태그 버튼 표시
                            me.btnNfc.setVisibility(View.GONE);
                        }

                        break;
                    }
                default:
                    // 사용 가능한 제네시스 카드 없는 경우
                    me.tvCardBg.setVisibility(View.VISIBLE);
                    me.lStcCardBottom.setVisibility(View.GONE);
                    me.lEasypayInfo.setVisibility(View.GONE);
                    me.btnNfc.setVisibility(View.GONE);
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
            case R.id.iv_ev_logo:
            case R.id.tv_card_nm:
            case R.id.btn_expand_card:
                if(me.lStcCardBottom.getTag() != null && (boolean) me.lStcCardBottom.getTag())
                    animSlidUpDown(false);
                else
                    animSlidUpDown(true);
                break;
            case R.id.btn_modify_pw:
                ((SubActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), EvChargeCardPasswordActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE);
                break;
        }
    }


    @Override
    public void onRefresh() {
    }

    private void animSlidUpDown(boolean down) {

        me.lStcCardBottom.setTag(down);

        int toMargin = ((ConstraintLayout.LayoutParams) me.lStcCardBottom.getLayoutParams()).topMargin;
        int targetMargin = me.lStcCard.getMeasuredHeight();
        ValueAnimator valAnim = ValueAnimator.ofInt(toMargin, (down ? toMargin + targetMargin : toMargin - targetMargin));
        valAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) me.lStcCardBottom.getLayoutParams();
                params.topMargin = val;
                me.lStcCardBottom.setLayoutParams(params);
            }
        });
        valAnim.setDuration(400);
        valAnim.start();
    }
}
