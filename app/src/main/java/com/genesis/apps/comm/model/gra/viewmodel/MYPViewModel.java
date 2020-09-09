package com.genesis.apps.comm.model.gra.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.MYP_8001;
import com.genesis.apps.comm.model.gra.MYP_8002;
import com.genesis.apps.comm.model.gra.MYP_8003;
import com.genesis.apps.comm.model.gra.MYP_8004;
import com.genesis.apps.comm.model.gra.repo.MYPRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import lombok.Data;

public @Data
class MYPViewModel extends ViewModel {

    private final MYPRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<MYP_8001.Response>> RES_MYP_8001;
    private MutableLiveData<NetUIResponse<MYP_8004.Response>> RES_MYP_8004;

//    public final LiveData<VehicleVO> carVO = Transformations.map(RES_LGN_0001, input -> input.data.getCarVO());
//    public final LiveData<VehicleVO> carVO =
//            Transformations.switchMap(RES_LGN_0001, new Function<NetUIResponse<LGN_0001.Response>, LiveData<VehicleVO>>() {
//                @Override
//                public LiveData<VehicleVO> apply(NetUIResponse<LGN_0001.Response> input) {
//                    return input.data.getCarVO(); repo에서 getcarvo로 가저올수있는.. 다른걸 요청 가능
//                }
//            });

    @ViewModelInject
    MYPViewModel(
            MYPRepo repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_MYP_8001 = repository.RES_MYP_8001;
        RES_MYP_8004 = repository.RES_MYP_8004;
    }

    public void reqMYP8001(final MYP_8001.Request reqData){
        RES_MYP_8001.setValue(repository.REQ_MYP_8001(reqData).getValue());
    }

    public void reqMYP8004(final MYP_8004.Request reqData){
        RES_MYP_8004.setValue(repository.REQ_MYP_8004(reqData).getValue());
    }

}