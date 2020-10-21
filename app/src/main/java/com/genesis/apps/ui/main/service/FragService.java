package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.databinding.FragmentServiceBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;

public class FragService extends SubFragment<FragmentServiceBinding> {
    //TODO ViewModel
//    private ViewModel ViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_service);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO ViewModel
//        ViewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        initViewPager();

    }

    private void initViewPager() {
        //ViewPager Setting


    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {

        }
    }

    @Override
    public void onRefresh() {

        Log.e("onResume", "onReusme contents");


        ((MainActivity) getActivity()).setGNB(false, true, 0, View.VISIBLE);
    }


}
