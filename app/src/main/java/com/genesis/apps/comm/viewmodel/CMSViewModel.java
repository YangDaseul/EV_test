package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.gra.CMS_1001;
import com.genesis.apps.comm.model.repo.CMSRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class CMSViewModel extends ViewModel {

    private final CMSRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CMS_1001.Response>> RES_CMS_1001;

    @ViewModelInject
    CMSViewModel(
            CMSRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_CMS_1001 = repository.RES_CMS_1001;
    }

    public void reqCMS1001(final CMS_1001.Request reqData) {
        repository.REQ_CMS_1001(reqData);
    }
}