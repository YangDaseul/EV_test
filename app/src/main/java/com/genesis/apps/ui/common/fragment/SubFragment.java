package com.genesis.apps.ui.common.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class SubFragment<T extends ViewDataBinding> extends BaseFragment {

    public static final String POPUP_TRANSITION = "POPUP_TRANSITION";
    public T me;
    public OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            onClickCommon(v);
        }
    };
    public SubFragment(){

    }

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

    public void startForegroundService(Intent intent){
        if (Build.VERSION.SDK_INT >= 26) {
            getActivity().startForegroundService(intent);
        }
        else {
            getActivity().startService(intent);
        }
    }

    public void doFullScreen() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE|
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    public abstract void onClickCommon(View v);
}
