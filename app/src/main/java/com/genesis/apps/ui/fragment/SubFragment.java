package com.genesis.apps.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class SubFragment<T extends ViewDataBinding> extends BaseFragment {

    public static final String POPUP_TRANSITION = "POPUP_TRANSITION";
    public T me;

    public View setContentView(LayoutInflater layoutInflater, int layoutResId) {
        me = DataBindingUtil.inflate(layoutInflater, layoutResId, null, false);
        return me.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
            // 갱신 처리
            onRefresh();
    }

    /**
     * 갱신 처리 - 해당 프레그먼트에서 상속받아서 처리
     */
    public abstract void onRefresh();

    public abstract boolean onBackPressed();

    public boolean getBooleanArgument(String key, boolean defaultValue) {
        return getArguments() == null ? defaultValue : getArguments().getBoolean(key, defaultValue);
    }

    public SubFragment<T> setBooleanArgument(String key, boolean value) {
        final Bundle bundle = getArguments() == null ? new Bundle() : getArguments();
        bundle.putBoolean(key, value);
        try { setArguments(bundle); } catch(Exception e) { e.printStackTrace(); }
        return this;
    }

    public int getIntArgument(String key, int defaultValue) {
        return getArguments() == null ? defaultValue : getArguments().getInt(key, defaultValue);
    }

    public SubFragment<T> setIntArgument(String key, int value) {
        final Bundle bundle = getArguments() == null ? new Bundle() : getArguments();
        bundle.putInt(key, value);
        try { setArguments(bundle); } catch(Exception e) { e.printStackTrace(); }
        return this;
    }

    public String getStringArgument(String key, String defaultValue) {
        return getArguments() == null ? defaultValue : getArguments().getString(key, defaultValue);
    }

    public SubFragment<T> setStringArgument(String key, String value) {
        final Bundle bundle = getArguments() == null ? new Bundle() : getArguments();
        bundle.putString(key, value);
        try { setArguments(bundle); } catch(Exception e) { e.printStackTrace(); }
        return this;
    }

    public SubFragment<T> removeArgument(String key) {
        if (getArguments() != null) {
            getArguments().remove(key);
        }
        return this;
    }

    public void startActivityTransition(Intent intent, boolean popupTransition) {
        intent.putExtra(POPUP_TRANSITION, popupTransition);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        startActivity(intent);
        if (popupTransition) {
            baseActivity.overridePendingTransition(R.anim.slide_up, R.anim.hold);
            baseActivity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        } else {
        }
    }

    public void startActivityForResultTransition(Intent intent, int requestCode, boolean popupTransition) {
        intent.putExtra(POPUP_TRANSITION, popupTransition);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        startActivityForResult(intent, requestCode);
        if (popupTransition) {
            baseActivity.overridePendingTransition(R.anim.slide_up, R.anim.hold);
        } else {
            baseActivity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        }
    }
}
