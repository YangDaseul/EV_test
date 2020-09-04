package com.genesis.apps.comm.model.gra.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.LGN_0001;
import com.genesis.apps.comm.model.gra.LGN_0002;
import com.genesis.apps.comm.model.gra.LGN_0003;
import com.genesis.apps.comm.model.gra.LGN_0004;
import com.genesis.apps.comm.model.gra.LGN_0005;
import com.genesis.apps.comm.model.gra.MYP_8001;
import com.genesis.apps.comm.model.gra.MYP_8002;
import com.genesis.apps.comm.model.gra.MYP_8003;
import com.genesis.apps.comm.model.gra.MYP_8004;
import com.genesis.apps.comm.model.gra.repo.LGNRepo;
import com.genesis.apps.comm.model.gra.repo.MYPRepo;
import com.genesis.apps.comm.model.vo.CarVO;
import com.genesis.apps.comm.net.NetUIResponse;

import lombok.Data;

public @Data
class MYPViewModel extends ViewModel {

    private final MYPRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<MYP_8001.Response>> RES_MYP_8001;
    private MutableLiveData<NetUIResponse<MYP_8002.Response>> RES_MYP_8002;
    private MutableLiveData<NetUIResponse<MYP_8003.Response>> RES_MYP_8003;
    private MutableLiveData<NetUIResponse<MYP_8004.Response>> RES_MYP_8004;

//    public final LiveData<CarVO> carVO = Transformations.map(RES_LGN_0001, input -> input.data.getCarVO());
//    public final LiveData<CarVO> carVO =
//            Transformations.switchMap(RES_LGN_0001, new Function<NetUIResponse<LGN_0001.Response>, LiveData<CarVO>>() {
//                @Override
//                public LiveData<CarVO> apply(NetUIResponse<LGN_0001.Response> input) {
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
        RES_MYP_8002 = repository.RES_MYP_8002;
        RES_MYP_8003 = repository.RES_MYP_8003;
        RES_MYP_8004 = repository.RES_MYP_8004;
    }

    public void reqMYP8001(final MYP_8001.Request reqData){
        RES_MYP_8001.setValue(repository.REQ_MYP_8001(reqData).getValue());
    }

    public void reqMYP8002(final MYP_8002.Request reqData){
        RES_MYP_8002.setValue(repository.REQ_MYP_8002(reqData).getValue());
    }

    public void reqMYP8003(final MYP_8003.Request reqData){
        RES_MYP_8003.setValue(repository.REQ_MYP_8003(reqData).getValue());
    }

    public void reqMYP8004(final MYP_8004.Request reqData){
        RES_MYP_8004.setValue(repository.REQ_MYP_8004(reqData).getValue());
    }

}