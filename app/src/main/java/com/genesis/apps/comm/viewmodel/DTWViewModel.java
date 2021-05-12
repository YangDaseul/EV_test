package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.DTW_1001;
import com.genesis.apps.comm.model.api.gra.DTW_1002;
import com.genesis.apps.comm.model.api.gra.DTW_1003;
import com.genesis.apps.comm.model.api.gra.DTW_1004;
import com.genesis.apps.comm.model.api.gra.DTW_1007;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.comm.model.repo.DTWRepo;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

public @Data
class DTWViewModel extends ViewModel {

    private final DTWRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<DTW_1001.Response>> RES_DTW_1001;
    private MutableLiveData<NetUIResponse<DTW_1002.Response>> RES_DTW_1002;
    private MutableLiveData<NetUIResponse<DTW_1003.Response>> RES_DTW_1003;
    private MutableLiveData<NetUIResponse<DTW_1004.Response>> RES_DTW_1004;
    private MutableLiveData<NetUIResponse<DTW_1007.Response>> RES_DTW_1007;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    DTWViewModel(
            DTWRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_DTW_1001 = repository.RES_DTW_1001;
        RES_DTW_1002 = repository.RES_DTW_1002;
        RES_DTW_1003 = repository.RES_DTW_1003;
        RES_DTW_1004 = repository.RES_DTW_1004;

        vehicleList = new MutableLiveData<>();
    }

    public void reqDTW1001(final DTW_1001.Request reqData) {
        repository.REQ_DTW_1001(reqData);
    }
    public void reqDTW1002(final DTW_1002.Request reqData) {
        repository.REQ_DTW_1002(reqData);
    }
    public void reqDTW1003(final DTW_1003.Request reqData) {
        repository.REQ_DTW_1003(reqData);
    }
    public void reqDTW1004(final DTW_1004.Request reqData) {
        repository.REQ_DTW_1004(reqData);
    }
    public void reqDTW1007(final DTW_1007.Request reqData) {
        repository.REQ_DTW_1007(reqData);
    }

    public VehicleVO getMainVehicleFromDB() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(() -> {
            VehicleVO vehicleVO = null;
            try {
                vehicleVO = dbVehicleRepository.getMainVehicleFromDB();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                vehicleVO = null;
            }
            return vehicleVO;
        });

        try {
            return future.get();
        } finally {
            es.shutDownExcutor();
        }
    }
}