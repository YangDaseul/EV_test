package com.genesis.apps.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityBaseBinding;


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
    }

    private <T extends ViewDataBinding> T inflate(int layoutResId) {
        return DataBindingUtil.inflate(getLayoutInflater(), layoutResId, null, false);
    }

//    public final void showProgressDialog(final boolean show) {
//        try {
//            if (base.lProgress != null)
//                runOnUiThread(() -> base.lProgress.lProgress.setVisibility(show ? View.VISIBLE : View.GONE));
//        } catch (Exception e) {
//
//        }
//    }

    public final void showProgressDialog(final boolean show) {
        if(progressDialog==null) progressDialog = new ProgressDialog(SubActivity.this, AlertDialog.BUTTON_POSITIVE);

        try {
            runOnUiThread(() -> {
                if (show) {
                    progressDialog.setTitle("");
                    progressDialog.setMessage("Loading");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                } else {
                    progressDialog.hide();
                }
            });
        }catch (Exception ignore){

        }
    }

}
