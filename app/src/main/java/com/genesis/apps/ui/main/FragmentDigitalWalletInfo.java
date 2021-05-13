package com.genesis.apps.ui.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
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
    private boolean isSlideDown;

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
                case SUCCESS:
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase("0000") && result.data.getBlueCardInfo() != null) {

                        me.tvCardBg.setVisibility(View.GONE);
                        me.lStcCard.setVisibility(View.VISIBLE);
                        me.btnNfc.setVisibility(View.VISIBLE);

                        // 제네시스 멤버십 카드 정보 표시
                        String cardNo = result.data.getBlueCardInfo().getBlueCardNo();
                        Log.d("LJEUN", "cardNo : " + StringRe2j.replaceAll(StringUtil.isValidString(cardNo), getString(R.string.card_original), getString(R.string.card_mask)));
                        Log.d("LJEUN", "cardIsncSubspDt : " + result.data.getBlueCardInfo().getCardIsncSubspDt());

                        // 바코드 표시
                        me.ivBarcode.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                try {
                                    me.ivBarcode.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                    Bitmap bitmap = new BarcodeUtil().encodeAsBitmap("1111111111111111".replace("-", ""), BarcodeFormat.CODE_128, (int) DeviceUtil.dip2Pixel(getContext(), (float) me.ivBarcode.getWidth()), (int) DeviceUtil.dip2Pixel(getContext(), (float) me.ivBarcode.getHeight()));
                                    me.ivBarcode.setImageBitmap(bitmap);
                                } catch (WriterException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    }
                default:
                    // 사용 가능한 제네시스 카드 없는 경우
                    me.tvCardBg.setVisibility(View.VISIBLE);
                    me.lStcCard.setVisibility(View.GONE);
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
                if (!isSlideDown)
                    animSlideDown(me.lStcCard);
                else
                    animSlideUp(me.lStcCard);
                break;
        }
    }


    @Override
    public void onRefresh() {
    }


    AnimatorSet slideDownAniSet = new AnimatorSet();
    AnimatorSet slideUpAniSet = new AnimatorSet();

    private void animSlideDown(View view) {
        int targetHeight = me.lStcCardInfo.getMeasuredHeight() - (int) DeviceUtil.dip2Pixel(getContext(), 5);

        isSlideDown = true;

        ValueAnimator downAni = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, targetHeight);
        ValueAnimator alphaAni = ObjectAnimator.ofFloat(view, "alpha", 1.0f);

        slideDownAniSet.playTogether(downAni, alphaAni);
        slideDownAniSet.setDuration(400);

        slideDownAniSet.start();
    }

    private void animSlideUp(View view) {
        isSlideDown = false;

        ValueAnimator upAni = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0);
        ValueAnimator alphaAni = ObjectAnimator.ofFloat(view, "alpha", 0.7f);

        slideUpAniSet.playTogether(upAni, alphaAni);
        slideUpAniSet.setDuration(400);

        slideUpAniSet.start();
    }
}
