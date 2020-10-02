package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.gra.api.CMN_0001;
import com.genesis.apps.comm.model.gra.api.CMN_0002;
import com.genesis.apps.comm.model.gra.api.CMN_0003;
import com.genesis.apps.comm.model.gra.api.CMN_0004;
import com.genesis.apps.comm.model.repo.CMNRepo;
import com.genesis.apps.comm.model.repo.DBGlobalDataRepository;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.PackageUtil;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class CMNViewModel extends ViewModel {

    private final CMNRepo repository;
    private final DBGlobalDataRepository dbGlobalDataRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CMN_0001.Response>> RES_CMN_0001;
    private MutableLiveData<NetUIResponse<CMN_0002.Response>> RES_CMN_0002;
    private MutableLiveData<NetUIResponse<CMN_0003.Response>> RES_CMN_0003;
    private MutableLiveData<NetUIResponse<CMN_0004.Response>> RES_CMN_0004;

    @ViewModelInject
    CMNViewModel(
            CMNRepo repository,
            DBGlobalDataRepository dbGlobalDataRepository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.dbGlobalDataRepository = dbGlobalDataRepository;
        this.savedStateHandle = savedStateHandle;

        RES_CMN_0001 = repository.RES_CMN_0001;
        RES_CMN_0002 = repository.RES_CMN_0002;
        RES_CMN_0003 = repository.RES_CMN_0003;
        RES_CMN_0004 = repository.RES_CMN_0004;
    }

    public void reqCMN0001(final CMN_0001.Request reqData){
        repository.REQ_CMN_0001(reqData);
    }
    public void reqCMN0002(final CMN_0002.Request reqData){
        repository.REQ_CMN_0002(reqData);
    }
    public void reqCMN0003(final CMN_0003.Request reqData){
        repository.REQ_CMN_0003(reqData);
    }
    public void reqCMN0004(final CMN_0004.Request reqData){
        repository.REQ_CMN_0004(reqData);
    }

    public void updateNotiDt(String notiDt){
        dbGlobalDataRepository.update(KeyNames.KEY_NAME_DB_GLOBAL_DATA_NOTIDT, notiDt);
    }
}