package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1002;
import com.genesis.apps.comm.model.api.gra.SOS_1003;
import com.genesis.apps.comm.model.api.gra.SOS_1004;
import com.genesis.apps.comm.model.api.gra.SOS_1005;
import com.genesis.apps.comm.model.api.gra.SOS_1006;
import com.genesis.apps.comm.model.api.gra.SOS_3001;
import com.genesis.apps.comm.model.api.gra.SOS_3002;
import com.genesis.apps.comm.model.api.gra.SOS_3003;
import com.genesis.apps.comm.model.api.gra.SOS_3004;
import com.genesis.apps.comm.model.api.gra.SOS_3005;
import com.genesis.apps.comm.model.api.gra.SOS_3006;
import com.genesis.apps.comm.model.api.gra.SOS_3007;
import com.genesis.apps.comm.model.api.gra.SOS_3008;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.SOSRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class SOSViewModel extends ViewModel {

    private final SOSRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<SOS_1001.Response>> RES_SOS_1001;
    private MutableLiveData<NetUIResponse<SOS_1002.Response>> RES_SOS_1002;
    private MutableLiveData<NetUIResponse<SOS_1003.Response>> RES_SOS_1003;
    private MutableLiveData<NetUIResponse<SOS_1004.Response>> RES_SOS_1004;
    private MutableLiveData<NetUIResponse<SOS_1005.Response>> RES_SOS_1005;
    private MutableLiveData<NetUIResponse<SOS_1006.Response>> RES_SOS_1006;

    private MutableLiveData<NetUIResponse<SOS_3001.Response>> RES_SOS_3001;
    private MutableLiveData<NetUIResponse<SOS_3002.Response>> RES_SOS_3002;
    private MutableLiveData<NetUIResponse<SOS_3003.Response>> RES_SOS_3003;
    private MutableLiveData<NetUIResponse<SOS_3004.Response>> RES_SOS_3004;
    private MutableLiveData<NetUIResponse<SOS_3005.Response>> RES_SOS_3005;
    private MutableLiveData<NetUIResponse<SOS_3006.Response>> RES_SOS_3006;
    private MutableLiveData<NetUIResponse<SOS_3007.Response>> RES_SOS_3007;
    private MutableLiveData<NetUIResponse<SOS_3008.Response>> RES_SOS_3008;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    SOSViewModel(
            SOSRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_SOS_1001 = repository.RES_SOS_1001;
        RES_SOS_1002 = repository.RES_SOS_1002;
        RES_SOS_1003 = repository.RES_SOS_1003;
        RES_SOS_1004 = repository.RES_SOS_1004;
        RES_SOS_1005 = repository.RES_SOS_1005;
        RES_SOS_1006 = repository.RES_SOS_1006;
        RES_SOS_3001 = repository.RES_SOS_3001;
        RES_SOS_3002 = repository.RES_SOS_3002;
        RES_SOS_3003 = repository.RES_SOS_3003;
        RES_SOS_3004 = repository.RES_SOS_3004;
        RES_SOS_3005 = repository.RES_SOS_3005;
        RES_SOS_3006 = repository.RES_SOS_3006;
        RES_SOS_3007 = repository.RES_SOS_3007;
        RES_SOS_3008 = repository.RES_SOS_3008;
        vehicleList = new MutableLiveData<>();
    }

    public void reqSOS1001(final SOS_1001.Request reqData) {
        repository.REQ_SOS_1001(reqData);
    }

    public void reqSOS1002(final SOS_1002.Request reqData) {
        repository.REQ_SOS_1002(reqData);
    }

    public void reqSOS1003(final SOS_1003.Request reqData) {
        repository.REQ_SOS_1003(reqData);
    }

    public void reqSOS1004(final SOS_1004.Request reqData) {
        repository.REQ_SOS_1004(reqData);
    }

    public void reqSOS1005(final SOS_1005.Request reqData) {
        repository.REQ_SOS_1005(reqData);
    }

    public void reqSOS1006(final SOS_1006.Request reqData) {
        repository.REQ_SOS_1006(reqData);
    }

    public void reqSOS3001(final SOS_3001.Request reqData) {
        repository.REQ_SOS_3001(reqData);
    }

    public void reqSOS3002(final SOS_3002.Request reqData) {
        repository.REQ_SOS_3002(reqData);
    }

    public void reqSOS3003(final SOS_3003.Request reqData) {
        repository.REQ_SOS_3003(reqData);
    }

    public void reqSOS3004(final SOS_3004.Request reqData) {
        repository.REQ_SOS_3004(reqData);
    }

    public void reqSOS3005(final SOS_3005.Request reqData) {
        repository.REQ_SOS_3005(reqData);
    }

    public void reqSOS3006(final SOS_3006.Request reqData) {
        repository.REQ_SOS_3006(reqData);
    }

    public void reqSOS3007(final SOS_3007.Request reqData) {
        repository.REQ_SOS_3007(reqData);
    }

    public void reqSOS3008(final SOS_3008.Request reqData) {
        repository.REQ_SOS_3008(reqData);
    }
    
    public VehicleVO getMainVehicleFromDB() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(()->{
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
        }finally {
            es.shutDownExcutor();
        }
    }


}