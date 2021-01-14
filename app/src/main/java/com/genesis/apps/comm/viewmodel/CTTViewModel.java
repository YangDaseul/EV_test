package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.gra.CTT_1001;
import com.genesis.apps.comm.model.api.gra.CTT_1002;
import com.genesis.apps.comm.model.api.gra.CTT_1004;
import com.genesis.apps.comm.model.repo.CTTRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class CTTViewModel extends ViewModel {

    private final CTTRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CTT_1001.Response>> RES_CTT_1001;
    private MutableLiveData<NetUIResponse<CTT_1002.Response>> RES_CTT_1002;
    private MutableLiveData<NetUIResponse<CTT_1004.Response>> RES_CTT_1004;

    @ViewModelInject
    CTTViewModel(
            CTTRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_CTT_1001 = repository.RES_CTT_1001;
        RES_CTT_1002 = repository.RES_CTT_1002;
        RES_CTT_1004 = repository.RES_CTT_1004;
    }

    public void reqCTT1001(final CTT_1001.Request reqData) {
        repository.REQ_CTT_1001(reqData);
    }

    public void reqCTT1002(final CTT_1002.Request reqData) {
        repository.REQ_CTT_1002(reqData);
    }

    public void reqCTT1004(final CTT_1004.Request reqData) {
        repository.REQ_CTT_1004(reqData);
    }



}