package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.gra.RMT_1001;
import com.genesis.apps.comm.model.api.gra.RMT_1002;
import com.genesis.apps.comm.model.api.gra.RMT_1003;
import com.genesis.apps.comm.model.api.gra.RMT_1004;
import com.genesis.apps.comm.model.api.gra.RMT_1005;
import com.genesis.apps.comm.model.api.gra.RMT_1006;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.RMTRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

public @Data
class RMTViewModel extends ViewModel {

    private final RMTRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<RMT_1001.Response>> RES_RMT_1001;
    private MutableLiveData<NetUIResponse<RMT_1002.Response>> RES_RMT_1002;
    private MutableLiveData<NetUIResponse<RMT_1003.Response>> RES_RMT_1003;
    private MutableLiveData<NetUIResponse<RMT_1004.Response>> RES_RMT_1004;
    private MutableLiveData<NetUIResponse<RMT_1005.Response>> RES_RMT_1005;
    private MutableLiveData<NetUIResponse<RMT_1006.Response>> RES_RMT_1006;

    @ViewModelInject
    RMTViewModel(
            RMTRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_RMT_1001 = repository.RES_RMT_1001;
        RES_RMT_1002 = repository.RES_RMT_1002;
        RES_RMT_1003 = repository.RES_RMT_1003;
        RES_RMT_1004 = repository.RES_RMT_1004;
        RES_RMT_1005 = repository.RES_RMT_1005;
        RES_RMT_1006 = repository.RES_RMT_1006;
    }

    public void reqRMT1001(final RMT_1001.Request reqData) {
        repository.REQ_RMT_1001(reqData);
    }

    public void reqRMT1002(final RMT_1002.Request reqData) {
        repository.REQ_RMT_1002(reqData);
    }

    public void reqRMT1003(final RMT_1003.Request reqData) {
        repository.REQ_RMT_1003(reqData);
    }

    public void reqRMT1004(final RMT_1004.Request reqData) {
        repository.REQ_RMT_1004(reqData);
    }

    public void reqRMT1005(final RMT_1005.Request reqData) {
        repository.REQ_RMT_1005(reqData);
    }

    public void reqRMT1006(final RMT_1006.Request reqData) {
        repository.REQ_RMT_1006(reqData);
    }

    public VehicleVO getMainVehicle() throws ExecutionException, InterruptedException {
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