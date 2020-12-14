package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.gra.WRT_1001;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.WRTRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class WRTViewModel extends ViewModel {

    private final WRTRepo repository;
    private final SavedStateHandle savedStateHandle;
    private final DBVehicleRepository dbVehicleRepository;

    private MutableLiveData<NetUIResponse<WRT_1001.Response>> RES_WRT_1001;

    @ViewModelInject
    WRTViewModel(
            WRTRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        this.dbVehicleRepository = dbVehicleRepository;

        RES_WRT_1001 = repository.RES_WRT_1001;
    }

    public void reqWRT1001(final WRT_1001.Request reqData) {
        repository.REQ_WRT_1001(reqData);
    }

    public VehicleVO getMainVehicleSimplyFromDB() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(()->{
            VehicleVO vehicleVO = null;
            try {
                vehicleVO = dbVehicleRepository.getMainVehicleSimplyFromDB();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                vehicleVO = null;
            }
            return vehicleVO;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

}