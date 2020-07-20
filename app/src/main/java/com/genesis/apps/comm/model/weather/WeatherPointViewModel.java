package com.genesis.apps.comm.model.weather;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class WeatherPointViewModel extends ViewModel {
    private final WeatherRepository repository;
    private final SavedStateHandle savedStateHandle;
    private LiveData<WeatherPointResVO> liveData;

    @ViewModelInject
    WeatherPointViewModel(
            WeatherRepository repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
    }

    public void init(WeatherPointReqVO reqData){
        liveData = repository.getWeatherPoint(reqData);
    }

    public LiveData<WeatherPointResVO> getLiveData() {
        return liveData;
    }
}