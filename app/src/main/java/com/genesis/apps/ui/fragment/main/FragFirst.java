package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.ExampleViewModel;
import com.genesis.apps.databinding.Frame1pBinding;
import com.genesis.apps.ui.fragment.SubFragment;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class FragFirst extends SubFragment<Frame1pBinding> {
    private ExampleViewModel exampleViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_1p);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exampleViewModel = new ViewModelProvider(this).get(ExampleViewModel.class);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
