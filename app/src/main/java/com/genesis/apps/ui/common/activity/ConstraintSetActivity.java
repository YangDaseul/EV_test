package com.genesis.apps.ui.common.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.databinding.ActivityCont1Binding;
import com.google.android.material.textfield.TextInputEditText;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

public class ConstraintSetActivity extends SubActivity<ActivityCont1Binding> {

    private final int[] layouts = {R.layout.activity_cont_1, R.layout.activity_cont_2, R.layout.activity_cont_3, R.layout.activity_cont_4};
    private View[] editText;
    private View[] editLayout;
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layouts[0]);
        init();
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        initConstraintSets();
        initView();
        SoftKeyboardUtil.showKeyboard(this);
    }

    private void initConstraintSets() {
        editText = new View[]{ui.edit1, ui.edit2, ui.edit3, ui.edit4};
        editLayout = new View[]{ui.lEdit, ui.lEdit2, ui.lEdit3, ui.lEdit4};
        for (int i = 0; i < layouts.length; i++) {
            constraintSets[i] = new ConstraintSet();

            if (i == 0)
                constraintSets[i].clone(ui.container);
            else
                constraintSets[i].clone(this, layouts[i]);
        }
    }

    public void initView() {
        ui.edit1.setOnEditorActionListener(editorActionListener);
        ui.edit2.setOnEditorActionListener(editorActionListener);
        ui.edit3.setOnEditorActionListener(editorActionListener);
        ui.edit4.setOnEditorActionListener(editorActionListener);

        ui.btn.setOnClickListener(view -> {
            if(getCurrentFocus() instanceof TextView){
                if(!TextUtils.isEmpty(((TextView) getCurrentFocus()).getText().toString())){
                    doTransition(getViewPosition(getCurrentFocus()));
                }
            }
        });
    }

    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if(actionId==EditorInfo.IME_ACTION_DONE){
            if (!TextUtils.isEmpty(textView.getText().toString())) {
                doTransition(getViewPosition(textView));
            }
        }
        return false;
    };

    private void setVisibleView() {
        for (int i = 0; i < editLayout.length - 1; i++) {
            if (editLayout[i + 1].getVisibility() == View.GONE) {
                doTransition(i + 1);
                break;
            }
        }
    }

    private int getViewPosition(View view){
        int pos=0;
        for(int i=0; i<editText.length-1; i++){
            if(view.getId()==editText[i].getId()){
                pos = i+1;
                break;
            }
        }
        return pos;
    }

    /**
     * 지정된 포지션에 대한 트랜지션 진행
     *
     * @param pos
     */
    private void doTransition(int pos) {
        if (editLayout[pos].getVisibility() == View.GONE) {
            Transition changeBounds = new ChangeBounds();
            changeBounds.setInterpolator(new OvershootInterpolator());
            TransitionManager.beginDelayedTransition(ui.container);
            constraintSets[pos].applyTo(ui.container);

            if (editText[pos] instanceof TextInputEditText) {
                editText[pos].requestFocus();
            }
        }
    }

    }
