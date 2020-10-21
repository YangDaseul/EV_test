package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.genesis.apps.R;
import com.genesis.apps.databinding.FragmentServiceMaintenanceBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;

public class FragmentMaintenance extends SubFragment<FragmentServiceMaintenanceBinding> {
    private static final String TAG = FragmentMaintenance.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(): start");
        View view = super.setContentView(inflater, R.layout.fragment_service_maintenance);
        me.setFragment(this);

        return view;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

    }
}
