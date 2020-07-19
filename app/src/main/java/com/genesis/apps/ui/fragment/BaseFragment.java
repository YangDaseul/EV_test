package com.genesis.apps.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import com.genesis.apps.ui.activity.BaseActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BaseFragment extends Fragment {

    @Inject
    public BaseActivity baseActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("baseActivity","baseActivity:" +  (baseActivity==null ? "null" : baseActivity.hashCode()));
//        A = (BaseActivity) getActivity();
    }

//    public boolean isRefresh = false;
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (isRefresh) {
//            // 갱신 처리
//            onRefresh();
//        }
//    }
//
//    /**
//     * 갱신 처리 - 해당 프레그먼트에서 상속받아서 처리
//     */
//    public void onRefresh() {
//        isRefresh = false;
//    }
//
//    public abstract boolean onBackPressed();
//
//    public boolean getBooleanArgument(String key, boolean defaultValue) {
//        return getArguments() == null ? defaultValue : getArguments().getBoolean(key, defaultValue);
//    }
//
//    public BaseFragment<T> setBooleanArgument(String key, boolean value) {
//        final Bundle bundle = getArguments() == null ? new Bundle() : getArguments();
//        bundle.putBoolean(key, value);
//        try { setArguments(bundle); } catch(Exception e) { e.printStackTrace(); }
//        return this;
//    }
//
//    public int getIntArgument(String key, int defaultValue) {
//        return getArguments() == null ? defaultValue : getArguments().getInt(key, defaultValue);
//    }
//
//    public BaseFragment<T> setIntArgument(String key, int value) {
//        final Bundle bundle = getArguments() == null ? new Bundle() : getArguments();
//        bundle.putInt(key, value);
//        try { setArguments(bundle); } catch(Exception e) { e.printStackTrace(); }
//        return this;
//    }
//
//    public String getStringArgument(String key, String defaultValue) {
//        return getArguments() == null ? defaultValue : getArguments().getString(key, defaultValue);
//    }
//
//    public BaseFragment<T> setStringArgument(String key, String value) {
//        final Bundle bundle = getArguments() == null ? new Bundle() : getArguments();
//        bundle.putString(key, value);
//        try { setArguments(bundle); } catch(Exception e) { e.printStackTrace(); }
//        return this;
//    }
//
//    public BaseFragment<T> removeArgument(String key) {
//        if (getArguments() != null) {
//            getArguments().remove(key);
//        }
//        return this;
//    }
//
//    public void startActivityTransition(Intent intent, boolean popupTransition) {
//        intent.putExtra(POPUP_TRANSITION, popupTransition);
//        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
//        startActivity(intent);
//        if (popupTransition) {
//            A.overridePendingTransition(R.anim.slide_up, R.anim.hold);
//        } else {
//            A.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
//        }
//    }
//
//    public void startActivityForResultTransition(Intent intent, int requestCode, boolean popupTransition) {
//        intent.putExtra(POPUP_TRANSITION, popupTransition);
//        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
//        startActivityForResult(intent, requestCode);
//        if (popupTransition) {
//            A.overridePendingTransition(R.anim.slide_up, R.anim.hold);
//        } else {
//            A.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
//        }
//    }
}
