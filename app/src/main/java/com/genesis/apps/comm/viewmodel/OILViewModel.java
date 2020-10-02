package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.gra.api.OIL_0001;
import com.genesis.apps.comm.model.gra.api.OIL_0002;
import com.genesis.apps.comm.model.gra.api.OIL_0003;
import com.genesis.apps.comm.model.gra.api.OIL_0004;
import com.genesis.apps.comm.model.gra.api.OIL_0005;
import com.genesis.apps.comm.model.repo.OILRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class OILViewModel extends ViewModel {

    private final OILRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<OIL_0001.Response>> RES_OIL_0001;
    private MutableLiveData<NetUIResponse<OIL_0002.Response>> RES_OIL_0002;
    private MutableLiveData<NetUIResponse<OIL_0003.Response>> RES_OIL_0003;
    private MutableLiveData<NetUIResponse<OIL_0004.Response>> RES_OIL_0004;
    private MutableLiveData<NetUIResponse<OIL_0005.Response>> RES_OIL_0005;

    @ViewModelInject
    OILViewModel(
            OILRepo repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_OIL_0001 = repository.RES_OIL_0001;
        RES_OIL_0002 = repository.RES_OIL_0002;
        RES_OIL_0003 = repository.RES_OIL_0003;
        RES_OIL_0004 = repository.RES_OIL_0004;
        RES_OIL_0005 = repository.RES_OIL_0005;
    }

    public void reqOIL0001(final OIL_0001.Request reqData){
        repository.REQ_OIL_0001(reqData);
    }
    public void reqOIL0002(final OIL_0002.Request reqData){
        repository.REQ_OIL_0002(reqData);
    }
    public void reqOIL0003(final OIL_0003.Request reqData){
        repository.REQ_OIL_0003(reqData);
    }
    public void reqOIL0004(final OIL_0004.Request reqData){
        repository.REQ_OIL_0004(reqData);
    }
    public void reqOIL0005(final OIL_0005.Request reqData){
        repository.REQ_OIL_0005(reqData);
    }
}