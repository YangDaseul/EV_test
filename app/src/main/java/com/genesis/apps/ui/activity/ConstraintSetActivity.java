package com.genesis.apps.ui.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityCont1Binding;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

public class ConstraintSetActivity extends SubActivity<ActivityCont1Binding> {

    private ConstraintSet[] constraintSets = new ConstraintSet[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cont_1);

        constraintSets[0] = new ConstraintSet();
        constraintSets[0].clone(ui.container);
        constraintSets[1] = new ConstraintSet();
        constraintSets[1].clone(this, R.layout.activity_cont_2);
        constraintSets[2] = new ConstraintSet();
        constraintSets[2].clone(this, R.layout.activity_cont_3);
        constraintSets[3] = new ConstraintSet();
        constraintSets[3].clone(this, R.layout.activity_cont_4);
        swapView();
    }


    public void swapView(){
        Transition changeBounds = new ChangeBounds();
        changeBounds.setInterpolator(new OvershootInterpolator());
        TransitionManager.beginDelayedTransition(ui.container);


        ui.edit1.setOnFocusChangeListener(focusChangeListener);
        ui.edit2.setOnFocusChangeListener(focusChangeListener);
        ui.edit3.setOnFocusChangeListener(focusChangeListener);

        ui.edit1.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
                switch (actionId) {
                    case 0 :
//                        if(ui.edit2.getVisibility()==View.GONE){
                            constraintSets[1].applyTo(ui.container);
                            ui.edit1.setOnFocusChangeListener(null);
//                        }
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
//                        if(ui.edit2.getVisibility()==View.GONE){
                            constraintSets[1].applyTo(ui.container);
                            ui.edit1.setOnFocusChangeListener(null);
//                        }
                        break;
                    case R.id.edit2:
//                        if(ui.edit3.getVisibility()==View.GONE){
                            constraintSets[2].applyTo(ui.container);
                            ui.edit2.setOnFocusChangeListener(null);
//                        }
                        break;
                    case R.id.edit3:
//                        if(ui.edit4.getVisibility()==View.GONE){
                            constraintSets[3].applyTo(ui.container);
                            ui.edit3.setOnFocusChangeListener(null);
//                        }
                        break;
                    case R.id.edit4:
                        break;
                }

            }

        }
    };

}
