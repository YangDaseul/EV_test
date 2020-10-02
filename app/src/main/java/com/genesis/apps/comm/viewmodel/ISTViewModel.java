package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.api.IST_1001;
import com.genesis.apps.comm.model.gra.api.IST_1002;
import com.genesis.apps.comm.model.gra.api.IST_1003;
import com.genesis.apps.comm.model.gra.api.IST_1004;
import com.genesis.apps.comm.model.gra.api.IST_1005;
import com.genesis.apps.comm.model.repo.ISTRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import lombok.Data;

public @Data
class ISTViewModel extends ViewModel {

    private final ISTRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<IST_1001.Response>> RES_IST_1001;
    private MutableLiveData<NetUIResponse<IST_1002.Response>> RES_IST_1002;
    private MutableLiveData<NetUIResponse<IST_1003.Response>> RES_IST_1003;
    private MutableLiveData<NetUIResponse<IST_1004.Response>> RES_IST_1004;
    private MutableLiveData<NetUIResponse<IST_1005.Response>> RES_IST_1005;

    @ViewModelInject
    ISTViewModel(
            ISTRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_IST_1001 = repository.RES_IST_1001;
        RES_IST_1002 = repository.RES_IST_1002;
        RES_IST_1003 = repository.RES_IST_1003;
        RES_IST_1004 = repository.RES_IST_1004;
        RES_IST_1005 = repository.RES_IST_1005;
    }

    public void reqIST1001(final IST_1001.Request reqData) {
        repository.REQ_IST_1001(reqData);
    }

    public void reqIST1002(final IST_1002.Request reqData) {
        repository.REQ_IST_1002(reqData);
    }

    public void reqIST1003(final IST_1003.Request reqData) {
        repository.REQ_IST_1003(reqData);
    }

    public void reqIST1004(final IST_1004.Request reqData) {
        repository.REQ_IST_1004(reqData);
    }

    public void reqIST1005(final IST_1005.Request reqData) {
        repository.REQ_IST_1005(reqData);
    }
}