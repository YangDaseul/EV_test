package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.api.BTR_1001;
import com.genesis.apps.comm.model.gra.api.BTR_1008;
import com.genesis.apps.comm.model.gra.api.BTR_1009;
import com.genesis.apps.comm.model.gra.api.BTR_1010;
import com.genesis.apps.comm.model.gra.api.BTR_2001;
import com.genesis.apps.comm.model.gra.api.BTR_2002;
import com.genesis.apps.comm.model.gra.api.BTR_2003;
import com.genesis.apps.comm.model.repo.BTRRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import lombok.Data;

public @Data
class BTRViewModel extends ViewModel {

    private final BTRRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<BTR_1001.Response>> RES_BTR_1001;
    private MutableLiveData<NetUIResponse<BTR_1008.Response>> RES_BTR_1008;
    private MutableLiveData<NetUIResponse<BTR_1009.Response>> RES_BTR_1009;
    private MutableLiveData<NetUIResponse<BTR_1010.Response>> RES_BTR_1010;
    private MutableLiveData<NetUIResponse<BTR_2001.Response>> RES_BTR_2001;
    private MutableLiveData<NetUIResponse<BTR_2002.Response>> RES_BTR_2002;
    private MutableLiveData<NetUIResponse<BTR_2003.Response>> RES_BTR_2003;

    @ViewModelInject
    BTRViewModel(
            BTRRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_BTR_1001 = repository.RES_BTR_1001;
        RES_BTR_1008 = repository.RES_BTR_1008;
        RES_BTR_1009 = repository.RES_BTR_1009;
        RES_BTR_1010 = repository.RES_BTR_1010;
        RES_BTR_2001 = repository.RES_BTR_2001;
        RES_BTR_2002 = repository.RES_BTR_2002;
        RES_BTR_2003 = repository.RES_BTR_2003;
    }

    public void reqBTR1001(final BTR_1001.Request reqData) {
        repository.REQ_BTR_1001(reqData);
    }

    public void reqBTR1008(final BTR_1008.Request reqData) {
        repository.REQ_BTR_1008(reqData);
    }

    public void reqBTR1009(final BTR_1009.Request reqData) {
        repository.REQ_BTR_1009(reqData);
    }

    public void reqBTR1010(final BTR_1010.Request reqData) {
        repository.REQ_BTR_1010(reqData);
    }

    public void reqBTR2001(final BTR_2001.Request reqData) {
        repository.REQ_BTR_2001(reqData);
    }

    public void reqBTR2002(final BTR_2002.Request reqData) {
        repository.REQ_BTR_2002(reqData);
    }

    public void reqBTR2003(final BTR_2003.Request reqData) {
        repository.REQ_BTR_2003(reqData);
    }


}