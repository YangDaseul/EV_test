package com.genesis.apps.comm.model.weather;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.net.NetUIResponse;

public class WeatherPointViewModel extends ViewModel {
    private final WeatherRepository2 repository;
    private final SavedStateHandle savedStateHandle;
    private LiveData<NetUIResponse<WeatherPointResVO>> weatherPointResVOLiveData;

    @ViewModelInject
    WeatherPointViewModel(
            WeatherRepository2 repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
    }

    public void reqData(WeatherPointReqVO reqData, String url, String type){
            weatherPointResVOLiveData = repository.reqData(reqData, url, type);
    }

    public LiveData<NetUIResponse<WeatherPointResVO>> getWeatherPoint() {
        return weatherPointResVOLiveData;
    }
}