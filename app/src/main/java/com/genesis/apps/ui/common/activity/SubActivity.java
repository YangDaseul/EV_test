package com.genesis.apps.ui.common.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ActivityBaseBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;


public abstract class SubActivity<T extends ViewDataBinding> extends BaseActivity {

    public ActivityBaseBinding base;
    public T ui;
    private ProgressDialog progressDialog;
    public OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            onClickCommon(v);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }



//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

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
        Log.v("test","test show:"+show);
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
        closeTransition();
    }
    public abstract void onClickCommon(View v);
    public abstract void setViewModel();
    public abstract void setObserver();
    public abstract void getDataFromIntent();

    public void setResizeScreen(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes(lp);

    }



    public <T extends SubFragment> T getFragment(int id) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(id);
        return (T) fragment;
    }

    public <T extends SubFragment> List<T> getFragments() {
        final List<T> fragmentList = new ArrayList<>();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof SubFragment) {
                fragmentList.add((T) fragment);
            }
        }
        return fragmentList;
    }

    public <T extends SubFragment> boolean isVisible(T subFragment) {
        for (Fragment fragment : getFragments()) {
            if (fragment.isVisible() && fragment.equals(subFragment)) {
                return true;
            }
        }
        return false;
    }

    public <T extends SubFragment> T getVisibleFragment() {
        Fragment visibleFragment = null;
        for (Fragment fragment : getFragments()) {
            if (fragment.isVisible()) {
                visibleFragment = fragment;
                break;
            }
        }
        return (T) visibleFragment;
    }

    public <T extends SubFragment> T getBackFragment(T visibleFragment) {
        final List<T> fragmentList = getFragments();
        final int index = fragmentList.indexOf(visibleFragment);
        if (index > 0) {
            return fragmentList.get(index - 1);
        }
        return null;
    }

    public <T extends SubFragment> void showFragment(T fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_stay);
        transaction.replace(R.id.l_fragment, fragment);
        transaction.commitAllowingStateLoss();
    }

    public <T extends SubFragment> void showFragment(T fragment, Bundle bundle) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_stay);
        if(bundle!=null) fragment.setArguments(bundle);
        transaction.replace(R.id.l_fragment, fragment);
        transaction.commitAllowingStateLoss();
    }

    public <T extends SubFragment> void hideFragment(T fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(0, R.anim.fragment_exit_toright);
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    public void clearFragmentStack() {
        try {
            int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntry > 0) {
                for (int i = 0; i < backStackEntry; i++) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
            if (getSupportFragmentManager().getFragments().size() > 0) {
                for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                    Fragment fragment = getSupportFragmentManager().getFragments().get(i);
                    if (fragment != null) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
