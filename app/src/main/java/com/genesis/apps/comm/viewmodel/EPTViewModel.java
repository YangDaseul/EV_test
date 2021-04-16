package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.EPT_1001;
import com.genesis.apps.comm.model.api.gra.EPT_1002;
import com.genesis.apps.comm.model.api.gra.EPT_1003;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.EPTRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;

import java.util.List;

import lombok.Data;

/**
 * Class Name : EPTViewModel
 *
 * @author Ki-man Kim
 * @since 2021-04-16
 */
public @Data
class EPTViewModel extends ViewModel {

    private EPTRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<EPT_1001.Response>> RES_EPT_1001;
    private MutableLiveData<NetUIResponse<EPT_1002.Response>> RES_EPT_1002;
    private MutableLiveData<NetUIResponse<EPT_1003.Response>> RES_EPT_1003;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    public EPTViewModel(
            EPTRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;
        this.vehicleList = new MutableLiveData<>();

        RES_EPT_1001 = repository.RES_EPT_1001;
        RES_EPT_1002 = repository.RES_EPT_1002;
        RES_EPT_1003 = repository.RES_EPT_1003;
    }

    public void reqEPT1001(final EPT_1001.Request reqData) {
        repository.REQ_EPT_1001(reqData);
    }

    public void reqEPT1002(final EPT_1002.Request reqData) {
        repository.REQ_EPT_1002(reqData);
    }

    public void reqEPT1003(final EPT_1003.Request reqData) {
        repository.REQ_EPT_1003(reqData);
    }
} // end of class EPTViewModel
