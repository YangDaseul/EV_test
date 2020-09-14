package com.genesis.apps.comm.model.gra.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.MYP_0001;
import com.genesis.apps.comm.model.gra.MYP_1003;
import com.genesis.apps.comm.model.gra.MYP_1005;
import com.genesis.apps.comm.model.gra.MYP_1006;
import com.genesis.apps.comm.model.gra.MYP_2006;
import com.genesis.apps.comm.model.gra.MYP_8001;
import com.genesis.apps.comm.model.gra.MYP_8004;
import com.genesis.apps.comm.model.gra.MYP_8005;
import com.genesis.apps.comm.model.gra.repo.MYPRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import lombok.Data;

public @Data
class MYPViewModel extends ViewModel {

    private final MYPRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<MYP_0001.Response>> RES_MYP_0001;

    private MutableLiveData<NetUIResponse<MYP_1003.Response>> RES_MYP_1003;
    private MutableLiveData<NetUIResponse<MYP_1005.Response>> RES_MYP_1005;
    private MutableLiveData<NetUIResponse<MYP_1006.Response>> RES_MYP_1006;

    private MutableLiveData<NetUIResponse<MYP_8001.Response>> RES_MYP_8001;
    private MutableLiveData<NetUIResponse<MYP_8004.Response>> RES_MYP_8004;
    private MutableLiveData<NetUIResponse<MYP_8005.Response>> RES_MYP_8005;

    private MutableLiveData<NetUIResponse<MYP_2006.Response>> RES_MYP_2006;

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

        RES_MYP_0001 = repository.RES_MYP_0001;
        RES_MYP_1003 = repository.RES_MYP_1003;
        RES_MYP_1005 = repository.RES_MYP_1005;
        RES_MYP_1006 = repository.RES_MYP_1006;
        
        RES_MYP_8001 = repository.RES_MYP_8001;
        RES_MYP_8004 = repository.RES_MYP_8004;
        RES_MYP_8005 = repository.RES_MYP_8005;
        RES_MYP_2006 = repository.RES_MYP_2006;
    }

    public void reqMYP0001(final MYP_0001.Request reqData){
        RES_MYP_0001.setValue(repository.REQ_MYP_0001(reqData).getValue());
    }

    public void reqMYP1003(final MYP_1003.Request reqData){
        RES_MYP_1003.setValue(repository.REQ_MYP_1003(reqData).getValue());
    }

    public void reqMYP1005(final MYP_1005.Request reqData){
        RES_MYP_1005.setValue(repository.REQ_MYP_1005(reqData).getValue());
    }

    public void reqMYP1006(final MYP_1006.Request reqData){
        RES_MYP_1006.setValue(repository.REQ_MYP_1006(reqData).getValue());
    }


    public void reqMYP8001(final MYP_8001.Request reqData){
        RES_MYP_8001.setValue(repository.REQ_MYP_8001(reqData).getValue());
    }

    public void reqMYP8004(final MYP_8004.Request reqData){
        RES_MYP_8004.setValue(repository.REQ_MYP_8004(reqData).getValue());
    }

    public void reqMYP8005(final MYP_8005.Request reqData){
        RES_MYP_8005.setValue(repository.REQ_MYP_8005(reqData).getValue());
    }

    public void reqMYP2006(final MYP_2006.Request reqData){
        RES_MYP_2006.setValue(repository.REQ_MYP_2006(reqData).getValue());
    }

}