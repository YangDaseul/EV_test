package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.genesis.apps.R;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BaseBottomDialog<T extends ViewDataBinding> extends BottomSheetDialog {
    T ui;
    public Runnable yes, no;
    private boolean isAllowOutTouch=false;
    public BaseBottomDialog(@NonNull Context context) {
        super(context);
    }

    public BaseBottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void setContentView(int layoutResId) {
        ui = inflate(layoutResId);
        super.setContentView(ui.getRoot());

        //아래 적용된 두가지 정책은 하단 팝업에 대한 기획 정책
        //1. bottomSheetDialog를 드래그에서 내리지 않도록 수정
        getBehavior().addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING)
                    getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        //풀스크린으로 펼쳐지지 않는 문제를 해결하기 위해 아래 코드 적용
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        getBehavior().setSkipCollapsed(true);
        getBehavior().setHideable(true);

        try {
            findViewById(R.id.back).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    dismiss();
                }
            });
        } catch (Exception e) {

        }
        //2. bottomSheetDialog에서 editbox가 있어서 키보드 활성화 시 화면을 완전하게 올리도록 수정 (해당 부분을 추가하지 않으면 뷰가 잘림)
//        setOnShowListener(dialogInterface -> getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED));
    }

    private <T extends ViewDataBinding> T inflate(int layoutResId) {
        return DataBindingUtil.inflate(getLayoutInflater(), layoutResId, null, false);
    }

    public void setButtonAction(Runnable yes, Runnable no){
        this.yes = yes;
        this.no = no;
    }


    public boolean isAllowOutTouch() {
        return isAllowOutTouch;
    }

    public void setAllowOutTouch(boolean allowOutTouch) {
        isAllowOutTouch = allowOutTouch;
        setCanceledOnTouchOutside(isAllowOutTouch);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(!isAllowOutTouch()) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                backAction();
            }
            return false;
        }else{
            super.onKeyDown(keyCode,event);
            return true;
        }
    }

    public void backAction(){

    }


}
