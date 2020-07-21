package com.genesis.apps.comm.model;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.net.NetUIResponse;

public class ExampleViewModel extends ViewModel {

    private final ExampleRepository repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<ExampleResVO>> exampleResVo = new MutableLiveData<>();

    @ViewModelInject
    ExampleViewModel(
            ExampleRepository repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
    }

    public void reqData(ExampleReqVO reqData, String url, String type){
        exampleResVo = repository.reqDataTest(reqData, url, type);
    }

    public MutableLiveData<NetUIResponse<ExampleResVO>> getResVo() {
        return exampleResVo;
    }

    public void setAdd(){
        exampleResVo.getValue().data.setValue(exampleResVo.getValue().data.getValue()+1);
        exampleResVo.setValue(exampleResVo.getValue());

    }
}