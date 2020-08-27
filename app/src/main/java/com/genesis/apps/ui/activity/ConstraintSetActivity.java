package com.genesis.apps.ui.activity;


import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.databinding.ActivityCont1Binding;

public class ConstraintSetActivity extends SubActivity<ActivityCont1Binding> {

    private final int[] layouts = {R.layout.activity_cont_1, R.layout.activity_cont_2, R.layout.activity_cont_3, R.layout.activity_cont_4};
    private ConstraintSet[] constraintSets = new ConstraintSet[layouts.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layouts[0]);
        initConstraintSets();
        swapView();
        SoftKeyboardUtil.showKeyboard(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initConstraintSets(){
        for(int i=0; i<layouts.length; i++){
            constraintSets[i] = new ConstraintSet();
            if(i==0)
                constraintSets[i].clone(ui.container);
            else
                constraintSets[i].clone(this, layouts[i]);
        }
    }

    public void swapView(){
        ui.edit1.setOnFocusChangeListener(focusChangeListener);
        ui.edit2.setOnFocusChangeListener(focusChangeListener);
        ui.edit3.setOnFocusChangeListener(focusChangeListener);

        ui.edit1.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
                switch (actionId) {
                    case 0 :
                        Transition changeBounds = new ChangeBounds();
                        changeBounds.setInterpolator(new OvershootInterpolator());
                        TransitionManager.beginDelayedTransition(ui.container);
                        if(ui.lEdit2.getVisibility()==View.GONE){
                            constraintSets[1].applyTo(ui.container);
                            ui.edit1.setOnFocusChangeListener(null);
                        }
                        // TODO : process action button "OK!" (100)
                        System.out.println("OK! action button is pressed.") ;
                        break ;
                }

                return true ;
            }
        });

    }


    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {

            if(!TextUtils.isEmpty(((TextView)view).getText().toString())) {
                switch (view.getId()) {
                    case R.id.edit1:

                        if(ui.lEdit2.getVisibility()==View.GONE){
                            Transition changeBounds = new ChangeBounds();
                            changeBounds.setInterpolator(new OvershootInterpolator());
                            TransitionManager.beginDelayedTransition(ui.container);
                            constraintSets[1].applyTo(ui.container);
                            ui.edit1.setOnFocusChangeListener(null);
                        }
                        break;
                    case R.id.edit2:
                        if(ui.lEdit3.getVisibility()==View.GONE){
                            Transition changeBounds = new ChangeBounds();
                            changeBounds.setInterpolator(new OvershootInterpolator());
                            TransitionManager.beginDelayedTransition(ui.container);
                            constraintSets[2].applyTo(ui.container);
                            ui.edit2.setOnFocusChangeListener(null);
                        }
                        break;
                    case R.id.edit3:
                        if(ui.lEdit4.getVisibility()==View.GONE){
                            Transition changeBounds = new ChangeBounds();
                            changeBounds.setInterpolator(new OvershootInterpolator());
                            TransitionManager.beginDelayedTransition(ui.container);
                            constraintSets[3].applyTo(ui.container);
                            ui.edit3.setOnFocusChangeListener(null);
                        }
                        break;
                    case R.id.edit4:
                        break;
                }

            }

        }
    };

}
