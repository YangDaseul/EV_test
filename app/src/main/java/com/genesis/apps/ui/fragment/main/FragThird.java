package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.databinding.Frame3pBinding;
import com.genesis.apps.ui.fragment.SubFragment;

import androidx.annotation.Nullable;


public class FragThird extends SubFragment<Frame3pBinding> {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_3p);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}