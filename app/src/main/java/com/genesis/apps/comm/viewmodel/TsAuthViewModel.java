package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.tsauth.GetCheckCarOwnerShip;
import com.genesis.apps.comm.model.api.tsauth.GetNewCarOwnerShip;
import com.genesis.apps.comm.model.repo.TsAuthRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class TsAuthViewModel extends ViewModel {

    private final TsAuthRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<GetNewCarOwnerShip.Response>> RES_GET_NEW_CAR_OWNER_SHIP;
    private MutableLiveData<NetUIResponse<GetCheckCarOwnerShip.Response>> RES_GET_CHECK_CAR_OWNER_SHIP;

    @ViewModelInject
    TsAuthViewModel(
            TsAuthRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        RES_GET_NEW_CAR_OWNER_SHIP = repository.RES_GET_NEW_CAR_OWNER_SHIP;
        RES_GET_CHECK_CAR_OWNER_SHIP = repository.RES_GET_CHECK_CAR_OWNER_SHIP;
    }

    public void reqGetNewCarOwnerShip(final GetNewCarOwnerShip.Request reqData) {
        repository.REQ_GET_NEW_CAR_OWNER_SHIP(reqData);
    }

    public void reqGetCheckCarOwnerShip(final GetCheckCarOwnerShip.Request reqData) {
        repository.REQ_GET_CHECK_CAR_OWNER_SHIP(reqData);
    }
}