package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.ExampleViewModel;
import com.genesis.apps.comm.model.map.MapViewModel;
import com.genesis.apps.databinding.Frame2pBinding;
import com.genesis.apps.ui.fragment.SubFragment;
import com.hmns.playmap.network.PlayMapRestApi;


public class FragSecond extends SubFragment<Frame2pBinding> {

    private MapViewModel mapViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_2p);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        me.setLifecycleOwner(getViewLifecycleOwner());
        mapViewModel.getTestCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.v("test", "Second:" + integer);
            }
        });

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
