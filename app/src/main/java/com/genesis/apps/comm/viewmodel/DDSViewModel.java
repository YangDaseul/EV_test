package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.api.DDS_1001;
import com.genesis.apps.comm.model.gra.api.DDS_1002;
import com.genesis.apps.comm.model.gra.api.DDS_1003;
import com.genesis.apps.comm.model.gra.api.DDS_1004;
import com.genesis.apps.comm.model.gra.api.DDS_1005;
import com.genesis.apps.comm.model.gra.api.DDS_1006;
import com.genesis.apps.comm.model.repo.DDSRepo;
import com.genesis.apps.comm.net.NetUIResponse;

import lombok.Data;

public @Data
class DDSViewModel extends ViewModel {

    private final DDSRepo repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<DDS_1001.Response>> RES_DDS_1001;
    private MutableLiveData<NetUIResponse<DDS_1002.Response>> RES_DDS_1002;
    private MutableLiveData<NetUIResponse<DDS_1003.Response>> RES_DDS_1003;
    private MutableLiveData<NetUIResponse<DDS_1004.Response>> RES_DDS_1004;
    private MutableLiveData<NetUIResponse<DDS_1005.Response>> RES_DDS_1005;
    private MutableLiveData<NetUIResponse<DDS_1006.Response>> RES_DDS_1006;
//    public final LiveData<VehicleVO> carVO =
//            Transformations.switchMap(RES_DDS_0001, new Function<NetUIResponse<DDS_0001.Response>, LiveData<VehicleVO>>() {
//                @Override
//                public LiveData<VehicleVO> apply(NetUIResponse<DDS_0001.Response> input) {
//                    return input.data.getCarVO(); repo에서 getcarvo로 가저올수있는.. 다른걸 요청 가능
//                }
//            });

    @ViewModelInject
    DDSViewModel(
            DDSRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        RES_DDS_1001 = repository.RES_DDS_1001;
        RES_DDS_1002 = repository.RES_DDS_1002;
        RES_DDS_1003 = repository.RES_DDS_1003;
        RES_DDS_1004 = repository.RES_DDS_1004;
        RES_DDS_1005 = repository.RES_DDS_1005;
        RES_DDS_1006 = repository.RES_DDS_1006;
    }

    public void reqDDS1001(final DDS_1001.Request reqData) {
        repository.REQ_DDS_1001(reqData);
    }

    public void reqDDS1002(final DDS_1002.Request reqData) {
        repository.REQ_DDS_1002(reqData);
    }

    public void reqDDS1003(final DDS_1003.Request reqData) {
        repository.REQ_DDS_1003(reqData);
    }

    public void reqDDS1004(final DDS_1004.Request reqData) {
        repository.REQ_DDS_1004(reqData);
    }

    public void reqDDS1005(final DDS_1005.Request reqData) {
        repository.REQ_DDS_1005(reqData);
    }

    public void reqDDS1006(final DDS_1006.Request reqData) {
        repository.REQ_DDS_1006(reqData);
    }
}