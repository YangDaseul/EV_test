package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.VOC_1001;
import com.genesis.apps.comm.model.api.gra.VOC_1002;
import com.genesis.apps.comm.model.api.gra.VOC_1003;
import com.genesis.apps.comm.model.api.gra.VOC_1004;
import com.genesis.apps.comm.model.api.gra.VOC_1005;
import com.genesis.apps.comm.model.repo.VOCRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import lombok.Data;

public @Data
class VOCViewModel extends ViewModel {

    private final VOCRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<VOC_1001.Response>> RES_VOC_1001;
    private MutableLiveData<NetUIResponse<VOC_1002.Response>> RES_VOC_1002;
    private MutableLiveData<NetUIResponse<VOC_1003.Response>> RES_VOC_1003;
    private MutableLiveData<NetUIResponse<VOC_1004.Response>> RES_VOC_1004;
    private MutableLiveData<NetUIResponse<VOC_1005.Response>> RES_VOC_1005;

    @ViewModelInject
    VOCViewModel(
            VOCRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_VOC_1001 = repository.RES_VOC_1001;
        RES_VOC_1002 = repository.RES_VOC_1002;
        RES_VOC_1003 = repository.RES_VOC_1003;
        RES_VOC_1004 = repository.RES_VOC_1004;
        RES_VOC_1005 = repository.RES_VOC_1005;
    }

    public void reqVOC1001(final VOC_1001.Request reqData) {
        repository.REQ_VOC_1001(reqData);
    }

    public void reqVOC1002(final VOC_1002.Request reqData) {
        repository.REQ_VOC_1002(reqData);
    }

    public void reqVOC1003(final VOC_1003.Request reqData) {
        repository.REQ_VOC_1003(reqData);
    }

    public void reqVOC1004(final VOC_1004.Request reqData) {
        repository.REQ_VOC_1004(reqData);
    }

    public void reqVOC1005(final VOC_1005.Request reqData) {
        repository.REQ_VOC_1005(reqData);
    }
}