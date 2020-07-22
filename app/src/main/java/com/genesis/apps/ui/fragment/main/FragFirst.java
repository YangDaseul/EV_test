package com.genesis.apps.ui.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.ExampleReqVO;
import com.genesis.apps.comm.model.ExampleResVO;
import com.genesis.apps.comm.model.ExampleViewModel;
import com.genesis.apps.comm.model.weather.WeatherPointResVO;
import com.genesis.apps.comm.model.weather.WeatherPointViewModel;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.databinding.Frame1pBinding;
import com.genesis.apps.ui.fragment.SubFragment;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class FragFirst extends SubFragment<Frame1pBinding> {
    private ExampleViewModel exampleViewModel;
//    private WeatherPointViewModel weatherPointViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_1p);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exampleViewModel = new ViewModelProvider(getActivity()).get(ExampleViewModel.class);


        ExampleReqVO exampleReqVO = new ExampleReqVO();
        exampleReqVO.setRequestID("park");
        exampleReqVO.setValue(1);
        exampleViewModel.reqData(exampleReqVO,"","");

        final Observer<NetUIResponse<ExampleResVO>> observer = new Observer<NetUIResponse<ExampleResVO>>() {
            @Override
            public void onChanged(NetUIResponse<ExampleResVO> data) {
                Log.v("testLiveData","DATA:" + data.data.getValue());
            }
        };
        exampleViewModel.getResVo().observe(getActivity(), observer);

        me.tvName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exampleViewModel.setAdd();
            }
        });


//        weatherPointViewModel = new ViewModelProvider(getActivity()).get(WeatherPointViewModel.class);
//        weatherPointViewModel.reqData();
//
//        final Observer<NetUIResponse<WeatherPointResVO>> weatherPointResVOObserver = new Observer<NetUIResponse<WeatherPointResVO>>() {
//            @Override
//            public void onChanged(NetUIResponse<WeatherPointResVO> weatherPointResVONetUIResponse) {
//
//            }
//        };
//
//        weatherPointViewModel.getWeatherPoint().observe(getActivity(), weatherPointResVOObserver);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
