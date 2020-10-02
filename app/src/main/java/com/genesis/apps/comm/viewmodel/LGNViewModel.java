package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.api.LGN_0001;
import com.genesis.apps.comm.model.gra.api.LGN_0002;
import com.genesis.apps.comm.model.gra.api.LGN_0003;
import com.genesis.apps.comm.model.gra.api.LGN_0004;
import com.genesis.apps.comm.model.gra.api.LGN_0005;
import com.genesis.apps.comm.model.gra.api.LGN_0006;
import com.genesis.apps.comm.model.gra.api.LGN_0007;
import com.genesis.apps.comm.model.repo.LGNRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;

import java.util.List;

import lombok.Data;

public @Data
class LGNViewModel extends ViewModel {

    private final LGNRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<LGN_0001.Response>> RES_LGN_0001;
    private MutableLiveData<NetUIResponse<LGN_0002.Response>> RES_LGN_0002;
    private MutableLiveData<NetUIResponse<LGN_0003.Response>> RES_LGN_0003;
    private MutableLiveData<NetUIResponse<LGN_0004.Response>> RES_LGN_0004;
    private MutableLiveData<NetUIResponse<LGN_0005.Response>> RES_LGN_0005;

    private MutableLiveData<NetUIResponse<LGN_0006.Response>> RES_LGN_0006;
    private MutableLiveData<NetUIResponse<LGN_0007.Response>> RES_LGN_0007;

    public final LiveData<List<VehicleVO>> carVO = Transformations.map(RES_LGN_0001, input -> input.data.getOwnVhclList());


//    public final LiveData<VehicleVO> carVO =
//            Transformations.switchMap(RES_LGN_0001, new Function<NetUIResponse<LGN_0001.Response>, LiveData<VehicleVO>>() {
//                @Override
//                public LiveData<VehicleVO> apply(NetUIResponse<LGN_0001.Response> input) {
//                    return input.data.getCarVO(); repo에서 getcarvo로 가저올수있는.. 다른걸 요청 가능
//                }
//            });

    @ViewModelInject
    LGNViewModel(
            LGNRepo repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_LGN_0001 = repository.RES_LGN_0001;
        RES_LGN_0002 = repository.RES_LGN_0002;
        RES_LGN_0003 = repository.RES_LGN_0003;
        RES_LGN_0004 = repository.RES_LGN_0004;
        RES_LGN_0005 = repository.RES_LGN_0005;

        RES_LGN_0006 = repository.RES_LGN_0006;
        RES_LGN_0007 = repository.RES_LGN_0007;
    }

    public void reqLGN0001(final LGN_0001.Request reqData){
        RES_LGN_0001.setValue(repository.REQ_LGN_0001(reqData).getValue());
    }

    public void reqLGN0002(final LGN_0002.Request reqData){
        RES_LGN_0002.setValue(repository.REQ_LGN_0002(reqData).getValue());
    }

    public void reqLGN0003(final LGN_0003.Request reqData){
        RES_LGN_0003.setValue(repository.REQ_LGN_0003(reqData).getValue());
    }

    public void reqLGN0004(final LGN_0004.Request reqData){
        RES_LGN_0004.setValue(repository.REQ_LGN_0004(reqData).getValue());
    }

    public void reqLGN0005(final LGN_0005.Request reqData){
        RES_LGN_0005.setValue(repository.REQ_LGN_0005(reqData).getValue());
    }

    public void reqLGN0006(final LGN_0006.Request reqData){
        RES_LGN_0006.setValue(repository.REQ_LGN_0006(reqData).getValue());
    }

    public void reqLGN0007(final LGN_0007.Request reqData){
        RES_LGN_0007.setValue(repository.REQ_LGN_0007(reqData).getValue());
    }
}