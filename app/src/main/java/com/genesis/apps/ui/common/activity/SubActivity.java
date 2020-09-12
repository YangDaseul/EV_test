package com.genesis.apps.ui.common.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityBaseBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;


public abstract class SubActivity<T extends ViewDataBinding> extends BaseActivity {

    public ActivityBaseBinding base;
    public T ui;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(base==null) base = (ActivityBaseBinding) inflate(R.layout.activity_base);

    }

    @Override
    public void setContentView(int layoutResId) {
        ui = inflate(layoutResId);
        if (base == null) {
            base = (ActivityBaseBinding) inflate(R.layout.activity_base);
        }
        base.lContents.addView(ui.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        super.setContentView(base.getRoot());

        try{
            findViewById(R.id.back).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    onBackButton();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private <T extends ViewDataBinding> T inflate(int layoutResId) {
        return DataBindingUtil.inflate(getLayoutInflater(), layoutResId, null, false);
    }

    public final void showProgressDialog(final boolean show) {
        try {
            if (base.lProgress != null) {
                runOnUiThread(() -> {
                    base.lProgress.lProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                    AnimationDrawable animationDrawable = (AnimationDrawable) base.lProgress.ivProgress.getDrawable();
                    if (show) {
                        if (!animationDrawable.isRunning()) animationDrawable.start();
                    } else {
                        animationDrawable.stop();
                    }
                });
            }
//                runOnUiThread(() -> base.lProgress.lProgress.setVisibility(show ? View.VISIBLE : View.GONE));
        } catch (Exception e) {

        }
    }

//    public final void showProgressDialog(final boolean show) {
//        if(progressDialog==null) progressDialog = new ProgressDialog(SubActivity.this, AlertDialog.BUTTON_POSITIVE);
//
//        try {
//            runOnUiThread(() -> {
//                if (show) {
//                    progressDialog.setTitle("");
//                    progressDialog.setMessage("Loading");
//                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                    progressDialog.setCancelable(true);
//                    progressDialog.setCanceledOnTouchOutside(false);
//                    progressDialog.show();
//                } else {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                }
//            });
//        }catch (Exception ignore){
//            ignore.printStackTrace();
//        }
//    }

    public void onBackButton(){
        finish();
    }

}
