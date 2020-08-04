package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.ExampleViewModel;
import com.genesis.apps.databinding.Frame2pBinding;
import com.genesis.apps.ui.fragment.SubFragment;
import com.hmns.playmap.network.PlayMapRestApi;


public class FragSecond extends SubFragment<Frame2pBinding> {

    ExampleViewModel exampleViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_2p);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(exampleViewModel==null) {
            exampleViewModel = new ViewModelProvider(getActivity()).get(ExampleViewModel.class);
            me.setLifecycleOwner(this);
            me.setVm(exampleViewModel);
        }

//        final Observer<NetUIResponse<ExampleResVO>> observer = new Observer<NetUIResponse<ExampleResVO>>() {
//            @Override
//            public void onChanged(NetUIResponse<ExampleResVO> data) {
//                Log.v("testLiveData","DATA:" + data.data.getValue());
//            }
//        };
//        exampleViewModel.getResVo().observe(getActivity(), observer);

    }

    @Override
    public void onRefresh() {
        PlayMapRestApi mapRestApi = new PlayMapRestApi(getActivity());
        mapRestApi.setPlayMapApiKey("SE1DOkdlbmVTVEc=");

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
