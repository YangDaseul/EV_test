package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.gra.WRT_1001;
import com.genesis.apps.comm.model.repo.WRTRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class WRTViewModel extends ViewModel {

    private final WRTRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<WRT_1001.Response>> RES_WRT_1001;

    @ViewModelInject
    WRTViewModel(
            WRTRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_WRT_1001 = repository.RES_WRT_1001;
    }

    public void reqWRT1001(final WRT_1001.Request reqData) {
        repository.REQ_WRT_1001(reqData);
    }

}