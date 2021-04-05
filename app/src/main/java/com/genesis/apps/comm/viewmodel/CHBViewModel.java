package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.CHB_1001;
import com.genesis.apps.comm.model.api.gra.CHB_1006;
import com.genesis.apps.comm.model.api.gra.CHB_1007;
import com.genesis.apps.comm.model.api.gra.CHB_1008;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.CHB_1016;
import com.genesis.apps.comm.model.api.gra.CHB_1017;
import com.genesis.apps.comm.model.repo.CHBRepo;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

public @Data
class CHBViewModel extends ViewModel {

    private final CHBRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CHB_1001.Response>> RES_CHB_1001;
    private MutableLiveData<NetUIResponse<CHB_1006.Response>> RES_CHB_1006;
    private MutableLiveData<NetUIResponse<CHB_1007.Response>> RES_CHB_1007;
    private MutableLiveData<NetUIResponse<CHB_1008.Response>> RES_CHB_1008;
    private MutableLiveData<NetUIResponse<CHB_1015.Response>> RES_CHB_1015;
    private MutableLiveData<NetUIResponse<CHB_1016.Response>> RES_CHB_1016;
    private MutableLiveData<NetUIResponse<CHB_1017.Response>> RES_CHB_1017;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    CHBViewModel(
            CHBRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_CHB_1001 = repository.RES_CHB_1001;
        RES_CHB_1006 = repository.RES_CHB_1006;
        RES_CHB_1007 = repository.RES_CHB_1007;
        RES_CHB_1008 = repository.RES_CHB_1008;

        RES_CHB_1015 = repository.RES_CHB_1015;
        RES_CHB_1016 = repository.RES_CHB_1016;
        RES_CHB_1017 = repository.RES_CHB_1017;
        vehicleList = new MutableLiveData<>();
    }

    public void reqCHB1001(final CHB_1001.Request reqData) {
        repository.REQ_CHB_1001(reqData);
    }
    public void reqCHB1006(final CHB_1006.Request reqData) {
        repository.REQ_CHB_1006(reqData);
    }
    public void reqCHB1007(final CHB_1007.Request reqData) {
        repository.REQ_CHB_1007(reqData);
    }
    public void reqCHB1008(final CHB_1008.Request reqData) {
        repository.REQ_CHB_1008(reqData);
    }
    public void reqCHB1015(final CHB_1015.Request reqData) {
        repository.REQ_CHB_1015(reqData);
    }
    public void reqCHB1016(final CHB_1016.Request reqData) {
        repository.REQ_CHB_1016(reqData);
    }
    public void reqCHB1017(final CHB_1017.Request reqData) {
        repository.REQ_CHB_1017(reqData);
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