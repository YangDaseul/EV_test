package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.roadwin.CheckPrice;
import com.genesis.apps.comm.model.api.roadwin.ServiceAreaCheck;
import com.genesis.apps.comm.model.api.roadwin.Work;
import com.genesis.apps.comm.model.repo.RoadWinRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import lombok.Data;

public @Data
class RoadWinViewModel extends ViewModel {

    private final RoadWinRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CheckPrice.Response>> RES_CHECK_PRICE;
    private MutableLiveData<NetUIResponse<ServiceAreaCheck.Response>> RES_SERVICE_AREA_CHECK;
    private MutableLiveData<NetUIResponse<Work.Response>> RES_WORK;


    @ViewModelInject
    RoadWinViewModel(
            RoadWinRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_CHECK_PRICE = repository.RES_CHECK_PRICE;
        RES_SERVICE_AREA_CHECK = repository.RES_SERVICE_AREA_CHECK;
        RES_WORK = repository.RES_WORK;
    }

    public void reqCheckPrice(final CheckPrice.Request reqData) {
        repository.REQ_CHECK_PRICE(reqData);
    }

    public void reqServiceAreaCheck(final ServiceAreaCheck.Request reqData) {
        repository.REQ_SERVICE_AREA_CHECK(reqData);
    }

    public void reqWork(final Work.Request reqData) {
        repository.REQ_WORK(reqData);
    }

}